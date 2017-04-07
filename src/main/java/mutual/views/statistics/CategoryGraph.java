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
import java.util.Calendar;

public class CategoryGraph
{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarGraph<String, Number> barGraph;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");

    public CategoryGraph()
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 25, 5);
        barGraph = new BarGraph<>(xAxis, yAxis);

        initComponents();
    }

    private void initComponents()
    {
        xAxis.setLabel("Category");
        yAxis.setLabel("Amount");

        barGraph.setTitle("Number Of Products Sold In Each Category");
        barGraph.setAnimated(false);
        barGraph.setCategoryGap(15);
        barGraph.areValuesCurrency(false);
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
            ArrayList<StatisticsTracker> monthlyStats = new ArrayList<>();

            if(startDate.getDayOfMonth() == 1)
            {
                int numOfDays = (Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) + 1);

                for(int i = 1; i < numOfDays; i++)
                {
                    monthlyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                    startDate = startDate.plusDays(1);
                }
            }

            setMonthValues(monthlyStats);
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

        ArrayList<StatisticsTracker> dailyStats = new ArrayList<>();
        dailyStats.add(stats);

        barGraph.setTitle(day + " Category Stats");
        barGraph.getData().add(calculateCategoryStats(dailyStats));
    }

    private void setWeeklyValues(ArrayList<StatisticsTracker> weeklyStats)
    {
        LocalDate date = weeklyStats.get(0).getDay().toLocalDate();
        String formattedDate = simpleDateFormat.format(Date.valueOf(date));

        barGraph.setTitle("Week Of " + formattedDate + " Stats");
        barGraph.getData().add(calculateCategoryStats(weeklyStats));
    }

    private void setMonthValues(ArrayList<StatisticsTracker> monthlyStats)
    {
        LocalDate date = monthlyStats.get(0).getDay().toLocalDate();
        String MONTH = date.getMonth().toString();
        String month = MONTH.substring(0, 1).toUpperCase() + MONTH.substring(1, MONTH.length()).toLowerCase();

        barGraph.setTitle("Month Of " + month + " "  + date.getYear() + " Stats");
        barGraph.getData().add(calculateCategoryStats(monthlyStats));
    }

    private void setAllValues(StatisticsTracker allStats)
    {
        ArrayList<StatisticsTracker> allTimeStats = new ArrayList<>();
        allTimeStats.add(allStats);

        barGraph.setTitle("All Time Category Stats");
        barGraph.getData().add(calculateCategoryStats(allTimeStats));
    }

    private XYChart.Series<String, Number> calculateCategoryStats(ArrayList<StatisticsTracker> stats)
    {
        int foodSold = 0;
        int bakerySold = 0;
        int candySold = 0;
        int chipsSold = 0;
        int drinksSold = 0;
        int sodaSold = 0;
        int waterSold = 0;
        int juiceSold = 0;
        int coffeeSold = 0;
        int frozenSold = 0;
        int miscSold = 0;

        for(StatisticsTracker stat : stats)
        {
            foodSold += stat.getFoodSold();
            bakerySold += stat.getBakerySold();
            candySold += stat.getCandySold();
            chipsSold += stat.getChipsSold();
            drinksSold += stat.getDrinksSold();
            sodaSold += stat.getSodaSold();
            waterSold += stat.getWaterSold();
            juiceSold += stat.getJuiceSold();
            coffeeSold += stat.getCoffeeSold();
            frozenSold += stat.getFrozenSold();
            miscSold += stat.getMiscSold();
        }

        XYChart.Series<String, Number> categorySeries = new XYChart.Series<>();
        categorySeries.setName("Items Sold In Each Category");
        XYChart.Data<String, Number> foodData = new XYChart.Data<>("Food", foodSold);
        XYChart.Data<String, Number> bakeryData = new XYChart.Data<>("Bakery", bakerySold);
        XYChart.Data<String, Number> candyData = new XYChart.Data<>("Candy", candySold);
        XYChart.Data<String, Number> chipsData = new XYChart.Data<>("Chips", chipsSold);
        XYChart.Data<String, Number> drinksData = new XYChart.Data<>("Drinks", drinksSold);
        XYChart.Data<String, Number> sodaData = new XYChart.Data<>("Sodas", sodaSold);
        XYChart.Data<String, Number> waterData = new XYChart.Data<>("Water", waterSold);
        XYChart.Data<String, Number> juiceData = new XYChart.Data<>("Juice", juiceSold);
        XYChart.Data<String, Number> coffeeData = new XYChart.Data<>("Coffee", coffeeSold);
        XYChart.Data<String, Number> frozenData = new XYChart.Data<>("Frozen", frozenSold);
        XYChart.Data<String, Number> miscData = new XYChart.Data<>("Misc", miscSold);

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

        //TODO: Calculate Max Value
        //setUpperBounds();

        return categorySeries;
    }

    private void setUpperBounds(int maxValue)
    {
        if(maxValue < 150 && maxValue > 125)
        {
            yAxis.setUpperBound(150);
        }
        else if(maxValue < 125 && maxValue > 100)
        {
            yAxis.setUpperBound(125);
        }
        else if(maxValue < 100 && maxValue > 75)
        {
            yAxis.setUpperBound(100);
        }
        else if(maxValue < 75 && maxValue > 50)
        {
            yAxis.setUpperBound(75);
        }
        else if(maxValue < 50 && maxValue > 25)
        {
            yAxis.setUpperBound(50);
        }
        else if(maxValue < 25 && maxValue > 15)
        {
            yAxis.setUpperBound(25);
        }
        else if(maxValue < 15)
        {
            yAxis.setUpperBound(15);
        }
        else
        {
            yAxis.setUpperBound(maxValue + 15);
        }
    }

    public BarChart<String, Number> getGraph()
    {
        return barGraph;
    }
}