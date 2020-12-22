import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class AddressBook_JavaStream_Test {
    @Test
    public void givenAddressBookInDB_whenRetrieved_ShouldMatchDataCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookData = addressBookService.readAddressBookServiceData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(6,addressBookData.size());
        System.out.println("Answer found");
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
}
