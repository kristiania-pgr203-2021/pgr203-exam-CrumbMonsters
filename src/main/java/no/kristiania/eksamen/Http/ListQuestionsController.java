package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.sql.SQLException;
import java.util.HashMap;

public class ListQuestionsController implements HttpController{

    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder response = new StringBuilder();

        response.append("<h3 id='headerSize3'>Which question would you like to answer?</h3><br><br>");

        for (Question question :
                questionDao.listAll()) {
            response.append(question.getId()).append(": ").append(question.getTitle()).append(", ").append(question.getName())
                    .append("<br><br><br>");
        }

        response.append("<form action=\"/api/answer\" method=\"post\">" +
                "Which question would you like to answer? (enter id) <input type='text' value='' name='questionId' />" +
                "---What is your answer? <input type='text' value='' name='questionAnswer' />" +
                "<button>Submit</button></form>");

        return new HttpMessage("HTTP/1.1 200 OK", response.toString());
    }

        /*String form = "<form action=\"/api/answer\" method=\"post\">";
        StringBuilder response = new StringBuilder();
        for (Question question :
                questionDao.listAll()) {
            response.append(form).append(question.getTitle()).append(": ").append(question.getName())
                    .append("<input type='text' value='' name='questionAnswer' /><button>Submit</button></form><br>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", response.toString());
    }*/
}