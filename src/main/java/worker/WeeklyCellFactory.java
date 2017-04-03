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

public class WeeklyCellFactory implements Callback<DatePicker, DateCell>
{
    LocalDate firstDay = DailyStatsTable.getDateOfFirstStat().toLocalDate();
    LocalDate lastDay = DailyStatsTable.getDateOfLastStat().toLocalDate();

    @Override
    public DateCell call(DatePicker param)
    {
        return new DateCell()
        {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);

                //TODO: Multi-Thread ???
                while(firstDay.getDayOfWeek() != (DayOfWeek.MONDAY))
                {
                    firstDay = firstDay.minusDays(1);
                }

                while(lastDay.getDayOfWeek() != DayOfWeek.MONDAY)
                {
                    lastDay = lastDay.minusDays(1);
                }

                if(date.isBefore(firstDay) ||
                   date.getDayOfWeek() != (DayOfWeek.MONDAY) ||
                   date.isAfter(lastDay))
                {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }
}