package application;
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

public class AvailabilityNotifyDialog {
    private static Member testMember = new Member("roubinie21", 20);
    
    // Method to display a notification dialog when a book is not available
    public void showNotifDialog(Book book) {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Warning Message");

            Label warnLabel = new Label("Error: There aren't any Copies of this Book Available."
            		+ "Would you like to get notified when a copy of the book is Available?"); 
            
            warnLabel.getStyleClass().add("warn-label");

            // Create the "Accept" button and configure its properties
            Button acceptButton = new Button("Accept");
            acceptButton.getStyleClass().add("continue-btn");
            acceptButton.setCursor(Cursor.HAND);
            acceptButton.setOnAction(e -> {
            	Notification notif = new Notification(testMember.getUsername(), book);
				Notification.insertNotification(notif);
				
				Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();
				MainMenu main = new MainMenu();
				main.showMainPg();
            });

            // Create the "Reject" button and configure its properties
            Button rejectButton = new Button("Reject");
            rejectButton.getStyleClass().add("cancel-btn");
            rejectButton.setCursor(Cursor.HAND);
            rejectButton.setOnAction(e -> {
            	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();
				MainMenu main = new MainMenu();
				main.showMainPg();
            });

            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(acceptButton, rejectButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10, 0, 0, 0));
            buttonBox.setSpacing(150);
            
            VBox layout = new VBox(25);
            layout.setSpacing(25);
            layout.getChildren().addAll(warnLabel, buttonBox);
            layout.setAlignment(Pos.CENTER);
            layout.getStyleClass().add("layout");
            Scene scene = new Scene(layout);
            scene.getStylesheets().add(getClass().getResource("/styles/pointLossWarn.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }
}
