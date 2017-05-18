package admin.employees;

/*
 * Created by Jonah on 2/14/2017.
 */

import database.tables.EmployeesTable;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import mutual.security.UserValidator;
import mutual.types.Employee;
import mutual.types.Position;
import mutual.views.View;
import mutual.views.login.SignUpView;
import worker.BigDecimalSpinnerValueFactory;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static admin.home.ViewContainer.switchView;
import static worker.ErrorMessageShower.showErrorMessage;

public class HireEmployeeView extends VBox
{
    private GridPane container;

    private FileChooser imageChooser;
    private File imageChose;

    private ImageView imageView;
    private TextField nameTextField;
    private TextField emailTextField;
    private Label positionLabel;
    private ComboBox<Position> positionComboBox;
    private Label wageLabel;
    private Spinner<BigDecimal> wageSpinner;
    private Label dateHiredLabel;
    private DatePicker dateHiredPicker;

    private HBox buttonContainer;
    private Button clearFieldsButton;
    private Button hireButton;
    private Button cancelButton;

    private SignUpView signUpView;

    public HireEmployeeView()
    {
        container = new GridPane();

        imageChooser = new FileChooser();
        imageView = new ImageView(new Image(getClass().getResourceAsStream("/hireEmployee.png")));
        nameTextField = new TextField();
        emailTextField = new TextField();
        positionLabel = new Label("Position");
        positionComboBox = new ComboBox<>(FXCollections.observableArrayList(Position.values()));
        wageLabel = new Label("Wage $");
        wageSpinner = new Spinner<>();
        dateHiredLabel = new Label("Hired");
        dateHiredPicker = new DatePicker(LocalDate.now());

        buttonContainer = new HBox();
        clearFieldsButton = new Button("Clear");
        hireButton = new Button("Hire");
        cancelButton = new Button("Cancel");

        signUpView = new SignUpView();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setAlignment(Pos.TOP_LEFT);

        container.setPadding(new Insets(15));
        container.setVgap(15);
        container.setHgap(5);

        imageChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        imageView.setEffect(new DropShadow(2, Color.DARKGRAY));
        imageView.setFitHeight(225);
        imageView.setFitWidth(175);
        imageView.setOnMouseEntered(event -> setCursor(Cursor.HAND));
        imageView.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
        imageView.setOnMouseClicked(event ->
        {
            imageChose = imageChooser.showOpenDialog(this.getScene().getWindow());

            if(imageChose != null)
            {
                imageView.setImage(getEmployeeImage(imageChose));
            }
        });

        nameTextField.setPromptText("First & Last Name");
        nameTextField.setMaxWidth(150);

        emailTextField.setPromptText("Email");
        emailTextField.setMaxWidth(150);

        positionComboBox.getSelectionModel().selectFirst();

        wageSpinner.setValueFactory(new BigDecimalSpinnerValueFactory(new BigDecimal(0.25)));
        wageSpinner.getValueFactory().setValue(new BigDecimal(0.00));
        wageSpinner.setMaxWidth(75);

        dateHiredPicker.setMaxWidth(105);

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(10);

        clearFieldsButton.setOnAction(event -> resetFields());

        hireButton.setOnAction(event ->
        {
            if(validEmployeeCredentials())
            {
                addHireeCredentialsFields();
            }
        });

        cancelButton.setOnAction(event -> switchView(getParent(), View.MANAGE));

        signUpView.setBottom(null);

        signUpView.setOnValidSubmit(event ->
        {
            if(signUpView.validVerificationCode())
            {
                EmployeesTable.addEmployee(constructEmployee());
                switchView(getParent(), View.MANAGE);
            }
        });
    }

    private void addComponents()
    {
        GridPane.setRowSpan(imageView, 6);
        GridPane.setColumnSpan(imageView, 3);
        container.add(imageView, 0, 0);

        GridPane.setColumnSpan(nameTextField, 2);
        container.add(nameTextField, 3, 0);
        GridPane.setColumnSpan(emailTextField, 2);
        container.add(emailTextField, 3, 1);
        container.add(positionLabel, 3, 2);
        container.add(positionComboBox, 4, 2);
        container.add(wageLabel, 3, 3);
        container.add(wageSpinner, 4, 3);
        container.add(dateHiredLabel, 3, 4);
        container.add(dateHiredPicker, 4, 4);

        buttonContainer.getChildren().addAll(clearFieldsButton, hireButton, cancelButton);

        GridPane.setColumnSpan(buttonContainer, 2);
        container.add(buttonContainer, 3, 5);

        getChildren().add(container);
    }

    private void addHireeCredentialsFields()
    {
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(5, 20, 5, 20));
        GridPane.setRowSpan(separator, 6);
        container.add(separator, 5, 0);

        GridPane.setColumnSpan(signUpView, 5);
        GridPane.setRowSpan(signUpView, 8);
        container.add(signUpView, 6, 0);

        signUpView.setCredentials(nameTextField.getText(), emailTextField.getText());
    }

    private void resetFields()
    {
        imageView.setImage(null);
        imageChose = null;

        nameTextField.clear();
        emailTextField.clear();
        positionComboBox.getSelectionModel().selectFirst();
        wageSpinner.getValueFactory().setValue(new BigDecimal(0.00));
    }

    private boolean validEmployeeCredentials()
    {
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        boolean validEmail = (!email.isEmpty()) && UserValidator.validEmail(email);
        boolean validName = (!name.isEmpty()) && (name.contains(" ")) && (name.length() > 2);

        if(!validName)
        {
            showErrorMessage(this, "Name Doesn't Meet Requirements");
            return false;
        }
        else if(!validEmail)
        {
            showErrorMessage(this, "Email Doesn't Meet Requirements");
            return false;
        }
        else
        {
            return true;
        }
    }

    private Employee constructEmployee()
    {
        Employee employee = new Employee();

        employee.setImagePath(imageChose.getAbsolutePath());
        employee.setName(nameTextField.getText());
        employee.setEmail(emailTextField.getText());
        employee.setPosition(positionComboBox.getSelectionModel().getSelectedItem());
        employee.setWage(wageSpinner.getValue());

        return employee;
    }

    private Image getEmployeeImage(File image)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Image> futureImage = executor.submit(() -> new Image(image.toURI().toURL().toString()));

        try
        {
            return futureImage.get();
        }
        catch(InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            executor.shutdown();
        }
    }
}