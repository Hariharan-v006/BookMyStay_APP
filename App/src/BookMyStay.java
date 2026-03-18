public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        Room singleRoom = new SingleRoom("Single Room", 1, 20, 50);
        Room doubleRoom = new DoubleRoom("Double Room", 2, 35, 80);
        Room suiteRoom = new SuiteRoom("Suite Room", 3, 60, 150);

        System.out.println(singleRoom);
        System.out.println("Available: " + singleRoomAvailable + "\n");

        System.out.println(doubleRoom);
        System.out.println("Available: " + doubleRoomAvailable + "\n");

        System.out.println(suiteRoom);
        System.out.println("Available: " + suiteRoomAvailable + "\n");
    }
}

/**
 * Abstract class representing a generic hotel room.
 */
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

/**
 * Concrete room types
 */
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