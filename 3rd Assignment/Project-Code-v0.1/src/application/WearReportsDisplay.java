package application;
import java.io.IOException;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WearReportsDisplay extends Application {
	
    @FXML
    private VBox wearReports;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label wearDetails;
    
    @FXML
    private Label submissionLabel;
    
    @FXML
    private Button extendButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WearReports.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Wear Reports");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showWearReports(List<Wear> wear) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WearReports.fxml"));
            Parent root = loader.load();
            WearReportsDisplay controller = loader.getController();
            
            controller.setWearReports(wear);
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Wear Reports");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setWearReports(List<Wear> wear) {
        for (Wear w : wear) {
            Image image = new Image(w.getUrlToPhoto());
            imageView.setImage(image);
            titleLabel.setText("Copy ID: " + w.getCopyID());
            wearDetails.setText("Wear Description: " + w.getDetails()); 
            submissionLabel.setText("Submission Date: " + w.getSubmissionDate());

            extendButton.setOnAction(event -> {
                Stage oldStage = (Stage) wearReports.getScene().getWindow();
                oldStage.close();
                MainMenu main = new MainMenu();
                main.showMainPg();
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}