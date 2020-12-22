import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Person {
    int id;

    String firstname;

    String lastname;

    String address;

    String city;

    String state;

    int zip;

    int phone;

    String email;

    String type;

    public Person() {
    }

    public Person(int id, String firstname, String lastname, String address, String city, String state, int zip, int phone, String email, String type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.type = type;
    }

    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() { return type;}

    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                zip == person.zip &&
                phone == person.phone &&
                firstname.equals(person.firstname) &&
                lastname.equals(person.lastname) &&
                address.equals(person.address) &&
                city.equals(person.city) &&
                state.equals(person.state) &&
                email.equals(person.email) &&
                type.equals(person.type);
    }

}
