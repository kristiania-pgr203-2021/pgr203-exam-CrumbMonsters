package no.kristiania.eksamen.DaoTests;

import no.kristiania.eksamen.Objects.Question;
import no.kristiania.eksamen.Objects.QuestionDao;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
public class QuestionDaoTest {

    private final QuestionDao dao = new QuestionDao(TestData.testDataSource());

    @Test
    void shouldRetrieveQuestion() throws SQLException {
        Question question = exampleQuestion();
        dao.save(question);
        assertThat(dao.retrieve(question.getName()))
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Disabled
    void shouldListAllQuestions() throws SQLException {
        Question question = exampleQuestion2();
        dao.save(question);
        Question question2 = exampleQuestion3();
        dao.save(question2);

        assertThat(dao.listAll())
                .extracting(Question::getName)
                .contains(question.getName(), question2.getName());
    }

    private Question exampleQuestion() {
        Question question = new Question();
        question.setTitle("Food");
        question.setName("What is your favorite food?");

        return question;
    }

    private Question exampleQuestion2() {
        Question question = new Question();
        question.setTitle("Transport");
        question.setName("What transport vehicle do you use?");

        return question;
    }

    private Question exampleQuestion3() {
        Question question = new Question();
        question.setTitle("Food");
        question.setName("What is your favorite soup?");

        return question;
    }
}
