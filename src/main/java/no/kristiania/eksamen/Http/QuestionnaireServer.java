package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.Controllers.*;
import no.kristiania.eksamen.Objects.QuestionDao;
import no.kristiania.eksamen.Objects.AnswerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;

public class QuestionnaireServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        DataSource dataSource = Datasource.createDataSource();
        QuestionDao questionDao = new QuestionDao (dataSource);
        AnswerDao answerDao = new AnswerDao(dataSource);
        HttpServer httpServer = new HttpServer(1962);

        httpServer.addController("/api/listQuestions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/questionSelect", new questionSelectController(questionDao));
        httpServer.addController("/api/answer", new AnswerQuestionController(answerDao, questionDao));
        httpServer.addController("/api/newQuestion", new NewQuestionController(questionDao));
        httpServer.addController("/api/viewAnswers", new viewAnswersController(answerDao));
        httpServer.addController("/api/alterQuestion", new AlterQuestionController(questionDao));

        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

}
