package no.kristiania.eksamen.Objects;

import javax.sql.DataSource;
import java.sql.*;

import static no.kristiania.eksamen.Objects.AbstractDao.dataSource;

public class UserDao {

    private static DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void saveUser(Username username) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into cookiecrumbs (username) values (?) on conflict do nothing ",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, username.getName());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    username.setId(resultSet.getLong("id"));
                }
            }
        }
    }
}
