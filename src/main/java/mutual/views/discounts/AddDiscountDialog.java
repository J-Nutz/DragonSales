package mutual.views.discounts;

/*
 * Created by Jonah on 2/7/2017.
 */

import database.data.ProductsHeld;
import database.tables.ProductsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mutual.types.Discount;
import mutual.types.Product;
import mutual.types.Repeat;
import worker.BigDecimalSpinnerValueFactory;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

public class AddDiscountDialog extends Dialog<Discount>
{
    private Product product;
    private GridPane container;

    private Label productLabel;
    private Label oldPriceLabel;

    private Label discountPriceLabel;
    private Spinner<BigDecimal> discountPriceSpinner;

    private HBox toggleButtonContainer;
    private Label daysOfDiscountLabel;
    private ToggleButton mondayBtn;
    private ToggleButton tuesdayBtn;
    private ToggleButton wednesdayBtn;
    private ToggleButton thursdayBtn;
    private ToggleButton fridayBtn;

    private ArrayList<ToggleButton> days;
    private HBox repeatContainer;
    private Label repeatLabel;
    private ComboBox<String> repeatComboBox;

    public AddDiscountDialog(Product product)
    {
        this.product = product;
        container = new GridPane();

        productLabel = new Label();
        oldPriceLabel = new Label();

        discountPriceLabel = new Label("Discount Price: $");
        discountPriceSpinner = new Spinner<>();

        daysOfDiscountLabel = new Label("Days Of Discount");
        mondayBtn = new ToggleButton("M");
        tuesdayBtn = new ToggleButton("T");
        wednesdayBtn = new ToggleButton("W");
        thursdayBtn = new ToggleButton("TH");
        fridayBtn = new ToggleButton("F");
        toggleButtonContainer = new HBox(10, daysOfDiscountLabel, mondayBtn, tuesdayBtn, wednesdayBtn, thursdayBtn, fridayBtn);

        days = new ArrayList<>();
        days.add(mondayBtn);
        days.add(tuesdayBtn);
        days.add(wednesdayBtn);
        days.add(thursdayBtn);
        days.add(fridayBtn);

        repeatLabel = new Label("Repeat:");
        repeatComboBox = new ComboBox<>();
        repeatContainer = new HBox(10, repeatLabel, repeatComboBox);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setResultConverter(param ->
        {
            if(param == ButtonType.CANCEL)
            {
                close();
                return null;
            }
            else
            {
                return getDiscount();
            }
        });

        container.setHgap(10);
        container.setVgap(10);

        productLabel.setText(product.getName());
        productLabel.setFont(new Font(16));

        oldPriceLabel.setText("Old Price: $" + product.getPrice().toString());

        discountPriceSpinner.getEditor().setAlignment(Pos.CENTER);
        discountPriceSpinner.setMaxWidth(70);
        discountPriceSpinner.setValueFactory(new BigDecimalSpinnerValueFactory(new BigDecimal(0.25)));
        discountPriceSpinner.getValueFactory().setValue(new BigDecimal(0));

        toggleButtonContainer.setAlignment(Pos.CENTER);

        repeatContainer.setAlignment(Pos.CENTER);

        ObservableList<String> repeatValues = FXCollections.observableArrayList();

        for(Repeat repeat : Repeat.values())
        {
            repeatValues.add(repeat.toString());
        }

        repeatComboBox.setItems(repeatValues);
        repeatComboBox.getSelectionModel().selectFirst();
        repeatComboBox.setMaxWidth(85);
    }

    private void addComponents()
    {
        container.add(productLabel, 0, 0);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator, 4);
        container.add(separator, 0, 1);

        container.add(oldPriceLabel, 0, 2);
        container.add(discountPriceLabel, 2, 2);
        container.add(discountPriceSpinner, 3, 2);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator1, 4);
        container.add(separator1, 0, 3);

        container.add(toggleButtonContainer, 0, 4);
        GridPane.setColumnSpan(toggleButtonContainer, 4);
        GridPane.setHalignment(toggleButtonContainer, HPos.CENTER);

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator2, 4);
        container.add(separator2, 0, 5);

        container.add(repeatContainer, 0, 6);
        GridPane.setColumnSpan(repeatContainer, 4);

        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator3, 4);
        container.add(separator3, 0, 7);

        getDialogPane().setContent(container);
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
    }

    public Discount getDiscount()
    {
        HashMap<DayOfWeek, Boolean> daysOfDiscount = new HashMap<>();

        if(mondayBtn.isSelected())
        {
            daysOfDiscount.put(DayOfWeek.MONDAY, true);
        }
        else
        {
            daysOfDiscount.put(DayOfWeek.MONDAY, false);
        }

        if(tuesdayBtn.isSelected())
        {
            daysOfDiscount.put(DayOfWeek.TUESDAY, true);
        }
        else
        {
            daysOfDiscount.put(DayOfWeek.TUESDAY, false);
        }

        if(wednesdayBtn.isSelected())
        {
            daysOfDiscount.put(DayOfWeek.WEDNESDAY, true);
        }
        else
        {
            daysOfDiscount.put(DayOfWeek.WEDNESDAY, false);
        }

        if(thursdayBtn.isSelected())
        {
            daysOfDiscount.put(DayOfWeek.THURSDAY, true);
        }
        else
        {
            daysOfDiscount.put(DayOfWeek.THURSDAY, false);
        }

        if(fridayBtn.isSelected())
        {
            daysOfDiscount.put(DayOfWeek.FRIDAY, true);
        }
        else
        {
            daysOfDiscount.put(DayOfWeek.FRIDAY, false);
        }

        Discount discount = new Discount();
        BigDecimal discountPrice = discountPriceSpinner.getValue();
        Repeat repeat = Repeat.valueOf(repeatComboBox.getSelectionModel().getSelectedItem());

        product.setSalePrice(discountPrice);
        ProductsTable.updateProduct(product.getName(), product);
        ProductsHeld.updateProducts();

        discount.setProduct(this.product);
        discount.setOldPrice(product.getPrice());
        discount.setDiscountPrice(discountPrice);
        discount.setDaysOfDiscount(daysOfDiscount);
        discount.setRepeat(repeat);

        return discount;
    }

    public void setSelectedValues(Discount discount)
    {
        discountPriceSpinner.getValueFactory().setValue(discount.getDiscountPrice());

        HashMap<DayOfWeek, Boolean> daysOfDiscount = discount.getDaysOfDiscount();
        mondayBtn.setSelected(daysOfDiscount.get(DayOfWeek.MONDAY));
        tuesdayBtn.setSelected(daysOfDiscount.get(DayOfWeek.TUESDAY));
        wednesdayBtn.setSelected(daysOfDiscount.get(DayOfWeek.WEDNESDAY));
        thursdayBtn.setSelected(daysOfDiscount.get(DayOfWeek.THURSDAY));
        fridayBtn.setSelected(daysOfDiscount.get(DayOfWeek.FRIDAY));

        repeatComboBox.getSelectionModel().select(discount.getRepeat().toString());
    }
}