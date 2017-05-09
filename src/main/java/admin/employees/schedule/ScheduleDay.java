package admin.employees.schedule;

/*
 * Created by Jonah on 5/9/2017.
 */

import database.tables.EmployeesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.time.LocalDate;

public class ScheduleDay extends VBox
{
    private ObservableList<String> employees;

    private Date date;
    private LocalDate localDate;
    private Label dayOfWeekLabel;

    private Label break1;
    private ComboBox<String> break1Employee1CB;
    private ComboBox<String> break1Employee2CB;

    private Label b1e1Label;
    private Label b1e2Label;

    private Label lunch;
    private ComboBox<String> lunchEmployee1CB;
    private ComboBox<String> lunchEmployee2CB;

    private Label le1Label;
    private Label le2Label;

    private Label break2;
    private ComboBox<String> break2Employee1CB;
    private ComboBox<String> break2Employee2CB;

    private Label b2e1Label;
    private Label b2e2Label;

    private Label stocker;
    private ComboBox<String> stockerEmployee1CB;
    private ComboBox<String> stockerEmployee2CB;

    private Label se1Label;
    private Label se2Label;

    private Label advertiser;
    private ComboBox<String> advertiserEmployee1CB;
    private ComboBox<String> advertiserEmployee2CB;

    private Label ae1Label;
    private Label ae2Label;

    private Button editAndSaveBtn;


    public ScheduleDay(Date date)
    {
        this.date = date;
        this.localDate = date.toLocalDate();
        employees = FXCollections.observableArrayList(EmployeesTable.getEmployeeNames());

        dayOfWeekLabel = new Label(localDate.getDayOfWeek().toString());

        break1 = new Label("1st Break");
        break1Employee1CB = new ComboBox<>(employees);
        break1Employee2CB = new ComboBox<>(employees);

        b1e1Label = new Label();
        b1e2Label = new Label();

        lunch = new Label("Lunch");
        lunchEmployee1CB = new ComboBox<>(employees);
        lunchEmployee2CB = new ComboBox<>(employees);

        le1Label = new Label();
        le2Label = new Label();

        break2 = new Label("2nd Break");
        break2Employee1CB = new ComboBox<>(employees);
        break2Employee2CB = new ComboBox<>(employees);

        b2e1Label = new Label();
        b2e2Label = new Label();

        stocker = new Label("Stocker");
        stockerEmployee1CB = new ComboBox<>(employees);
        stockerEmployee2CB = new ComboBox<>(employees);

        se1Label = new Label();
        se2Label = new Label();

        advertiser = new Label("Advertiser");
        advertiserEmployee1CB = new ComboBox<>(employees);
        advertiserEmployee2CB = new ComboBox<>(employees);

        ae1Label = new Label();
        ae2Label = new Label();

        editAndSaveBtn = new Button();

        initComponents();
        addComponents(false);
    }

    private void initComponents()
    {

    }

    private void addComponents(boolean showComboBoxes)
    {
        getChildren().clear();

        if(showComboBoxes)
        {
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(break1, break1Employee1CB, break1Employee2CB);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(lunch, lunchEmployee1CB, lunchEmployee2CB);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(break2, break2Employee1CB, break2Employee2CB);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(stocker, stockerEmployee1CB, stockerEmployee2CB);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(advertiser, advertiserEmployee1CB, advertiserEmployee2CB);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().add(editAndSaveBtn);
        }
        else
        {
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(break1, new Separator(Orientation.HORIZONTAL), b1e1Label, new Separator(Orientation.HORIZONTAL), b1e2Label);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(lunch, new Separator(Orientation.HORIZONTAL), le1Label, new Separator(Orientation.HORIZONTAL), le2Label);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(break2, new Separator(Orientation.HORIZONTAL), b2e1Label, new Separator(Orientation.HORIZONTAL), b2e2Label);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(stocker, new Separator(Orientation.HORIZONTAL), se1Label, new Separator(Orientation.HORIZONTAL), se2Label);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().addAll(advertiser, new Separator(Orientation.HORIZONTAL), ae1Label, new Separator(Orientation.HORIZONTAL), ae2Label);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().add(editAndSaveBtn);
        }
    }

}