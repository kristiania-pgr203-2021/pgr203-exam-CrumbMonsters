package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

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

        String decodedTitle = URLDecoder.decode(queryMap.get("question_Title"), StandardCharsets.UTF_8.name());
        String decodedName = URLDecoder.decode(queryMap.get("question_Name"), StandardCharsets.UTF_8.name());

        question.setTitle(decodedTitle);
        question.setName(decodedName);

        questionDao.save(question);

        String response = "<a href='/index.html'>Click to go to index</a><br>" +
                "<a href='/newQuestion.html'>Click to add more questions</a>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
