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
        /*String response = "";
        for (Question question :
                questionDao.listAll()) {
            response += question.getTitle() + ": " + question.getName() +
                    "<input type='text' value='' name='questionAnswer' />" +
                    "<form action=\"/api/answer\" method=\"post\"><button>Submit</button></form>";
        }

         */
        String form = "<form action=\"/api/answer\" method=\"post\">";
        StringBuilder response = new StringBuilder();

        for (Question question :
                questionDao.listAll()) {
            response.append(form).append(question.getTitle()).append(": ").append(question.getName())
                    .append("<input type='text' value='' name='questionAnswer' /><button>Submit</button></form><br>");
        }

        return new HttpMessage("HTTP/1.1 200 OK", response.toString());
    }
}
