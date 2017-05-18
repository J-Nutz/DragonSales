package mutual.views.login;

/*
 * Created by Jonah on 12/8/2016.
 */

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mutual.security.UserValidator;
import mutual.views.View;

import java.util.Arrays;

import static admin.home.ViewContainer.switchView;

public class LockedView extends VBox
{
    private PasswordField passwordField;
    private Button unlockButton;
    private Label errorLabel;

    private UserValidator userValidator;
    private View oldView;

    public LockedView(View oldView) // TODO: Make pin or pattern unlock option
    {
        this.oldView = oldView;

        passwordField = new PasswordField();
        unlockButton = new Button("Unlock");
        errorLabel = new Label("Wrong Password");
        userValidator = new UserValidator();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setOnMousePressed(event -> requestFocus());
        requestFocus();

        setOnKeyReleased(event ->
        {
            if(event.getCode() == KeyCode.ENTER)
            {
                unlockButton.fire();
            }
        });

        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(150);

        unlockButton.setOnAction(e ->
        {
            char[] attemptedPassword = passwordField.getText().toCharArray();
            boolean validLogin = userValidator.validLockedLogin(attemptedPassword);

            Arrays.fill(attemptedPassword, '0');

            if(validLogin)
            {
                getChildren().remove(errorLabel);
                switchView(getParent(), this.oldView);
            }
            else
            {
                if(!getChildren().contains(errorLabel))
                {
                    getChildren().add(errorLabel);
                }
            }
        });

        errorLabel.setTextFill(Color.RED);
    }

    private void addComponents()
    {
        getChildren().addAll(passwordField, unlockButton);
    }

    @Override
    public void requestFocus()
    {
        Platform.runLater(super::requestFocus);
    }
}