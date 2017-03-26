package worker;

/*
 * Created by Jonah on 2/24/2017.
 */

import database.tables.ScheduleTable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ScheduleSaver
{
    public ScheduleSaver() {}

    public static boolean saveSchedule(DayOfWeek dayOfWeek, ArrayList<ComboBox<String>> comboBoxes)
    {
        ArrayList<String> schedule = comboBoxes
                .stream()
                .map(comboBox -> comboBox.getSelectionModel().getSelectedItem())
                .collect(Collectors.toCollection(ArrayList::new));

        return !schedule.isEmpty() && ScheduleTable.setScheduleFor(dayOfWeek, schedule);
    }

    public static boolean loadEditableSchedule(DayOfWeek dayOfWeek, ArrayList<ComboBox<String>> comboBoxes)
    {
        ArrayList<String> schedule = ScheduleTable.getScheduleFor(dayOfWeek);

        if(schedule != null)
        {
            for(int i = 0; i < schedule.size(); i++)
            {
                ComboBox<String> comboBox = comboBoxes.get(i);
                comboBox.setValue(schedule.get(i));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean loadUneditableSchedule(DayOfWeek dayOfWeek, ArrayList<Label> labels)
    {
        ArrayList<String> schedule = ScheduleTable.getScheduleFor(dayOfWeek);

        if(schedule != null)
        {
            for(int i = 0; i < schedule.size(); i++)
            {
                Label label = labels.get(i);
                label.setText(schedule.get(i));
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}