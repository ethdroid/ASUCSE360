package nogui;

import java.util.Scanner;

public class makeuserpass {

    public void createUser(String role) {

        Scanner scanner = new Scanner(System.in);

        String username;
        String password;
        String password1;
        boolean passwordsMatch = false;

        System.out.print("Enter username: ");
        username = scanner.nextLine();

        // Password entry and validation loop
        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            System.out.print("Enter password again: ");
            password1 = scanner.nextLine();

            if (!password.equals(password1)) {
                System.out.println("Passwords do not match, please try again.");
            } else {
                passwordsMatch = true;
            }

        } while (!passwordsMatch);

        // Check password strength or other validation
        String resultText = PasswordEvaluator.evaluatePassword(password);

        // If there's an error with the password, prompt to retry
        while (!resultText.isEmpty()) {
            System.out.println(resultText + " Password Error. Do you want to try again (y/n)?");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("y")) {
                // Re-enter passwords and validate them again
                passwordsMatch = false;
                do {
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();

                    System.out.print("Enter password again: ");
                    password1 = scanner.nextLine();

                    if (!password.equals(password1)) {
                        System.out.println("Passwords do not match, please try again.");
                    } else {
                        passwordsMatch = true;
                    }

                } while (!passwordsMatch);

                // Re-check password strength
                resultText = PasswordEvaluator.evaluatePassword(password);

            } else if (ans.equalsIgnoreCase("n")) {
                System.out.println("Account creation cancelled.");
                return; // Exit the function if the user doesn't want to try again
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }

        // Create the user if password is valid
        UserClass user = new UserClass(username, password, role);
        System.out.println("Account created successfully with role: " + role);

        scanner.close(); // Always close the scanner
    }

}
