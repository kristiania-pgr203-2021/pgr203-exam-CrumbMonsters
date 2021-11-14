package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.Controllers.*;
import no.kristiania.eksamen.Objects.QuestionDao;
import no.kristiania.eksamen.Objects.AnswerDao;
import no.kristiania.eksamen.Objects.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

import static no.kristiania.eksamen.Objects.AbstractDao.dataSource;

public class QuestionnaireServer {

    private static QuestionDao questionDao = new QuestionDao(dataSource);
    private static AnswerDao answerDao = new AnswerDao(dataSource);
    private static UserDao userDao = new UserDao(dataSource);

    public static Map<String, HttpController> controllers = Map.of("/api/listQuestions", new ListQuestionsController(questionDao),
            "/api/questionSelect", new QuestionSelectController(questionDao),
            "/api/answer", new AnswerQuestionController(answerDao, questionDao),
            "/api/newQuestion", new NewQuestionController(questionDao),
            "/api/viewAnswers", new ViewAnswersController(answerDao),
            "/api/alterQuestion", new AlterQuestionController(questionDao),
            "/api/cookieAPI", new CookieCrumbController(userDao)
    );

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = new HttpServer(1962);


        logger.info("Starting http://localhost:{}/preIndex.html", httpServer.getPort());
    }

}
