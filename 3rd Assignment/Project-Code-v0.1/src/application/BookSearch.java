package application;
import javafx.scene.Cursor; // Import statement for Cursor
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookSearch extends Application {
	
    @FXML
    private VBox bookDisplayArea; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookSearch.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookSearch.css").toExternalForm());
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();

            // You must get a reference to the controller and call loadBooks after the stage is shown
            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadBooks() {
        bookDisplayArea.setSpacing(25); // Spacing between each book entry
        List<Book> books = Book.fetchBooks(); // Your method to fetch books
        for (Book book : books) {
            HBox hbox = new HBox(20); // Main container with spacing between image and text
            
            // Set up the image
            Image image = new Image(getClass().getResourceAsStream("/misc/book1.jpg")); // Adjust path as needed
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150); // Adjust width as needed
            imageView.setFitHeight(150); // Adjust height as needed

            // VBox for the text details
            VBox textDetails = new VBox(5);
            Label titleLabel = new Label(book.getTitle());
            titleLabel.getStyleClass().add("book-title");
            titleLabel.setCursor(Cursor.HAND);
            
            // Author and genres in one HBox
            HBox authorGenresBox = new HBox(10);
            Label authorLabel = new Label("Author: " + book.getAuthor());
            Label genresLabel = new Label("Genres: " + book.getGenresFormatted());
            authorGenresBox.getChildren().addAll(authorLabel, genresLabel);

            // Rating and borrowed in another HBox
            HBox ratingBorrowedBox = new HBox(10);
            Label ratingLabel = new Label(String.format("Rating: %.1f", book.getRating()));
            Label borrowedLabel = new Label("Borrowed: " + book.getBorrowedCount() + " times");
            ratingBorrowedBox.getChildren().addAll(ratingLabel, borrowedLabel);

            // Add the text boxes to the textDetails VBox
            textDetails.getChildren().addAll(titleLabel, authorGenresBox, ratingBorrowedBox);
            textDetails.getStyleClass().add("book-label");

            // Add image and text details to the main HBox
            hbox.getChildren().addAll(imageView, textDetails);
            bookDisplayArea.getChildren().add(hbox);
            
            titleLabel.setOnMouseClicked(event -> {
            	BookDetail bookDet = new BookDetail();
            	bookDet.showBookDet(book);
            	Stage currentStage = (Stage) titleLabel.getScene().getWindow();
            	currentStage.close();
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

