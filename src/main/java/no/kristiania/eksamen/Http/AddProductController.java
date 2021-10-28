package no.kristiania.eksamen.Http;


import no.kristiania.eksamen.product.Product;
import no.kristiania.eksamen.product.ProductDao;

import java.sql.SQLException;
import java.util.Map;

public class AddProductController implements HttpController {

    private final ProductDao productDao;

    public AddProductController (ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);

        String category = queryMap.get("category");
        String categoryToString = "";

        Product product = new Product();
        product.setName(queryMap.get("productName"));
        product.setPrice(queryMap.get("productPrice"));
        product.setDescription(queryMap.get("productDescription"));
        product.setCategory(categoryToString);
        product.setCategory(category);

        productDao.save(product);

        return new HttpMessage("HTTP/1.1 200 OK", "K");
    }
}
