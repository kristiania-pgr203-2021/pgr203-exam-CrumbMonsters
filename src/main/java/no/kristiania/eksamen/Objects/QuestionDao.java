package no.kristiania.eksamen.Objects;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private static DataSource dataSource;

    public QuestionDao (DataSource dataSource) {
        this.dataSource = dataSource;
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

    public static void save(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into questions (questiontitle, questionname) values (?, ?)"
            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getName());

                statement.executeUpdate();
            }
        }
    }

    public List<Question> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from questions")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> res = new ArrayList<>();
                    while (rs.next()){
                        res.add(readFromResultSet(rs));
                    }
                    return res;
                }
            }
        }
    }


    public List<Question> listByTitle() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from questions where questiontitle = ?"
            )) {
                statement.setString(1, "questionTitle");
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> questions = new ArrayList<>();

                    while (rs.next()) {
                        questions.add(mapFromResultSet(rs));
                    }
                    return questions;
                }
            }
        }
    }

    private Question readFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setTitle(rs.getString("questionTitle"));
        question.setName(rs.getString("questionName"));

        return question;
    }

    private Question mapFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setTitle(rs.getString("questionTitle"));
        question.setName(rs.getString("questionName"));

        return question;
    }
}
