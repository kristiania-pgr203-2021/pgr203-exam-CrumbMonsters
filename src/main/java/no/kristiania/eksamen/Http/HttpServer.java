package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Question;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;

import static no.kristiania.eksamen.Http.HttpMessage.parseRequestParameters;

public class HttpServer {

    private final ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        new Thread(this::clientHandler).start();
    }

    private void clientHandler() {
        try {
            while (true){
                handleClient();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException, SQLException {
        Socket clientSocket = serverSocket.accept();

        HttpMessage httpMessage = new HttpMessage(clientSocket);
        String[] requestLine = httpMessage.startLine.split(" ");
        String requestTarget = requestLine[1];

        int questionPos = requestTarget.indexOf('?');
        String fileTarget;
        String query = null;
        if (questionPos != -1) {
            fileTarget = requestTarget.substring(0, questionPos);
            query = requestTarget.substring(questionPos+1);
        } else {
            fileTarget = requestTarget;
        }

        switch (fileTarget) {
            case "/api/questions": {
                Map<String, String> queryMap = parseRequestParameters(httpMessage.messageBody);
                Question question = new Question();

                question.setTitle(queryMap.get("questionTitle"));
                question.setName(queryMap.get("questionName"));
                question.setAnswer(queryMap.get("questionAnswer"));

                String responseText = "Done<br><br>" +
                        "<a href=\"/newQuestion.html\">Click to add another question</a>" +
                        "<br>";

                writeOkResponse(clientSocket, responseText, "text/html");
            }
            case "/api/alternativeAnswers": {
                String responseText = "456";
                writeOkResponse(clientSocket, responseText, "text/html");
                break;
            }
            case "/api/questionOptions": {
                String responseText = "789";
                writeOkResponse(clientSocket, responseText, "text/html");
                break;
            }
            default: {
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
                break;
            }
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
