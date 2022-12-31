package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChartDemo extends JFrame {

    /**
     * Displayed {@code BarChart}
     */
    private BarChart barChart;

    public BarChartDemo(BarChart barChart){
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChart");
        setLocation(50, 50);
        this.barChart = Objects.requireNonNull(barChart);
        initGUI();
    }

    private void initGUI(){
        Container cp = getContentPane();
        cp.setPreferredSize(new Dimension(760, 420));
        cp.add(new BarChartComponent(barChart));
    }

    public static void main(String[] args) {
        if(args.length != 1)
            throw new IllegalArgumentException();

        BarChart chart = createChart(Paths.get(args[0]));

        SwingUtilities.invokeLater(() -> {
            BarChartDemo demo = new BarChartDemo(chart);
            demo.setVisible(true);
        });
    }

    /**
     * Creates a {@link BarChart} from input text.
     * @return {@link BarChart}
     */
    private static BarChart createChart(Path path){
        List<String> list;
        try{
            list = Files.readAllLines(path);
        } catch (IOException e){
            throw new IllegalArgumentException("Wrong path given as argument!");
        }

        if(list.size() != 6)
            throw new IllegalArgumentException("Wrong file given!");

        return new BarChart(getValues(list.get(2)), list.get(0), list.get(1),
            Integer.parseInt(list.get(3)), Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)));


    }

    /**
     * Creates a list of values for the chart.
     * @param str {@code String} being parsed.
     * @return {@code List<XYValue>} of values.
     */
    private static List<XYValue> getValues(String str){
        String[] splits = str.split("\\s+");

        List<XYValue> values = new ArrayList<>();

        for(String value: splits){
            String[] points = value.split(",");
            values.add(new XYValue(Integer.parseInt(points[0].trim()), Integer.parseInt(points[1].trim())));
        }
        return values;
    }
}
