package application;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
    }

    public void showMainPg() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenu controller = loader.getController();
            controller.loadMenu();

            Scene scene = new Scene(root);
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
