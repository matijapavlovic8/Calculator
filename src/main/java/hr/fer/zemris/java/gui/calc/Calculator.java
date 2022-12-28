package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

import java.awt.Color;
import java.awt.Container;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Calculator GUI implementation.
 * @author MatijaPav
 */
public class Calculator extends JFrame {
    /**
     * {@code CalcModel} instance used for implementation of basic Calculator functionlities.
     *
     */
    private final CalcModel calcModel;

    /**
     * Calculators stack.
     */
    private Stack<Double> stack;

    /**
     * List of {@link Button} with inverse.
     */
    private List<Button> inverseButtons;

    /**
     * Instantiates a new {@code Calculator}
     */
    public Calculator(){
        this.calcModel = new CalcModelImpl();
        this.stack = new Stack<>();
        setLocation(100, 100);
        setSize(500, 500);
        setTitle("JCalculator");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initGUI();
        pack();
    }

    /**
     * Initializes GUI for {@link Calculator} app.
     */
    private void initGUI(){
        Container cp = getContentPane();
        JPanel panel = new JPanel(new CalcLayout(5));
        panel.add(createResDisplay(), "1, 1");
        addNumButtons(panel);
        panel.add(createInverseCheckbox());
        addStackButtons(panel);
        addTrigFunctionButtons(panel);
        addLogFunctionButtons(panel);
    }

    /**
     * Creates a {@link JLabel} for result display.
     * @return {@link JLabel} instance.
     */
    private JLabel createResDisplay(){
        JLabel display = new JLabel(this.calcModel.toString());
        calcModel.addCalcValueListener(l -> display.setText(l.toString()));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setVerticalAlignment(SwingConstants.CENTER);
        return display;
    }

    /**
     * Adds number buttons to {@link Calculator}'s panel.
     * @param panel {@code JPanel} to which the buttons are added;
     */
    private void addNumButtons(JPanel panel){
        panel.add(new Button("0", o -> calcModel.insertDigit(0)), "5, 3");
        panel.add(new Button("1", o -> calcModel.insertDigit(1)), "4, 3");
        panel.add(new Button("2", o -> calcModel.insertDigit(2)), "4, 4");
        panel.add(new Button("3", o -> calcModel.insertDigit(3)), "4, 5");
        panel.add(new Button("4", o -> calcModel.insertDigit(4)), "3, 3");
        panel.add(new Button("5", o -> calcModel.insertDigit(5)), "3, 4");
        panel.add(new Button("6", o -> calcModel.insertDigit(6)), "3, 5");
        panel.add(new Button("7", o -> calcModel.insertDigit(7)), "2, 3");
        panel.add(new Button("8", o -> calcModel.insertDigit(8)), "2, 4");
        panel.add(new Button("9", o -> calcModel.insertDigit(9)), "2, 5");
    }

    /**
     * Creates a {@link JCheckBox} instance that sets the {@code Calculator}
     * to inverse mode and back.
     * @return {@code JCheckBox}
     */
    private JCheckBox createInverseCheckbox(){
        JCheckBox inverse = new JCheckBox("Inv");
        inverse.setBackground(Color.BLUE);
        inverse.setForeground(Color.lightGray);
        inverse.addItemListener(l -> {
            for(Button btn: inverseButtons)
                btn.inverse(inverse.isSelected());
        });
        return inverse;
    }

    private void addStackButtons(JPanel panel){
        panel.add(new Button("pop", e -> {
            try{
                this.calcModel.setValue(this.stack.pop());
            } catch (EmptyStackException ignored){}
        }), "4, 7");

        panel.add(new Button("push", e -> this.stack.push(calcModel.getValue())), "3, 7");
    }

    /**
     * Creates buttons for trigonometric functions.
     * @param panel {@link JPanel} panel to which the buttons are added.
     */
    private void addTrigFunctionButtons(JPanel panel){
        Button sin = new Button("sin", "arcsin", e -> calcModel.setValue(Math.sin(calcModel.getValue())),
            e -> calcModel.setValue(Math.asin(calcModel.getValue())));
        inverseButtons.add(sin);
        panel.add(sin, "2, 2");

        Button cos = new Button("cos", "arccos", e -> calcModel.setValue(Math.cos(calcModel.getValue())),
            e -> calcModel.setValue(Math.acos(calcModel.getValue())));
        inverseButtons.add(cos);
        panel.add(cos, "3, 2");

        Button tan = new Button("tan", "arctan", e -> calcModel.setValue(Math.tan(calcModel.getValue())),
            e -> calcModel.setValue(Math.atan(calcModel.getValue())));
        inverseButtons.add(tan);
        panel.add(tan, "4, 2");

        Button ctg = new Button("ctg", "arcctg", e -> calcModel.setValue(1. / Math.tan(calcModel.getValue())),
            e -> calcModel.setValue(Math.PI / 2 - Math.atan(calcModel.getValue())));
        inverseButtons.add(ctg);
        panel.add(ctg, "5, 2");
    }

    private void addLogFunctionButtons(JPanel panel){
        Button log = new Button("log", "10^x", e -> calcModel.setValue(Math.log10(calcModel.getValue())),
            e -> calcModel.setValue(Math.pow(calcModel.getValue(), 10)));
        inverseButtons.add(log);
        panel.add(log, "3, 1");

        Button ln = new Button("ln", "e^x", e -> calcModel.setValue(Math.log(calcModel.getValue())),
            e -> calcModel.setValue(Math.pow(Math.E, calcModel.getValue())));
        inverseButtons.add(ln);
        panel.add(ln, "4, 1");
    }

    
}
