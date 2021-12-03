package scooter.qa;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierRegistrationTest {

    private MethodsForCourier methodsForCourier;
    private int courierId;

    @Before
    public void setUp() {
        methodsForCourier = new MethodsForCourier();
    }

    @After
    public void tearDown() {
        methodsForCourier.deleteCourier(courierId);
    }

    @Test
    public void createNewCourierTest() {
        CourierDataForRegistration courierDataForRegistration = CourierDataForRegistration.getRandomDataForRegistration();
        Response response = methodsForCourier.createNewCourier(courierDataForRegistration);
        response.then().assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
        courierId = methodsForCourier.returnCourierId(CourierDataForAuthorization.from(courierDataForRegistration));
    }
    @Test
    public void createCourierWithSameParametersTest() {
        CourierDataForRegistration courierDataForRegistration = CourierDataForRegistration.getRandomDataForRegistration();
        methodsForCourier.createNewCourier(courierDataForRegistration);
        courierId = methodsForCourier.returnCourierId(CourierDataForAuthorization.from(courierDataForRegistration));
        Response response = methodsForCourier.createNewCourier(courierDataForRegistration);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }
    @Test
    public void createCourierWithoutLoginTest() {
        JSONObject jo = new JSONObject();
        jo.put("password", "Passw0rdRamd0m7984567");
        jo.put("firstName", "F1rstnameRandome394865");
        Response response = methodsForCourier.createNewCourierWithIncorrectData(jo.toString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    public void createCourierWithoutPasswordTest() {
        JSONObject jo = new JSONObject();
        jo.put("login", "Log1nRand0m98654763");
        jo.put("firstName", "F1rstNameRand0m24579057");
        Response response = methodsForCourier.createNewCourierWithIncorrectData(jo.toString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}