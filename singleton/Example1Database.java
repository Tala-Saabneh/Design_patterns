/*
 * Singleton - Example 1: Database connection (getInstance)
 *
 * This is the exact example from the explanation (Pseudocode section),
 * including the double-checked locking for multithreaded environments.
 *
 * Run (JDK 11+):   java Example1Database.java
 * Or compile:      javac Example1Database.java && java Example1Database
 *
 * Roles:
 *   Singleton -> Database
 *       - private static field `instance`
 *       - private constructor
 *       - public static creation method `getInstance()`
 *   Client    -> Application
 */
public class Example1Database {

    public static void main(String[] args) {
        new Application().main();
    }
}

class Database {
    // The field for storing the singleton instance is static.
    // `volatile` makes double-checked locking safe in Java.
    private static volatile Database instance;

    // The singleton's constructor is private to prevent direct construction
    // calls with the `new` operator. `new Database()` outside this class does
    // not even compile.
    private Database() {
        // Some initialization code, such as the actual connection to a
        // database server.
        System.out.println("Connecting to the database server... (happens only once)");
    }

    // The static method that controls access to the singleton instance.
    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) { // acquireThreadLock()
                // Ensure that the instance hasn't yet been initialized by
                // another thread while this one was waiting for the lock.
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    // Business logic: every query of the app goes through this method, so
    // throttling or caching logic could be placed here.
    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }
}

class Application {
    void main() {
        Database foo = Database.getInstance();
        foo.query("SELECT * FROM users");

        Database bar = Database.getInstance();
        bar.query("SELECT * FROM orders");

        // The variable `bar` contains the same object as the variable `foo`.
        System.out.println("foo == bar -> " + (foo == bar));
        System.out.println("hash(foo) == hash(bar) -> "
                + (System.identityHashCode(foo) == System.identityHashCode(bar)));

        // Database wrong = new Database();   // compile error: constructor is private
    }
}
