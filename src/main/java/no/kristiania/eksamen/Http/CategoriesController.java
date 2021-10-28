package no.kristiania.eksamen.Http;

import no.kristiania.eksamen.product.CategoryDao;

import java.io.IOException;
import java.sql.SQLException;

public class CategoriesController implements HttpController {
    private final CategoryDao categoryDao;

    public CategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder responseText = new StringBuilder();

        int value = 1;
        for (String category : categoryDao.listAll()) {
            responseText.append("<option value=").append(value++).append(">").append(category).append("</option>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());
    }
}

