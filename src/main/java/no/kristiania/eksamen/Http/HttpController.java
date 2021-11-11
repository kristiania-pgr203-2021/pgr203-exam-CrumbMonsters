package no.kristiania.eksamen.Http;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public interface HttpController {
    HttpMessage handle(HttpMessage request) throws SQLException, IOException;
}
