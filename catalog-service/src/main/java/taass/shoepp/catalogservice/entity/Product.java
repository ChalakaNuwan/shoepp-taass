package taass.shoepp.catalogservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    private int stock;
    private double price;
    private String picture;
    private String description;

    @OneToMany(mappedBy="product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Size> sizes;

    @OneToMany(mappedBy="product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Brand> brands;

    @OneToMany(mappedBy="product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        if (sizes != null && !sizes.isEmpty()) {
            List<String> listDistinct = sizes.stream().distinct().collect(Collectors.toList());
            Set<Size> tmpSizes = new HashSet<>();
            for (String size : listDistinct) {
                tmpSizes.add(new Size(size, this));
            }

            if (this.sizes != null) {
                this.sizes.clear();
                this.sizes.addAll(tmpSizes);
            } else {
                this.sizes = tmpSizes;
            }
        }
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        if (brands != null && !brands.isEmpty()) {
            List<String> listDistinct = brands.stream().distinct().collect(Collectors.toList());
            Set<Brand> tmpBrands = new HashSet<>();
            for (String brand : listDistinct) {
                tmpBrands.add(new Brand(brand, this));
            }
            if (this.brands != null) {
                this.brands.clear();
                this.brands.addAll(tmpBrands);
            } else {
                this.brands = tmpBrands;
            }

        }
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        if (categories != null && !categories.isEmpty()) {
            List<String> listDistinct = categories.stream().distinct().collect(Collectors.toList());
            Set<Category> tmpCategories = new HashSet<>();
            for (String category : listDistinct) {
                tmpCategories.add(new Category(category, this));
            }
            if (this.categories != null) {
                this.categories.clear();
                this.categories.addAll(tmpCategories);
            } else this.categories = tmpCategories;

        }
    }
}
