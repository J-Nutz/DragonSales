package mutual.views.discounts;

/*
 * Created by Jonah on 5/11/2017.
 */

import database.tables.DiscountsTable;
import database.tables.ProductsTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import mutual.types.Discount;
import mutual.types.Product;
import mutual.views.View;
import mutual.views.sale.selector.ProductView;

import java.util.ArrayList;
import java.util.Optional;

import static admin.home.ViewContainer.switchView;

public class ProductDiscountSelector extends BorderPane
{
    private TilePane productViewContainer;

    public ProductDiscountSelector()
    {
        productViewContainer = new TilePane(10, 10);

        initComponents();
        addComponents(ProductsTable.getProducts());
    }

    private void initComponents()
    {
        productViewContainer.setPadding(new Insets(15));
    }

    private void addComponents(ArrayList<Product> products)
    {
        productViewContainer.getChildren().clear();

        if(products != null && !products.isEmpty())
        {
            for(Product product : products)
            {
                ProductView productView = new ProductView(product);
                productView.setOnClicked(event ->
                {
                    AddDiscountDialog addDiscountDialog = new AddDiscountDialog(product);
                    Optional<Discount> productDiscount = addDiscountDialog.showAndWait();

                    if(productDiscount.isPresent())
                    {
                        DiscountsTable.addDiscount(productDiscount.get());
                        switchView(this, View.DISCOUNTS);
                    }
                    else
                    {
                        addDiscountDialog.close();
                        switchView(this, View.ADD_DISCOUNT);
                    }
                });

                productViewContainer.getChildren().add(productView);
            }

            setCenter(productViewContainer);
        }
        else
        {
            Label noProductsLabel = new Label("No Products Found");
            noProductsLabel.setFont(new Font(36));

            HBox hBox = new HBox(noProductsLabel);
            hBox.setAlignment(Pos.CENTER);

            setCenter(hBox);
        }
    }

    public void setProducts(ArrayList<Product> products)
    {
        addComponents(products);
    }
}