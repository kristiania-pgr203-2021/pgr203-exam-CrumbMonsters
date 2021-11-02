package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.QuestionDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class QuestionnaireServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DataSource dataSource = createDataSource();
        QuestionDao questionDao = new QuestionDao (dataSource);
        AnswerDao answerDao = new AnswerDao (dataSource);
        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController("/api/questions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/newQuestion", new NewQuestionController(questionDao));
        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException, GeneralSecurityException {
        FileInputStream fis = new FileInputStream("src/main/resources/properties/config.properties");
        Properties properties = new Properties();
        properties.load(fis);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        Flyway.configure().dataSource(dataSource).load();

        return dataSource;
    }
}
