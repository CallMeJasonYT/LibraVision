package application;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DmgReportForm extends Application {
	// Declare FXML components
	@FXML
    private VBox dmgReportFormArea;
	
	@FXML
    private VBox bookWearForm;
	
	@FXML
	private TextArea wearDetailsArea;
    
    @FXML
    private Button uploadImageButton;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button acceptButton;
    
    @FXML
    private Button rejectButton;
    
    // Override the start method to set up the primary stage
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the damage report form interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmgReportForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            primaryStage.setTitle("Wear Report Form");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     // Method to display the damage report form for a given copy
    public void showDmgForm(Copy copy) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmgReportForm.fxml"));
            Parent root = loader.load();
            DmgReportForm controller = loader.getController();
            
            controller.loadForm(copy);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Wear Report Form");
            newStage.setScene(scene);            
            newStage.show();
            Platform.runLater(() -> controller.dmgReportFormArea.requestFocus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to create an overlay pane for the scene
    private Pane createOverlayPane(Scene scene) {
        Pane overlayPane = new Pane();
        overlayPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        overlayPane.setOpacity(0.7);
        overlayPane.setPrefSize(scene.getWidth(), scene.getHeight());
        return overlayPane;
    }
    
    boolean var1 = false;
    boolean var2 = false;
    String pictureUrl;
    private static Member testMember = new Member("roubinie21", 20);
    
    // Method to load the form with data for a given copy
    public void loadForm(Copy copy) {
        // Set the action for the accept button
        acceptButton.setOnAction(e -> {
            // Create a new Wear object and insert it into the database
        	Wear wear = new Wear(copy.getCopyID(), testMember.getUsername(), pictureUrl, wearDetailsArea.getText(), Date.valueOf(LocalDate.now()));
        	Wear.insertWear(wear);
        	
            // Create and configure a popup to show a success message
            Popup popup = new Popup();
            popup.setWidth(200);
            popup.setHeight(200);
            popup.setAutoHide(true);

            Label messageLabel = new Label("The Wear Report has been submitted succesfully. Redirecting to Main Menu...");
            messageLabel.getStyleClass().add("popup-label");
            popup.getContent().add(messageLabel);
            Stage curStage = (Stage) bookWearForm.getScene().getWindow();

            popup.setOnShown(r -> {
                popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
            });

            popup.show(curStage);
            
            // Create and show an overlay pane
            Scene currentScene = bookWearForm.getScene();
            Pane rootPane = (Pane) currentScene.getRoot();
            Pane overlay = createOverlayPane(currentScene);
            rootPane.getChildren().add(overlay);

            // Create a timeline to hide the popup and overlay after a delay, then redirect to the main menu
            Duration delay = Duration.seconds(5);
            KeyFrame keyFrame = new KeyFrame(delay, er -> {
            	popup.hide();
            	rootPane.getChildren().remove(overlay);
            	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
        		currentStage.close();
            	MainMenu main = new MainMenu();
            	main.showMainPg();
            	});
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();     
    });
        // Set the action for the reject button
        rejectButton.setOnAction(e -> {
        	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
			
        });

        // Add a listener to the wear details text area to validate input
        wearDetailsArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                var1 = true;
                buttonBox.setVisible(var1 && var2);
            } else {
            	var1 = false;
            	buttonBox.setVisible(var1 && var2);
            }
        });
        
        // Set the action for the upload image button
        uploadImageButton.setOnAction(e -> {
        	pictureUrl = getPicture();
        	var2 = true;
        	buttonBox.setVisible(var1 && var2);
        });
}
    public String getPicture() {
    	return "/misc/wornBook.jpg";
    }

    public static void main(String[] args) {
        launch(args);
    }
}