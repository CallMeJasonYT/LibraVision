package application;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

public class AddBooksDisplayLib extends Application {
	
	@FXML
    private VBox booksInsertArea;
	
	@FXML
    private VBox booksInsertTextArea;
	
	@FXML
	private TextField booksInsertField;
	
	@FXML
	private HBox buttonBox;
	
	@FXML
	private Button continueButton;
	
	@FXML
	private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBooksLib.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/donationForm.css").toExternalForm());
            primaryStage.setTitle("Insert Books");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showAddBooks() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBooksLib.fxml"));
            Parent root = loader.load();
            AddBooksDisplayLib controller = loader.getController();
            
            controller.setAddBooks();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/donationForm.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Insert Books");
            newStage.setScene(scene);            
            newStage.show();
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
    
    List<Book> booksToBeInserted = new ArrayList<>();
    
    public void setAddBooks() {
        
    	continueButton.setOnAction(e -> {
        	List<String> bookIsbns = parseIsbns(booksInsertField.getText());
        	List<Integer> bookAmounts = parseAmounts(booksInsertField.getText());
        	
        	for (String isbn : bookIsbns) checkBooks(isbn);
        	
        	if(bookIsbns.size() == booksToBeInserted.size()) {
            	Stage currentStage = (Stage) continueButton.getScene().getWindow();
    			currentStage.close();
    			NewAddedBooks main = new NewAddedBooks();
    			main.showNewBooks(booksToBeInserted, bookAmounts);
    			
        	} else{
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Please Insert the correct ISBN Number(s) in the correct Format");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) booksInsertField.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = booksInsertField.getScene();
                Pane rootPane = (Pane) currentScene.getRoot();
                Pane overlay = createOverlayPane(currentScene);
                rootPane.getChildren().add(overlay);

                Duration delay = Duration.seconds(5);
                KeyFrame keyFrame = new KeyFrame(delay, er -> {
                	popup.hide();
                	rootPane.getChildren().remove(overlay);
                	});
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
        	}
        });
        
        booksInsertField.textProperty().addListener((observable, oldValue, newValue) -> buttonBox.setVisible(!newValue.trim().isEmpty()));
}
    
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
    
    private boolean checkBooks(String isbn) {
        String urlString = "https://openlibrary.org/search.json?isbn=" + isbn + "&fields=title,author_name,first_publish_year,number_of_pages_median,subject_key,numFound";

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
                
                if (getNumFound(response) > 0) {
                	JsonArray docsArray = jsonObject.getAsJsonArray("docs");
                    JsonObject jsonObj = docsArray.get(0).getAsJsonObject();
                    
                    getAuthors(jsonObj);
                    getPublishYear(jsonObj);
                    getTitle(jsonObj);
                    getNumPages(jsonObj);
                    getGenres(jsonObj);
                    Book book = new Book(getTitle(jsonObj), getAuthors(jsonObj), getGenres(jsonObj), 0.0, 0, "Test Description", getNumPages(jsonObj), isbn, 
                    		getPublishYear(jsonObj), 0, "/misc/book1.jpg");
                    booksToBeInserted.add(book);
                    System.out.println(booksToBeInserted.get(0).getTitle());
                    return true;
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

    public static int getNumFound(StringBuilder response) {
    	return JsonParser.parseString(response.toString()).getAsJsonObject().getAsJsonPrimitive("numFound").getAsInt();
    }
    
    public static List<String> getAuthors(JsonObject jsonObj){
    	JsonArray authorNames = jsonObj.getAsJsonArray("author_name");
    	List<String> authors = new ArrayList<>();
    	for (JsonElement authorName : authorNames) {
            authors.add(authorName.getAsString());
        }
    	return authors;
    }
    
    public static int getPublishYear(JsonObject jsonObj){
    	return jsonObj.get("first_publish_year").getAsInt();
    }
    
    public static String getTitle(JsonObject jsonObj){
    	return jsonObj.get("title").getAsString();
    }
    
    public static int getNumPages(JsonObject jsonObj){
    	return jsonObj.get("number_of_pages_median").getAsInt();
    }
    
    public static List<String> getGenres(JsonObject jsonObj){
    	JsonArray genreKeys = jsonObj.getAsJsonArray("subject_key");
        List<String> genres = new ArrayList<>();
        for (JsonElement genre : genreKeys) {
        	genres.add(genre.getAsString());
        }
        Collections.shuffle(genres);
        return genres.subList(0, Math.min(5, genres.size()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}