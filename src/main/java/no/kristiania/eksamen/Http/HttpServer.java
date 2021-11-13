package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.Controllers.HttpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class HttpServer {

    private final ServerSocket serverSocket;
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        new Thread(() -> {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClient(clientSocket);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) throws IOException, SQLException {
        HttpMessage httpMessage = new HttpMessage(clientSocket);
        String requestLine = httpMessage.retrieveStartLine();

        logger.info(requestLine, clientSocket.getPort());

        String requestTarget = requestLine.split(" ")[1];
        int questionPos = requestTarget.indexOf('?');
        String fileTarget = questionPos != -1 ? requestTarget.substring(0, questionPos) : requestTarget;

        HttpController controller = QuestionnaireServer.controllers.get(fileTarget);

        if (controller != null) {
            controller.handle(httpMessage);
        } else {
            InputStream fileResource = getClass().getResourceAsStream(fileTarget);
            if (fileResource != null) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                fileResource.transferTo(buffer);
                String responseText = buffer.toString();

                String contentType = "text/plain";
                if (requestTarget.endsWith(".html")) {
                    contentType = "text/html";
                } else if (requestTarget.endsWith(".css")) {
                    contentType = "text/css";
                }
                writeOkResponse(clientSocket, responseText, contentType);
                return;
            }

            String responseText = "File not found: " + requestTarget;

            String response = "HTTP/1.1 404 Not found\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());
        }
    }

    private void writeOkResponse(Socket clientSocket, String responseText, String contentType) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseText.length() + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseText;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
