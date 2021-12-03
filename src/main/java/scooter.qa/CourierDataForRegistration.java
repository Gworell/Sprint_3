package scooter.qa;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierDataForRegistration {

    public final String login;
    public final String password;
    public final String firstName;

    public CourierDataForRegistration(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    @Step("Получение данных для регистрации.")
    public static CourierDataForRegistration getRandomDataForRegistration () {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierDataForRegistration(login, password, firstName);
    }
}
