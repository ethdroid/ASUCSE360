public class PasswordEvaluator {
	//track the passwords state when we run through it
	public static String passwordErrorMessage = "";
	public static String passwordInput = "";
	public static int passwordIndexofError = -1; // Index of the character where an error occurred
	// Tracks when certain conditions are met
	public static boolean foundUpperCase = false;
	public static boolean foundLowerCase = false;
	public static boolean foundNumericDigit = false;
	public static boolean foundSpecialChar = false;
	public static boolean foundLongEnough = false;
	private static String inputLine = "";
	private static char currentChar; //The current character being evaluated
	private static int currentCharNdx; // The index of the current character being evaluated
	private static boolean running;

	
	//SHows the current state of the password and process
	private static void displayInputState() {
		System.out.println(inputLine);//show password input
		System.out.println(inputLine.substring(0,currentCharNdx) + "?"); //shows current point of evaluation
		System.out.println("The password size: " + inputLine.length() + "  |  The currentCharNdx: " + 
				currentCharNdx + "  |  The currentChar: \"" + currentChar + "\"");// Showing other info like index and character we are in process
	}
	
	// Evaluate the input password with conditions

	public static String evaluatePassword(String input) {
		// Reset variables for each evaluation
		passwordErrorMessage = "";
		passwordIndexofError = 0;
		inputLine = input;
		currentCharNdx = 0;
		
		
		// Check if password is empty
		if(input.length() <= 0) return "*** Error *** The password is empty!";
		
		currentChar = input.charAt(0);		// The current character from the above indexed position

		// Initialize var
		passwordInput = input;
		foundUpperCase = false;
		foundLowerCase = false;	
		foundNumericDigit = false;
		foundSpecialChar = false;
		foundNumericDigit = false;
		foundLongEnough = false;
		running = true;
		
		// Loop through each character
		// Check each character and make sure conditions are met and update if so
		while (running) {
			displayInputState();
			if (currentChar >= 'A' && currentChar <= 'Z') {//uppercase
				System.out.println("Upper case letter found");
				foundUpperCase = true;
			} else if (currentChar >= 'a' && currentChar <= 'z') {//lowercase
				System.out.println("Lower case letter found");
				foundLowerCase = true;
			} else if (currentChar >= '0' && currentChar <= '9') {//number
				System.out.println("Digit found");
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) {//special char
				System.out.println("Special character found");
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx;
				return "*** Error *** An invalid character has been found!";
			}
			if (currentCharNdx >= 7) {
				System.out.println("At least 8 characters found"); // Check if password is at least 8 characters long
				foundLongEnough = true;
			}
			
			//nect char
			currentCharNdx++;
			if (currentCharNdx >= inputLine.length())
				running = false;
			else
				currentChar = input.charAt(currentCharNdx);
			
			System.out.println();
		}
		
		
		//makes error message if conditions are not met
		String errMessage = "";
		if (!foundUpperCase)
			errMessage += "Upper case; ";
		
		if (!foundLowerCase)
			errMessage += "Lower case; ";
		
		if (!foundNumericDigit)
			errMessage += "Numeric digits; ";
			
		if (!foundSpecialChar)
			errMessage += "Special character; ";
			
		if (!foundLongEnough)
			errMessage += "Long Enough; ";
		
		if (errMessage == "")
			return "";
		
		passwordIndexofError = currentCharNdx;
		return errMessage + "conditions were not satisfied";// return error message

	}
}
