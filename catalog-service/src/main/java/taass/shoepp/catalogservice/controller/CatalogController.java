package taass.shoepp.catalogservice.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.shoepp.catalogservice.entity.Product;
import taass.shoepp.catalogservice.models.FilterCatalogForm;
import taass.shoepp.catalogservice.models.SortFilter;
import taass.shoepp.catalogservice.service.CatalogService;
import taass.shoepp.catalogservice.service.ProductService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ProductService productService;

    @GetMapping("/catalog/allCatalog")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> catalog = catalogService.getAllProducts();
        return ResponseEntity.ok(catalog);
    }

    @GetMapping("/catalog/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = catalogService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/catalog/brands")
    public ResponseEntity<List<String>> getAllBrands() {
        List<String> brands = catalogService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/catalog/sizes")
    public ResponseEntity<List<String>> getAllSizes() {
        List<String> sizes = catalogService.getAllSizes();
        Collections.sort(sizes);
        return ResponseEntity.ok(sizes);
    }

    @GetMapping("/catalog/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = catalogService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/catalog/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = catalogService.getProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/catalog/catalog")
    public ResponseEntity<List<Product>> filterCatalog(@RequestBody FilterCatalogForm filters) {
        System.out.println("FORM: " + filters.toString());
        Integer page = filters.getPage();
        int pagenumber = (page == null || page <= 0) ? 0 : page-1;
        SortFilter sortFilter = new SortFilter((filters.getSort()));
        Page<Product> pageResult = catalogService.findArticlesByCriteria(
                PageRequest.of(pagenumber, 9, sortFilter.getSortType()),
                filters.getPricelow(), filters.getPricehigh(),
                filters.getSize(), filters.getCategory(), filters.getBrand(), filters.getSearch());

        return ResponseEntity.ok(pageResult.getContent());
    }

    @GetMapping("/catalog/featured")
    public ResponseEntity<List<Product>> featuredProducts() {
        List<Product> products = catalogService.findFeaturedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/catalog/{idProduct}")
    public ResponseEntity<Product> getProduct(@PathVariable Long idProduct) throws Exception {
        Product product = productService.getProduct(idProduct);
        return ResponseEntity.ok(product);
    }
}

