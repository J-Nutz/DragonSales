package mutual.views.statistics;

/*
 * Created by Jonah on 3/19/2017.
 */

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mutual.types.Interval;
import mutual.types.StatisticSelection;

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

    private VBox genStatsCenterContainer;
    private GridPane genStatsContainer;
    private Label incomeLabel;
    private Label incomeAmountLabel;
    private Label profitLabel;
    private Label profitAmountLabel;

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
        incomeVsProfitGraph = new IncomeVsProfitGraph(Interval.DAILY);

        dayStats = incomeVsProfitGraph.getDayStats();
        genStatsFont = new Font(16);

        genStatsCenterContainer = new VBox();
        genStatsContainer = new GridPane();
        incomeLabel = new Label("Income: ");
        incomeAmountLabel = new Label("$" + dayStats.getTotalIncome());
        profitLabel = new Label("Profit: ");
        profitAmountLabel = new Label("$" + dayStats.getTotalProfit());

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

        genStatsCenterContainer.setAlignment(Pos.CENTER);
        genStatsCenterContainer.setPadding(new Insets(15));

        genStatsContainer.setAlignment(Pos.CENTER);

        incomeLabel.setFont(genStatsFont);
        incomeAmountLabel.setFont(genStatsFont);

        profitLabel.setFont(genStatsFont);
        profitAmountLabel.setFont(genStatsFont);
    }

    private void addComponents()
    {
        intervalContainer.getChildren().addAll(dayBtn, weekBtn, monthBtn, yearBtn, allBtn);
        setTop(intervalContainer);

        graphContainer.setCenter(incomeVsProfitGraph.getBarGraph());
        setCenter(graphContainer);

        genStatsContainer.add(incomeLabel, 0, 0);
        genStatsContainer.add(incomeAmountLabel, 1, 0);
        genStatsContainer.add(profitLabel, 0, 1);
        genStatsContainer.add(profitAmountLabel, 1, 1);
        genStatsCenterContainer.getChildren().add(new Separator(Orientation.HORIZONTAL));
        genStatsCenterContainer.getChildren().add(genStatsContainer);
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

                if((!button.equals(allBtn)))
                {
                    Optional<StatisticSelection> selection = new StatisticTypeDialog(getInterval(button)).showAndWait();

                    if(selection.isPresent())
                    {
                        incomeVsProfitGraph.setData(selection.get().getInterval(), selection.get().getStartDate().toLocalDate());
                    }
                }
            }
        });
    }

    private void setToggleFunctionality(ToggleButton button)
    {
        button.setOnAction(event ->
        {
            if(!event.getSource().equals(allBtn))
            {
                Optional<StatisticSelection> selection = new StatisticTypeDialog(getInterval(button)).showAndWait();

                if(selection.isPresent())
                {
                    incomeVsProfitGraph.setData(selection.get().getInterval(), selection.get().getStartDate().toLocalDate());
                }
            }
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
        else
        {
            interval = Interval.DAILY;
        }

        return interval;
    }
}