package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Answer;
import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AnswerQuestionController implements HttpController {
    private final AnswerDao answerDao;
    private final QuestionDao questionDao;

    public AnswerQuestionController (QuestionDao questionDao, AnswerDao answerDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        for (Question question :
                questionDao.listAll()) {
        Answer answer = new Answer();
        answer.setName(queryMap.get("questionName"));

        String decodedAnswer = URLDecoder.decode(queryMap.get("questionAnswer"), StandardCharsets.UTF_8.name());

        answer.setAnswer(decodedAnswer);
        answerDao.save(answer);
        }

        String response = "<a href='/index.html'>Answer registered. Click to go to index</a>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
