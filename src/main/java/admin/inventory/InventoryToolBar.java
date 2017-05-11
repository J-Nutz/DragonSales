package admin.inventory;

/*
 * Created by Jonah on 1/12/2017.
 */

import database.tables.ProductsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mutual.types.Category;
import mutual.types.Product;
import mutual.views.FullAccess;

import java.util.ArrayList;

import static admin.home.ViewContainer.switchView;

public class InventoryToolBar extends BorderPane
{
    private HBox searchContainer;
    private TextField productSearchField;
    private ComboBox<String> categoryComboBox;
    private Button searchButton;
    private Button clearButton;

    private HBox rightContainer;
    private Button newProductButton;

    public InventoryToolBar()
    {
        ObservableList<String> categories = FXCollections.observableArrayList();

        for(Category category : Category.values())
        {
            categories.add(category.toString());
        }

        searchContainer = new HBox(10);
        productSearchField = new TextField();
        categoryComboBox = new ComboBox<>(categories);
        searchButton = new Button("Search");
        clearButton = new Button("Clear");

        rightContainer = new HBox();
        newProductButton = new Button("New Product");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
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
                searchResults = new ArrayList<>(0);
            }

            AdminInventoryView.setProducts(searchResults);
        });

        clearButton.setOnAction(event1 ->
        {
            productSearchField.clear();

            AdminInventoryView.setProducts(ProductsTable.getProducts());
        });

        searchContainer.setAlignment(Pos.CENTER_LEFT);

        rightContainer.setAlignment(Pos.CENTER);
        rightContainer.setMinWidth(175);
        newProductButton.setOnAction(event -> switchView(this, FullAccess.STOCK));
    }

    private void addComponents()
    {
        searchContainer.getChildren().addAll(productSearchField, categoryComboBox, searchButton, clearButton);

        rightContainer.getChildren().add(newProductButton);

        setCenter(searchContainer);
        setRight(rightContainer);
    }
}