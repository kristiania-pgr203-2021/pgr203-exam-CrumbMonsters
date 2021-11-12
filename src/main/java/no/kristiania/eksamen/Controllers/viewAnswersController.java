package no.kristiania.eksamen.Controllers;

import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.Answer;
import no.kristiania.eksamen.Objects.AnswerDao;

import java.sql.SQLException;

public class viewAnswersController implements HttpController {

    private AnswerDao answerDao;

    public viewAnswersController (AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder response = new StringBuilder();
        for (Answer answer :
                answerDao.listAllAns()) {
            response.append(answer.getName()).append(" - ").append(answer.getAnswer()).append("<br>");
        }

        return new HttpMessage("HTTP/1.1 200 OK", response.toString());
    }
}
