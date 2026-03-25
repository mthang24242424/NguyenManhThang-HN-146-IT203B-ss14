package DAO;

import Utils.DatabaseConnectionManager;
import entity.Order_Details;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO {
    public void insertBatch(List<Order_Details> list, int orderId) {
        String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (Order_Details d : list) {
                ps.setInt(1, orderId);
                ps.setInt(2, d.getProduct_id());
                ps.setInt(3, d.getQuantity());
                ps.setDouble(4, d.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            System.out.println("Insert batch success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public double FUNC_CalculateProductRevenue(int id){
        String sql="SELECT CalculateProductRevenue(? ,?)";
        try(Connection connection=DatabaseConnectionManager.getConnection();
            CallableStatement ps=connection.prepareCall(sql)) {
            ps.setInt(1, id);
            ps.registerOutParameter(2, java.sql.Types.DOUBLE);
            ps.execute();
            return ps.getDouble(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

