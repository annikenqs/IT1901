package ui;

import java.io.IOException;

import core.Book;
import core.BookShelf;
import core.User;
import javafx.scene.effect.DropShadow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShelfController {
    @FXML
    private Button profileButton;

    @FXML
    private Button shelfButton;

    @FXML
    private Button homePageButton;


    @FXML
    private Pane pane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label usernameTag;

    private TilePane shelfTilePane;
    private double lastX = 0;
    private User loggedInUser;
    private RemoteDataAccess dataAccess = new RemoteDataAccess();

    public void initialize() {
        shelfTilePane = createShelfTilePane();
        scrollPane.setContent(shelfTilePane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar if needed
        scrollPane.setContent(shelfTilePane);
        this.loggedInUser = dataAccess.getLoggedInUser();
        usernameTag.setText(loggedInUser.getUsername());
        //scrollPane.setMouseTransparent(true);

       /*  shelfTilePane.setOnMousePressed(event -> lastX = event.getSceneX());
        shelfTilePane.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastX;
            double newX = shelfTilePane.getLayoutX() + deltaX;
            shelfTilePane.setLayoutX(newX);
            lastX = event.getSceneX();
        });*/ 

        try {
            addBookShelf(shelfTilePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TilePane createShelfTilePane() {
        TilePane tilePane = new TilePane();
        tilePane.setVgap(70); // Adjust the vertical gap as needed
        tilePane.setHgap(50);
        tilePane.setPrefColumns(3); // Display 5 books in a row
        tilePane.setPrefWidth(1000); // Set the width of the TilePane

        return tilePane;

    }

    //SAMME NAVN, MÅ FIKSE 
    public void addBookShelf(TilePane tilePane) throws IOException{
    public void addBookShelf(TilePane tilePane) throws IOException {
        BookShelf bookShelf = loggedInUser.getBookShelf();
        for (Book book : bookShelf) {
            ImageView bookImageView = createBookImageView(book);
            Node bookInfoView = createBookInfoView(book, bookImageView);
            tilePane.getChildren().add(bookInfoView);
        }
    }

    private Node createBookInfoView(Book book) {
        ImageView imageView = createBookImageView();
    private Node createBookInfoView(Book book, ImageView imageView){
        imageView = createBookImageView(book);
        Label titleLabel = new Label(book.getTitle());
        Label authorLabel = new Label(book.getAuthor());

        titleLabel.setFont(new Font(16));
        titleLabel.setStyle("-fx-font-weight: bold");
        authorLabel.setFont(new Font(14));

        VBox labelContainer = new VBox();
        labelContainer.getChildren().addAll(titleLabel, authorLabel);
        labelContainer.setAlignment(Pos.CENTER);

        StackPane bookInfoView = new StackPane();
        bookInfoView.getChildren().addAll(imageView, labelContainer);

        Insets margin = new Insets(0, 0, 0, 25);
        StackPane.setMargin(labelContainer, margin);

        return bookInfoView;
    }

    private ImageView createBookImageView(Book book) {
        ImageView imageView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/ui/BookImages/bookDefault.png"));
        imageView.setImage(image);
        imageView.setFitWidth(180); // Adjust the width as needed
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        imageView.setEffect(dropShadow);

        imageView.setId(book.getBookId());

        imageView.setOnMouseClicked(event -> {
            System.out.println("click on image");
            try {
                handleImgClicked(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });

        return imageView;
    }

    public void handleProfileButton(ActionEvent event) throws IOException {
        changeScene("/ui/Profile.fxml", event);
    }

    public void handleShelfButton(ActionEvent event) throws IOException {
        changeScene("/ui/ShelfPage.fxml", event);
    }

    public void handleHomePageButton(ActionEvent event) throws IOException {
        changeScene("/ui/Startpage.fxml", event);
    }

    private void handleImgClicked(ImageView imageView) throws IOException {
        String bookId = imageView.getId();

        Book clickedBook = getBookId(bookId);

        if(clickedBook != null){
            displayBookPopup(clickedBook);
            System.out.println("the book whas clicked on");
        }

    }

    private Book getBookId(String bookId){
        BookShelf bookShelf = loggedInUser.getBookShelf();
        for (Book book : bookShelf) {
            if (bookId.equals(book.getBookId())) {
                return book;
            }
        }
        return null; 

    }

    private void displayBookPopup(Book book) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Title: " + book.getTitle());
        Label author = new Label("Author: " + book.getAuthor());
        Label pages = new Label("Pages: " + book.getPages());
        Label description = new Label("Description: " + book.getDescription());

        Button removeButton = new Button("Remove book");
        removeButton.setOnAction(e -> {
            System.out.println("Book removed from shelf");
            try {
                removeBookFromShelf(book);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            stage.close();
        });

        VBox labels = new VBox(10, title, author, pages, description);
        labels.setPadding(new Insets(10));

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10));
        layout.add(title, 0, 0);
        layout.add(author, 0, 1);
        layout.add(pages, 0, 2);
        layout.add(description, 0, 3);
        layout.add(removeButton, 0, 4);
        layout.add(doneButton, 0, 5);

        Scene scene = new Scene(layout, 500, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void removeBookFromShelf(Book book) throws IOException {

        // Create new user and add to users with new book
        User newUser = this.loggedInUser;
        newUser.getBookShelf().removeBook(book);
        dataAccess.putUser(newUser);
        System.out.println(book.getTitle() + "added to book shelf");
    }
    /**
     * Changes the scene to the given file path
     * 
     * @param filePath the fxml file to change to
     * @param event    the mouse click
     * @throws IOException if the file cannot be found
     */
    private void changeScene(String filePath, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(filePath));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // mangler evnen til å fjerene bok fra shelf.
    // kan lese informasjon om boka, popup.
    // må bruke bookid, for å lese om boka
}
