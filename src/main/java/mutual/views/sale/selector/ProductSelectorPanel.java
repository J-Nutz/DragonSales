package mutual.views.sale.selector;

/*
 * Created by Jonah on 11/19/2016.
 */

import database.tables.ProductsTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import mutual.types.Product;
import mutual.views.sale.components.SaleToolBar;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductSelectorPanel extends BorderPane
{
    private SaleToolBar saleToolBar;
    private TilePane productViewContainer;

    public ProductSelectorPanel()
    {
        saleToolBar = new SaleToolBar();
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
                if(product.getExpirationDate().toLocalDate().isAfter(LocalDate.now()) &&
                   product.getCurrentQuantity() > 0)
                {
                    ProductView productView = new ProductView(product);

                    productViewContainer.getChildren().add(productView);
                }
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

        setTop(saleToolBar);
    }

    public void setProducts(ArrayList<Product> products)
    {
        addComponents(products);
    }
}