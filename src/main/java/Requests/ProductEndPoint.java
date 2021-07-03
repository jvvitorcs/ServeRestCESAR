package Requests;
import Models.Product;
import Models.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.given;

public class ProductEndPoint extends RequestBase{

    public static Response authenticateProductRequest(RequestSpecification spec, User user, Product product){
        Response productResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        header("Authorization", user.authToken).
                and().
                        body(product.getProductInformation()).
                when().
                        post("produtos");

        product.setProductID(getValueFromResponse(productResponse, "_id"));
        return productResponse;
    }

    public static Response getProductRequest(RequestSpecification spec, String query){
        Response getProductResponse =
                given().
                        spec(spec).
                        when().
                        get("produtos"+query);
        return getProductResponse;
    }

    public static Response registerProductRequest(RequestSpecification spec, Product product){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", product.name);
        userJsonRepresentation.put("price", product.price);
        userJsonRepresentation.put("description", product.description);
        userJsonRepresentation.put("quantity", product.quantity);

        Response registerProductResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                and().
                        body(userJsonRepresentation.toJSONString()).
                when().
                        post("produtos");

        product.setProductID(getValueFromResponse(registerProductResponse, "_id"));
        return registerProductResponse;
    }

    public static Response deleteProductRequest(RequestSpecification spec, User user, Product product) {
        Response deleteProductResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        header("Authorization", user.authToken).
                and().
                        body(product.getProductInformation()).
                when().
                        delete("produtos/" + product.getProductID());

        return deleteProductResponse;
    }
}

