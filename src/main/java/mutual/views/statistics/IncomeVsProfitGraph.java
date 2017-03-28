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
import mutual.types.WeekStats;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
    }

    private void addComponents()
    {

    }

    private void setData(Interval interval)
    {
        if(interval.equals(Interval.DAILY))
        {
            StatisticsTracker dayStats = DailyStatsTable.getDayStats(Date.valueOf(LocalDate.now()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            String date = simpleDateFormat.format(dayStats.getDay());

            setDayValues(date, dayStats.getTotalIncome(), dayStats.getTotalProfit());

            xAxis.setLabel(date);
            barGraph.setMaxSize(300, 500);
        }
    }

    private void setDayValues(String day, Number income, Number profit)
    {
        XYChart.Series<String, Number> incomeData = new XYChart.Series<>();
        incomeData.setName("Income");
        incomeData.getData().add(new XYChart.Data<>(day, income));

        XYChart.Series<String, Number> profitData = new XYChart.Series<>();
        profitData.setName("Profit");
        profitData.getData().add(new XYChart.Data<>(day, profit));

        barGraph.getData().add(incomeData);
        barGraph.getData().add(profitData);

        //Doesn't change the color of the legend
        /*for(Node node : barGraph.lookupAll(".default-color0.chart-bar"))
        {
            node.setStyle("-fx-bar-fill: purple;");
        }

        for(Node node : barGraph.lookupAll(".default-color1.chart-bar"))
        {
            node.setStyle("-fx-bar-fill: green;");
        }*/
    }

    private void setWeekValues(String week, WeekStats stats)
    {

    }

    public BarChart getBarGraph()
    {
        return barGraph;
    }
}