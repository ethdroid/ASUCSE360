import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

// PasswordEvaluator class remains unchanged
class PasswordEvaluator {
    public static String evaluatePassword(String password) {
        // Check if password is at least 6 characters
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        return ""; // Return empty string if password is valid
    }
}

public class UserLogin {
    // Arrays to store user information
    static String[] usernames = new String[20];
    static String[] passwords = new String[20];
    static String[] roles = new String[20];
    static String[] emails = new String[20];
    static String[] firstNames = new String[20];
    static String[] middleNames = new String[20];
    static String[] lastNames = new String[20];
    static String[] preferredNames = new String[20];
    static int count = 0;  // Track the number of users stored

    // Variable to track the currently logged-in user
    static int currentUserIndex = -1;

    // CardLayout to switch between panels
    static CardLayout cardLayout = new CardLayout();
    static JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("User Registration and Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create Registration, Login, and Finish Account panels
        JPanel registrationPanel = createRegistrationPanel();
        JPanel loginPanel = createLoginPanel();
        JPanel finishAccountPanel = createFinishAccountPanel();

        // Add panels to the main panel with identifiers
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(finishAccountPanel, "FinishAccount");

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
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

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
                    // If password is good, store username, password, and role
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
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

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

                // Validate credentials and get user index
                int userIndex = validateLogin(usernameInput, passwordInput);
                if (userIndex != -1) {
                    currentUserIndex = userIndex; // Set the current user index
                    String role = roles[userIndex];
                    JOptionPane.showMessageDialog(panel, "Login successful! Welcome, " + usernameInput + ". Your role is: " + role + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Switch to Finish Account panel
                    cardLayout.show(mainPanel, "FinishAccount");
                    // Clear login fields
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

    // Method to create the Finish Account Panel
    private static JPanel createFinishAccountPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Labels and fields
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);

        JLabel middleNameLabel = new JLabel("Middle Name:");
        JTextField middleNameField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(20);

        JLabel preferredNameLabel = new JLabel("Preferred Name:");
        JTextField preferredNameField = new JTextField(20);

        JButton finishButton = new JButton("Finish Account");

        // Arrange components using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(middleNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(middleNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lastNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(preferredNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(preferredNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(finishButton, gbc);

        // Finish button action
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ensure a user is logged in
                if (currentUserIndex == -1) {
                    JOptionPane.showMessageDialog(panel, "No user is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String email = emailField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String middleName = middleNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String preferredName = preferredNameField.getText().trim();

                // Validate required fields
                if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Email, First Name, and Last Name are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                    emailField.setText("");
                    return;
                }

                // Store the additional user information
                emails[currentUserIndex] = email;
                firstNames[currentUserIndex] = firstName;
                middleNames[currentUserIndex] = middleName;
                lastNames[currentUserIndex] = lastName;
                preferredNames[currentUserIndex] = preferredName;

                JOptionPane.showMessageDialog(panel, "Account details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Optionally, navigate to a welcome panel or reset to login
                // For demonstration, we'll reset to Login panel
                cardLayout.show(mainPanel, "Login");

                // Clear Finish Account fields
                emailField.setText("");
                firstNameField.setText("");
                middleNameField.setText("");
                lastNameField.setText("");
                preferredNameField.setText("");

                // Reset currentUserIndex
                currentUserIndex = -1;
            }
        });

        return panel;
    }

    // Method to store user data in arrays and assign roles
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
            // Assign role: first user is admin, others are user
            roles[count] = (count == 0) ? "admin" : "user";
            count++;
            // Uncomment the following line to display stored data in console (for testing purposes)
            // displayStoredData();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "User limit reached. Cannot store more users.", "Storage Full", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    // Method to validate login credentials and return user index
    private static int validateLogin(String username, String password) {
        for (int i = 0; i < count; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return i; // Return the index of the user
            }
        }
        return -1; // User not found
    }

    // Method to validate email format using regex
    private static boolean isValidEmail(String email) {
        // Simple regex for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    // Optional: Method to display stored user data (for testing purposes)
    private static void displayStoredData() {
        System.out.println("Stored Users:");
        for (int i = 0; i < count; i++) {
            System.out.println("Username: " + usernames[i] + ", Password: " + passwords[i] + ", Role: " + roles[i]
                    + ", Email: " + emails[i] + ", First Name: " + firstNames[i]
                    + ", Middle Name: " + middleNames[i] + ", Last Name: " + lastNames[i]
                    + ", Preferred Name: " + preferredNames[i]);
        }
    }
}
