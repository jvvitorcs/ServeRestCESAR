import Models.Product;
import Models.User;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.ProductEndPoint.*;
import static Requests.UserEndPoint.*;
import static org.hamcrest.Matchers.equalTo;

public class PostProductTest extends TestBase{

    private User validUser, invalidUser, NoAdminUser;
    private Product product, product2;

    @BeforeClass
    public void productGenerateTestData(){
        validUser = new User("Chico", "chico@email.com", "123abc", "true");
        invalidUser = new User("Maria", "maria@email.com", "123", "true");

        NoAdminUser = new User("Jão", "jv@email.com", "123abc", "false");
        product = new Product("Playstation5", "5000", "Um console legal", "5");
        product2 = new Product("Playstation4", "3000", "Um console legal", "5");

        registerUserRequest(SPEC, validUser);
        registerUserRequest(SPEC, NoAdminUser);
        registerUserRequest(SPEC, invalidUser);
        authenticateUserRequest(SPEC, validUser);
        authenticateUserRequest(SPEC, NoAdminUser);
        authenticateUserRequest(SPEC, invalidUser);

        invalidUser.setUserAuthToken("123454321");

    }

    @Test
    public void shouldReturnFailureMessageAndStatus201() {
        Response ProductSucessResponse = authenticateProductRequest(SPEC, validUser, product2);
        ProductSucessResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCESS_REGISTER));
    }

    @Test
    public void shouldReturnFailureMessageAndStatus400() {
        Response ProductFailureResponseProd = authenticateProductRequest(SPEC, validUser, product);
        ProductFailureResponseProd.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_FAILED_EXISTED_PRODUCT));
    }

    @Test
    public void shouldReturnFailureMessageAndStatus401() {
        Response ProductFailResponse = authenticateProductRequest(SPEC, invalidUser, product);
        ProductFailResponse.
                then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_TOKEN));
    }

    @Test
    public void shouldReturnFailureMessageAndStatus403() {
        Response ProductFailAdmin = authenticateProductRequest(SPEC, NoAdminUser, product);
        ProductFailAdmin.
                then().
                assertThat().
                statusCode(403).
                body("message", equalTo(Constants.MESSAGE_FAILED_ADMIN_ROUTE));
    }
}
