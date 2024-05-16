package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddBooksDisplay extends Application {
	
    @FXML
    private VBox bookTitleArea;
    
    @FXML
    private Label addBooksTitle;
    
    @FXML
    private TextField bookTitleField;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBooksCat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCopyDisplay.css").toExternalForm());
            primaryStage.setTitle("Add Books in Category");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showAddBooks(String catName, String username) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBooksCat.fxml"));
            Parent root = loader.load();
            AddBooksDisplay controller = loader.getController();
            
            controller.setNewBooks(catName, username);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCopyDisplay.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Add Books in Category");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setNewBooks(String catName, String username) {
    	addBooksTitle.setText("Please insert your desired books in the category " + catName);
    	
    	bookTitleField.setOnMouseClicked(event -> {
            if (bookTitleField.getText().equals("Category Title")) {
            	bookTitleField.setText("");
            }
        });

        // Create continue button
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("continue-btn");
        continueButton.setCursor(Cursor.HAND);

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-btn");
        cancelButton.setCursor(Cursor.HAND);
        
        HBox buttonBox = new HBox(10); // Set spacing between buttons
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(continueButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Set top padding
        buttonBox.setAlignment(Pos.CENTER); // Align buttons to the center
        buttonBox.setSpacing(150);
        buttonBox.setVisible(false); 
        
        bookTitleField.textProperty().addListener((observable, oldValue, newValue) -> {
    		if (!newValue.trim().isEmpty()) {
                buttonBox.setVisible(true);
            } else {
            	buttonBox.setVisible(false);
            }
        });
        
        bookTitleArea.getChildren().add(buttonBox);
    	bookTitleArea.getStyleClass().add("input-area");
        
		continueButton.setOnAction(event -> {
			List<Book> newBooks = Book.getBooksByTitle(titleParser());
			BookCategory bookCat = new BookCategory(newBooks, username, catName, "/misc/bookCategory.jpg");
			BookCategory.insertBookCat(bookCat);
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
			BookCategoryDetails details = new BookCategoryDetails();
			details.showBookCatDetails(bookCat);
		});

        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            MainMenu main = new MainMenu();
			main.showMainPg();
        });
    }
    
    public List<String> titleParser() {
        String input = bookTitleField.getText();
        
        // Split the input string by comma and trim the titles
        String[] titlesArray = input.split(",");
        List<String> titlesList = new ArrayList<>();

        for (String title : titlesArray) {
            titlesList.add(title.trim());
        }

        // Print the titles list
        for (String title : titlesList) {
            System.out.println(title);
        }
        
        return titlesList;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}