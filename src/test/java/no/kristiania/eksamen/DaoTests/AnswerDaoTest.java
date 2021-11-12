package no.kristiania.eksamen.DaoTests;

import no.kristiania.eksamen.Objects.Answer;
import no.kristiania.eksamen.Objects.AnswerDao;
import no.kristiania.eksamen.Objects.Question;
import no.kristiania.eksamen.Objects.QuestionDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static no.kristiania.eksamen.DaoTests.TestData.pickOne;
import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {

    private final AnswerDao dao = new AnswerDao(TestData.testDataSource());

    @Test
    void shouldAnswerQuestion() throws SQLException {
        Answer answer = exampleAnswer();
        answer.setName("Favorite food?");
        answer.setAnswer(pickOne("Pizza", "Burger", "Pasta", "Soup", "Grilled cheese"));
        dao.answer(answer);
    }

    @Test
    void shouldRetrieveAnswers() throws SQLException {
        Answer answer = exampleAnswer();
        dao.answer(answer);
        Answer answer2 = exampleAnswer2();
        dao.answer(answer2);

        assertThat(dao.listAllAnswers())
                .extracting(Answer::getName)
                .contains(answer.getName(), answer2.getName());
    }

    private Answer exampleAnswer() {
        Answer answer = new Answer();
        answer.setName("Question");
        answer.setAnswer("Answer");
        return answer;
    }

    private Answer exampleAnswer2() {
        Answer answer = new Answer();
        answer.setName("Question");
        answer.setAnswer("Answer");
        return answer;
    }
}
