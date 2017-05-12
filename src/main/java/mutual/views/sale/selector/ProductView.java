package mutual.views.sale.selector;

/*
 * Created by Jonah on 11/25/2016.
 */

import database.tables.DiscountsTable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.types.Discount;
import mutual.types.OrderFragment;
import mutual.types.Product;
import mutual.views.sale.components.SaleDialog;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import static mutual.views.sale.components.OrderPanel.addToOrder;
import static mutual.views.sale.components.SaleControlsPanel.updateTotal;

public class ProductView extends VBox
{
    private Product product;
    private boolean hasDiscount = false;

    private final Label productNameLabel;
    private final Label productPriceLabel;
    private final Label productDiscountLabel;

    public ProductView(Product product)
    {
        this.product = product;

        productNameLabel = new Label();
        productPriceLabel = new Label("$" + product.getSalePrice().toString());
        productDiscountLabel = new Label();

        initComponents();
        addComponents(hasDiscount);
    }

    private void initComponents()
    {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        //setVgap(5);
        //setHgap(10);
        setPadding(new Insets(6));
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        setOnMouseEntered(event -> setCursor(Cursor.HAND));
        setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
        setOnMouseClicked(event ->
        {
            Optional<OrderFragment> orderFragment = new SaleDialog(product, hasDiscount).showAndWait();

            if(orderFragment.isPresent())
            {
                addToOrder(orderFragment.get());
                updateTotal();
            }
        });

        productNameLabel.setText(product.getName());
        productNameLabel.setFont(new Font(24));

        DiscountsTable.getProductsWithDiscount()
                      .stream()
                      .filter(productName -> productName.equals(product.getName()))
                      .forEach(productName ->
        {
            Discount discount = DiscountsTable.getDiscount(productName);
            DayOfWeek today = LocalDate.now().getDayOfWeek();
            boolean notWeekend = (today != DayOfWeek.SATURDAY) && (today != DayOfWeek.SUNDAY);

            hasDiscount = notWeekend && discount.getDayOfSale(today);
        });

        productDiscountLabel.setFont(new Font(18));
        productPriceLabel.setAlignment(Pos.CENTER);
        productPriceLabel.setFont(new Font(18));
    }

    private void addComponents(boolean discountVersion)
    {
        getChildren().clear();

        if(discountVersion)
        {
            productDiscountLabel.setText("On Sale!");
            productPriceLabel.setText("$" + product.getDiscountPrice().toString());

            getChildren().add(productNameLabel);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().add(productDiscountLabel);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().add(productPriceLabel);
        }
        else
        {
            productPriceLabel.setText("$" + product.getSalePrice().toString());

            getChildren().add(productNameLabel);
            getChildren().add(new Separator(Orientation.HORIZONTAL));
            getChildren().add(productPriceLabel);
        }

        /*for(Node node : getChildren())
        {
            GridPane.setHalignment(node, HPos.CENTER);
        }*/
    }

    public Product getProduct()
    {
        return product;
    }

    public void setOnClicked(EventHandler<MouseEvent> mouseEvent)
    {
        setOnMouseClicked(mouseEvent);
    }
}