package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.question.Answer;
import no.kristiania.eksamen.question.Question;
import no.kristiania.eksamen.question.QuestionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListQuestionsController implements HttpController{
    
    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";

        ArrayList<Question> arrayList = new ArrayList<>();

        for (Question question :
                questionDao.listAll()) {
            question.getTitle();
            question.getName();

            arrayList.add(question);
        }

        for (int i = 0; i < arrayList.size(); i++) {
            response += arrayList.get(i) + "<input type='text' name='questionAnswer' />" +
                    "<form action='/api/answer' method='post'> <button>Submit</button> </form> <br>";
        }



        /*HashMap<String, String> map = new HashMap<>();

        for (Question question :
                questionDao.listAll()) {
            question.setTitle("questionTitle");
            question.setName("questionName");
            map.put(question.getTitle(), question.getName());
        }

        for (int i = 0; i < map.size(); i++) {
            response += map.get(i);
        }*/

        return new HttpMessage("HTTP/1.1 200 OK", response);


        /*String form = "<form action=\"/api/answer\" method=\"post\">";
        StringBuilder response = new StringBuilder();

        for (Question question :
                questionDao.listAll()) {
            response.append(form).append(question.getTitle()).append(": ").append(question.getName())
                    .append("<input type='text' value='' name='questionAnswer' /><button>Submit</button></form><br>");
        }

        return new HttpMessage("HTTP/1.1 200 OK", response.toString());*/
        }
}
