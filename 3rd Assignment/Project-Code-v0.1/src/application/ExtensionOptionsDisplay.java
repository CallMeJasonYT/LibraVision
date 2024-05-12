package application;
import javafx.scene.Cursor; // Import statement for Cursor
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExtensionOptionsDisplay extends Application {
	
    @FXML
    private VBox optionPickArea; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExtensionOptions.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/extensionOptions.css").toExternalForm());
            primaryStage.setTitle("Extension Options");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    LocalDate selectedDate = null;
    private static User testUser = new User("Test User", 20);
    
    public void showOptions(List<LocalDate> openDates, Borrowing borrow) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExtensionOptions.fxml"));
            Parent root = loader.load();
            ExtensionOptionsDisplay controller = loader.getController();
            controller.loadExtOptions(openDates, borrow);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/extensionOptions.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Extension Options");
            newStage.setScene(scene);
            newStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadExtOptions(List<LocalDate> openDates, Borrowing borrow) {
        optionPickArea.setSpacing(50);

        Label titleLabel = new Label("Please select one Of the Following Options for Extension");
        titleLabel.getStyleClass().add("date-title");
        Label pointsLabel = new Label("Your current Points: " + testUser.getPoints());
        pointsLabel.getStyleClass().add("points-label");
        
        ObservableList<String> extensionRecords = FXCollections.observableArrayList();

        LocalDate borrowingEndDate = borrow.getBorrowingEnd().toLocalDate();
        extensionRecords.add("1 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 1) + ") - 3 Points");
        extensionRecords.add("3 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 3) + ") - 5 Points");
        extensionRecords.add("6 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 6) + ") - 9 Points");

        // Create ListView
        ListView<String> listView = new ListView<>(extensionRecords);
        listView.getStyleClass().add("list-view");
        listView.setFixedCellSize(50); // Adjust the cell size as needed
        listView.setPrefHeight(152);

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
                selectedDate = parseSelectedDate(newValue); // Parse the selected date from the string
                
                continueButton.setVisible(true); // Show continue button
                cancelButton.setVisible(true); // Show cancel button
            }
        });

        continueButton.setOnAction(event -> {
            int points = getPointsForIndex(listView.getSelectionModel().getSelectedIndex());
            testUser.updatePoints(testUser.getPoints()-points);
            borrow.updateBorrowing(borrow, Date.valueOf(selectedDate));
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.close();
            MainMenu main = new MainMenu();
			main.showMainPg();
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

        optionPickArea.getChildren().addAll(titleLabel, pointsLabel, listView, buttonBox);
        optionPickArea.getStyleClass().add("option-area");
    }
    
    private LocalDate getNextAvailableWorkingDate(LocalDate startDate, List<LocalDate> openDates, int numberOfDays) {
        LocalDate nextWorkingDate = startDate.plusDays(numberOfDays);
        while (!openDates.contains(nextWorkingDate)) {
            nextWorkingDate = nextWorkingDate.plusDays(1);
        }
        return nextWorkingDate;
    }
    
    private LocalDate parseSelectedDate(String selectedItem) {
        // Extract and parse the selected date from the list item
        String dateStr = selectedItem.split("\\(")[1].split("\\)")[0];
        return LocalDate.parse(dateStr);
    }

    private int getPointsForIndex(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                return 3;
            case 1:
                return 5;
            case 2:
                return 9;
            default:
                return 0; // Or handle this case as appropriate
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}

