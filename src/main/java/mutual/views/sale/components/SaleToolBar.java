package mutual.views.sale.components;

/*
 * Created by Jonah on 1/30/2017.
 */

import database.tables.ProductsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mutual.types.Category;
import mutual.types.Product;
import mutual.views.sale.selector.ProductSelectorPanel;

import java.util.ArrayList;

public class SaleToolBar extends HBox
{
    private TextField productSearchField;
    private ComboBox<String> categoryComboBox;
    private Button searchButton;
    private Button clearButton;

    public SaleToolBar()
    {
        ObservableList<String> categories = FXCollections.observableArrayList();

        for(Category category : Category.values())
        {
            categories.add(category.toString());
        }

        productSearchField = new TextField();
        categoryComboBox = new ComboBox<>(categories);
        searchButton = new Button("Search");
        clearButton = new Button("Clear");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setSpacing(10);
        setPadding(new Insets(10));
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0))));

        productSearchField.setPromptText("Search...");

        categoryComboBox.getSelectionModel().selectFirst();

        searchButton.setOnAction(event ->
        {
            String search = productSearchField.getText();
            String category = categoryComboBox.getSelectionModel().getSelectedItem();

            ArrayList<Product> searchResults = new ArrayList<>();

            if(!search.isEmpty() && category.equalsIgnoreCase("all"))
            {
                searchResults = ProductsTable.getProductsLike(search);
            }
            else if(!search.isEmpty() && !category.equalsIgnoreCase("all"))
            {
                searchResults = ProductsTable.getProductsLikeAndInCategory(search, category);
            }
            else if(search.isEmpty() && category.equalsIgnoreCase("all"))
            {
                searchResults = ProductsTable.getProducts();
            }
            else if(search.isEmpty() && !category.equalsIgnoreCase("all"))
            {
                searchResults = ProductsTable.getProductsInCategory(category);
            }
            else
            {
                // Invalid Search
            }

            ProductSelectorPanel parent = (ProductSelectorPanel) getParent();
            parent.setProducts(searchResults);
        });

        clearButton.setOnAction(event ->
        {
            productSearchField.clear();

            ProductSelectorPanel parent = (ProductSelectorPanel) getParent();
            parent.setProducts(ProductsTable.getProducts());
        });
    }

    private void addComponents()
    {
        getChildren().addAll(productSearchField, categoryComboBox, searchButton, clearButton);
    }
}
