package no.kristiania.eksamen.question;

import javax.sql.DataSource;

public class AnswerDao {
    private final DataSource dataSource;

    public AnswerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
