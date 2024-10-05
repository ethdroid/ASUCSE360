import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogin {
    // Define the arrays to store up to 10 usernames and passwords
    static String[] usernames = new String[10];
    static String[] passwords = new String[10];
    static int count = 0;  // Counter to track the number of users stored
    
    public static void main(String[] args) {
        // Create and set up the window (JFrame)
        JFrame frame = new JFrame("User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        
        // Create labels and text fields for username and password
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        
        // Create submit button
        JButton submitButton = new JButton("Submit");

        // Set up layout using GridLayout
        frame.setLayout(new GridLayout(3, 2));
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(submitButton);
        
        // Button action to store username and password in arrays
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
    }

    // Method to store user data (username and password) in arrays
    private static void storeUserData(String username, String password) {
        // Check if there is space to store more users
        if (count < usernames.length) {
            usernames[count] = username;
            passwords[count] = password;
            count++;
            JOptionPane.showMessageDialog(null, "User data stored in array successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Array is full, cannot store more users.");
        }

        // Display the contents of the arrays (for testing purposes)
        displayStoredData();
    }

    // Method to display the stored usernames and passwords
    private static void displayStoredData() {
        System.out.println("Stored Users:");
        for (int i = 0; i < count; i++) {
            System.out.println("Username: " + usernames[i] + ", Password: " + passwords[i]);
        }
    }
}
