import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddressBook_JavaStream_Test {
    @Test
    public void givenAddressBookInDB_whenRetrieved_ShouldMatchDataCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookData = addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(6,addressBookData.size());
        System.out.println("Answer found");
    }

    @Test
    public void givenNewAddressForContact_WhenUpdated_ShouldSyncWithDB() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookData = addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        addressBookService.updateContactAddress("Murali", "Nth-Cross");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Murali");
        Assert.assertTrue(result);
        System.out.println("Answer found");
    }

    @Test
    public void givenContactName_ShouldRemove_AddressBookData_FromDataBase() throws AddressBookException{
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        addressBookService.removeAddressContactFromDB("hello");
        int noOfContacts = addressBookService.getNoOfActiveContacts();
        Assert.assertEquals(6,noOfContacts);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchAddressBook_DataCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018,01,01);
        LocalDate endDate = LocalDate.now();
        List<Person> addressBookData = addressBookService.readAddressBookForDateRange(AddressBookService.IOService.DB_IO, startDate, endDate);
        Assert.assertEquals(7, addressBookData.size());
    }

    @Test
    public void givenAddressBookData_whenCountRetrieveByCity_ShouldReturnProperValue() throws AddressBookException{
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        Map<String, Double> countByCity = addressBookService.readCountByCity(AddressBookService.IOService.DB_IO);
        Assert.assertTrue(countByCity.get("trichy").equals(3.0) && countByCity.get("bangalore").equals(1.0));
    }

    @Test
    public void givenAddressBookData_whenCountRetrieveByState_ShouldReturnProperValue() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        Map<String, Double> countByCity = addressBookService.readCountByState(AddressBookService.IOService.DB_IO);
        Assert.assertTrue(countByCity.get("tamilnadu").equals(3.0) && countByCity.get("karnataka").equals(1.0));
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        addressBookService.addContactToBook("Mark", "rex", "firstcross", "bangalore", "karnataka", 456, 8934, "mark@abc.com", "family");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Mark");
        Assert.assertTrue(result);
    }

    @Test
    public void given6Contacts_WhenAddedToDB_ShouldMatchAddressBook_ContactEntries() throws AddressBookException {
        Person[] arrayOfContacts = {
                new Person(0, "Jeff", "Bezos", "1st-cross", "mumbai", "maharashtra", 234, 99865, "jeff@abc.com", "friends"),
                new Person(0, "Bill", "Gates", "2nd-cross", "bangalore", "karnataka", 567, 991133, "bill@abc.com", "friends"),
                new Person(0, "Mark", "Zuckerberg", "3rd-cross", "hyderabad", "andhrapradesh", 123, 92307, "mark@abc.com", "friends"),
                new Person(0, "Sundar", "Pichai", "4th-cross", "bangalore", "karnataka", 734, 92100, "sundar@abc.com", "family"),
                new Person(0, "Mukesh", "Ambani", "5th-cross", "chennai", "tamilnadu", 937, 998877, "mukesh@abc.com", "family"),
                new Person(0, "Anil", "rex", "6th-cross", "mumbai", "maharashtra", 111, 999999, "anil@abc.com", "friends")
        };
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        Instant start = Instant.now();
        addressBookService.addContactsToAddressBook(Arrays.asList(arrayOfContacts));
        Instant end = Instant.now();
        System.out.println("Duration without Thread : "+ Duration.between(start,end));
        Instant threadStart = Instant.now();
        addressBookService.addContactsToAddressBookWithThreads(Arrays.asList(arrayOfContacts));
        Instant threadEnd = Instant.now();
        System.out.println("Duration With Threads: "+Duration.between(threadStart, threadEnd));
        addressBookService.printData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(19, addressBookService.countEntries(AddressBookService.IOService.DB_IO));
    }
}
