import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogin {
    static CardLayout cardLayout = new CardLayout();
    static JPanel mainPanel = new JPanel(cardLayout);
    static User currentUser = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("User System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Add panels to the main panel
        mainPanel.add(createRegistrationPanel(), "Registration");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createHomePanel(), "Home");

        frame.add(mainPanel);
        frame.setVisible(true);
        cardLayout.show(mainPanel, "Registration");
    }

    // Registration panel
    private static JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(confirmPassLabel, gbc);
        gbc.gridx = 1;
        panel.add(confirmPassField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                String confirmPassword = new String(confirmPassField.getPassword());

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(panel, "Passwords do not match.");
                    return;
                }

                // Validate password
                String passwordError = PasswordEvaluator.evaluatePassword(password);
                if (!passwordError.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, passwordError, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create user
                User newUser = new User(username, password, "user");
                if (UserStorage.addUser(newUser)) {
                    JOptionPane.showMessageDialog(panel, "User registered successfully!");
                    cardLayout.show(mainPanel, "Login");
                } else {
                    JOptionPane.showMessageDialog(panel, "Username already exists.");
                }
            }
        });

        return panel;
    }

    // Login panel
    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // Validate login
                User user = UserStorage.validateLogin(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(panel, "Login successful!");
