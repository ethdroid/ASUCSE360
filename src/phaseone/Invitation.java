package phaseone;


import java.util.ArrayList;


public class Invitation {
    private String code;
    private ArrayList<String> roles;


    public Invitation(String code, String role) {
        this.code = code;
        this.roles = new ArrayList<>();
        this.roles.add(role);
    }






    public Invitation(String code, ArrayList<String> roles) {
        this.code = code;
        this.roles = roles;
    }




   


    public String getCode() {
        return code;
    }


    public ArrayList<String> getRoles() {
        return roles;
    }
}

