package application;
import javafx.scene.Cursor;
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
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();

            // You must get a reference to the controller and call loadBooks after the stage is shown
            BookSearch controller = loader.getController();
            controller.loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showBookCat(List<BookCategory> bookCats) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCategories.fxml"));
            Parent root = loader.load();
            BookCategoriesDisplay controller = loader.getController();
            controller.loadCategories(bookCats);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCategories.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Donation Form");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadCategories(List<BookCategory> bookCats) {
    	bookCategoriesArea.setSpacing(50); // Spacing between each book entry
    	
        for (BookCategory bookC : bookCats) {
            HBox hbox = new HBox(50); // Main container with spacing between image and text

            Image image = new Image(getClass().getResourceAsStream(bookC.getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150); // Adjust width as needed
            imageView.setFitHeight(150); // Adjust height as needed

            VBox categoryTitle = new VBox(30);
            Label titleLabel = new Label(bookC.getCategoryName());
            titleLabel.getStyleClass().add("category-title");
            titleLabel.setCursor(Cursor.HAND);
            
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

