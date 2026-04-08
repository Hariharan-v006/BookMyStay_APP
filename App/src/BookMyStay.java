import java.util.*;


public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to BookMyStay Hotel Booking System");
        System.out.println("Version: 1.0\n");

        AddOnServiceManager manager = new AddOnServiceManager();
        Scanner sc = new Scanner(System.in);

        String reservationId = "RES123";

        // Predefined services
        AddOnService breakfast = new AddOnService("S1", "Breakfast", 500);
        AddOnService airportPickup = new AddOnService("S2", "Airport Pickup", 1200);
        AddOnService spa = new AddOnService("S3", "Spa Access", 2000);

        while (true) {
            System.out.println("\n===== Book My Stay - Add-On Services =====");
            System.out.println("1. Add Breakfast");
            System.out.println("2. Add Airport Pickup");
            System.out.println("3. Add Spa Access");
            System.out.println("4. View Selected Services");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, breakfast);
                    System.out.println("Breakfast added.");
                    break;

                case 2:
                    manager.addService(reservationId, airportPickup);
                    System.out.println("Airport Pickup added.");
                    break;

                case 3:
                    manager.addService(reservationId, spa);
                    System.out.println("Spa Access added.");
                    break;

                case 4:
                    manager.displayServices(reservationId);
                    break;

                case 5:
                    System.out.println("Thank you for using BookMyStay!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

// Add-On Service Class
class AddOnService {
    private String serviceId;
    private String serviceName;
    private double price;

    public AddOnService(String serviceId, String serviceName, double price) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + price + ")";
    }
}

// Manager Class
class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServicesMap;

    public AddOnServiceManager() {
        reservationServicesMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (AddOnService s : getServices(reservationId)) {
            total += s.getPrice();
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nSelected Add-On Services:");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}