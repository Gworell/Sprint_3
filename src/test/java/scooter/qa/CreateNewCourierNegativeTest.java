package qa;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateNewCourierNegativeTest {

    public final static String APIURL = "api/v1/courier/";
    public final static String TYPE = "Content-type";
    public final static String APP = "application/json";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Проверка регистрации курьера без пароля")
    public void createNewCourierWithNoPasswordTest() {
        File json = new File("src/test/resources/newCourierWithNoPassword.json");
        Response response = given()
                .header(TYPE, APP)
                .body(json)
                .when()
                .post(APIURL);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @Step("Проверка регистрации курьера без логина")
    public void createNewCourierWithNoLoginTest() {
        File json = new File("src/test/resources/newCourierWithNoLogin.json");
        Response response = given()
                .header(TYPE, APP)
                .body(json)
                .when()
                .post(APIURL);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
