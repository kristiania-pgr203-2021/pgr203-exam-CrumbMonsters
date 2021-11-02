package no.kristiania.eksamen.question;

public class Answer {
    private static String name;
    private static String answer;
    private static long id;

    public long getId() {
        return id;
    }

    public static void setId(long id) {
        Answer.id = id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        Answer.name = name;
    }

    public static String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        Answer.answer = answer;
    }
}
