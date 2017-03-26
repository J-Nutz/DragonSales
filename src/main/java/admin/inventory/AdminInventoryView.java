package admin.inventory;

/*
 * Created by Jonah on 11/10/2016.
 */

import database.data.ProductsHeld;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mutual.types.Product;

import java.util.ArrayList;

public class AdminInventoryView extends BorderPane
{
    private ArrayList<Product> products;
    private InventoryToolBar inventoryToolBar;
    private GridPane productContainer;
    private ScrollPane productsScrollPane;

    public AdminInventoryView()
    {
        products = ProductsHeld.getProducts();
        inventoryToolBar = new InventoryToolBar();
        productContainer = new GridPane();
        productsScrollPane = new ScrollPane(productContainer);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        productContainer.setHgap(15);
        productContainer.setVgap(15);
        productContainer.setPadding(new Insets(5));
    }

    private void addComponents()
    {
        setTop(inventoryToolBar);
        setCenter(productsScrollPane);

        setProducts(products);
    }

    public void setProducts(ArrayList<Product> products)
    {
        productContainer.getChildren().clear();

        if(!products.isEmpty())
        {
            int column = 0, row = 0;
            for(Product product : products)
            {
                final int finalColumn = column;
                final int finalRow = row;

                if(column < 3)
                {
                    Platform.runLater(() -> productContainer.add(new AdminProductView(product), finalColumn, finalRow));
                    column++;
                }
                else
                {
                    column = 0;
                    row++;
                    Platform.runLater(() -> productContainer.add(new AdminProductView(product), finalColumn, finalRow));
                }
            }
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
}