package admin.employees;

/*
 * Created by Jonah on 1/9/2017.
 */

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class EmployeeScheduler extends GridPane
{
    public EmployeeScheduler()
    {
        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setPadding(new Insets(15));
    }

    private void addComponents()
    {
        for (int i = 0; i < 5; i++)
        {
            ScheduleDayView scheduleDayView = new ScheduleDayView(i + 1);
            setHgrow(scheduleDayView, Priority.ALWAYS);
            add(scheduleDayView, i, 0);
        }
    }
}