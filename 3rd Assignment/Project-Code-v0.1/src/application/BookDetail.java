package application;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookDetail extends Application {
	// Declare FXML components
    @FXML
    private VBox bookDetailsArea;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Label borrowedLabel;
    
    @FXML
    private Button reserveButton;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label authorLabel;
    
    @FXML
    private HBox starRatingBox;
    
    @FXML
    private Label ratingLabel;
    
    @FXML
    private Label descriptionLabel;
    
    @FXML
    private Label genresLabel;
    
    @FXML
    private Label pageNumberLabel;
    
    @FXML
    private Label isbnLabel;
    
    @FXML
    private Label relDateLabel;
    
    @Override
    public void start(Stage primaryStage) {
        try {
             // Load FXML layout for book details
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookDetails.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Book Details");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to display book details in a new stage
    public void showBookDet(Book book) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookDetails.fxml"));
            Parent root = loader.load();
            BookDetail controller = loader.getController();
            controller.setBook(book);
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Book Details");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     // Method to set book details in the UI components 
    public void setBook(Book book) {
        try {
			book = Book.fetchBookDet(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        Book tempBook = book;
        
        Image image = new Image(getClass().getResourceAsStream(book.getUrlToPhoto()));
        imageView.setImage(image);
        
        borrowedLabel.setText("Borrowed: " + book.getBorrowedCount() + " times");
        titleLabel.setText(book.getTitle());
        authorLabel.setText("Author: " + book.getAuthorsFormatted());
        ratingLabel.setText("Rating: ");
        
        int fullStars = (int) book.getRating();
        for (int i = 0; i < fullStars; i++) {
            ImageView star = new ImageView(new Image(getClass().getResourceAsStream("/misc/filledStar.png")));
            star.setFitWidth(20);
            star.setFitHeight(20);
            starRatingBox.getChildren().add(star);
        }

        descriptionLabel.setText("Description: " + book.getDescription());
        genresLabel.setText("Genres: " + book.getGenresFormatted());
        pageNumberLabel.setText("Page Number: " + book.getPageNum());
        isbnLabel.setText("ISBN: " + book.getIsbn());
        relDateLabel.setText("Release Date: " + book.getRelDate());
        
        // Set action for reserve button
        reserveButton.setOnAction(event -> reserveBook(tempBook));
    }

    // Method to reserve a book
    public void reserveBook(Book book) {
    	if(book.getAvailCopy() == 0) {
    		Stage oldStage = (Stage) bookDetailsArea.getScene().getWindow();
    		oldStage.close();
    		AvailabilityNotifyDialog availDialog = new AvailabilityNotifyDialog();
    		availDialog.showNotifDialog(book);
    		
    	} else {
    		List<LocalDate> openDates = new ArrayList<>();
			try {
				openDates = Library.getOpenDates("Roumpini's Library");
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		
    		PickupOptions pickUp = new PickupOptions();
    		pickUp.showPickOpt(openDates, book);
    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}

