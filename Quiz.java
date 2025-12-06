/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizapp;

/**
 *
 * @author Mega Providers
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {
    //encapsulation
    private static final long serialVersionUID = 1L;
    private String id; // unique id
    private String title;
    private String createdBy; // teacher username
    private List<Question> questions = new ArrayList<>();
//constructor
    public Quiz(String id, String title, String createdBy) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
    }
//getters encapsulation only read
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCreatedBy() { return createdBy; }
    public List<Question> getQuestions() { return questions; }
//composition quiz has a question
    public void addQuestion(Question q) { questions.add(q); }
    public void removeQuestion(int idx) { if (idx >=0 && idx < questions.size()) questions.remove(idx); }
}