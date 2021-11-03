package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.sql.SQLException;

public class ListQuestionsController implements HttpController{
    
    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "<form action=\"/api/answer\" method=\"post\">";

        for (Question question :
                questionDao.listAll()) {
            response += question.getTitle() + ": " + question.getName() +
                    "<input type='text' value='' name='questionAnswer' /><button>Submit</button><br>";
        }

        response += "</form>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
