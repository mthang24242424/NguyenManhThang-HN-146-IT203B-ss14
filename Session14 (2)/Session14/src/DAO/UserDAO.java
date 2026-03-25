package DAO;

import entity.Users;
import Utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean insertUser(Users user) {
        String sql = "insert into Users(username, password, role) values (?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(Users user) {
        String sql = "update Users set username = ?, password = ?, role = ? where user_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getUser_id());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        String sql = "delete from Users where user_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Users findUserById(int id) {
        String sql = "select * from Users where user_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCreated_at(rs.getString("created_at"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Users> getAllUsers() {
        List<Users> list = new ArrayList<>();
        String sql = "select * from Users";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCreated_at(rs.getString("created_at"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}