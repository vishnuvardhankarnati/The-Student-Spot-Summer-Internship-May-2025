import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private double num1, num2, result;
    private String operator;

    public Calculator() {
        display = new JTextField();
        display.setBounds(30, 40, 280, 30);
        add(display);

        String[] buttonLabels = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};
        int x = 30, y = 100;
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setBounds(x, y, 60, 40);
            button.addActionListener(this);
            add(button);
            x += 70;
            if (x > 240) {
                x = 30;
                y += 50;
            }
        }

        setLayout(null);
        setSize(350, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implement calculator logic here
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
