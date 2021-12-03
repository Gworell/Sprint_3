package scooter.qa;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsForOrder extends RestClient {

    @Step("Получение списка заказов.")
    public Response getAvailableOrders() {
        return given()
                .spec(getBaseSpec())
                .get("/api/v1/orders/");
    }

    @Step("Создание заказа с указанием цвета.")
    public Response createOrderWithColor (String color) {
        String createOrderBody = "{\"firstName\": \"Alex\",\"lastName\": \"Velikanov\",\"address\": \"Moscow, ul. Pohodniy, 3\",\"metroStation\": 4,\"phone\": \"+7 915 327 99 99\",\"rentTime\": 6,\"deliveryDate\": \"2021-06-06\",\"comment\": \" JSON\"";
        return given()
                .spec(getBaseSpec())
                .and()
                .body(createOrderBody + color + "}")
                .when()
                .post("/api/v1/orders");
    }
}
