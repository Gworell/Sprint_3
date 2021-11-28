package scooter.qa;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private String color;

     public CreateOrderTest(String color){
     this.color=color;
 }
    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {"," + "\n" + "\"color\": [\"BLACK\"]"},
                {"," + "\n" + "\"color\": [\"GREY\"]"},
                {"," + "\n" + "\"color\": [\"BLACK\", \"GREY\"]"},
                {""},
        };
    }
    @Test
    public void createOrdersWithDifferentColorsTest () {
        MethodsForOrder createOrder = new MethodsForOrder();
        Response response = createOrder.createOrderWithColor(color);
        response.then().assertThat().body("track", is(not(0)))
                .and()
                .statusCode(SC_CREATED);
    }
}
