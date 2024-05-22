package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LocationDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox optionPickArea;
    
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
    
    // Method to display the LocationDisplay window
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
    private static Member testMember = new Member("roubinie21", 20);
    
    // Method to set up the display logic for location options
    public void setLocDisplay() {
        // ObservableList for ListView items
    	ObservableList<String> extensionRecords = FXCollections.observableArrayList(
                "Wear noticed at the Library",
                "Wear noticed at Home"
            );
            listView.setItems(extensionRecords);
            
            // Listener to handle selection changes in the ListView
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selection = newValue;
                    buttonBox.setVisible(true);
                }
            });

        // Event handler for the continue button
        continueButton.setOnAction(event -> {
        	Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.close();
        	if(selection.equals("Wear noticed at the Library")) {
        		BookCopyDisplay bookCpyDisp = new BookCopyDisplay();
        		bookCpyDisp.showBookCopyDisplay();
        	}else {
        		List<Borrowing> curBorrowings = new ArrayList<>();
				try {
					curBorrowings = Borrowing.getCurBorrowings(testMember.getUsername());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
        		if(!curBorrowings.isEmpty()) {
            		CurrentBorrowingsDisplay curBorrowingsDisp = new CurrentBorrowingsDisplay();
                	curBorrowingsDisp.showCurBorrow(curBorrowings, "Wear");

            		Stage currentStage = (Stage) optionPickArea.getScene().getWindow();
            		currentStage.close();
            	}
        	}
        });

        // Event handler for the cancel button
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            MainMenu main = new MainMenu();
			main.showMainPg();
        });
     }

    public static void main(String[] args) {
        launch(args);
    }
}

