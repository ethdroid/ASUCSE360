import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PasswordEvaluator {
    public static String evaluatePassword(String password) {
        // Check if password is at least 6 characters
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        // Additional password checks can be added here (e.g., special characters, numbers)
        return ""; // Return empty string if password is valid
    }
}

public class UserLogin {
    // Arrays to store usernames and passwords
    static String[] usernames = new String[20];
    static String[] passwords = new String[20];
    static int count = 0;  // Track the number of users stored

    // CardLayout to switch between panels
    static CardLayout cardLayout = new CardLayout();
    static JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("User Registration and Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create Registration and Login panels
        JPanel registrationPanel = createRegistrationPanel();
        JPanel loginPanel = createLoginPanel();

        // Add panels to the main panel with identifiers
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(loginPanel, "Login");

        // Show Registration panel initially
        cardLayout.show(mainPanel, "Registration");

        // Add main panel to frame
        frame.add(mainPanel);

        // Display the window
        frame.setVisible(true);
    }

    // Method to create the Registration Panel
    private static JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Labels and fields
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField(20);

        JButton submitButton = new JButton("Register");

        // Arrange components using GridBagLayout
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

        // Register button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String confirmPassword = new String(confirmPassField.getPassword()).trim();

                // Check if any field is empty
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(panel, "Passwords do not match. Please try again.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                    passField.setText("");
                    confirmPassField.setText("");
                    return;
                }

                // Evaluate the password
                String resultText = PasswordEvaluator.evaluatePassword(password);

                // Check if the password is valid
                if (!resultText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, resultText, "Password Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // If password is good, store username and password
                    if (storeUserData(username, password)) {
                        JOptionPane.showMessageDialog(panel, "Account created successfully! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Switch to Login panel
                        cardLayout.show(mainPanel, "Login");
                        // Clear registration fields
                        userField.setText("");
                        passField.setText("");
                        confirmPassField.setText("");
                    }
                }
            }
        });

        return panel;
    }

    // Method to create the Login Panel
    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Labels and fields
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        // Arrange components using GridBagLayout
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

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = userField.getText().trim();
                String passwordInput = new String(passField.getPassword()).trim();

                // Check if any field is empty
                if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Both fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate credentials
                if (validateLogin(usernameInput, passwordInput)) {
                    JOptionPane.showMessageDialog(panel, "Login successful! Welcome, " + usernameInput + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Optionally, proceed to the next part of the application
                    // For demonstration, we can clear the fields
                    userField.setText("");
                    passField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    passField.setText("");
                }
            }
        });

        return panel;
    }

    // Method to store user data in arrays
    private static boolean storeUserData(String username, String password) {
        // Check if space to store more users
        if (count < usernames.length) {
            // Check for duplicate usernames
            for (int i = 0; i < count; i++) {
                if (usernames[i].equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose another.", "Duplicate Username", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            usernames[count] = username;
            passwords[count] = password;
            count++;
            // Uncomment the following line to display stored data in console (for testing purposes)
            // displayStoredData();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "User limit reached. Cannot store more users.", "Storage Full", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    // Method to validate login credentials
    private static boolean validateLogin(String username, String password) {
        for (int i = 0; i < count; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Optional: Method to display stored usernames and passwords (for testing purposes)
    private static void displayStoredData() {
        System.out.println("Stored Users:");
        for (int i = 0; i < count; i++) {
            System.out.println("Username: " + usernames[i] + ", Password: " + passwords[i]);
        }
    }
}
