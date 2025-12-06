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
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TakeQuizDialog extends JDialog {
    private Quiz quiz;
    private User student;
    private int current = 0;
    private int[] answers; // store index chosen or -1
    private JLabel lblQuestion;
    private JRadioButton[] opts;
    private JPanel center;
    private ButtonGroup optionGroup;
    private JButton btnNext, btnPrev, btnSubmit;

    public TakeQuizDialog(Frame owner, Quiz quiz, User student) {
        super(owner, "Taking: " + quiz.getTitle(), true);
        this.quiz = quiz;
        this.student = student;
        this.answers = new int[quiz.getQuestions().size()];
        for (int i = 0; i < answers.length; i++) answers[i] = -1;
        init();
        setSize(640, 360);
        setLocationRelativeTo(owner);
    }

    private void init() {
        JPanel top = new JPanel(new BorderLayout());
        lblQuestion = new JLabel("", SwingConstants.LEFT);
        lblQuestion.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        top.add(lblQuestion, BorderLayout.NORTH);
        center = new JPanel(new GridLayout(0, 1));
        opts = new JRadioButton[4];
     for(int i = 0; i < 4; i++){
         opts[i] = new JRadioButton();
     }
        for(int i = 0; i < 4; i++){
            final int idx = i;
        opts[i].addActionListener(e-> {
            answers[current] = idx + 1;
                });
        }
        JPanel bottom = new JPanel();
        btnPrev = new JButton("Previous");
        btnNext = new JButton("Next");
        btnSubmit = new JButton("Submit");
        bottom.add(btnPrev); 
        bottom.add(btnNext);
        bottom.add(btnSubmit);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnPrev.addActionListener(e -> {
            if (current > 0) { current--; 
            loadQuestion();
            }
        });
        btnNext.addActionListener(e -> {
            if (current < quiz.getQuestions().size()- 1) { 
                current++; 
                loadQuestion(); 
            }
        });
        btnSubmit.addActionListener(e -> doSubmit());
        loadQuestion();
    }

    private void loadQuestion() {
        center.removeAll();
        optionGroup = new ButtonGroup();
        center.revalidate();
        for (JRadioButton opt : opts){
            opt.setSelected(false);
        }
        Question q = quiz.getQuestions().get(current);
        lblQuestion.setText(String.format("Q%d: %s", current+1, q.getText()));
        List<String> ops = q.getOptions();
        for (int i = 0; i < 4; i++) {
            opts[i].setText((i+1) + ". " + ops.get(i));
            optionGroup.add(opts[i]);
            center.add(opts[i]);
            opts[i].setSelected(answers[current] == (i + 1));
        }
        center.revalidate();
        center.repaint();
        this.repaint();
    }
    private void doSubmit() {
        int score = 0;
        int total = quiz.getQuestions().size();
        for (int i = 0; i < total; i++) {
            int ans = answers[i];
            int corr = quiz.getQuestions().get(i).getCorrectIndex();
            if (ans != -1 && (ans - 1) == corr){
                    score++;
        }
        }
        Result r = new Result(quiz.getId(), quiz.getTitle(), student.getUsername(), score, total);
        DataStore.results.add(r);
        DataStore.saveResults();
        JOptionPane.showMessageDialog(this, String.format("You scored %d out of %d.", score, total));
        this.dispose();
    }
}
