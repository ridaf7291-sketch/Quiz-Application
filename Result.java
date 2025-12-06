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
import java.util.Date;

public class Result implements Serializable {
    //encapsulation
    private static final long serialVersionUID = 1L;
    private String quizId;
    private String quizTitle;
    private String studentUsername;
    private int score;
    private int total;
    private Date takenAt;
//constructor
    public Result(String quizId, String quizTitle, String studentUsername, int score, int total) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.studentUsername = studentUsername;
        this.score = score;
        this.total = total;
        this.takenAt = new Date();
    }
//getter methods
    public String getQuizId() { return quizId; }
    public String getQuizTitle() { return quizTitle; }
    public String getStudentUsername() { return studentUsername; }
    public int getScore() { return score; }
    public int getTotal() { return total; }
    public Date getTakenAt() { return takenAt; }
}