package application;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmgReportForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/dmgReportForm.css").toExternalForm());
            primaryStage.setTitle("Wear Report Form");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showDmgForm(Copy copy) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmgReportForm.fxml"));
            Parent root = loader.load();
            DmgReportForm controller = loader.getController();
            
            controller.loadForm(copy);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/dmgReportForm.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Wear Report Form");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
    
    public void loadForm(Copy copy) {
        acceptButton.setOnAction(e -> {
        	Wear wear = new Wear(copy.getCopyID(), testMember.getUsername(), pictureUrl, wearDetailsArea.getText(), Date.valueOf(LocalDate.now()));
        	Wear.insertWear(wear);
        	
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
            
            Scene currentScene = bookWearForm.getScene();
            Pane rootPane = (Pane) currentScene.getRoot();
            Pane overlay = createOverlayPane(currentScene);
            rootPane.getChildren().add(overlay);

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
        
        rejectButton.setOnAction(e -> {
        	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
			
        });

        wearDetailsArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                var1 = true;
                buttonBox.setVisible(var1 && var2);
            } else {
            	var1 = false;
            	buttonBox.setVisible(var1 && var2);
            }
        });
        
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