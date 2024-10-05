import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserLogin {
    // JDBC URL for SQLite database
    static final String JDBC_URL = "jdbc:sqlite:users.db";

    public static void main(String[] args) {
        // Create and set up the window
        JFrame frame = new JFrame("User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        
        // Create labels and text fields
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        
        // Create submit button
        JButton submitButton = new JButton("Submit");

        // Set up layout
        frame.setLayout(new GridLayout(3, 2));
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(submitButton);
        
        // Button action to store username and password in the database
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                storeUserData(username, password);
            }
        });
        
        // Display the window
        frame.setVisible(true);
        
        // Initialize the database
        createDatabaseTable();
    }

    // Create users table if it doesn't exist
    private static void createDatabaseTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username TEXT NOT NULL, " +
                                "password TEXT NOT NULL);";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            conn.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Store user data in the database
    private static void storeUserData(String username, String password) {
        String insertSQL = "INSERT INTO users(username, password) VALUES (?, ?);";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User data saved successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }
}

