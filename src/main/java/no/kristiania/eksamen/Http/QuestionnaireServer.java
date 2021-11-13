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

public class QuestionnaireServer {

    private static DataSource dataSource = Datasource.createDataSource();
    private static QuestionDao questionDao = new QuestionDao(dataSource);
    private static AnswerDao answerDao = new AnswerDao(dataSource);
    private static UserDao userDao = new UserDao(dataSource);

    public static Map<String, HttpController> controllers = Map.of("/api/listQuestions", new ListQuestionsController(questionDao),
            "/api/questionSelect", new questionSelectController(questionDao),
            "/api/answer", new AnswerQuestionController(answerDao, questionDao),
            "/api/newQuestion", new NewQuestionController(questionDao),
            "/api/viewAnswers", new viewAnswersController(answerDao),
            "/api/alterQuestion", new AlterQuestionController(questionDao),
            "/api/cookieAPI", new CookieCrumbController(userDao)
    );

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        //DataSource dataSource = Datasource.createDataSource();
        //QuestionDao questionDao = new QuestionDao (dataSource);
        //AnswerDao answerDao = new AnswerDao(dataSource);
        //UserDao userDao = new UserDao(dataSource);
        HttpServer httpServer = new HttpServer(1962);

        /*controllers = Map.of("/api/listQuestions", new ListQuestionsController(questionDao),
                        "/api/questionSelect", new questionSelectController(questionDao),
                        "/api/answer", new AnswerQuestionController(answerDao, questionDao),
                        "/api/newQuestion", new NewQuestionController(questionDao),
                        "/api/viewAnswers", new viewAnswersController(answerDao),
                        "/api/alterQuestion", new AlterQuestionController(questionDao),
                        "/api/cookieAPI", new CookieCrumbController(userDao)
        );*/

        logger.info("Starting http://localhost:{}/preIndex.html", httpServer.getPort());
    }

}
