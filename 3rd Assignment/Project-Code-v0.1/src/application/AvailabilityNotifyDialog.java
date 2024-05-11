package application;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AvailabilityNotifyDialog {

    public static void showNotifDialog(String message) {
        // Ensure this runs on the JavaFX application thread
        Platform.runLater(() -> {
            Stage stage = new Stage();

            // Set the modality to block interaction with other windows
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Error Message");

            Label label = new Label(message);
            label.setStyle("-fx-font-size: 16px; -fx-padding: 20px;");

            VBox layout = new VBox(10);
            layout.getChildren().add(label);
            layout.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: red; -fx-border-width: 2px;");

            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Set the PauseTransition to close the popup after 5 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> stage.close());
            delay.play();
        });
    }
}
