package admin.employees;

/*
 * Created by Jonah on 1/9/2017.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ScheduleDayView extends VBox
{
    private Label dayLabel;
    private Label dateLabel;

    private ArrayList<String> employees;

    private DayOfWeek dayOfWeek;

    public ScheduleDayView(int day, ArrayList<String> employees)
    {
        this.employees = employees;

        dayLabel = new Label();
        dateLabel = new Label();

        dayOfWeek = setDayAndDate(day);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        dayLabel.setFont(new Font(22));

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);
    }

    private void addComponents()
    {
        getChildren().addAll(dayLabel, dateLabel);
        getChildren().add(new ScheduleShiftView(employees, dayOfWeek));
    }

    private DayOfWeek setDayAndDate(int dayOfWeek)
    {
        Calendar c = GregorianCalendar.getInstance();

        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat df = new SimpleDateFormat("M-d-yyyy", Locale.getDefault());

        if(dayOfWeek == 1)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Monday");
            dateLabel.setText(df.format(c.getTime()));

            return DayOfWeek.MONDAY;
        }
        else if(dayOfWeek == 2)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Tuesday");
            c.add(Calendar.DATE, 1);
            dateLabel.setText(df.format(c.getTime()));

            return DayOfWeek.TUESDAY;
        }
        else if(dayOfWeek == 3)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Wednesday");
            c.add(Calendar.DATE, 2);
            dateLabel.setText(df.format(c.getTime()));

            return DayOfWeek.WEDNESDAY;
        }
        else if(dayOfWeek == 4)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Thursday");
            c.add(Calendar.DATE, 3);
            dateLabel.setText(df.format(c.getTime()));

            return DayOfWeek.THURSDAY;
        }
        else if(dayOfWeek == 5)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));
            dayLabel.setText("Friday");
            c.add(Calendar.DATE, 4);
            dateLabel.setText(df.format(c.getTime()));

            return DayOfWeek.FRIDAY;
        }
        else
        {
            dayLabel.setText("Null");
            dateLabel.setText("Null");

            return DayOfWeek.SUNDAY;
        }
    }
}