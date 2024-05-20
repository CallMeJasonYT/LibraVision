package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookConfirmDisplay extends Application {
	
    @FXML
    private VBox bookDisplayArea;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private Button rejectButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookConfirm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookConfirm.css").toExternalForm());
            primaryStage.setTitle("Book Confirm");
            primaryStage.setScene(scene);
            primaryStage.show();

            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showBookConfDisplay(Copy copy) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookConfirm.fxml"));
            Parent root = loader.load();
            BookConfirmDisplay controller = loader.getController();
            
            controller.loadCopy(copy);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookConfirm.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Book Confirm");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadCopy(Copy copy) {
        
        HBox hbox = new HBox(75);
        hbox.getStyleClass().add("hbox");
        
        Image image = new Image(getClass().getResourceAsStream(copy.getUrlToPhoto()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        
        VBox titleBox = new VBox(0);
        titleBox.getStyleClass().add("book-title");
        Label titleLabel = new Label(copy.getTitle());
        titleLabel.setWrapText(true);
        titleBox.getChildren().add(titleLabel);
        titleBox.setPrefWidth(150);
        
        VBox copyDetails = new VBox(50);
        copyDetails.getStyleClass().add("copy-details");
        
        Label copyID = new Label("Copy ID: " + copy.getCopyID());
        copyID.getStyleClass().add("copyID-label");
        Label copyISBN = new Label("ISBN: " + copy.getIsbn());
        copyISBN.getStyleClass().add("isbn-label");
        copyDetails.getChildren().addAll(copyID, copyISBN);
        
        confirmButton.setOnAction(e -> {
        	List<Wear> wear = new ArrayList<>();
			try {
				wear = Wear.getWear(copy.getCopyID());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	if(wear.isEmpty()) {
        		Stage currentStage = (Stage) confirmButton.getScene().getWindow();
    			currentStage.close();
    			DmgReportForm dmgForm = new DmgReportForm();
    			dmgForm.showDmgForm(copy);
        	} else {
        		Stage currentStage = (Stage) confirmButton.getScene().getWindow();
    			currentStage.close();
    			WearReportsDisplay dmgForm = new WearReportsDisplay();
    			dmgForm.showWearReports(wear);
        	}
        });

        rejectButton.setOnAction(e -> {
        	Stage currentStage = (Stage) confirmButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });
        
        hbox.getChildren().addAll(imageView, titleBox, copyDetails);
        bookDisplayArea.getChildren().add(1, hbox);
        bookDisplayArea.getStyleClass().add("main-vbox");
}

    public static void main(String[] args) {
        launch(args);
    }
}