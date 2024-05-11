package application;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PointLossWarning {

    public void showPointWarn(String message) {
        // Ensure this runs on the JavaFX application thread
        Platform.runLater(() -> {
            Stage stage = new Stage();

            // Set the modality to block interaction with other windows
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Warning Message");

            Label warnLabel = new Label(message);
            
            warnLabel.getStyleClass().add("warn-label");

            Button acceptButton = new Button("Accept");
            acceptButton.getStyleClass().add("continue-btn");
            acceptButton.setOnAction(e -> {
            	try {
                    // Create the FXMLLoader for the book details
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
                    Parent root = loader.load();
                    MainMenu controller = loader.getController();
                    controller.loadMenu();
                    
                    Scene scene = new Scene(root);
                    Stage newStage = new Stage();
                    Stage currentStage = (Stage) acceptButton.getScene().getWindow();
                    showMainPg(newStage, currentStage, scene);
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            Button rejectButton = new Button("Reject");
            rejectButton.getStyleClass().add("cancel-btn");
            rejectButton.setOnAction(e -> {
            	try {
                    // Create the FXMLLoader for the book details
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
                    Parent root = loader.load();
                    MainMenu controller = loader.getController();
                    controller.loadMenu();
                    
                    Scene scene = new Scene(root);
                    Stage newStage = new Stage();
                    Stage currentStage = (Stage) acceptButton.getScene().getWindow();
                    showMainPg(newStage, currentStage, scene);
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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

	private void showMainPg(Stage newStage, Stage oldStage, Scene scene) {
		newStage.setTitle("Book Details");
        newStage.setScene(scene);
        oldStage.close();
        newStage.show();
		
	}
}
