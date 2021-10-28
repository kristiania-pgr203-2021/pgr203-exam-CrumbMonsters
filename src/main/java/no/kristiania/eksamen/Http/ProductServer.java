package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.product.CategoryDao;
import no.kristiania.eksamen.product.ProductDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProductServer {

    public static void main(String[] args) throws IOException {
        DataSource dataSource = createDataSource();
        CategoryDao categoryDao = new CategoryDao (dataSource);
        ProductDao productDao = new ProductDao (dataSource);
        HttpServer httpServer = new HttpServer(1962);
    }

    private static DataSource createDataSource() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/properties/config.properties");
        Properties properties = new Properties();
        properties.load(fis);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        Flyway.configure().dataSource(dataSource).load();

        return dataSource;
    }
}
