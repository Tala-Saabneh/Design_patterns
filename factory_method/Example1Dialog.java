/*
 * Factory Method - Example 1: Cross-platform dialog (Dialog / Button)
 *
 * This is the exact example from the explanation (Pseudocode section).
 *
 * Run (JDK 11+):   java Example1Dialog.java
 * Or compile:      javac Example1Dialog.java && java Example1Dialog
 *
 * Roles:
 *   Product            -> Button (interface)
 *   Concrete Products  -> WindowsButton, HTMLButton
 *   Creator            -> Dialog (declares the factory method createButton)
 *   Concrete Creators  -> WindowsDialog, WebDialog (override createButton)
 *   Client             -> Application
 */
public class Example1Dialog {

    public static void main(String[] args) {
        Application app = new Application();

        System.out.println("--- Config: Windows ---");
        app.main("Windows");

        System.out.println("\n--- Config: Web ---");
        app.main("Web");

        System.out.println("\n--- Config: Unknown ---");
        try {
            app.main("Linux");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}

// ---------------------------------------------------------------------------
// Product interface: operations that ALL concrete products must implement.
// ---------------------------------------------------------------------------
interface Button {
    void render();

    void onClick(Runnable f);

    void click(); // simulates the user pressing the button
}

// ---------------------------------------------------------------------------
// Concrete products: different implementations of the product interface.
// ---------------------------------------------------------------------------
class WindowsButton implements Button {
    private Runnable handler;

    @Override
    public void render() {
        System.out.println("Render a button in Windows style.");
    }

    @Override
    public void onClick(Runnable f) {
        System.out.println("Bind a native OS click event.");
        this.handler = f;
    }

    @Override
    public void click() {
        handler.run();
    }
}

class HTMLButton implements Button {
    private Runnable handler;

    @Override
    public void render() {
        System.out.println("Return an HTML representation of a button: <button>OK</button>");
    }

    @Override
    public void onClick(Runnable f) {
        System.out.println("Bind a web browser click event.");
        this.handler = f;
    }

    @Override
    public void click() {
        handler.run();
    }
}

// ---------------------------------------------------------------------------
// Creator: declares the factory method AND holds core business logic (render)
// that relies on the product returned by the factory method.
// ---------------------------------------------------------------------------
abstract class Dialog {

    // The factory method. Subclasses provide the implementation.
    abstract Button createButton();

    void closeDialog() {
        System.out.println("Dialog closed.");
    }

    Button render() {
        // Call the factory method to create a product object.
        Button okButton = createButton();
        // Now use the product.
        okButton.onClick(this::closeDialog);
        okButton.render();
        return okButton;
    }
}

// ---------------------------------------------------------------------------
// Concrete creators: override the factory method to change the product type.
// ---------------------------------------------------------------------------
class WindowsDialog extends Dialog {
    @Override
    Button createButton() {
        return new WindowsButton();
    }
}

class WebDialog extends Dialog {
    @Override
    Button createButton() {
        return new HTMLButton();
    }
}

// ---------------------------------------------------------------------------
// Client: picks a creator from the configuration, then works with it only
// through the base Dialog type.
// ---------------------------------------------------------------------------
class Application {
    private Dialog dialog;

    void initialize(String osName) {
        String configOS = osName; // stands in for readApplicationConfigFile()

        if (configOS.equals("Windows")) {
            dialog = new WindowsDialog();
        } else if (configOS.equals("Web")) {
            dialog = new WebDialog();
        } else {
            throw new RuntimeException("Error! Unknown operating system.");
        }
    }

    void main(String osName) {
        initialize(osName);
        Button button = dialog.render();
        button.click(); // simulate the user pressing OK
    }
}
