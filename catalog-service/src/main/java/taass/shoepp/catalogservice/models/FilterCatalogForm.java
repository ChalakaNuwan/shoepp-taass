package taass.shoepp.catalogservice.models;

import java.util.List;

public class        FilterCatalogForm {

    private List<String> size;
    private List<String> category;
    private List<String> brand;
    private Integer pricelow;
    private Integer pricehigh;
    private String sort;
    private Integer page;
    private String search;

    public List<String> getSize() {
        return size;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<String> getBrand() {
        return brand;
    }

    public Integer getPricelow() {
        return pricelow;
    }

    public Integer getPricehigh() {
        return pricehigh;
    }

    public String getSort() {
        return sort;
    }

    public Integer getPage() {
        return page;
    }

    public String getSearch() {
        return search;
    }

    @Override
    public String toString() {
        return "FilterCatalogForm{" +
                "size=" + size +
                ", category=" + category +
                ", brand=" + brand +
                ", pricelow=" + pricelow +
                ", pricehigh=" + pricehigh +
                ", sort='" + sort + '\'' +
                ", page=" + page +
                ", search='" + search + '\'' +
                '}';
    }
}
