package taass.shoepp.catalogservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "sizes")
public class Size implements Comparable<Size> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="article_id", nullable = false)
    @JsonBackReference
    private Product product;
    private String value;

    public Size() { }

    public Size(String value, Product product) {
        this.value = value;
        this.product = product;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product article) {
        this.product = article;
    }

    @Override
    public int compareTo(Size s) {
        return this.value.compareTo(s.getValue());
    }
}
