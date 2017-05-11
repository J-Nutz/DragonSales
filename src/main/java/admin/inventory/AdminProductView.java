package admin.inventory;

/*
 * Created by Jonah on 11/10/2016.
 */

import database.tables.DiscountsTable;
import database.tables.ProductsTable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.types.Product;

import java.util.Optional;

import static admin.inventory.AdminInventoryView.setRowSpanOnClick;

public class AdminProductView extends GridPane
{
    private final Product product;
    //private boolean extraComponentsShowing = false;

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

    private boolean selected = false;

    public AdminProductView(Product product)
    {
        this.product = product;

        productLbl = new Label(product.getName());
        productStockLevelLabel = new Label(product.getCurrentQuantity() + " / " + product.getInitialQuantity());
        productStockLevel = new ProgressBar();
        purchasePriceLabel = new Label("Purchase Price: $" + product.getPurchasePrice().toString());
        salePriceLabel = new Label("Sale Price: $" + product.getSalePrice().toString());
        productSaleLabel = new Label();
        productCategoryLabel = new Label("Category: " + product.getCategory());
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
        setHgap(20);
        setPadding(new Insets(5));
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));


        setOnMouseClicked(event ->
        {
            if(!selected)
            {
                selected = true;
                setRowSpanOnClick(this, true);
                addExtraComponents();
            }
            else
            {
                selected = false;
                setRowSpanOnClick(this, false);
                removeExtraComponents();
            }
        });

        productLbl.setFont(new Font("Courier New", 24));

        productStockLevel.setProgress((double) product.getCurrentQuantity() / (double) product.getInitialQuantity());
        productStockLevel.setMaxWidth(Double.POSITIVE_INFINITY);

        restockButton.setOnAction(event ->
        {
            StockProductDialog stockProductDialog = new StockProductDialog();
            stockProductDialog.setFields(product);

            Optional<Product> newProductResult = stockProductDialog.showAndWait();

            if(newProductResult.isPresent())
            {
                Product newProduct = newProductResult.get();
                newProduct.setTotalSold(product.getTotalSold());
                newProduct.setDiscountPrice(product.getDiscountPrice());

                ProductsTable.updateProduct(product.getName(), newProduct);
                AdminInventoryView.setProducts(ProductsTable.getProducts());
            }
        });

        removeButton.setOnAction(event ->
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "About To Delete " + product.getName() + "\n\n Do You Want To Continue?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get().equals(ButtonType.YES))
            {
                DiscountsTable.deleteDiscount(product.getName());
                ProductsTable.deleteProduct(product.getName());
                AdminInventoryView.setProducts(ProductsTable.getProducts());
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

        add(totalSoldLabel, 0, 4);
        add(dateLastSoldLabel, 1, 4);
    }

    public void addExtraComponents()
    {
        add(productCategoryLabel, 0, 5);
        add(expirationDateLabel, 1, 5);

        add(dateOrderedLabel, 0, 6);
        add(dateReceivedLabel, 1, 6);

        GridPane.setHalignment(restockButton, HPos.CENTER);
        add(restockButton, 0, 7);

        GridPane.setHalignment(removeButton, HPos.CENTER);
        add(removeButton, 1, 7);
    }

    private void removeExtraComponents()
    {
        getChildren().removeAll(productCategoryLabel, expirationDateLabel, dateOrderedLabel, dateReceivedLabel, restockButton, removeButton);
    }
}