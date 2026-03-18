import java.util.*;

public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Single Room", 5);
        inventory.registerRoom("Double Room", 3);
        inventory.registerRoom("Suite Room", 2);

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom("Single Room", 1, 20, 50));
        rooms.add(new DoubleRoom("Double Room", 2, 35, 80));
        rooms.add(new SuiteRoom("Suite Room", 3, 60, 150));

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room"));

        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(bookingQueue);
    }
}

abstract class Room {
    private String name;
    private int beds;
    private int size;
    private double price;

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

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation pollRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
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
}

class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    public void processBookings(BookingRequestQueue queue) {
        while (!queue.isEmpty()) {
            Reservation res = queue.pollRequest();
            String roomType = res.getRoomType();
            int available = inventory.getAvailability(roomType);
            if (available > 0) {
                String roomId = generateRoomId(roomType);
                allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);
                inventory.updateAvailability(roomType, -1);
                System.out.println("Reservation confirmed for " + res.getGuestName() + ": " + roomType + " [Room ID: " + roomId + "]");
            } else {
                System.out.println("Sorry " + res.getGuestName() + ", no " + roomType + " available.");
            }
        }
    }

    private String generateRoomId(String roomType) {
        int count = allocatedRooms.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType.replaceAll("\\s", "").toUpperCase() + "-" + count;
    }
}