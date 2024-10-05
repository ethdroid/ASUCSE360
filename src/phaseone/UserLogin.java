import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PasswordEvaluator {
    public static String evaluatePassword(String password) {
        //check if password is at least 6 characters
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        return ""; // return nothing if password is valid
    }
}

public class UserLogin {
    //arrays to store username/pass
    static String[] usernames = new String[20];
    static String[] passwords = new String[20];
    static int count = 0;  //track the number of users stored

    public static void main(String[] args) {
        //create window
        JFrame frame = new JFrame("User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField(20);

        JButton submitButton = new JButton("Submit");

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

        frame.add(panel);

        //store username and password in arrays
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String confirmPassword = new String(confirmPassField.getPassword()).trim();

                //check if something is empty
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //if passwords match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match. Please try again.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                    passField.setText("");
                    confirmPassField.setText("");
                    return;
                }

                // eval password
                String resultText = PasswordEvaluator.evaluatePassword(password);

                //check if the password is valid
                if (!resultText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, resultText, "Password Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    //if password is good, store username and password
                    storeUserData(username, password);

                    userField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");
                }
            }
        });

        // display the window
        frame.setVisible(true);
    }

    //store user data in arrays
    private static void storeUserData(String username, String password) {
        //check if space to store more users
        if (count < usernames.length) {
            usernames[count] = username;
            passwords[count] = password;
            count++;
            JOptionPane.showMessageDialog(null, "Account created successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "User limit reached. Cannot store more users.", "Storage Full", JOptionPane.WARNING_MESSAGE);
        }

        // show the contents of the arrays
        displayStoredData();
    }

    //display the stored usernames and passwords
    private static void displayStoredData() {
        System.out.println("Stored Users:");
        for (int i = 0; i < count; i++) {
            System.out.println("Username: " + usernames[i] + ", Password: " + passwords[i]);
        }
    }
}
