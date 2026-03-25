package service;

import DAO.OrderDAO;
import DAO.OrderDetailDAO;
import DAO.ProductDAO;
import Utils.DatabaseConnectionManager;
import entity.Products;
import entity.Order_Details;
import java.sql.Connection;
import java.util.List;

public class OrderService {

    public void placeOrder(int userId, List<Order_Details> list) {
        Connection conn = null;

        try {
            conn = DatabaseConnectionManager.getConnection();
            conn.setAutoCommit(false);
            ProductDAO productDAO = new ProductDAO();
            OrderDAO orderDAO = new OrderDAO();
            OrderDetailDAO detailDAO = new OrderDetailDAO();

            double total = 0;
            for (Order_Details d : list) {
                Products p = productDAO.getProductForUpdate(d.getProduct_id(), conn);

                if (p == null) {
                    throw new RuntimeException("Sản phẩm không tồn tại: " + d.getProduct_id());
                }
                if (p.getStock() < d.getQuantity()) {
                    throw new RuntimeException("Không đủ hàng cho product_id: " + d.getProduct_id());
                }

                productDAO.updateStock(
                        d.getProduct_id(),
                        p.getStock() - d.getQuantity(),
                        conn
                );

                total += d.getQuantity() * p.getPrice();

                d.setPrice(p.getPrice());
            }
            int orderId = orderDAO.createOrder();


            for (Order_Details d : list) {
                detailDAO.insertBatch(list, orderId);
            }

            conn.commit();
            System.out.println("Đặt hàng thành công! ID = " + orderId);

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Đặt hàng thất bại: " + e.getMessage());

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}