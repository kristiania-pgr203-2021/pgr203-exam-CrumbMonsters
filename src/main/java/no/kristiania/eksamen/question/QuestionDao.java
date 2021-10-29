package no.kristiania.eksamen.question;

import javax.sql.DataSource;

public class QuestionDao {
    private final DataSource dataSource;

    public QuestionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
