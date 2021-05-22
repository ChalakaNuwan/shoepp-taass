package taass.shoepp.catalogservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import taass.shoepp.catalogservice.entity.Product;

import java.util.List;

public interface CatalogService {


    List<Product> getAllProducts();

    Page<Product> findArticlesByCriteria(PageRequest pageable, Integer pricelow, Integer pricehigh,
                                         List<String> sizes, List<String> categories, List<String> brands, String search);

    List<String> getAllCategories();

    List<String> getAllBrands();

    List<String> getAllSizes();

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategory(String category);

    List<Product> findFeaturedProducts();
}
