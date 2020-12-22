import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddressBookService {

    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    private List<Person> addressBookDataList;
    private AddressBookDBService addressBookDBService;

    public AddressBookService(){
        addressBookDBService  = AddressBookDBService.getInstance();
    }


    public AddressBookService(List<Person> addressBookDataList){
        this();
        this.addressBookDataList = addressBookDataList;
    }

    public static void main(String[] args) {
        ArrayList<Person> addressBookList = new ArrayList<>();
        AddressBookService addressBookService = new AddressBookService(addressBookList);
        Scanner consoleInputReader = new Scanner(System.in);
        addressBookService.readAddressBookData(consoleInputReader);
    }

    private void readAddressBookData(Scanner consoleInputReader) {
        System.out.println("Enter ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter first Name: ");
        String firstname = consoleInputReader.next();
        System.out.println("Enter last Name: ");
        String lastname = consoleInputReader.next();
        System.out.println("Enter address: ");
        String address = consoleInputReader.next();
        System.out.println("Enter city: ");
        String city = consoleInputReader.next();
        System.out.println("Enter State: ");
        String state = consoleInputReader.next();
        System.out.println("Enter zip: ");
        int zip = consoleInputReader.nextInt();
        System.out.println("Enter phone: ");
        int phone = consoleInputReader.nextInt();
        System.out.println("Enter email: ");
        String email = consoleInputReader.next();
        System.out.println("Enter type: ");
        String type = consoleInputReader.next();
        addressBookDataList.add(new Person(id,firstname,lastname, address, city, state, zip, phone, email, type));
    }

    public List<Person> readAddressBookServiceData(IOService ioService) throws AddressBookException {
        if(ioService.equals(IOService.DB_IO))
            this.addressBookDataList = addressBookDBService.readData();
        return this.addressBookDataList;
    }

    public List<Person> readAddressBookForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) throws AddressBookException {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getAddressBookContactDateRange(startDate, endDate);
        return null;
    }

    public Map<String, Double> readCountByCity(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getCountByCity();
        return null;
    }

    public Map<String, Double> readCountByState(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getCountByState();
        return null;
    }

    public void addContactToBook(String firstname, String lastname, String address, String city, String state, int zip, int phone, String email, String type) {
        addressBookDataList.add(addressBookDBService.addContactToBook(firstname, lastname, address, city, state, zip, phone, email, type));
    }

    public boolean checkAddressBookInSyncWithDB(String name) {
        List<Person> addressBookDataList = addressBookDBService.getAddressBookData(name);
        return addressBookDataList.get(0).equals(getAddressBookData(name));
    }

    private Person getAddressBookData(String name) {
        return this.addressBookDataList.stream()
                .filter(addressBookDataItem -> addressBookDataItem.firstname.equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateContactAddress(String name, String address) {
        int result = addressBookDBService.updateContactData(name,address);
        if(result == 0) return;
        Person contactData = this.getAddressBookData(name);
        if(contactData != null) contactData.address = address;
    }

    public void removeAddressContactFromDB(String name) throws AddressBookException {
        addressBookDBService.removeContactFromDB(name);
    }

    public int getNoOfActiveContacts() throws AddressBookException {
        return addressBookDBService.getNoOfActiveContactfromDB();
    }

}
