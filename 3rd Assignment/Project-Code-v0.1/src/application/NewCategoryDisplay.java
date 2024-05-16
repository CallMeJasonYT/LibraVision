package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

public class NewCategoryDisplay extends Application {
	
    @FXML
    private VBox categoryTitleArea;
    
    @FXML
    private TextField catTitleField;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewCategory.fxml"));
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
    
    public void showNewCatDisplay(List<BookCategory> bookCats) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewCategory.fxml"));
            Parent root = loader.load();
            NewCategoryDisplay controller = loader.getController();
            
            controller.setNewCat(bookCats);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCopyDisplay.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("New Category Display");
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
    
    public void setNewCat(List<BookCategory> bookCats) {
    	catTitleField.setOnMouseClicked(event -> {
            if (catTitleField.getText().equals("Category Title")) {
            	catTitleField.setText("");
            }
        });

        // Create continue button
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);

        // Create cancel button
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
        
    	catTitleField.textProperty().addListener((observable, oldValue, newValue) -> {
    		if (!newValue.trim().isEmpty()) {
                buttonBox.setVisible(true);
            } else {
            	buttonBox.setVisible(false);
            }
        });
        
    	categoryTitleArea.getChildren().add(buttonBox);
    	categoryTitleArea.getStyleClass().add("input-area");
        
    	continueButton.setOnAction(event -> {
    		
    		Optional<BookCategory> matchingCat = bookCats.stream().filter(cat -> Objects.equals(cat.getCategoryName(), catTitleField.getText())).findFirst();
    		
    		if (matchingCat.isPresent()) {
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Error: A Category with this name already exists. Redirecting to it...");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) categoryTitleArea.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = categoryTitleArea.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, e -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	Stage stage = (Stage) continueButton.getScene().getWindow();
        		    stage.close();
        		    BookCategoryDetails display = new BookCategoryDetails();
        		    display.showBookCatDetails(matchingCat.get());
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
            } else {
    			Stage stage = (Stage) continueButton.getScene().getWindow();
    		    stage.close();
    		    AddBooksQuestion main = new AddBooksQuestion();
    		    try {
					main.showAddBooksQuestion(catTitleField.getText(), bookCats.get(0).getUsername());
				} catch (IOException e) {
					e.printStackTrace();
				}
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