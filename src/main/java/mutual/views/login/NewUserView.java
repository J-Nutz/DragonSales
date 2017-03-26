package mutual.views.login;

/*
 * Created by Jonah on 12/5/2016.
 */

import database.tables.UsersTable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mutual.security.Salt;
import mutual.security.UserValidator;
import mutual.types.User;
import mutual.views.FullAccess;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static admin.home.ViewContainer.switchView;
import static mutual.security.Encryption.encrypt;
import static worker.TimedDisplay.showErrorMessage;

public class NewUserView extends BorderPane
{
    private VBox centerBox;
    private VBox bottomBox;

    private TextField emailTextField;
    private TextField nameTextField;
    private TextField usernameTextField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Button submitCredentialsButton;
    private TextField verificationCodeTextField;
    private Button checkVerificationCodeButton;

    private Label goToLoginLabel;
    private Button goToLoginButton;

    private String verificationCode;

    public NewUserView()
    {
        centerBox = new VBox(15);
        emailTextField = new TextField();
        nameTextField = new TextField();
        usernameTextField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        submitCredentialsButton = new Button("Submit");
        verificationCodeTextField = new TextField();
        checkVerificationCodeButton = new Button("Check Code");

        bottomBox = new VBox(15);
        goToLoginLabel = new Label("Already Have An Account?");
        goToLoginButton = new Button("Go Login");

        verificationCode = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999 + 1));

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        //This
        setOnMousePressed(event -> requestFocus());
        requestFocus();

        //Center Box
        centerBox.setAlignment(Pos.CENTER);

        emailTextField.setPromptText("Email");
        emailTextField.setMaxWidth(150);
        addToolTipListener(emailTextField, new Tooltip("Provide Correct Email As It \nWill Be Used In The Future"));

        nameTextField.setPromptText("First & Last Name");
        nameTextField.setMaxWidth(150);
        addToolTipListener(nameTextField, new Tooltip("Enter First And Last Name \n   Separated By A Space"));

        usernameTextField.setPromptText("Username");
        usernameTextField.setMaxWidth(150);
        addToolTipListener(usernameTextField, new Tooltip("Enter Username That \n   Is > 3 Characters"));

        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(150);
        addToolTipListener(passwordField, new Tooltip("Enter Password That \n   Is > 5 Characters"));

        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(150);
        addToolTipListener(confirmPasswordField, new Tooltip("Confirm Password"));

        verificationCodeTextField.setPromptText("Verification Code");
        verificationCodeTextField.setMaxWidth(150);
        addToolTipListener(verificationCodeTextField, new Tooltip("Enter The 4 Digit Number \n    Sent To Your Email"));

        submitCredentialsButton.setOnAction(e ->
        {
            String email = emailTextField.getText();
            String name = nameTextField.getText();
            String username = usernameTextField.getText();
            char[] password = confirmPasswordField.getText().toCharArray();

            boolean validEmail = (!email.isEmpty()) && UserValidator.validEmail(email);
            boolean validName = (!name.isEmpty()) && (name.contains(" ")) && (name.length() > 3);
            boolean usernameValidAndAvailable = username.length() > 3 && UsersTable.usernameAvailable(username);
            boolean passwordsValidAndMatch = password.length > 5 && passwordField.getText().equals(confirmPasswordField.getText());

            if(validEmail && validName && usernameValidAndAvailable && passwordsValidAndMatch)
            {
                fieldsEditable(false);

                Arrays.fill(password, '0');

                /*VerificationEmailSender emailSender = new VerificationEmailSender();
                Result emailResult = emailSender.sendEmail(email, verificationCode);

                if(!emailResult.isCompleted())
                {
                    fieldsEditable(true);
                    showErrorMessage(centerBox, "No Internet Connection Or SMTP Blocked \n Connect To Internet Or Switch Connection");
                }
                else
                {*/
                    Platform.runLater(() ->
                    {
                        fieldsEditable(false);
                        centerBox.getChildren().addAll(verificationCodeTextField, checkVerificationCodeButton);
                        System.out.println(verificationCode);
                    });
                //}
            }
            else
            {
                fieldsEditable(true);

                if(!validEmail)
                {
                    showErrorMessage(centerBox, "Email Doesn't Meet Requirements");
                }
                else if(!validName)
                {
                    showErrorMessage(centerBox, "Name Doesn't Meet Requirements");
                }
                else if(!usernameValidAndAvailable)
                {
                    showErrorMessage(centerBox, "Username Taken Or Doesn't Meet Requirements");
                }
                else if(!(password.length > 5))
                {
                    showErrorMessage(centerBox, "Password Not Long Enough");
                }
                else
                {
                    showErrorMessage(centerBox, "Passwords Don't Match");
                }
            }
        });

        checkVerificationCodeButton.setOnAction(e ->
        {
            if(verificationCodeTextField.getText().equals(verificationCode))
            {
                UsersTable.addUser(constructUser());
                //setView(FullAccess.LOGIN, true);
            }
            else
            {
                showErrorMessage(centerBox, "Wrong Verification Code");
            }
        });

        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(0, 0, 15, 0));

        goToLoginButton.setOnAction(e ->
        {
            long startTime = System.nanoTime();

            /*ViewContainer2 viewContainer = (ViewContainer2) getParent().lookup("#viewContainer");
            viewContainer.setView(FullAccess.LOGIN);*/

            //setView(FullAccess.LOGIN, true);

            switchView(getParent(), FullAccess.LOGIN);

            long endTime = System.nanoTime();
            System.out.println("Switched To Login View In: " + ((endTime - startTime) / 1000000) + "ms");
        });
    }

    private void addComponents()
    {
        centerBox.getChildren().addAll(nameTextField, emailTextField, usernameTextField, passwordField, confirmPasswordField, submitCredentialsButton);
        bottomBox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), goToLoginLabel, goToLoginButton);

        setCenter(centerBox);
        setBottom(bottomBox);
    }

    private void addToolTipListener(Control control, Tooltip tooltip)
    {
        control.focusedProperty().addListener((observable, oldValue, newValue) ->
        {
            if(newValue)
            {
                tooltip.show(control,
                        control.getScene().getWindow().getX() + control.getLayoutX() + control.getWidth() + 15,
                        control.getScene().getWindow().getY() + control.getLayoutY() + control.getHeight());
            }
            else
            {
                tooltip.hide();
            }
        });
    }

    public void setCredentials(String name, String email)
    {
        nameTextField.setText(name);
        emailTextField.setText(email);
    }

    public void setOnValidSubmit(EventHandler<ActionEvent> action)
    {
        checkVerificationCodeButton.setOnAction(action);
    }

    public boolean validVerificationCode()
    {
        if(verificationCodeTextField.getText().equals(verificationCode))
        {
            UsersTable.addUser(constructUser());
            return true;
        }
        else
        {
            showErrorMessage(centerBox, "Wrong Verification Code");
            return false;
        }
    }

    public User constructUser()
    {
        User user = new User();

        user.setEmail(emailTextField.getText());
        user.setName(nameTextField.getText());
        user.setUsername(usernameTextField.getText());
        user.setSalt(new Salt().addSalt(8)); // SET SALT BEFORE SETTING PASSWORD!! PASSWORD SETS SALT TO ALL 0'S
        user.setPassword(encrypt(passwordField.getText().toCharArray(), user.getSalt()));

        return user;
    }

    public void fieldsEditable(boolean editable)
    {
        Platform.runLater(() ->
        {
            emailTextField.setEditable(editable);
            nameTextField.setEditable(editable);
            usernameTextField.setEditable(editable);
            passwordField.setEditable(editable);
            confirmPasswordField.setEditable(editable);
            submitCredentialsButton.setDisable(!editable);
        });
    }

    @Override
    public void requestFocus()
    {
        Platform.runLater(super::requestFocus);
    }
}