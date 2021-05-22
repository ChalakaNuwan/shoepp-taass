package taass.shoepp.catalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import taass.shoepp.catalogservice.entity.Brand;
import taass.shoepp.catalogservice.entity.Category;
import taass.shoepp.catalogservice.entity.Product;
import taass.shoepp.catalogservice.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllEagerBy();
    }

    private int featuredProductsNumber = 8;

    @Override
    public Page<Product> findArticlesByCriteria(PageRequest pageable, Integer pricelow, Integer pricehigh, List<String> sizes, List<String> categories, List<String> brands, String search) {
        Page<Product> page = productRepository.findAll(ProductSpecification.filterBy(pricelow, pricehigh, sizes, categories, brands, search), pageable);

        return page;

    }

    @Override
    public List<String> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public List<String> getAllBrands() {
        return brandRepository.findAllBrands();
    }

    @Override
    public List<String> getAllSizes() { return sizeRepository.findAllSizes(); }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Category> categories = categoryRepository.findByName(category);
        List<Product> products = new ArrayList<>();
        for (Category c : categories) {
            products.add(c.getProduct());
        }

        return products;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Brand> brands = brandRepository.findByName(brand);
        List<Product> products = new ArrayList<>();
        for (Brand b : brands) {
            products.add(b.getProduct());
        }

        return products;
    }

    @Override
    public List<Product> findFeaturedProducts() {
        return productRepository.findAll(PageRequest.of(0, featuredProductsNumber)).getContent();
    }
}
