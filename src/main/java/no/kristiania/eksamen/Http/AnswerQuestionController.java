package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AnswerQuestionController implements HttpController {
    private final QuestionDao questionDao;

    public AnswerQuestionController (QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question ans = new Question();
        ans.setName(queryMap.get("questionName"));
        ans.setAnswer(queryMap.get("questionAnswer"));
        QuestionDao.answer(ans);

        String response = "OK";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
