package admin.employees;

/*
 * Created by Jonah on 12/18/2016.
 */

import javafx.scene.layout.BorderPane;
import mutual.types.Employee;

public class AdminEmployeesView extends BorderPane
{
    private EmployeeSelector employeeSelector;

    public AdminEmployeesView()
    {
        employeeSelector = new EmployeeSelector();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {

    }

    private void addComponents()
    {
        setRight(employeeSelector);
    }

    public void setSelectedEmployee(Employee employee)
    {
        setCenter(new EmployeeProfile(employee));
    }
}