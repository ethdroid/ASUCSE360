import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Assuming PasswordEvaluator is defined elsewhere
class PasswordEvaluator {
    // Placeholder for password evaluation logic
    public static String evaluatePassword(String password) {
        // Example: Check if password length is at least 6 characters
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        // Add more password checks as needed
        return ""; // Return empty string if password is valid
    }
}

public class UserLogin {
    // Define the arrays to store up to 10 usernames and passwords
    static String[] usernames = new String[10];
    static String[] passwords = new String[10];
    static int count = 0;  // Counter to track the number of users stored

    public static void main(String[] args) {
        // Create and set up the window (JFrame)
        JFrame frame = new JFrame("User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel with GridBagLayout for better component arrangement
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        // Labels and fields
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField(20);

        JButton submitButton = new JButton("Submit");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(confirmPassLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(confirmPassField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        // Add the panel to the frame
        frame.add(panel);

        // Button action to store username and password in arrays
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String confirmPassword = new String(confirmPassField.getPassword()).trim();

                // Check if any field is empty
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match. Please try again.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                    passField.setText("");
                    confirmPassField.setText("");
                    return;
                }

                // Evaluate the password
                String resultText = PasswordEvaluator.evaluatePassword(password);

                // Check if the password is valid (no error messages returned)
                if (!resultText.isEmpty()) {
                    // If there's an error, show it in a dialog box
                    JOptionPane.showMessageDialog(frame, resultText, "Password Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // If password is valid, store the username and password
                    storeUserData(username, password);
                    
                    // Clear input fields after successful registration
                    userField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");
                }
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
            JOptionPane.showMessageDialog(null, "Account created successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "User limit reached. Cannot store more users.", "Storage Full", JOptionPane.WARNING_MESSAGE);
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
