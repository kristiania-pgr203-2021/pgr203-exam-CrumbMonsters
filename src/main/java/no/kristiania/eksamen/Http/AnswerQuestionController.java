package no.kristiania.eksamen.Http;


import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AnswerQuestionController implements HttpController {
    private final QuestionDao questionDao;

    public AnswerQuestionController (QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question answer = new Question();

        String name = queryMap.get("question_Name");
        String nameToString = "";

        for (int i = 0; i < questionDao.listAll().size(); i++) {
            if (i == Integer.parseInt(name)) {
                nameToString = String.valueOf(questionDao.listAll().get(i));
                //System.out.println(questionDao.listAll().get(i));
            }
        }
        String nameToStringDecoded = URLDecoder.decode(nameToString, StandardCharsets.UTF_8.name());
        answer.setName(nameToStringDecoded);

        String answerPreDecoded = queryMap.get("question_Answer");
        String answerDecoded = URLDecoder.decode(answerPreDecoded, StandardCharsets.UTF_8.name());
        answer.setAnswer(answerDecoded);

        QuestionDao.answer(answer);

        String response = "<a href='/index.html'>Click to go to index</a><br>" +
                "<a href='/newQuestion.html'>Click to add more questions</a>";

        return new HttpMessage("HTTP/1.1 300 Redirect", response);
    }
}