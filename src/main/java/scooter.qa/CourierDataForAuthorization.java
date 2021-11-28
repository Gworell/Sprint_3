package scooter.qa;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierDataForAuthorization {
    public final String login;
    public final String password;

    public CourierDataForAuthorization(String login, String password) {
        this.login = login;
        this.password = password;
    }
    @Step("Получение данных для авторизации.")
    public static CourierDataForAuthorization getRandomDataForAuthorization () {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierDataForAuthorization(login, password);
    }
    @Step("Получение логина и пароля из данных о регистрации.")
    public static CourierDataForAuthorization from (CourierDataForRegistration courierDataForRegistration) {
        return new CourierDataForAuthorization(courierDataForRegistration.login, courierDataForRegistration.password);
    }
}

