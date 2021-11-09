package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.sql.SQLException;
import java.util.Map;

public class AnswerQuestionController implements HttpController {
    private final QuestionDao questionDao;

    public AnswerQuestionController (QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        for (Question question :
                questionDao.listAll()) {
        question.setName(queryMap.get("questionName"));
        question.setAnswer(queryMap.get("questionAnswer"));
        QuestionDao.answer(question);
        }

        String response = "<a href='/index.html'>Answer registered. Click to go to index</a>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
