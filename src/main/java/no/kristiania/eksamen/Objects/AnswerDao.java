package no.kristiania.eksamen.Objects;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {

    private static DataSource dataSource;

    public AnswerDao (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Answer> listAllAnswers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from answers")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Answer> res = new ArrayList<>();
                    while (rs.next()){
                        res.add(readFromResultSet(rs));
                    }
                    return res;
                }
            }
        }
    }

    public static void answer(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answers (questionName, questionAnswer) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, answer.getName());
                statement.setString(2, answer.getAnswer());

                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    answer.setId(resultSet.getLong("id"));
                }
            }
        }
    }

    private Answer readFromResultSet(ResultSet rs) throws SQLException {
        Answer answer = new Answer();

        answer.setName(rs.getString("questionName"));
        answer.setAnswer(rs.getString("questionAnswer"));

        return answer;
    }
}
