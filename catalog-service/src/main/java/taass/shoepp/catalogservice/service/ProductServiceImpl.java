package taass.shoepp.catalogservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import taass.shoepp.catalogservice.entity.Product;
import taass.shoepp.catalogservice.models.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taass.shoepp.catalogservice.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(ProductForm productForm) {
        Product product = new Product();
        product.setTitle(productForm.getTitle());
        product.setPicture(productForm.getPicture());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        product.setStock(productForm.getStock());
        if (productForm.getSizes() != null)
            product.setSizes(productForm.getSizes());
        if (productForm.getBrands() != null)
            product.setBrands(productForm.getBrands());
        if (productForm.getCategories() != null)
            product.setCategories(productForm.getCategories());

        productRepository.save(product);
    }

    public void editProduct(ProductForm productForm) throws Exception {

        Product product = productRepository.findById(productForm.getId()).orElseThrow(() -> new Exception("Product doesnt exist"));

        product.setTitle(productForm.getTitle());
        product.setPicture(productForm.getPicture());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        product.setStock(productForm.getStock());
        product.setSizes(productForm.getSizes());
        product.setBrands(productForm.getBrands());
        product.setCategories(productForm.getCategories());

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) throws ResponseStatusException {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesnt exist"));

        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(Long idProduct) throws ResponseStatusException {
        Product product = productRepository.findById(idProduct).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesnt exist"));

        return product;
    }
}
