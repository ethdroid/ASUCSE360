package phaseone;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class UserLogin {
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Invitation> invitations = new ArrayList<>();
    static int currentUserIndex = -1;
    static CardLayout cardLayout = new CardLayout();
    static JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        // Main Frame setup
        JFrame frame = new JFrame("User Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        // Panels
        JPanel registrationPanel = createRegistrationPanel();
        JPanel loginPanel = createLoginPanel();
        JPanel finishAccountPanel = createFinishAccountPanel();
        JPanel adminPanel = createAdminPanel();

        // Add panels to main panel
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(finishAccountPanel, "FinishAccount");
        mainPanel.add(adminPanel, "Admin");
        mainPanel.add(createFinishAccountPanel(), "FinishAccount");
        mainPanel.add(createAdminPanel(), "Admin");



        // Show Registration panel initially
        cardLayout.show(mainPanel, "Registration");

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // 1. Create Registration Panel for Admin
    private static JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField(20);
        JButton submitButton = new JButton("Register");

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

        submitButton.addActionListener(e -> {


            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            String confirmPassword = new String(confirmPassField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(panel, "Passwords do not match.", "Password Error", JOptionPane.ERROR_MESSAGE);
                return;
            }



            if (users.isEmpty()) {
                User admin = new User(username, password, "Admin");
                users.add(admin);
                JOptionPane.showMessageDialog(panel, "Admin account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(panel, "An admin already exists. Please log in.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }


    // 2. Admin Panel for Admin Actions (Invite Users, Reset Passwords, Delete Users, List Users)
    private static JPanel createAdminPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1));

        JButton inviteButton = new JButton("Invite User");
        JButton resetPasswordButton = new JButton("Reset Password");
        JButton deleteUserButton = new JButton("Delete User");
        JButton listUsersButton = new JButton("List Users");
        JButton logoutButton = new JButton("Logout");

        panel.add(inviteButton);
        panel.add(resetPasswordButton);
        panel.add(deleteUserButton);
        panel.add(listUsersButton);
        panel.add(logoutButton);

        // Invite a user
        inviteButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter username to invite:");
            if (username == null || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Invalid username", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String role = JOptionPane.showInputDialog("Assign role (Student, Instructor):");
            if (role == null || (!role.equalsIgnoreCase("Student") && !role.equalsIgnoreCase("Instructor"))) {
                JOptionPane.showMessageDialog(panel, "Invalid role", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String inviteCode = generateInviteCode();
            invitations.add(new Invitation(inviteCode, role));  // Store the invite code
            JOptionPane.showMessageDialog(panel, "Invite code: " + inviteCode, "Invite Code", JOptionPane.INFORMATION_MESSAGE);
        });



        // Reset user password
        resetPasswordButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter username to reset password:");
            User user = findUserByUsername(username);
            if (user == null) {
                JOptionPane.showMessageDialog(panel, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String oneTimePassword = generateOneTimePassword();
            user.setOneTimePassword(oneTimePassword);
            JOptionPane.showMessageDialog(panel, "One-time password: " + oneTimePassword, "Password Reset", JOptionPane.INFORMATION_MESSAGE);
        });

        // Delete user
        deleteUserButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter username to delete:");
            User user = findUserByUsername(username);
            if (user != null) {
                int response = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    users.remove(user);
                    JOptionPane.showMessageDialog(panel, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        // List all users
        listUsersButton.addActionListener(e -> {
            StringBuilder userList = new StringBuilder("Users:\n");
            for (User user : users) {
                userList.append(user.getUsername())
                        .append(" - Roles: ").append(user.getRoles())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(panel, userList.toString(), "User List", JOptionPane.INFORMATION_MESSAGE);
        });

        // Logout
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        return panel;
    }

    private static String generateInviteCode() {
        return Integer.toHexString(new Random().nextInt(0xFFFFFF)).toUpperCase();
    }

    private static String generateOneTimePassword() {
        return Integer.toHexString(new Random().nextInt(0xFFFFFF)).toUpperCase();
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }


 
    // 3. Create Login Panel for users
private static JPanel createLoginPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel userLabel = new JLabel("Username:");
    JTextField userField = new JTextField(20);
    JLabel passLabel = new JLabel("Password:");
    JPasswordField passField = new JPasswordField(20);
    JLabel inviteLabel = new JLabel("Invitation Code:");
    JTextField inviteField = new JTextField(20);
    JButton loginButton = new JButton("Login");
    JButton createAccountWithInviteButton = new JButton("Use Invitation Code");



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
    panel.add(inviteLabel, gbc);
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(inviteField, gbc);

    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(loginButton, gbc);
    gbc.gridy = 4;
    panel.add(createAccountWithInviteButton, gbc);

    
    loginButton.addActionListener(e -> {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        int userIndex = validateLogin(username, password);

        if (userIndex != -1) {
            currentUserIndex = userIndex;
            User currentUser = users.get(currentUserIndex);

            if (!currentUser.isAccountSetupComplete()) {
                // Redirect to Finish Account Setup Panel
                cardLayout.show(mainPanel, "FinishAccount");
            } else if (currentUser.getRoles().contains("Admin")) {
                // If the user is an Admin, redirect to Admin Panel
                cardLayout.show(mainPanel, "Admin");
            } else if (currentUser.getRoles().size() > 1) {
                // If the user has multiple roles, show role selection (not implemented in this code)
                cardLayout.show(mainPanel, "RoleSelection");
            } else {
                // User has completed setup and has only one role, show success message
                JOptionPane.showMessageDialog(panel, "Logged in as " + currentUser.getRoles().get(0) + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Invalid login credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    });



    // Create Account with Invitation Code Button Action
    createAccountWithInviteButton.addActionListener(e -> {
        String inviteCode = inviteField.getText().trim();
        if (inviteCode.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter an invitation code.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Invitation invitation = validateInvitationCode(inviteCode);
        if (invitation != null) {
            String username = JOptionPane.showInputDialog("Enter your new username:");
            String password = JOptionPane.showInputDialog("Enter your new password:");
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                User newUser = new User(username, password, invitation.getRoles().get(0)); // Only using the first role here for simplicity
                for (String role : invitation.getRoles()) {
                    newUser.addRole(role); // Add all roles associated with the invitation
                }
                users.add(newUser);
                invitations.remove(invitation);  // Remove the used invitation code
                JOptionPane.showMessageDialog(panel, "Account created successfully! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid username or password.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Invalid or expired invitation code.", "Invalid Code", JOptionPane.ERROR_MESSAGE);
        }
    });

    return panel;
}



    // 4. Finish Account Setup Panel
private static JPanel createFinishAccountPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel emailLabel = new JLabel("Email:");
    JTextField emailField = new JTextField(20);
    
    JLabel firstNameLabel = new JLabel("First Name:");
    JTextField firstNameField = new JTextField(20);

    JLabel middleNameLabel = new JLabel("Middle Name:");
    JTextField middleNameField = new JTextField(20);

    JLabel lastNameLabel = new JLabel("Last Name:");
    JTextField lastNameField = new JTextField(20);

    JLabel preferredFirstNameLabel = new JLabel("Preferred First Name (Optional):");
    JTextField preferredFirstNameField = new JTextField(20);

    JButton finishButton = new JButton("Finish Setup");

    // Arrange components in GridBagLayout
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
    panel.add(preferredFirstNameLabel, gbc);
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(preferredFirstNameField, gbc);

    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(finishButton, gbc);

    // Action listener for finishing account setup
    finishButton.addActionListener(e -> {
        String email = emailField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String preferredFirstName = preferredFirstNameField.getText().trim();

        if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Email, First Name, and Last Name are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the current user's details
        User currentUser = users.get(currentUserIndex);
        currentUser.setEmail(email);
        currentUser.setFirstName(firstName);
        currentUser.setMiddleName(middleName);
        currentUser.setLastName(lastName);
        currentUser.setPreferredFirstName(preferredFirstName.isEmpty() ? firstName : preferredFirstName);
        currentUser.completeAccountSetup();

        JOptionPane.showMessageDialog(panel, "Account setup completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(mainPanel, "Login");
    });

    return panel;
}

    private static int validateLogin(String username, String password) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return i;  // Return the index of the user
            }
        }
        return -1;  // Login failed
    }

    private static Invitation validateInvitationCode(String code) {
        for (Invitation invitation : invitations) {
            if (invitation.getCode().equalsIgnoreCase(code)) {
                return invitation;
            }
        }
        return null; // Code not found  expired
    }

   

}
