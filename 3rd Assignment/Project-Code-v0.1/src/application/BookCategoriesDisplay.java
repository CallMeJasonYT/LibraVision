package application;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookCategoriesDisplay extends Application {
	// Declare FXML components
    @FXML
    private VBox bookCategoriesArea;
    
    @FXML
    private HBox createCatBtn; 
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCategories.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCategories.css").toExternalForm());
            primaryStage.setTitle("Book Categories");
            primaryStage.setScene(scene);
            primaryStage.show();

            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to show book categories 
    public void showBookCat(List<BookCategory> bookCats) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCategories.fxml"));
            Parent root = loader.load();
            BookCategoriesDisplay controller = loader.getController();
            controller.loadCategories(bookCats);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCategories.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Book Categories");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to load categories into the UI
    public void loadCategories(List<BookCategory> bookCats) {
    	bookCategoriesArea.setSpacing(50);
    	
        for (BookCategory bookC : bookCats) {
            HBox hbox = new HBox(50);

            Image image = new Image(getClass().getResourceAsStream(bookC.getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            VBox categoryTitle = new VBox(30);
            Label titleLabel = new Label(bookC.getCategoryName());
            titleLabel.getStyleClass().add("category-title");
            
            Label categoryBooksLabel = new Label("# of Books in the Category: " + bookC.getBooks().size());
            categoryBooksLabel.getStyleClass().add("category-books");
            
            categoryTitle.getChildren().addAll(titleLabel, categoryBooksLabel);
            
            hbox.getChildren().addAll(imageView, categoryTitle);
            bookCategoriesArea.getChildren().add(hbox);
            
            createCatBtn.setOnMouseClicked(event -> {
            	NewCategoryDisplay newCat = new NewCategoryDisplay();
            	newCat.showNewCatDisplay(bookCats);
            	Stage currentStage = (Stage) titleLabel.getScene().getWindow();
            	currentStage.close();
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

