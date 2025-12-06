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

public class User implements Serializable {
    //encapsulation
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private UserRole role;
    private String displayName;

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.displayName = username;
    }
//getter methods
    public String getUsername() { return username; }
    public String getPassword() { return password; } // not secure; ok for demo
    public UserRole getRole() { return role; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String name) { this.displayName = name; }
}
