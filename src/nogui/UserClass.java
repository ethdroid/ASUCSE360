
package nogui;

import java.util.ArrayList;
import java.util.Arrays;

public class UserClass {
    private String username;
    private String password;
    private ArrayList<String> roles; // A user can have multiple roles
    private boolean accountSetUp; // Whether the account setup is complete
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredName;

    // Constructor to initialize with username, password, and role
    public UserClass(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>(Arrays.asList(role)); // Add the initial role
        this.accountSetUp = false; // Account is not set up yet
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username (optional)
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for roles
    public ArrayList<String> getRoles() {
        return roles;
    }

    // Add a role to the user
    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    // Remove a role from the user
    public void removeRole(String role) {
        roles.remove(role);
    }

    // Getter for accountSetUp status
    public boolean isAccountSetUp() {
        return accountSetUp;
    }

    // Setter for accountSetUp status
    public void setAccountSetUp(boolean accountSetUp) {
        this.accountSetUp = accountSetUp;
    }

    // Set account details after the setup (email, firstName, middleName, etc.)
    public void setAccountDetails(String email, String firstName, String middleName, String lastName, String preferredName) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredName = (preferredName == null || preferredName.isEmpty()) ? firstName : preferredName;
        setAccountSetUp(true); // Mark the account as set up once details are provided
    }

    // Optional: Get the user's full name
    public String getFullName() {
        return preferredName + " " + lastName;
    }

    // Overriding toString() to provide a user-readable format of the object
    @Override
    public String toString() {
        return "UserClass{" +
               "username='" + username + '\'' +
               ", roles=" + roles +
               ", accountSetUp=" + accountSetUp +
               ", email='" + email + '\'' +
               ", firstName='" + firstName + '\'' +
               ", middleName='" + middleName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", preferredName='" + preferredName + '\'' +
               '}';
    }
}
    
