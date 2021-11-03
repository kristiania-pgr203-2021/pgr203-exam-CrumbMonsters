package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Answer;
import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class viewAnswersController implements HttpController {
    private final QuestionDao questionDao;

    public viewAnswersController (QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";
        for (Question question :
                questionDao.listAllAnswers()) {
            response += question.getName() + ": " + question.getAnswer() + "<br>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
