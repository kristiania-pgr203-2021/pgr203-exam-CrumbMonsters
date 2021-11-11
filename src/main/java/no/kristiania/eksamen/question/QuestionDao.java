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

    public static void answer (Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answers (question_name, question_answer) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, question.getName());
                statement.setString(2, question.getAnswer());
                statement.executeUpdate();
                {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        generatedKeys.next();
                        question.setId(generatedKeys.getInt("id"));
                    }
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


}
