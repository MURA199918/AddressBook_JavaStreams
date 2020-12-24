import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class AddressBook_REST_AssuredTest {
    private int contactId;

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4000;
        contactId = 2;
    }

    public Response getContactList(){
        Response response = RestAssured.get("/contacts/list");
        return response;
    }

    @Test
    public void onCallingList_ReturnAddressBookList() {
        Response response = getContactList();
        System.out.println("AT FIRST: "+response.asString());
        response.then().body("id", Matchers.hasItems(1,2));
        response.then().body("firstname", Matchers.hasItems("Priya"));
    }

    @Test
    public void givenContacts_OnPost_ShouldReturnAddedContact() {
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
    }
}
