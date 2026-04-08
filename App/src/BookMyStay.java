import java.util.*;

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        Scanner sc = new Scanner(System.in);
        BookingService bookingService = new BookingService();

        while (true) {
            System.out.println("===== Booking with Validation =====");
            System.out.println("1. Book Room");
            System.out.println("2. View Availability");
            System.out.println("3. Exit");
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

                        // Validate & process booking
                        bookingService.bookRoom(id, name, roomType);

                        System.out.println("Booking successful!");

                    } catch (InvalidBookingException e) {
                        System.out.println("Booking Failed: " + e.getMessage());
                    }
                    break;

                case 2:
                    bookingService.displayAvailability();
                    break;

                case 3:
                    System.out.println("Thank you for using BookMyStay!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

// Booking Service (Core Logic + Validation)
class BookingService {

    private Map<String, Integer> roomInventory;

    public BookingService() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
    }

    public void bookRoom(String id, String name, String roomType)
            throws InvalidBookingException {

        // Step 1: Validate input
        validateRoomType(roomType);

        // Step 2: Check availability
        int available = roomInventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No " + roomType + " rooms available.");
        }

        // Step 3: Update inventory safely
        roomInventory.put(roomType, available - 1);
    }

    // Validation logic (Fail-Fast)
    private void validateRoomType(String roomType)
            throws InvalidBookingException {

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException(
                    "Invalid room type! Only Single/Double allowed."
            );
        }
    }

    public void displayAvailability() {
        System.out.println("\nRoom Availability:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + ": " + roomInventory.get(type));
        }
    }
}

// Custom Exception
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}