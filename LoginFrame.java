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
//inheritance
public class LoginFrame extends JFrame {
    //encapsulation
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnRegister;
    private JPanel p;
//constructor
    public LoginFrame() {
        setTitle("Quiz Application - Login");
        setSize(380, 220);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.gridx = 0; c.gridy = 0; p.add(new JLabel("Username:"), c);
        c.gridx = 1; txtUser = new JTextField(15); p.add(txtUser, c);
        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Password:"), c);
        c.gridx = 1; txtPass = new JPasswordField(15); p.add(txtPass, c);
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        JPanel btnPanel = new JPanel();
        btnLogin = new JButton("Login"); btnRegister = new JButton("Register (Student)");
        btnPanel.add(btnLogin); btnPanel.add(btnRegister);
        p.add(btnPanel, c);

        add(p, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> doRegister());

        // Enter presses login
        txtPass.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }
        User user = DataStore.users.get(u);
        if (user == null || !user.getPassword().equals(p)) {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
            return;
        }
        // open role frame
        switch (user.getRole()) {
            case ADMIN:
                new AdminFrame(user).setVisible(true); break;
            case TEACHER:
                new TeacherFrame(user).setVisible(true); break;
            case STUDENT:
                new StudentFrame(user).setVisible(true); break;
        }
        this.dispose();
    }

    private void doRegister() {
        String u = JOptionPane.showInputDialog(this, "Choose a username:");
        if (u == null || u.trim().isEmpty()) return;
        u = u.trim();
        if (DataStore.users.containsKey(u)) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
            return;
        }
        String pass = JOptionPane.showInputDialog(this, "Choose a password:");
        if (pass == null || pass.trim().isEmpty()) return;
        User newStudent = new User(u, pass, UserRole.STUDENT);
        DataStore.users.put(u, newStudent);
        DataStore.saveUsers();
        JOptionPane.showMessageDialog(this, "Student registered. You can login now.");
    }
}