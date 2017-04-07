package mutual.views.statistics;

/*
 * Created by Jonah on 3/29/2017.
 */

import database.tables.DailyStatsTable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    private VBox leftContainer;
    private Label chooseStartLabel;
    private DatePicker startDatePicker;
    private DatePicker startWeekPicker;
    private DatePicker startMonthPicker;
    private ComboBox<Integer> yearComboBox;

    private VBox rightContainer;
    private Label statTypeLabel;
    private ToggleButton productsBtn;
    private ToggleButton salesBtn;
    private ToggleGroup toggleGroup;

    private ButtonType selectBtn;

    public StatisticTypeDialog(Interval interval)
    {
        this.interval = interval;

        container = new HBox();
        leftContainer = new VBox();
        chooseStartLabel = new Label();

        rightContainer = new VBox();
        statTypeLabel = new Label("Choose Type Of Stats");
        productsBtn = new ToggleButton("Products");
        salesBtn = new ToggleButton("Sales");
        toggleGroup = new ToggleGroup();

        selectBtn = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);

        initComponents();
        addComponents();
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
                    return null;
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
            chooseStartLabel.setText("Select Day");
            startDatePicker = new DatePicker(LocalDate.now());
            startDatePicker.setDayCellFactory(new DailyCellFactory());
            startDatePicker.setMaxWidth(110);

            leftContainer.getChildren().addAll(chooseStartLabel, startDatePicker);
        }
        else if(interval.equals(Interval.WEEKLY))
        {
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

            leftContainer.getChildren().addAll(chooseStartLabel, startWeekPicker);
        }
        else if(interval.equals(Interval.MONTHLY))
        {
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

            leftContainer.getChildren().addAll(chooseStartLabel, startMonthPicker);
        }
        else if(interval.equals(Interval.YEARLY))
        {
            chooseStartLabel.setText("Select Year");
            yearComboBox = new ComboBox<>();

            leftContainer.getChildren().addAll(chooseStartLabel, yearComboBox);
        }

        container.setAlignment(Pos.CENTER);
        container.setSpacing(5);
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setSpacing(5);
        rightContainer.setAlignment(Pos.CENTER);
        rightContainer.setSpacing(5);

        productsBtn.setToggleGroup(toggleGroup);
        setSelfUntoggleable(productsBtn);
        salesBtn.setToggleGroup(toggleGroup);
        salesBtn.setSelected(true);
        setSelfUntoggleable(salesBtn);
    }

    private void addComponents()
    {
        rightContainer.getChildren().addAll(statTypeLabel, productsBtn, salesBtn);

        container.getChildren().addAll(leftContainer, rightContainer);

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