package admin.employees;

/*
 * Created by Jonah on 12/18/2016.
 */

import database.tables.EmployeesTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mutual.types.Employee;

public class EmployeeSelector extends VBox
{
    public EmployeeSelector()
    {
        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setPadding(new Insets(10));
        setSpacing(15);
        setAlignment(Pos.CENTER);
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 2))));
    }

    private void addComponents()
    {
        for(Employee employee : EmployeesTable.getEmployees())
        {
            EmployeeToSelect employeeToSelect = new EmployeeToSelect(employee);
            getChildren().add(employeeToSelect);
        }
    }
}