package application;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

public class LibraryMemberDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox newUserArea;
    
    @FXML
    private TextField usernameInput;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibraryMember.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Library Member");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to show member information
    public void showMemberInfo(List<Copy> copies) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibraryMember.fxml"));
            Parent root = loader.load();
            LibraryMemberDisplay controller = loader.getController();
            controller.setUserPrompt(copies);
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Library Member");
            newStage.setScene(scene);            
            newStage.show();
            Platform.runLater(() -> controller.newUserArea.requestFocus());
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
    
    // Method to set the user prompt
    public void setUserPrompt(List<Copy> copies) {
        usernameInput.setOnMouseClicked(event -> {
            if (usernameInput.getText().equals("Member Username")) {
            	usernameInput.setText("");
            }
        });
        
        usernameInput.textProperty().addListener((observable, oldValue, newValue) -> buttonBox.setVisible(!newValue.isEmpty()));

        continueButton.setOnAction(event -> {
        	try {
        		Member member = Member.memberExist(usernameInput.getText());
				if (member.getUsername() == null) {
					Popup popup = new Popup();
				    popup.setWidth(200);
				    popup.setHeight(200);
				    popup.setAutoHide(true);

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

				    Duration delay = Duration.seconds(5);
				    KeyFrame keyFrame = new KeyFrame(delay, e -> {
				    	popup.hide();
				    	rootPane.getChildren().remove(overlay);
				    	});
				    Timeline timeline = new Timeline(keyFrame);
				    timeline.play();
				}else {
					List<LocalDate> dates = Library.getOpenDates("Roumpini's Library");
					Stage stage = (Stage) continueButton.getScene().getWindow();
				    stage.close();
				    DeadlineOptDisplay main = new DeadlineOptDisplay();
					main.showDeadline(copies, member, dates);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

