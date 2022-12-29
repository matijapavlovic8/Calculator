package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
        this.inverseButtons = new ArrayList<>();
        setLocation(100, 100);
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
        panel.add(createInverseCheckbox(), "5, 7");
        addStackButtons(panel);
        addTrigFunctionButtons(panel);
        addLogFunctionButtons(panel);
        addOperatorButtons(panel);
        panel.add(equalsButton(), "1, 6");
        addOtherButtons(panel);
        cp.setPreferredSize(panel.getPreferredSize());
        cp.add(panel);
    }

    /**
     * Creates a {@link JLabel} for result display.
     * @return {@link JLabel} instance.
     */
    private JLabel createResDisplay(){
        JLabel display = new JLabel(calcModel.toString());
        display.setFont(display.getFont().deriveFont(30f));
        display.setOpaque(true);
        display.setBackground(Color.YELLOW);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        calcModel.addCalcValueListener(model -> display.setText(model.toString()));
        return display;
    }

    /**
     * Adds number buttons to {@link Calculator}'s panel.
     * @param panel {@code JPanel} to which the buttons are added;
     */
    private void addNumButtons(JPanel panel){
        Button zero = new Button("0", o -> calcModel.insertDigit(0));
        zero.setFont(zero.getFont().deriveFont(30f));

        Button one = new Button("1", o -> calcModel.insertDigit(1));
        one.setFont(zero.getFont());

        Button two = new Button("2", o -> calcModel.insertDigit(2));
        two.setFont(zero.getFont());

        Button three = new Button("3", o -> calcModel.insertDigit(3));
        three.setFont(zero.getFont());

        Button four = new Button("4", o -> calcModel.insertDigit(4));
        four.setFont(zero.getFont());

        Button five = new Button("5", o -> calcModel.insertDigit(5));
        five.setFont(zero.getFont());

        Button six = new Button("6", o -> calcModel.insertDigit(6));
        six.setFont(zero.getFont());

        Button seven = new Button("7", o -> calcModel.insertDigit(7));
        seven.setFont(zero.getFont());

        Button eight = new Button("8", o -> calcModel.insertDigit(8));
        eight.setFont(zero.getFont());

        Button nine = new Button("9", o -> calcModel.insertDigit(9));
        nine.setFont(zero.getFont());
        panel.add(zero, "5, 3");
        panel.add(one, "4, 3");
        panel.add(two, "4, 4");
        panel.add(three, "4, 5");
        panel.add(four, "3, 3");
        panel.add(five, "3, 4");
        panel.add(six, "3, 5");
        panel.add(seven, "2, 3");
        panel.add(eight, "2, 4");
        panel.add(nine, "2, 5");
    }

    /**
     * Creates a {@link JCheckBox} instance that sets the {@code Calculator}
     * to inverse mode and back.
     * @return {@code JCheckBox}
     */
    private JCheckBox createInverseCheckbox(){
        JCheckBox inverse = new JCheckBox("Inv");
        inverse.setForeground(Color.BLACK);
        inverse.addItemListener(l -> {
            for(Button btn: inverseButtons)
                btn.inverse(inverse.isSelected());
        });
        return inverse;
    }

    /**
     * Creates and adds buttons for stack manipulation.
     * @param panel {@code JPanel} to which the buttons are added.
     */
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

    /**
     * Creates and adds logarithmic function buttons to current {@link JPanel}.
     * @param panel {@code JPanel} to which the buttons are added.
     */
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
    /**
     * Creates and adds operator buttons to current {@link JPanel}.
     * @param panel {@code JPanel} to which the buttons are added.
     */
    private void addOperatorButtons(JPanel panel){
        Button plus = new Button("+", e -> {
            operatorAction();
            calcModel.setPendingBinaryOperation(Double::sum);
        });
        panel.add(plus, "5, 6");

        Button min = new Button("-", e -> {
            operatorAction();
            calcModel.setPendingBinaryOperation((l, r) -> l - r);
        });
        panel.add(min, "4, 6");

        Button mul = new Button("*", e -> {
            operatorAction();
            calcModel.setPendingBinaryOperation((l, r) -> l * r);
        });
        panel.add(mul, "3, 6");

        Button div = new Button("/", e -> {
            operatorAction();
            calcModel.setPendingBinaryOperation((l, r) -> l / r);
        });
        panel.add(div, "2, 6");

        Button pow = new Button("x^n", "x^(1/n)", e ->{
            operatorAction();
            calcModel.setPendingBinaryOperation(Math::pow);
        },
            e ->{
                operatorAction();
                calcModel.setPendingBinaryOperation((l, r) ->
                    Math.pow(l, 1 / r)
                );
        });
        inverseButtons.add(pow);
        panel.add(pow, "5, 1");
    }

    /**
     * Creates an equals button.
     * @return equals button.
     */
    private Button equalsButton(){
        return new Button("=", e -> {
            if(calcModel.getPendingBinaryOperation() != null && calcModel.isActiveOperandSet()){
                System.out.println(calcModel.getActiveOperand());
                calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
                calcModel.setPendingBinaryOperation(null);
            }
        });
    }

    /**
     * Checks if the passed {@link CalcModel} instance has a pending operation and an active operand set.
     */
    private void operatorAction(){
        if(calcModel.getPendingBinaryOperation() == null){
            calcModel.setActiveOperand(calcModel.getValue());
            calcModel.setValue(calcModel.getValue());
        }
        else if(calcModel.isActiveOperandSet()){
           double res = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
           calcModel.setValue(res);
           calcModel.setActiveOperand(calcModel.getValue());
        }
    }

    /**
     * Creates and adds buttons to given panel.
     */
    private void addOtherButtons(JPanel panel){
        Button rp = new Button("1/x", e -> calcModel.setValue(1 / calcModel.getValue()));
        panel.add(rp, "2, 1");

        Button clear = new Button("clr", e -> calcModel.clear());
        panel.add(clear, "1, 7");

        Button reset = new Button("reset", e -> calcModel.clearAll());
        panel.add(reset, "2, 7");

        Button negate = new Button("+/-", e -> calcModel.swapSign());
        panel.add(negate, "5, 4");

        Button decimal = new Button(".", e -> calcModel.insertDecimalPoint());
        panel.add(decimal, "5, 5");

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }

}
