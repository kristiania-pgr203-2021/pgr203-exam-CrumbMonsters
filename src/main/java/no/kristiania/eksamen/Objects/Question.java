package no.kristiania.eksamen.Objects;

public class Question {

    private String title;
    private String name;
    private long id;
    private String answer;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}