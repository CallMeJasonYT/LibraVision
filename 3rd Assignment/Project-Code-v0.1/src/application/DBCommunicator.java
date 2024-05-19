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

    public static ResultSet fetchBorrowings(String username) {

        int userId = fetchUserId(username);

        String query = "SELECT b.title, b.book_id, br.borrowing_start, br.borrowing_finish, c.copy_id, b.url FROM borrowing br JOIN copy c ON br.copy_id = c.copy_id JOIN book b ON c.book_id = b.book_id WHERE br.user_id = ? AND br.borrowing_finish <= CURRENT_DATE";
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

    public static List<ResultSet> fetchCopies(List<Integer> copyIds) {
        List<ResultSet> existentCopyIds = new ArrayList<>();
        String sql = "SELECT b.title, b.book_id, c.copy_id, b.url FROM copy c JOIN book b ON c.book_id = b.book_id WHERE copy_id = ?";
        
        for (Integer id : copyIds) {
            try {
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);

                try {
                    ResultSet rs = stmt.executeQuery();
                    existentCopyIds.add(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existentCopyIds;
    }

    public static ResultSet fetchMember(String username) {
        String query = "SELECT u.username, m.points FROM user u JOIN member m ON u.user_id = m.user_id WHERE username = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username); 
            rs = stmt.executeQuery();
            /*while (rs.next()) {
                System.out.println(rs.getString("username"));
                System.out.println(rs.getInt("points"));
            }*/
           
            
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs; 
    }

    public static ResultSet fetchOpenDates(String name) {

        int libraryId = fetchLibraryId(name);
        if(libraryId == -1){
            return null;
        }

        ResultSet rs = null;
        String query = "SELECT d.closed FROM library_days d WHERE d.library_id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, libraryId); 
            rs = stmt.executeQuery();
    
            /*while (rs.next()) {
                System.out.println(rs.getString("closed"));
            }*/
             
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs;
    }

    public static int fetchLibraryId(String name) {
        String query = "SELECT library_id FROM library WHERE name = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name); 
            rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("library_id");
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

    public static void insertDBBookRev(BookReview bookr) {

        int userId = fetchUserId(bookr.getUsername());
        if(userId == -1){
            return;
        }

        String sql = "INSERT INTO book_review(book_id, user_id, submission_date, stars, details) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, bookr.getIsbn());
            stmt.setInt(2, userId);
            stmt.setDate(3, bookr.getSubmissionDate());
            stmt.setDouble(4, bookr.getStars());
            stmt.setString(5, bookr.getDetails());
            int rowsInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage()); }
    }

    public static void insertDBExpRev(ExperienceReview expr) {

        int userId = fetchUserId(expr.getUsername());
        if(userId == -1){
            return;
        }

        String sql = "INSERT INTO experience_review(user_id, submission_date, details, staff_stars, app_stars, condition_stars) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, expr.getSubmissionDate());
            stmt.setString(3, expr.getDetails());
            stmt.setDouble(4, expr.getStarStaff());
            stmt.setDouble(5, expr.getStarApp());
            stmt.setDouble(6, expr.getStarBook());
            int rowsInserted = stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage()); }
    }

    public static void insertDBBorrowing(List<Borrowing> borList) {

        int userId = fetchUserId(borList.get(0).getUsername());
        if(userId == -1){
            return;
        }

        for(Borrowing bor: borList){
            String sql = "INSERT INTO borrowing(copy_id, user_id, borrowing_start, borrowing_finish) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, bor.getCopy().getCopyID());
                stmt.setInt(2, userId);
                stmt.setDate(3, bor.getBorrowingStart());
                stmt.setDate(4, bor.getBorrowingEnd());
                int rowsInserted = stmt.executeUpdate();
                // if (rowsInserted > 0) {
                //     System.out.println("A new borrowing record was inserted successfully!");
                // }
            } catch (SQLException e) { System.err.println("SQL Error: " + e.getMessage()); }
        }
    }

//------------------------UPDATE-----------------------------------
    public static void updateDBpoints(Member m) {
        int userId = fetchUserId(m.getUsername());
        if(userId == -1){
            return;
        }
    
        String sql = "UPDATE member SET points = ? WHERE user_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, m.getPoints()); 
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}