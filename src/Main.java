import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import financials.MoneyTransferPlatform;
import financials.PayPal;
import financials.Visa;
import individuals.Citizen;
import individuals.Landlord;
import individuals.Tenant;
import residentials.*;
import serializations.LocalDateSerializer;
import serializations.AbstractClassAdapter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

public class Main {

    private static Gson gson;
    private final static String CITIZENS_FILE_PATH = "src/data/citizens.json";
    private final static String RESIDENCES_FILE_PATH = "src/data/residences.json";
    private final static String RENTAL_LEASES_FILE_PATH = "src/data/rental_leases.json";
    private static final String ClASS_TYPE = "CLASS_TYPE";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeGson();
        createSomeDefaultObjects();
        loadAllFiles();

        MoneyTransferPlatform mtp4 = new PayPal(582,"suzan_lock@gmail.com");
        Citizen tenant4 = new Tenant("Suzan Lock", mtp4);

        System.out.println(tenant4);
        System.out.println(Citizen.getCitizens());


        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createCitizen();
                case 2 -> createResidence();
                case 3 -> rentResidence();
                case 4 -> saveWorkToFiles();
                case 5 -> printCitizens();
                case 6 -> printResidences();
                case 7 -> printRentalLeases();
                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }


    }

    // files interaction
    private static void initializeGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(MoneyTransferPlatform.class, new AbstractClassAdapter());
        gsonBuilder.registerTypeAdapter(Citizen.class, new AbstractClassAdapter());
        gsonBuilder.registerTypeAdapter(Residence.class, new AbstractClassAdapter());
        gson = gsonBuilder.setPrettyPrinting().create();
    }
    private static void createSomeDefaultObjects(){
        MoneyTransferPlatform mtp1 = new PayPal(72,"John_lock@gmail.com");
        Citizen tenant1 = new Tenant("John Lock", mtp1);

        MoneyTransferPlatform mtp2 = new Visa(5100, "cx_1294");
        Citizen landlord1 = new Landlord("Tim Watley", mtp2, "+972578342949");

        Residence residence = new Studio("eta_4587", 90.5, (short) 2, "Ramallah Albireh Berlin st. 84",
                20.0, (short) 2, 1, landlord1.getIdNumber());


        residence.rent(tenant1.getIdNumber(),
                LocalDate.of(2024, 11, 15),
                LocalDate.of(2024, 11, 18));

        saveWorkToFiles();
    }
    private static void loadAllFiles(){
        loadCitizensFromJsonFile();
        loadResidencesFromJsonFile();
        loadLeasesFromJsonFile();
    }
    private static void saveWorkToFiles(){
        saveMapToFile(CITIZENS_FILE_PATH, Citizen.getCitizens(), "Citizens");
        saveMapToFile(RESIDENCES_FILE_PATH, Residence.getResidences(), "Residences");
        saveLeasesToJsonFile();
    }
    private static void saveMapToFile(String filePath, Map<?,?> map, String message){
        String jsonString = gson.toJson(map);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        List<String> modifiedList = new ArrayList<>();
        for (var value : map.values())
            modifiedList.add(value.getClass().getCanonicalName());

        Iterator<String> iterator = modifiedList.iterator();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonObject object = entry.getValue().getAsJsonObject();
            object.addProperty(ClASS_TYPE, iterator.next());
        }

        String modifiedJsonString = gson.toJson(jsonObject);

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(modifiedJsonString);
            writer.close();
            System.out.println(message+" successfully written to JSON file: "+filePath);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void loadCitizensFromJsonFile(){
        try (FileReader reader = new FileReader(CITIZENS_FILE_PATH)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            Type type = new TypeToken<HashMap<Integer, Citizen>>() {}.getType();
            HashMap<Integer, Citizen> citizens = gson.fromJson(jsonObject, type);
            Citizen.setCitizens(citizens);
            System.out.println(Citizen.getCitizens());
            System.out.println("Successfully loaded data from JSON file: " + CITIZENS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void saveLeasesToJsonFile(){
        String jsonString = gson.toJson(RentalLease.getLeases());
        try {
            FileWriter writer = new FileWriter(RENTAL_LEASES_FILE_PATH);
            writer.write(jsonString);
            writer.close();
            System.out.println("Rental leases successfully written to JSON file: "+RENTAL_LEASES_FILE_PATH);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void loadLeasesFromJsonFile(){
        try (FileReader reader = new FileReader(RENTAL_LEASES_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            Type type = new TypeToken<LinkedList<RentalLease>>() {}.getType();
           List<RentalLease> rentalLeases = gson.fromJson(jsonArray, type);
            RentalLease.setLeases(rentalLeases);
            System.out.println(RentalLease.getLeases());
            System.out.println("Successfully loaded data from JSON file: " + RENTAL_LEASES_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void loadResidencesFromJsonFile(){
        try (FileReader reader = new FileReader(RESIDENCES_FILE_PATH)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            Type type = new TypeToken<HashMap<String, Residence>>() {}.getType();
            HashMap<String, Residence> residences = gson.fromJson(jsonObject, type);
            Residence.setResidences(residences);
            System.out.println(Residence.getResidences());
            System.out.println("Successfully loaded data from JSON file: " + RESIDENCES_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // command line user interaction
    private static void printMenu() {
        System.out.println("Welcome to my rental application.");
        System.out.println("Choose an option (number):");
        System.out.println("1. Create Citizen");
        System.out.println("2. Create Residence");
        System.out.println("3. Rent Residence");
        System.out.println("4. Save All Work");
        System.out.println("5. Print Citizens");
        System.out.println("6. Print Residences");
        System.out.println("7. Print Rental Leases");
        System.out.println("8. Exit");
    }

    private static void createCitizen() {
        while (true){
            System.out.println("Enter citizen type (tenant/landlord):");
            String type = scanner.nextLine();

            if (!type.equalsIgnoreCase("tenant") && !type.equalsIgnoreCase("landlord")){
                System.out.println("Invalid citizen type.");
                continue;
            }

            System.out.println("Enter name:");
            String name = scanner.nextLine();

            MoneyTransferPlatform platform = null;
            while (true) {
                System.out.println("Enter online payment account (Paypal/Visa):");
                String platformChoice = scanner.nextLine();

                if (platformChoice.equalsIgnoreCase("Paypal")) {
                    System.out.println("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.println("Enter balance (in dollars): ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();
                    platform = new PayPal(balance, email);
                    break;
                } else if (platformChoice.equalsIgnoreCase("Visa")) {
                    System.out.println("Enter card number (digits & character):");
                    String cardNumber = scanner.nextLine();

                    System.out.println("Enter balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();
                    platform = new Visa(balance, cardNumber);
                    break;
                } else
                    System.out.println("Invalid online payment choice.");
            }
            Citizen citizen = null;
            if (type.equalsIgnoreCase("tenant")) {
                citizen = new Tenant(name, platform);
            } else {
                System.out.println("Enter contact (your email, phone number or any way):");
                String contact = scanner.nextLine();
                citizen = new Landlord(name, platform, contact);
            }
            System.out.println("The provided citizen information: ");
            System.out.println(citizen);
            break;
        }
    }
    private static void createResidence() {

        Residence residence;
        String type;
        while (true) {
            System.out.println("Enter residence type (studio/villa):");
            type = scanner.nextLine();

            if (!type.equalsIgnoreCase("studio") && !type.equalsIgnoreCase("villa"))
                System.out.println("Invalid residence type.");
            else
                break;
        }

        System.out.println("Enter residence ID (characters allowed too):");
        String residenceId = scanner.nextLine();

        System.out.println("Enter residence area (squared meters):");
        double area = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter number of rooms:");
        short numberOfRooms = scanner.nextShort();

        String address = scanner.nextLine();

        System.out.println("Enter the residence daily renting price (dollars):");
        double dailyPrice = scanner.nextDouble();
        scanner.nextLine();


        System.out.println("Enter landlord Id:");
        int landlordId = scanner.nextInt();
        scanner.nextLine();

        if (type.equalsIgnoreCase("studio")) {
            System.out.println("Enter the floor number:");
            short floorNumber = scanner.nextShort();
            scanner.nextLine();

            System.out.println("Enter unit (apartment) number:");
            int unitNumber = scanner.nextInt();
            scanner.nextLine();

            residence =  new Studio(residenceId, area, numberOfRooms, address, dailyPrice,
                                        floorNumber, unitNumber, landlordId);
        } else{
            boolean hasBool;
            while (true) {
                System.out.println("Does it have a pool? (yes or no).");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("yes") || input.equals("y")) {
                    hasBool = true;
                    break;
                } else if (input.equals("no") || input.equals("n")) {
                    hasBool = false;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }

            System.out.println("Enter garden size:");
            double gardenSize = scanner.nextDouble();
            scanner.nextLine();
            residence = new Villa(residenceId, area, numberOfRooms, address,
                                        dailyPrice, hasBool, gardenSize, landlordId);
        }
        System.out.println("The received residence information:");
        System.out.println(residence);
    }

    private static void rentResidence() {
        System.out.println("Enter residence ID:");
        String residenceId = scanner.nextLine();

        if (!Residence.getResidences().containsKey(residenceId)){
            System.out.println("No residence id exist with this id: "+residenceId);
            return;
        }
        Residence residence = Residence.getResidences().get(residenceId);

        System.out.println("Enter tenant ID:");
        int tenantId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter start date (YYYY-MM-DD):");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date (YYYY-MM-DD):");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        residence.rent(tenantId, start, end);
    }

    private static void printCitizens() {
        System.out.println("Citizens:");
        System.out.println(Citizen.getCitizens());
    }

    private static void printResidences() {
        System.out.println("Residences:");
        System.out.println(Residence.getResidences());
    }

    private static void printRentalLeases() {
        System.out.println("Rental Leases:");
        System.out.println(RentalLease.getLeases());
    }

}