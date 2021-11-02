package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.AnswerDao;
import no.kristiania.eksamen.question.QuestionDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProductServer {

    public static void main(String[] args) throws IOException {
        DataSource dataSource = createDataSource();
        QuestionDao questionDao = new QuestionDao (dataSource);
        AnswerDao answerDao = new AnswerDao (dataSource);
        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController("/api/questions", new ListQuestionsController(questionDao));
    }

    private static DataSource createDataSource() throws IOException {
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
