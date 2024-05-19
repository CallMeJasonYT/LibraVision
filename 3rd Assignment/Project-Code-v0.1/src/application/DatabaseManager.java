package application;
import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", ""); // Adjust your database URL, username, and password
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean copyExists(String copyId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Copies WHERE copy_id = ?");
            stmt.setString(1, copyId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet listCurrentLoans(int userId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT Copies.copy_id, Books.title FROM Loans " +
                    "JOIN Copies ON Loans.copy_id = Copies.copy_id " +
                    "JOIN Books ON Copies.book_id = Books.book_id " +
                    "WHERE user_id = ?");
            stmt.setInt(1, userId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
