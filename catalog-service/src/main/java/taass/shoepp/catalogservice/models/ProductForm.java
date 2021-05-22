package taass.shoepp.catalogservice.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductForm {

    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String picture;
    @NotNull
    private double price;
    private int stock;
    private List<String> sizes;
    private List<String> brands;
    private List<String> categories;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public List<String> getBrands() {
        return brands;
    }

    public List<String> getCategories() {
        return categories;
    }

    public long getId() { return id; }
}

