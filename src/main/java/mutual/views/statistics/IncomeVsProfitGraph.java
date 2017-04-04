package mutual.views.statistics;

/*
 * Created by Jonah on 3/27/2017.
 */

import database.tables.DailyStatsTable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import mutual.types.Interval;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class IncomeVsProfitGraph
{
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String, Number> barGraph;

    public IncomeVsProfitGraph()
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 100, 5);

        barGraph = new BarChart<>(xAxis, yAxis);

        initComponents();
        setData(Interval.DAILY, LocalDate.now());
        addComponents();
    }

    private void initComponents()
    {
        xAxis.setLabel("Interval");
        yAxis.setLabel("Amount In $");

        barGraph.setTitle("Income VS Profit");
        barGraph.setAnimated(false);
    }

    private void addComponents() {}

    public void setData(Interval interval, LocalDate startDate)
    {
        getBarGraph().getData().clear();

        if(interval.equals(Interval.DAILY))
        {
            setDayValues(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
        }
        else if(interval.equals(Interval.WEEKLY))
        {
            ArrayList<StatisticsTracker> weeklyStats = new ArrayList<>();

            if(startDate.getDayOfWeek().equals(DayOfWeek.MONDAY))
            {
                while(!startDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                {
                    weeklyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                    startDate = startDate.plusDays(1);
                }
            }

            setWeekValues(weeklyStats);
        }
    }

    private void setDayValues(StatisticsTracker stats)
    {
        String day = simpleDateFormat.format(stats.getDay());
        Number income = stats.getTotalIncome();
        Number profit = stats.getTotalProfit();
        int incomeAsInt = income.intValue();

        if(incomeAsInt < 75 && incomeAsInt > 50)
        {
            yAxis.setUpperBound(75);
        }
        else if(incomeAsInt < 50 && incomeAsInt > 25)
        {
            yAxis.setUpperBound(50);
        }
        else if(income.intValue() < 25)
        {
            yAxis.setUpperBound(25);
        }

        xAxis.setLabel("Daily Stats");

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        XYChart.Data<String, Number> incomeData = new XYChart.Data<>(day, income);
        incomeSeries.getData().add(incomeData);
        barGraph.getData().add(incomeSeries);

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");
        XYChart.Data<String, Number> profitData = new XYChart.Data<>(day, profit);
        profitSeries.getData().add(profitData);
        barGraph.getData().add(profitSeries);

        //TODO: Labels on each bar - See https://gist.github.com/jewelsea/5094893
    }

    private void setWeekValues(ArrayList<StatisticsTracker> weeklyStats)
    {
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");

        LocalDate date = weeklyStats.get(0).getDay().toLocalDate();
        String formattedDate = simpleDateFormat.format(date);
        xAxis.setLabel("Week Of " + formattedDate + " Stats");

        for(StatisticsTracker statisticsTracker : weeklyStats)
        {
            XYChart.Data<String, Number> incomeData = new XYChart.Data<>(date.getDayOfWeek().toString(), statisticsTracker.getTotalIncome());
            incomeSeries.getData().add(incomeData);

            XYChart.Data<String, Number> profitData = new XYChart.Data<>(date.getDayOfWeek().toString(), statisticsTracker.getTotalProfit());
            profitSeries.getData().add(profitData);

            date = date.plusDays(1);
        }

        barGraph.getData().add(incomeSeries);
        barGraph.getData().add(profitSeries);
    }

    public BarChart getBarGraph()
    {
        return barGraph;
    }
}