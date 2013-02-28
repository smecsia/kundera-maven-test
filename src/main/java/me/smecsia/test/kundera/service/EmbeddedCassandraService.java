package me.smecsia.test.kundera.service;

import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.smecsia.test.kundera.util.ArrayUtil;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.CassandraDaemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Embedded Cassandra instance runner
 *
 * @author ilyasadykov
 */
public class EmbeddedCassandraService extends BasicService {

    public static final String DEFAULT_STRATEGY = "SimpleStrategy";
    public static final int DEFAULT_REPLICATION_FACTOR = 1;
    private boolean initialized = false;

    private CassandraDaemon cassandraDaemon;

    private String cassandraConfigFile = null;

    public EmbeddedCassandraService() {
    }

    @Autowired
    public EmbeddedCassandraService(@Value("${aimy.db.nosql.cassandraConfigFile}") String cassandraConfigFile) {
        this.cassandraConfigFile = cassandraConfigFile;
        preConfigure();
    }

    public void setCassandraConfigFile(String cassandraConfigFile) {
        this.cassandraConfigFile = cassandraConfigFile;
    }

    public void preConfigure() {
        if (cassandraConfigFile == null) {
            throw new RuntimeException("Embedded Cassandra configuration file path cannot be null! ");
        }
        URL configRes = getClass().getResource(cassandraConfigFile);
        if (configRes == null) {
            throw new RuntimeException("Cannot start embedded Cassandra instance with the config file: " + cassandraConfigFile);
        }
        System.setProperty("cassandra.config", configRes.toString());
        System.setProperty("log4j.defaultInitOverride", "false");
        System.setProperty("cassandra-foreground", "true");
    }

    /**
     * Initialize cassandra service
     */
    public void init() {
        logger.info("Activating Embedded Cassandra daemon...");
        if (!initialized) {
            try {
                cassandraDaemon = new CassandraDaemon();
                cassandraDaemon.activate();
                initialized = true;
            } catch (Exception e) {
                logAndThrow(e);
            }
        } else
            logAndThrow("Cassandra embedded instance already initialized!");
    }

    public void cleanupStorage() throws IOException {
        cleanupStorage(
                ArrayUtil.add(DatabaseDescriptor.getAllDataFileLocations(),
                        DatabaseDescriptor.getCommitLogLocation(),
                        DatabaseDescriptor.getSavedCachesLocation())
        );
    }

    public void cleanupStorage(String... locations) throws IOException {
        System.out.println("Performing the storage cleanup...");
        for (String tmpLocation : locations) {
            File file = new File(tmpLocation);
            if (file.isDirectory() && file.exists()) {
                FileUtils.deleteRecursive(file);
            }
        }
    }

    /**
     * Deactivates embedded cassandra service
     */
    public synchronized void deactivate() {
        try {
            cassandraDaemon.deactivate();
        } catch (Exception e) {
            logAndThrow("Cannot stop Cassandra instance: " + e.getMessage());
        }

    }

    public boolean isRunning() {
        return isInitialized() && cassandraDaemon.thriftServer.isRunning();
    }

    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Creates the keyspace if it does not exist
     */
    public void createKeySpaceIfNotExist(String keySpaceName) {
        Cluster cluster = HFactory.getOrCreateCluster(DatabaseDescriptor.getClusterName(),
                DatabaseDescriptor.getRpcAddress().getHostAddress() + ":" +
                        DatabaseDescriptor.getRpcPort());
        if (cluster != null) {
            KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(keySpaceName);
            if (keyspaceDef == null) {
                BasicKeyspaceDefinition ksDef = new BasicKeyspaceDefinition();
                ksDef.setName(keySpaceName);
                ksDef.setReplicationFactor(DEFAULT_REPLICATION_FACTOR);
                ksDef.setStrategyClass(DEFAULT_STRATEGY);
                cluster.addKeyspace(ksDef);
            }
        }
    }
}
