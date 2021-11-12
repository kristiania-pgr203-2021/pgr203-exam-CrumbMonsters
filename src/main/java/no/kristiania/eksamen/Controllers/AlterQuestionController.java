package no.kristiania.eksamen.Controllers;

import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.Answer;
import no.kristiania.eksamen.Objects.Question;
import no.kristiania.eksamen.Objects.QuestionDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AlterQuestionController implements HttpController {

    private QuestionDao questionDao;
    public AlterQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();

        String name = queryMap.get("questionName");
        String nameToString = "";

        for (int i = 0; i < questionDao.listAll().size(); i++) {
            if (i == Integer.parseInt(name)) {
                nameToString = String.valueOf(questionDao.listAll().get(i));
            }
        }

        question.setName(URLDecoder.decode(nameToString, StandardCharsets.UTF_8.name()));

        question.setNewName(URLDecoder.decode(queryMap.get("newquestionname"), StandardCharsets.UTF_8.name()));
        question.setNewTitle(URLDecoder.decode(queryMap.get("newquestiontitle"), StandardCharsets.UTF_8.name()));

        questionDao.alter(question);

        String response = "<a href='/index.html'>Click to go to index</a><br>" +
                "<a href='/alterQuestion.html'>Click to alter more questions</a>";

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
