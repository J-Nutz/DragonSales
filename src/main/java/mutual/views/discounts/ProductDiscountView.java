package mutual.views.discounts;

/*
 * Created by Jonah on 2/7/2017.
 */

import database.tables.DiscountsTable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.types.Discount;
import mutual.types.ToggleButton2;
import mutual.views.FullAccess;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Optional;

import static admin.home.ViewContainer.switchView;

public class ProductDiscountView extends GridPane
{
    private Discount discount;

    private Label productLabel;
    private Label oldPriceLabel;
    private Label discountPriceLabel;

    private HBox toggleButtonContainer;
    private Label daysOfDiscountLabel;
    private ToggleButton2 mondayBtn;
    private ToggleButton2 tuesdayBtn;
    private ToggleButton2 wednesdayBtn;
    private ToggleButton2 thursdayBtn;
    private ToggleButton2 fridayBtn;

    private Label repeatLabel;

    private Button editButton;
    private Button deleteButton;

    private HBox buttonContainer;

    private final Font font = new Font(16);

    public ProductDiscountView(Discount discount)
    {
        this.discount = discount;

        productLabel = new Label(discount.getProductName());
        oldPriceLabel = new Label("Old Price: $" + discount.getOldPrice().toString());
        discountPriceLabel = new Label("Discount Price: $" + discount.getDiscountPrice().toString());

        daysOfDiscountLabel = new Label("Days Of Discount:");
        mondayBtn = new ToggleButton2("M");
        tuesdayBtn = new ToggleButton2("T");
        wednesdayBtn = new ToggleButton2("W");
        thursdayBtn = new ToggleButton2("TH");
        fridayBtn = new ToggleButton2("F");
        toggleButtonContainer = new HBox(10, daysOfDiscountLabel, mondayBtn, tuesdayBtn, wednesdayBtn, thursdayBtn, fridayBtn);

        repeatLabel = new Label("Repeat: " + discount.getRepeat().toString());

        buttonContainer = new HBox();
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setDaysOfSaleButtons(discount.getDaysOfDiscount());

        productLabel.setFont(font);
        oldPriceLabel.setFont(font);
        discountPriceLabel.setFont(font);
        daysOfDiscountLabel.setFont(font);

        toggleButtonContainer.setAlignment(Pos.CENTER);
        mondayBtn.setToggleable(false);
        mondayBtn.setFont(font);
        tuesdayBtn.setToggleable(false);
        tuesdayBtn.setFont(font);
        wednesdayBtn.setToggleable(false);
        wednesdayBtn.setFont(font);
        thursdayBtn.setToggleable(false);
        thursdayBtn.setFont(font);
        fridayBtn.setToggleable(false);
        fridayBtn.setFont(font);

        repeatLabel.setFont(font);

        editButton.setOnAction(event ->
        {
            AddDiscountDialog addDiscountDialog = new AddDiscountDialog(this.discount.getProduct());
            addDiscountDialog.setSelectedValues(this.discount);
            Optional<Discount> editedDiscount = addDiscountDialog.showAndWait();
            if(editedDiscount.isPresent())
            {
                DiscountsTable.editDiscount(discount.getProductName(), editedDiscount.get());
                switchView(getParent(), FullAccess.DISCOUNTS);
            }
        });

        deleteButton.setOnAction(event ->
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "About To Delete Discount \n\n Do You Want To Continue?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get().equals(ButtonType.YES))
            {
                DiscountsTable.deleteDiscount(this.discount.getProductName());
                switchView(getParent(), FullAccess.DISCOUNTS);
            }
            else
            {
                alert.close();
            }
        });
    }

    private void addComponents()
    {
        add(productLabel, 0, 0);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator, 4);
        add(separator, 0, 1);

        add(oldPriceLabel, 0, 2);
        add(discountPriceLabel, 2, 2);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator1, 4);
        add(separator1, 0, 3);

        add(toggleButtonContainer, 0, 4);
        GridPane.setColumnSpan(toggleButtonContainer, 4);
        GridPane.setHalignment(toggleButtonContainer, HPos.CENTER);

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator2, 4);
        add(separator2, 0, 5);

        add(repeatLabel, 0, 6);

        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        GridPane.setColumnSpan(separator3, 4);
        add(separator3, 0, 7);

        add(editButton, 0, 8);
        add(deleteButton, 3, 8);
        GridPane.setHalignment(deleteButton, HPos.RIGHT);
    }

    private void setDaysOfSaleButtons(HashMap<DayOfWeek, Boolean> daysOfDiscount)
    {
        mondayBtn.setSelected(daysOfDiscount.get(DayOfWeek.MONDAY));
        tuesdayBtn.setSelected(daysOfDiscount.get(DayOfWeek.TUESDAY));
        wednesdayBtn.setSelected(daysOfDiscount.get(DayOfWeek.WEDNESDAY));
        thursdayBtn.setSelected(daysOfDiscount.get(DayOfWeek.THURSDAY));
        fridayBtn.setSelected(daysOfDiscount.get(DayOfWeek.FRIDAY));
    }
}