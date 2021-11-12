package no.kristiania.eksamen.Controllers;

import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.Question;
import no.kristiania.eksamen.Objects.QuestionDao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class NewQuestionController implements HttpController {

    private final QuestionDao questionDao;

    public NewQuestionController (QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();


        question.setTitle(URLDecoder.decode(queryMap.get("question_title"), StandardCharsets.UTF_8.name()));
        question.setName(URLDecoder.decode(queryMap.get("question_name"), StandardCharsets.UTF_8.name()));

        questionDao.save(question);

        String response = "<a href='/index.html'>Click to go to index</a><br>" +
                "<a href='/newQuestion.html'>Click to add more questions<br></a>" +
                "<a href='/listQuestions.html'>Click to answer questions</a>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
