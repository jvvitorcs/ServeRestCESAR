import Models.User;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.UserEndPoint.*;
import static org.hamcrest.Matchers.equalTo;

public class PostUserTest extends TestBase{

    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User ("Jão", "Jão123@gmail.com", "123abc", "true");
        invalidUser = new User ("Felipe", "beltrano@qa.com.br", "123abc", "true");
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }

    @Test
    public void shouldReturnSucessMessageAndStatus201(){
        Response registerSuccessResponse = registerUserRequest(SPEC, validUser);
        registerSuccessResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCESS_USER_REGISTER));
    }

    @Test
    public void shouldReturnFailureMessageAndStatus400(){
        Response registerSuccessResponse = registerUserRequest(SPEC, invalidUser);
        registerSuccessResponse.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_FAILED_USER_EMAIL));
    }
}
