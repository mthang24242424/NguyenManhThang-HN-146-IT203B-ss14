package entity;

public class Orders {
    private int order_id;
    private int user_id;
    private String order_date;
    private double total;
    public Orders(){

    }

    public Orders(int order_id, int user_id, String order_date, double total) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.total = total;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return String.format("%-5d %-5d %-15s %-10s",
                order_id, user_id, order_date, total);
    }
}
