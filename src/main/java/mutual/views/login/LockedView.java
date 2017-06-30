package mutual.views.login;

/*
 * Created by Jonah on 12/8/2016.
 */

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.security.UserValidator;
import mutual.views.View;

import java.util.Arrays;

import static admin.home.ViewContainer.switchView;

public class LockedView extends VBox
{
    private Label lockedLabel;
    private Label enterPassLabel;
    private PasswordField passwordField;
    private Button unlockButton;
    private Label errorLabel;

    private UserValidator userValidator;
    private View oldView;

    public LockedView(View oldView) // TODO: Make pin or pattern unlock option
    {
        this.oldView = oldView;

        lockedLabel = new Label("Dragon Sales Is Locked");
        enterPassLabel = new Label("Enter Your Password To Get Back In");
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

        lockedLabel.setFont(new Font(20));
        enterPassLabel.setFont(new Font(20));
        enterPassLabel.setPadding(new Insets(0, 0, 20, 0));

        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(16));
        passwordField.setMaxWidth(150);

        unlockButton.setFont(new Font(16));
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
        getChildren().addAll(lockedLabel, enterPassLabel, passwordField, unlockButton);
    }

    @Override
    public void requestFocus()
    {
        Platform.runLater(super::requestFocus);
    }
}