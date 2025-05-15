import java.io.*;
import java.util.*;

class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if(amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if(amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful. Remaining balance: " + balance);
        }
    }

    public void displayAccount() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: " + balance);
    }
}

public class BankManagementSystem {
    private static final String DATA_FILE = "Task3/accounts.dat";
    private HashMap<String, BankAccount> accounts;
    private Scanner scanner;

    public BankManagementSystem() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        loadAccounts();
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accounts = (HashMap<String, BankAccount>) ois.readObject();
            System.out.println("Accounts loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous account data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading account data.");
        }
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("Account data saved.");
        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }

    private void createAccount() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        if(accounts.containsKey(accNum)) {
            System.out.println("Account number already exists.");
            return;
        }
        System.out.print("Enter account holder name: ");
        String accHolder = scanner.nextLine();
        BankAccount account = new BankAccount(accNum, accHolder);
        accounts.put(accNum, account);
        System.out.println("Account created successfully.");
        saveAccounts();
    }

    private void deposit() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = accounts.get(accNum);
        if(account == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();
        account.deposit(amount);
        saveAccounts();
    }

    private void withdraw() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = accounts.get(accNum);
        if(account == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();
        account.withdraw(amount);
        saveAccounts();
    }

    private void displayAccount() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = accounts.get(accNum);
        if(account == null) {
            System.out.println("Account not found.");
            return;
        }
        account.displayAccount();
    }

    private void displayAllAccounts() {
        if(accounts.isEmpty()) {
            System.out.println("No accounts to display.");
            return;
        }
        for (BankAccount account : accounts.values()) {
            account.displayAccount();
            System.out.println("--------------------");
        }
    }

    private double getDoubleInput() {
        while(true) {
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch(NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid number: ");
            }
        }
    }

    private void menu() {
        while(true) {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display Account Details");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch(choice) {
                case "1" -> createAccount();
                case "2" -> deposit();
                case "3" -> withdraw();
                case "4" -> displayAccount();
                case "5" -> displayAllAccounts();
                case "6" -> {
                    saveAccounts();
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem();
        system.menu();
    }
}
