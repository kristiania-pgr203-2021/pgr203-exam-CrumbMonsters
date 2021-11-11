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

    private static DataSource dataSource;

    public AnswerDao (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answers (questionanswer) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, answer.getAnswer());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answer.setId(rs.getLong("id"));
                }
            }
        }
    }

    public List<Answer> listAllAnswers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answers")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Answer> res = new ArrayList<>();
                    while (rs.next()) {
                        Answer answer = new Answer();
                        answer.setName(rs.getString("questionName"));
                        answer.setAnswer(rs.getString("questionAnswer"));
                        res.add(answer);
                    }
                    return res;
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
}
