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

public class CurrentBorrowingsDisplay extends Application {
	
    @FXML
    private VBox curBorrowingsArea;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CurrentBorrowings.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/currentBorrowings.css").toExternalForm());
            primaryStage.setTitle("Current Borrowings");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the current borrowings in a new stage
    public void showCurBorrow(List<Borrowing> curBorrowings, String mode) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/currentBorrowings.fxml"));
            Parent root = loader.load();
            CurrentBorrowingsDisplay controller = loader.getController();
            
            // Set borrowings data in the controller
            controller.setBorrowings(curBorrowings, mode);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/currentBorrowings.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Current Borrowings");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set the borrowings data in the UI
    public void setBorrowings(List<Borrowing> curBorrowings, String mode) {
    	curBorrowingsArea.setSpacing(35);
    	for (Borrowing borrowing : curBorrowings) {
    		
            HBox hbox = new HBox(75);
            
            // Load book image
            Image image = new Image(getClass().getResourceAsStream(borrowing.getCopy().getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            // Create title box with book title
            VBox titleBox = new VBox(0);
            titleBox.getStyleClass().add("book-title");
            Label titleLabel = new Label(borrowing.getCopy().getTitle()); 
            titleLabel.setCursor(Cursor.HAND);
            titleLabel.setWrapText(true);
            titleBox.getChildren().add(titleLabel);
            titleBox.setPrefWidth(150);
            
            // Create borrow dates box
            VBox borrowDates = new VBox(50);
            borrowDates.getStyleClass().add("borrow-dates");
            Label borrowStart = new Label("Pickup Date: " + borrowing.getBorrowingStart());
            borrowStart.getStyleClass().add("borrow-start");
            Label borrowEnd = new Label("Due Date: " + borrowing.getBorrowingEnd());
            borrowEnd.getStyleClass().add("borrow-end");
            borrowDates.getChildren().addAll(borrowStart, borrowEnd);
            
            // Create extend button
            VBox extendBox = new VBox(0);
            Button extendButton = new Button();
            if(!"Wear".equals(mode)) {
               extendButton.setText("Extend");
            } else {
            	extendButton.setText("Select");
            }
            
            extendButton.getStyleClass().add("extend-btn");
            extendButton.setPrefWidth(160);
            extendButton.setPrefHeight(35);
            extendButton.setCursor(Cursor.HAND);

            extendBox.getStyleClass().add("extend-box");
            extendBox.getChildren().add(extendButton);
            
            // Set button action
            extendButton.setOnAction(event -> {
            	Stage oldStage = (Stage) curBorrowingsArea.getScene().getWindow();
        		oldStage.close();
        		
            	if(!"Wear".equals(mode)) {
            		PointLossWarning pointLossWarning = new PointLossWarning();
					pointLossWarning.showPointWarn("Extending your Due Date on a Borrowing will result in point loss."
							+ "\r\n"
							+ "Would you like to proceed with the extension?", null, null, borrowing);  
            	}else {
            		BookConfirmDisplay bookConfDisp = new BookConfirmDisplay();
            		bookConfDisp.showBookConfDisplay(borrowing.getCopy());
            	}
            	
            });
            
            hbox.getChildren().addAll(imageView, titleBox, borrowDates, extendBox);
            curBorrowingsArea.getChildren().add(hbox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}