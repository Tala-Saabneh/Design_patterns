/*
 * Builder - Example 1: Cars and car manuals (Builder / Director)
 *
 * This is the exact example from the explanation (Pseudocode section).
 *
 * Run (JDK 11+):   java Example1CarBuilder.java
 * Or compile:      javac Example1CarBuilder.java && java Example1CarBuilder
 *
 * Roles:
 *   Builder interface  -> Builder
 *   Concrete Builders  -> CarBuilder (builds a Car), CarManualBuilder (builds a Manual)
 *   Products           -> Car, Manual (they do NOT share a common interface)
 *   Director           -> Director (constructSportsCar, constructSUV)
 *   Client             -> Application
 *
 * Note: the class that contains main() is declared first so that the
 * single-file launcher ("java Example1CarBuilder.java") can find it.
 */
import java.util.ArrayList;
import java.util.List;

public class Example1CarBuilder {

    public static void main(String[] args) {
        new Application().makeCar();
    }
}

// ---------------------------------------------------------------------------
// Parts used by the products.
// ---------------------------------------------------------------------------
class Engine {
    private final String name;
    private final double volume;

    Engine(String name, double volume) {
        this.name = name;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return name + " (" + volume + "L)";
    }
}

class SportEngine extends Engine {
    SportEngine() {
        super("Sport engine", 3.0);
    }
}

class SUVEngine extends Engine {
    SUVEngine() {
        super("SUV diesel engine", 2.5);
    }
}

// ---------------------------------------------------------------------------
// Products. They are related, although they don't have a common interface.
// ---------------------------------------------------------------------------
class Car {
    private int seats;
    private Engine engine;
    private boolean tripComputer;
    private boolean gps;

    void setSeats(int seats) {
        this.seats = seats;
    }

    void setEngine(Engine engine) {
        this.engine = engine;
    }

    void setTripComputer(boolean tripComputer) {
        this.tripComputer = tripComputer;
    }

    void setGPS(boolean gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        return "Car{seats=" + seats
                + ", engine=" + engine
                + ", tripComputer=" + tripComputer
                + ", gps=" + gps + "}";
    }
}

class Manual {
    private final List<String> lines = new ArrayList<>();

    void addLine(String line) {
        lines.add(line);
    }

    void print() {
        for (String line : lines) {
            System.out.println("  - " + line);
        }
    }
}

// ---------------------------------------------------------------------------
// The builder interface specifies methods for creating the different parts
// of the product objects.
// ---------------------------------------------------------------------------
interface Builder {
    void reset();

    void setSeats(int seats);

    void setEngine(Engine engine);

    void setTripComputer(boolean hasTripComputer);

    void setGPS(boolean hasGPS);
}

// ---------------------------------------------------------------------------
// Concrete builder: builds a Car.
// ---------------------------------------------------------------------------
class CarBuilder implements Builder {
    private Car car;

    // A fresh builder instance contains a blank product object.
    CarBuilder() {
        reset();
    }

    // The reset method clears the object being built.
    @Override
    public void reset() {
        this.car = new Car();
    }

    // All production steps work with the same product instance.
    @Override
    public void setSeats(int seats) {
        car.setSeats(seats);
    }

    @Override
    public void setEngine(Engine engine) {
        car.setEngine(engine);
    }

    @Override
    public void setTripComputer(boolean hasTripComputer) {
        car.setTripComputer(hasTripComputer);
    }

    @Override
    public void setGPS(boolean hasGPS) {
        car.setGPS(hasGPS);
    }

    // The method for retrieving the result lives in the concrete builder, not
    // in the Builder interface, because different builders create different
    // products. After returning the result, the builder resets itself so it is
    // ready to produce another product.
    Car getProduct() {
        Car product = this.car;
        this.reset();
        return product;
    }
}

// ---------------------------------------------------------------------------
// Concrete builder: builds a Manual (same steps, but it DESCRIBES the parts).
// ---------------------------------------------------------------------------
class CarManualBuilder implements Builder {
    private Manual manual;

    CarManualBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.manual = new Manual();
    }

    @Override
    public void setSeats(int seats) {
        manual.addLine("Seats: this car has " + seats + " seats.");
    }

    @Override
    public void setEngine(Engine engine) {
        manual.addLine("Engine: " + engine + ". Check the oil level every month.");
    }

    @Override
    public void setTripComputer(boolean hasTripComputer) {
        if (hasTripComputer) {
            manual.addLine("Trip computer: shows fuel use and average speed.");
        }
    }

    @Override
    public void setGPS(boolean hasGPS) {
        if (hasGPS) {
            manual.addLine("GPS: enter a destination to start navigation.");
        }
    }

    Manual getProduct() {
        Manual product = this.manual;
        this.reset();
        return product;
    }
}

// ---------------------------------------------------------------------------
// The director only executes the building steps in a particular sequence.
// It works with ANY builder passed to it, so the same steps can produce a car
// or a manual.
// ---------------------------------------------------------------------------
class Director {

    void constructSportsCar(Builder builder) {
        builder.reset();
        builder.setSeats(2);
        builder.setEngine(new SportEngine());
        builder.setTripComputer(true);
        builder.setGPS(true);
    }

    void constructSUV(Builder builder) {
        builder.reset();
        builder.setSeats(7);
        builder.setEngine(new SUVEngine());
        builder.setTripComputer(true);
        builder.setGPS(true);
    }
}

// ---------------------------------------------------------------------------
// Client: creates a builder, passes it to the director, starts construction,
// then retrieves the result from the BUILDER (the director doesn't know the
// concrete products).
// ---------------------------------------------------------------------------
class Application {

    void makeCar() {
        Director director = new Director();

        // Sports car
        CarBuilder carBuilder = new CarBuilder();
        director.constructSportsCar(carBuilder);
        Car sportsCar = carBuilder.getProduct();
        System.out.println("--- Sports car ---");
        System.out.println(sportsCar);

        // Its manual: same steps, different builder.
        CarManualBuilder manualBuilder = new CarManualBuilder();
        director.constructSportsCar(manualBuilder);
        Manual sportsManual = manualBuilder.getProduct();
        System.out.println("--- Sports car manual ---");
        sportsManual.print();

        // The same builders are reused (getProduct reset them): now an SUV.
        director.constructSUV(carBuilder);
        Car suv = carBuilder.getProduct();
        System.out.println("\n--- SUV ---");
        System.out.println(suv);

        director.constructSUV(manualBuilder);
        Manual suvManual = manualBuilder.getProduct();
        System.out.println("--- SUV manual ---");
        suvManual.print();
    }
}
