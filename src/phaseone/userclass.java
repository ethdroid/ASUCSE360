public class userclass {
    private String username;
    private String password;
    private String role;
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String preferredName;


    public userclass(String username, String password, String role, String email, String firstname, String middlename, String lastname, String preferredName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.preferredName = preferredName;
    }

    public String getUsername() {
         return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String toString() {
        return "Username: " + username + " , " +
                "Password: " + password + " , " +
                "Role: " + role + ", " +
                "Email: " + email + " , " +
                "Firstname: " + firstname + " , " +
                "Middlename: " + middlename + " , " +
                "Lastname: " + lastname + " , " +
                "Preferred Name: " + preferredName;
    }
}