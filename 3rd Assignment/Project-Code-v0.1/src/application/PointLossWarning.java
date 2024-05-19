package application;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

public class PointLossWarning {

    public void showPointWarn(String message, Book book, LocalDate selDate, Borrowing borrow) throws IOException {
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
            acceptButton.setCursor(Cursor.HAND); 
            acceptButton.setOnAction(e -> {
            	if(borrow == null) {
	            	Reservation res = new Reservation(book, "Test", Date.valueOf(selDate), Date.valueOf(LocalDate.now()));
					Reservation.createRes(res);
					Stage currentStage = (Stage) acceptButton.getScene().getWindow();
					currentStage.close();
					MainMenu main = new MainMenu();
					main.showMainPg();
				} else {
					List<LocalDate> openDates = new ArrayList<>();
					try {
						openDates = Library.getOpenDates("Roumpini's Library");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					Stage oldStage = (Stage) warnLabel.getScene().getWindow();
            		oldStage.close();
            		
            		ExtensionOptionsDisplay extOptions = new ExtensionOptionsDisplay();
            		extOptions.showOptions(openDates, borrow);
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
