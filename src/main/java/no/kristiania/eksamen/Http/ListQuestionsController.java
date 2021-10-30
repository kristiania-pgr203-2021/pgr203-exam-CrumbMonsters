package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListQuestionsController implements HttpController{
    
    private final QuestionDao questionDao;
    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
    
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";
        for (Question question :
                questionDao.listAll()) {
            response += "<div>" + question.getTitle() + ", " + question.getName() + "</div>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
