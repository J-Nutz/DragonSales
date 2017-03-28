package admin.inventory;

/*
 * Created by Jonah on 1/9/2017.
 */

import database.data.ProductsHeld;
import database.tables.ProductsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import mutual.types.Category;
import mutual.types.Product;
import worker.BigDecimalSpinnerValueFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class StockInventoryView extends GridPane
{
    private ObservableList<Category> categories;
    private LocalDate date;

    private TextField nameTextField;
    private Label categoryLabel;
    private ComboBox<Category> categoryComboBox;
    private Label purchasePriceLabel;
    private Spinner<BigDecimal> purchasePriceSpinner;
    private Label salePriceLabel;
    private Spinner<BigDecimal> salePriceSpinner;
    private Label initialQuantityLabel;
    private Spinner<Integer> initialQuantitySpinner;
    private Label expirationDateLabel;
    private DatePicker expirationDatePicker;
    private Label orderedDateLabel;
    private DatePicker orderedDatePicker;
    private Label receivedDateLabel;
    private DatePicker receivedDatePicker;

    private Button clearFieldsButton;
    private Button addProductButton;
    private Button closeButton;
    private HBox buttonContainer;

    private BigDecimal zero = new BigDecimal("0.00");

    public StockInventoryView()
    {
        categories = FXCollections.observableArrayList(Category.values());
        date = LocalDate.now();

        nameTextField = new TextField();
        categoryLabel = new Label("Category:");
        categoryComboBox = new ComboBox<>();
        purchasePriceLabel = new Label("Purchase Price");
        purchasePriceSpinner = new Spinner<>();
        salePriceLabel = new Label("Sale Price:");
        salePriceSpinner = new Spinner<>();
        initialQuantityLabel = new Label("Quantity:");
        initialQuantitySpinner = new Spinner<>(0, 99, 0, 1);
        expirationDateLabel = new Label("Expires:");
        expirationDatePicker = new DatePicker(date);
        orderedDateLabel = new Label("Ordered:");
        orderedDatePicker = new DatePicker(date);
        receivedDateLabel = new Label("Received:");
        receivedDatePicker = new DatePicker(date);

        clearFieldsButton = new Button("Clear");
        addProductButton = new Button("Add");
        closeButton = new Button("Close");
        buttonContainer = new HBox(10);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10));
        setMinWidth(175);

        nameTextField.setPromptText("Product Name");
        nameTextField.setMaxWidth(150);

        categoryComboBox.setItems(categories);
        categoryComboBox.getSelectionModel().selectFirst();
        categoryComboBox.setMaxWidth(105);
        categoryComboBox.setMinWidth(105);

        purchasePriceSpinner.setValueFactory(new BigDecimalSpinnerValueFactory(new BigDecimal("0.01")));
        purchasePriceSpinner.getValueFactory().setValue(zero);
        purchasePriceSpinner.setMaxWidth(105);

        salePriceSpinner.setValueFactory(new BigDecimalSpinnerValueFactory(new BigDecimal("0.25")));
        salePriceSpinner.getValueFactory().setValue(zero);
        salePriceSpinner.setMaxWidth(105);

        initialQuantitySpinner.setEditable(true);
        initialQuantitySpinner.setMaxWidth(105);

        expirationDatePicker.setMaxWidth(105);

        orderedDatePicker.setMaxWidth(105);

        receivedDatePicker.setMaxWidth(105);

        clearFieldsButton.setOnAction(event -> clearFields());

        addProductButton.setOnAction(event ->
        {
            if(validFields())
            {
                Product product = constructProduct();

                ProductsTable.addProduct(product); //TODO: Check if product exists
                ProductsHeld.addProduct(product);

                AdminInventoryView inventoryView = (AdminInventoryView) this.getParent();
                inventoryView.setProducts(ProductsHeld.getProducts());

                clearFields();
            }
        });

        closeButton.setOnAction(event ->
        {
            BorderPane inventoryView = (BorderPane) this.getParent();
            inventoryView.setRight(null);
        });

        buttonContainer.setAlignment(Pos.CENTER);
    }

    private void addComponents()
    {
        add(nameTextField, 0, 0);
        GridPane.setColumnSpan(nameTextField, 2);
        GridPane.setHalignment(nameTextField, HPos.CENTER);

        add(categoryLabel, 0, 1);
        add(categoryComboBox, 1, 1);
        GridPane.setHalignment(categoryLabel, HPos.RIGHT);
        GridPane.setHalignment(categoryComboBox, HPos.LEFT);

        add(purchasePriceLabel, 0, 2);
        add(purchasePriceSpinner, 1, 2);
        GridPane.setHalignment(purchasePriceLabel, HPos.RIGHT);
        GridPane.setHalignment(purchasePriceSpinner, HPos.LEFT);

        add(salePriceLabel, 0, 3);
        add(salePriceSpinner, 1, 3);
        GridPane.setHalignment(salePriceLabel, HPos.RIGHT);
        GridPane.setHalignment(salePriceSpinner, HPos.LEFT);

        add(initialQuantityLabel, 0, 4);
        add(initialQuantitySpinner, 1, 4);
        GridPane.setHalignment(initialQuantityLabel, HPos.RIGHT);
        GridPane.setHalignment(initialQuantitySpinner, HPos.LEFT);

        add(expirationDateLabel, 0, 5);
        add(expirationDatePicker, 1, 5);
        GridPane.setHalignment(expirationDateLabel, HPos.RIGHT);
        GridPane.setHalignment(expirationDatePicker, HPos.LEFT);

        add(orderedDateLabel, 0, 6);
        add(orderedDatePicker, 1, 6);
        GridPane.setHalignment(orderedDateLabel, HPos.RIGHT);
        GridPane.setHalignment(orderedDatePicker, HPos.LEFT);

        add(receivedDateLabel, 0, 7);
        add(receivedDatePicker, 1, 7);
        GridPane.setHalignment(receivedDateLabel, HPos.RIGHT);
        GridPane.setHalignment(receivedDatePicker, HPos.LEFT);

        buttonContainer.getChildren().addAll(clearFieldsButton, addProductButton, closeButton);
        add(buttonContainer, 0, 8);
        GridPane.setColumnSpan(buttonContainer, 2);
    }

    private void clearFields()
    {
        nameTextField.clear();
        categoryComboBox.getSelectionModel().selectFirst();
        purchasePriceSpinner.getValueFactory().setValue(zero);
        salePriceSpinner.getValueFactory().setValue(zero);
        initialQuantitySpinner.getValueFactory().setValue(0);
        expirationDatePicker.setValue(date);
        orderedDatePicker.setValue(date);
        receivedDatePicker.setValue(date);
    }

    private boolean validFields()
    {
        boolean validName = !(nameTextField.getText().isEmpty());
        boolean validPrice = (salePriceSpinner.getValue().compareTo(zero) == 1);
        boolean validQuantity = initialQuantitySpinner.getValue() > 0;

        return validName && validPrice && validQuantity;
    }

    private Product constructProduct()
    {
        Product product = new Product();

        String productName = nameTextField.getText();
        product.setName(productName);

        String productCategory = categoryComboBox.getSelectionModel().getSelectedItem().toString();
        product.setCategory(productCategory);

        BigDecimal purchasePrice = purchasePriceSpinner.getValue();
        product.setPurchasePrice(purchasePrice);

        BigDecimal salePrice = salePriceSpinner.getValue();
        product.setSalePrice(salePrice);

        int productInitQuantity = initialQuantitySpinner.getValue();
        product.setInitialQuantity(productInitQuantity);
        product.setCurrentQuantity(productInitQuantity);

        product.setTotalSold(0);

        Date productExpirationDate = Date.valueOf(expirationDatePicker.getValue());
        product.setExpirationDate(productExpirationDate);

        Date productOrderedDate = Date.valueOf(orderedDatePicker.getValue());
        product.setDateOrdered(productOrderedDate);

        Date productReceivedDate = Date.valueOf(receivedDatePicker.getValue());
        product.setDateReceived(productReceivedDate);

        return product;
    }
}