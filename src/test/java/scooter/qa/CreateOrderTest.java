package qa;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    public final static String TYPE = "Content-type";
    public final static String APP = "application/json";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    private final String testDataFilePath;

        public CreateOrderTest(String testDataFilePath) {
            this.testDataFilePath = testDataFilePath;
        }

        @Parameterized.Parameters
        public static Object[] testDataSet() {
            return new Object[][] {
                    {"src/test/resources/createOrderWithGrayScooter.json"},
                    {"src/test/resources/createOrderWithBlackScooter.json"},
                    {"src/test/resources/createOrderWithGrayAndBlackScooter.json"},
                    {"src/test/resources/createOrderWithNoColorScooter.json"}
            };
        }

        @Test
        @Step("Проверка на указание цветов самоката в заказе")
        public void createOrderPositiveTest() {
            File json = new File(testDataFilePath);

         given()
        .header(TYPE, APP)
        .body(json)
        .when()
        .post("/api/v1/orders")
        .then()
        .assertThat().body("track", is(not(0)))
        .statusCode(201);
        }
}
