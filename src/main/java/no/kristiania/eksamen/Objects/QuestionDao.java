package no.kristiania.eksamen.Objects;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends AbstractDao<Question> {

    public QuestionDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Question mapRow(ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setTitle(rs.getString("question_title"));
        question.setName(rs.getString("question_name"));

        return question;
    }

    public static void save(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into questions (question_title, question_name) values (?, ?) ON CONFLICT DO NOTHING"
            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getName());
                statement.executeUpdate();
            }
        }
    }

    public Question retrieve(String name) throws SQLException {
        return super.retrieve(name, "SELECT * FROM questions WHERE question_name = ?");
    }

    public List<Question> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from questions")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> res = new ArrayList<>();
                    while (rs.next()) {
                        res.add(mapRow(rs));
                    }
                    return res;
                }
            }
        }
    }
}