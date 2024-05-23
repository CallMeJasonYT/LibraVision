package application;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

public class NewCategoryDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox categoryTitleArea;
    
    @FXML
    private TextField catTitleField;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewCategory.fxml"));
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
    
    // Method to show the new category display
    public void showNewCatDisplay(List<BookCategory> bookCats) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewCategory.fxml"));
            Parent root = loader.load();
            NewCategoryDisplay controller = loader.getController();
            
            controller.setNewCat(bookCats);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("New Category Display");
            newStage.setScene(scene);            
            newStage.show();
            Platform.runLater(() -> controller.categoryTitleArea.requestFocus());
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
    
    // Method to set up the new category display
    public void setNewCat(List<BookCategory> bookCats) {
    	catTitleField.setOnMouseClicked(event -> {
            if (catTitleField.getText().equals("Category Title")) {
            	catTitleField.setText("");
            }
        });
        // Show buttons when text is entered
    	catTitleField.textProperty().addListener((observable, oldValue, newValue) -> buttonBox.setVisible(!newValue.trim().isEmpty()));
        // Action for continue button
    	continueButton.setOnAction(event -> {
            // Check if category with the same name already exists	
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
                // Close popup and open existing category details after delay
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
    		    main.showAddBooksQuestion(catTitleField.getText(), bookCats.get(0).getUsername());
    		}
        });

        // Action for cancel button
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