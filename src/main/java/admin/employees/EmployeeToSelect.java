package admin.employees;

/*
 * Created by Jonah on 2/14/2017.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.types.Employee;

public class EmployeeToSelect extends VBox
{
    private Employee employee;

    private Label nameLabel;
    private Label positionLabel;

    public EmployeeToSelect(Employee employee)
    {
        this.employee = employee;

        nameLabel = new Label(employee.getName());
        positionLabel = new Label(employee.getPosition().toString());

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        setOnMouseEntered(event -> setCursor(Cursor.HAND));
        setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
        setOnMouseClicked(event ->
        {
            EmployeeSelector employeeSelector = (EmployeeSelector) getParent();
            employeeSelector.getChildren()
                            .stream()
                            .filter(node -> node instanceof EmployeeToSelect)
                            .forEach(node ->
                                    {
                                        EmployeeToSelect employeeToSelect = (EmployeeToSelect) node;
                                        employeeToSelect.unselectBackground();
                                    });

            setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

            ManageEmployeesView manageEmployeesView = (ManageEmployeesView) getParent().getParent();
            manageEmployeesView.setSelectedEmployee(this.employee);
        });

        nameLabel.setFont(new Font(16));
        positionLabel.setFont(new Font(12));
    }

    private void addComponents()
    {
        getChildren().addAll(nameLabel, positionLabel);
    }

    public void unselectBackground()
    {
        setBackground(null);
    }
}