package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.QuestionDao;
import no.kristiania.eksamen.question.AnswerDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QuestionnaireServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        DataSource dataSource = createDataSource();
        QuestionDao questionDao = new QuestionDao (dataSource);
        AnswerDao answerDao = new AnswerDao(dataSource);
        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController("/api/listQuestions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/questionSelect", new questionSelectController(questionDao));
        httpServer.addController("/api/answer", new AnswerQuestionController(questionDao));
        httpServer.addController("/api/newQuestion", new NewQuestionController(questionDao));
        httpServer.addController("/api/viewAnswers", new viewAnswersController(answerDao));
        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        String[] keys  = {"URL", "username", "password"};
        FileInputStream fis = new FileInputStream("src/main/resources/properties/config.properties");
        Properties properties = new Properties();

        try(fis){
            properties.load(fis);

            for (String key : keys) {
                if (!properties.containsKey(key)) {
                    logger.warn("Properties file missing key: " + key);
                } else if (properties.getProperty(key).length() == 0) {
                    logger.warn("Missing value for property: " + key);
                }
            }

        }catch (Exception e) {
            logger.warn("Properties file does not exist - " + e.getMessage());
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        logger.info("Using database {}", dataSource.getUrl());

        Flyway.configure().dataSource(dataSource).load();
        logger.info("Started on http://localhost:{}/index.html", 1962);

        return dataSource;

    }
}
