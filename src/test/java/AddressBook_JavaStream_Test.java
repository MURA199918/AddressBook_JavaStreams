import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AddressBook_JavaStream_Test {
    @Test
    public void givenAddressBookInDB_whenRetrieved_ShouldMatchDataCount() throws AddressBookException {
        AddressBookDBService addressBookDBService = new AddressBookDBService();
        List<Person> personList = addressBookDBService.readAddressBookData();
        Assert.assertEquals(6,personList.size());
        System.out.println("Answer found");
    }
}
