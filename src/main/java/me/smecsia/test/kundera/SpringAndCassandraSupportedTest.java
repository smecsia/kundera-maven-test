package me.smecsia.test.kundera;

import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.smecsia.test.kundera.service.AutowiringSupportService;
import me.smecsia.test.kundera.service.EmbeddedCassandraService;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Ilya Sadykov
 *         Date: 02.10.12
 *         Time: 18:18
 */
public class SpringAndCassandraSupportedTest {
    protected static EmbeddedCassandraService embeddedCassandraService;
    protected static ApplicationContext applicationContext;
    private static final String TEST_KEYSPACE_NAME = "KunderaTest";
    public static final int REPLICATION_FACTOR = 1;
    public static final String STRATEGY = "SimpleStrategy";
    public static final String CASSANDRA_CONFIG_FILE = "/META-INF/cassandra-test.yaml";
    public static final String SPRING_CONFIG_FILE = "classpath*:/spring-config.xml";

    static {
        try {
            initCassandraEmbedded();
            createKeySpaceIfNotExist();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void initCassandraEmbedded() throws IOException {
        embeddedCassandraService = new EmbeddedCassandraService(CASSANDRA_CONFIG_FILE);
        embeddedCassandraService.cleanupStorage();
        embeddedCassandraService.init();
        applicationContext = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);
    }

    public SpringAndCassandraSupportedTest() {
        applicationContext.getBean(AutowiringSupportService.class).autowireFields(this);
    }

    /**
     * Creates the keyspace if it does not exist
     */
    private static void createKeySpaceIfNotExist() {
        Cluster cluster = HFactory.getOrCreateCluster(DatabaseDescriptor.getClusterName(),
                DatabaseDescriptor.getRpcAddress().getHostAddress() + ":" +
                        DatabaseDescriptor.getRpcPort());
        if (cluster != null) {
            KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(TEST_KEYSPACE_NAME);
            if (keyspaceDef == null) {
                BasicKeyspaceDefinition ksDef = new BasicKeyspaceDefinition();
                ksDef.setName(TEST_KEYSPACE_NAME);
                ksDef.setReplicationFactor(REPLICATION_FACTOR);
                ksDef.setStrategyClass(STRATEGY);
                cluster.addKeyspace(ksDef);
            }
        }
    }
}
