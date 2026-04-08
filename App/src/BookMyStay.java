import java.util.*;

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        Scanner sc = new Scanner(System.in);
        BookingService bookingService = new BookingService();
        CancellationService cancellationService = new CancellationService(bookingService);

        while (true) {
            System.out.println("\n===== Booking & Cancellation System =====");
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Availability");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Reservation ID: ");
                        String id = sc.nextLine();

                        System.out.print("Enter Guest Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Room Type (Single/Double): ");
                        String roomType = sc.nextLine();

                        bookingService.bookRoom(id, name, roomType);
                        System.out.println("Booking successful!");

                    } catch (InvalidBookingException e) {
                        System.out.println("Booking Failed: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Reservation ID to cancel: ");
                        String cancelId = sc.nextLine();

                        cancellationService.cancelBooking(cancelId);
                        System.out.println("Cancellation successful!");

                    } catch (InvalidBookingException e) {
                        System.out.println("Cancellation Failed: " + e.getMessage());
                    }
                    break;

                case 3:
                    bookingService.displayAvailability();
                    break;

                case 4:
                    System.out.println("Thank you for using BookMyStay!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

// Reservation Model
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Reservation> reservations = new HashMap<>();
    private int roomCounter = 1;

    public BookingService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    public void bookRoom(String id, String name, String roomType)
            throws InvalidBookingException {

        validateRoomType(roomType);

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        String roomId = roomType.charAt(0) + String.valueOf(roomCounter++);

        Reservation res = new Reservation(id, name, roomType, roomId);
        reservations.put(id, res);

        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    private void validateRoomType(String roomType)
            throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type!");
        }
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void increaseInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayAvailability() {
        System.out.println("\nRoom Availability:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Cancellation Service (Rollback Logic using Stack)
class CancellationService {

    private BookingService bookingService;

    // Stack for rollback tracking (LIFO)
    private Stack<String> releasedRooms = new Stack<>();

    public CancellationService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void cancelBooking(String reservationId)
            throws InvalidBookingException {

        Reservation res = bookingService.getReservation(reservationId);

        // Validation
        if (res == null) {
            throw new InvalidBookingException("Reservation does not exist.");
        }

        if (res.isCancelled) {
            throw new InvalidBookingException("Booking already cancelled.");
        }

        // Step 1: Push roomId into stack (rollback tracking)
        releasedRooms.push(res.roomId);

        // Step 2: Restore inventory
        bookingService.increaseInventory(res.roomType);

        // Step 3: Mark as cancelled (history update)
        res.isCancelled = true;

        // Controlled rollback complete
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + releasedRooms);
    }
}

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}