package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.Controllers.*;
import no.kristiania.eksamen.Objects.QuestionDao;
import no.kristiania.eksamen.Objects.AnswerDao;
import no.kristiania.eksamen.Objects.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;

public class QuestionnaireServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(1962);

        DataSource datasource = Datasource.createDataSource();
        QuestionDao questionDao = new QuestionDao(datasource);
        AnswerDao answerDao = new AnswerDao(datasource);
        UserDao userDao = new UserDao(datasource);

        httpServer.addController("/api/listQuestions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/questionSelect", new QuestionSelectController(questionDao));
        httpServer.addController("/api/answer", new AnswerQuestionController(answerDao, questionDao));
        httpServer.addController("/api/newQuestion", new NewQuestionController(questionDao));
        httpServer.addController("/api/viewAnswers", new ViewAnswersController(answerDao));
        httpServer.addController("/api/alterQuestion", new AlterQuestionController(questionDao));
        httpServer.addController("/api/cookieAPI", new CookieCrumbController(userDao));


        logger.info("Starting http://localhost:{}/preIndex.html", httpServer.getPort());
    }

}
