package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * {@code BarChart} represents the information needed for creation of bar charts.
 * @author MatijaPav
 */
public class BarChart {
    /**
     * List of values.
     */
    private List<XYValue> values;

    /**
     * Description of x-axis.
     */
    private String xDesc;

    /**
     * Description of y-axis.
     */
    private String yDesc;

    /**
     * Minimal value on y-axis.
     */
    private int yMin;

    /**
     * Maximal value on y-axis.
     */
    private int yMax;

    /**
     * Step between two y-axis values.
     */
    private int step;

    public BarChart(List<XYValue> values, String xDesc, String yDesc, int yMin, int yMax, int step) {
        this.values = Objects.requireNonNull(values, "Values can't be null!");
        if(yMin < 0 || yMin >= yMax)
            throw new IllegalArgumentException("yMin must be positive and less than yMax!");

        values.forEach(val -> {
            if(val.getY() < yMin)
                throw new IllegalArgumentException("Point can't have y-axis value les than y-min!");
        });

        this.xDesc = xDesc;
        this.yDesc = yDesc;
        this.yMin = yMin;
        this.yMax = yMax;
        this.step = step;
    }

    public List<XYValue> getValues() {
        return values;
    }

    public String getxDesc() {
        return xDesc;
    }

    public String getyDesc() {
        return yDesc;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public int getStep() {
        return step;
    }
}
