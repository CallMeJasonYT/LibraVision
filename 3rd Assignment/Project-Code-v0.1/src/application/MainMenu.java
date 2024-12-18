package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Application {
    // Declare FXML components
    @FXML
    private VBox bookDetailsArea;
    
    @FXML
    private HBox bookDisplayArea;

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
            primaryStage.setTitle("Main Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            MainMenu controller = loader.getController();
            controller.loadMenu(null);
            Platform.runLater(() -> controller.bookDetailsArea.requestFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     // Method to display the Main Menu without recommendations
    public void showMainPg() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenu controller = loader.getController();
            controller.loadMenu(null);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Main Menu");
            newStage.show();
            
            Platform.runLater(() -> controller.bookDetailsArea.requestFocus());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to display the Main Menu with book recommendations
    public void showMainPgWithRec(List<Book> bookRecs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenu controller = loader.getController();
            controller.loadMenu(bookRecs);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Main Menu");
            newStage.show();
            
            Platform.runLater(() -> controller.bookDetailsArea.requestFocus());

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
    
    private static Member testMember = new Member("roubinie21", 20);

    // Method to load the menu with book recommendations or default books
    public void loadMenu(List<Book> bookRecs) {
    	List<Book> booksToBeDisplayed = new ArrayList<>();
    	if(bookRecs == null) {
    		try {
    			booksToBeDisplayed = Book.getAIBooks(null);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}else {
    		booksToBeDisplayed = bookRecs;
    	}
		
        // Display each book in the booksToBeDisplayed list
		for (Book book : booksToBeDisplayed) {
			VBox vbox = new VBox(10);
			
			Image image = new Image(getClass().getResourceAsStream(book.getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            
            Label titleLabel = new Label(book.getTitle());
            titleLabel.getStyleClass().add("book-title");
            titleLabel.setPrefWidth(200);
            titleLabel.setWrapText(true);
            
            HBox starRatingBox = new HBox(5);
            int fullStars = (int) book.getRating();
            for (int i = 0; i < fullStars; i++) {
                ImageView star = new ImageView(new Image(getClass().getResourceAsStream("/misc/filledStar.png")));
                star.setFitWidth(20);
                star.setFitHeight(20);
                starRatingBox.getChildren().add(star);
            }
			
            vbox.getChildren().addAll(imageView, titleLabel, starRatingBox);
            vbox.getStyleClass().add("vbox");
            
			bookDisplayArea.getChildren().addAll(vbox);
		}
		
		ScrollBar scroll = new ScrollBar();
        bookDetailsArea.getChildren().add(scroll);

        bextensionLabel.setOnMouseClicked(e -> {
        	List<Borrowing> curBorrowings = new ArrayList<>();
			try {
				curBorrowings = Borrowing.getBorrowings(testMember.getUsername(), "Extension");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	if(!curBorrowings.isEmpty()) {
        		CurrentBorrowingsDisplay curBorrowingsDisp = new CurrentBorrowingsDisplay();
            	curBorrowingsDisp.showCurBorrow(curBorrowings, null);

        		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
        		currentStage.close();
        	}else {
                // Show popup if there are no borrowings to extend
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Error: None of your Active Borrowings that expire within 3 days");
                messageLabel.getStyleClass().add("popup-label");
                popup.getContent().add(messageLabel);
                Stage curStage = (Stage) bextensionLabel.getScene().getWindow();

                popup.setOnShown(r -> {
                    popup.setX(curStage.getX() + 120 + curStage.getWidth() / 2 - popup.getWidth() / 2);
                    popup.setY(curStage.getY() + curStage.getHeight() / 2 - popup.getHeight() / 2);
                });

                popup.show(curStage);
                
                Scene currentScene = bextensionLabel.getScene();
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
        // Event handler for the "Book Wear" label
        bookWearLabel.setOnMouseClicked(e -> {
        	LocationDisplay locDisp = new LocationDisplay();
        	locDisp.showLocDisplay();

    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
        });
        // Event handler for the "Book Donation" label
        bookDonLabel.setOnMouseClicked(e -> {
        	DonationForm donForm = new DonationForm();
        	donForm.showDonationForm();

    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
        });
        // Event handler for the "My Books" label
        mybooksLabel.setOnMouseClicked(e -> {
        	List<BookCategory> bookCat = new ArrayList<>();
			try {
				bookCat = BookCategory.getBookCat(testMember.getUsername());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	BookCategoriesDisplay locDisp = new BookCategoriesDisplay();
        	locDisp.showBookCat(bookCat);

    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
        });  
        // Event handler for the "Borrowing History" label
        bhistoryLabel.setOnMouseClicked(e -> {
        	List<Borrowing> borrowings = new ArrayList<>();
			try {
				borrowings = Borrowing.getBorrowings(testMember.getUsername(), "Expired");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	BorrowingHistory display = new BorrowingHistory();
        	display.showBorrowHist(borrowings);

    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
        });   
    }

    public static void main(String[] args) {
        launch(args);
    }
}
