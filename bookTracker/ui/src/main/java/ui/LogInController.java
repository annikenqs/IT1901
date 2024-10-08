package ui;

import java.io.IOException;

import core.User;
import core.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Controller connectet to LogInPage.fxml.
 * Manages the interactions on the login page.
 */
public class LogInController extends DataAccessController {

    private User user;
    private String wrongPassword;

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    Button logInButton;

    @FXML
    Button RegisterButton;

    @FXML
    Label feedbackLabel;

    /**
     * Changes scene to the registration page.
     * 
     * @param event click on registerButton.
     * @throws IOException if it cannot find the fxml file.
     */
    public void handleRegisterButton(ActionEvent event) throws IOException {
        changeScene("RegistrationPage.fxml", event);
    }

    /**
     * Checks if the user exists and changes the scene to StartPage.fxml if it does.
     * 
     * @param event click on logInButton.
     * @throws IOException if it cannot find the fxml file.
     */
    public void handleLogInButton(ActionEvent event) throws IOException {
        try {
            this.user = this.logIn(usernameField.getText(), passwordField.getText());
            this.user.setLoggedIn(true);
            this.getDataAccess().putUser(user);
            changeScene("Startpage.fxml", event);
            feedbackLabel.setText("Successfull log in");
        } catch (IllegalArgumentException e) {
            feedbackLabel.setText(e.getMessage());
        }

    }

    /**
     * Gives the user acceess to the application if provided with
     * correct username and password.
     * 
     * @param username      the username entered by user.
     * @param passwordInput the password entered by user.
     * @return the user connected to the given username.
     * @throws IllegalArgumentException when provided with wrong username and
     *                                  password.
     */
    private User logIn(String username, String passwordInput) {
        Users users = this.getDataAccess().getUsers();
        if (users.getUser(username) == null) {
            setWrongPassword("Wrong username or password");
            throw new IllegalArgumentException("Wrong username or password");
        }
        User user = this.getDataAccess().getUserByUsername(username);
        String password = user.getPassword();
        if (!passwordInput.equals(password)) {
            setWrongPassword("Wrong username or password");
            throw new IllegalArgumentException("Wrong username or password");
        }
        return this.getDataAccess().getUserByUsername(username);
    }

    /**
     * Changes the scene.
     * 
     * @param filePath the scene to change to.
     * @param event    the click.
     * @throws IOException if it cannot find the fxml file.
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

    /**
     * Getter for the String that shows in the UI if the password is wrong.
     * For UI test.
     * 
     * @return message if the password is wrong.
     */
    public String getWrongPassword() {
        return wrongPassword;
    }

    /**
     * Getter for the String that shows in the UI if the password is wrong.
     * For UI test.
     * 
     * @param wrongPassword message that shows when password is wrong.
     */
    public void setWrongPassword(String wrongPassword) {
        this.wrongPassword = wrongPassword;
    }

}
