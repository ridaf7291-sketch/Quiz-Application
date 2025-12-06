/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizapp;

/**
 *
 * @author Mega Providers
 */
import java.io.*;        
import java.util.*;               

public class DataStore {
    private static final String USERS_FILE = "users.dat";
    private static final String QUIZZES_FILE = "quizzes.dat";
    private static final String RESULTS_FILE = "results.dat";

    // in-memory structures
    public static Map<String, User> users = new HashMap<>();
    public static Map<String, Quiz> quizzes = new LinkedHashMap<>();
    public static java.util.List<Result> results = new ArrayList<>();

    public static synchronized void initialize() {
        loadUsers();
        loadQuizzes();
        loadResults();
        // create default admin and teacher if not exists
        if (!users.containsKey("admin")) {
            User admin = new User("admin", "admin", UserRole.ADMIN);
            users.put(admin.getUsername(), admin);
            saveUsers();
        }
        if (!users.containsKey("teacher")) {
            User t = new User("teacher", "teacher", UserRole.TEACHER);
            users.put(t.getUsername(), t);
            saveUsers();
        }
    }

    public static synchronized void saveUsers() {
        saveObject(USERS_FILE, users);
    }

    public static synchronized void saveQuizzes() {
        saveObject(QUIZZES_FILE, quizzes);
    }

    public static synchronized void saveResults() {
        saveObject(RESULTS_FILE, results);
    }

    @SuppressWarnings("unchecked")
    private static void loadUsers() {
        Object obj = readObject(USERS_FILE);
        if (obj instanceof Map) users = (Map<String, User>) obj;
    }

    @SuppressWarnings("unchecked")
    private static void loadQuizzes() {
        Object obj = readObject(QUIZZES_FILE);
        if (obj instanceof Map) quizzes = (Map<String, Quiz>) obj;
    }

    @SuppressWarnings("unchecked")
    private static void loadResults() {
        Object obj = readObject(RESULTS_FILE);
        if (obj instanceof List) results = (List<Result>) obj;
    }

    private static void saveObject(String filename, Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Object readObject(String filename) {
        File f = new File(filename);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}