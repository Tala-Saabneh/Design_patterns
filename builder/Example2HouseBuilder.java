/*
 * Builder - Example 2: Building houses (the "Problem" analogy)
 *
 * No telescoping constructor and no subclass for every combination: the
 * construction is split into steps (buildWalls, buildDoor, ...). Different
 * builders implement the same steps differently (wood house / stone castle),
 * and the director decides which steps are executed.
 *
 * Run (JDK 11+):   java Example2HouseBuilder.java
 * Or compile:      javac Example2HouseBuilder.java && java Example2HouseBuilder
 *
 * Roles:
 *   Builder interface  -> HouseBuilder
 *   Concrete Builders  -> WoodenHouseBuilder (House), StoneCastleBuilder (Castle)
 *   Products           -> House, Castle (no common interface)
 *   Director           -> HouseDirector (constructSimpleHouse, constructLuxuryHouse)
 *   Client             -> Example2HouseBuilder.main()
 */
import java.util.ArrayList;
import java.util.List;

public class Example2HouseBuilder {

    public static void main(String[] args) {
        HouseDirector director = new HouseDirector();

        WoodenHouseBuilder woodBuilder = new WoodenHouseBuilder();
        StoneCastleBuilder stoneBuilder = new StoneCastleBuilder();

        // Same steps (simple), different builders -> different products.
        director.constructSimpleHouse(woodBuilder);
        House simpleHouse = woodBuilder.getHouse();
        System.out.println("--- Simple house (wood and glass) ---");
        simpleHouse.describe();

        director.constructSimpleHouse(stoneBuilder);
        Castle simpleCastle = stoneBuilder.getCastle();
        System.out.println("\n--- Simple castle (stone and iron) ---");
        simpleCastle.describe();

        // More steps (luxury): backyard, garage, pool and heating are only
        // built when the director asks for them.
        director.constructLuxuryHouse(woodBuilder);
        House luxuryHouse = woodBuilder.getHouse();
        System.out.println("\n--- Luxury house ---");
        luxuryHouse.describe();

        director.constructLuxuryHouse(stoneBuilder);
        Castle luxuryCastle = stoneBuilder.getCastle();
        System.out.println("\n--- Luxury castle ---");
        luxuryCastle.describe();

        // The client may also skip the director and call only the steps it needs.
        woodBuilder.reset();
        woodBuilder.buildWalls();
        woodBuilder.buildRoof();
        House shed = woodBuilder.getHouse();
        System.out.println("\n--- Custom (no director): only walls and roof ---");
        shed.describe();
    }
}

// ---------------------------------------------------------------------------
// Products: related, but they don't follow a common interface.
// ---------------------------------------------------------------------------
class House {
    private final List<String> parts = new ArrayList<>();

    void add(String part) {
        parts.add(part);
    }

    void describe() {
        System.out.println("House with " + parts.size() + " parts:");
        for (String part : parts) {
            System.out.println("  * " + part);
        }
    }
}

class Castle {
    private final List<String> defenses = new ArrayList<>();

    void add(String part) {
        defenses.add(part);
    }

    void describe() {
        System.out.println("Castle with " + defenses.size() + " parts:");
        for (String part : defenses) {
            System.out.println("  # " + part);
        }
    }
}

// ---------------------------------------------------------------------------
// Builder interface: all the construction steps.
// ---------------------------------------------------------------------------
interface HouseBuilder {
    void reset();

    void buildWalls();

    void buildDoor();

    void buildWindows(int count);

    void buildRoof();

    void buildGarage();

    void buildSwimmingPool();

    void buildHeating();
}

// ---------------------------------------------------------------------------
// Concrete builder 1: wood and glass -> House
// ---------------------------------------------------------------------------
class WoodenHouseBuilder implements HouseBuilder {
    private House house;

    WoodenHouseBuilder() {
        reset();
    }

    @Override
    public void reset() {
        house = new House();
    }

    @Override
    public void buildWalls() {
        house.add("Four wooden walls and a floor");
    }

    @Override
    public void buildDoor() {
        house.add("Wooden door");
    }

    @Override
    public void buildWindows(int count) {
        house.add(count + " glass windows");
    }

    @Override
    public void buildRoof() {
        house.add("Tiled roof");
    }

    @Override
    public void buildGarage() {
        house.add("Garage for one car");
    }

    @Override
    public void buildSwimmingPool() {
        house.add("Backyard swimming pool");
    }

    @Override
    public void buildHeating() {
        house.add("Heating system, plumbing and electrical wiring");
    }

    // The builder hands the finished product to the client and resets itself.
    House getHouse() {
        House product = house;
        reset();
        return product;
    }
}

// ---------------------------------------------------------------------------
// Concrete builder 2: stone and iron -> Castle
// ---------------------------------------------------------------------------
class StoneCastleBuilder implements HouseBuilder {
    private Castle castle;

    StoneCastleBuilder() {
        reset();
    }

    @Override
    public void reset() {
        castle = new Castle();
    }

    @Override
    public void buildWalls() {
        castle.add("Thick stone walls with towers");
    }

    @Override
    public void buildDoor() {
        castle.add("Iron gate with a drawbridge");
    }

    @Override
    public void buildWindows(int count) {
        castle.add(count + " arrow-slit windows");
    }

    @Override
    public void buildRoof() {
        castle.add("Slate battlements");
    }

    @Override
    public void buildGarage() {
        castle.add("Royal stable for horses");
    }

    @Override
    public void buildSwimmingPool() {
        castle.add("Moat filled with water");
    }

    @Override
    public void buildHeating() {
        castle.add("Stone fireplaces in every hall");
    }

    Castle getCastle() {
        Castle product = castle;
        reset();
        return product;
    }
}

// ---------------------------------------------------------------------------
// Director: defines the ORDER (and the choice) of the building steps.
// ---------------------------------------------------------------------------
class HouseDirector {

    void constructSimpleHouse(HouseBuilder builder) {
        builder.reset();
        builder.buildWalls();
        builder.buildDoor();
        builder.buildWindows(2);
        builder.buildRoof();
    }

    void constructLuxuryHouse(HouseBuilder builder) {
        builder.reset();
        builder.buildWalls();
        builder.buildDoor();
        builder.buildWindows(8);
        builder.buildRoof();
        builder.buildGarage();
        builder.buildSwimmingPool();
        builder.buildHeating();
    }
}
