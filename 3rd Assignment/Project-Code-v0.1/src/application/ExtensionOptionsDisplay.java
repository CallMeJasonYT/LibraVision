package application;
import javafx.scene.Cursor;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExtensionOptionsDisplay extends Application {
	
    @FXML
    private VBox optionPickArea;
    
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
    private static Member testMember = new Member("roubinie21", 20);
    
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
        Label pointsLabel = new Label("Your current Points: " + testMember.getPoints());
        pointsLabel.getStyleClass().add("points-label");
        
        ObservableList<String> extensionRecords = FXCollections.observableArrayList();
        LocalDate borrowingEndDate = borrow.getBorrowingEnd().toLocalDate();
        extensionRecords.add("1 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 1) + ") - 3 Points");
        extensionRecords.add("3 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 3) + ") - 5 Points");
        extensionRecords.add("6 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 6) + ") - 9 Points");

        ListView<String> listView = new ListView<>(extensionRecords);
        listView.getStyleClass().add("list-view");
        listView.setFixedCellSize(50);
        listView.setPrefHeight(152);

        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setVisible(false);

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-btn");
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setVisible(false);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedDate = parseSelectedDate(newValue);
                
                continueButton.setVisible(true);
                cancelButton.setVisible(true);
            }
        });

        continueButton.setOnAction(event -> {
            int points = getPointsForIndex(listView.getSelectionModel().getSelectedIndex());
            testMember.setPoints(testMember.getPoints()-points);
            testMember.updatePoints(testMember);
            borrow.setBorrowingEnd(Date.valueOf(selectedDate));
            Borrowing.updateBorrowing(borrow);
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

        HBox buttonBox = new HBox(10);
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(continueButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
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
                return 0;
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}

