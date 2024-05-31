package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BookCopyDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox bookCopyArea;
    
    @FXML
    private TextField bookInput;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML and set up the primary stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCopyDisplay.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            primaryStage.setTitle("Book Copy Display");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the book copy display in a new stage
    public void showBookCopyDisplay() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCopyDisplay.fxml"));
            Parent root = loader.load();
            BookCopyDisplay controller = loader.getController();
            
            controller.setBookCopyDisplay();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Book Copy Display");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to create an overlay pane for popups
    private Pane createOverlayPane(Scene scene) {
        Pane overlayPane = new Pane();
        overlayPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        overlayPane.setOpacity(0.7);
        overlayPane.setPrefSize(scene.getWidth(), scene.getHeight());
        return overlayPane;
    }
    
    // Method to set up the book copy display
    public void setBookCopyDisplay() {	
    	bookCopyArea.setSpacing(30);
        
        // Handle mouse click event on the book input field
        bookInput.setOnMouseClicked(event -> {
            if (bookInput.getText().equals("Copy ID")) {
            	bookInput.setText("");
            }
        });
        
        // Add listener to the book input field to toggle the visibility of the button box
        bookInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	buttonBox.setVisible(!newValue.isEmpty());
        });
    
        // Handle the continue button click event
        continueButton.setOnAction(event -> {
        	List<Copy> insCopy = new ArrayList<>();
        	Integer copyID = Integer.parseInt(bookInput.getText());
        	try {
				insCopy = Copy.searchCopy(copyID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
            // Check if the copy is found
        	if (insCopy.isEmpty()) {
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Error: There are not any books with this Copy ID. Please input the correct Copy ID");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) bookCopyArea.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = bookCopyArea.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, e -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	} else {
        		Stage stage = (Stage) continueButton.getScene().getWindow();
                stage.close();
                BookConfirmDisplay main = new BookConfirmDisplay();
    			main.showBookConfDisplay(insCopy.get(0));
        	}	
        });
        
        // Handle the cancel button click event
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

