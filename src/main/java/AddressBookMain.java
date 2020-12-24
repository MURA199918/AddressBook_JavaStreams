import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class AddressBook {
    ArrayList<Person> contactDetails = new ArrayList<Person>();

    public void addPerson(Person pobj) {
        contactDetails.add(pobj);
    }

    public ArrayList<Person> viewAllPerson(){
        return contactDetails;
    }
}

public class AddressBookMain {

    static Scanner sc = new Scanner(System.in);
    public static final String ADDRESSBOOK_CSV_FILE = "C:\\Users\\mural\\IdeaProjects\\AddressBook_JavaStreams\\src\\test\\resources\\addressBook.csv";
    public static final String ADDRESSBOOK_JSON_FILE = "C:\\Users\\mural\\IdeaProjects\\AddressBook_JavaStreams\\src\\test\\resources\\addressBook.json";

    public static Person addContacts(AddressBook book1) {
        Person p1 = new Person();
        System.out.println("Enter First Name:");
        p1.firstname = sc.next();
        boolean result = book1.viewAllPerson().stream().anyMatch(s -> s.firstname.equals(p1.firstname));
        while(result) {
            System.out.println("Entered First name already exist please enter another name");
            p1.firstname = sc.next();
            result = book1.viewAllPerson().stream().anyMatch(s -> s.firstname.equals(p1.firstname));
        }
        System.out.println("Enter the last name: ");
        p1.lastname = sc.next();
        System.out.println("Enter the address");
        p1.address = sc.next();
        System.out.println("Enter the City: ");
        p1.city = sc.next();
        System.out.println("Enter the State: ");
        p1.state = sc.next();
        System.out.println("Enter the zip: ");
        p1.zip = sc.nextInt();
        System.out.println("Enter the mobile number: ");
        p1.phone = sc.nextInt();
        System.out.println("Enter the email: ");
        p1.email = sc.next();

        System.out.println("Thank You");
        return p1;
    }

    public static void printContacts(Person p) {

        System.out.println("\nFirst Name: " + p.firstname);
        System.out.println("Last Name: " + p.lastname);
        System.out.println("Address: " + p.address);
        System.out.println("City: " + p.city);
        System.out.println("State: " + p.state);
        System.out.println("ZIP: " + p.zip);
        System.out.println("Phone No: " + p.phone);
        System.out.println("Email: " + p.email);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("........Welcome......");

        HashMap<String, AddressBook> AddressBookList = new HashMap<>();

        Scanner sc = new Scanner(System.in);
        int next = 1;
        while (next == 1) {
            System.out.println("Choose \n 1. New AddressBook\n 2. Exit");
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    System.out.println("Enter the name of address book ");
                    String nameOfAddressBook = sc.next();

                    System.out.println("Enter Number of Contact persons you wish to add : ");
                    int noOfPerson = sc.nextInt();

                    AddressBook book1 = new AddressBook();

                    for (int i = 0; i < noOfPerson; i++) {
                        book1.addPerson(addContacts(book1));
                    }

                    AddressBookList.put(nameOfAddressBook, book1);

                    for (int i = 0; i < book1.viewAllPerson().size(); i++) {
                        System.out.println(book1.viewAllPerson().get(i).firstname);
                    }

                    int check = 1;
                    while (check == 1) {
                        System.out.println("Choose 1.Edit\n 2.Delete\n 3.Exit");
                        int option = sc.nextInt();
                        String nameofperson;
                        switch (option) {
                            case 1:
                                System.out.println("Enter firstname");
                                nameofperson = sc.next();

                                for (int i = 0; i < book1.viewAllPerson().size(); i++) {
                                    if (nameofperson.equalsIgnoreCase(book1.viewAllPerson().get(i).firstname)) {
                                        System.out.println("Which detail you want to change\n 1.First Name\n 2.Last Name\n"
                                                + "3.Address\n 4. City\n 5.State \n 6. zip \n 7. phone no\n 8.email");
                                        int choose = sc.nextInt();
                                        switch (choose) {
                                            case 1:
                                                System.out.println("New First Name:");
                                                book1.viewAllPerson().get(i).firstname = sc.next();
                                                break;
                                            case 2:
                                                System.out.println("New Last Name:");
                                                book1.viewAllPerson().get(i).lastname = sc.next();
                                                break;
                                            case 3:
                                                System.out.println("New Address:");
                                                book1.viewAllPerson().get(i).address = sc.next();
                                                break;
                                            case 4:
                                                System.out.println("New City:");
                                                book1.viewAllPerson().get(i).city = sc.next();
                                                break;
                                            case 5:
                                                System.out.println("New State:");
                                                book1.viewAllPerson().get(i).state = sc.next();
                                                break;
                                            case 6:
                                                System.out.println("New zip:");
                                                book1.viewAllPerson().get(i).zip = sc.nextInt();
                                                break;
                                            case 7:
                                                System.out.println("New phone no:");
                                                book1.viewAllPerson().get(i).phone = sc.nextInt();
                                                break;
                                            case 8:
                                                System.out.println("New Email:");
                                                book1.viewAllPerson().get(i).email = sc.next();
                                                break;
                                        }
                                    }
                                }
                                check = 1;
                                break;
                            case 2:
                                System.out.println("Enter firstname");
                                nameofperson = sc.next();
                                for (int i = 0; i < book1.viewAllPerson().size(); i++) {
                                    if (nameofperson.equalsIgnoreCase(book1.viewAllPerson().get(i).firstname)) {
                                        book1.viewAllPerson().remove(i);
                                    }
                                }
                                check = 1;
                                break;

                            case 3:
                                check = 0;
                                break;

                        }
                    }
                    for (int i = 0; i < book1.viewAllPerson().size(); i++) {
                        System.out.println("Details of " + (i + 1) + " Person");
                        System.out.println("First Name: " + book1.viewAllPerson().get(i).firstname);
                        System.out.println("Last Name: " + book1.viewAllPerson().get(i).lastname);
                        System.out.println("Address: " + book1.viewAllPerson().get(i).address);
                        System.out.println("City: " + book1.viewAllPerson().get(i).city);
                        System.out.println("State: " + book1.viewAllPerson().get(i).state);
                        System.out.println("ZIP: " + book1.viewAllPerson().get(i).zip);
                        System.out.println("Phone No: " + book1.viewAllPerson().get(i).phone);
                        System.out.println("Email: " + book1.viewAllPerson().get(i).email);
                    }
                    next = 1;
                    break;

                case 2:
                    System.out.println("Thank You");
                    next = 0;
                    break;
            }
        }

        boolean nextOption = true;
        while (nextOption) {
            System.out.println(
                    "Select 1. search a person by city\n 2.search a person by state\n 3. view person and cities\n 4. view person and states\n 5.find head count in city\n 6.find head count in state\n 7.Sorting of entries based on firstname\n 8.Sorting of entries based on city\n 9.Sorting of entries based on state\n 10.Sorting of entries based on zip\n 11.Write Addressbook Data into file\\n 12.Read AddressBook Data From File\\n 13.Write Data to CSV\\n 14.Read Data From CSV\\n 15.Write Data into JSON file\\n 16.Read Data from JSON file\\n 17.Write Data into JSON Server\\n 18.Read Data from JSON Server\\n 19.Exit");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Which city you want to find out:");
                    String cityName = sc.next();
                    AddressBookList.values().forEach(s -> {
                        s.contactDetails.forEach(sm -> {
                            if (sm.city.equals(cityName)) {
                                System.out.println(sm.firstname);
                            }
                        });
                    });
                    nextOption = true;
                    break;
                case 2:
                    System.out.println("Which state you want to find out:");
                    String stateName = sc.next();
                    AddressBookList.values().forEach(s -> {
                        s.contactDetails.forEach(sm -> {
                            if (sm.state.equals(stateName)) {
                                System.out.println(sm.firstname);
                            }
                        });
                    });
                    nextOption = true;
                    break;
                case 3:
                    Map<String, String> cityVsPerson = new HashMap<>();
                    AddressBookList.values().forEach(s -> {
                        s.contactDetails.forEach(sm -> {
                            {
                                cityVsPerson.put(sm.firstname, sm.city);
                                System.out.println(sm.firstname + " : " + sm.city);
                            }
                        });
                    });
                    nextOption = true;
                    break;
                case 4:
                    Map<String, String> stateVsPerson = new HashMap<>();
                    AddressBookList.values().forEach(s -> {
                        s.contactDetails.forEach(sm -> {
                            {
                                stateVsPerson.put(sm.firstname, sm.city);
                                System.out.println(sm.firstname + " : " + sm.state);
                            }
                        });
                    });
                    nextOption = true;
                    break;
                case 5:
                    System.out.println("Find Contact By City name:");
                    String cityName1 = sc.next();
                    List<Person> personList = AddressBookList.values().stream()
                            .flatMap(s -> s.contactDetails.stream()).collect(Collectors.toList());
                    long count = personList.stream().filter(s -> s.city.equals(cityName1)).count();
                    System.out.println("Count in " + cityName1 + " is " + count);
                    nextOption = true;
                    break;
                case 6:
                    System.out.println("Find Contact By State name:");
                    String stateName1 = sc.next();
                    List<Person> personList1 = AddressBookList.values().stream()
                            .flatMap(s -> s.contactDetails.stream()).collect(Collectors.toList());
                    long count1 = personList1.stream().filter(s -> s.state.equals(stateName1)).count();
                    System.out.println("Count in " + stateName1 + " is " + count1);
                    nextOption = true;
                    break;
                case 7:
                    System.out.println("Sorting entries based on firstname:");
                    List<String> personList2 = AddressBookList.values().stream()
                            .flatMap(s -> s.contactDetails.stream()).map(st -> st.firstname).sorted()
                            .collect(Collectors.toList());
                    for (String name : personList2) {
                        AddressBookList.values().forEach(s -> {
                            s.contactDetails.forEach(sm -> {
                                if (sm.firstname.equals(name)) {
                                    printContacts(sm);
                                }
                            });
                        });
                    }
                    nextOption = true;
                    break;
                case 8:
                    System.out.println("Sorting entries based on city:");
                    List<String> personList3 = AddressBookList.values().stream()
                            .flatMap(s -> s.contactDetails.stream()).map(st -> st.city).sorted().distinct()
                            .collect(Collectors.toList());
                    for (String city : personList3) {
                        AddressBookList.values().forEach(s -> {
                            s.contactDetails.forEach(sm -> {
                                if (sm.city.equals(city)) {
                                    printContacts(sm);
                                }
                            });
                        });
                    }
                    nextOption = true;
                    break;
                case 9:
                    System.out.println("Sorting entries based on state:");
                    List<String> personList4 = AddressBookList.values().stream()
                            .flatMap(s -> s.contactDetails.stream()).map(st -> st.state).sorted().distinct()
                            .collect(Collectors.toList());
                    System.out.println(personList4);
                    for (String state : personList4) {
                        AddressBookList.values().forEach(s -> {
                            s.contactDetails.forEach(sm -> {
                                if (sm.state.equals(state)) {
                                    printContacts(sm);
                                }
                            });
                        });
                    }
                    nextOption = true;
                    break;
                case 10:
                    System.out.println("Sorting entries based on zip:");
                    List<Integer> personList5 = AddressBookList.values().stream()
                            .flatMap(s -> s.contactDetails.stream()).map(st -> st.zip).sorted().distinct()
                            .collect(Collectors.toList());
                    for (Integer zip : personList5) {
                        AddressBookList.values().forEach(s -> {
                            s.contactDetails.forEach(sm -> {
                                if (sm.zip == zip) {
                                    printContacts(sm);
                                }
                            });
                        });
                    }
                    nextOption = true;
                    break;
                case 11:
                    System.out.println("Writing into a file");
                    StringBuffer empBuffer = new StringBuffer();
                    AddressBookList.entrySet().forEach(x->{
                        String nameString = x.getKey()+"\n";
                        empBuffer.append(nameString);
                        x.getValue().contactDetails.forEach(y->{
                            String employeeDataString = y.toString().concat("\n");
                            empBuffer.append(employeeDataString);
                        });
                    });

                    try {
                        Files.write(Paths.get("addressBook-file.txt"), empBuffer.toString().getBytes());

                    } catch (IOException e) {
                    }
                    break;
                case 12:
                    System.out.println("Reading from a file");
                    try {
                        Files.lines(new File("addressBook-file.txt").toPath()).map(line -> line.trim()).forEach(line -> System.out.println(line));
                    } catch (IOException e) {
                    }
                    break;
                case 13:
                    System.out.println("Writing into a CSV File");
                    try(Writer writer = Files.newBufferedWriter(Paths.get(ADDRESSBOOK_CSV_FILE));){
                        ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy();
                        columnPositionMappingStrategy.setType(Person.class);

                        String[] columns = new String[]
                                { "FirstName","LastName","Address","City","State","ZIP", "Phone", "Email" };
                        columnPositionMappingStrategy.setColumnMapping(columns);
                        StatefulBeanToCsv<Person> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                                .withMappingStrategy(columnPositionMappingStrategy)
                                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                                .build();
                        AddressBookList.values().forEach(x-> {
                            try {
                                beanToCsv.write(x.contactDetails);
                            } catch (CsvDataTypeMismatchException e) {
                                e.printStackTrace();
                            } catch (CsvRequiredFieldEmptyException e) {
                                e.printStackTrace();
                            }
                        });

                    }
                    break;
                case 14:
                    System.out.println("Reading from a CSV File");
                    try(Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_CSV_FILE));){
                        CSVReader csvReader = new CSVReader(reader);
                        List<String[]> records = csvReader.readAll();
                        for (String[] record: records){
                            System.out.println("FirstName: "+record[0]);
                            System.out.println("LastName: "+record[1]);
                            System.out.println("Address: "+record[2]);
                            System.out.println("City: "+record[3]);
                            System.out.println("State: "+record[4]);
                            System.out.println("ZIP: "+record[5]);
                            System.out.println("Phone: "+record[6]);
                            System.out.println("Email: "+record[7]);
                            System.out.println("---------------------");
                        }
                    }
                case 15:
                    System.out.println("Writing into a JSON file");
                    try {
                        List<Person> listOfPersons = new ArrayList<>();
                        AddressBookList.values().forEach(x->x.contactDetails.forEach(y->{
                            listOfPersons.add(y);
                        }));
                        Path filePath = Paths.get(ADDRESSBOOK_JSON_FILE);
                        Gson gson = new Gson();
                        String json = gson.toJson(listOfPersons);
                        FileWriter writer = new FileWriter(String.valueOf(filePath));
                        writer.write(json);
                        writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 16:
                    System.out.println("Reading from a JSON File");
                    Gson gson1 = new Gson();
                    BufferedReader br = new BufferedReader(new FileReader(ADDRESSBOOK_JSON_FILE));
                    Person [] personObj = gson1.fromJson(br,Person[].class);
                    List<Person> csvUserList = Arrays.asList(personObj);
                    for(Person i: csvUserList){
                        System.out.println(i.toString());
                    }
                    break;
                case 17:
                    System.out.println("Writing into a JSON Server");
                    RestAssured.baseURI = "http://localhost";
                    RestAssured.port = 4000;
                    Response response = RestAssured.given()
                            .contentType(ContentType.JSON)
                            .accept(ContentType.JSON)
                            .body("{\"firstname\": \"Lisa\",\"lastname\": \"rex\",\"address\": \"3rd-cross\",\"city\": \"chennai\",\"state\": \"tamilnadu\",\"zip\": \"620019\",\"phone\": \"991166\",\"email\": \"lisa@abc.com\",\"type\": \"family\"}")
                            .when()
                            .post("/contacts/create");
                    String respAsStr = response.asString();
                    JsonObject jsonObject = new Gson().fromJson(respAsStr, JsonObject.class);
                    int id = jsonObject.get("id").getAsInt();
                    response.then().body("id", Matchers.any(Integer.class));
                    response.then().body("firstname", Matchers.is("Lisa"));
                    break;
                case 18:
                    System.out.println("Reading from a JSON Server");
                    RestAssured.baseURI = "http://localhost";
                    RestAssured.port = 4000;
                    int contactId = 2;
                    Response response1 = RestAssured.get("/contacts/list");
                    System.out.println("AT FIRST: "+response1.asString());
                    response1.then().body("id", Matchers.hasItems(1,2));
                    response1.then().body("firstname", Matchers.hasItems("Priya"));
                case 19:
                    System.out.println("Thank You!!!!");
                    nextOption = false;
                    break;
            }
        }
    }

}
