package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class MainMenu extends Application {

    @FXML
    private VBox bookDetailsArea;

    @FXML
    private Label homeLabel;

    @FXML
    private Label bhistoryLabel;

    @FXML
    private Label bextensionLabel;

    @FXML
    private Label mybooksLabel;

    @FXML
    private Label bookDonLabel;

    @FXML
    private Label bookWearLabel;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Main Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            MainMenu controller = loader.getController();
            controller.loadMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMenu() {
        homeLabel.setOnMouseClicked(e -> {
            System.out.println("Home Label clicked!");
        });
        bextensionLabel.setOnMouseClicked(e -> {
        	List<Borrowing> curBorrowings = Borrowing.getBorrowings();
        	if(!curBorrowings.isEmpty()) {
        		CurrentBorrowingsDisplay curBorrowingsDisp = new CurrentBorrowingsDisplay();
            	curBorrowingsDisp.showCurBorrow(curBorrowings);

        		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
        		currentStage.close();
        	}else {
        		// Create the popup
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true); // Hide popup when clicking outside of it

                // Create the label with your message
                Label messageLabel = new Label("Error: You don't have any active Borrowings");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) bextensionLabel.getScene().getWindow();
                // Position the popup in the center of the screen
                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                // Show the popup
                popup.show(curStage);

                // Hide the popup after 5 seconds
                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, event -> popup.hide());
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	}
        	
        });
    }

    public void showMainPg() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenu controller = loader.getController();
            controller.loadMenu();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Main Menu");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
