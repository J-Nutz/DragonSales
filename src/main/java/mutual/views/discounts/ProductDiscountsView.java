package mutual.views.discounts;

/*
 * Created by Jonah on 2/7/2017.
 */

import database.tables.DiscountsTable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import mutual.types.Discount;

import java.util.ArrayList;

public class ProductDiscountsView extends BorderPane
{
    private ProductDiscountsToolBar productDiscountsToolBar;
    private TilePane productDiscountsContainer;

    public ProductDiscountsView()
    {
        productDiscountsToolBar = new ProductDiscountsToolBar();
        productDiscountsContainer = new TilePane(Orientation.HORIZONTAL, 10, 10);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        productDiscountsContainer.setPadding(new Insets(10));
        productDiscountsContainer.setPrefColumns(3);
    }

    private void addComponents()
    {
        ArrayList<Discount> discounts = DiscountsTable.getDiscounts();

        if(!discounts.isEmpty())
        {
            for(Discount discount : discounts)
            {
                ProductDiscountView productDiscountView = new ProductDiscountView(discount);
                productDiscountsContainer.getChildren().add(productDiscountView);
            }

            setCenter(productDiscountsContainer);
        }
        else
        {
            Label noDiscountsLabel = new Label("No Discounts Found");
            noDiscountsLabel.setFont(new Font(36));

            HBox hBox = new HBox(noDiscountsLabel);
            hBox.setAlignment(Pos.CENTER);

            setCenter(hBox);
        }

        setTop(productDiscountsToolBar);

    }

    public void setProducts(ArrayList<Discount> products)
    {
        Platform.runLater(() -> productDiscountsContainer.getChildren().clear());

        for(Discount discount : products)
        {
            ProductDiscountView productDiscountView = new ProductDiscountView(discount);
            Platform.runLater(() -> productDiscountsContainer.getChildren().add(productDiscountView));
        }
    }

    public void addSale(ProductDiscountView productDiscountView)
    {
        productDiscountsContainer.getChildren().add(productDiscountView);
    }
}