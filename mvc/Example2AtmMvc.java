/*
 * MVC - Example 2: ATM withdrawal (the everyday example of the explanation)
 *
 * Scenario: the user wants to withdraw 100 dinars.
 *   Model       -> Account        (keeps the balance, checks it is enough, deducts)
 *   View        -> AtmView        (amount screen, success / failure message, balance)
 *   Controller  -> AtmController  (receives the request, asks the Model, tells the View)
 *
 * Run (JDK 11+):   java Example2AtmMvc.java
 * Or compile:      javac Example2AtmMvc.java && java Example2AtmMvc
 */
public class Example2AtmMvc {

    public static void main(String[] args) {

        // Balance 500: withdrawing 100 succeeds and the balance becomes 400.
        Account account = new Account(500);
        AtmView view = new AtmView();
        AtmController controller = new AtmController(account, view);

        System.out.println("=== Scenario 1: balance 500, withdraw 100 ===");
        controller.withdraw(100);

        // The same Model is used again: now 450 is more than the balance (400).
        System.out.println("\n=== Scenario 2: same account, withdraw 450 ===");
        controller.withdraw(450);

        // Balance 50: the Model refuses and the View shows a suitable message.
        System.out.println("\n=== Scenario 3: new account with balance 50, withdraw 100 ===");
        AtmController poorController = new AtmController(new Account(50), view);
        poorController.withdraw(100);
    }
}

// ---------------------------------------------------------------------------
// Model: the balance and the rules for changing it.
// ---------------------------------------------------------------------------
class Account {

    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean hasEnough(double amount) {
        return amount > 0 && balance >= amount;
    }

    // Returns true when the withdrawal was done, false when it was refused.
    public boolean withdraw(double amount) {
        if (!hasEnough(amount)) {
            return false;
        }
        balance -= amount;
        return true;
    }
}

// ---------------------------------------------------------------------------
// View: only displays things. No balance rules here.
// ---------------------------------------------------------------------------
class AtmView {

    public void showWithdrawScreen(double amount) {
        System.out.println("[Screen] You asked to withdraw " + amount + " JD");
    }

    public void showSuccess(double amount, double newBalance) {
        System.out.println("[Screen] Success! Please take your " + amount + " JD");
        System.out.println("[Screen] Remaining balance: " + newBalance + " JD");
    }

    public void showFailure(double balance) {
        System.out.println("[Screen] Insufficient balance. Your balance is " + balance + " JD");
    }
}

// ---------------------------------------------------------------------------
// Controller: receives the request, asks the Model to do the operation,
// receives the result, and asks the View to show the right message.
// ---------------------------------------------------------------------------
class AtmController {

    private Account model;
    private AtmView view;

    public AtmController(Account model, AtmView view) {
        this.model = model;
        this.view = view;
    }

    public void withdraw(double amount) {
        view.showWithdrawScreen(amount);

        boolean done = model.withdraw(amount);

        if (done) {
            view.showSuccess(amount, model.getBalance());
        } else {
            view.showFailure(model.getBalance());
        }
    }
}
