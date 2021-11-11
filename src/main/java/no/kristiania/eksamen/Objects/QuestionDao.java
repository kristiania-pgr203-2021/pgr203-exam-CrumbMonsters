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

        question.setId(rs.getLong("question_id"));
        question.setTitle(rs.getString("question_Title"));
        question.setName(rs.getString("question_Name"));

        return question;
    }



    public static void save(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into questions (question_title, question_name) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getName());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    question.setId(generatedKeys.getLong("question_id"));
                }
            }
        }
    }

    public Question retrieve(long id) throws SQLException {
        return super.retrieve(id, "SELECT * FROM questions WHERE question_id = ?");
    }

    public Question retrieve (String questionName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from questions where questionname = ?"
            )) {
                statement.setString(1, questionName);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return mapFromResultSet(rs);
                }
            }
        }
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


    public List<Question> listByTitle() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from questions where question_title = ?"
            )) {
                statement.setString(1, "questionTitle");
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> questions = new ArrayList<>();

                    while (rs.next()) {
                        questions.add(mapRow(rs));
                    }
                    return questions;
                }
            }
        }
    }


        return question;
    }
}
