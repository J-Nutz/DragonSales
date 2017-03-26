package admin.employees;

/*
 * Created by Jonah on 2/28/2017.
 */

import javafx.scene.layout.BorderPane;
import mutual.types.Employee;

public class ManageEmployeesView extends BorderPane
{
    // Top search bar search for employee -> Employee bar on right
    // Hire Employee button top right in search bar

    private EmployeeSelector employeeSelector;

    public ManageEmployeesView()
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