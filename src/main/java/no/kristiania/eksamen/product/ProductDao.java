package no.kristiania.eksamen.product;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDao {

    private static DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        ProductDao.dataSource = dataSource;
    }

    public static DataSource createDataSource() throws IOException {
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

    public static void save(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into products (product_name, product_description, product_price, product_category) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, product.getName());
                statement.setString(2, product.getDescription());
                statement.setString(3, product.getPrice());
                statement.setString(4, product.getCategory());

                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    product.setId(resultSet.getLong("id"));
                }
            }
            /*try (PreparedStatement statement = connection.prepareStatement(
                    "insert into categories (category_name) values (?) "
            )) {
                statement.setString(1, product.getCategory());

                statement.executeUpdate();

            }*/
        }
    }

    public Product retrieve(long id) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from products where id = ?"
            )) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return mapFromResultSet(rs);
                }
            }
        }
    }

    private Product mapFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("product_name"));
        product.setDescription(rs.getString("product_description"));
        product.setPrice(rs.getString("product_price"));
        product.setCategory(rs.getString("product_category"));
        return product;
    }

    public List<Product> listByName(String productName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from products where product_name ilike ?"
            )) {
                statement.setString(1, productName);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Product> products = new ArrayList<>();

                    while (rs.next()) {
                        products.add(mapFromResultSet(rs));
                    }
                    return products;
                }
            }
        }
    }

    public List<Product> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from products")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Product> res = new ArrayList<>();
                    while (rs.next()){
                        res.add(readFromResultSet(rs));
                    }
                    return res;
                }
            }
        }
    }

    public List<Product> listByCategory(String category) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from products where product_category = ?"
            )) {
                statement.setString(1, category);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Product> result = new ArrayList<>();

                    while (rs.next()){
                        result.add(readFromResultSet(rs));
                    }

                    return result;
                }
            }
        }
    }

    private Product readFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("product_name"));
        product.setDescription(rs.getString("product_description"));
        product.setPrice(rs.getString("product_price"));
        product.setCategory(rs.getString("product_category"));

        return product;
    }
}
