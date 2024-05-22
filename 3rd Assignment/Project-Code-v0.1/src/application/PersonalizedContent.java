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

public class PersonalizedContent {

    // Method to show a dialog asking the user if they want personalized book suggestions
    public void showPerContDiag(User user) {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Question Message");

            Label warnLabel = new Label("Would you like to get Personalized Book Suggestions, by creating a Profile?");
            
            warnLabel.getStyleClass().add("warn-label");

            // Create the "Accept" button and configure its properties
            Button acceptButton = new Button("Accept");
            acceptButton.getStyleClass().add("continue-btn");
            acceptButton.setCursor(Cursor.HAND);
            acceptButton.setOnAction(e -> {
            	PreferencesForm display = new PreferencesForm();
				display.showPrefForm(user);
            	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();
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
