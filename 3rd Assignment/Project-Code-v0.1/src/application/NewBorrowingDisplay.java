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

public class NewBorrowingDisplay extends Application {
	
    @FXML
    private VBox newBorrowingArea; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewBorrowing.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/newBorrowing.css").toExternalForm());
            primaryStage.setTitle("New Borrowing");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showNewBorrow() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewBorrowing.fxml"));
            Parent root = loader.load();
            NewBorrowingDisplay controller = loader.getController();
            
            controller.setBorrowings();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/newBorrowing.css").toExternalForm());
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
    
    public void setBorrowings() {
    	newBorrowingArea.setSpacing(50);        
       
        Label titleLabel = new Label("Please insert the CopyIDs of the books");
        titleLabel.getStyleClass().add("window-title");
        
        TextField bookInput = new TextField("Copy IDs");
        bookInput.getStyleClass().add("text-input");

        bookInput.setOnMouseClicked(event -> {
            if (bookInput.getText().equals("Copy IDs")) {
                bookInput.setText("");
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
        bookInput.textProperty().addListener((observable, oldValue, newValue) -> {
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
        
        newBorrowingArea.getChildren().addAll(titleLabel, bookInput, buttonBox);
        newBorrowingArea.getStyleClass().add("input-area");
        
        continueButton.setOnAction(event -> {
        	List<Copy> insCopies = new ArrayList<>();
        	List<Integer> copyIDs = parseInputText(bookInput.getText());
        	try {
				insCopies = Copy.searchCopy(copyIDs.get(0));
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        	if (insCopies.size() != copyIDs.size()) {
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

        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            LibrarianMainMenu main = new LibrarianMainMenu();
			main.showLibMainPg();
        });
    }

    
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

