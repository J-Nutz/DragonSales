package admin.employees;

/*
 * Created by Jonah on 1/9/2017.
 */

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

public class EmployeeScheduler extends GridPane
{
    private ArrayList<String> employees;

    public EmployeeScheduler(ArrayList<String> employees)
    {
        this.employees = employees;

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setPadding(new Insets(15));

        employees.add("Empty");
    }

    private void addComponents()
    {
        for(int i = 0; i < 5; i++)
        {
            ScheduleDayView scheduleDayView = new ScheduleDayView(i + 1, employees);
            setHgrow(scheduleDayView, Priority.ALWAYS);
            add(scheduleDayView, i, 0);
        }
    }
}