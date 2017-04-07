package mutual.views.statistics;

/*
 * Created by Jonah on 3/19/2017.
 */

import database.tables.DailyStatsTable;
import database.tables.ProductsTable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mutual.types.Interval;
import mutual.types.StatisticSelection;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SaleStatsView extends BorderPane
{
    private StatisticsTracker dayStats;
    private Font genStatsFont;

    private HBox intervalContainer;
    private Font toggleFont;
    private ToggleButton dayBtn;
    private ToggleButton weekBtn;
    private ToggleButton monthBtn;
    private ToggleButton yearBtn;
    private ToggleButton allBtn;
    private ToggleGroup intervalGroup;

    private BorderPane graphContainer;
    private IncomeVsProfitGraph incomeVsProfitGraph;
    private CategoryGraph categoryGraph;

    private HBox genStatsCenterContainer;
    private GridPane leftGenStatsContainer;
    private Label incomeLabel;
    private Label incomeAmountLabel;
    private Label profitLabel;
    private Label profitAmountLabel;
    private Label itemsSoldLabel;
    private Label itemSoldAmountLabel;
    private Label salesLabel;
    private Label salesAmountLabel;

    private GridPane rightGenStatsContainer;
    private Label bestSellingLabel;
    private Label bestSellingValueLabel;
    private Label bestSellingSuggestionLabel;
    private Label worstSellingLabel;
    private Label worstSellingValueLabel;
    private Label worstSellingSuggestionLabel;

    public SaleStatsView()
    {
        intervalContainer = new HBox();
        toggleFont = new Font(16);
        dayBtn = new ToggleButton("Day");
        weekBtn = new ToggleButton("Week");
        monthBtn = new ToggleButton("Month");
        yearBtn = new ToggleButton("Year");
        allBtn = new ToggleButton("All");
        intervalGroup = new ToggleGroup();

        graphContainer = new BorderPane();
        incomeVsProfitGraph = new IncomeVsProfitGraph();

        dayStats = DailyStatsTable.getDayStats(Date.valueOf(LocalDate.now()));
        genStatsFont = new Font(16);

        genStatsCenterContainer = new HBox();
        leftGenStatsContainer = new GridPane();
        incomeLabel = new Label("Total Income: ");
        incomeAmountLabel = new Label("$" + dayStats.getTotalIncome());
        profitLabel = new Label("Total Profit: ");
        profitAmountLabel = new Label("$" + dayStats.getTotalProfit());
        itemsSoldLabel = new Label("Items Sold: ");
        itemSoldAmountLabel = new Label("" + dayStats.getNumOfItemsSold());
        salesLabel = new Label("Total Sales: ");
        salesAmountLabel = new Label("" + dayStats.getNumOfSales());

        rightGenStatsContainer = new GridPane();
        bestSellingLabel = new Label("Best Selling Product: ");
        bestSellingValueLabel = new Label();
        bestSellingSuggestionLabel = new Label("Suggestion: Increase Price");
        worstSellingLabel = new Label("Worst Selling Product: ");
        worstSellingValueLabel = new Label();
        worstSellingSuggestionLabel = new Label("Suggestion: Lower Price Or Add Discount");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        intervalContainer.setAlignment(Pos.CENTER);
        intervalContainer.setPadding(new Insets(10));

        dayBtn.setSelected(true);
        dayBtn.setToggleGroup(intervalGroup);
        dayBtn.setFont(toggleFont);
        setSelfUntoggleable(dayBtn);
        setToggleFunctionality(dayBtn);

        weekBtn.setToggleGroup(intervalGroup);
        weekBtn.setFont(toggleFont);
        setSelfUntoggleable(weekBtn);
        setToggleFunctionality(weekBtn);

        monthBtn.setToggleGroup(intervalGroup);
        monthBtn.setFont(toggleFont);
        setSelfUntoggleable(monthBtn);
        setToggleFunctionality(monthBtn);

        yearBtn.setToggleGroup(intervalGroup);
        yearBtn.setFont(toggleFont);
        setSelfUntoggleable(yearBtn);
        setToggleFunctionality(yearBtn);

        allBtn.setToggleGroup(intervalGroup);
        allBtn.setFont(toggleFont);
        setSelfUntoggleable(allBtn);
        setToggleFunctionality(allBtn);

        graphContainer.setPadding(new Insets(10));

        genStatsCenterContainer.setAlignment(Pos.CENTER);
        genStatsCenterContainer.setPadding(new Insets(15));
        genStatsCenterContainer.setSpacing(15);

        leftGenStatsContainer.setAlignment(Pos.CENTER);

        incomeLabel.setFont(genStatsFont);
        incomeAmountLabel.setFont(genStatsFont);

        profitLabel.setFont(genStatsFont);
        profitAmountLabel.setFont(genStatsFont);

        itemsSoldLabel.setFont(genStatsFont);
        itemSoldAmountLabel.setFont(genStatsFont);

        salesLabel.setFont(genStatsFont);
        salesAmountLabel.setFont(genStatsFont);

        bestSellingLabel.setFont(genStatsFont);
        bestSellingValueLabel.setFont(genStatsFont);
        bestSellingValueLabel.setText(getBestSellingProduct());

        worstSellingLabel.setFont(genStatsFont);
        worstSellingValueLabel.setFont(genStatsFont);
        worstSellingValueLabel.setText(getWorstSellingProduct());
    }

    private void addComponents()
    {
        intervalContainer.getChildren().addAll(dayBtn, weekBtn, monthBtn, yearBtn, allBtn);
        setTop(intervalContainer);

        graphContainer.setCenter(incomeVsProfitGraph.getGraph());
        setCenter(graphContainer);

        leftGenStatsContainer.add(incomeLabel, 0, 0);
        leftGenStatsContainer.add(incomeAmountLabel, 1, 0);
        leftGenStatsContainer.add(profitLabel, 0, 1);
        leftGenStatsContainer.add(profitAmountLabel, 1, 1);

        leftGenStatsContainer.add(itemsSoldLabel, 0, 2);
        leftGenStatsContainer.add(itemSoldAmountLabel, 1, 2);
        leftGenStatsContainer.add(salesLabel, 0, 3);
        leftGenStatsContainer.add(salesAmountLabel, 1, 3);

        rightGenStatsContainer.add(bestSellingLabel, 0, 0);
        rightGenStatsContainer.add(bestSellingValueLabel, 1, 0);
        GridPane.setColumnSpan(bestSellingSuggestionLabel, 2);
        GridPane.setHalignment(bestSellingSuggestionLabel, HPos.CENTER);
        rightGenStatsContainer.setVgap(5);
        rightGenStatsContainer.add(bestSellingSuggestionLabel, 0, 1);
        rightGenStatsContainer.add(worstSellingLabel, 0, 2);
        rightGenStatsContainer.add(worstSellingValueLabel, 1, 2);
        GridPane.setColumnSpan(worstSellingSuggestionLabel, 2);
        GridPane.setHalignment(worstSellingSuggestionLabel, HPos.CENTER);
        rightGenStatsContainer.add(worstSellingSuggestionLabel, 0, 3);

        genStatsCenterContainer.getChildren().add(new Separator(Orientation.HORIZONTAL));
        genStatsCenterContainer.getChildren().add(leftGenStatsContainer);
        genStatsCenterContainer.getChildren().add(new Separator(Orientation.VERTICAL));
        genStatsCenterContainer.getChildren().add(rightGenStatsContainer);
        genStatsCenterContainer.getChildren().add(new Separator(Orientation.HORIZONTAL));
        setBottom(genStatsCenterContainer);
    }

    private void setSelfUntoggleable(ToggleButton button)
    {
        button.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent ->
        {
            if(button.equals(intervalGroup.getSelectedToggle()))
            {
                mouseEvent.consume();

                /*if(!button.equals(allBtn))
                {*/
                    launchDialog(button);
                /*}
                else
                {
                    incomeVsProfitGraph.setData(Interval.ALL, LocalDate.now());
                    setCenter(incomeVsProfitGraph.getGraph());
                    setGeneralStats();
                }*/
            }
        });
    }

    private void setToggleFunctionality(ToggleButton button)
    {
        Toggle selected = intervalGroup.getSelectedToggle();

        button.setOnAction(event ->
        {
            /*if(!event.getSource().equals(allBtn))
            {*/
                if(!launchDialog(button))
                {
                    event.consume();
                    intervalGroup.selectToggle(selected);

                    if(selected.equals(dayBtn))
                    {
                        dayBtn.requestFocus();
                    }
                    else if(selected.equals(weekBtn))
                    {
                        weekBtn.requestFocus();
                    }
                    else if(selected.equals(monthBtn))
                    {
                        monthBtn.requestFocus();
                    }
                    else if(selected.equals(yearBtn))
                    {
                        yearBtn.requestFocus();
                    }
                    else if(selected.equals(allBtn))
                    {
                        allBtn.requestFocus();
                    }
                }
            //}
            /*else
            {
                incomeVsProfitGraph.setData(Interval.ALL, LocalDate.now());
                setCenter(incomeVsProfitGraph.getGraph());
                setGeneralStats();
            }*/
        });
    }

    private Interval getInterval(ToggleButton button)
    {
        Interval interval;

        if(button.equals(dayBtn))
        {
            interval = Interval.DAILY;
        }
        else if(button.equals(weekBtn))
        {
            interval = Interval.WEEKLY;
        }
        else if(button.equals(monthBtn))
        {
            interval = Interval.MONTHLY;
        }
        else if(button.equals(yearBtn))
        {
            interval = Interval.YEARLY;
        }
        else if(button.equals(allBtn))
        {
            interval = Interval.ALL;
        }
        else
        {
            interval = Interval.DAILY;
        }

        return interval;
    }

    private boolean launchDialog(ToggleButton button)
    {
        Optional<StatisticSelection> result = new StatisticTypeDialog(getInterval(button)).showAndWait();

        if(result.isPresent())
        {
            StatisticSelection selection = result.get();

            if(selection.getStatisticsType().equals("Sales"))
            {
                incomeVsProfitGraph.setData(selection.getInterval(), selection.getStartDate().toLocalDate());

                setCenter(incomeVsProfitGraph.getGraph());
                setGeneralStats();
            }
            else
            {
                categoryGraph = new CategoryGraph();
                categoryGraph.setData(selection.getInterval(),
                                      selection.getStartDate().toLocalDate());

                setCenter(categoryGraph.getGraph());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private String getBestSellingProduct()
    {
        HashMap<String, Integer> productsAndAmounts = ProductsTable.getProductsAndAmountsSold();
        ArrayList<String> productNames = ProductsTable.getProductNames();
        int maxValue = productsAndAmounts.get(productNames.get(0));
        String productName = productNames.get(0);

        for(int i = 1; i < productNames.size(); i++)
        {
            if(maxValue < productsAndAmounts.get(productNames.get(i)))
            {
                maxValue = productsAndAmounts.get(productNames.get(i));
                productName = productNames.get(i);
            }
        }

        return productName.trim() + " With " +  maxValue + " Sales";
    }

    private String getWorstSellingProduct()
    {
        HashMap<String, Integer> productsAndAmounts = ProductsTable.getProductsAndAmountsSold();
        ArrayList<String> productNames = ProductsTable.getProductNames();
        int minValue = productsAndAmounts.get(productNames.get(0));
        String productName = productNames.get(0);

        for(int i = 1; i < productNames.size(); i++)
        {
            if(minValue > productsAndAmounts.get(productNames.get(i)))
            {
                minValue = productsAndAmounts.get(productNames.get(i));
                productName = productNames.get(i);
            }
        }

        return productName.trim() + " With " +  minValue + " Sales";
    }

    private void setGeneralStats()
    {
        incomeAmountLabel.setText("$" + incomeVsProfitGraph.getTotalIncome().toString());
        profitAmountLabel.setText("$" + incomeVsProfitGraph.getTotalProfit().toString());

        itemSoldAmountLabel.setText("" + incomeVsProfitGraph.getTotalItemsSold());
        salesAmountLabel.setText("" + incomeVsProfitGraph.getTotalSales());
    }
}