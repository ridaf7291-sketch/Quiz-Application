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
import java.util.List;
//indirect file handling
public class Question implements Serializable {
    //encapsulation
    private static final long serialVersionUID = 1L;
    private String text;
    private List<String> options;
    private int correctIndex; // index in options (0-based)
//constructor
    public Question(String text, List<String> options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }
//getters encapsulation
    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
}
