package no.kristiania.eksamen.DaoTests;

import no.kristiania.eksamen.product.ProductDao;
import no.kristiania.eksamen.product.Product;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
public class ProductDaoTest {

    /*private final ProductDao dao = new ProductDao(ProductDao.createDataSource());

    public ProductDaoTest() throws IOException {
    }*/

    private final ProductDao dao = new ProductDao(testDataSource());

    private DataSource testDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productdb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        Product product = exampleProduct();
        //noinspection AccessStaticViaInstance
        dao.save(product);
        assertThat(dao.retrieve(product.getId()))
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    void shouldListProductsByName() throws SQLException {
        Product matchingProduct = exampleProduct();
        matchingProduct.setName("TestName");
        dao.save(matchingProduct);
        Product anotherMatchingProduct = exampleProduct();
        anotherMatchingProduct.setName(matchingProduct.getName());
        dao.save(anotherMatchingProduct);

        Product nonMatchingProduct = exampleProduct();
        dao.save(nonMatchingProduct);

        assertThat(dao.listByName(matchingProduct.getName()))
                .extracting(Product::getId)
                .contains(matchingProduct.getId(), anotherMatchingProduct.getId())
                .doesNotContain(nonMatchingProduct.getId());
    }

    @Test
    void shouldListAllProducts() throws SQLException {
        Product product = exampleProduct();
        dao.save(product);
        Product anotherProduct = exampleProduct();
        dao.save(anotherProduct);

        assertThat(dao.listAll())
                .extracting(Product::getId)
                .contains(product.getId(), anotherProduct.getId());
    }

    @Test
    void shouldListByCategory() throws SQLException {
        Product one = exampleProduct();
        one.setCategory("car");
        dao.save(one);
        Product two = exampleProduct();
        two.setCategory("car");
        dao.save(two);
        Product three = exampleProduct();
        dao.save(three);

        assertThat(dao.listByCategory(one.getCategory()))
                .extracting(Product::getId)
                .contains(one.getId(), two.getId())
                .doesNotContain(three.getId());
    }

    private Product exampleProduct() {
        Product product = new Product();
        product.setName(pickOne("NameOne", "NameTwo", "NameThree", "NameFour", "NameFive", "NameSix"));
        product.setDescription(pickOne("This is a pack of" + product.getName(), "A single" + product.getName(), "Vegan", "The best" + product.getName() + "for you", "We like it so you should too"));
        product.setCategory(pickOne("Category 1", "Category 2", "Category 3", "Category 4", "Category 5"));
        product.setPrice(pickOne("20", "50", "100"));

        return product;
    }

    public int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }

}
