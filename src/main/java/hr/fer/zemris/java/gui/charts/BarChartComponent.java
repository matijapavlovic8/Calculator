package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import javax.swing.JComponent;

/**
 * Extension of {@link JComponent} which acts as a container for a {@link BarChart} instance.
 */
public class BarChartComponent extends JComponent {

    /**
     * Displayed {@link BarChart}
     */
    private final BarChart chart;

    /**
     * Creates a {@link BarChartComponent}.
     * @param chart {@link BarChart} instance displayed in the component.
     */
    public BarChartComponent(BarChart chart){
        this.chart = Objects.requireNonNull(chart);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

    }

    private void drawYAxisText(Graphics2D g2d){
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(chart.getyDesc(), g2d);

    }
}
