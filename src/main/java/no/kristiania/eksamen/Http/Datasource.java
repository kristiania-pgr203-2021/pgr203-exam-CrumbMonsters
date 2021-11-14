package no.kristiania.eksamen.Http;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.util.Properties;

public class Datasource {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    static DataSource createDataSource() {

        String[] propertyKeys = {"URL", "username", "password"};
        Properties properties = new Properties();

        try (FileReader fileReader = new FileReader("config.properties")) {
            properties.load(fileReader);

            for (String key : propertyKeys) {
                if (!properties.containsKey(key)) {
                    logger.warn("Key missing from from properties file: " + key);
                } else if (properties.getProperty(key).length() == 0) {
                    logger.warn("Value missing from key in properties file: " + key);
                }
            }
        } catch (Exception e) {
            logger.warn("Properties file not found: " + e.getMessage());
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource. setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        logger.info("Using database {}", dataSource.getUrl());

        Flyway.configure().dataSource(dataSource).load().baseline();

        return dataSource;
    }
}
