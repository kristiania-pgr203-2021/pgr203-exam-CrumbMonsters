package no.kristiania.eksamen.question;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao extends AbstractDao{

    public AnswerDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object mapRow(ResultSet rs) throws SQLException {
        Answer answer = new Answer();

        answer.setAnswer(rs.getString("qustion_answer"));
        answer.setName(rs.getString("question_Name"));

        return answer;
    }

    public List<Answer> listAllAnswers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answers")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Answer> res = new ArrayList<>();
                    while (rs.next()) {
                        res.add(mapRow(rs));
                    }
                    return res;
                }
            }
        }
    }
}
