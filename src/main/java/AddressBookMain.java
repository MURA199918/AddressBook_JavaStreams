import java.util.*;
import java.util.stream.Collectors;

class Person{
    String firstname;
    String lastname;
    String address;
    String city;
    String state;
    int zip;
    int phone;
    String email;
}

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

    public static void main(String[] args) {
        System.out.println("........Welcome......");

        HashMap<String, AddressBook> AddressBookList= new HashMap<>();

        Scanner sc = new Scanner(System.in);
        int next = 1;
        while(next == 1)
        {
            System.out.println("Choose \n 1. New AddressBook\n 2. Exit");
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    System.out.println("Enter the name of address book ");
                    String nameOfAddressBook = sc.next();

                    System.out.println("Enter Number of Contact persons you wish to add : ");
                    int noOfPerson = sc.nextInt();

                    AddressBook book1 = new AddressBook();

                    for(int i=0;i<noOfPerson;i++) {
                        book1.addPerson(addContacts(book1));
                    }

                    AddressBookList.put(nameOfAddressBook, book1);

                    for(int i=0;i<book1.viewAllPerson().size();i++) {
                        System.out.println(book1.viewAllPerson().get(i).firstname);
                    }

                    int check =1;
                    while(check == 1)
                    {
                        System.out.println("Choose 1.Edit\n 2.Delete\n 3.Exit");
                        int option = sc.nextInt();
                        String nameofperson;
                        switch (option) {
                            case 1:
                                System.out.println("Enter firstname");
                                nameofperson = sc.next();

                                for(int i=0;i<book1.viewAllPerson().size();i++)
                                {
                                    if(nameofperson.equalsIgnoreCase(book1.viewAllPerson().get(i).firstname))
                                    {
                                        System.out.println("Which detail you want to change\n 1.First Name\n 2.Last Name\n"
                                                + "3.Address\n 4. City\n 5.State \n 6. zip \n 7. phone no\n 8.email");
                                        int choose = sc.nextInt();
                                        switch(choose) {
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
                            case 2 :
                                System.out.println("Enter firstname");
                                nameofperson = sc.next();
                                for(int i=0;i<book1.viewAllPerson().size();i++)
                                {
                                    if(nameofperson.equalsIgnoreCase(book1.viewAllPerson().get(i).firstname))
                                    {
                                        book1.viewAllPerson().remove(i);
                                    }
                                }
                                check = 1;
                                break;

                            case 3 :
                                check = 0;
                                break;

                        }
                    }
                    for(int i=0;i<book1.viewAllPerson().size();i++)
                    {
                        System.out.println("Details of "+(i+1)+" Person");
                        System.out.println("First Name: "+book1.viewAllPerson().get(i).firstname);
                        System.out.println("Last Name: "+book1.viewAllPerson().get(i).lastname);
                        System.out.println("Address: "+book1.viewAllPerson().get(i).address);
                        System.out.println("City: "+book1.viewAllPerson().get(i).city);
                        System.out.println("State: "+book1.viewAllPerson().get(i).state);
                        System.out.println("ZIP: "+book1.viewAllPerson().get(i).zip);
                        System.out.println("Phone No: "+book1.viewAllPerson().get(i).phone);
                        System.out.println("Email: "+book1.viewAllPerson().get(i).email);
                    }
                    next = 1;
                    break;

                case 2:
                    System.out.println("Thank You");
                    next = 0;
                    break;
            }
        }
        System.out.println("Enter city name to search for: ");
        String cityName = sc.next();
        AddressBookList.values().forEach(e->{
            e.contactDetails.forEach(s->{
                if(s.city.equals(cityName)){
                    System.out.println(s.firstname);
                }
            });
        });
        System.out.println("Enter State name to search for: ");
        String stateName = sc.next();
        AddressBookList.values().forEach(e->{
            e.contactDetails.forEach(s->{
                if(s.state.equals(stateName)){
                    System.out.println(s.firstname);
                }
            });
        });

        Map<String, String> cityVsPerson = new HashMap<>();
        Map<String, String> stateVsPerson = new HashMap<>();
        System.out.println("Name : city : state");
        AddressBookList.values().forEach(s->{
            s.contactDetails.forEach(sm->{
                {
                    cityVsPerson.put(sm.firstname, sm.city);
                    stateVsPerson.put(sm.firstname, sm.state);
                    System.out.println(sm.firstname+" : "+sm.city+" : "+sm.state);
                }
            });
        });

        List<Person> personList = AddressBookList.values().stream().flatMap(s->s.contactDetails.stream()).collect(Collectors.toList());
        long count = personList.stream().filter(s->s.city.equals(cityName)).count();
        System.out.println("Count in "+cityName+" is "+count);
        List<Person> personList1 = AddressBookList.values().stream().flatMap(s->s.contactDetails.stream()).collect(Collectors.toList());
        long count1 = personList1.stream().filter(s->s.state.equals(stateName)).count();
        System.out.println("Count in "+stateName+" is "+count1);

        List<String> sortListByName = AddressBookList.values().stream().flatMap(s -> s.contactDetails.stream())
                                     .map(st -> st.firstname).sorted().collect(Collectors.toList());
        System.out.println("Sorted by First Names");
        for (String name : sortListByName) {
            AddressBookList.values().forEach(s -> {
                s.contactDetails.forEach(sm -> {
                    if (sm.firstname.equals(name)) {
                        printContacts(sm);
                    }
                });
            });
        }

    }

}
