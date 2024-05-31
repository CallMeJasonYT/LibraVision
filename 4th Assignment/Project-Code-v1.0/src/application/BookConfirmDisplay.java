package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookConfirmDisplay extends Application {
    // Declare FXML components
    @FXML
    private VBox bookDisplayArea;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label copyID;
    
    @FXML
    private Label copyISBN;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private Button rejectButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML and set up the primary stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookConfirm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Book Confirm");
            primaryStage.setScene(scene);
            primaryStage.show();

        // Initialize the controller
            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     // Method to show the book confirmation display in a new stage
    public void showBookConfDisplay(Copy copy) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookConfirm.fxml"));
            Parent root = loader.load();
            BookConfirmDisplay controller = loader.getController();
            
            // Load the copy details
            controller.loadCopy(copy);
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Book Confirm");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     // Method to load and display the copy details
    public void loadCopy(Copy copy) {
        // Load and display the book image
        Image image = new Image(getClass().getResourceAsStream(copy.getUrlToPhoto()));
        imageView.setImage(image);
        
        titleLabel.setText(copy.getTitle());
        copyID.setText("Copy ID: " + copy.getCopyID());
        copyISBN.setText("ISBN: " + copy.getIsbn());
        
        // Set action for the confirm button
        confirmButton.setOnAction(e -> {
        	List<Wear> wear = new ArrayList<>();
			try {
				wear = Wear.getWear(copy.getCopyID());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	if(wear.isEmpty()) {
        		Stage currentStage = (Stage) confirmButton.getScene().getWindow();
    			currentStage.close();
    			DmgReportForm dmgForm = new DmgReportForm();
    			dmgForm.showDmgForm(copy);
        	} else {
        		Stage currentStage = (Stage) confirmButton.getScene().getWindow();
    			currentStage.close();
    			WearReportsDisplay dmgForm = new WearReportsDisplay();
    			dmgForm.showWearReports(wear);
        	}
        });

        // Set action for the reject button
        rejectButton.setOnAction(e -> {
        	Stage currentStage = (Stage) confirmButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });
}

    public static void main(String[] args) {
        launch(args);
    }
}