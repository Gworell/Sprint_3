package qa;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class CreateNewCourierPositiveTest {

    public final static String APIURL = "api/v1/courier/";
    public final static String TYPE = "Content-type";
    public final static String APP = "application/json";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown() {
        File json = new File("src/test/resources/registredCourier.json");
        int courierId = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/v1/courier/login")
                .then()
                .assertThat().body("id", is(not(0)))
                .statusCode(200)
                .extract()
                .path("id");

        Response response = given()
                .when()
                .delete("api/v1/courier/" + courierId);
    }

    @Test
    @Step("Успешная регистрация курьера в системе")
    public void createNewCourierPositiveTest() {
        File json = new File("src/test/resources/courierForRegistration.json");

        boolean ok = given()
                .header(TYPE, APP)
                .body(json)
                .when()
                .post(APIURL)
                .then()
                .assertThat()
                .and()
                .statusCode(201)
                .extract()
                .path("ok");
        assertTrue(ok);
    }

    @Test
    @Step("Проверка повторной регистрации зарегистрированного курьера")
    public void reRegistrationNewCourierTest() {
        File json = new File("src/test/resources/courierForRegistration.json");
                given()
                .header(TYPE, APP)
                .body(json)
                .when()
                .post(APIURL);

        Response reRegistration = given()
                .header(TYPE, APP)
                .body(json)
                .when()
                .post(APIURL);
        reRegistration.then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
}
