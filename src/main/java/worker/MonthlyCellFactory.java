package worker;

/*
 * Created by Jonah on 4/3/2017.
 */

import database.tables.DailyStatsTable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

public class MonthlyCellFactory implements Callback<DatePicker, DateCell>
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
                while(firstDay.getDayOfMonth() != 1)
                {
                    firstDay = firstDay.minusDays(1);
                }

                while(lastDay.getDayOfMonth() != 1)
                {
                    lastDay = lastDay.minusDays(1);
                }

                if(date.isBefore(firstDay) ||
                   date.isAfter(lastDay) ||
                   date.getDayOfMonth() != 1)
                {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }
}