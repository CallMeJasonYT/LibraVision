package application;
import javafx.scene.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private VBox bookDisplayArea;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookSearch.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookSearch.css").toExternalForm());
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();

            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to load books into the UI
    public void loadBooks() {        
        List<Book> books = new ArrayList<>();
		try {
			books = Book.getBooks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        // Iterate through each book and display its details
        for (Book book : books) {
            HBox hbox = new HBox(20);
            
            // Create and configure ImageView for book cover
            Image image = new Image(getClass().getResourceAsStream(book.getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            // Create VBox for text details of the book
            VBox textDetails = new VBox(5);
            Label titleLabel = new Label(book.getTitle());
            titleLabel.getStyleClass().add("book-title");
            titleLabel.setCursor(Cursor.HAND);
            
            // Create HBox for author and genres labels
            HBox authorGenresBox = new HBox(10);
            Label authorLabel = new Label("Author: " + book.getAuthorsFormatted());
            authorLabel.setWrapText(true);
            Label genresLabel = new Label("Genres: " + book.getGenresFormatted());
            genresLabel.setWrapText(true);
            authorGenresBox.getChildren().addAll(authorLabel, genresLabel);

            // Create HBox for rating and borrowed count labels
            HBox ratingBorrowedBox = new HBox(10);
            Label ratingLabel = new Label(String.format("Rating: %.1f", book.getRating()));
            Label borrowedLabel = new Label("Borrowed: " + book.getBorrowedCount() + " times");
            ratingBorrowedBox.getChildren().addAll(ratingLabel, borrowedLabel);

            // Add all text details to the VBox    
            textDetails.getChildren().addAll(titleLabel, authorGenresBox, ratingBorrowedBox);
            textDetails.getStyleClass().add("book-label");

            // Add ImageView and text details VBox to the HBox
            hbox.getChildren().addAll(imageView, textDetails);
            bookDisplayArea.getChildren().add(hbox);
            
            // Handle mouse click event on the book title label
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

