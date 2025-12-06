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

public class AdminFrame extends JFrame {
    private User admin;
    private DefaultListModel<String> teacherModel;
    private JList<String> teacherList;
//constructor
    public AdminFrame(User admin) {
        this.admin = admin;
        setTitle("Admin - Quiz App");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createTitledBorder("Teachers"));
        teacherModel = new DefaultListModel<>();
        teacherList = new JList<>(teacherModel);
        refreshTeachers();
        left.add(new JScrollPane(teacherList), BorderLayout.CENTER);
        JPanel lbtn = new JPanel();
        JButton add = new JButton("Add Teacher");
        JButton del = new JButton("Delete Teacher");
        lbtn.add(add); lbtn.add(del);
        left.add(lbtn, BorderLayout.SOUTH);

        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Quizzes"));
        DefaultListModel<String> quizModel = new DefaultListModel<>();
        JList<String> quizList = new JList<>(quizModel);
        refreshQuizzes(quizModel);
        right.add(new JScrollPane(quizList), BorderLayout.CENTER);
        JButton viewResults = new JButton("View Results");
        right.add(viewResults, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.CENTER);

        add.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Teacher username:");
            if (username == null || username.trim().isEmpty()) return;
            if (DataStore.users.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
                return;
            }
            String pass = JOptionPane.showInputDialog(this, "Password for teacher:");
            if (pass == null || pass.trim().isEmpty()) return;
            User t = new User(username.trim(), pass.trim(), UserRole.TEACHER);
            DataStore.users.put(t.getUsername(), t);
            DataStore.saveUsers();
            refreshTeachers();
            JOptionPane.showMessageDialog(this, "Teacher added.");
        });

        del.addActionListener(e -> {
            String sel = teacherList.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Select teacher."); return; }
            if (sel.equals("admin") || sel.equals("teacher")) {
                JOptionPane.showMessageDialog(this, "Cannot delete default accounts.");
                return;
            }
            int ok = JOptionPane.showConfirmDialog(this, "Delete teacher " + sel + " ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) return;
            DataStore.users.remove(sel);
            DataStore.saveUsers();
            refreshTeachers();
        });

        viewResults.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Result r : DataStore.results) {
                sb.append(String.format("Quiz: %s | Student: %s | Score: %d/%d | At: %s\n",
                        r.getQuizTitle(), r.getStudentUsername(), r.getScore(), r.getTotal(), r.getTakenAt()));
            }
            if (sb.length() == 0) sb.append("No results yet.");
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Results", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void refreshTeachers() {
        teacherModel.clear();
        for (User u : DataStore.users.values()) {
            if (u.getRole() == UserRole.TEACHER) teacherModel.addElement(u.getUsername());
        }
    }

    private void refreshQuizzes(DefaultListModel<String> model) {
        model.clear();
        for (Quiz q : DataStore.quizzes.values()) model.addElement(q.getTitle() + " (by " + q.getCreatedBy() + ")");
    }
}