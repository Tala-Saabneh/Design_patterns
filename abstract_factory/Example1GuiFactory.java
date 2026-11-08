/*
 * Abstract Factory - Example 1: Cross-platform UI elements (GUIFactory)
 *
 * This is the exact example from the explanation (Pseudocode section).
 *
 * Run (JDK 11+):   java Example1GuiFactory.java
 * Or compile:      javac Example1GuiFactory.java && java Example1GuiFactory
 *
 * Roles:
 *   Abstract Products  -> Button, Checkbox
 *   Concrete Products  -> WinButton, MacButton, WinCheckbox, MacCheckbox
 *   Abstract Factory   -> GUIFactory
 *   Concrete Factories -> WinFactory, MacFactory
 *   Client             -> Application (talks only to abstract types)
 *
 * Note: the class that contains main() is declared first so that the
 * single-file launcher ("java Example1GuiFactory.java") can find it.
 */
public class Example1GuiFactory {

    public static void main(String[] args) {
        ApplicationConfigurator configurator = new ApplicationConfigurator();

        System.out.println("--- Config: Windows ---");
        configurator.main("Windows");

        System.out.println("\n--- Config: Mac ---");
        configurator.main("Mac");

        System.out.println("\n--- Config: Linux (unknown) ---");
        try {
            configurator.main("Linux");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}

// ---------------------------------------------------------------------------
// Abstract products: each distinct product of the family has a base interface.
// ---------------------------------------------------------------------------
interface Button {
    void paint();
}

interface Checkbox {
    void paint();
}

// ---------------------------------------------------------------------------
// Concrete products: grouped by variant (Windows / macOS).
// ---------------------------------------------------------------------------
class WinButton implements Button {
    @Override
    public void paint() {
        System.out.println("Render a button in Windows style.");
    }
}

class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Render a button in macOS style.");
    }
}

class WinCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Render a checkbox in Windows style.");
    }
}

class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Render a checkbox in macOS style.");
    }
}

// ---------------------------------------------------------------------------
// Abstract factory: creation methods return ABSTRACT product types.
// ---------------------------------------------------------------------------
interface GUIFactory {
    Button createButton();

    Checkbox createCheckbox();
}

// ---------------------------------------------------------------------------
// Concrete factories: each one creates products of ONE variant only.
// ---------------------------------------------------------------------------
class WinFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

// ---------------------------------------------------------------------------
// Client code: works with factories and products ONLY through abstract types.
// ---------------------------------------------------------------------------
class Application {
    private final GUIFactory factory;
    private Button button;
    private Checkbox checkbox;

    Application(GUIFactory factory) {
        this.factory = factory;
    }

    void createUI() {
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    void paint() {
        button.paint();
        checkbox.paint();
    }
}

// ---------------------------------------------------------------------------
// The application picks the factory type from configuration at start-up.
// ---------------------------------------------------------------------------
class ApplicationConfigurator {

    // Stands in for readApplicationConfigFile().
    private String readApplicationConfigFile(String osName) {
        return osName;
    }

    void main(String osName) {
        String configOS = readApplicationConfigFile(osName);

        GUIFactory factory;
        if (configOS.equals("Windows")) {
            factory = new WinFactory();
        } else if (configOS.equals("Mac")) {
            factory = new MacFactory();
        } else {
            throw new RuntimeException("Error! Unknown operating system.");
        }

        Application app = new Application(factory);
        app.createUI();
        app.paint();
    }
}
