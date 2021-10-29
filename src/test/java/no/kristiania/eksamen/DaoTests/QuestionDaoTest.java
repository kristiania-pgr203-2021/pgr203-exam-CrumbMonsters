package no.kristiania.eksamen.DaoTests;

import no.kristiania.eksamen.question.ProductDao;
import no.kristiania.eksamen.question.Question;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
public class QuestionDaoTest {

    /*private final ProductDao dao = new ProductDao(ProductDao.createDataSource());

    public ProductDaoTest() throws IOException {
    }

    private final ProductDao dao = new ProductDao(testDataSource());

    private DataSource testDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productdb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        Question question = exampleProduct();
        //noinspection AccessStaticViaInstance
        dao.save(question);
        assertThat(dao.retrieve(question.getId()))
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Test
    void shouldListProductsByName() throws SQLException {
        Question matchingQuestion = exampleProduct();
        matchingQuestion.setName("TestName");
        dao.save(matchingQuestion);
        Question anotherMatchingQuestion = exampleProduct();
        anotherMatchingQuestion.setName(matchingQuestion.getName());
        dao.save(anotherMatchingQuestion);

        Question nonMatchingQuestion = exampleProduct();
        dao.save(nonMatchingQuestion);

        assertThat(dao.listByName(matchingQuestion.getName()))
                .extracting(Question::getId)
                .contains(matchingQuestion.getId(), anotherMatchingQuestion.getId())
                .doesNotContain(nonMatchingQuestion.getId());
    }

    @Test
    void shouldListAllProducts() throws SQLException {
        Question question = exampleProduct();
        dao.save(question);
        Question anotherQuestion = exampleProduct();
        dao.save(anotherQuestion);

        assertThat(dao.listAll())
                .extracting(Question::getId)
                .contains(question.getId(), anotherQuestion.getId());
    }

    @Test
    void shouldListByCategory() throws SQLException {
        Question one = exampleProduct();
        one.setCategory("car");
        dao.save(one);
        Question two = exampleProduct();
        two.setCategory("car");
        dao.save(two);
        Question three = exampleProduct();
        dao.save(three);

        assertThat(dao.listByCategory(one.getCategory()))
                .extracting(Question::getId)
                .contains(one.getId(), two.getId())
                .doesNotContain(three.getId());
    }

    private Question exampleProduct() {
        Question question = new Question();
        question.setName(pickOne("NameOne", "NameTwo", "NameThree", "NameFour", "NameFive", "NameSix"));
        question.setDescription(pickOne("This is a pack of" + question.getName(), "A single" + question.getName(), "Vegan", "The best" + question.getName() + "for you", "We like it so you should too"));
        question.setCategory(pickOne("Category 1", "Category 2", "Category 3", "Category 4", "Category 5"));
        question.setPrice(pickOne("20", "50", "100"));

        return question;
    }

    public int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }*/

}
