package DAO;

import entity.Products;
import Utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    public boolean insertProduct(Products product) {
        String sql = "insert into Products(product_name, price, stock) values (?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getProduct_name());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProduct(Products product) {
        String sql = "update Products set product_name = ?, price = ?, stock = ? where product_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getProduct_name());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getProduct_id());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        String sql = "delete from Products where product_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Products findProductById(int id) {
        String sql = "select * from Products where product_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Products product = new Products();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCreated_at(rs.getString("created_at"));
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Products> getAllProducts() {
        List<Products> list = new ArrayList<>();
        String sql = "select * from Products";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Products product = new Products();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCreated_at(rs.getString("created_at"));
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public Products getProductForUpdate(int productId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM Products WHERE product_id = ? FOR UPDATE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Products p = new Products();
                p.setProduct_id(rs.getInt("product_id"));
                p.setProduct_name(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                return p;
            }
        }
        return null;
    }
    public boolean updateStock(int productId, int newStock, Connection conn) throws SQLException {
        String sql = "UPDATE Products SET stock = ? WHERE product_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;
        }
    }
    public Optional<Products> findProductOptionalById(int id) {
        String sql = "SELECT * FROM products WHERE product_id= ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Products p = new Products();
                p.setProduct_id(rs.getInt("product_id"));
                p.setProduct_name(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));

                return Optional.of(p);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm sản phẩm: " + e.getMessage());
        }

        return Optional.empty();
    }
}