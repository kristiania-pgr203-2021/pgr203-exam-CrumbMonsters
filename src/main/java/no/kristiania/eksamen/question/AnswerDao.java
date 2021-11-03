package no.kristiania.eksamen.question;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {

    private static DataSource dataSource;

    public AnswerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void answer(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answers (questionname, questionanswer) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, question.getName());
                statement.setString(2, question.getAnswer());
                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    Answer.setId(resultSet.getLong("answerid"));
                }
            }
        }
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
}
