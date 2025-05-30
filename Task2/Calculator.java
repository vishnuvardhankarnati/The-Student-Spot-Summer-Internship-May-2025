import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String operator;
    private boolean startNewNumber;

    public Calculator() {
        setTitle("Simple Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        currentInput = new StringBuilder();
        result = 0;
        operator = "";
        startNewNumber = true;

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            if (startNewNumber) {
                currentInput.setLength(0); // Clear current input
                startNewNumber = false;
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if (command.equals("C")) {
            currentInput.setLength(0);
            result = 0;
            operator = "";
            startNewNumber = true;
            display.setText("0");
        } else if (command.equals("=")) {
            calculate();
            operator = "";
            startNewNumber = true;
        } else {
            calculate();
            operator = command;
            startNewNumber = true;
        }
    }

    private void calculate() {
        double inputValue;
        try {
            inputValue = currentInput.length() == 0 ? 0 : Double.parseDouble(currentInput.toString());
        } catch (NumberFormatException e) {
            display.setText("Error");
            currentInput.setLength(0);
            startNewNumber = true;
            return;
        }

        if (operator.isEmpty()) {
            result = inputValue;
        } else {
            switch (operator) {
                case "+":
                    result += inputValue;
                    break;
                case "-":
                    result -= inputValue;
                    break;
                case "*":
                    result *= inputValue;
                    break;
                case "/":
                    if (inputValue == 0) {
                        display.setText("Error: Div by 0");
                        currentInput.setLength(0);
                        startNewNumber = true;
                        operator = "";
                        return;
                    }
                    result /= inputValue;
                    break;
            }
        }
        display.setText(String.valueOf(result));
        currentInput.setLength(0);
        currentInput.append(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
