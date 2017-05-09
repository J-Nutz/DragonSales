package admin.employees;

/*
 * Created by Jonah on 1/9/2017.
 */

import database.tables.EmployeesTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class ScheduleDayView extends VBox
{
    private Label dayLabel;
    private Label dateLabel;

    private ArrayList<String> employees;

    private Date date;

    public ScheduleDayView(int day)
    {
        this.employees = EmployeesTable.getEmployeeNames();
        employees.add(0, "Empty");

        dayLabel = new Label();
        dateLabel = new Label();

        date = setDayAndDate(day);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        dayLabel.setFont(Font.font("Ubuntu", FontWeight.BOLD, 22));
        dateLabel.setFont(Font.font("Ubuntu", FontWeight.MEDIUM, 13));

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);
    }

    private void addComponents()
    {
        getChildren().addAll(dayLabel, dateLabel);
        getChildren().add(new ScheduleShiftView(employees, date));
    }

    private Date setDayAndDate(int dayOfWeek)
    {
        LocalDate localDate = LocalDate.now();

        Calendar c = GregorianCalendar.getInstance();

        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat df = new SimpleDateFormat("M-d-yyyy", Locale.getDefault());

        if(dayOfWeek == 1)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Monday");
            dateLabel.setText(df.format(c.getTime()));

            return getDate(DayOfWeek.MONDAY);
        }
        else if(dayOfWeek == 2)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Tuesday");
            c.add(Calendar.DATE, 1);
            dateLabel.setText(df.format(c.getTime()));

            return getDate(DayOfWeek.TUESDAY);
        }
        else if(dayOfWeek == 3)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Wednesday");
            c.add(Calendar.DATE, 2);
            dateLabel.setText(df.format(c.getTime()));

            return getDate(DayOfWeek.WEDNESDAY);
        }
        else if(dayOfWeek == 4)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 0, 2, 2))));
            dayLabel.setText("Thursday");
            c.add(Calendar.DATE, 3);
            dateLabel.setText(df.format(c.getTime()));

            return getDate(DayOfWeek.THURSDAY);
        }
        else if(dayOfWeek == 5)
        {
            setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));
            dayLabel.setText("Friday");
            c.add(Calendar.DATE, 4);
            dateLabel.setText(df.format(c.getTime()));

            return getDate(DayOfWeek.FRIDAY);
        }
        else
        {
            dayLabel.setText("Null");
            dateLabel.setText("Null");

            return Date.valueOf(localDate.with(previousOrSame(DayOfWeek.SATURDAY)));
        }
    }

    private Date getDate(DayOfWeek dayOfWeek)
    {
        LocalDate localDate = LocalDate.now();

        if(LocalDate.now().getDayOfWeek().getValue() > dayOfWeek.getValue())
        {
            return Date.valueOf(localDate.with(previousOrSame(dayOfWeek)));
        }
        else
        {
            return Date.valueOf(localDate.with(nextOrSame(dayOfWeek)));
        }
    }
}