package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * {@code PrimDemo} generates and displays prime numbers.
 */
public class PrimDemo extends JFrame {

    public PrimDemo(){
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PrimDemo");
        initGUI();
    }

    private void initGUI(){
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        PrimListModel model = new PrimListModel();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JScrollPane(new JList<>(model)));
        panel.add(new JScrollPane(new JList<>(model)));

        JButton next = new JButton("Next");
        next.addActionListener(e -> model.next());

        cp.add(panel, BorderLayout.CENTER);
        cp.add(next, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> {
                new PrimDemo().setVisible(true);
            }
        );
    }
}
