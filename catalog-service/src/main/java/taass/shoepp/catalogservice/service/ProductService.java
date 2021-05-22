package taass.shoepp.catalogservice.service;

import taass.shoepp.catalogservice.entity.Product;
import taass.shoepp.catalogservice.models.ProductForm;

public interface ProductService {

    void addProduct(ProductForm productForm);

    void editProduct(ProductForm productForm) throws Exception;

    void deleteProduct(Long id) throws Exception;

    Product getProduct(Long idProduct) throws Exception;
}
