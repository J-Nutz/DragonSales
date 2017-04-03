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
    private StatisticsTracker dayStats;
    //private StatisticsTracker[] weeklyStats;
    private ArrayList<StatisticsTracker> weeklyStats;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private BarChart<String, Number> barGraph;

    public IncomeVsProfitGraph(Interval interval)
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 100, 5);

        barGraph = new BarChart<>(xAxis, yAxis);

        initComponents();
        setData(interval, LocalDate.now());
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

    public void setData(Interval interval, LocalDate startDate)
    {
        getBarGraph().getData().clear();

        if(interval.equals(Interval.DAILY))
        {
            dayStats = DailyStatsTable.getDayStats(Date.valueOf(startDate));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            String date = simpleDateFormat.format(dayStats.getDay());

            setDayValues(date, dayStats.getTotalIncome(), dayStats.getTotalProfit());

            xAxis.setLabel("Daily Stats");
            barGraph.setMaxSize(350, 500);
        }
        else if(interval.equals(Interval.WEEKLY))
        {
            weeklyStats = new ArrayList<>(5);

            while(startDate.getDayOfWeek() != DayOfWeek.SATURDAY)
            {
                weeklyStats.add(DailyStatsTable.getDayStats(Date.valueOf(startDate)));
                startDate = startDate.plusDays(1);
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            String date = simpleDateFormat.format(dayStats.getDay());

            setWeekValues(weeklyStats);

            xAxis.setLabel("Daily Stats");
            barGraph.setMaxSize(350, 500);
        }
    }

    private void setDayValues(String day, Number income, Number profit)
    {
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        XYChart.Data<String, Number> incomeData = new XYChart.Data<>(day, income);
        incomeSeries.getData().add(incomeData);
        barGraph.getData().add(incomeSeries);
        //displayDataLabel(incomeData);

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");
        XYChart.Data<String, Number> profitData = new XYChart.Data<>(day, profit);
        profitSeries.getData().add(profitData);
        barGraph.getData().add(profitSeries);
        //displayDataLabel(profitData);

        //TODO: Labels on each bar - See https://gist.github.com/jewelsea/5094893

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

    private void setWeekValues(ArrayList<StatisticsTracker> weeklyStats)
    {
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");

        LocalDate date = weeklyStats.get(0).getDay().toLocalDate();

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

    /*private void displayDataLabel(XYChart.Data<String, Number> data)
    {
        System.out.println("Adding Data Label...");

        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue().toString());

        node.parentProperty().addListener((ov, oldParent, parent) ->
        {
            Group parentGroup = (Group) parent;
            System.out.println("Data Label Added: " + parentGroup.getChildren().add(dataText));
        });

        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) ->
        {
            dataText.setLayoutX(Math.round(bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2));
            dataText.setLayoutY(Math.round(bounds.getMinY() - dataText.prefHeight(-1) * 0.5));
        });
    }*/

    public StatisticsTracker getDayStats()
    {
        return dayStats;
    }
}