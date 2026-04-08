import java.util.*;

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        while (true) {
            System.out.println("===== Booking History & Reporting =====");
            System.out.println("1. Confirm New Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String room = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    Reservation reservation = new Reservation(id, name, room, price);

                    history.addReservation(reservation);
                    System.out.println("Booking confirmed and stored in history.");
                    break;

                case 2:
                    history.displayAllBookings();
                    break;

                case 3:
                    reportService.generateSummary(history.getAllReservations());
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

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType + " | ₹" + price;
    }
}

// Booking History Class
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void displayAllBookings() {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\nBooking History:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

// Reporting Service Class
class BookingReportService {

    public void generateSummary(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("No data available for report.");
            return;
        }

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();

            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\n===== Booking Report =====");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);

        System.out.println("\nRoom Type Distribution:");
        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + ": " + roomTypeCount.get(type));
        }
    }
}