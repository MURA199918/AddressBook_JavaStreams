import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

    private List<Person> contactList;
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    private AddressBookDBService() {

    }

    public static AddressBookDBService getInstance() {
        if(addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }


    public Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbooksql_service?useSSL=false";
        String userName = "root";
        String password = "muralis@";
        Connection connection;
        System.out.println("connecting to the database:" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("connection is successful!!!!" + connection);
        return connection;
    }

    public List<Person> readData() throws AddressBookException{
        String sql = "SELECT * FROM address_book";
        return this.getAddressBookDataUsingDB(sql);
    }

    private List<Person> getAddressBookDataUsingDB(String sql) throws AddressBookException {
        List<Person> addressBookList = new ArrayList<>();
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        }catch (SQLException | AddressBookException e){
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.RETRIEVAL_PROBLEM);
        }
        return addressBookList;
    }


    public List<Person> getAddressBookData(String name) {
        List<Person> addressBookList = null;
        if(this.addressBookDataStatement == null)
            this.prepareStatementForAddressBookData();
        try{
            addressBookDataStatement.setString(1,name);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookList = this.getAddressBookData(resultSet);
        }catch (SQLException | AddressBookException e){
            e.printStackTrace();
        }
        return addressBookList;
    }

    private List<Person> getAddressBookData(ResultSet resultSet) throws AddressBookException {
        List<Person> addressList = new ArrayList<>();
        try{
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                int zip = resultSet.getInt("zip");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String type = resultSet.getString("type");
                addressList.add(new Person(id, firstname, lastname, address, city, state, zip, phone, email, type));
            }
        }catch (SQLException e){
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.RETRIEVAL_PROBLEM);
        }
        return addressList;

    }

    private void prepareStatementForAddressBookData() {
        try{
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM address_book WHERE firstname = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Person> getAddressBookContactDateRange(LocalDate startDate, LocalDate endDate) throws AddressBookException {
        String sql = String.format("SELECT * FROM address_book WHERE date BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getAddressBookDataUsingDB(sql);
    }


    public Person addContactToBook(String firstname, String lastname, String address, String city, String state, int zip, int phone, String email, String type) {
        int contactId = -1;
        Connection connection = null;
        Person personData = null;
        try{
            connection = this.getConnection();
            connection.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
        try(Statement statement = connection.createStatement()) {
            String sql = String.format("INSERT INTO address_book (firstname, lastname, address, city, state, zip, phone, email, type) "+
                    "VALUES ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s' )", firstname, lastname, address, city, state, zip, phone, email, type);
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) contactId = resultSet.getInt(1);
            }
            personData = new Person(contactId, firstname, lastname, address, city, state, zip, phone, email, type);
        }catch (SQLException e){
            e.printStackTrace();
            try {
                connection.rollback();
                return personData;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return personData;
    }

    public int updateContactData(String name, String address) {
        return this.updateContactDataUsingPreparedStatement(name, address);
    }

    private int updateContactDataUsingPreparedStatement(String name, String address) {
        try(Connection connection = this.getConnection();){
            String sql = "update address_book set address = ? where firstname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,address);
            preparedStatement.setString(2,name);
            int status =  preparedStatement.executeUpdate();
            return status;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void removeContactFromDB(String name) throws AddressBookException {
        String sql = String.format("update address_book set is_active = false where firstname = '%s'", name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(sql);
            if(rowAffected>0){
                System.out.println("Contact Removed");
            }
        }catch (SQLException e){
            throw new AddressBookException(e.getMessage(),AddressBookException.ExceptionType.CONNECTION_PROBLEM);
        }
    }

    public int getNoOfActiveContactfromDB() throws AddressBookException {
        int noOfActiveContacts = 0;
        String sql = "select sum(is_active) from address_book";
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                noOfActiveContacts = resultSet.getInt("sum(is_active)");
            }

        }catch (SQLException e){
            throw new AddressBookException(e.getMessage(),AddressBookException.ExceptionType.CONNECTION_PROBLEM);
        }
        return noOfActiveContacts;
    }
}
