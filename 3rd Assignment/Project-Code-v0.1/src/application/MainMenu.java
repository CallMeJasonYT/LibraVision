package application;                                                                                                                                                                                         
                                                                                                                  
import javafx.application.Application;                                                                            
import javafx.fxml.FXML;                                                                                          
import javafx.fxml.FXMLLoader;                                                                                    
import javafx.scene.Parent;                                                                                       
import javafx.scene.Scene;                                                                                        
import javafx.scene.control.Label;                                                                                                                                                               
import javafx.scene.layout.VBox;                                                                                  
import javafx.stage.Stage;                                                                                        
                                                                                                                  
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
            //scene.getStylesheets().add(getClass().getResource("/styles/bookSearch.css").toExternalForm());        
            primaryStage.setTitle("Book Viewer");                                                                 
            primaryStage.setScene(scene);                                                                         
            primaryStage.show();                                                                                  
                                                                                                                  
            // You must get a reference to the controller and call loadBooks after the stage is shown             
            MainMenu controller = loader.getController();                                                       
            controller.loadMenu();                                                                      
        } catch (Exception e) {                                                                                   
            e.printStackTrace();                                                                                  
        }                                                                                                         
    }                                                                                                             
                                                                                                                  
    public void loadMenu() {       
    	
    	homeLabel.setOnMouseClicked(e -> {
            System.out.println("Label clicked!");
            // Add your action here
        });
    }                                                                                                             
                                                                                                                                                                                                                         
                                                                                                                  
    public static void main(String[] args) {                                                                      
        launch(args);                                                                                             
    }                                                                                                             
}                                                                                                                 
                                                                                                                  
                                                                                                                  