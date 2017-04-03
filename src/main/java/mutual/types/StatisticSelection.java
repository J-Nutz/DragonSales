package mutual.types;

/*
 * Created by Jonah on 3/29/2017.
 */

import java.sql.Date;

public class StatisticSelection
{
    private Interval interval;
    private Date startDate;
    private String statisticsType;

    public StatisticSelection(Interval interval, Date startDate, String statisticsType)
    {
        this.interval = interval;
        this.startDate = startDate;
        this.statisticsType = statisticsType;
    }


    public Interval getInterval()
    {
        return interval;
    }

    public void setInterval(Interval interval)
    {
        this.interval = interval;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public String getStatisticsType()
    {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType)
    {
        this.statisticsType = statisticsType;
    }
}