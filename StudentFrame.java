/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizapp;

/**
 *
 * @author Mega Providers
 */
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.BorderLayout;
//inheritance
public class StudentFrame extends JFrame {
    //encapsulation
    private User student;
    private DefaultListModel<String> quizModel;
    private JList<String> quizList;
    private JPanel mainPanel;
//constructor
    public StudentFrame(User student) {
        this.student = student;
        setTitle("Student - Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.add(mainPanel);
        setSize(700, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        mainPanel = new JPanel(new BorderLayout());
        quizModel = new DefaultListModel<>();
        quizList = new JList<>(quizModel);
        refreshQuizzes();
        mainPanel.add(new JScrollPane(quizList), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton take = new JButton("Take Quiz");
        JButton view = new JButton("My Results");
        btns.add(take); btns.add(view);
        mainPanel.add(btns, BorderLayout.SOUTH);
//polymorphism
        take.addActionListener(e -> doTakeQuiz());
        view.addActionListener(e -> showResults());
    }

    private void refreshQuizzes() {
        quizModel.clear();
        for (Quiz q : DataStore.quizzes.values()) {
            quizModel.addElement(q.getTitle() + " (by " + q.getCreatedBy() + ") - " + q.getQuestions().size() + " Qs");
        }
    }

    private void doTakeQuiz() {
        int idx = quizList.getSelectedIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Select a quiz."); return; }
        java.util.List<String> keys = new ArrayList<>(DataStore.quizzes.keySet());
        String key = keys.get(idx);
        Quiz q = DataStore.quizzes.get(key);
        if (q.getQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "This quiz has no questions yet.");
            return;
        }
        TakeQuizDialog dialog = new TakeQuizDialog(this, q, student);
        dialog.setVisible(true);
        refreshQuizzes();
    }
//for displaying results
    private void showResults() {
        StringBuilder sb = new StringBuilder();
        for (Result r : DataStore.results) {
            if (r.getStudentUsername().equals(student.getUsername())) {
                sb.append(String.format("Quiz: %s | Score: %d/%d | Taken: %s\n", r.getQuizTitle(), r.getScore(), r.getTotal(), r.getTakenAt()));
            }
        }
        if (sb.length() == 0) sb.append("No results yet.");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "My Results", JOptionPane.INFORMATION_MESSAGE);
    }
}