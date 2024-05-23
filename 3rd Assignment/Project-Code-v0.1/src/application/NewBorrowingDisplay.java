package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

public class NewBorrowingDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox newBorrowingArea;
    
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewBorrowing.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            primaryStage.setTitle("New Borrowing");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the New Borrowing form
    public void showNewBorrow() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewBorrowing.fxml"));
            Parent root = loader.load();
            NewBorrowingDisplay controller = loader.getController();
            
            controller.setBorrowings();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("New Borrowing");
            newStage.setScene(scene);            
            newStage.show();
            Platform.runLater(() -> controller.newBorrowingArea.requestFocus());
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
    
    // Method to set the borrowing form
    public void setBorrowings() {
        // Event listener for the book input field
        bookInput.setOnMouseClicked(event -> {
            if (bookInput.getText().equals("Copy IDs")) {
                bookInput.setText("");
            }
        });
        
        bookInput.textProperty().addListener((observable, oldValue, newValue) -> buttonBox.setVisible(!newValue.isEmpty()));
        // Handle continue button action
        continueButton.setOnAction(event -> {
        	List<Copy> insCopies = new ArrayList<>();
        	List<Integer> copyIDs = parseInputText(bookInput.getText());
        	try {
				insCopies = Copy.getCopies(copyIDs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        	if (insCopies.size() != copyIDs.size()) {
                 // Display error message if any copy ID is invalid
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Error: One of the Copy IDs were wrong. Please input the correct Copy IDs");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) newBorrowingArea.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = newBorrowingArea.getScene();
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
                LibraryMemberDisplay main = new LibraryMemberDisplay();
    			main.showMemberInfo(insCopies);
        	}	
        });

        // Handle cancel button action
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            LibrarianMainMenu main = new LibrarianMainMenu();
			main.showLibMainPg();
        });
    }
    
    // Method to parse input text containing copy IDs
    public static List<Integer> parseInputText(String inputText) {
        List<Integer> resultList = new ArrayList<>();

        String[] tokens = inputText.split(",");
        for (String token : tokens) {
            resultList.add(Integer.parseInt(token.trim()));
        }
        return resultList;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

