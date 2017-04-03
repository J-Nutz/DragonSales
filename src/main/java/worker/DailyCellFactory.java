package worker;

/*
 * Created by Jonah on 4/3/2017.
 */

import database.tables.DailyStatsTable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DailyCellFactory implements Callback<DatePicker, DateCell>
{
    LocalDate firstDay = DailyStatsTable.getDateOfFirstStat().toLocalDate();
    LocalDate lastDay = DailyStatsTable.getDateOfLastStat().toLocalDate();

    @Override
    public DateCell call(final DatePicker datePicker)
    {
        return new DateCell()
        {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);

                if(date.isBefore(firstDay) ||
                   date.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                   date.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                   date.isAfter(lastDay))
                {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }
}