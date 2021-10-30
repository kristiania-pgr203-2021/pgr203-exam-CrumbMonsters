package no.kristiania.eksamen.DaoTests;

import no.kristiania.eksamen.question.AnswerDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {
    private AnswerDao dao = new AnswerDao(TestData.testDataSource());

    /*@Test
    void shouldListAllCategories() throws SQLException {
        String answer1 = "answer-" + UUID.randomUUID();
        String answer2 = "answer-" + UUID.randomUUID();

        dao.save(answer1);
        dao.save(answer1);

        assertThat(dao.listAll())
                .contains(answer1, answer2);
    }*/
}
