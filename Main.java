/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizapp;

/**
 *
 * @author Mega Providers
 */
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Load data (creates defaults if missing)
        DataStore.initialize();
        SwingUtilities.invokeLater(() -> {
     LoginFrame login = new LoginFrame();
     login.pack();
    login.setLocationRelativeTo(null);
    login.setVisible(true);
        });
    }
}
