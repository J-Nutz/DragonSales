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
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mutual.types.Product;

import java.time.LocalDate;
import java.util.Optional;

import static admin.inventory.AdminInventoryView.setRowSpanOnClick;

public class AdminProductView extends GridPane
{
    private final Product product;

    private Label productLabel;
    private Label expiredLabel;
    private Label productStockLevelLabel;
    private ProgressBar productStockLevel;
    private Label purchasePriceLabel;
    private Label salePriceLabel;
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

        productLabel = new Label(product.getName());
        expiredLabel = new Label("Expired!");
        productStockLevelLabel = new Label(product.getCurrentQuantity() + " / " + product.getInitialQuantity());
        productStockLevel = new ProgressBar();
        purchasePriceLabel = new Label("Purchase Price: $" + product.getPurchasePrice().toString());
        salePriceLabel = new Label("Sale Price: $" + product.getSalePrice().toString());
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

        LocalDate expirationDate = product.getExpirationDate().toLocalDate();
        LocalDate today = LocalDate.now();

        if(expirationDate.isBefore(today))
        {
            GridPane.setColumnSpan(expiredLabel, 2);
            GridPane.setFillWidth(expiredLabel, true);
            GridPane.setHalignment(expiredLabel, HPos.CENTER);
            add(expiredLabel, 0, 1);

            setBackground(new Background(new BackgroundFill(Color.rgb(255, 26, 26), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        setOnMouseClicked(event -> showFullDetails());

        productLabel.setFont(new Font("Ubuntu", 24));
        productLabel.setTextAlignment(TextAlignment.CENTER);

        expiredLabel.setTextAlignment(TextAlignment.CENTER);
        expiredLabel.setFont(Font.font("Ubuntu", FontWeight.BOLD, 18));

        productStockLevel.setProgress((double) product.getCurrentQuantity() / (double) product.getInitialQuantity());
        productStockLevel.setMaxWidth(Double.POSITIVE_INFINITY);
        productStockLevel.setOnMouseClicked(event1 -> showFullDetails());

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
        GridPane.setColumnSpan(productLabel, 2);
        GridPane.setFillWidth(productLabel, true);
        GridPane.setHalignment(productLabel, HPos.CENTER);
        add(productLabel, 0, 0);

        GridPane.setColumnSpan(productStockLevelLabel, 2);
        GridPane.setFillWidth(productStockLevelLabel, true);
        GridPane.setHalignment(productStockLevelLabel, HPos.CENTER);
        add(productStockLevelLabel, 0, 2);

        GridPane.setColumnSpan(productStockLevel, 2);
        GridPane.setFillWidth(productStockLevel, true);
        add(productStockLevel, 0, 3);

        add(purchasePriceLabel, 0, 4);
        add(salePriceLabel, 1, 4);

        add(totalSoldLabel, 0, 5);
        add(dateLastSoldLabel, 1, 5);
    }

    public void addExtraComponents()
    {
        add(productCategoryLabel, 0, 6);
        add(expirationDateLabel, 1, 6);

        add(dateOrderedLabel, 0, 7);
        add(dateReceivedLabel, 1, 7);

        GridPane.setHalignment(restockButton, HPos.CENTER);
        add(restockButton, 0, 8);

        GridPane.setHalignment(removeButton, HPos.CENTER);
        add(removeButton, 1, 8);
    }

    private void removeExtraComponents()
    {
        getChildren().removeAll(productCategoryLabel, expirationDateLabel, dateOrderedLabel, dateReceivedLabel, restockButton, removeButton);
    }

    private void showFullDetails()
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
    }
}