package nogui;



import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AdminOperations {
    private static Scanner scanner = new Scanner(System.in);

    public static void adminMenu(UserClass admin, ArrayList<UserClass> userList) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Invite New User");
            System.out.println("2. Reset User Password");
            System.out.println("3. Delete User");
            System.out.println("4. List All Users");
            System.out.println("5. Add or Remove Roles");
            System.out.println("6. Log Out");
            System.out.print("Choose an option: ");
            
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    inviteNewUser(userList);
                    break;
                case 2:
                    resetUserPassword(userList);
                    break;
                case 3:
                    deleteUser(userList);
                    break;
                case 4:
                    listAllUsers(userList);
                    break;
                case 5:
                    modifyUserRoles(userList);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return; // Exit the admin menu
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // 1. Invite new user
    public static void inviteNewUser(ArrayList<UserClass> userList) {
        System.out.print("Enter role for the new user (Admin/Student/Instructor): ");
        String role = scanner.nextLine();

        String inviteCode = generateInviteCode();
        System.out.println("Invite code for new user: " + inviteCode);

        // In a real system, this invite code would be stored somewhere secure.
        // For this example, we assume that the code will be manually given to the new user.

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String password2 = scanner.nextLine();

        if (!password.equals(password2)) {
            System.out.println("Passwords do not match.");
            return;
        }

        UserClass newUser = new UserClass(username, password, role);
        userList.add(newUser);
        System.out.println("User created successfully with role: " + role);
    }

    // 2. Reset user password
    public static void resetUserPassword(ArrayList<UserClass> userList) {
        System.out.print("Enter username of the user to reset password: ");
        String username = scanner.nextLine();

        for (UserClass user : userList) {
            if (user.getUsername().equals(username)) {
                String oneTimePassword = generateInviteCode();
                System.out.println("One-time password for " + username + ": " + oneTimePassword);
                user.setPassword(oneTimePassword); // In a real system, expiration and secure handling would be implemented
                System.out.println("Password reset. The user must log in with this one-time password.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    // 3. Delete user
    public static void deleteUser(ArrayList<UserClass> userList) {
        System.out.print("Enter username of the user to delete: ");
        String username = scanner.nextLine();

        for (UserClass user : userList) {
            if (user.getUsername().equals(username)) {
                System.out.print("Are you sure you want to delete this user? (yes/no): ");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    userList.remove(user);
                    System.out.println("User deleted.");
                    return;
                } else {
                    System.out.println("Delete action cancelled.");
                    return;
                }
            }
        }
        System.out.println("User not found.");
    }

    // 4. List all users
    public static void listAllUsers(ArrayList<UserClass> userList) {
        System.out.println("\nListing all users:");
        for (UserClass user : userList) {
            System.out.println(user);
        }
    }

    // 5. Modify user roles (add or remove roles)
    public static void modifyUserRoles(ArrayList<UserClass> userList) {
        System.out.print("Enter username of the user to modify roles: ");
        String username = scanner.nextLine();

        for (UserClass user : userList) {
            if (user.getUsername().equals(username)) {
                System.out.println("Current roles: " + user.getRoles());
                System.out.print("Do you want to (a)dd or (r)emove a role? ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("a")) {
                    System.out.print("Enter new role to add: ");
                    String newRole = scanner.nextLine();
                    user.getRoles().add(newRole);
                    System.out.println("Role added.");
                } else if (choice.equalsIgnoreCase("r")) {
                    System.out.print("Enter role to remove: ");
                    String removeRole = scanner.nextLine();
                    if (user.getRoles().contains(removeRole)) {
                        user.getRoles().remove(removeRole);
                        System.out.println("Role removed.");
                    } else {
                        System.out.println("Role not found.");
                    }
                }
                return;
            }
        }
        System.out.println("User not found.");
    }

    // Helper to generate a random invite code
    private static String generateInviteCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate a 6-digit random number
        return String.valueOf(code);
    }
}

