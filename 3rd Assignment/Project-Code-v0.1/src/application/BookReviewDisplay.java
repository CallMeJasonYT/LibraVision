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

public class BookReviewDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox ratingArea;
    
    @FXML
    private Rating reviewStarArea;
    
    @FXML
    private TextArea reviewTextArea;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookReview.fxml"));
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
    
    Member testMember = new Member("roubinie21", 20);
    
    // Method to display the book review screen for the given borrowing
    public void showBookReview(Borrowing borrowing) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookReview.fxml"));
            Parent root = loader.load();
            BookReviewDisplay controller = loader.getController();
            
            controller.setBookReview(borrowing);
            
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
    
    // Method to set the book review form and show the experience review dialog
    public void setBookReview(Borrowing borrowing) {
    	submitButton.setOnMouseClicked(e -> {// Set up the submit button action
    		if(checkFields()) {
                // Create a new BookReview object and insert it into the database
        		BookReview bookRev = new BookReview(borrowing.getCopy().getIsbn(), Date.valueOf(LocalDate.now()), reviewStarArea.getRating(), testMember.getUsername(), reviewTextArea.getText());
        		BookReview.insertBookRev(bookRev);

                // Show the experience review dialog
        		ExperienceReviewDialog diag = new ExperienceReviewDialog();
        		diag.showExpRevDiag(testMember);
        		Stage oldStage = (Stage) ratingArea.getScene().getWindow();
        		oldStage.close();
        	} else { // Show a popup message if the star rating is not set
        		Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Please insert a Star Rating for the Selected Book.");
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
    	
        // Set up the cancel button action
    	cancelButton.setOnMouseClicked(e -> {
    		Stage oldStage = (Stage) ratingArea.getScene().getWindow();
    		oldStage.close();
    		MainMenu main = new MainMenu();
			main.showMainPg();
    	});
    }
    
    // Method to check if the star rating is set
    public boolean checkFields() {
    	return (reviewStarArea.getRating() != 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}