package no.kristiania.eksamen.DaoTests;

import no.kristiania.eksamen.question.CategoryDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryDaoTest {

    /*private CategoryDao dao = new CategoryDao(TestData.testDataSource());

    @Test
    void shouldListAllCategories() throws SQLException {
        String category1 = "category-" + UUID.randomUUID();
        String category2 = "category-" + UUID.randomUUID();

        dao.save(category1);
        dao.save(category2);

        assertThat(dao.listAll())
                .contains(category1, category2);
    }*/

}