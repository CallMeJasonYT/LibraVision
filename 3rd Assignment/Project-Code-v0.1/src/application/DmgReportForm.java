package application;
import javafx.scene.Cursor; // Import statement for Cursor

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DmgReportForm extends Application {
	
	@FXML
    private VBox dmgReportFormArea; // The UI component to display book data
	
	@FXML
    private VBox bookWearForm; // The UI component to display book data
	
	@FXML
	private TextArea wearDetailsArea;
    
    @FXML
    private Button uploadImageButton; // The UI component to display book data
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmgReportForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/dmgReportForm.css").toExternalForm());
            primaryStage.setTitle("Wear Report Form");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showDmgForm(Copy copy) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmgReportForm.fxml"));
            Parent root = loader.load();
            DmgReportForm controller = loader.getController();
            
            controller.loadForm(copy);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/dmgReportForm.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Wear Report Form");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    boolean var1 = false;
    boolean var2 = false;
    String pictureUrl;
    private static Member testMember = new Member("Test Member", 20);
    
    public void loadForm(Copy copy) {
        
        Button acceptButton = new Button("Confirm");
        acceptButton.getStyleClass().add("continue-btn");
        acceptButton.setCursor(Cursor.HAND);
        acceptButton.setOnAction(e -> {});
        
        acceptButton.setOnAction(e -> {
        	Wear wear = new Wear(copy.getCopyID(), testMember.getUsername(), pictureUrl, wearDetailsArea.getText(), Date.valueOf(LocalDate.now()));
        	Wear.insertWear(wear);
        	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });

        Button rejectButton = new Button("Reject");
        rejectButton.getStyleClass().add("cancel-btn");
        rejectButton.setCursor(Cursor.HAND);
        rejectButton.setOnAction(e -> {
        	Stage currentStage = (Stage) acceptButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });

        // HBox to contain the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(acceptButton, rejectButton);
        buttonBox.setAlignment(Pos.CENTER); // Align buttons to the center
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Add top padding
        buttonBox.setSpacing(150);
        buttonBox.setVisible(false);
        
        wearDetailsArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                var1 = true;
                buttonBox.setVisible(var1 && var2);
            } else {
            	var1 = false;
            	buttonBox.setVisible(var1 && var2);
            }
        });
        
        uploadImageButton.setOnAction(e -> {
        	pictureUrl = getPicture();
        	var2 = true;
        	buttonBox.setVisible(var1 && var2);
        });
        
        bookWearForm.getChildren().add(buttonBox);
        bookWearForm.getStyleClass().add("main-vbox");
}
    public String getPicture() {
    	String url = "/misc/wornBook.jpg";
    	return url;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

