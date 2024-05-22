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

public class ExperienceReviewDialog {

    // Method to show the experience review dialog
    public void showExpRevDiag(Member member) {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Warning Message");

            Label warnLabel = new Label("Would you like to fill out a quick survey about your borrowing experience?"); 
            
            warnLabel.getStyleClass().add("warn-label");

            // Create the accept button with styles and event handler
            Button acceptButton = new Button("Accept");
            acceptButton.getStyleClass().add("continue-btn");
            acceptButton.setCursor(Cursor.HAND);
            acceptButton.setOnAction(e -> {
				Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();
				ExperienceReviewDisplay displ = new ExperienceReviewDisplay();
				displ.showExpReview(member);
            });

            // Create the reject button with styles and event handler
            Button rejectButton = new Button("Reject");
            rejectButton.getStyleClass().add("cancel-btn");
            rejectButton.setCursor(Cursor.HAND);
            rejectButton.setOnAction(e -> {
                // Award points to the member and update their points in the system
            	member.setPoints(member.getPoints() + 5);
            	member.updatePoints(member);
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
