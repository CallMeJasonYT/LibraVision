package application;

import java.io.IOException;
import java.time.LocalDate;
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

public class LibraryMemberDisplay extends Application {
	
    @FXML
    private VBox newUserArea; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibraryMember.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/libraryMember.css").toExternalForm());
            primaryStage.setTitle("New Borrowing");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public void showMemberInfo(List<Copy> copies) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibraryMember.fxml"));
            Parent root = loader.load();
            LibraryMemberDisplay controller = loader.getController();
            
            controller.setUserPrompt(copies);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/libraryMember.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("New Borrowing");
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
    
    public void setUserPrompt(List<Copy> copies) {
    	newUserArea.setSpacing(50);        
       
        Label titleLabel = new Label("Please insert the Member Username");
        titleLabel.getStyleClass().add("window-title");
        
        //Make the txtfield so when you click it the placeholder leaves
        TextField usernameInput = new TextField("Member Username");
        usernameInput.getStyleClass().add("text-input");

        usernameInput.setOnMouseClicked(event -> {
            if (usernameInput.getText().equals("Member Username")) {
            	usernameInput.setText("");
            }
        });
        
        // Create continue button
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setVisible(false); // Initially hidden 

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-btn");
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setVisible(false); // Initially hidden
        
     // Add listener to the text property of the TextField
        usernameInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                continueButton.setVisible(true); // Show continue button if there is text
                cancelButton.setVisible(true); // Show cancel button if there is text
            } else {
                continueButton.setVisible(false); // Hide continue button if there is no text
                cancelButton.setVisible(false); // Hide cancel button if there is no text
            }
        });
        
        HBox buttonBox = new HBox(10); // Set spacing between buttons
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(continueButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Set top padding
        buttonBox.setAlignment(Pos.CENTER); // Align buttons to the center
        buttonBox.setSpacing(150);
        
        newUserArea.getChildren().addAll(titleLabel, usernameInput, buttonBox);
        newUserArea.getStyleClass().add("input-area");
        
        continueButton.setOnAction(event -> {
        	User curUser = User.userExist("Test");
        	if (curUser.getUsername() == null) {
        		Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                // Create the label with your message
                Label messageLabel = new Label("Error: The Username you inserted is wrong. Please input the correct Username");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) newUserArea.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = newUserArea.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                // Hide the popup after 5 seconds
                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, e -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	}else {
        		List<LocalDate> dates = Library.getOpenDates();
        		Stage stage = (Stage) continueButton.getScene().getWindow();
                stage.close();
                DeadlineOptDisplay main = new DeadlineOptDisplay();
    			main.showDeadline(copies, curUser, dates);
        	}
        	//if (insCopies.isEmpty()) {
        		//Show a popup error Message
        	//}else {
        		
                //LibraryMemberDisplay main = new LibraryMemberDisplay();
    			//main.showMemberInfo();
        	//}	
        });

        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            LibrarianMainMenu main = new LibrarianMainMenu();
			main.showLibMainPg();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

