package cashier.dao.entity;

import java.util.Objects;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public class Product {
    private Integer id;
    private Boolean active;
    private String name;
    private Long price;
    private Long weight;
    private Long dateOfAdding;
    private Integer categoriesId;
    private Integer count;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getDateOfAdding() {
        return dateOfAdding;
    }

    public void setDateOfAdding(Long dateOfAdding) {
        this.dateOfAdding = dateOfAdding;
    }

    public Integer getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Integer categoriesId) {
        this.categoriesId = categoriesId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(active, product.active) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(weight, product.weight) &&
                Objects.equals(dateOfAdding, product.dateOfAdding) &&
                Objects.equals(categoriesId, product.categoriesId) &&
                Objects.equals(count, product.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, name, price, weight, dateOfAdding, categoriesId);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Products{id='").append(id).append('\'')
                .append(", active='").append(active).append('\'')
                .append(", name='").append(name).append('\'')
                .append(", price='").append(price).append('\'')
                .append(", weight='").append(weight).append('\'')
                .append(", dateOfAdding='").append(dateOfAdding).append('\'')
                .append(", categoriesId='").append(categoriesId).append('\'')
                .append(", count='").append(count).append('\'')
                .append('}');
        return stringBuilder.toString();
    }
}
