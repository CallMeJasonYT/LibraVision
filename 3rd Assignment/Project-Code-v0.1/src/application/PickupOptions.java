package application;
import javafx.scene.Cursor; // Import statement for Cursor
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PickupOptions extends Application {
	
    @FXML
    private VBox datePickArea; // The UI component to display book data
    
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
    
    public void loadDates(List<LocalDate> openDates, Book book) {
    	
    	Label titleLabel = new Label("Please select an Available Date to pickup your Book");
        titleLabel.getStyleClass().add("date-title");
    	LocalDate firstWorkingDay = openDates.get(0);

    	DatePicker datePicker = new DatePicker();
        
        VBox dateVbox = new VBox(datePicker);
        datePickArea.setSpacing(150);
        
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
        
        datePicker.setOnAction(event -> {
            selectedDate = datePicker.getValue();
        });
        
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);

        continueButton.setOnAction(event -> {
        	try {
        		Stage oldStage = (Stage) continueButton.getScene().getWindow();
        		oldStage.close();
        		
        		PointLossWarning pointLossWarning = new PointLossWarning();
        		pointLossWarning.showPointWarn("Please note that missing the reservation deadline may result in a points penalty."
            			+ "\r\n"
            			+ "Would you like to proceed with completing the reservation?", book, selectedDate, null);
			} catch (IOException e) {
				e.printStackTrace();
			}  
        });
        
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-btn");
        cancelButton.setCursor(Cursor.HAND);
        
        cancelButton.setOnAction(e -> {
        	Stage currentStage = (Stage) cancelButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(continueButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.setSpacing(150);
        
        dateVbox.getStyleClass().add("date-vbox");
        datePicker.getStyleClass().add("date-picker");
        datePickArea.getStyleClass().add("date-area");
        datePickArea.getChildren().addAll(titleLabel, dateVbox, buttonBox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

