package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.sql.SQLException;
import java.util.Map;

public class NewQuestionController implements HttpController {

    private final QuestionDao questionDao;

    public NewQuestionController (QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();
        question.setTitle(queryMap.get("questionTitle"));
        question.setName(queryMap.get("questionName"));
        QuestionDao.save(question);

        return new HttpMessage("HTTP/1.1 200 OK", "\"Product added to inventory<br><br>\" +\n" +
                "                        \"<a href=\\\"/newProduct.html\\\">Click to add more products</a>\" +\n" +
                "                        \"<br>\" +\n" +
                "                        \"<a href=\\\"/listProducts.html\\\">Click to view all products</a>\" +\n" +
                "                        \"<br>\"");
    }
}
