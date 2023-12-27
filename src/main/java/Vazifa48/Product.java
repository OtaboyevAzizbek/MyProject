package Vazifa48;

public class Product {
    private Integer id;
    private String name;
    private String type;
    private String meagure_type;
    private Double quantity;
    private Double price;

    public Product(Integer id, String name, String type, String meagure_type, Double quantity, Double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.meagure_type = meagure_type;
        this.quantity = quantity;
        this.price = price;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeagure_type() {
        return meagure_type;
    }

    public void setMeagure_type(String meagure_type) {
        this.meagure_type = meagure_type;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", meagure_type='" + meagure_type + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
