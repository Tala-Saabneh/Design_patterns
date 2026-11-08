/*
 * Singleton - Example 2: Application configuration manager shared by modules,
 * created lazily and safe under multiple threads.
 *
 * Run (JDK 11+):   java Example2ConfigManager.java
 * Or compile:      javac Example2ConfigManager.java && java Example2ConfigManager
 *
 * It uses the same two steps every Singleton shares:
 *   1. the constructor is hidden from clients (private)
 *   2. a static creation method (getInstance) returns the cached object
 *
 * Several "modules" (a logger, a mailer, worker threads) all ask for the
 * configuration. They all receive the SAME object, so a change made by one
 * module is visible to the others - that is the point of a shared resource.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Example2ConfigManager {

    public static void main(String[] args) throws InterruptedException {
        // 1) Same object from different parts of the program.
        ConfigManager loggerConfig = ConfigManager.getInstance();
        ConfigManager mailerConfig = ConfigManager.getInstance();
        System.out.println("Same instance -> " + (loggerConfig == mailerConfig));

        // 2) A change through one reference is visible through the other.
        new Logger().log("before change");
        loggerConfig.set("language", "ar");
        loggerConfig.set("debug", "true");
        new Logger().log("after change");
        new Mailer().send();

        // 3) Thread-safety: 20 threads race to call getInstance for the first time.
        ConfigManager.resetForDemo();
        final ConfigManager[] results = new ConfigManager[20];
        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> results[index] = ConfigManager.getInstance());
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        long distinct = java.util.Arrays.stream(results)
                .mapToInt(System::identityHashCode).distinct().count();
        System.out.println("Threads started        -> " + threads.length);
        System.out.println("Distinct instances     -> " + distinct);
        System.out.println("Constructor ran        -> " + ConfigManager.getCreationCount() + " time(s)");
    }
}

class ConfigManager {
    private static volatile ConfigManager instance;
    private static final AtomicInteger creationCount = new AtomicInteger(0);

    private final Map<String, String> settings = new HashMap<>();

    // Private constructor: clients cannot call `new ConfigManager()`.
    private ConfigManager() {
        creationCount.incrementAndGet();
        try {
            Thread.sleep(50); // pretend that loading the config file is slow
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        settings.put("language", "en");
        settings.put("debug", "false");
    }

    public static ConfigManager getInstance() {
        if (instance == null) {                      // fast path, no lock
            synchronized (ConfigManager.class) {
                if (instance == null) {              // re-check inside lock
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public String get(String key) {
        return settings.get(key);
    }

    public void set(String key, String value) {
        settings.put(key, value);
    }

    // ---- demo/testing helpers only: a real Singleton would not expose these.
    static void resetForDemo() {
        synchronized (ConfigManager.class) {
            instance = null;
            creationCount.set(0);
        }
    }

    static int getCreationCount() {
        return creationCount.get();
    }
}

// --- Clients: they never construct ConfigManager themselves ----------------
class Logger {
    void log(String message) {
        ConfigManager config = ConfigManager.getInstance();
        String prefix = config.get("debug").equals("true") ? "[DEBUG] " : "";
        System.out.println(prefix + "Logger (" + config.get("language") + "): " + message);
    }
}

class Mailer {
    void send() {
        ConfigManager config = ConfigManager.getInstance();
        System.out.println("Mailer is using language '" + config.get("language") + "'");
    }
}
