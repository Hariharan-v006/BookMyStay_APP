import java.util.*;

public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Single Room", 5);
        inventory.registerRoom("Double Room", 3);
        inventory.registerRoom("Suite Room", 2);

        // Initialize rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom("Single Room", 1, 20, 50));
        rooms.add(new DoubleRoom("Double Room", 2, 35, 80));
        rooms.add(new SuiteRoom("Suite Room", 3, 60, 150));

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);
        System.out.println("Available Rooms:\n");
        searchService.displayAvailableRooms();

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room"));

        System.out.println("Booking Requests in Queue (First-Come-First-Served):\n");
        bookingQueue.displayQueue();
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

class RoomSearchService {
    private RoomInventory inventory;
    private List<Room> rooms;

    public RoomSearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void displayAvailableRooms() {
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getName());
            if (available > 0) {
                System.out.println(room);
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Requested Room: " + roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation processNextRequest() {
        return queue.poll(); // removes and returns the head
    }

    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests in the queue.");
            return;
        }
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}