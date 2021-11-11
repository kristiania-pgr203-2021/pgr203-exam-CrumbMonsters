package no.kristiania.eksamen.Controllers;

import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.Question;
import no.kristiania.eksamen.Objects.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class questionSelectController implements HttpController {

    private QuestionDao questionDao;

    public questionSelectController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String responseText = "";

        int value = 0;

        for (Question question :
                questionDao.listAll()) {
            responseText += "<option value=" + (value++) + ">" + question + "</option>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
