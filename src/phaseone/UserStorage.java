import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    // Storing users
    private static List<User> users = new ArrayList<>();

    // Adds a user to the system
    public static boolean addUser(User user) {
        // Check for duplicate usernames
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false; // Username already exists
            }
        }
        users.add(user);
        return true; // User successfully added
    }

    // Validates user login
    public static User validateLogin(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return u; // Return user if credentials match
            }
        }
        return null; // Login failed
    }

    // Optional: Method to display all users for testing purposes
    public static void displayAllUsers() {
        for (User u : users) {
            System.out.println(u);
        }
    }
}
