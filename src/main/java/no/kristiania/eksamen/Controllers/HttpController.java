package no.kristiania.eksamen.Controllers;

import no.kristiania.eksamen.Http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public interface HttpController {
    HttpMessage handle(HttpMessage request) throws SQLException, IOException;
}
