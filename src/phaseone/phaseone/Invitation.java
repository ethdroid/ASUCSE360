package phaseone;

import java.util.ArrayList;

public class Invitation {
    private String code;
    private ArrayList<String> roles;
    private ArrayList<String> groups;

    public Invitation(String code, String role) {
        this.code = code;
        this.roles = new ArrayList<>();
        this.roles.add(role);
        this.groups = new ArrayList<>();
    }

    public Invitation(String code, ArrayList<String> roles, ArrayList<String> groups) {
        this.code = code;
        this.roles = roles;
        this.groups = groups;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void addRole(String role) {
        if (!roles.contains(role)) roles.add(role);
    }

    public void addGroup(String group) {
        if (!groups.contains(group)) groups.add(group);
    }
}
