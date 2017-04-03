package admin.employees;

/*
 * Created by Jonah on 1/9/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.DayOfWeek;
import java.util.ArrayList;

import static worker.ScheduleSaver.*;

public class ScheduleShiftView extends VBox
{
    private DayOfWeek dayOfWeek;

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

    public ScheduleShiftView(ArrayList<String> employeesTemp, DayOfWeek dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;

        ObservableList<String> employees = FXCollections.observableArrayList(employeesTemp);

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
        loadSchedule();
    }

    private void initComponents()
    {
        setAlignment(Pos.CENTER);
        setSpacing(5);

        break1.setFont(new Font(16));

        lunch.setFont(new Font(16));

        break2.setFont(new Font(16));

        stocker.setFont(new Font(16));

        advertiser.setFont(new Font(16));

        editAndSaveBtn.setOnAction(event ->
        {
            if(editAndSaveBtn.getText().equals("Save"))
            {
                saveSchedule(dayOfWeek, getComboBoxes());
                addComponents(false);
                loadUneditableSchedule(dayOfWeek, getLabels());

                editAndSaveBtn.setText("Edit");
            }
            else
            {
                addComponents(true);
                loadEditableSchedule(dayOfWeek, getComboBoxes());

                editAndSaveBtn.setText("Save");
            }
        });
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

    private void loadSchedule()
    {
        if(loadUneditableSchedule(dayOfWeek, getLabels()))
        {
            addComponents(false);
            editAndSaveBtn.setText("Edit");
        }
        else
        {
            addComponents(true);

            for(ComboBox<String> comboBox : getComboBoxes())
            {
                comboBox.getSelectionModel().selectLast();
            }

            editAndSaveBtn.setText("Save");
        }
    }

    private ArrayList<ComboBox<String>> getComboBoxes()
    {
        ArrayList<ComboBox<String>> comboBoxes = new ArrayList<>();

        getChildren()
                .stream()
                .filter(node -> node instanceof ComboBox)
                .forEach(node ->
                {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    comboBoxes.add(comboBox);
                });

        return comboBoxes;
    }

    private ArrayList<Label> getLabels()
    {
        ArrayList<Label> labels = new ArrayList<>();

        labels.add(b1e1Label);
        labels.add(b1e2Label);

        labels.add(le1Label);
        labels.add(le2Label);

        labels.add(b2e1Label);
        labels.add(b2e2Label);

        labels.add(se1Label);
        labels.add(se2Label);

        labels.add(ae1Label);
        labels.add(ae2Label);

        return labels;
    }
}