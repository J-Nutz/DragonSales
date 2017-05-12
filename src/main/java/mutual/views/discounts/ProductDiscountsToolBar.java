package mutual.views.discounts;

/*
 * Created by Jonah on 2/7/2017.
 */

import database.tables.DiscountsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private Button searchButton;
    private Button clearButton;

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
        searchButton = new Button("Search");
        clearButton = new Button("Clear");

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

        searchButton.setOnAction(event ->
        {
            String search = productSearchField.getText();

            ArrayList<Discount> searchResults = new ArrayList<>();

            if(!search.isEmpty())
            {
                searchResults = DiscountsTable.getDiscountsLike(search);
            }
            else if(search.isEmpty())
            {
                searchResults = DiscountsTable.getDiscounts();
            }
            else
            {
                // Invalid Search
            }

            ProductDiscountsView parent = (ProductDiscountsView) getParent();
            parent.setProducts(searchResults);
        });

        clearButton.setOnAction(event1 ->
        {
            productSearchField.clear();

            ProductDiscountsView parent = (ProductDiscountsView) getParent();
            parent.setProducts(DiscountsTable.getDiscounts());
        });

        newDiscountBtn.setOnAction(event -> switchView(this, FullAccess.ADD_DISCOUNT));
    }

    private void addComponents()
    {
        container.getChildren().addAll(productSearchField, searchButton, clearButton);

        setCenter(container);
        setRight(newDiscountBtn);
    }
}