package application;

import java.io.IOException;
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

public class BorrowingHistory extends Application {
	// Declare FXML components
    @FXML
    private VBox borrowingsArea;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrowingHistory.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/currentBorrowings.css").toExternalForm());
            primaryStage.setTitle("Borrowings History");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the borrowing history dialog with a list of borrowings
    public void showBorrowHist(List<Borrowing> borrowings) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrowingHistory.fxml"));
            Parent root = loader.load();
            BorrowingHistory controller = loader.getController();
            
            controller.setBorrowings(borrowings);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/currentBorrowings.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Borrowings History");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to populate the borrowing history details
    public void setBorrowings(List<Borrowing> borrowings) {
    	
    	borrowingsArea.setSpacing(35);
    	for (Borrowing borrowing : borrowings) { // Iterate through each borrowing record
    		
            HBox hbox = new HBox(75);
            
            Image image = new Image(getClass().getResourceAsStream("/misc/book1.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            
            VBox titleBox = new VBox(0);
            titleBox.getStyleClass().add("book-title");
            Label titleLabel = new Label(borrowing.getCopy().getTitle()); 
            titleLabel.setCursor(Cursor.HAND);
            titleLabel.setWrapText(true);
            titleBox.getChildren().add(titleLabel);
            titleBox.setPrefWidth(150);
            
            VBox borrowDates = new VBox(50);
            
            borrowDates.getStyleClass().add("borrow-dates");
            Label borrowStart = new Label("Pickup Date: " + borrowing.getBorrowingStart());
            borrowStart.getStyleClass().add("borrow-start");
            Label borrowEnd = new Label("Due Date: " + borrowing.getBorrowingEnd());
            borrowEnd.getStyleClass().add("borrow-end");
            borrowDates.getChildren().addAll(borrowStart, borrowEnd);
            
            VBox rateBox = new VBox(0);
            
            Button rateButton = new Button();
            rateButton.setText("Rate It");
            rateButton.getStyleClass().add("extend-btn");
            rateButton.setPrefWidth(160);
            rateButton.setPrefHeight(35);
            rateButton.setCursor(Cursor.HAND);

            rateBox.getStyleClass().add("extend-box");
            rateBox.getChildren().add(rateButton);
            
            rateButton.setOnAction(event -> {
            	Stage oldStage = (Stage) borrowingsArea.getScene().getWindow();
        		oldStage.close();
        		BookReviewDisplay display = new BookReviewDisplay();
        		display.showBookReview(borrowing);
            });
            
            hbox.getChildren().addAll(imageView, titleBox, borrowDates, rateBox);
            borrowingsArea.getChildren().add(hbox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}