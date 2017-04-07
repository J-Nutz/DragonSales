package mutual.views.statistics;

/*
 * Created by Jonah on 3/29/2017.
 */

import database.tables.DailyStatsTable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import mutual.types.Interval;
import mutual.types.StatisticSelection;
import worker.DailyCellFactory;
import worker.MonthlyCellFactory;
import worker.WeeklyCellFactory;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class StatisticTypeDialog extends Dialog<StatisticSelection>
{
    private Interval interval;
    private HBox container;

    private GridPane leftContainer;
    private Label chooseStartLabel;
    private DatePicker startDatePicker;
    private DatePicker startWeekPicker;
    private DatePicker startMonthPicker;
    private ComboBox<Integer> yearComboBox;

    private GridPane rightContainer;
    private Label statTypeLabel;
    private ToggleButton productsBtn;
    private ToggleButton salesBtn;
    private ToggleGroup toggleGroup;

    private ButtonType selectBtn;

    public StatisticTypeDialog(Interval interval)
    {
        this.interval = interval;

        container = new HBox();
        leftContainer = new GridPane();
        chooseStartLabel = new Label();

        rightContainer = new GridPane();
        statTypeLabel = new Label("Choose Type Of Stats");
        productsBtn = new ToggleButton("Products");
        salesBtn = new ToggleButton("Sales");
        toggleGroup = new ToggleGroup();

        selectBtn = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);

        initComponents();
        //addRightComponents();
    }

    private void initComponents()
    {
        setResultConverter(selectedButton ->
        {
            if(selectedButton.getButtonData() == ButtonBar.ButtonData.OK_DONE)
            {
                String statTypeToGet = toggleGroup.getSelectedToggle().equals(productsBtn) ? "Products" : "Sales";

                if(startDatePicker != null)
                {
                    return new StatisticSelection(interval,
                                                  Date.valueOf(startDatePicker.getValue()),
                                                  statTypeToGet);
                }
                else if(startWeekPicker != null)
                {
                    return new StatisticSelection(interval,
                                                  Date.valueOf(startWeekPicker.getValue()),
                                                  statTypeToGet);
                }
                else if(startMonthPicker != null)
                {
                    return new StatisticSelection(interval,
                                                  Date.valueOf(startMonthPicker.getValue()),
                                                  statTypeToGet);
                }
                else
                {
                    return new StatisticSelection(Interval.ALL,
                                                  Date.valueOf(LocalDate.now()),
                                                  statTypeToGet);
                }
            }
            else
            {
                close();
                return null;
            }
        });

        if(interval.equals(Interval.DAILY))
        {
            setTitle("Daily Selector");
            chooseStartLabel.setText("Select Day");
            startDatePicker = new DatePicker(LocalDate.now());
            startDatePicker.setDayCellFactory(new DailyCellFactory());
            startDatePicker.setMaxWidth(110);

            addLeftComponents(startDatePicker);
            addRightComponents();
            addToDialog();
        }
        else if(interval.equals(Interval.WEEKLY))
        {
            setTitle("Weekly Selector");
            LocalDate dateToBeSelected = DailyStatsTable.getDateOfFirstStat().toLocalDate();
            if(dateToBeSelected.getDayOfWeek() != DayOfWeek.MONDAY)
            {
                while(dateToBeSelected.getDayOfWeek() != DayOfWeek.MONDAY)
                {
                    dateToBeSelected = dateToBeSelected.minusDays(1);
                }
            }

            chooseStartLabel.setText("Select Week");
            startWeekPicker = new DatePicker(dateToBeSelected);
            startWeekPicker.setDayCellFactory(new WeeklyCellFactory());
            startWeekPicker.setMaxWidth(110);

            addLeftComponents(startWeekPicker);
            addRightComponents();
            addToDialog();
        }
        else if(interval.equals(Interval.MONTHLY))
        {
            setTitle("Monthly Selector");
            LocalDate dateToBeSelected = DailyStatsTable.getDateOfFirstStat().toLocalDate();
            if(dateToBeSelected.getDayOfMonth() != 1)
            {
                while(dateToBeSelected.getDayOfMonth() != 1)
                {
                    dateToBeSelected = dateToBeSelected.minusDays(1);
                }
            }

            chooseStartLabel.setText("Select Month");
            startMonthPicker = new DatePicker(dateToBeSelected);
            startMonthPicker.setDayCellFactory(new MonthlyCellFactory());
            startMonthPicker.setMaxWidth(110);

            addLeftComponents(startMonthPicker);
            addRightComponents();
            addToDialog();
        }
        else if(interval.equals(Interval.YEARLY))
        {
            setTitle("Year Selector");
            chooseStartLabel.setText("Select Year");
            yearComboBox = new ComboBox<>();

            addLeftComponents(yearComboBox);
            addRightComponents();
            addToDialog();
        }
        else if(interval.equals(Interval.ALL))
        {
            container.getChildren().clear();
            addRightComponents();
            addToDialog();
        }

        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setPadding(new Insets(5));
        leftContainer.setVgap(5);
        leftContainer.setHgap(5);

        rightContainer.setAlignment(Pos.CENTER);
        rightContainer.setPadding(new Insets(5));
        rightContainer.setVgap(5);
        rightContainer.setHgap(5);

        productsBtn.setToggleGroup(toggleGroup);
        productsBtn.setMinWidth(75);
        setSelfUntoggleable(productsBtn);
        salesBtn.setToggleGroup(toggleGroup);
        salesBtn.setSelected(true);
        salesBtn.setMinWidth(75);
        setSelfUntoggleable(salesBtn);
    }

    private void addLeftComponents(Node nodeToAdd)
    {
        GridPane.setHalignment(chooseStartLabel, HPos.CENTER);
        leftContainer.add(chooseStartLabel, 0, 0);
        leftContainer.add(nodeToAdd, 0, 1);
        Button invisibleButton = new Button();
        invisibleButton.setOpacity(0);
        leftContainer.add(invisibleButton, 0, 2);

        container.getChildren().add(leftContainer);
    }

    private void addRightComponents()
    {
        rightContainer.add(statTypeLabel, 0, 0);
        GridPane.setHalignment(productsBtn, HPos.CENTER);
        rightContainer.add(productsBtn, 0, 1);
        GridPane.setHalignment(salesBtn, HPos.CENTER);
        rightContainer.add(salesBtn, 0, 2);

        container.getChildren().add(rightContainer);
    }

    private void addToDialog()
    {
        getDialogPane().setContent(container);
        getDialogPane().getButtonTypes().add(0, selectBtn);
        getDialogPane().getButtonTypes().add(1, ButtonType.CANCEL);
    }

    private void setSelfUntoggleable(ToggleButton button)
    {
        button.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent ->
        {
            if(button.equals(toggleGroup.getSelectedToggle()))
            {
                mouseEvent.consume();
            }
        });
    }
}