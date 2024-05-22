package application;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PickupOptions extends Application {
	// Declare FXML components
    @FXML
    private VBox datePickArea;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PickupOptions.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/pickupOptions.css").toExternalForm());
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    LocalDate selectedDate = null;
    
    // Method to show pickup options in a new stage
    public void showPickOpt(List<LocalDate> openDates, Book book) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PickupOptions.fxml"));
            Parent root = loader.load();
            PickupOptions controller = loader.getController();
            controller.loadDates(openDates, book);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/pickupOptions.css").toExternalForm());
            Stage newStage = new Stage();
    		newStage.setTitle("Book Details");
            newStage.setScene(scene);
            newStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to load available dates into the date picker
    public void loadDates(List<LocalDate> openDates, Book book) {
    	LocalDate firstWorkingDay = openDates.get(0);
        
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }

                if (!openDates.contains(date)) {
                    setDisable(true);
                }

                if (date.isAfter(firstWorkingDay.plusDays(7))) {
                    setDisable(true);
                }
            }
        });
        
        // Handle action when a date is selected
        datePicker.setOnAction(event -> {
	        selectedDate = datePicker.getValue();
	        buttonBox.setVisible(true);
        });
        
        // Handle action when continue button is clicked
        continueButton.setOnAction(event -> {
        	Stage oldStage = (Stage) continueButton.getScene().getWindow();
			oldStage.close();
			
			PointLossWarning pointLossWarning = new PointLossWarning();
			pointLossWarning.showPointWarn("Please note that missing the reservation deadline may result in a points penalty."
					+ "\r\n"
					+ "Would you like to proceed with completing the reservation?", book, selectedDate, null);  
        });
        
        // Handle action when cancel button is clicked
        cancelButton.setOnAction(e -> {
        	Stage currentStage = (Stage) cancelButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

