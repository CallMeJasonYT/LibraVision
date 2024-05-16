package application;
import java.io.IOException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddBooksQuestion {

    public void showAddBooksQuestion(String catName, String username) throws IOException {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Question Message");

            Label warnLabel = new Label("Would you like to add books in your New Category?");
            
            warnLabel.getStyleClass().add("warn-label");

            Button acceptButton = new Button("Accept");
            acceptButton.getStyleClass().add("continue-btn");
            acceptButton.setCursor(Cursor.HAND); 
            acceptButton.setOnAction(e -> {
            	AddBooksDisplay display = new AddBooksDisplay();
				display.showAddBooks(catName, username);
            	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();	
            });

            Button rejectButton = new Button("Reject");
            rejectButton.getStyleClass().add("cancel-btn");
            rejectButton.setCursor(Cursor.HAND);
            rejectButton.setOnAction(e -> {
            	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();
				
				BookCategoryDetails display = new BookCategoryDetails();
				display.showBookCatDetails(new BookCategory(null, username, catName, "/misc/bookCategory.jpg" ));
            });

            // HBox to contain the buttons
            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(acceptButton, rejectButton);
            buttonBox.setAlignment(Pos.CENTER); // Align buttons to the center
            buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Add top padding
            buttonBox.setSpacing(150);
            
            // VBox to contain label and buttonBox
            VBox layout = new VBox(25);
            layout.setSpacing(25);
            layout.getChildren().addAll(warnLabel, buttonBox);
            layout.setAlignment(Pos.CENTER); // Align VBox to the center
            layout.getStyleClass().add("layout");
            Scene scene = new Scene(layout);
            scene.getStylesheets().add(getClass().getResource("/styles/pointLossWarn.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }
}
