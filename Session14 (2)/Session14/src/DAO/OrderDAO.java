package DAO;

import Utils.DatabaseConnectionManager;
import entity.Orders;
import entity.Order_Details;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Products;

public class OrderDAO {
    Scanner sc = new Scanner(System.in);
    ProductDAO productDAO = new ProductDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    public int insert(Orders order) {
        String sql = "INSERT INTO Orders(user_id,total) VALUES (?,?)";
        int orderId = -1;
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUser_id());
            ps.setDouble(2, order.getTotal());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }
    public int createOrder() {
        int orderId = -1;

        try {
            int userId;
            while (true) {
                System.out.print("Nhập user_id: ");
                String input = sc.nextLine().trim();
                try {
                    userId = Integer.parseInt(input);
                    if (userId > 0) break;
                    else System.out.println("Không được nhập số âm hoặc 0!");
                } catch (NumberFormatException e) {
                    System.out.println("Phải nhập số nguyên!");
                }
            }

            int n;
            while (true) {
                System.out.print("Nhập số lượng sản phẩm: ");
                if (sc.hasNextInt()) {
                    n = sc.nextInt();
                    if (n > 0) break;
                } else {
                    sc.next();
                }
                System.out.println("Số lượng phải > 0! Nhập lại.");
            }

            List<Order_Details> list = new ArrayList<>();
            double total = 0;

            // ===== Nhập từng sản phẩm =====
            for (int i = 0; i < n; i++) {
                System.out.println("\nSản phẩm " + (i + 1));

                int productId;
                while (true) {
                    System.out.print("product_id: ");
                    if (sc.hasNextInt()) {
                        productId = sc.nextInt();
                        if (productId > 0) break;
                    } else {
                        sc.next();
                    }
                    System.out.println("product_id không hợp lệ!");
                }

                int quantity;
                while (true) {
                    System.out.print("quantity: ");
                    if (sc.hasNextInt()) {
                        quantity = sc.nextInt();
                        if (quantity > 0) break;
                    } else {
                        sc.next();
                    }
                    System.out.println("quantity phải > 0!");
                }
                try (Connection conn = DatabaseConnectionManager.getConnection()) {
                    Products product = productDAO.getProductForUpdate(productId, conn);

                    if (product == null) {
                        System.out.println("Sản phẩm không tồn tại!");
                        i--;
                        continue;
                    }

                    double price = quantity * product.getPrice();
                    total += price;

                    Order_Details d = new Order_Details();
                    d.setProduct_id(productId);
                    d.setQuantity(quantity);
                    d.setPrice(price);
                    list.add(d);
                }
            }

            sc.nextLine();

            // ===== Tạo order =====
            Orders order = new Orders();
            order.setUser_id(userId);
            order.setTotal(total);

            orderId = insert(order);

            if (orderId == -1) {
                System.out.println("Tạo order thất bại!");
                return -1;
            }
            orderDetailDAO.insertBatch(list, orderId);

            System.out.println("Tạo đơn hàng thành công! ID = " + orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderId;
    }
    public void getall(){
        String sql="select od.order_id,u.username,od.total,od.order_date from orders od join Users u on od.user_id = u.user_id";
        try(Connection connection=DatabaseConnectionManager.getConnection();
        PreparedStatement ps=connection.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        ) {
            while (rs.next()) {
                System.out.println("id: "+rs.getInt("order_id") + " - name: " + rs.getString("username")+" - total: "+rs.getDouble("total")+" - Date: "+rs.getDate("order_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getTop5Customers() {
        String sql = "SELECT u.user_id, u.username, SUM(od.total) AS total_spent " +
                "FROM Users u " +
                "JOIN Orders od ON u.user_id = od.user_id " +
                "GROUP BY u.user_id, u.username " +
                "ORDER BY total_spent DESC " +
                "LIMIT 5";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("--- TOP 5 KHÁCH HÀNG CHI TIÊU NHIỀU NHẤT ---");
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("username");
                double total = rs.getDouble("total_spent");

                System.out.printf("ID: %d | Tên: %-15s | Tổng chi tiêu: %,.2f VNĐ %n", id, name, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}