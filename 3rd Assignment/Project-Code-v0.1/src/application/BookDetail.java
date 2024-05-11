package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
	
    @FXML
    private VBox bookDetailsArea; // The UI component to display book data
    private ImageView homeButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookDetails.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookDetail.css").toExternalForm());
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();

            // You must get a reference to the controller and call loadBooks after the stage is shown
            BookDetail controller = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setBook(Book book) {
        book = Book.fetchBookDet(book);

        VBox imageBox = new VBox(10); // VBox to hold the image and the button

        // Set up the image
        Image image = new Image(getClass().getResourceAsStream("/misc/book1.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        
        Label borrowedLabel = new Label("Borrowed: " + book.getBorrowedCount() + " times");
        borrowedLabel.getStyleClass().add("borrowed");
        // Create the button
        Button reserveButton = new Button("Reserve it");
        reserveButton.getStyleClass().add("reserve-btn"); // Add style class for CSS styling
        reserveButton.setPrefWidth(160);
        reserveButton.setPrefHeight(35);
        reserveButton.setCursor(Cursor.HAND);
        Book tempBook = book;
        reserveButton.setOnAction(event -> {    	
        	reserveBook(tempBook);  
        });

        // Add image and button to the imageBox
        imageBox.getChildren().addAll(imageView, borrowedLabel, reserveButton);

        // VBox for the text details
        VBox textDetails = new VBox(5);
        textDetails.setSpacing(30); // Spacing between each book entry
        Label titleLabel = new Label(book.getTitle());
        titleLabel.getStyleClass().add("book-title");

        Label authorLabel = new Label("Author: " + book.getAuthor());

        // Star Rating
        HBox starRatingBox = new HBox(5);
        Label ratingLabel = new Label("Rating: ");
        starRatingBox.getChildren().add(ratingLabel);
        int fullStars = (int) book.getRating();
        for (int i = 0; i < fullStars; i++) {
            ImageView star = new ImageView(new Image(getClass().getResourceAsStream("/misc/filled_star.png")));
            star.setFitWidth(20);
            star.setFitHeight(20);
            starRatingBox.getChildren().add(star);
        }

        Label descriptionLabel = new Label("Description: " + book.getDescription());
        descriptionLabel.setWrapText(true);
        Label genresLabel = new Label("Genres: " + book.getGenresFormatted());
        Label pageNumberLabel = new Label("Page Number: " + book.getPageNum());
        Label isbnLabel = new Label("ISBN: " + book.getIsbn());
        Label relDateLabel = new Label("Release Date: " + book.getRelDate());

        textDetails.getChildren().addAll(titleLabel, authorLabel, starRatingBox, descriptionLabel, genresLabel, 
                                         pageNumberLabel, relDateLabel, isbnLabel);
        textDetails.getStyleClass().add("book-label");

        // Main container
        HBox hbox = new HBox(50);
        hbox.getChildren().addAll(imageBox, textDetails); // Now imageBox includes both image and button
        bookDetailsArea.getChildren().add(hbox);
    }

    public void reserveBook(Book book) {
    	if(book.getAvailCopy() == 0) {
    		AvailabilityNotifyDialog.showNotifDialog("Error: There aren't any Copies of this Book Available.");
    	}else {
    		List<LocalDate> openDates = Library.getOpenDates();
    		try {
                // Create the FXMLLoader for the book details
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PickupOptions.fxml"));
                Parent root = loader.load();
                PickupOptions controller = loader.getController();
                controller.loadDates(openDates);
                
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/pickupOptions.css").toExternalForm());
                Stage newStage = new Stage();

                Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
                showPickOpt(newStage, currentStage, scene);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    }
    
    public void showPickOpt(Stage newStage, Stage oldStage, Scene scene) {
    	newStage.setTitle("Book Details");
        newStage.setScene(scene);
        oldStage.close();
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

