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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import mutual.types.Product;

import java.util.ArrayList;

public class InventoryView extends BorderPane
{
    private ArrayList<Product> products;
    private InventoryToolBar inventoryToolBar;

    private static VBox container;
    private static ScrollPane productsScrollPane;

    public InventoryView()
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
        container.setFillWidth(true);
        container.setSpacing(10);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);

        double width = (Screen.getPrimary().getBounds().getWidth());

        productsScrollPane.setMinWidth(width);
        productsScrollPane.setMaxWidth(width);
        productsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        container.setMinWidth(width);
    }

    private void addComponents()
    {
        setTop(inventoryToolBar);
        setCenter(productsScrollPane);

        InventoryView.setProducts(products);
    }

    public static void setProducts(ArrayList<Product> products)
    {
        container.getChildren().clear();

        if(productsScrollPane.getContent() != container)
        {
            container.setFillWidth(true);
            container.setAlignment(Pos.CENTER);
            productsScrollPane.setContent(container);
        }

        if(products != null && !products.isEmpty())
        {
            int column = 0;
            GridPane gridPane = new GridPane();

            gridPane.setHgap(10);

            container.getChildren().add(gridPane);

            for(Product product : products)
            {
                final int finalColumn = column;
                final int finalRow = 0;

                if(column < 4)
                {
                    InventoryProductView inventoryProductView = new InventoryProductView(product);

                    gridPane.add(inventoryProductView, finalColumn, finalRow);
                    column++;
                }
                else
                {
                    column = 0;

                    gridPane = new GridPane();
                    gridPane.setHgap(10);

                    InventoryProductView inventoryProductView = new InventoryProductView(product);
                    gridPane.add(inventoryProductView, 0, 0);

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