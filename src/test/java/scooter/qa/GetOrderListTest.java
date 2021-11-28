package qa;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class GetOrderListTest {

    public final static String TYPE = "Content-type";
    public final static String APP = "application/json";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";}

    @Test
    @Step("Проверка на получение списка заказов")
    public void getOrderListPositiveTest() {
                String stringResponceBody = given()
                .header(TYPE, APP)
                .when()
                .get("api/v1/orders/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .asString();
                assertTrue(stringResponceBody.contains("rentTime"));
    }
}
