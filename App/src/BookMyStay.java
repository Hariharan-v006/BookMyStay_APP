import java.util.HashMap;
import java.util.Map;

public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Single Room", 5);
        inventory.registerRoom("Double Room", 3);
        inventory.registerRoom("Suite Room", 2);

        Room singleRoom = new SingleRoom("Single Room", 1, 20, 50);
        Room doubleRoom = new DoubleRoom("Double Room", 2, 35, 80);
        Room suiteRoom = new SuiteRoom("Suite Room", 3, 60, 150);

        displayRoom(singleRoom, inventory);
        displayRoom(doubleRoom, inventory);
        displayRoom(suiteRoom, inventory);

        // Example of updating availability
        inventory.updateAvailability("Single Room", -1); // booked 1 single room
        System.out.println("\nAfter booking 1 Single Room:");
        displayRoom(singleRoom, inventory);
    }

    private static void displayRoom(Room room, RoomInventory inventory) {
        System.out.println(room);
        System.out.println("Available: " + inventory.getAvailability(room.getName()) + "\n");
    }
}

abstract class Room {
    private String name;
    private int beds;
    private int size; // in square meters
    private double price; // per night

    public Room(String name, int beds, int size, double price) {
        this.name = name;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getName() { return name; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " [Beds: " + beds + ", Size: " + size + "sqm, Price: $" + price + "]";
    }
}

class SingleRoom extends Room {
    public SingleRoom(String name, int beds, int size, double price) {
        super(name, beds, size, price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(String name, int beds, int size, double price) {
        super(name, beds, size, price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(String name, int beds, int size, double price) {
        super(name, beds, size, price);
    }
}

class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    public void registerRoom(String roomName, int count) {
        availabilityMap.put(roomName, count);
    }

    public int getAvailability(String roomName) {
        return availabilityMap.getOrDefault(roomName, 0);
    }

    public void updateAvailability(String roomName, int change) {
        availabilityMap.put(roomName, getAvailability(roomName) + change);
    }
}