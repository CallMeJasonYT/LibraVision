package application;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import org.controlsfx.control.Rating;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExperienceReviewDisplay extends Application {
	
    @FXML
    private VBox ratingArea;
    
    @FXML
    private Rating appStarArea;
    
    @FXML
    private Rating staffStarArea;
    
    @FXML
    private Rating bookStarArea;
    
    @FXML
    private TextArea reviewTextArea;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienceReview.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/currentBorrowings.css").toExternalForm());
            primaryStage.setTitle("Book Review");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
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
    
    public void showExpReview(Member member) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienceReview.fxml"));
            Parent root = loader.load();
            ExperienceReviewDisplay controller = loader.getController();
            
            controller.setExpReview(member);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/currentBorrowings.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Book Review");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setExpReview(Member member) {
    	submitButton.setOnMouseClicked(e -> {
    		if(checkFields()) {
        		ExperienceReview expRev = new ExperienceReview(appStarArea.getRating(), staffStarArea.getRating(), bookStarArea.getRating(), reviewTextArea.getText(), member.getUsername(), Date.valueOf(LocalDate.now()));
        		ExperienceReview.insertExpRev(expRev);
        		
        		member.setPoints(member.getPoints() + 10);
            	member.updatePoints(member);
            	
            	Stage currentStage = (Stage) submitButton.getScene().getWindow();
				currentStage.close();
				MainMenu main = new MainMenu();
				main.showMainPg();
        	} else {
        		Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Please insert a Star Rating in every field.");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) ratingArea.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = ratingArea.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, er -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	}
        });
    }
    
    public boolean checkFields() {
    	return (appStarArea.getRating() != 0 && staffStarArea.getRating() != 0 && bookStarArea.getRating() != 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}