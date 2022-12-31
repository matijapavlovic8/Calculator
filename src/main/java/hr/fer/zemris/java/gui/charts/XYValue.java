package hr.fer.zemris.java.gui.charts;

/**
 * {@code XYValue} represents a point described by its x and y value.
 * @author MatijaPav
 */
public class XYValue {
    /**
     * x-axis value.
     */
    private int x;

    /**
     * y-axis value.
     */
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public XYValue(int x, int y){
        this.x = x;
        this.y = y;
    }
}
