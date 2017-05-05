package mutual.views.login;

/*
 * Created by Jonah on 11/8/2016.
 */

import database.tables.LoggedInUserTable;
import database.tables.UsersTable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mutual.security.UserValidator;
import mutual.types.User;
import mutual.views.FullAccess;

import java.util.Arrays;

import static admin.home.ViewContainer.switchView;
import static mutual.security.Encryption.encrypt;
import static worker.ErrorMessageShower.showErrorMessage;

public class LoginView extends BorderPane
{
    private VBox topBox;
    private Label welcomeLabel;
    private Label loginLabel;

    private VBox centerBox;
    private TextField usernameTF;
    private PasswordField passwordTF;
    private Button loginBtn;

    private VBox bottomBox;
    private Label goToNewUserLabel;
    private Button newUserBtn;

    public LoginView()
    {
        topBox = new VBox(5);
        welcomeLabel = new Label("Welcome To Dragon Sales!");
        loginLabel = new Label("Login Below");

        centerBox = new VBox(5);
        usernameTF = new TextField();
        passwordTF = new PasswordField();
        loginBtn = new Button("Login");

        bottomBox = new VBox(15);
        goToNewUserLabel = new Label("Don't Have An Account Yet?");
        newUserBtn = new Button("Go Sign Up");

        init();
        addComponents();
    }

    private void init()
    {
        setOnMousePressed(event -> requestFocus());
        requestFocus();

        setOnKeyReleased(event ->
        {
            if(event.getCode() == KeyCode.ENTER)
            {
                loginBtn.fire();
            }
        });

        topBox.setAlignment(Pos.CENTER);
        welcomeLabel.setFont(new Font(30));
        loginLabel.setFont(new Font(20));

        centerBox.setAlignment(Pos.CENTER);

        usernameTF.setPromptText("Username");
        usernameTF.setMaxWidth(150);
        usernameTF.setFont(new Font(14));

        passwordTF.setPromptText("Password");
        passwordTF.setMaxWidth(150);
        passwordTF.setFont(new Font(14));

        loginBtn.setAlignment(Pos.CENTER);
        loginBtn.setFont(new Font(15));
        loginBtn.setOnAction(event ->
        {
            String userName = usernameTF.getText();
            char[] password = passwordTF.getText().toCharArray();

            if(!userName.isEmpty() && password.length != 0)
            {
                User correctUser = UsersTable.getUser(userName);
                User attemptedUser = new User();

                if(correctUser != null)
                {
                    attemptedUser.setUsername(userName);
                    attemptedUser.setPassword(encrypt(password,
                                                      correctUser.getSalt()));
                    attemptedUser.setSalt(correctUser.getSalt());

                    String[] loginAttemptResult = UserValidator.validLogin(attemptedUser);
                    boolean validLogin = Boolean.parseBoolean(loginAttemptResult[0]);
                    String loginResultMessage = loginAttemptResult[1];

                    // Set password[] to all zeroes (Security)
                    Arrays.fill(password, '0');

                    if(validLogin)
                    {
                        LoggedInUserTable.setLoggedInUser(attemptedUser);
                        switchView(getParent(), FullAccess.HOME);
                    }
                    else
                    {
                        showErrorMessage(centerBox, loginResultMessage);
                    }
                }
                else
                {
                    showErrorMessage(centerBox, "User Not Found");
                }
            }
            else
            {
                if(userName.isEmpty())
                {
                    showErrorMessage(centerBox, "Username Not Entered");
                }
                else if(password.length == 0)
                {
                    showErrorMessage(centerBox, "Password Not Entered");
                }
            }
        });

        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(0, 0, 15, 0));

        goToNewUserLabel.setFont(new Font(16));

        newUserBtn.setFont(new Font(14));
        newUserBtn.setOnAction(e -> switchView(getParent(), FullAccess.NEW_USER));
    }

    private void addComponents()
    {
        topBox.getChildren().addAll(welcomeLabel, loginLabel, new Separator(Orientation.HORIZONTAL));
        centerBox.getChildren().addAll(usernameTF, passwordTF, loginBtn);
        bottomBox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), goToNewUserLabel, newUserBtn);

        setTop(topBox);
        setCenter(centerBox);
        setBottom(bottomBox);
    }

    @Override
    public void requestFocus()
    {
        Platform.runLater(super::requestFocus);
    }
}