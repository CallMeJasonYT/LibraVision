package application;
import javafx.scene.Cursor; // Import statement for Cursor

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookConfirmDisplay extends Application {
	
    @FXML
    private VBox bookDisplayArea; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookConfirm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookConfirm.css").toExternalForm());
            primaryStage.setTitle("Book Confirm");
            primaryStage.setScene(scene);
            primaryStage.show();

            // You must get a reference to the controller and call loadBooks after the stage is shown
            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showBookConfDisplay(Copy copy) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookConfirm.fxml"));
            Parent root = loader.load();
            BookConfirmDisplay controller = loader.getController();
            
            controller.loadCopy(copy);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookConfirm.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Book Confirm");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadCopy(Copy copy) {
        bookDisplayArea.setSpacing(75); // Spacing between each book entry
       
        Label prompt = new Label("Please Confirm that this is the correct Copy");
        prompt.getStyleClass().add("prompt-label");
        
        HBox hbox = new HBox(75); // Main container with spacing between image and text
        hbox.getStyleClass().add("hbox");
        
        // Set up the image
        Image image = new Image(getClass().getResourceAsStream("/misc/book1.jpg")); // Adjust path as needed
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150); // Adjust width as needed
        imageView.setFitHeight(150); // Adjust height as needed
        
        VBox titleBox = new VBox(0);
        titleBox.getStyleClass().add("book-title");
        Label titleLabel = new Label(copy.getTitle());
        titleLabel.setWrapText(true);
        titleBox.getChildren().add(titleLabel);
        titleBox.setPrefWidth(150);
        
        VBox copyDetails = new VBox(50);
        copyDetails.getStyleClass().add("copy-details");
        
        Label copyID = new Label("Copy ID: " + copy.getCopyID());
        copyID.getStyleClass().add("copyID-label");
        Label copyISBN = new Label("ISBN: " + copy.getIsbn());
        copyISBN.getStyleClass().add("isbn-label");
        copyDetails.getChildren().addAll(copyID, copyISBN);
        
        Button acceptButton = new Button("Confirm");
        acceptButton.getStyleClass().add("continue-btn");
        acceptButton.setCursor(Cursor.HAND);
        acceptButton.setOnAction(e -> {});
        
        acceptButton.setOnAction(e -> {
        	List<Wear> wear = Wear.getWear(copy.getCopyID());
        	if(wear.isEmpty()) {
        		Stage currentStage = (Stage) acceptButton.getScene().getWindow();
    			currentStage.close();
    			DmgReportForm dmgForm = new DmgReportForm();
    			dmgForm.showDmgForm(copy);
        	} else {
        		Stage currentStage = (Stage) acceptButton.getScene().getWindow();
    			currentStage.close();
    			WearReportsDisplay dmgForm = new WearReportsDisplay();
    			dmgForm.showWearReports(wear);
        	}
        });

        Button rejectButton = new Button("Reject");
        rejectButton.getStyleClass().add("cancel-btn");
        rejectButton.setCursor(Cursor.HAND);
        rejectButton.setOnAction(e -> {
        	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });

        // HBox to contain the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(acceptButton, rejectButton);
        buttonBox.setAlignment(Pos.CENTER); // Align buttons to the center
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Add top padding
        buttonBox.setSpacing(150);
        
        hbox.getChildren().addAll(imageView, titleBox, copyDetails);
        bookDisplayArea.getChildren().addAll(prompt, hbox, buttonBox);
        bookDisplayArea.getStyleClass().add("main-vbox");
}

    public static void main(String[] args) {
        launch(args);
    }
}