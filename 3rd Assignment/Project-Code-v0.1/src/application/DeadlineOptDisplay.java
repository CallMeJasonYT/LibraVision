package application;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeadlineOptDisplay extends Application {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DeadlineOptions.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/deadlineOptions.css").toExternalForm());
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    LocalDate selectedDate = null;
    
    // Method to show the deadline options display
    public void showDeadline(List<Copy> copies, Member member, List<LocalDate> openDates) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DeadlineOptions.fxml"));
            Parent root = loader.load();
            DeadlineOptDisplay controller = loader.getController();
            controller.loadDates(copies, member, openDates);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/deadlineOptions.css").toExternalForm());
            Stage newStage = new Stage();
    		newStage.setTitle("Book Details");
            newStage.setScene(scene);
            newStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to load available dates for borrowing
    public void loadDates(List<Copy> copies, Member member, List<LocalDate> openDates) {
    	Label titleLabel = new Label("Please select an Available Date to pickup your Book");
        titleLabel.getStyleClass().add("date-title");
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

                if (date.isAfter(firstWorkingDay.plusDays(14))) {
                    setDisable(true);
                }
            }
        });
        
        // Event handler for date selection
        datePicker.setOnAction(event -> {
        	selectedDate = datePicker.getValue();
        	buttonBox.setVisible(true);
        });

        // Event handler for continue button
        continueButton.setOnAction(event -> {
        	List<Borrowing> newBorrows = new ArrayList<>();
        	for (Copy copy : copies) {
        		member.setPoints(member.getPoints()-1);
        		member.updatePoints(member);
        		Borrowing newBorrow = new Borrowing(copy, member.getUsername(), Date.valueOf(LocalDate.now()), Date.valueOf(selectedDate));
        		newBorrows.add(newBorrow);
        	}
        	Borrowing.insertBorrowing(newBorrows);
    		Stage oldStage = (Stage) continueButton.getScene().getWindow();
    		oldStage.close();
    		
    		LibrarianMainMenu main = new LibrarianMainMenu();
    		main.showLibMainPg();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

