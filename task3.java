package oasisinfobyte;

import java.util.*;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String pin) {
        return this.userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount, balance));
            return true;
        }
        return false;
    }

    public void addTransaction(String type, double amount, double balanceAfter) {
        transactionHistory.add(new Transaction(type, amount, balanceAfter));
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class ATM {
    private Bank bank;
    private Scanner scanner;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPin = scanner.nextLine();

        User currentUser = bank.authenticateUser(userId, userPin);
        if (currentUser != null) {
            System.out.println("Authentication successful!");
            boolean quit = false;
            while (!quit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        showTransactionHistory(currentUser);
                        break;
                    case 2:
                        performWithdraw(currentUser);
                        break;
                    case 3:
                        performDeposit(currentUser);
                        break;
                    case 4:
                        performTransfer(currentUser);
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    private void showTransactionHistory(User user) {
        System.out.println("Transaction History:");
        for (Transaction transaction : user.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private void performWithdraw(User user) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful. Current balance: " + user.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void performDeposit(User user) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        user.deposit(amount);
        System.out.println("Deposit successful. Current balance: " + user.getBalance());
    }

    private void performTransfer(User user) {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.next();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        User recipient = bank.getUser(recipientId);
        if (recipient != null && user.withdraw(amount)) {
            recipient.deposit(amount);
            user.addTransaction("Transfer to " + recipientId, amount, user.getBalance());
            recipient.addTransaction("Transfer from " + user.getUserId(), amount, recipient.getBalance());
            System.out.println("Transfer successful. Current balance: " + user.getBalance());
        } else {
            System.out.println("Transfer failed. Check recipient ID and balance.");
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private Date date;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return date + " - " + type + ": ₹" + amount + " (Balance: ₹" + balanceAfter + ")";
    }
}

class Bank {
    private Map<String, User> users;

    public Bank() {
        this.users = new HashMap<>();
        addUser(new User("user1", "1234", 1000.0));
        addUser(new User("user2", "5678", 2000.0));
        addUser(new User("user3", "9101", 3000.0));
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.validatePin(pin)) {
            return user;
        }
        return null;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}

public class task3 {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ATM atm = new ATM(bank);
        atm.start();
    }
}