package application;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class LibrarianMainMenu extends Application {
    // Declare FXML components
    @FXML
    private Label homeLabel;

    @FXML
    private Label newBorrowingLabel;

    @FXML
    private Label updateBooksLabel;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibrarianMainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Librarian Main Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
            scene.getStylesheets().add(getClass().getResource("/styles/librarianMainMenu.css").toExternalForm());
            LibrarianMainMenu controller = loader.getController();
            controller.loadMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to set up event handlers for the menu options
    public void loadMenu() {
        newBorrowingLabel.setOnMouseClicked(e -> {
    		NewBorrowingDisplay newBorrowingDispl = new NewBorrowingDisplay();
    		newBorrowingDispl.showNewBorrow();

    		Stage currentStage = (Stage) newBorrowingLabel.getScene().getWindow();
    		currentStage.close();
        });
        updateBooksLabel.setOnMouseClicked(e -> {
    		AddBooksDisplayLib display = new AddBooksDisplayLib();
    		display.showAddBooks();

    		Stage currentStage = (Stage) newBorrowingLabel.getScene().getWindow();
    		currentStage.close();
        });
    }

    // Method to display the Librarian Main Menu
    public void showLibMainPg() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibrarianMainMenu.fxml"));
            Parent root = loader.load();
            LibrarianMainMenu controller = loader.getController();
            controller.loadMenu();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/librarianMainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Librarian Main Menu");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
