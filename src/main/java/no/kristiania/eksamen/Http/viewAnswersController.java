package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Answer;
import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class viewAnswersController implements HttpController {

    private final AnswerDao answerDao;

    public viewAnswersController (AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder response = new StringBuilder();
        for (Answer answer :
                answerDao.listAllAnswers()) {
            response.append(Answer.getName()).append(": ").append(Answer.getAnswer()).append("<br>");
        }

        return new HttpMessage("HTTP/1.1 200 OK", response.toString());
    }
}
