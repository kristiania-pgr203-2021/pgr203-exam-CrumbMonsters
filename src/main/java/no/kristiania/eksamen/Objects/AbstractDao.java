package no.kristiania.eksamen.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);
    public static DataSource dataSource;
    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected T retrieve(String name, String sql) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return resMap(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    protected List<T> listAll (String sql) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<T> list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(resMap(rs));
                    }
                    return list;
                }
            }
        }
    }


    protected abstract T resMap(ResultSet rs) throws SQLException;

}
