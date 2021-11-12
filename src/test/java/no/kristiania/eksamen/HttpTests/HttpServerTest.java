package no.kristiania.eksamen.HttpTests;

import no.kristiania.eksamen.Http.HttpServer;
import no.kristiania.eksamen.Http.HttpClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private final HttpServer server = new HttpServer(0);

    public HttpServerTest() throws IOException {
    }

    @Test
    void shouldReturn404() throws IOException {
        HttpClient client = new HttpClient( "localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReturnRequestTargetFor404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals("File not found: /non-existing", client.getMessageBody());
    }

    @Test
    void shouldReturn200() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/index.html");
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
    }

    @Test
    void shouldServeFiles() throws IOException {
        Paths.get("target/test-classes");

        String fileContent = "File made at " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/test-file.txt"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/test-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldLetFileExtensionsDefineContentTypes() throws IOException {
        Paths.get("target/test-classes");

        String fileContent = "<p>Hello</p>";
        Files.write(Paths.get("target/test-classes/test-file.html"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/test-file.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }

    @Disabled
    void shouldEchoQueryParameter() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/index.html");
        assertEquals("<!DOCTYPE html>", client.getMessageBody());
    }

    @Test
    void shouldHandleMultipleRequests() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html").
                getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html").
                getStatusCode());
    }
}
