package model;

import com.google.gson.Gson;

public class Message {
    private String action;
    private String data;
    private String dataSlot2;
    private String dataSlot3;
    private String dataSlot4;

    public String getDataSlot4() {
        return dataSlot4;
    }

    public void setDataSlot4(String dataSlot4) {
        this.dataSlot4 = dataSlot4;
    }

    public Message(){
        this.action="";
        this.data = "";
        this.dataSlot2 = "";
        this.dataSlot3 = "";
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
