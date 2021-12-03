package scooter.qa;

import io.restassured.response.Response;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {

    @Test
    public void getAvailableOrdersTest () {
        MethodsForOrder ordersList = new MethodsForOrder();
        Response response = ordersList.getAvailableOrders();
        response.then().assertThat().body("orders", notNullValue()).and().statusCode(SC_OK);
    }
}
