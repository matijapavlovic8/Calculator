package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Objects;
import javax.swing.JComponent;

/**
 * Extension of {@link JComponent} which acts as a container for a {@link BarChart} instance.
 */
public class BarChartComponent extends JComponent {

    /**
     * Displayed {@link BarChart}
     */
    private BarChart chart;

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
        Insets insets = getInsets();
        Dimension dim = getSize();
        FontMetrics fm = g2d.getFontMetrics();

        int height = dim.height - insets.top - insets.bottom;
        int width = dim.width - insets.left - insets.right;

        Rectangle area = new Rectangle(insets.left, insets.top, width, height);

        g2d.setColor(Color.red);
        g2d.fillRect(area.x, area.y, area.width, area.height);


    }
}
