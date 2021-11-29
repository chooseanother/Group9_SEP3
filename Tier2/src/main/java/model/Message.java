package model;

import com.google.gson.Gson;

public class Message {
    private String action;
    private String username;
    private String password;
    private String email;
    private String data;
    private String dataSlot2;
    private String dataSlot3;


    public Message(){
        this.action="";
        this.data = "";
        this.dataSlot2 = "";
        this.dataSlot3 = "";
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

    public String getDataSlot2() {
        return dataSlot2;
    }

    public void setDataSlot2(String data2) {
        this.dataSlot2 = data2;
    }

    public String getDataSlot3() {
        return dataSlot3;
    }

    public void setDataSlot3(String dataSlot3) {
        this.dataSlot3 = dataSlot3;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
