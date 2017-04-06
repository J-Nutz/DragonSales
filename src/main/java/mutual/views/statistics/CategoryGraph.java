package mutual.views.statistics;

/*
 * Created by Jonah on 4/4/2017.
 */

import database.tables.AllTimeStatsTable;
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

public class CategoryGraph
{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String, Number> barGraph;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");

    public CategoryGraph()
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 25, 5);

        barGraph = new BarChart<>(xAxis, yAxis);

        initComponents();
    }

    private void initComponents()
    {
        xAxis.setLabel("Interval");
        yAxis.setLabel("Amount");

        barGraph.setTitle("Number Of Products Sold In Each Category");
        barGraph.setAnimated(false);
        barGraph.setCategoryGap(15);
    }

    public void setData(Interval interval, LocalDate startDate)
    {
        getGraph().getData().clear();

        if(interval.equals(Interval.DAILY))
        {
            setDailyValues(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
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

            setWeeklyValues(weeklyStats);
        }
        else if(interval.equals(Interval.MONTHLY))
        {

        }
        else if(interval.equals(Interval.YEARLY))
        {

        }
        else if(interval.equals(Interval.ALL))
        {
            setAllValues(AllTimeStatsTable.getAllTimeStats());
            //TODO: Two Different Types? One For Category One For Income? Two Different DB Calls?
        }
    }

    private void setDailyValues(StatisticsTracker stats)
    {
        String day = simpleDateFormat.format(stats.getDay());
        int largestNum = stats.getLargestNumInCategory();

        if(largestNum < 75 && largestNum > 50)
        {
            yAxis.setUpperBound(75);
        }
        else if(largestNum < 50 && largestNum > 25)
        {
            yAxis.setUpperBound(50);
        }
        else if(largestNum < 25)
        {
            yAxis.setUpperBound(25);
        }
        else
        {
            yAxis.setUpperBound(100);
        }

        xAxis.setLabel(day + " Category Stats");

        XYChart.Series<String, Number> categorySeries = new XYChart.Series<>();
        categorySeries.setName("Items Sold In Each Category");
        XYChart.Data<String, Number> foodData = new XYChart.Data<>("Food", stats.getFoodSold());
        XYChart.Data<String, Number> bakeryData = new XYChart.Data<>("Bakery", stats.getBakerySold());
        XYChart.Data<String, Number> candyData = new XYChart.Data<>("Candy", stats.getCandySold());
        XYChart.Data<String, Number> chipsData = new XYChart.Data<>("Chips", stats.getChipsSold());
        XYChart.Data<String, Number> drinksData = new XYChart.Data<>("Drinks", stats.getDrinksSold());
        XYChart.Data<String, Number> sodaData = new XYChart.Data<>("Sodas", stats.getSodaSold());
        XYChart.Data<String, Number> waterData = new XYChart.Data<>("Water", stats.getWaterSold());
        XYChart.Data<String, Number> juiceData = new XYChart.Data<>("Juice", stats.getJuiceSold());
        XYChart.Data<String, Number> coffeeData = new XYChart.Data<>("Coffee", stats.getCoffeeSold());
        XYChart.Data<String, Number> frozenData = new XYChart.Data<>("Frozen", stats.getFrozenSold());
        XYChart.Data<String, Number> miscData = new XYChart.Data<>("Misc", stats.getMiscSold());

        categorySeries.getData().add(foodData);
        categorySeries.getData().add(bakeryData);
        categorySeries.getData().add(candyData);
        categorySeries.getData().add(chipsData);
        categorySeries.getData().add(drinksData);
        categorySeries.getData().add(sodaData);
        categorySeries.getData().add(waterData);
        categorySeries.getData().add(juiceData);
        categorySeries.getData().add(coffeeData);
        categorySeries.getData().add(frozenData);
        categorySeries.getData().add(miscData);
        barGraph.getData().add(categorySeries);
    }

    private void setWeeklyValues(ArrayList<StatisticsTracker> weeklyStats)
    {

    }

    private void setAllValues(StatisticsTracker allStats)
    {
        xAxis.setLabel("All Time Category Stats");

        XYChart.Series<String, Number> categorySeries = new XYChart.Series<>();
        categorySeries.setName("Items Sold In Each Category");
        XYChart.Data<String, Number> foodData = new XYChart.Data<>("Food", allStats.getFoodSold());
        XYChart.Data<String, Number> bakeryData = new XYChart.Data<>("Bakery", allStats.getBakerySold());
        XYChart.Data<String, Number> candyData = new XYChart.Data<>("Candy", allStats.getCandySold());
        XYChart.Data<String, Number> chipsData = new XYChart.Data<>("Chips", allStats.getChipsSold());
        XYChart.Data<String, Number> drinksData = new XYChart.Data<>("Drinks", allStats.getDrinksSold());
        XYChart.Data<String, Number> sodaData = new XYChart.Data<>("Sodas", allStats.getSodaSold());
        XYChart.Data<String, Number> waterData = new XYChart.Data<>("Water", allStats.getWaterSold());
        XYChart.Data<String, Number> juiceData = new XYChart.Data<>("Juice", allStats.getJuiceSold());
        XYChart.Data<String, Number> coffeeData = new XYChart.Data<>("Coffee", allStats.getCoffeeSold());
        XYChart.Data<String, Number> frozenData = new XYChart.Data<>("Frozen", allStats.getFrozenSold());
        XYChart.Data<String, Number> miscData = new XYChart.Data<>("Misc", allStats.getMiscSold());

        categorySeries.getData().add(foodData);
        categorySeries.getData().add(bakeryData);
        categorySeries.getData().add(candyData);
        categorySeries.getData().add(chipsData);
        categorySeries.getData().add(drinksData);
        categorySeries.getData().add(sodaData);
        categorySeries.getData().add(waterData);
        categorySeries.getData().add(juiceData);
        categorySeries.getData().add(coffeeData);
        categorySeries.getData().add(frozenData);
        categorySeries.getData().add(miscData);
        barGraph.getData().add(categorySeries);
    }

    public BarChart<String, Number> getGraph()
    {
        return barGraph;
    }
}