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

public class WearReportsDisplay extends Application {
	
    @FXML
    private VBox wearReports; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WearReports.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/wearReports.css").toExternalForm());
            primaryStage.setTitle("Current Borrowings");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showWearReports(List<Wear> wear) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WearReports.fxml"));
            Parent root = loader.load();
            WearReportsDisplay controller = loader.getController();
            
            controller.setWearReports(wear);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/wearReports.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Current Borrowings");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setWearReports(List<Wear> wear) {
    	wearReports.setSpacing(35);
    	
    	for (Wear w : wear) {
    		
            HBox hbox = new HBox(75); // Main container with spacing between image and text
            
            // Set up the image
            Image image = new Image(w.getUrlToPhoto()); // Adjust path as needed
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150); // Adjust width as needed
            imageView.setFitHeight(150); // Adjust height as needed
            
            VBox idBox = new VBox(0);
            idBox.getStyleClass().add("id-vbox");
            Label titleLabel = new Label(String.valueOf(w.getCopyID()));
            titleLabel.setWrapText(true);
            idBox.getChildren().add(titleLabel);
            idBox.setPrefWidth(150);
            
            VBox detailsBox = new VBox(50);
            detailsBox.getStyleClass().add("details-vbox");
            
            Label wearDetails = new Label("Wear Description: " + w.getDetails());
            wearDetails.setWrapText(true);
            wearDetails.getStyleClass().add("wear-details");
            Label submissionLabel = new Label("Submission Date: " + w.getSubmissionDate());
            submissionLabel.getStyleClass().add("submission-date");
            detailsBox.getChildren().addAll(wearDetails, submissionLabel);
            
            VBox extendBox = new VBox(0);
            
            Button extendButton = new Button();
            extendButton.setText("Select");
            
            extendButton.getStyleClass().add("select-btn"); // Add style class for CSS styling
            extendButton.setPrefWidth(160);
            extendButton.setPrefHeight(35);
            extendButton.setCursor(Cursor.HAND);

            extendBox.getStyleClass().add("select-box");
            extendBox.getChildren().add(extendButton);
            
            extendButton.setOnAction(event -> {
            	Stage oldStage = (Stage) wearReports.getScene().getWindow();
        		oldStage.close();
                MainMenu main = new MainMenu();
    			main.showMainPg();
            	
            });
            
            hbox.getChildren().addAll(imageView, idBox, detailsBox, extendBox);
            wearReports.getChildren().add(hbox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}