package model;

/**
 * @author Nick/Rokas
 * @version 1.0
 */

public class Position {
    private int verticalAxis;
    private int horizontalAxis;

    /**
     * Creating a Position and setting the x and z axis to 0
     */
    public Position(int VerticalAxis, int HorizontalAxis){
        this.verticalAxis = VerticalAxis;
        this.horizontalAxis = HorizontalAxis;
    }

    /**
     * Returns Vertical Axis
     * @return Vertical AXis
     */
    public int getVerticalAxis() {
        return verticalAxis;
    }

    /**
     * Sets Vertical Axis
     * @param verticalAxis Vertical Axis
     */
    public void setVerticalAxis(int verticalAxis) {
        this.verticalAxis = verticalAxis;
    }

    /**
     * Returns Horizontal Axis
     * @return Horizontal Axis
     */
    public int getHorizontalAxis() {
        return horizontalAxis;
    }

    /**
     * Sets Horizontal Axis
     * @param horizontalAxis Horizontal Axis
     */
    public void setHorizontalAxis(int horizontalAxis) {
        this.horizontalAxis = horizontalAxis;
    }

    /**
     * To String method displaying the coordinates
     * @return coordinates
     */
    @Override
    public String toString(){
        return "Coordinates (Y,X): " + verticalAxis + ", " + horizontalAxis;
    }
}
