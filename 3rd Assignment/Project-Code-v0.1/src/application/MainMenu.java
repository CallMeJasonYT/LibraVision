package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
            controller.loadMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showMainPg() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenu controller = loader.getController();
            controller.loadMenu();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/mainMenu.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Main Menu");
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
    
    private static Member testMember = new Member("roubinie21", 20);

    public void loadMenu() {
    	
		List<Book> booksToBeDisplayed = Book.getAIBooks(null);
		
		for (Book book : booksToBeDisplayed) {
			VBox vbox = new VBox(10);
			
			Image image = new Image(getClass().getResourceAsStream(book.getUrlToPhoto()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            
            Label titleLabel = new Label(book.getTitle());
            titleLabel.getStyleClass().add("book-title");
            
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
		scroll.getStyleClass().add("scroll-bar");
        bookDetailsArea.getChildren().add(scroll);

        homeLabel.setOnMouseClicked(e -> {
            System.out.println("Home Label clicked!");
        });
        bextensionLabel.setOnMouseClicked(e -> {
        	List<Borrowing> curBorrowings = new ArrayList<>();
			try {
				curBorrowings = Borrowing.getBorrowings(testMember.getUsername());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	if(!curBorrowings.isEmpty()) {
        		CurrentBorrowingsDisplay curBorrowingsDisp = new CurrentBorrowingsDisplay();
            	curBorrowingsDisp.showCurBorrow(curBorrowings, null);

        		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
        		currentStage.close();
        	}else {
                Popup popup = new Popup();
                popup.setWidth(200);
                popup.setHeight(200);
                popup.setAutoHide(true);

                Label messageLabel = new Label("Error: You don't have any active Borrowings");
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
        bookWearLabel.setOnMouseClicked(e -> {
        	LocationDisplay locDisp = new LocationDisplay();
        	locDisp.showLocDisplay();

    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
        });
        bookDonLabel.setOnMouseClicked(e -> {
        	DonationForm donForm = new DonationForm();
        	donForm.showDonationForm();

    		Stage currentStage = (Stage) bookDetailsArea.getScene().getWindow();
    		currentStage.close();
        });
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
        bhistoryLabel.setOnMouseClicked(e -> {
        	List<Borrowing> borrowings = new ArrayList<>();
			try {
				borrowings = Borrowing.getBorrowings(testMember.getUsername());
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
