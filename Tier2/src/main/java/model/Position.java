package model;

/**
 * @author Nick/Rokas
 * @version 1.0
 */

public class Position {
    private int VerticalAxis;
    private int HorizontalAxis;

    /**
     * Creating a Position and setting the x and z axis to 0
     */
    public Position(int VerticalAxis, int HorizontalAxis){
        this.VerticalAxis = VerticalAxis;
        this.HorizontalAxis = HorizontalAxis;
    }

    /**
     * Returns Vertical Axis
     * @return Vertical AXis
     */
    public int getVerticalAxis() {
        return VerticalAxis;
    }

    /**
     * Sets Vertical Axis
     * @param verticalAxis Vertical Axis
     */
    public void setVerticalAxis(int verticalAxis) {
        VerticalAxis = verticalAxis;
    }

    /**
     * Returns Horizontal Axis
     * @return Horizontal Axis
     */
    public int getHorizontalAxis() {
        return HorizontalAxis;
    }

    /**
     * Sets Horizontal Axis
     * @param horizontalAxis Horizontal Axis
     */
    public void setHorizontalAxis(int horizontalAxis) {
        HorizontalAxis = horizontalAxis;
    }

    /**
     * To String method displaying the coordinates
     * @return coordinates
     */
    @Override public String toString(){
        return "Coordinates (Y,X): " + VerticalAxis+ ", " + HorizontalAxis;
    }
}
