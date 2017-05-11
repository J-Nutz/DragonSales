package admin.inventory;

/*
 * Created by Jonah on 11/10/2016.
 */

import database.tables.ProductsTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mutual.types.Product;

import java.util.ArrayList;

public class AdminInventoryView extends BorderPane
{
    private ArrayList<Product> products;
    private InventoryToolBar inventoryToolBar;

    private static VBox container;
    private static ScrollPane productsScrollPane;

    public AdminInventoryView()
    {
        products = ProductsTable.getProducts();
        inventoryToolBar = new InventoryToolBar();

        container = new VBox();
        productsScrollPane = new ScrollPane(container);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        container.setSpacing(15);
        container.setPadding(new Insets(10));
    }

    private void addComponents()
    {
        setTop(inventoryToolBar);
        setCenter(productsScrollPane);

        AdminInventoryView.setProducts(products);
    }

    public static void setProducts(ArrayList<Product> products)
    {
        container.getChildren().clear();

        if(productsScrollPane.getContent() != container)
        {
            productsScrollPane.setContent(container);
        }

        if(!products.isEmpty())
        {
            int column = 0;
            GridPane gridPane = new GridPane();

            gridPane.setHgap(10);
            container.getChildren().add(gridPane);

            for(Product product : products)
            {
                final int finalColumn = column;
                final int finalRow = 0;

                if(column < 5)
                {
                    gridPane.add(new AdminProductView(product), finalColumn, finalRow);
                    column++;
                }
                else
                {
                    column = 0;

                    gridPane = new GridPane();
                    gridPane.add(new AdminProductView(product), 0, 0);
                    gridPane.setHgap(10);

                    container.getChildren().add(gridPane);
                    column++;
                }
            }
        }
        else
        {
            Label noProductsLabel = new Label("No Products Found");
            noProductsLabel.setFont(new Font(36));

            HBox hBox = new HBox(noProductsLabel);
            hBox.setPadding(new Insets(25));
            hBox.setAlignment(Pos.CENTER);

            productsScrollPane.setContent(hBox);
            productsScrollPane.setFitToWidth(true);
        }
    }

    public static void setRowSpanOnClick(Node node, boolean selected)
    {
        if(selected)
        {
            GridPane.setRowSpan(node, 2);
        }
        else
        {
            GridPane.setRowSpan(node, 1);
        }
    }
}