package no.kristiania.eksamen.Http;

import java.io.IOException;
import java.sql.SQLException;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

public class ListQuestionsController implements HttpController {

    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String response = "";
        for (Question question :
                questionDao.listAll()) {
            response += "<p>" + question.getTitle() + ", " + question.getName() + "<br>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
