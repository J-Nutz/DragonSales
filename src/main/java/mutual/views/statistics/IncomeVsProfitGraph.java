package mutual.views.statistics;

/*
 * Created by Jonah on 3/27/2017.
 */

import database.tables.AllTimeStatsTable;
import database.tables.DailyStatsTable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import mutual.types.Interval;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class IncomeVsProfitGraph
{
    private SimpleDateFormat simpleDateFormat;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarGraph<String, Number> barGraph;
    private BigDecimal totalIncome;
    private BigDecimal totalProfit;
    private int totalItemsSold;
    private int totalSales;

    public IncomeVsProfitGraph()
    {
        simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");

        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 25, 5);
        barGraph = new BarGraph<>(xAxis, yAxis);

        initComponents();
        setData(Interval.DAILY, LocalDate.now());
    }

    private void initComponents()
    {
        xAxis.setLabel("Interval");
        yAxis.setLabel("Amount In $");

        barGraph.setTitle("Income VS Profit");
        barGraph.setAnimated(false);
        barGraph.setCategoryGap(25);
        barGraph.areValuesCurrency(true);

        totalIncome = new BigDecimal("0.00");
        totalProfit = new BigDecimal("0.00");
    }

    public void setData(Interval interval, LocalDate startDate)
    {
        getGraph().getData().clear();

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
                    /*if(!startDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
                       !startDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                    {*/
                        weeklyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                        startDate = startDate.plusDays(1);
                    //}
                }
            }

            setWeekValues(weeklyStats);
        }
        else if(interval.equals(Interval.MONTHLY))
        {
            ArrayList<StatisticsTracker> monthlyStats = new ArrayList<>();

            if(startDate.getDayOfMonth() == 1)
            {
                int numOfDays = (Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) + 1);
                //Bug: Adding 1 Makes bars not show on bar graph
                //When 29 Values, Bars Are There
                //When 30 Values, Bars Disappear
                //Using For Loop Fixes This

                for(int i = 1; i < numOfDays; i++)
                {
                    monthlyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                    startDate = startDate.plusDays(1);
                }

                /*while(startDate.getDayOfMonth() != numOfDays)
                {
                    monthlyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                    startDate = startDate.plusDays(1);
                }*/
            }

            setMonthValues(monthlyStats);
        }
        else if(interval.equals(Interval.YEARLY))
        {

        }
        else if(interval.equals(Interval.ALL))
        {
            setAllValues(AllTimeStatsTable.getAllTimeStats());
        }
    }

    private void setDayValues(StatisticsTracker stats)
    {
        if(stats != null)
        {
            totalIncome = stats.getTotalIncome();
            totalProfit = stats.getTotalProfit();
            totalItemsSold = stats.getNumOfItemsSold();
            totalSales = stats.getNumOfSales();

            String day = simpleDateFormat.format(stats.getDay());
            Number income = stats.getTotalIncome();
            Number profit = stats.getTotalProfit();

            barGraph.setTitle(day + " Income Stats");
            xAxis.setLabel("Day");

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

            xAxis.setLabel("Daily Stats");
            int maxValue = totalIncome.compareTo(totalProfit) == 1 ? totalIncome.intValue() : totalProfit.intValue();
            setUpperBounds(maxValue);
        }
    }

    private void setWeekValues(ArrayList<StatisticsTracker> weeklyStats)
    {
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");

        LocalDate date = weeklyStats.get(0).getDay().toLocalDate();
        String formattedDate = simpleDateFormat.format(Date.valueOf(date));
        barGraph.setTitle("Week Of " + formattedDate + " Stats");
        xAxis.setLabel("Day Of Week");

        totalIncome = new BigDecimal("0.00");
        totalProfit = new BigDecimal("0.00");
        totalItemsSold = 0;
        totalSales = 0;

        int maxValue = 25;

        for(StatisticsTracker stats : weeklyStats)
        {
            maxValue = stats.getTotalIncome().compareTo(stats.getTotalProfit()) == 1 ? stats.getTotalIncome().intValue() : stats.getTotalProfit().intValue();

            totalItemsSold += stats.getNumOfItemsSold();
            totalSales += stats.getNumOfSales();

            totalIncome = totalIncome.add(stats.getTotalIncome());
            XYChart.Data<String, Number> incomeData = new XYChart.Data<>(date.getDayOfWeek().toString(), stats.getTotalIncome());
            incomeSeries.getData().add(incomeData);

            totalProfit = totalProfit.add(stats.getTotalProfit());
            XYChart.Data<String, Number> profitData = new XYChart.Data<>(date.getDayOfWeek().toString(), stats.getTotalProfit());
            profitSeries.getData().add(profitData);

            date = date.plusDays(1);
        }

        setUpperBounds(maxValue);
        barGraph.getData().add(incomeSeries);
        barGraph.getData().add(profitSeries);
    }

    private void setMonthValues(ArrayList<StatisticsTracker> monthlyStats)
    {
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");

        LocalDate date = monthlyStats.get(0).getDay().toLocalDate();

        String MONTH = date.getMonth().toString();
        String month = MONTH.substring(0, 1).toUpperCase() + MONTH.substring(1, MONTH.length()).toLowerCase();
        barGraph.setTitle("Month Of " + month + " "  + date.getYear() + " Income Stats");
        xAxis.setLabel("Day Of Month");

        totalIncome = new BigDecimal("0.00");
        totalProfit = new BigDecimal("0.00");
        totalItemsSold = 0;
        totalSales = 0;

        int maxValue = 25;

        for(StatisticsTracker stats : monthlyStats)
        {
            if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
            {
                maxValue = stats.getTotalIncome().compareTo(stats.getTotalProfit()) == 1 ? stats.getTotalIncome().intValue() : stats.getTotalProfit().intValue();

                totalItemsSold += stats.getNumOfItemsSold();
                totalSales += stats.getNumOfSales();

                totalIncome = totalIncome.add(stats.getTotalIncome());
                XYChart.Data<String, Number> incomeData = new XYChart.Data<>("" + date.getDayOfMonth(), stats.getTotalIncome());
                incomeSeries.getData().add(incomeData);

                totalProfit = totalProfit.add(stats.getTotalProfit());
                XYChart.Data<String, Number> profitData = new XYChart.Data<>("" + date.getDayOfMonth(), stats.getTotalProfit());
                profitSeries.getData().add(profitData);
            }

            date = date.plusDays(1);
        }

        setUpperBounds(maxValue);
        barGraph.setCategoryGap(10);
        barGraph.getData().add(incomeSeries);
        barGraph.getData().add(profitSeries);
    }

    private void setYearValues()
    {

    }

    private void setAllValues(StatisticsTracker stats)
    {
        xAxis.setLabel("All Time");
        barGraph.setTitle("All Time Income Stats");

        totalIncome = stats.getTotalIncome();
        totalProfit = stats.getTotalProfit();
        totalItemsSold = stats.getNumOfItemsSold();
        totalSales = stats.getNumOfSales();

        int maxValue = totalIncome.compareTo(totalProfit) == 1 ? totalIncome.intValue() : totalProfit.intValue();
        setUpperBounds(maxValue);

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        XYChart.Data<String, Number> incomeData = new XYChart.Data<>("All Time", stats.getTotalIncome());
        incomeSeries.getData().add(incomeData);
        barGraph.getData().add(incomeSeries);

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");
        XYChart.Data<String, Number> profitData = new XYChart.Data<>("All Time", stats.getTotalProfit());
        profitSeries.getData().add(profitData);
        barGraph.getData().add(profitSeries);
    }

    private void setUpperBounds(int maxValue)
    {
        //yAxis.setUpperBound(maxValue + 10);

        if(maxValue < 150 && maxValue > 125)
        {
            yAxis.setUpperBound(155);
        }
        else if(maxValue < 125 && maxValue > 100)
        {
            yAxis.setUpperBound(130);
        }
        else if(maxValue < 100 && maxValue > 75)
        {
            yAxis.setUpperBound(105);
        }
        else if(maxValue < 75 && maxValue > 50)
        {
            yAxis.setUpperBound(80);
        }
        else if(maxValue < 50 && maxValue > 25)
        {
            yAxis.setUpperBound(55);
        }
        else if(maxValue < 25 && maxValue > 15)
        {
            yAxis.setUpperBound(30);
        }
        else if(maxValue < 15)
        {
            yAxis.setUpperBound(20);
        }
        else
        {
            yAxis.setUpperBound(maxValue + 5);
        }
    }

    public BigDecimal getTotalIncome()
    {
        return totalIncome;
    }

    public BigDecimal getTotalProfit()
    {
        return totalProfit;
    }

    public int getTotalItemsSold()
    {
        return totalItemsSold;
    }

    public int getTotalSales()
    {
        return totalSales;
    }

    public BarChart getGraph()
    {
        return barGraph;
    }
}