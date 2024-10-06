package nogui;


import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private static ArrayList<UserClass> userList = new ArrayList<>(); // Store all users
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the System!");
            System.out.println("1. Create New Account");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void createNewAccount() {
        if (userList.isEmpty()) {
            System.out.println("No users found, the first user will be an Admin.");
            createUser("Admin");
        } else {
            System.out.println("You need an invitation code to create an account.");
        }
    }

    private static void logIn() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (UserClass user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (!user.isAccountSetUp()) {
                    finishAccountSetup(user);
                }
                navigateHomePage(user);
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    private static void finishAccountSetup(UserClass user) {
        System.out.println("Finish setting up your account:");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter middle name: ");
        String middleName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter preferred first name (optional): ");
        String preferredName = scanner.nextLine();

        user.setAccountDetails(email, firstName, middleName, lastName, preferredName);
        System.out.println("Account setup finished.");
    }

    private static void createUser(String role) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String password2 = scanner.nextLine();

        if (!password.equals(password2)) {
            System.out.println("Passwords do not match!");
            return;
        }

        UserClass user = new UserClass(username, password, role);
        userList.add(user);
        System.out.println("Account created successfully with role: " + role);
    }

    private static void navigateHomePage(UserClass user) {
        if (user.getRoles().size() > 1) {
            System.out.println("Choose your role for this session:");
            for (int i = 0; i < user.getRoles().size(); i++) {
                System.out.println((i + 1) + ". " + user.getRoles().get(i));
            }
            int roleChoice = Integer.parseInt(scanner.nextLine()) - 1;
            String chosenRole = user.getRoles().get(roleChoice);
            homePage(chosenRole);
        } else {
            homePage(user.getRoles().get(0));
        }
    }

    private static void homePage(String role) {
        System.out.println("Home page for role: " + role);
        System.out.println("1. Log out");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            System.out.println("Logged out.");
        }
    }
}



/* 
public class newlogin {

    public void createUser(String role) {

        Scanner scanner = new Scanner(System.in);

        String email;
        String first;
        String middle;
        String last;
        String pref;
        

        System.out.print("Enter email ");
        email = scanner.nextLine();

        
        
        UserClass user = new UserClass(username, password, role);//update user with the new info
        System.out.println("finished account");

        scanner.close(); // Always close the scanner
    }

}
*/

