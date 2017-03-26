package mutual.views.sale.selector;

/*
 * Created by Jonah on 11/19/2016.
 */

import database.data.ProductsHeld;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mutual.types.Product;
import mutual.views.sale.components.SaleToolBar;

import java.util.ArrayList;

public class ProductSelectorPanel extends BorderPane
{
    private SaleToolBar saleToolBar;
    private GridPane productsContainer;

    public ProductSelectorPanel()
    {
        saleToolBar = new SaleToolBar();
        productsContainer = new GridPane();

        initComponents();
        addComponents(ProductsHeld.getProducts());
        //addComponents(ProductsTable.getProducts());
    }

    private void initComponents()
    {
        productsContainer.setVgap(10);
        productsContainer.setHgap(10);
        productsContainer.setPadding(new Insets(10));
    }

    private void addComponents(ArrayList<Product> products)
    {
        int column = 0;
        int row = 0;

        if(!products.isEmpty())
        {
            for(Product product : products)
            {
                if(column < 3)
                {
                    productsContainer.add(new ProductView(product),
                                          column,
                                          row);
                    column++;
                }
                else if(column == 3)
                {
                    column = 0;
                    row++;
                    productsContainer.add(new ProductView(product),
                                          column,
                                          row);
                }
            }

            setCenter(productsContainer);
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
        productsContainer.getChildren().clear();

        addComponents(products);
    }

    public void setOnProductViewClicked(EventHandler<MouseEvent> mouseEvent)
    {
        for(Node node : productsContainer.getChildren())
        {
            ProductView productView = (ProductView) node;
            productView.setOnClicked(mouseEvent);
        }
    }
}