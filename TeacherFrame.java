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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TeacherFrame extends JFrame {
    //encapsulation
    private User teacher;
    private DefaultListModel<String> quizModel;
    private JList<String> quizList;
    private JPanel main;
//constructor
    public TeacherFrame(User teacher) {
        this.teacher = teacher;
        setTitle("Teacher - Quiz App");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.add(main);
        setVisible(true);
    }

    private void init() {
        main = new JPanel(new BorderLayout());
        quizModel = new DefaultListModel<>();
        quizList = new JList<>(quizModel);
        refreshList();
        main.add(new JScrollPane(quizList), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton create = new JButton("Create Quiz");
        JButton edit = new JButton("Edit Quiz (Add Question)");
        JButton delete = new JButton("Delete Quiz");
        btns.add(create); btns.add(edit); btns.add(delete);

        main.add(btns, BorderLayout.SOUTH);
//crud operations
        create.addActionListener(e -> doCreateQuiz());
        edit.addActionListener(e -> doEditQuiz());
 //delete quiz       
        delete.addActionListener(e -> {
            int idx = quizList.getSelectedIndex();
            if (idx < 0) { JOptionPane.showMessageDialog(this, "Select a quiz."); return; }
            String key = (String) DataStore.quizzes.keySet().toArray()[idx];
            Quiz q = (Quiz) DataStore.quizzes.values().toArray()[idx];
            // find by createdBy and title to be safe
            List<String> keys = new ArrayList<>(DataStore.quizzes.keySet());
            String chosenKey = keys.get(idx);
            int ok = JOptionPane.showConfirmDialog(this, "Delete quiz: " + DataStore.quizzes.get(chosenKey).getTitle() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) return;
            DataStore.quizzes.remove(chosenKey);
            DataStore.saveQuizzes();
            refreshList();
        });
    }
//quiz created
    private void doCreateQuiz() {
        String title = JOptionPane.showInputDialog(this, "Quiz title:");
        if (title == null || title.trim().isEmpty()) return;
        String id = "quiz-" + UUID.randomUUID().toString();
        Quiz q = new Quiz(id, title.trim(), teacher.getUsername());
        DataStore.quizzes.put(id, q);
        DataStore.saveQuizzes();
        refreshList();
        JOptionPane.showMessageDialog(this, "Quiz created. Now add questions using Edit.");
    }
//quiz edited
    private void doEditQuiz() {
        int idx = quizList.getSelectedIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Select a quiz."); return; }
        java.util.List<String> keys = new ArrayList<>(DataStore.quizzes.keySet());
        String key = keys.get(idx);
        Quiz q = DataStore.quizzes.get(key);
        // add question dialog
        JTextArea taQ = new JTextArea(3,30);
        JTextField opt1 = new JTextField(25);
        JTextField opt2 = new JTextField(25);
        JTextField opt3 = new JTextField(25);
        JTextField opt4 = new JTextField(25);
        String[] choices = {"1","2","3","4"};
        JComboBox<String> cb = new JComboBox<>(choices);
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(4,4,4,4); c.gridx=0;c.gridy=0;
        p.add(new JLabel("Question:"), c); c.gridx=1; p.add(new JScrollPane(taQ), c);
        c.gridx=0; c.gridy++; p.add(new JLabel("Option 1:"), c); c.gridx=1; p.add(opt1, c);
        c.gridx=0; c.gridy++; p.add(new JLabel("Option 2:"), c); c.gridx=1; p.add(opt2, c);
        c.gridx=0; c.gridy++; p.add(new JLabel("Option 3:"), c); c.gridx=1; p.add(opt3, c);
        c.gridx=0; c.gridy++; p.add(new JLabel("Option 4:"), c); c.gridx=1; p.add(opt4, c);
        c.gridx=0; c.gridy++; p.add(new JLabel("Correct option (1-4):"), c); c.gridx=1; p.add(cb, c);

        int res = JOptionPane.showConfirmDialog(this, p, "Add Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;
        String qtext = taQ.getText().trim();
        List<String> opts = new ArrayList<>();
        opts.add(opt1.getText().trim()); opts.add(opt2.getText().trim()); opts.add(opt3.getText().trim()); opts.add(opt4.getText().trim());
        int corr = cb.getSelectedIndex();
        if (qtext.isEmpty() || opts.stream().anyMatch(String::isEmpty)) {
            JOptionPane.showMessageDialog(this, "Fill all fields.");
            return;
        }
        Question qq = new Question(qtext, opts, corr);
        q.addQuestion(qq);
        DataStore.saveQuizzes();
        JOptionPane.showMessageDialog(this, "Question added.");
    }

    private void refreshList() {
        quizModel.clear();
        for (Quiz q : DataStore.quizzes.values()) {
            if (q.getCreatedBy().equals(teacher.getUsername())) {
                quizModel.addElement(q.getTitle() + " | Questions: " + q.getQuestions().size());
            }
        }
    }
}
