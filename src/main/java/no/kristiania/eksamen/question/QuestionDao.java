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

    /*public static void answer (Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("insert into answers (questionname, questionanswer) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, question.getName());
                statement.setString(2, question.getAnswer());

                statement.executeUpdate();
            }
        }
    }*/

    public List<Question> listByTitle(String questionTitle) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from questions where questions.questionTitle ilike ?"
            )) {
                statement.setString(1, questionTitle);
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

    private Question readFromResultSet(ResultSet rs) throws SQLException {
        Question product = new Question();
        product.setTitle(rs.getString("questionTitle"));
        product.setName(rs.getString("questionName"));

        return product;
    }

    private Question mapFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setTitle(rs.getString("questionTitle"));
        question.setName(rs.getString("questionName"));
        return question;
    }

    public static DataSource createDataSource() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/properties/config.properties");
        Properties properties = new Properties();
        properties.load(fis);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        Flyway.configure().dataSource(dataSource).load();

        return dataSource;
    }
}
