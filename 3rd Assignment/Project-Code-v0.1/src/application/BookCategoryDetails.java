package application;
import java.io.IOException;
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

public class BookCategoryDetails extends Application {
	
	@FXML
	private Label catTitle;
	
    @FXML
    private VBox booksDisplayArea;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCategoryDetails.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCategoryDetails.css").toExternalForm());
            primaryStage.setTitle("Book Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showBookCatDetails(BookCategory bookCat) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookCategoryDetails.fxml"));
            Parent root = loader.load();
            BookCategoryDetails controller = loader.getController();
            controller.loadCategories(bookCat);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/bookCategoryDetails.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Donation Form");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadCategories(BookCategory bookCat) {
    	catTitle.setText(bookCat.getCategoryName());
    	
    	booksDisplayArea.setSpacing(50);
    	if (bookCat.getBooks() != null) {
    		for (Book book : bookCat.getBooks()) {
        		HBox hbox = new HBox(50);
        		hbox.setPrefWidth(990);
        		hbox.setMaxWidth(990);
        		
                Image image = new Image(getClass().getResourceAsStream(book.getUrlToPhoto()));
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

                VBox textDetails = new VBox(5);
                Label titleLabel = new Label(book.getTitle());
                titleLabel.getStyleClass().add("book-title");
                
                HBox authorGenresBox = new HBox(10);
                Label authorLabel = new Label("Author: " + book.getAuthorsFormatted());
                Label genresLabel = new Label("Genres: " + book.getGenresFormatted());
                authorGenresBox.getChildren().addAll(authorLabel, genresLabel);

                HBox ratingBorrowedBox = new HBox(10);
                Label ratingLabel = new Label(String.format("Rating: %.1f", book.getRating()));
                ratingBorrowedBox.getChildren().add(ratingLabel);

                textDetails.getChildren().addAll(titleLabel, authorGenresBox, ratingBorrowedBox);
                textDetails.getStyleClass().add("book-label");

                hbox.getChildren().addAll(imageView, textDetails);
                booksDisplayArea.getChildren().add(hbox);
        	}
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}

