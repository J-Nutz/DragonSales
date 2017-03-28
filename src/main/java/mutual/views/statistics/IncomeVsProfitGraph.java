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

public class IncomeVsProfitGraph
{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private BarChart<String, Number> barGraph;

    public IncomeVsProfitGraph(Interval interval)
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 100, 5);

        barGraph = new BarChart<>(xAxis, yAxis);

        initComponents();
        setData(interval);
        addComponents();
    }

    private void initComponents()
    {
        xAxis.setLabel("Interval");
        yAxis.setLabel("Amount In $");

        barGraph.setTitle("Income VS Profit");
        //barGraph.setMaxSize(500, 300);
        //barGraph.setBarGap(20);
        barGraph.setCategoryGap(1);
    }

    private void addComponents()
    {

    }

    private void setData(Interval interval)
    {
        if(interval.equals(Interval.DAILY))
        {
            StatisticsTracker dayStats = DailyStatsTable.getDayStats();
            setDayValues(dayStats.getDay(), dayStats.getTotalIncome(), dayStats.getTotalProfit());
            barGraph.setMaxSize(300, 500);
        }
    }

    public void setDayValues(Date day, Number income, Number profit)
    {
        XYChart.Series<String, Number> incomeData = new XYChart.Series<>();
        incomeData.setName("Income");
        incomeData.getData().add(new XYChart.Data<>(day.toString() + " Income", income));

        XYChart.Series<String, Number> profitData = new XYChart.Series<>();
        profitData.setName("Profit");
        profitData.getData().add(new XYChart.Data<>(day.toString() + "Profit", profit));

        barGraph.getData().add(incomeData);
        barGraph.getData().add(profitData);
    }

    public BarChart getBarGraph()
    {
        return barGraph;
    }
}