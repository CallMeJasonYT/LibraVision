package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
	
    @FXML
    private VBox bookCopyArea;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCopyDisplay.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCopyDisplay.css").toExternalForm());
            primaryStage.setTitle("Book Copy Display");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showBookCopyDisplay() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCopyDisplay.fxml"));
            Parent root = loader.load();
            BookCopyDisplay controller = loader.getController();
            
            controller.setBookCopyDisplay();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCopyDisplay.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Book Copy Display");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Create overlay pane method
    private Pane createOverlayPane(Scene scene) {
        Pane overlayPane = new Pane();
        overlayPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        overlayPane.setOpacity(0.7);
        overlayPane.setPrefSize(scene.getWidth(), scene.getHeight());
        return overlayPane;
    }
    
    public void setBookCopyDisplay() {	
    	bookCopyArea.setSpacing(30);
    	
    	Label titleLabel = new Label("Please insert the Copy ID");
        titleLabel.getStyleClass().add("window-title");
        
        TextField bookInput = new TextField("Copy ID");
        bookInput.getStyleClass().add("text-input");

        bookInput.setOnMouseClicked(event -> {
            if (bookInput.getText().equals("Copy ID")) {
            	bookInput.setText("");
            }
        });
        
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-btn");
        cancelButton.setCursor(Cursor.HAND);
        
        HBox buttonBox = new HBox(10); // Set spacing between buttons
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(continueButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Set top padding
        buttonBox.setAlignment(Pos.CENTER); // Align buttons to the center
        buttonBox.setSpacing(150);
        buttonBox.setVisible(false);
        
        bookCopyArea.getChildren().addAll(titleLabel, bookInput, buttonBox);
        bookCopyArea.getStyleClass().add("input-area");
        
        bookInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                buttonBox.setVisible(true);
            } else {
                buttonBox.setVisible(false);
            }
        });
        
        continueButton.setOnAction(event -> {
        	List<Copy> insCopy = new ArrayList<>();
        	List<Integer> copyID = new ArrayList<>(); 
        	copyID.add(Integer.parseInt(bookInput.getText()));
        	insCopy = Copy.searchCopy(copyID);
        	
        	if (insCopy.size() != copyID.size()) {
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

