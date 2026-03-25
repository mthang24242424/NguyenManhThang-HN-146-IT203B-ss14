package entity;

public class Order_Details {
    private int detail_id;
    private int order_id;
    private int product_id;
    private double price;
    private int quantity;
    private double total;
    public Order_Details() {
    }
    public Order_Details(int detail_id, int order_id, int product_id, double price, int quantity, double total) {
        this.detail_id = detail_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return String.format("%-5d %-5d %-5d %-10s %-5d %-10s",
                detail_id, order_id, product_id, price, quantity, total);
    }
}
