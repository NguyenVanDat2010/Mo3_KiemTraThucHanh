package product.service;

import product.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductService {
    List<Product> selectAllProduct();

    Product selectProductById(int id);

    void insertProduct(Product product) throws SQLException;

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);

    List<Product> searchProduct(String value) throws SQLException;

    List<Product> sortProduct(String sortBy,String styleSort);
}
