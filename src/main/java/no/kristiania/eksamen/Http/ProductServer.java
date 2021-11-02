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

import static no.kristiania.eksamen.Http.Encryption.*;

public class ProductServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DataSource dataSource = createDataSource();
        QuestionDao questionDao = new QuestionDao (dataSource);
        AnswerDao answerDao = new AnswerDao (dataSource);
        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController("/api/questions", new ListQuestionsController(questionDao));
        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException, GeneralSecurityException {
        FileInputStream fis = new FileInputStream("src/main/resources/properties/config.properties");
        Properties properties = new Properties();
        properties.load(fis);

        byte[] salt = "12345678".getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        String password = properties.getProperty("password");

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));

        if (password == null) {
            throw new IllegalArgumentException("Run with -Dpassword=<password>");
        }

        SecretKeySpec key = createSecretKey(password.toCharArray(), salt, iterationCount, keyLength);


        //Remember to remove
        System.out.println("Original password: " + password);
        String encryptedPassword = encrypt(password, key);
        System.out.println("Encrypted password: " + encryptedPassword);
        String decryptedPassword = decrypt(encryptedPassword, key);
        System.out.println("Decrypted password: " + decryptedPassword);

        dataSource.setPassword(decryptedPassword);


        Flyway.configure().dataSource(dataSource).load();

        return dataSource;
    }
}
