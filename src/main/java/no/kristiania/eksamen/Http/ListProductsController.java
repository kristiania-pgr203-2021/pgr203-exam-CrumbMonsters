package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.product.Product;
import no.kristiania.eksamen.product.ProductDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListProductsController implements HttpController {

    private final ProductDao productDao;

    public ListProductsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder response = new StringBuilder();

        for (Product product : productDao.listAll()) {
            response.append("<div>Product id: ").append(product.getId()).append("<br>Product name: ").append(product.getName()).append("<br>Product price: ").append(product.getPrice()).append("<br>Product description: ").append(product.getDescription()).append("<br>Product category: ").append(product.getCategory()).append("</div><br><br>");
        }

        return new HttpMessage("HTTP/1.1 200 OK", response.toString());
    }
}
