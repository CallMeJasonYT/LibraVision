package application;
import java.io.IOException;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FinalDonationForm extends Application {
	// Declare FXML components
	@FXML
    private VBox donationFormArea;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FinalDonationForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/finaldonationForm.css").toExternalForm());
            primaryStage.setTitle("Final Donation Form");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the final donation form
    public void showFinalForm(List<Donation> donations) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FinalDonationForm.fxml"));
            Parent root = loader.load();
            FinalDonationForm controller = loader.getController();
            
            controller.loadFinalForm(donations);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/finaldonationForm.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Donation Form");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to load the final donation form with donation information
    public void loadFinalForm(List<Donation> donations) {
    	for (Donation don : donations) {
            HBox hbox = new HBox(75);
            Label donNum = new Label("Donation Number: " + donations.indexOf(don));
            donNum.getStyleClass().add("book-isbn");

            VBox titleBox = new VBox(0);
            titleBox.getStyleClass().add("book-isbn");
            Label titleLabel = new Label("Book ISBN: " + don.getIsbn()); 
            titleLabel.setWrapText(true);
            titleBox.getChildren().add(titleLabel);
            titleBox.setPrefWidth(150);
            
            Label donAmount = new Label("Donated Amount: " + don.getBookNum());
            donAmount.getStyleClass().add("book-isbn");

            hbox.getChildren().addAll(donNum, titleBox, donAmount);
            donationFormArea.getChildren().add(hbox);
        }

            Duration delay = Duration.seconds(5);
            KeyFrame keyFrame = new KeyFrame(delay, er -> {
            	Stage oldStage = (Stage) donationFormArea.getScene().getWindow();
        		oldStage.close();
        		MainMenu main = new MainMenu();
    			main.showMainPg();
            	});
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
}

    public static void main(String[] args) {
        launch(args);
    }
}