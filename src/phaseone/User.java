package phaseone;


import java.util.ArrayList;


public class User {
    private String username;
    private String password;
    private ArrayList<String> roles;
    private boolean accountSetupComplete;
    private String oneTimePassword;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;


    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add(role);
        this.accountSetupComplete = false;
    }


    // Getters and Setters for new fields
    public String getEmail() {
        return email;
    }










    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }




   


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getMiddleName() {
        return middleName;
    }


    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPreferredFirstName() {
        return preferredFirstName;
    }


    public void setPreferredFirstName(String preferredFirstName) {
        this.preferredFirstName = preferredFirstName;
    }


    // Other existing methods
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public ArrayList<String> getRoles() {
        return roles;
    }


    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }


    public boolean isAccountSetupComplete() {
        return accountSetupComplete;
    }


    public void completeAccountSetup() {
        this.accountSetupComplete = true;
    }


    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }


    public String getOneTimePassword() {
        return oneTimePassword;
    }
}
