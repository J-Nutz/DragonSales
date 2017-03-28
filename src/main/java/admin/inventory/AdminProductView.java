package admin.inventory;

/*
 * Created by Jonah on 11/10/2016.
 */

import database.data.ProductsHeld;
import database.tables.ProductsTable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.types.Product;

import java.util.ArrayList;
import java.util.Optional;

public class AdminProductView extends GridPane
{
    private final Product product;
    private boolean extraComponentsShowing = false;

    private Label productLbl;
    private Label productStockLevelLabel;
    private ProgressBar productStockLevel;
    private Label purchasePriceLabel;
    private Label salePriceLabel;
    private Label productSaleLabel;
    private Label productCategoryLabel;
    private Label expirationDateLabel;
    private Label totalSoldLabel;
    private Label dateLastSoldLabel;
    private Label dateOrderedLabel;
    private Label dateReceivedLabel;

    private Button restockButton;
    private Button removeButton;

    public AdminProductView(Product product)
    {
        this.product = product;

        productLbl = new Label(product.getName());
        productStockLevelLabel = new Label(product.getCurrentQuantity() + " / " + product.getInitialQuantity());
        productStockLevel = new ProgressBar();
        purchasePriceLabel = new Label("$" + product.getPurchasePrice().toString());
        salePriceLabel = new Label("$" + product.getSalePrice().toString());
        productSaleLabel = new Label();
        productCategoryLabel = new Label("(" + product.getCategory() + ")");
        expirationDateLabel = new Label("Expires: " + product.getExpirationDate());
        totalSoldLabel = new Label("Total Sold: " + product.getTotalSold());
        dateLastSoldLabel = new Label("Last Sold: " + product.getDateLastSold());
        dateOrderedLabel = new Label("Ordered: " + product.getDateOrdered());
        dateReceivedLabel = new Label("Received: " + product.getDateReceived());

        restockButton = new Button("Restock");
        removeButton = new Button("Remove");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setVgap(10);
        setHgap(10);
        setPadding(new Insets(5));
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        addEventFilter(MouseEvent.MOUSE_PRESSED, event ->
        {
            extraComponentsShowing = !extraComponentsShowing;
            addExtraComponents();
        });

        productLbl.setFont(new Font("Courier New", 24));

        productStockLevel.setProgress((double) product.getCurrentQuantity() / (double) product.getInitialQuantity());
        productStockLevel.setMaxWidth(Double.POSITIVE_INFINITY);

        removeButton.setOnAction(event ->
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "About To Delete " + product.getName() + "\n\n Do You Want To Continue?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get().equals(ButtonType.YES))
            {
                ProductsTable.deleteProduct(product.getName());
                ProductsHeld.removeProduct(product);
                AdminInventoryView inventoryView = (AdminInventoryView) this.getParent().getParent().getParent().getParent().getParent();
                ArrayList<Product> products = ProductsHeld.getProducts(); //ProductsTable.getProducts();
                inventoryView.setProducts(products);
            }
            else
            {
                alert.close();
            }
        });
    }

    private void addComponents()
    {
        GridPane.setColumnSpan(productLbl, 2);
        GridPane.setFillWidth(productLbl, true);
        GridPane.setHalignment(productLbl, HPos.CENTER);
        add(productLbl, 0, 0);

        GridPane.setColumnSpan(productStockLevelLabel, 2);
        GridPane.setFillWidth(productStockLevelLabel, true);
        GridPane.setHalignment(productStockLevelLabel, HPos.CENTER);
        add(productStockLevelLabel, 0, 1);

        GridPane.setColumnSpan(productStockLevel, 2);
        GridPane.setFillWidth(productStockLevel, true);
        add(productStockLevel, 0, 2);

        add(purchasePriceLabel, 0, 3);
        add(salePriceLabel, 1, 3);

        add(productCategoryLabel, 0, 4);
        add(expirationDateLabel, 1, 4);
    }

    private void addExtraComponents()
    {
        if(extraComponentsShowing)
        {
            add(dateLastSoldLabel, 0, 5);
            add(totalSoldLabel, 1, 5);

            add(dateOrderedLabel, 0, 6);
            add(dateReceivedLabel, 1, 6);

            GridPane.setHalignment(restockButton, HPos.CENTER);
            add(restockButton, 0, 7);

            GridPane.setHalignment(removeButton, HPos.CENTER);
            add(removeButton, 1, 7);
        }
        else
        {
            getChildren().removeAll(dateLastSoldLabel, totalSoldLabel, dateOrderedLabel, dateReceivedLabel, restockButton, removeButton);
        }
    }
}