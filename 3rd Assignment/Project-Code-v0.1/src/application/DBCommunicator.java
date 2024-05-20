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

    public static List<ResultSet> fetchReqBooks(List<String> isbns) {
        List<ResultSet> booksWithBigCount = new ArrayList<>();
        String sql = "SELECT b.book_id FROM book b JOIN copy c ON b.book_id = c.book_id WHERE b.book_id = ? GROUP BY b.book_id HAVING COUNT(*) < 10";

        for (String isbn : isbns) {
            try {
            	PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, isbn);

                try {
                	ResultSet rs = stmt.executeQuery();
                	booksWithBigCount.add(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return booksWithBigCount;
    }

    public static List<ResultSet> fetchBookSearch() {
        List<ResultSet> resultSets = new ArrayList<>();
        
        String randomBooksQuery = "SELECT book_id FROM book ORDER BY RAND() LIMIT 3";
        
        try {
        	PreparedStatement randomBooksStmt = con.prepareStatement(randomBooksQuery);
            ResultSet randomBooksResult = randomBooksStmt.executeQuery();
        
            List<String> bookIds = new ArrayList<>();
            while (randomBooksResult.next()) {
                bookIds.add(randomBooksResult.getString("book_id"));
            }
        
            for (String bookId : bookIds) {
            	String sql = "SELECT b.title, a.author_name, g.genre_name, b.rating, b.url, " +
                        "(SELECT COUNT(*) FROM copy c JOIN borrowing br ON c.copy_id = br.copy_id WHERE c.book_id = b.book_id) AS bor_count " +
                        "FROM book b " +
                        "JOIN book_author ba ON b.book_id = ba.book_id " +
                        "JOIN author a ON ba.author_id = a.author_id " +
                        "JOIN book_genre bg ON b.book_id = bg.book_id " +
                        "JOIN genre g ON bg.genre_id = g.genre_id " +
                        "WHERE b.book_id = ?";
	           PreparedStatement stmt = con.prepareStatement(sql);
	           stmt.setString(1, bookId);
	           ResultSet resultSet = stmt.executeQuery();
	           resultSets.add(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSets;
    }

    public static ResultSet fetchBookDet(Book book) {
            String query = "SELECT b.book_id, b.page_num, b.release_date, b.description, " +
               "(SELECT COUNT(*) FROM copy c WHERE c.book_id = b.book_id) AS cop_count, " +
               "(SELECT COUNT(*) FROM copy c " +
               "JOIN borrowing br ON c.copy_id = br.copy_id " +
               "WHERE c.book_id = b.book_id AND br.borrowing_finish < CURRENT_DATE) AS nf_bor_count, " +
               "(SELECT COUNT(*) FROM copy c WHERE c.book_id = b.book_id) - " +
               "(SELECT COUNT(*) FROM copy c " +
               "JOIN borrowing br ON c.copy_id = br.copy_id " +
               "WHERE c.book_id = b.book_id AND br.borrowing_finish < CURRENT_DATE) AS available_copies " +
               "FROM book b WHERE b.title = ?;";

            ResultSet rs = null;
            
            try{
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, book.getTitle());
                rs = stmt.executeQuery();
                
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage());
            }
            return rs;
    }

    public static ResultSet fetchBookCat(String username) {
        String sql = "SELECT c.category_id, c.category_name, c.url, "+
                    "(SELECT COUNT(*) FROM book_category bc WHERE bc.category_id = c.category_id) AS count FROM category c " +
                    "JOIN user u ON c.user_id = u.user_id " +
                    "WHERE u.username = ?;";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username); 
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs; 
    }

    public static List<ResultSet> fetchCatBooks(int cat_id) {
        List<ResultSet> resultSets = new ArrayList<>();
        
        try {
            String sql = "SELECT b.title, a.author_name, g.genre_name, b.rating, b.url, " +
                    "(SELECT COUNT(*) FROM copy c JOIN borrowing br ON c.copy_id = br.copy_id WHERE c.book_id = b.book_id) AS bor_count " +
                    "FROM book b " +
                    "JOIN book_category bc ON b.book_id = bc.book_id " +
                    "JOIN book_author ba ON bc.book_id = ba.book_id " +
                    "JOIN author a ON ba.author_id = a.author_id " +
                    "JOIN book_genre bg ON b.book_id = bg.book_id " +
                    "JOIN genre g ON bg.genre_id = g.genre_id " +
                    "WHERE bc.category_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, cat_id);
            ResultSet resultSet = stmt.executeQuery();
            resultSets.add(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSets;
    }
    
    public static List<ResultSet> fetchBooksByTitle(List<String> titles){
        List<ResultSet> categoryBooks = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title, b.rating, a.author_name, g.genre_name, b.url FROM book b "
        		+ "JOIN book_author ba ON b.book_id = ba.book_id "
        		+ "JOIN author a ON ba.author_id = a.author_id JOIN book_genre bg ON b.book_id = bg.book_id "
        		+ "JOIN genre g ON bg.genre_id = g.genre_id WHERE b.title = ?";
        
        for (String title : titles) {
            try {
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, title);

                try {
                    ResultSet rs = stmt.executeQuery();
                    categoryBooks.add(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categoryBooks;
    }
    
    public static ResultSet fetchRandBooks() {
        String query = "SELECT title, rating, url FROM book ORDER BY RAND() LIMIT 4";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return rs;
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
            stmt.executeUpdate();
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
            stmt.executeUpdate();
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
            stmt.executeUpdate();
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
            } catch (SQLException e) { System.err.println("SQL Error: " + e.getMessage()); }
        }
    }

    public static void insertDBDonation(List<Donation> donationList) {

        int userId = fetchUserId(donationList.get(0).getUsername());
        if(userId == -1){
            return;
        }

        for(Donation don: donationList){
            String sql = "INSERT INTO donation(user_id, date, isbn, book_num) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setDate(2, don.getDonDate());
                stmt.setString(3, don.getIsbn());
                stmt.setInt(4, don.getBookNum());
                
                int rowsInserted = stmt.executeUpdate();
            } catch (SQLException e) { System.err.println("SQL Error: " + e.getMessage()); }
        }
    }

    public static void insertDBRes(Reservation res) {

        int userId = fetchUserId(res.getUsername());
        if(userId == -1){
            return;
        }

        String sql = "INSERT INTO reservation(book_id, user_id, datetime, creation_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, res.getBook().getIsbn());
            stmt.setInt(2, userId);
            stmt.setDate(3, res.getDatetime());
            stmt.setDate(4, res.getCreationDate());
            
            int rowsInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    public static void insertDBNotification(Notification notif) {

        int userId = fetchUserId(notif.getUsername());
        if(userId == -1){
            return;
        }

        String sql = "INSERT INTO notification(book_id, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, notif.getBook().getIsbn());
            stmt.setInt(2, userId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
    public static void insertDBBookCategory(BookCategory bookCat) {
        int userId = fetchUserId(bookCat.getUsername());
        if (userId == -1) {
            return;
        }
    
        String sql = "INSERT INTO category(category_name, user_id, url) VALUES (?, ?, ?)";
        String sql2 = "SELECT category_id FROM category WHERE user_id = ? AND category_name = ?";
    
        try {
        	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, bookCat.getCategoryName());
            stmt.setInt(2, userId);
            stmt.setString(3, bookCat.getUrlToPhoto());
            stmt.executeUpdate();
    
            if (bookCat.getBooks() != null) {
                try {
                	PreparedStatement stmt2 = con.prepareStatement(sql2);
                    stmt2.setInt(1, userId);
                    stmt2.setString(2, bookCat.getCategoryName());
                    ResultSet rs = stmt2.executeQuery();
    
                    if (rs.next()) {
                        int categoryId = rs.getInt("category_id");
                        
                        String sql3 = "INSERT INTO book_category(book_id, category_id) VALUES (?, ?)";
                        try {
                        	PreparedStatement stmt3 = con.prepareStatement(sql3);
                            for (Book book : bookCat.getBooks()) {
                                stmt3.setString(1, book.getIsbn());
                                stmt3.setInt(2, categoryId);
                                
                                stmt3.executeUpdate();
                            }
                        } catch (SQLException e) {
                            System.err.println("Error inserting into book_category: " + e.getMessage());
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error retrieving category ID: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
    public static void insertDBUser(User us) {
        String sql = "INSERT INTO user(fullname, username, age, password, email, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        String sql2 = "SELECT user_id FROM user WHERE username = ?";
        String sql3 = "INSERT INTO member(user_id) VALUES (?)";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            PreparedStatement stmt2 = con.prepareStatement(sql2);
            PreparedStatement stmt3 = con.prepareStatement(sql3);

            stmt.setString(1, us.getFullname());
            stmt.setString(2, us.getUsername());
            stmt.setString(3, String.valueOf(us.getAge()));
            stmt.setString(4, us.getPassword());
            stmt.setString(5, us.getEmail());
            stmt.setString(6, us.getTelephone());
            
            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                stmt2.setString(1, us.getUsername());
                try (ResultSet rs = stmt2.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("user_id");
                        stmt3.setInt(1, userId);
                        stmt3.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
    public static void insertDBProfile(UserProfile userProf) {
        int userId = fetchUserId(userProf.getUsername());
        if (userId == -1) {
            return;
        }
    
        String sql = "INSERT INTO user_profile(user_id, fav_authors, fav_genres, pages, interests) VALUES (?, ?, ?, ?, ?)";
        try {
        	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, String.join(", ", userProf.getAuthors()));
            stmt.setString(3, String.join(", ", userProf.getGenres()));
            stmt.setInt(4, userProf.getPages());
            stmt.setString(5, String.join(", ", userProf.getInterests()));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
//------------------------UPDATE-----------------------------------
    public static void updateDBpoints(Member m) {
        int userId = fetchUserId(m.getUsername());
        if(userId == -1){
            return;
        }
    
        String sql = "UPDATE member SET points = ? WHERE user_id = ?";
        try {
        	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, m.getPoints()); 
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}