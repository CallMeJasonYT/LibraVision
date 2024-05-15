package application;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LocationDisplay extends Application {
	
    @FXML
    private VBox optionPickArea; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LocationDisplay.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/locationDisplay.css").toExternalForm());
            primaryStage.setTitle("Location Display");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showLocDisplay() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LocationDisplay.fxml"));
            Parent root = loader.load();
            LocationDisplay controller = loader.getController();
            
            controller.setLocDisplay();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/locationDisplay.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Location Display");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    String selection = null;
    
    public void setLocDisplay() {	
    	optionPickArea.setSpacing(50);

        Label titleLabel = new Label("Please select one Of the Following Options for Extension");
        titleLabel.getStyleClass().add("date-title");
        
    	ObservableList<String> extensionRecords = FXCollections.observableArrayList();

        extensionRecords.add("Wear noticed at the Library");
        extensionRecords.add("Wear noticed at Home");

        // Create ListView
        ListView<String> listView = new ListView<>(extensionRecords);
        listView.getStyleClass().add("list-view");
        listView.setFixedCellSize(50); // Adjust the cell size as needed
        listView.setPrefHeight(102);

        // Create continue button
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setVisible(false); // Initially hidden

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-btn");
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setVisible(false); // Initially hidden

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	selection = newValue;
                continueButton.setVisible(true); // Show continue button
                cancelButton.setVisible(true); // Show cancel button
            }
        });

        continueButton.setOnAction(event -> {
        	Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.close();
        	if(selection.equals("Wear noticed at the Library")) {
        		BookCopyDisplay bookCpyDisp = new BookCopyDisplay();
        		bookCpyDisp.showBookCopyDisplay();
        	}else {
        		List<Borrowing> curBorrowings = Borrowing.getBorrowings();
        		for(Borrowing bor : curBorrowings) {
        			if(bor.getBorrowingEnd().before(Date.valueOf(LocalDate.now()))) {
        				curBorrowings.remove(bor);
        			}
        		}
        		if(!curBorrowings.isEmpty()) {
            		CurrentBorrowingsDisplay curBorrowingsDisp = new CurrentBorrowingsDisplay();
                	curBorrowingsDisp.showCurBorrow(curBorrowings, "Wear");

            		Stage currentStage = (Stage) optionPickArea.getScene().getWindow();
            		currentStage.close();
            	}
        	}
        });

        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            MainMenu main = new MainMenu();
			main.showMainPg();
        });

        HBox buttonBox = new HBox(10); // Set spacing between buttons
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(continueButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Set top padding
        buttonBox.setSpacing(150);

        optionPickArea.getChildren().addAll(titleLabel, listView, buttonBox);
        optionPickArea.getStyleClass().add("option-area");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

