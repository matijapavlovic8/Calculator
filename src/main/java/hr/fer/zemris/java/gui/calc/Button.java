package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JButton;

/**
 * Class {@code OperationButton} represents operation buttons of a calculator.
 * @author MatijaPav
 */
public class Button extends JButton {
    /**
     * Currently displayed label.
     */
    private String displayedLabel;

    /**
     * Button label.
     */
    private String label;

    /**
     * Button label in inverse mode.
     */
    private String invLabel;

    /**
     * {@link ActionListener} used when {@link Calculator} is in "normal" (i.e. non-inverse) mode.
     */
    private ActionListener operation;

    /**
     * {@link ActionListener} used when {@link Calculator} is in inverse mode.
     */
    private ActionListener invOperation;

    /**
     * Instantiates {@link Button}
     * @param label Label of the button.
     * @param operation {@link ActionListener} for "normal" mode.
     * @param invOperation {@link ActionListener} for inverse mode.
     */
    public Button(String label, String invLabel, ActionListener operation, ActionListener invOperation){
        this(label, operation);
        this.invOperation = Objects.requireNonNull(invOperation, "Inverse operation can't be null!");
        this.invLabel = Objects.requireNonNull(invLabel, "Inverse label can't be null!");

    }

    /**
     * Instantiates {@link Button} without defined {@link ActionListener} for inverse
     * operation.
     * @param label Label of the button.
     * @param operation {@link ActionListener} for "normal" mode.
     */
    public Button(String label, ActionListener operation){
        this.label = Objects.requireNonNull(label, "Label can't be null!");
        this.operation = Objects.requireNonNull(operation, "Operation can't be null!");
        this.displayedLabel = label;
        setDisplay();
    }

    private void setDisplay(){
        setBackground(Color.decode("#C1D0E8"));
        setText(this.displayedLabel);
        setForeground(Color.BLACK);
        addActionListener(operation);
    }

    /**
     * Switches current {@link ActionListener}.
     * @param inverse tracks active action listener.
     */
    public void inverse(boolean inverse){
        if(!inverse){
            removeActionListener(invOperation);
            addActionListener(operation);
            this.displayedLabel = label;
            setDisplay();
        } else {
            Objects.requireNonNull(invOperation, "Inverse not supported for this button!");
            removeActionListener(operation);
            addActionListener(invOperation);
            this.displayedLabel = invLabel;
            setDisplay();
        }
    }

}
