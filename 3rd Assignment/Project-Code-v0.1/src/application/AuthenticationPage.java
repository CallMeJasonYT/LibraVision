package application;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AuthenticationPage extends Application {

    @FXML
    private Button signinButton;

    @FXML
    private Button signupButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AuthenticationPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Authentication Page");
            primaryStage.setScene(scene);
            primaryStage.show();

            AuthenticationPage controller = loader.getController();
            controller.loadPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPage() {
    	signupButton.setOnMouseClicked(e -> {
    		RegistrationForm regForm = new RegistrationForm();
    		regForm.showSignUp();

    		Stage currentStage = (Stage) signupButton.getScene().getWindow();
    		currentStage.close();
        	
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
