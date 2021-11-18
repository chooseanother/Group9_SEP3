package model;

public class Message {
    private String action;
    private String username;
    private String password;
    private String email;
    private int firstLayer;
    private int secondLayer;
    private String object;
    private String upgradeSelected;

    public Message(){
        this.action="";
        this.password="";
        this.email="";
        this.username="";
        this.firstLayer = 0;
        this.secondLayer = 0;
        this.object = "";
        this.upgradeSelected = "";
    }

    public Message(String action, String username, String password, String email) {
        this.action = action;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getAction() {
        return action;
    }

    public String getUpgradeSelected() {
        return upgradeSelected;
    }

    public void setUpgradeSelected(String upgradeSelected) {
        this.upgradeSelected = upgradeSelected;
    }

    public int getFirstLayer() {
        return firstLayer;
    }

    public void setFirstLayer(int firstLayer) {
        this.firstLayer = firstLayer;
    }

    public int getSecondLayer() {
        return secondLayer;
    }

    public void setSecondLayer(int secondLayer) {
        this.secondLayer = secondLayer;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    @Override
    public String toString() {
        return "Message{" +
                "action='" + action + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}