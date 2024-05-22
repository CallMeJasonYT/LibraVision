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
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class RegistrationForm extends Application {
    // Declare FXML components
	@FXML
    private Spinner<Integer> ageField;
	
    @FXML
    private TextField fullnameField;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button createButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegistrationForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());

            primaryStage.setTitle("Registration Form");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to show the sign-up form  
    public void showSignUp() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegistrationForm.fxml"));
            Parent root = loader.load();
            RegistrationForm controller = loader.getController();
            controller.setSignUp();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            
            Stage newStage = new Stage();
            newStage.setTitle("Registration Form");
            newStage.setScene(scene);            
            newStage.show();
            
            Platform.runLater(() -> controller.createButton.requestFocus());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    boolean isFullnameFilled = false;
    boolean isUsernameFilled = false;
    boolean isEmailFilled = false;
    boolean isPhoneFilled = false;
    boolean isPasswordFilled = false;
    
    private Pane createOverlayPane(Scene scene) {
        Pane overlayPane = new Pane();
        overlayPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        overlayPane.setOpacity(0.7);
        overlayPane.setPrefSize(scene.getWidth(), scene.getHeight());
        return overlayPane;
    }
    
     // Method to set up the sign-up form
    public void setSignUp() {
    	// Configure the spinner for age input
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, 100);
        ageField.setValueFactory(valueFactory);

        ageField.setEditable(true);
        ageField.getEditor().setText("0");
        // Ensure the spinner value is within the valid range
        ageField.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                ageField.getEditor().setText("0");
            }
        });

        ageField.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*")) {
                ageField.getEditor().setText(oldValue);
            } else {
                try {
                    int enteredValue = Integer.parseInt(newValue);
                    if (enteredValue > 100) {
                        ageField.getEditor().setText("100");
                    } else {
                        ageField.getValueFactory().setValue(enteredValue);
                    }
                } catch (NumberFormatException e) {
                    ageField.getEditor().setText(oldValue);
                }
            }
        });
        
        // Add listeners to text fields to check if they are filled
        addFieldListener(fullnameField, () -> isFullnameFilled = !fullnameField.getText().isEmpty());
        addFieldListener(usernameField, () -> isUsernameFilled = !usernameField.getText().isEmpty());
        addFieldListener(emailField, () -> isEmailFilled = !emailField.getText().isEmpty());
        addFieldListener(phoneField, () -> isPhoneFilled = !phoneField.getText().isEmpty());
        addFieldListener(passwordField, () -> isPasswordFilled = !passwordField.getText().isEmpty());
        
        // Configure the create button to handle user registration
        createButton.setOnMouseClicked(e -> {
            // Check if all required fields are filled
        	if((isFullnameFilled && isUsernameFilled && isEmailFilled && isPhoneFilled && isPasswordFilled)) {
        		User newUser;
        		if(ageField.getValue() == 0) {
        			newUser = new User("Member", fullnameField.getText(), usernameField.getText(), 0, passwordField.getText(), emailField.getText(), phoneField.getText());
        		} else {
            		newUser = new User("Member", fullnameField.getText(), usernameField.getText(), ageField.getValue(), passwordField.getText(), emailField.getText(), phoneField.getText());
        		}
        		User.insertNewUser(newUser);
        		PersonalizedContent dialog = new PersonalizedContent();
        		dialog.showPerContDiag(newUser);

        		Stage currentStage = (Stage) createButton.getScene().getWindow();
        		currentStage.close();
        	} else { // Show a popup message if not all required fields are filled
        		Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Please fill in all the Required (*) Fields");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) createButton.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = createButton.getScene();
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
    
    public static void main(String[] args) {
        launch(args);
    }
}
