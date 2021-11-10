package no.kristiania.eksamen.Http;


import no.kristiania.eksamen.question.Answer;
import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.sql.SQLException;
import java.util.Map;

public class AnswerQuestionController implements HttpController {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    public AnswerQuestionController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        for (Question answer:
                questionDao.listAll()) {
            answer.setName(queryMap.get("questionName"));
            answer.setAnswer(queryMap.get("questionAnswer"));
            QuestionDao.saveAnswer(answer);
        }

        String response = "<a href='/index.html'>Answer registered. Click to go to index</a>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}