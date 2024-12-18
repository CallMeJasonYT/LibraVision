package application;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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

public class DonationForm extends Application {
	// Declare FXML components
	@FXML
    private VBox donationFormArea;
	
	@FXML
    private VBox donationFormBox;
	
	@FXML
	private TextField donationCodesArea;
	
	@FXML
	private HBox buttonBox;
	
	@FXML
    private Button confirmButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DonationForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            primaryStage.setTitle("Donation Form");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the donation form
    public void showDonationForm() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DonationForm.fxml"));
            Parent root = loader.load();
            DonationForm controller = loader.getController();
            
            controller.loadForm();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Donation Form");
            newStage.setScene(scene);            
            newStage.show();      
            Platform.runLater(() -> controller.donationFormArea.requestFocus());
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
    
    int booksFound = 0;
    private static Member testMember = new Member("roubinie21", 20);
    
    // Method to load the donation form
    public void loadForm() {    
    	confirmButton.setOnAction(e -> {// Set action for confirm button
        	List<String> donationIsbns = parseIsbns(donationCodesArea.getText());
        	List<Integer> donationAmounts = parseAmounts(donationCodesArea.getText());
        	// Check if all books exist
        	for (String isbn : donationIsbns) {if(booksExist(isbn)) booksFound++;}

        	List<Donation> newDonations = new ArrayList<>();
        	
        	if(donationIsbns.size() == booksFound) {
                // Retrieve books needed from the database
        		List<String> booksNeeded = new ArrayList<>();
				try {
					booksNeeded = Book.booksNeeded(donationIsbns);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
        		if(booksNeeded.size() == donationIsbns.size()) {
        			for (int i=0; i<donationIsbns.size(); i++) {
        				Donation don = new Donation(testMember.getUsername(), Date.valueOf(LocalDate.now()), donationIsbns.get(i), donationAmounts.get(i));
        				newDonations.add(don);
        			}
        		} else {
        			for (String isbn1 : donationIsbns) {
        				if(booksNeeded.contains(isbn1)) {
        					Donation don = new Donation(testMember.getUsername(), Date.valueOf(LocalDate.now()), isbn1, donationAmounts.get(donationIsbns.indexOf(isbn1)));
            				newDonations.add(don);
        				}
        			}
         		}
        		
                // Insert donations into the database
        		if(!newDonations.isEmpty()) {
        			Donation.insertDonation(newDonations);
        		}
            	
        		if(booksNeeded.size() == donationIsbns.size()) {
        			Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        			currentStage.close();
        			MainMenu main = new MainMenu();
        			main.showMainPg();
        		}else {
        			Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        			currentStage.close();
        			FinalDonationForm main = new FinalDonationForm();
        			main.showFinalForm(newDonations);
        		}
            	
    			
        	} else{// Show error message if some ISBN numbers are incorrect
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Please Insert the correct ISBN Number(s) in the correct Format");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) donationCodesArea.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = donationCodesArea.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, er -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	booksFound = 0;
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	}
        });

        // Set action for cancel button
    	cancelButton.setOnAction(e -> {
        	Stage currentStage = (Stage) cancelButton.getScene().getWindow();
			currentStage.close();
			MainMenu main = new MainMenu();
			main.showMainPg();
        });
        
        donationCodesArea.textProperty().addListener((observable, oldValue, newValue) -> buttonBox.setVisible(!newValue.trim().isEmpty()));
}
    
    // Method to parse ISBN numbers from input
    private List<String> parseIsbns(String input) {
        List<String> isbns = new ArrayList<>();
        String[] pairs = input.split("\",\" ");
        for (String pair : pairs) {
            String[] parts = pair.replace("\"", "").split(", ");
            for(int i=0; i<parts.length; i+=2) {
            	isbns.add(parts[i]);
            }
        }
        return isbns;
    }

     // Method to parse donation amounts from input
    private List<Integer> parseAmounts(String input) {
        List<Integer> amounts = new ArrayList<>();
        String[] pairs = input.split("\",\" ");
        for (String pair : pairs) {
            String[] parts = pair.replace("\"", "").split(", ");
            for(int i=1; i<parts.length; i+=2) {
            	amounts.add(Integer.valueOf(parts[i]));
            }
        }
        return amounts;
    }
    
    //Method to check if the book exists using API
    private boolean booksExist(String isbn) {
        String urlString = "https://openlibrary.org/search.json?isbn=" + isbn + "&fields=numFound";

        try {
        	URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                
                if (jsonObject.has("numFound")) {
                    int numFound = jsonObject.getAsJsonPrimitive("numFound").getAsInt();
                    return numFound > 0;
                } else {
                    System.out.println("Response does not contain 'numFound' field.");
                    return false;
                }
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}