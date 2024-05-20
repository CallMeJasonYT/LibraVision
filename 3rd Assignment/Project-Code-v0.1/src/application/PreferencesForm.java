package application;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferencesForm extends Application {
	
    @FXML
    private TextField authorsField;
    
    @FXML
    private TextField genresField;
    
    @FXML
    private TextField pagesField;
    
    @FXML
    private TextField interestsField;
    
    @FXML
    private Button submitButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PreferencesForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/registrationForm.css").toExternalForm());

            primaryStage.setTitle("Registration Form");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPrefForm(User user) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PreferencesForm.fxml"));
            Parent root = loader.load();
            PreferencesForm controller = loader.getController();
            controller.setPrefForm(user);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/registrationForm.css").toExternalForm());

            Stage newStage = new Stage();
            newStage.setTitle("Registration Form");
            newStage.setScene(scene);            
            newStage.show();
            
            Platform.runLater(() -> controller.submitButton.requestFocus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    boolean isAuthorsFilled = false;
    boolean isGenresFilled = false;
    boolean isPagesFilled = false;
    boolean isInterestsFilled = false;
    
    private Pane createOverlayPane(Scene scene) {
        Pane overlayPane = new Pane();
        overlayPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        overlayPane.setOpacity(0.7);
        overlayPane.setPrefSize(scene.getWidth(), scene.getHeight());
        return overlayPane;
    }
    
    public void setPrefForm(User user) {
    	submitButton.requestFocus();
    	
    	addFieldListener(authorsField, () -> isAuthorsFilled = !authorsField.getText().isEmpty());
        addFieldListener(genresField, () -> isGenresFilled = !genresField.getText().isEmpty());
        addFieldListener(pagesField, () -> isPagesFilled = !pagesField.getText().isEmpty());
        addFieldListener(interestsField, () -> isInterestsFilled = !interestsField.getText().isEmpty());
        
        submitButton.setOnMouseClicked(e -> {
        	if((isAuthorsFilled && isGenresFilled && isPagesFilled && isInterestsFilled)) {
        		UserProfile newProfile = new UserProfile(user.getUsername(), textToList(authorsField.getText()), textToList(genresField.getText()), 
        				Integer.valueOf(pagesField.getText()), textToList(interestsField.getText()));
        		
        		List<Book> aiGenBooks = new ArrayList<>();
				try {
					aiGenBooks = UserProfile.insertProfile(newProfile);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        		
        		MainMenu main = new MainMenu();
        		main.showMainPgWithRec(aiGenBooks);
        		Stage currentStage = (Stage) submitButton.getScene().getWindow();
        		currentStage.close();
        	} else {
        		Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Please fill in all the Required (*) Fields");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) submitButton.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = submitButton.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, r -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	}
        });
    }

    private void addFieldListener(TextField field, Runnable updateFilledState) {
        field.textProperty().addListener((observable, oldValue, newValue) -> updateFilledState.run());
    }
    
    private List<String> textToList(String text){
    	
        String[] textArray = text.split(",");
        List<String> list = new ArrayList<>();

        for (String listItem : textArray) {
            list.add(listItem.trim());
        }
        return list;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
