public class userclass {
            private String username;
            private String password;
            private String role;

            public userclass(String username, String password, String role) {
                this.username = username;
                this.password = password;
                this.role = role;
            }

    // Getter for username
            public String getUsername() {
                return username;
            }

    // Setter for username
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

    // Getter for role
            public String getRole() {
                return role;
            }

    // Setter for role
            public void setRole(String role) {
                this.role = role;
            }

            public String toString() {
                return "User{" +
                    "username='" + username + '\'' +
                    ", password='[PROTECTED]" +
                    ", role='" + role + '\'' +
                    '}';
            }
        }