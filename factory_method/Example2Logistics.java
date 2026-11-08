/*
 * Factory Method - Example 2: Logistics management app (the "Problem" analogy)
 *
 * The first version handled trucks only. Sea transport was added WITHOUT
 * touching the planning logic, by adding a new creator subclass.
 *
 * Run (JDK 11+):   java Example2Logistics.java
 * Or compile:      javac Example2Logistics.java && java Example2Logistics
 *
 * Roles:
 *   Product            -> Transport (interface, declares deliver)
 *   Concrete Products  -> Truck, Ship
 *   Creator            -> Logistics (declares createTransport, owns planDelivery)
 *   Concrete Creators  -> RoadLogistics (returns Truck), SeaLogistics (returns Ship)
 *   Client             -> clientCode()
 */
public class Example2Logistics {

    // Client: works with any Logistics subclass through the base type.
    static void clientCode(Logistics logistics, String cargo) {
        System.out.println("Client: I don't know the creator's class: "
                + logistics.getClass().getSimpleName());
        System.out.println(logistics.planDelivery(cargo));
    }

    public static void main(String[] args) {
        System.out.println("--- App launched with Road logistics ---");
        clientCode(new RoadLogistics(), "Furniture");

        System.out.println("\n--- App launched with Sea logistics ---");
        clientCode(new SeaLogistics(), "Bananas");
    }
}

// ---------------------------------------------------------------------------
// Product interface
// ---------------------------------------------------------------------------
interface Transport {
    String deliver(String cargo);
}

// ---------------------------------------------------------------------------
// Concrete products
// ---------------------------------------------------------------------------
class Truck implements Transport {
    @Override
    public String deliver(String cargo) {
        return "Truck delivers '" + cargo + "' by land in a box container.";
    }
}

class Ship implements Transport {
    @Override
    public String deliver(String cargo) {
        return "Ship delivers '" + cargo + "' by sea in a shipping container.";
    }
}

// ---------------------------------------------------------------------------
// Creator: the primary responsibility is NOT creating transports. It owns the
// business logic (planDelivery) which uses whatever transport the factory
// method returns.
// ---------------------------------------------------------------------------
abstract class Logistics {

    // The factory method.
    abstract Transport createTransport();

    String planDelivery(String cargo) {
        // Core logic written once; it only knows the Transport interface.
        Transport transport = createTransport();
        return transport.deliver(cargo);
    }
}

// ---------------------------------------------------------------------------
// Concrete creators
// ---------------------------------------------------------------------------
class RoadLogistics extends Logistics {
    @Override
    Transport createTransport() {
        return new Truck();
    }
}

class SeaLogistics extends Logistics {
    @Override
    Transport createTransport() {
        return new Ship();
    }
}
