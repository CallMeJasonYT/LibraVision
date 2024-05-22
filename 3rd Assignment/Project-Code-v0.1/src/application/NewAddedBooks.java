package application;
import java.io.IOException;
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

public class NewAddedBooks extends Application {
	
	@FXML
    private VBox insertedBooksArea;
	
	@FXML
	private HBox buttonBox;
	
	@FXML
	private Button continueButton;
	
	@FXML
	private Button cancelButton;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewAddedBooks.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/newAddedBooks.css").toExternalForm());
            primaryStage.setTitle("Inserted Books");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showNewBooks(List<Book> books, List<Integer> bookAmounts) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewAddedBooks.fxml"));
            Parent root = loader.load();
            NewAddedBooks controller = loader.getController();
            
            controller.setNewBooks(books, bookAmounts);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/newAddedBooks.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setTitle("Inserted Books");
            newStage.setScene(scene);            
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setNewBooks(List<Book> books, List<Integer> bookAmounts) {
    	for (Book book : books) {
            HBox hbox = new HBox(20);
            
            Image image = new Image(getClass().getResourceAsStream(book.getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            VBox textDetails = new VBox(5);
            Label titleLabel = new Label(book.getTitle());
            titleLabel.getStyleClass().add("book-title");
            
            Label authorLabel = new Label("Author: " + book.getAuthorsFormatted());
            authorLabel.setWrapText(true);
            
            Label genresLabel = new Label("Genres: " + book.getGenresFormatted());
            genresLabel.setWrapText(true);
            
            Label copiesInserted = new Label("Copies to be Inserted: " + bookAmounts.get(books.indexOf(book)));

            textDetails.getChildren().addAll(titleLabel, authorLabel, genresLabel, copiesInserted);
            textDetails.getStyleClass().add("book-label");

            hbox.getChildren().addAll(imageView, textDetails);
            insertedBooksArea.getChildren().add(0, hbox);
            
            titleLabel.setOnMouseClicked(event -> {
            	BookDetail bookDet = new BookDetail();
            	bookDet.showBookDet(book);
            	Stage currentStage = (Stage) titleLabel.getScene().getWindow();
            	currentStage.close();
            });
         }

        continueButton.setOnAction(event -> {
        	List<Copy> copiesToBeInserted = new ArrayList<>();
        	
			for(Book book : books) {
				Copy copy = new Copy(book.getTitle(), book.getIsbn());
				copiesToBeInserted.add(copy);
			}
			
			Book.insertBooks(books);
			Copy.insertCopies(copiesToBeInserted, bookAmounts);
        	
        	Stage currentStage = (Stage) continueButton.getScene().getWindow();
			currentStage.close();
			LibrarianMainMenu main = new LibrarianMainMenu();
			main.showLibMainPg();
        });
        
        cancelButton.setOnAction(event -> {
        	Stage currentStage = (Stage) continueButton.getScene().getWindow();
			currentStage.close();
			LibrarianMainMenu main = new LibrarianMainMenu();
			main.showLibMainPg();
        });
}

    public static void main(String[] args) {
        launch(args);
    }
}