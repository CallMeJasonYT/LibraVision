package application;
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
    private static Member testMember = new Member("roubinie21", 20);
    // Method to display a warning message regarding potential point loss
    public void showPointWarn(String message, Book book, LocalDate selDate, Borrowing borrow) {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Warning Message");

            Label warnLabel = new Label(message);
            
            warnLabel.getStyleClass().add("warn-label");
            // Create buttons for user response
            Button acceptButton = new Button("Accept");
            acceptButton.getStyleClass().add("continue-btn");
            acceptButton.setCursor(Cursor.HAND); 
            acceptButton.setOnAction(e -> {
                // If no borrowing object is provided, create a reservation
            	if(borrow == null) {
	            	Reservation res = new Reservation(book, testMember.getUsername(), Date.valueOf(selDate), Date.valueOf(LocalDate.now()));
					Reservation.insertRes(res);
					Stage currentStage = (Stage) acceptButton.getScene().getWindow();
					currentStage.close();
					MainMenu main = new MainMenu();
					main.showMainPg();
				} else {// If borrowing object is provided, display extension options
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

            // Create reject button to decline the action
            Button rejectButton = new Button("Reject");
            rejectButton.getStyleClass().add("cancel-btn");
            rejectButton.setCursor(Cursor.HAND);
            rejectButton.setOnAction(e -> {
            	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
				currentStage.close();
				MainMenu main = new MainMenu();
				main.showMainPg();
            });

            // Create an HBox to hold the buttons
            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(acceptButton, rejectButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10, 0, 0, 0));
            buttonBox.setSpacing(150);
            
            // Create a VBox to hold the warning message and button box
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
