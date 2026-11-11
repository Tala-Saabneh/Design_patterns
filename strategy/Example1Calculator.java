/*
 * Strategy - Example 1: Arithmetic operations (Strategy / Context)
 *
 * This is the exact example from the explanation (Pseudocode section).
 *
 * Run (JDK 11+):   java Example1Calculator.java
 * Or compile:      javac Example1Calculator.java && java Example1Calculator
 *
 * Optional: pass your own input   java Example1Calculator.java 12 4 multiplication
 *           (first number, second number, action: addition | subtraction | multiplication)
 *
 * Roles:
 *   Strategy interface  -> Strategy
 *   Concrete Strategies -> ConcreteStrategyAdd, ConcreteStrategySubtract,
 *                          ConcreteStrategyMultiply
 *   Context             -> Context
 *   Client              -> ExampleApplication
 */
public class Example1Calculator {

    public static void main(String[] args) {
        ExampleApplication app = new ExampleApplication();

        if (args.length == 3) {
            app.run(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
            return;
        }

        // Simulates the user input of the pseudocode: two numbers + an action.
        int first = 12;
        int second = 4;
        String[] actions = {"addition", "subtraction", "multiplication", "division"};
        for (String action : actions) {
            app.run(first, second, action);
        }
    }
}

// ---------------------------------------------------------------------------
// The strategy interface declares operations common to all supported versions
// of some algorithm. The context uses this interface to call the algorithm.
// ---------------------------------------------------------------------------
interface Strategy {
    int execute(int a, int b);
}

// ---------------------------------------------------------------------------
// Concrete strategies implement the algorithm while following the base
// strategy interface. The interface makes them interchangeable in the context.
// ---------------------------------------------------------------------------
class ConcreteStrategyAdd implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}

class ConcreteStrategySubtract implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
}

class ConcreteStrategyMultiply implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
}

// ---------------------------------------------------------------------------
// The context maintains a reference to one of the strategy objects. It doesn't
// know the concrete class of a strategy: it works with all of them through the
// strategy interface, and delegates the work instead of implementing several
// versions of the algorithm itself.
// ---------------------------------------------------------------------------
class Context {
    private Strategy strategy;

    // The setter lets the strategy be switched at runtime.
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int a, int b) {
        return strategy.execute(a, b);
    }
}

// ---------------------------------------------------------------------------
// The client code picks a concrete strategy and passes it to the context.
// The client must know the differences between strategies to choose properly.
// ---------------------------------------------------------------------------
class ExampleApplication {

    void run(int firstNumber, int secondNumber, String action) {
        Context context = new Context();

        if (action.equals("addition")) {
            context.setStrategy(new ConcreteStrategyAdd());
        }
        if (action.equals("subtraction")) {
            context.setStrategy(new ConcreteStrategySubtract());
        }
        if (action.equals("multiplication")) {
            context.setStrategy(new ConcreteStrategyMultiply());
        }

        try {
            int result = context.executeStrategy(firstNumber, secondNumber);
            System.out.println(firstNumber + " " + action + " " + secondNumber + " = " + result);
        } catch (NullPointerException e) {
            // No strategy was selected for this action.
            System.out.println("Unknown action: " + action);
        }
    }
}
