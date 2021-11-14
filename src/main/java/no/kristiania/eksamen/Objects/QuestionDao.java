package no.kristiania.eksamen.Objects;

import no.kristiania.eksamen.Http.Datasource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends AbstractDao<Question> {

    public QuestionDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Question resMap(ResultSet rs) throws SQLException {
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

    public static void saveForTest(Question question) throws SQLException {
        try (Connection connection = Datasource.testDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into questions (question_title, question_name) values (?, ?)"
            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getName());
                statement.executeUpdate();
            }
        }
    }

    public List<Question> listAllQues() throws SQLException {
        return super.listAll("SELECT * FROM questions");
    }

    public Question retrieve(String name) throws SQLException {
        return super.retrieve(name, "SELECT * FROM questions WHERE question_name = ?");
    }

    public static void alter(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "update questions set question_name = (?) where question_name = (?);" +
                            "update questions set question_title = (?) where question_name = (?)"
            )) {
                statement.setString(1, question.getNewName());
                statement.setString(2, question.getName());
                statement.setString(3, question.getNewTitle());
                statement.setString(4, question.getNewName());
                statement.executeUpdate();
            }
        }
    }
}