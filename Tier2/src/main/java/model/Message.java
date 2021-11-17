package model;

public class Message {
    private String action;
    private String username;
    private String password;
    private String email;
    private String data;

    public Message(){
        this.action="";
        this.password="";
        this.email="";
        this.username="";
    }

    public Message(String action, String username, String password, String email) {
        this.action = action;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Message(String action) {
        this.action = action;
    }

    public Message(String action, String data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "action='" + action + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
