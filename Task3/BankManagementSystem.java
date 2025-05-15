import java.io.*;
import java.util.*;

class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if(amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if(amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: $" + balance);
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
               "\nAccount Holder: " + accountHolderName +
               "\nBalance: $" + String.format("%.2f", balance);
    }
}

public class BankManagementSystem {
    private static final String DATA_FILE = "accounts.dat";
    private Map<String, BankAccount> accounts;
    private Scanner scanner;

    public BankManagementSystem() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        loadAccounts();
    }

    private void loadAccounts() {
        File file = new File(DATA_FILE);
        if(!file.exists()) {
            System.out.println("No accounts data found, starting fresh.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            accounts = (Map<String, BankAccount>) ois.readObject();
            System.out.println("Accounts loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load accounts: " + e.getMessage());
        }
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save accounts: " + e.getMessage());
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
        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = readDouble();
        if(initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative.");
            return;
        }
        BankAccount account = new BankAccount(accNum, accHolder, initialDeposit);
        accounts.put(accNum, account);
        System.out.println("Account created successfully!");
    }

    private void deposit() {
        BankAccount account = getAccountByNumber();
        if(account == null) return;
        System.out.print("Enter deposit amount: ");
        double amount = readDouble();
        account.deposit(amount);
    }

    private void withdraw() {
        BankAccount account = getAccountByNumber();
        if(account == null) return;
        System.out.print("Enter withdrawal amount: ");
        double amount = readDouble();
        account.withdraw(amount);
    }

    private void displayAccount() {
        BankAccount account = getAccountByNumber();
        if(account != null) {
            System.out.println(account);
        }
    }

    private void displayAllAccounts() {
        if(accounts.isEmpty()) {
            System.out.println("No accounts to display.");
            return;
        }
        for(BankAccount acc : accounts.values()) {
            System.out.println("---------------");
            System.out.println(acc);
        }
    }

    private BankAccount getAccountByNumber() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = accounts.get(accNum);
        if(account == null) {
            System.out.println("Account not found.");
        }
        return account;
    }

    private double readDouble() {
        while(true) {
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch(NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private void menu() {
        while(true) {
            System.out.println("\nBank Management System");
            System.out.println("1. Create new account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display account");
            System.out.println("5. Display all accounts");
            System.out.println("6. Save and Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    createAccount();
                    break;
                case "2":
                    deposit();
                    break;
                case "3":
                    withdraw();
                    break;
                case "4":
                    displayAccount();
                    break;
                case "5":
                    displayAllAccounts();
                    break;
                case "6":
                    saveAccounts();
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem();
        system.menu();
    }
}
