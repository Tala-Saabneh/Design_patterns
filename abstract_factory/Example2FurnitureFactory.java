/*
 * Abstract Factory - Example 2: Furniture shop simulator (the "Problem" analogy)
 *
 * Family of products: Chair + Sofa + CoffeeTable
 * Variants of the family: Modern, Victorian, ArtDeco
 *
 * Run (JDK 11+):   java Example2FurnitureFactory.java
 * Or compile:      javac Example2FurnitureFactory.java && java Example2FurnitureFactory
 *
 * Roles:
 *   Abstract Products  -> Chair, Sofa, CoffeeTable
 *   Concrete Products  -> {Modern,Victorian,ArtDeco}{Chair,Sofa,CoffeeTable}
 *   Abstract Factory   -> FurnitureFactory
 *   Concrete Factories -> ModernFurnitureFactory, VictorianFurnitureFactory,
 *                         ArtDecoFurnitureFactory
 *   Client             -> FurnitureShop (talks only to abstract types)
 */
public class Example2FurnitureFactory {

    // The application selects the factory from configuration at initialization.
    static FurnitureFactory createFactory(String style) {
        switch (style) {
            case "Modern":
                return new ModernFurnitureFactory();
            case "Victorian":
                return new VictorianFurnitureFactory();
            case "ArtDeco":
                return new ArtDecoFurnitureFactory();
            default:
                throw new RuntimeException("Error! Unknown furniture style: " + style);
        }
    }

    public static void main(String[] args) {
        String[] styles = {"Modern", "Victorian", "ArtDeco"};
        for (String style : styles) {
            System.out.println("--- " + style + " living room ---");
            new FurnitureShop(createFactory(style)).furnishLivingRoom();
            System.out.println();
        }

        System.out.println("--- Unknown style ---");
        try {
            createFactory("Gothic");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}

// ---------------------------------------------------------------------------
// Abstract products
// ---------------------------------------------------------------------------
interface Chair {
    String sitOn();
}

interface Sofa {
    String lieOn();
}

interface CoffeeTable {
    String putOn(String item);
}

// ---------------------------------------------------------------------------
// Concrete products - Modern variant
// ---------------------------------------------------------------------------
class ModernChair implements Chair {
    @Override
    public String sitOn() {
        return "Sitting on a sleek Modern chair.";
    }
}

class ModernSofa implements Sofa {
    @Override
    public String lieOn() {
        return "Lying on a minimalist Modern sofa.";
    }
}

class ModernCoffeeTable implements CoffeeTable {
    @Override
    public String putOn(String item) {
        return "Placing " + item + " on a glass-top Modern coffee table.";
    }
}

// ---------------------------------------------------------------------------
// Concrete products - Victorian variant
// ---------------------------------------------------------------------------
class VictorianChair implements Chair {
    @Override
    public String sitOn() {
        return "Sitting on an ornate Victorian chair.";
    }
}

class VictorianSofa implements Sofa {
    @Override
    public String lieOn() {
        return "Lying on a tufted Victorian sofa.";
    }
}

class VictorianCoffeeTable implements CoffeeTable {
    @Override
    public String putOn(String item) {
        return "Placing " + item + " on a carved-wood Victorian coffee table.";
    }
}

// ---------------------------------------------------------------------------
// Concrete products - ArtDeco variant
// ---------------------------------------------------------------------------
class ArtDecoChair implements Chair {
    @Override
    public String sitOn() {
        return "Sitting on a geometric ArtDeco chair.";
    }
}

class ArtDecoSofa implements Sofa {
    @Override
    public String lieOn() {
        return "Lying on a velvet ArtDeco sofa.";
    }
}

class ArtDecoCoffeeTable implements CoffeeTable {
    @Override
    public String putOn(String item) {
        return "Placing " + item + " on a brass-and-marble ArtDeco coffee table.";
    }
}

// ---------------------------------------------------------------------------
// Abstract factory
// ---------------------------------------------------------------------------
interface FurnitureFactory {
    Chair createChair();

    Sofa createSofa();

    CoffeeTable createCoffeeTable();
}

// ---------------------------------------------------------------------------
// Concrete factories: one per variant
// ---------------------------------------------------------------------------
class ModernFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new ModernChair();
    }

    @Override
    public Sofa createSofa() {
        return new ModernSofa();
    }

    @Override
    public CoffeeTable createCoffeeTable() {
        return new ModernCoffeeTable();
    }
}

class VictorianFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new VictorianChair();
    }

    @Override
    public Sofa createSofa() {
        return new VictorianSofa();
    }

    @Override
    public CoffeeTable createCoffeeTable() {
        return new VictorianCoffeeTable();
    }
}

class ArtDecoFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new ArtDecoChair();
    }

    @Override
    public Sofa createSofa() {
        return new ArtDecoSofa();
    }

    @Override
    public CoffeeTable createCoffeeTable() {
        return new ArtDecoCoffeeTable();
    }
}

// ---------------------------------------------------------------------------
// Client code: never mentions a concrete class
// ---------------------------------------------------------------------------
class FurnitureShop {
    private final FurnitureFactory factory;

    FurnitureShop(FurnitureFactory factory) {
        this.factory = factory;
    }

    void furnishLivingRoom() {
        Chair chair = factory.createChair();
        Sofa sofa = factory.createSofa();
        CoffeeTable table = factory.createCoffeeTable();

        System.out.println(chair.sitOn());
        System.out.println(sofa.lieOn());
        System.out.println(table.putOn("a book"));
        // Every piece matches because it came from the same factory.
        System.out.println("Types -> " + chair.getClass().getSimpleName()
                + " | " + sofa.getClass().getSimpleName()
                + " | " + table.getClass().getSimpleName());
    }
}
