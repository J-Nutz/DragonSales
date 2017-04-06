package mutual.views.statistics;

/*
 * Created by Jonah on 3/27/2017.
 */

import database.tables.AllTimeStatsTable;
import database.tables.DailyStatsTable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import mutual.types.Interval;

import java.math.BigDecimal;
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
    private BigDecimal totalIncome;
    private BigDecimal totalProfit;
    private int totalItemsSold;
    private int totalSales;

    public IncomeVsProfitGraph()
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 100, 5);

        //barGraph = new BarChart<>(xAxis, yAxis);
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
                    weeklyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                    startDate = startDate.plusDays(1);
                }
            }

            setWeekValues(weeklyStats);
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
        }
    }

    private void setDayValues(StatisticsTracker stats)
    {
        totalIncome = stats.getTotalIncome();
        totalProfit = stats.getTotalProfit();
        totalItemsSold = stats.getNumOfItemsSold();
        totalSales = stats.getNumOfSales();

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
        else if(incomeAsInt < 25)
        {
            yAxis.setUpperBound(25);
        }
        else
        {
            yAxis.setUpperBound(100);
        }

        xAxis.setLabel("Daily Stats");

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        XYChart.Data<String, Number> incomeData = new XYChart.Data<>(day, income);
        //displayLabelForData(incomeData);
        incomeSeries.getData().add(incomeData);
        barGraph.getData().add(incomeSeries);

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");
        XYChart.Data<String, Number> profitData = new XYChart.Data<>(day, profit);
        //displayLabelForData(profitData);
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
        String formattedDate = simpleDateFormat.format(Date.valueOf(date));
        xAxis.setLabel("Week Of " + formattedDate + " Stats");

        totalIncome = new BigDecimal("0.00");
        totalProfit = new BigDecimal("0.00");
        totalItemsSold = 0;
        totalSales = 0;

        for(StatisticsTracker stats : weeklyStats)
        {
            totalItemsSold += stats.getNumOfItemsSold();
            totalSales += stats.getNumOfSales();

            totalIncome = totalIncome.add(stats.getTotalIncome());
            XYChart.Data<String, Number> incomeData = new XYChart.Data<>(date.getDayOfWeek().toString(), stats.getTotalIncome());
            //displayLabelForData(incomeData);
            incomeSeries.getData().add(incomeData);

            totalProfit = totalProfit.add(stats.getTotalProfit());
            XYChart.Data<String, Number> profitData = new XYChart.Data<>(date.getDayOfWeek().toString(), stats.getTotalProfit());
            //displayLabelForData(profitData);
            profitSeries.getData().add(profitData);

            date = date.plusDays(1);
        }

        barGraph.getData().add(incomeSeries);
        barGraph.getData().add(profitSeries);
    }

    private void setMonthValues()
    {

    }

    private void setAllValues(StatisticsTracker stats)
    {
        xAxis.setLabel("All Time Stats");

        totalIncome = stats.getTotalIncome();
        totalProfit = stats.getTotalProfit();
        totalItemsSold = stats.getNumOfItemsSold();
        totalSales = stats.getNumOfSales();

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        XYChart.Data<String, Number> incomeData = new XYChart.Data<>("All Time", stats.getTotalIncome());
        //displayLabelForData(incomeData);
        incomeSeries.getData().add(incomeData);
        barGraph.getData().add(incomeSeries);

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");
        XYChart.Data<String, Number> profitData = new XYChart.Data<>("All Time", stats.getTotalProfit());
        //displayLabelForData(profitData);
        profitSeries.getData().add(profitData);
        barGraph.getData().add(profitSeries);
    }

    private void displayLabelForData(XYChart.Data<String, Number> data)
    {
        data.nodeProperty().addListener((ov, oldNode, newNode) ->
        {
            if(newNode != null)
            {
                final Node node = data.getNode();
                final Text dataText = new Text("$" + data.getYValue());

                node.parentProperty().addListener((ov2, oldParent, parent) ->
                {
                    Group oldParentGroup = (Group) oldParent;
                    oldParentGroup.getChildren().remove(dataText);

                    Group parentGroup = (Group) parent;
                    parentGroup.getChildren().add(dataText);
                });

                node.boundsInParentProperty().addListener((ov3, oldBounds, bounds) ->
                {
                    dataText.setLayoutX(Math.round(bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                    dataText.setLayoutY(Math.round(bounds.getMinY() - dataText.prefHeight(-1) * 0.5));
                });
            }
        });


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