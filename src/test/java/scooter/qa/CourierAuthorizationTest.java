package scooter.qa;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierAuthorizationTest {

    private MethodsForCourier methodsForCourier;

    @Before
    public void setUp () {
        methodsForCourier = new MethodsForCourier();
    }

    @Test
    public void correctCourierAuthorizationTest() {
        CourierDataForRegistration courierDataForRegistration = CourierDataForRegistration.getRandomDataForRegistration();
        methodsForCourier.createNewCourier(courierDataForRegistration);
        Response response = methodsForCourier.courierAuthorization(CourierDataForAuthorization.from(courierDataForRegistration));
        int courierId = methodsForCourier.returnCourierId(CourierDataForAuthorization.from(courierDataForRegistration));
        response.then().assertThat().body("id", equalTo(courierId)).and().statusCode(SC_OK);
        methodsForCourier.deleteCourier(courierId);
    }
    @Test
    public void courierAuthorizationWithIncorrectDataTest() {
        CourierDataForAuthorization courierDataForAuthorization = CourierDataForAuthorization.getRandomDataForAuthorization();
        Response response = methodsForCourier.courierAuthorization(courierDataForAuthorization);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }
    @Test
    public void courierAuthorizationWithoutLoginTest() {
        JSONObject jo = new JSONObject();
        jo.put("password", "Passw0rdRand0m692740");
        Response response = methodsForCourier.courierAuthorizationWithIncorrectData(jo.toString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }
    @Test(timeout = 10000)
    public void courierAuthorizationWithoutPasswordTest() {
        JSONObject jo = new JSONObject();
        jo.put("login", "L0ginRand0m598574");
        Response response = methodsForCourier.courierAuthorizationWithIncorrectData(jo.toString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }
}
