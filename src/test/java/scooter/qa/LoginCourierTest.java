package qa;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


public class LoginCourierTest {

    public final static String APIURL = "api/v1/courier/login/";
    public final static String TYPE = "Content-type";
    public final static String APP = "application/json";
    int courierId = 0;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        File requestRegistrationBodyJson = new File("src/test/resources/courierForRegistration.json");
                given()
                .header(TYPE, APP)
                .body(requestRegistrationBodyJson)
                .when()
                .post("/api/v1/courier");
    }

    @After
    public void tearDown() {
        if (courierId !=0) {
                given()
                .when()
                .delete("api/v1/courier/" + courierId);}
    }

    @Test
    @Step("Проверка успешного логина в систему")
    public void loginIntoSystemPositiveTest() {

        File requestLoginBodyJson = new File("src/test/resources/registredCourier.json");
         courierId = given()
                .header(TYPE, APP)
                .body(requestLoginBodyJson)
                .when()
                .post("api/v1/courier/login/")
                .then()
                .assertThat().body("id", is(not(0)))
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Test(timeout=10000)
    @Step("Проверка входа в систему без указания пароля")
    public void loginIntoSystemWithNoPasswordTest() {
        File requestWithNoPasswordJson = new File("src/test/resources/newCourierWithNoPassword.json");
                given()
                .header(TYPE, APP)
                .body(requestWithNoPasswordJson)
                .when()
                .post(APIURL)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);

    }
    @Test
    @Step("Проверка входа в систему без указания логина")
    public void loginIntoSystemWithNoLoginTest() {
        File requestWithNoLoginJson = new File("src/test/resources/newCourierWithNoLogin.json");
                given()
                .header(TYPE, APP)
                .body(requestWithNoLoginJson)
                .when()
                .post(APIURL)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }
    @Test
    @Step("Проверка входа в систему незарегистрированным курьером")
    public void loginIntoSystemNotRegisteredCourierTest() {
        File notRegisteredCourierJson = new File("src/test/resources/notRegisteredCourier.json");
        given()
                .header(TYPE, APP)
                .body(notRegisteredCourierJson)
                .when()
                .post(APIURL)
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }
}
