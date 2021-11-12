package no.kristiania.eksamen.Controllers;

import no.kristiania.eksamen.Http.HttpController;
import no.kristiania.eksamen.Http.HttpMessage;
import no.kristiania.eksamen.Objects.UserDao;
import no.kristiania.eksamen.Objects.Username;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class CookieCrumbController implements HttpController {

    private final UserDao userDao;

    public CookieCrumbController (UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Username username = new Username();

        username.setName(URLDecoder.decode(queryMap.get("usernameBox"), StandardCharsets.UTF_8.name()));

        userDao.saveUser(username);

        String response = "<a href='/index.html'>Thanks, click to go to index</a><br>";
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
