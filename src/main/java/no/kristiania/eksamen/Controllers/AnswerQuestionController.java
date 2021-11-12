package no.kristiania.eksamen.Controllers;


import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.Answer;
import no.kristiania.eksamen.Objects.AnswerDao;
import no.kristiania.eksamen.Objects.QuestionDao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AnswerQuestionController implements HttpController {
    private final AnswerDao answerDao;
    private final QuestionDao questionDao;

    public AnswerQuestionController (AnswerDao answerDao, QuestionDao questionDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Answer answer = new Answer();

        String name = queryMap.get("questionName");
        String nameToString = "";

        for (int i = 0; i < questionDao.listAllQues().size(); i++) {
            if (i == Integer.parseInt(name)) {
                nameToString = String.valueOf(questionDao.listAllQues().get(i));

            }
        }

        String nameToStringDecoded = URLDecoder.decode(nameToString, StandardCharsets.UTF_8.name());
        answer.setName(nameToStringDecoded);

        String answerDecoded = URLDecoder.decode(queryMap.get("questionAnswer"), StandardCharsets.UTF_8.name());
        answer.setAnswer(answerDecoded);

        answerDao.answer(answer);

        String response = "<a href='/index.html'>Click to go to index</a><br>" +
                "<a href='/viewAnswer.html'>Click to view answers</a><br>" +
                "<a href='/listQuestions.html'>Click to answer more questions</a>";

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}