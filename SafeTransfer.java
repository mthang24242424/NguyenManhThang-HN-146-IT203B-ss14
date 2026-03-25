import java.sql.*;

public class SafeTransfer {
    private static final String URL = "jdbc:mysql://localhost:3306/ktra";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void main(String[] args) {
        String senderId = "ACC02";
        String receiverId = "ACC01";
        double amount = 1000;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            conn.setAutoCommit(false);

            if (!hasEnoughBalance(conn, senderId, amount)) {
                System.out.println("Số dư không đủ để chuyển khoản!");
                return;
            }

            try {
                try (CallableStatement cs1 = conn.prepareCall("{call sp_UpdateBalance(?, ?)}")) {
                    cs1.setString(1, senderId);
                    cs1.setDouble(2, -amount);
                    cs1.executeUpdate();
                }

                try (CallableStatement cs2 = conn.prepareCall("{call sp_UpdateBalance(?, ?)}")) {
                    cs2.setString(1, receiverId);
                    cs2.setDouble(2, amount);
                    cs2.executeUpdate();
                }

                conn.commit();
                System.out.println("Chuyển khoản thành công!");


            } catch (Exception e) {
                conn.rollback();
                System.out.println(" Lỗi xảy ra, rollback giao dịch!");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasEnoughBalance(Connection conn, String accountId, double amount) throws SQLException {
        String sql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("Balance");
                    return balance >= amount;
                }
            }
        }
        return false;
    }

}