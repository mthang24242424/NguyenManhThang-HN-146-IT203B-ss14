package entity;

public class Users {
    private int user_id;
    private String username;
    private String password;
    private String role;
    private String created_at;

    public Users() {
    }

    public Users(int user_id, String username, String password, String role, String created_at) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    @Override
    public String toString() {
        return String.format("%-5d %-15s %-10s %-20s",
                user_id, username, role, created_at);
    }
}
