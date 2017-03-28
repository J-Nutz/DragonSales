package mutual.views.statistics;

/*
 * Created by Jonah on 3/19/2017.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mutual.types.Interval;

public class SaleStatsView extends BorderPane
{
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

    private GridPane genStatsContainer;


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

        genStatsContainer = new GridPane();

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

        weekBtn.setToggleGroup(intervalGroup);
        weekBtn.setFont(toggleFont);
        setSelfUntoggleable(weekBtn);

        monthBtn.setToggleGroup(intervalGroup);
        monthBtn.setFont(toggleFont);
        setSelfUntoggleable(monthBtn);

        yearBtn.setToggleGroup(intervalGroup);
        yearBtn.setFont(toggleFont);
        setSelfUntoggleable(yearBtn);

        allBtn.setToggleGroup(intervalGroup);
        allBtn.setFont(toggleFont);
        setSelfUntoggleable(allBtn);
    }

    private void addComponents()
    {
        intervalContainer.getChildren().addAll(dayBtn, weekBtn, monthBtn, yearBtn, allBtn);
        setTop(intervalContainer);

        graphContainer.setCenter(incomeVsProfitGraph.getBarGraph());
        setCenter(graphContainer);

        setBottom(genStatsContainer);
    }

    private void setSelfUntoggleable(ToggleButton button)
    {
        button.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent ->
        {
            if(button.equals(intervalGroup.getSelectedToggle()))
            {
                mouseEvent.consume();
            }
        });
    }
}