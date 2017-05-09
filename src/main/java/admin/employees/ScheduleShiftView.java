package admin.employees;

/*
 * Created by Jonah on 1/9/2017.
 */

import database.tables.ScheduleTable;
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
import javafx.scene.text.FontWeight;

import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ScheduleShiftView extends VBox
{
    private Date date;

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

    public ScheduleShiftView(ArrayList<String> employeesTemp, Date date)
    {
        this.date = date;

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

        Font font = Font.font("Ubuntu", FontWeight.BOLD, 16);

        break1.setFont(font);

        lunch.setFont(font);

        break2.setFont(font);

        stocker.setFont(font);

        advertiser.setFont(font);

        editAndSaveBtn.setOnAction(event ->
        {
            if(editAndSaveBtn.getText().equals("Save"))
            {
                saveSchedule(date, getComboBoxes());
                addComponents(false);
                loadUneditableSchedule(date, getLabels());

                editAndSaveBtn.setText("Edit");
            }
            else
            {
                addComponents(true);
                loadEditableSchedule(date, getComboBoxes());

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
        if(loadUneditableSchedule(date, getLabels()))
        {
            addComponents(false);
            editAndSaveBtn.setText("Edit");
        }
        else
        {
            addComponents(true);

            for(ComboBox<? extends String> comboBox : getComboBoxes())
            {
                comboBox.getSelectionModel().selectFirst();
            }

            editAndSaveBtn.setText("Save");
        }
    }

    @SuppressWarnings("unchecked") //Stupid Type Erasure
    private ArrayList<ComboBox<String>> getComboBoxes()
    {
        return getChildren().stream()
                            .filter(node -> node instanceof ComboBox)
                            .map(node -> (ComboBox<String>) node)
                            .collect(Collectors.toCollection(ArrayList::new));
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

    private boolean saveSchedule(Date date, ArrayList<ComboBox<String>> comboBoxes)
    {
        ArrayList<String> schedule2 = comboBoxes.stream()
                                                .map(comboBox -> comboBox.getSelectionModel()
                                                                 .getSelectedItem())
                                                .collect(Collectors.toCollection(ArrayList::new));

        return ScheduleTable.setScheduleFor(date, schedule2);
    }

    private boolean loadEditableSchedule(Date date, ArrayList<ComboBox<String>> comboBoxes)
    {
        ArrayList<String> schedule = new ArrayList<>(10);
        schedule.addAll(ScheduleTable.getScheduleFor(date));

        if(schedule.size() != 10)
        {
            while(schedule.size() < 10)
            {
                schedule.add("Empty");
            }
        }

        for(int i = 0; i < schedule.size(); i++)
        {
            ComboBox<String> comboBox = comboBoxes.get(i);
            comboBox.getSelectionModel().select(schedule.get(i));
        }

        return true;
    }

    private boolean loadUneditableSchedule(Date date, ArrayList<Label> labels)
    {
        ArrayList<String> schedule = ScheduleTable.getScheduleFor(date);

        if(schedule.size() == 1 && schedule.contains("Empty"))
        {
            for(Label label : labels)
            {
                label.setText("Empty");
            }

            return true;
        }
        else if(schedule.size() > 1)
        {
            for(int i = 0; i < labels.size(); i++)
            {
                Label label = labels.get(i);
                label.setText(schedule.get(i));
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}