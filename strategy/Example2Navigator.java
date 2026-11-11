/*
 * Strategy - Example 2: Navigation app (the "Problem" analogy)
 *
 * Every routing algorithm lives in its own class with ONE method, buildRoute.
 * The Navigator (context) only renders the checkpoints on the map and doesn't
 * care which algorithm built them. The active strategy can be switched at
 * runtime, like the buttons of the app's user interface.
 *
 * Run (JDK 11+):   java Example2Navigator.java
 * Or compile:      javac Example2Navigator.java && java Example2Navigator
 *
 * Roles:
 *   Strategy interface  -> RouteStrategy
 *   Concrete Strategies -> RoadStrategy, WalkingStrategy, PublicTransportStrategy,
 *                          CyclingStrategy, SightseeingStrategy
 *   Context             -> Navigator
 *   Client              -> Example2Navigator.main() (plays the role of the UI buttons)
 */
import java.util.Arrays;
import java.util.List;

public class Example2Navigator {

    public static void main(String[] args) {
        String origin = "Birzeit University";
        String destination = "Ramallah City Center";

        Navigator navigator = new Navigator();

        // The client (UI button) passes the desired strategy to the context.
        navigator.setRouteStrategy(new RoadStrategy());
        navigator.buildRoute(origin, destination);

        // Switching the behavior at runtime: the Navigator code doesn't change.
        navigator.setRouteStrategy(new WalkingStrategy());
        navigator.buildRoute(origin, destination);

        navigator.setRouteStrategy(new PublicTransportStrategy());
        navigator.buildRoute(origin, destination);

        navigator.setRouteStrategy(new CyclingStrategy());
        navigator.buildRoute(origin, destination);

        navigator.setRouteStrategy(new SightseeingStrategy());
        navigator.buildRoute(origin, destination);

        // Bonus from "Pros and Cons": because the interface has a single method,
        // a strategy can also be a lambda, without a new class.
        navigator.setRouteStrategy((from, to) -> Arrays.asList(from, "Coffee stop", to));
        navigator.buildRoute(origin, destination);

        // No strategy selected yet.
        System.out.println("\n--- A new navigator without a strategy ---");
        new Navigator().buildRoute(origin, destination);
    }
}

// ---------------------------------------------------------------------------
// Strategy interface: one method that every routing algorithm implements.
// ---------------------------------------------------------------------------
interface RouteStrategy {
    List<String> buildRoute(String origin, String destination);
}

// ---------------------------------------------------------------------------
// Concrete strategies: the same input can give a different route.
// ---------------------------------------------------------------------------
class RoadStrategy implements RouteStrategy {
    @Override
    public List<String> buildRoute(String origin, String destination) {
        return Arrays.asList(origin, "Main Road", "Highway 60", destination);
    }
}

class WalkingStrategy implements RouteStrategy {
    @Override
    public List<String> buildRoute(String origin, String destination) {
        return Arrays.asList(origin, "Pedestrian path", "Old city stairs", destination);
    }
}

class PublicTransportStrategy implements RouteStrategy {
    @Override
    public List<String> buildRoute(String origin, String destination) {
        return Arrays.asList(origin, "Bus stop 12", "Bus 18 to Ramallah", "Central station", destination);
    }
}

class CyclingStrategy implements RouteStrategy {
    @Override
    public List<String> buildRoute(String origin, String destination) {
        return Arrays.asList(origin, "Bike lane", "Park trail", destination);
    }
}

class SightseeingStrategy implements RouteStrategy {
    @Override
    public List<String> buildRoute(String origin, String destination) {
        return Arrays.asList(origin, "Viewpoint", "Historic mosque", "Local museum", destination);
    }
}

// ---------------------------------------------------------------------------
// Context: its primary job is to render a set of checkpoints on the map.
// It delegates the route building to the linked strategy.
// ---------------------------------------------------------------------------
class Navigator {
    private RouteStrategy routeStrategy;

    // Lets clients replace the active strategy at runtime.
    public void setRouteStrategy(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public void buildRoute(String origin, String destination) {
        if (routeStrategy == null) {
            System.out.println("No routing strategy selected.");
            return;
        }
        List<String> checkpoints = routeStrategy.buildRoute(origin, destination);
        renderOnMap(checkpoints);
    }

    private void renderOnMap(List<String> checkpoints) {
        // A lambda has no readable class name, so it gets a friendly label.
        String name = routeStrategy.getClass().isSynthetic()
                ? "Lambda strategy (no class needed)"
                : routeStrategy.getClass().getSimpleName();
        System.out.println("\nStrategy: " + name);
        System.out.println("Map: " + String.join(" -> ", checkpoints));
    }
}
