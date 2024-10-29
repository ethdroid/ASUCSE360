package phaseone;

public class PasswordEvaluator {
    public static String passwordErrorMessage = "";
    public static int passwordIndexofError = -1;

    public static boolean validatePassword(String password) {
        // Reset errors and criteria
        passwordErrorMessage = "";
        passwordIndexofError = -1;

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        boolean isLongEnough = password.length() >= 8;

        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (Character.isUpperCase(currentChar)) hasUpperCase = true;
            else if (Character.isLowerCase(currentChar)) hasLowerCase = true;
            else if (Character.isDigit(currentChar)) hasDigit = true;
            else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) hasSpecialChar = true;
            else {
                passwordIndexofError = i;
                passwordErrorMessage = "Invalid character found!";
                return false;
            }
        }

        if (!hasUpperCase) passwordErrorMessage += "Missing uppercase; ";
        if (!hasLowerCase) passwordErrorMessage += "Missing lowercase; ";
        if (!hasDigit) passwordErrorMessage += "Missing digit; ";
        if (!hasSpecialChar) passwordErrorMessage += "Missing special character; ";
        if (!isLongEnough) passwordErrorMessage += "Password too short; ";

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && isLongEnough;
    }
}
