package scooter.qa;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsForCourier extends RestClient {

    private static final String ENDPOINT = "/api/v1/courier/";

    @Step("Создание нового курьера.")
    public Response createNewCourier (CourierDataForRegistration registrationData) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(registrationData)
                .when()
                .post(ENDPOINT);
    }

    @Step("Создание нового курьера.")
    public Response createNewCourierWithIncorrectData (String registrationData) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(registrationData)
                .when()
                .post(ENDPOINT);
    }

    @Step("Получение ID курьера.")
    public int returnCourierId (CourierDataForAuthorization authorizationData) {
        return given()
                .spec(getBaseSpec())
                .body(authorizationData)
                .when()
                .post(ENDPOINT + "login")
                .then()
                .extract()
                .path("id");
    }

    @Step("Авторизация в системе.")
    public Response courierAuthorization (CourierDataForAuthorization authorizationData) {
        return given()
                .spec(getBaseSpec())
                .body(authorizationData)
                .when()
                .post(ENDPOINT + "login");
    }

    @Step("Авторизация в системе.")
    public Response courierAuthorizationWithIncorrectData (String incorrectBody) {
        return given()
                .spec(getBaseSpec())
                .body(incorrectBody)
                .when()
                .post(ENDPOINT + "login");
    }

    @Step("Удаление курьера из системы.")
    public Response deleteCourier (int courierId) {
        Response delete = null;
        if (courierId != 0) {
            delete = given()
                    .spec(getBaseSpec())
                    .when()
                    .delete(ENDPOINT + courierId);
        }
        return delete;
    }
}
