package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBCommunicator {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/libravision";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection con;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection Successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

//--------------------------FETCH---------------------------------
    public static ResultSet fetchCopy(int id) {
        String query = "SELECT b.title, c.book_id, c.copy_id, b.url FROM copy c JOIN book b ON c.book_id = b.book_id WHERE copy_id = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs;
    }

    public static ResultSet fetchCurBorrowings(String username) {

        int userId = fetchUserId(username);

        String query = "SELECT b.title, b.book_id, br.borrowing_start, br.borrowing_finish, c.copy_id, b.url FROM borrowing br JOIN copy c ON br.copy_id = c.copy_id JOIN book b ON c.book_id = b.book_id WHERE br.user_id = ? AND br.borrowing_finish >= CURRENT_DATE";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, userId); 
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs;
    }

    public static ResultSet fetchWear(int id) {
        String query = "SELECT w.details, w.submission_date, w.url FROM copy c JOIN wear w ON c.copy_id = w.copy_id WHERE c.copy_id = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs;
    }

    public static int fetchUserId(String username) {
        String query = "SELECT user_id FROM user WHERE username = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username); 
            rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("user_id");
            } else{
                return -1;
            } 
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return -1;
        }
    }
    
//----------------------INSERT---------------------------
    public static void insertDBWear(Wear wear) {

        int userId = fetchUserId(wear.getUsername());
        if(userId == -1){
            return;
        }

        String sql = "INSERT INTO wear(copy_id, user_id, details, submission_date, url) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, wear.getCopyID());
            stmt.setInt(2, userId);
            stmt.setString(3, wear.getDetails());
            stmt.setDate(4, wear.getSubmissionDate());
            stmt.setString(5, wear.getUrlToPhoto());

            int rowsInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}