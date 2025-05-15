import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String operator;
    private boolean startNewNumber;

    public Calculator() {
        setTitle("Basic Calculator");
        setSize(320, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        currentInput = new StringBuilder();
        result = 0;
        operator = "";
        startNewNumber = true;

        // Display panel at top
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        // Button panel center
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "C", "", "", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String text : buttons) {
            JButton button;
            if (text.equals("")) {
                button = new JButton();
                button.setEnabled(false);
                button.setVisible(false);
            } else {
                button = new JButton(text);
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.addActionListener(this);
            }
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (startNewNumber) {
                currentInput.setLength(0); // Clear current input
                startNewNumber = false;
            }
            if (command.equals(".") && currentInput.toString().contains(".")) {
                // ignore second decimal point
                return;
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if ("+-*/".contains(command)) {
            calculate();
            operator = command;
            startNewNumber = true;
        } else if (command.equals("=")) {
            calculate();
            operator = "";
            startNewNumber = true;
        } else if (command.equals("C")) {
            currentInput.setLength(0);
            result = 0;
            operator = "";
            startNewNumber = true;
            display.setText("0");
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
        if (Math.floor(result) == result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }
        currentInput.setLength(0);
        currentInput.append(display.getText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
