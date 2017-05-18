package admin.employees;

/*
 * Created by Jonah on 12/18/2016.
 */

import database.tables.EmployeesTable;
import database.tables.UsersTable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mutual.types.Employee;
import mutual.views.View;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static admin.home.ViewContainer.switchView;

public class EmployeeProfile extends GridPane
{
    private Employee employee;

    private ImageView imageView;
    private GridPane profileContainer;
    private Label nameLabel;
    private Label emailLabel;
    private Label positionLabel;
    private Label wageLabel;
    private Label dateHiredLabel;

    private HBox buttonContainer;
    private Button statsButton;
    private ToggleButton manageButton;

    private Separator separator;

    private HBox buttonContainer2;
    private Button updateButton;
    private Button raiseButton;
    private Button fireButton;

    private Alert fireAlert;

    private Font font;

    public EmployeeProfile(Employee employee)
    {
        this.employee = employee;

        imageView = new ImageView(getEmployeeImage());
        profileContainer = new GridPane();
        nameLabel = new Label("Name: " + employee.getName());
        emailLabel = new Label("Email: " + employee.getEmail());
        positionLabel = new Label("Position: " + employee.getPosition().toString());
        wageLabel = new Label("Wage: $" + employee.getWage().toString());
        dateHiredLabel = new Label("Hired On: " + employee.getDateHired().toString());

        buttonContainer = new HBox();
        statsButton = new Button("Stats");
        manageButton = new ToggleButton("Manage");

        separator = new Separator(Orientation.HORIZONTAL);

        buttonContainer2 = new HBox();
        updateButton = new Button("Update");
        raiseButton = new Button("Raise");
        fireButton = new Button("Fire");

        fireAlert = new Alert(Alert.AlertType.WARNING, "About To Fire " + this.employee.getName() + "! \n\n Do You Want To Continue?", ButtonType.YES, ButtonType.NO);

        font = new Font(18);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setPadding(new Insets(15));
        setVgap(15);
        setHgap(15);

        imageView.setFitHeight(300);
        imageView.setFitWidth(250);

        profileContainer.setHgap(15);
        profileContainer.setVgap(15);

        nameLabel.setFont(font);

        emailLabel.setFont(font);

        positionLabel.setFont(font);

        wageLabel.setFont(font);

        dateHiredLabel.setFont(font);

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(15);

        statsButton.setFont(new Font(14));
        statsButton.setOnAction(event ->
        {
            switchView(this, View.STATS);
        });

        manageButton.setFont(new Font(14));

        manageButton.setOnAction(event ->
        {
            if(manageButton.isSelected())
            {
                add(separator, 0, 2);
                add(buttonContainer2, 0, 3);
            }
            else
            {
                getChildren().remove(separator);
                getChildren().remove(buttonContainer2);
            }

        });

        buttonContainer2.setSpacing(10);
        buttonContainer2.setAlignment(Pos.CENTER);

        //updateButton.setOnAction(event -> );

        //raiseButton.setOnAction(event -> );

        fireButton.setOnAction(event -> fireAlert.showAndWait());

        fireAlert.setResultConverter(param ->
        {
            if(param.equals(ButtonType.YES))
            {
                EmployeesTable.fireEmployee(employee.getName());
                UsersTable.deleteUser(employee.getEmail());
                switchView(this, View.MANAGE);
            }
            else
            {
                fireAlert.close();
            }

            return null;
        });
    }

    private void addComponents()
    {
        add(imageView, 0, 0);

        profileContainer.add(nameLabel, 0, 0);
        profileContainer.add(emailLabel, 0, 1);
        profileContainer.add(positionLabel, 0, 2);
        profileContainer.add(wageLabel, 0, 3);
        profileContainer.add(dateHiredLabel, 0, 4);

        add(profileContainer, 1, 0);

        buttonContainer.getChildren().add(statsButton);
        buttonContainer.getChildren().add(manageButton);

        add(buttonContainer, 0, 1);

        buttonContainer2.getChildren().addAll(updateButton, raiseButton, fireButton);

    }

    private Image getEmployeeImage()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Image> futureImage = executor.submit(() -> new Image(employee.getImagePath().toURI().toURL().toString()));

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