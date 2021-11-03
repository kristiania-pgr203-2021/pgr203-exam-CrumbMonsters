package no.kristiania.eksamen.question;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnswerDao {

    /*private static DataSource dataSource;

    public AnswerDao (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answers")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<String> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(rs.getString("questionName"));
                    }
                    return result;
                }
            }
        }
    }

    public List<Answer> listByQuestion(String questionName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from answers where questionname ilike ?"
            )) {
                statement.setString(1, questionName);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Answer> answers = new ArrayList<>();

                    while (rs.next()) {
                        answers.add(mapFromResultSet(rs));
                    }
                    return answers;
                }
            }
        }
    }

    private Answer mapFromResultSet(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setName(rs.getString("questionName"));
        answer.setAnswer(rs.getString("questionAnswer"));
        return answer;
    }

    public static DataSource dataSource() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/properties/config.properties");
        Properties properties = new Properties();
        properties.load(fis);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        Flyway.configure().dataSource(dataSource).load();

        return dataSource;
    }*/
}
