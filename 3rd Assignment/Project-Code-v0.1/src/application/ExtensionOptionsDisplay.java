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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExtensionOptionsDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox optionPickArea;
    
    @FXML 
    private Label pointsLabel;
    
    @FXML 
    private ListView<String> listView;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button cancelButton;
    
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
    
    // Method to show extension options for a borrowing
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
    
    // Method to load extension options
    public void loadExtOptions(List<LocalDate> openDates, Borrowing borrow) {
        pointsLabel.setText("Your current Points: " + testMember.getPoints());
        // Populate ListView with extension options
        ObservableList<String> extensionRecords = FXCollections.observableArrayList();
        LocalDate borrowingEndDate = borrow.getBorrowingEnd().toLocalDate();
        extensionRecords.add("1 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 1) + ") - 3 Points");
        extensionRecords.add("3 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 3) + ") - 5 Points");
        extensionRecords.add("6 Day Extension (" + getNextAvailableWorkingDate(borrowingEndDate, openDates, 6) + ") - 9 Points");

        listView.setItems(extensionRecords);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedDate = parseSelectedDate(newValue);
                buttonBox.setVisible(true);
            }
        });

        // Handle continue button action
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

        // Handle cancel button action
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            MainMenu main = new MainMenu();
			main.showMainPg();
        });
    }
    
     // Method to calculate the next available working date
    private LocalDate getNextAvailableWorkingDate(LocalDate startDate, List<LocalDate> openDates, int numberOfDays) {
        LocalDate nextWorkingDate = startDate.plusDays(numberOfDays);
        while (!openDates.contains(nextWorkingDate)) {
            nextWorkingDate = nextWorkingDate.plusDays(1);
        }
        return nextWorkingDate;
    }
    
    // Method to parse the selected date from the extension option
    private LocalDate parseSelectedDate(String selectedItem) {
    	String dateStr = selectedItem.split("\\(")[1].split("\\)")[0];
        return LocalDate.parse(dateStr);
    }

    // Method to get points for the selected extension option
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

