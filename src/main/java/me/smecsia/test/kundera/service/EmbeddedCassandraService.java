package me.smecsia.test.kundera.service;

import me.smecsia.test.kundera.util.ArrayUtil;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.thrift.CassandraDaemon;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Embedded Cassandra instance runner
 *
 * @author ilyasadykov
 */
public class EmbeddedCassandraService extends BasicService {

    private Boolean initialized = false;

    private CassandraDaemon cassandraDaemon;

    private String cassandraConfigFile = null;

    @Autowired
    public EmbeddedCassandraService(String cassandraConfigFile) {
        this.cassandraConfigFile = cassandraConfigFile;
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
        return isInitialized() && cassandraDaemon.isRPCServerRunning();
    }

    public Boolean isInitialized() {
        return initialized;
    }
}
