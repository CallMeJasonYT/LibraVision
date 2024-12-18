package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddBooksDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox bookTitleArea;
    
    @FXML
    private Label addBooksTitle;
    
    @FXML
    private TextField bookTitleField;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBooksCat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Add Books in Category");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show the add books display
    public void showAddBooks(String catName, String username) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBooksCat.fxml"));
            Parent root = loader.load();
            AddBooksDisplay controller = loader.getController();
            
            controller.setNewBooks(catName, username);
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Add Books in Category");
            newStage.setScene(scene);            
            newStage.show();
            Platform.runLater(() -> controller.bookTitleArea.requestFocus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to set up the add books display
    public void setNewBooks(String catName, String username) {
    	addBooksTitle.setText("Please insert your desired books in the category " + catName);
    	
    	bookTitleField.setOnMouseClicked(event -> {
            if (bookTitleField.getText().equals("Category Title")) {
            	bookTitleField.setText("");
            }
        });

        bookTitleField.textProperty().addListener((observable, oldValue, newValue) -> buttonBox.setVisible(!newValue.trim().isEmpty()));

        // Action for continue button
		continueButton.setOnAction(event -> {
			List<Book> newBooks = new ArrayList<>();
			try {
				newBooks = Book.getBooksByTitle(titleParser());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			BookCategory bookCat = new BookCategory(newBooks, username, catName, "/misc/bookCategory.jpg");
			BookCategory.insertBookCat(bookCat);
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
			BookCategoryDetails details = new BookCategoryDetails();
			details.showBookCatDetails(bookCat);
		});
        // Action for cancel button
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            MainMenu main = new MainMenu();
			main.showMainPg();
        });
    }
    
    // Method to parse book titles entered in the text field
    public List<String> titleParser() {
        String input = bookTitleField.getText();
        
        String[] titlesArray = input.split(",");
        List<String> titlesList = new ArrayList<>();

        for (String title : titlesArray) {
            titlesList.add(title.trim());
        }
        
        return titlesList;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}