package mutual.views.discounts;

/*
 * Created by Jonah on 2/7/2017.
 */

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
import mutual.types.Discount;
import mutual.views.FullAccess;

import java.util.ArrayList;

import static admin.home.ViewContainer.switchView;

public class ProductDiscountsToolBar extends BorderPane
{
    private HBox container;

    private TextField productSearchField;
    private ComboBox<String> categoryComboBox;
    private Button searchButton;

    private Button newDiscountBtn;

    public ProductDiscountsToolBar()
    {
        ObservableList<String> categories = FXCollections.observableArrayList();

        for(Category category : Category.values())
        {
            categories.add(category.toString());
        }

        container = new HBox(15);

        productSearchField = new TextField();
        categoryComboBox = new ComboBox<>(categories);
        searchButton = new Button("Search");

        newDiscountBtn = new Button("New Discount");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0))));
        setPadding(new Insets(10));
        setAlignment(newDiscountBtn, Pos.CENTER);

        container.setSpacing(10);

        productSearchField.setPromptText("Search...");

        categoryComboBox.getSelectionModel().selectFirst();

        searchButton.setOnAction(event ->
        {
            String search = productSearchField.getText();
            String category = categoryComboBox.getSelectionModel().getSelectedItem();

            ArrayList<Discount> searchResults = new ArrayList<>();

            if(!search.isEmpty() && category.equalsIgnoreCase("all"))
            {
                //searchResults = ProductsTable.getProductsLike(search);
                //TODO Change To SalesTable When Implemented
            }
            else if(!search.isEmpty() && !category.equalsIgnoreCase("all"))
            {
                //searchResults = ProductsTable.getProductsLikeAndInCategory(search, category);
                //TODO Change To SalesTable When Implemented
            }
            else if(search.isEmpty() && category.equalsIgnoreCase("all"))
            {
                //searchResults = ProductsTable.getProducts();
                //TODO Change To SalesTable When Implemented
            }
            else if(search.isEmpty() && !category.equalsIgnoreCase("all"))
            {
                //searchResults = ProductsTable.getProductsInCategory(category);
                //TODO Change To SalesTable When Implemented
            }
            else
            {
                // Invalid Search
            }

            ProductDiscountsView parent = (ProductDiscountsView) getParent();
            parent.setProducts(searchResults);
        });

        newDiscountBtn.setOnAction(event -> switchView(this, FullAccess.PRODUCT_DISCOUNT_SELECTOR));
    }

    private void addComponents()
    {
        container.getChildren().addAll(productSearchField, categoryComboBox, searchButton);

        setCenter(container);
        setRight(newDiscountBtn);
    }
}