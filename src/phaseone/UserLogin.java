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

    public static void main(String[] args) {
    }
}