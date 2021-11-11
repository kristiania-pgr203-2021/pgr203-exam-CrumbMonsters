package no.kristiania.eksamen.Controllers;

import java.sql.SQLException;

import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.Question;
import no.kristiania.eksamen.Objects.QuestionDao;

public class ListQuestionsController implements HttpController {

    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";
        for (Question question :
                questionDao.listAll()) {
            response += "<p>" + question.getTitle() + ", " + question.getName() + "</p>";
        }
        response += "<br><br>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
