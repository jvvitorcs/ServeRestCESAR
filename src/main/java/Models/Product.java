package Models;

import org.json.simple.JSONObject;

public class Product {
    public String id;
    public String name;
    public String price;
    public String description;
    public String quantity;

    public Product(String name, String price, String description, String quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public void setProductID(String id){this.id = id;}

    public String getProductID(){
        return this.id;
    }

    public String getProductInformation() {
        JSONObject productJsonRepresentation = new JSONObject();
        productJsonRepresentation.put("nome", this.name);
        productJsonRepresentation.put("preco", this.price);
        productJsonRepresentation.put("descricao", this.description);
        productJsonRepresentation.put("quantidade", this.quantity);
        return productJsonRepresentation.toJSONString();
    }
}
