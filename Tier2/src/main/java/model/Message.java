package model;

import com.google.gson.Gson;

/**
 * @author group9
 * @version 1.0
 */

public class Message {
    private String action;
    private String data;
    private String dataSlot2;
    private String dataSlot3;
    private String dataSlot4;

    /**
     * Gets the data slot 4
     * @return data slot 4
     */
    public String getDataSlot4() {
        return dataSlot4;
    }

    /**
     * Sets teh data slot 4
     * @param dataSlot4 slot 4
     */
    public void setDataSlot4(String dataSlot4) {
        this.dataSlot4 = dataSlot4;
    }

    /**
     * Creates the message
     */
    public Message(){
        this.action="";
        this.data = "";
        this.dataSlot2 = "";
        this.dataSlot3 = "";
    }

    /**
     * Creates the message
     * @param action action
     */
    public Message(String action) {
        this.action = action;
    }

    /**
     * Creates the message
     * @param action action
     * @param data data
     */
    public Message(String action, String data) {
        this.action = action;
        this.data = data;
    }

    /**
     * Returns the action
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action
     * @param action action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Sets the data
     * @param data data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Returns the data
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * Returns the second data slot
     * @return second data slot
     */
    public String getDataSlot2() {
        return dataSlot2;
    }

    /**
     * Sets the second data slot
     * @param data2 data slot
     */
    public void setDataSlot2(String data2) {
        this.dataSlot2 = data2;
    }

    /**
     * Returns the 3 data slot
     * @return 3 data slot
     */
    public String getDataSlot3() {
        return dataSlot3;
    }

    /**
     * Sets the 3 data slot
     * @param dataSlot3 slot 3
     */
    public void setDataSlot3(String dataSlot3) {
        this.dataSlot3 = dataSlot3;
    }

    /**
     * Method to print out the message
     * @return message string
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
