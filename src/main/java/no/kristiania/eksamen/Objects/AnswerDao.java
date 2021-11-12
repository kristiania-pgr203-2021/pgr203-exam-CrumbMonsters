package no.kristiania.eksamen.Objects;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao extends AbstractDao<Answer>{

    public AnswerDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Answer resMap(ResultSet rs) throws SQLException {
        Answer answer = new Answer();

        answer.setName(rs.getString("question_answered"));
        answer.setAnswer(rs.getString("question_answer"));

        return answer;
    }

    public List<Answer> listAllAns() throws SQLException {
        return super.listAll("SELECT * FROM answers");
    }

    public static void answer(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answers (question_answered, question_answer) values (?, ?)",
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
}
