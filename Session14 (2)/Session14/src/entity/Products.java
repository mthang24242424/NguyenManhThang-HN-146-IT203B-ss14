package entity;

public class Products {
    private int product_id;
    private String product_name;
    private Double price;
    private int stock;
    private String created_at;
    public Products(){}

    public Products(int product_id, String product_name, Double price, int stock, String created_at) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.stock = stock;
        this.created_at = created_at;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    @Override
    public String toString() {
        return String.format("%-5d %-15s %-10s %-20s",
                product_id, product_name, price, created_at);
    }
}
