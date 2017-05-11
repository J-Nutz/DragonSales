package admin.inventory;

/*
 * Created by Jonah on 5/11/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import mutual.types.Category;
import mutual.types.Product;
import worker.BigDecimalSpinnerValueFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static worker.ErrorMessageShower.showErrorMessage;

public class StockProductDialog extends Dialog<Product>
{
    private ObservableList<Category> categories;
    private LocalDate date;

    private GridPane container;

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
    private HBox buttonContainer;

    private HBox errorMessageContainer;

    private BigDecimal zero = new BigDecimal("0.00");

    public StockProductDialog()
    {
        getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);
        setResultConverter(param ->
        {
            if(!param.getButtonData().isCancelButton())
            {
                return constructProduct();
            }
            else
            {
                return null;
            }
        });

        final Button btOk = (Button) getDialogPane().lookupButton(ButtonType.FINISH);
        btOk.addEventFilter(ActionEvent.ACTION, event ->
        {
            if(!validFields())
            {
                event.consume();
                showErrorMessage(errorMessageContainer, "Invalid Fields");
            }
        });

        categories = FXCollections.observableArrayList(Category.values());
        date = LocalDate.now();

        container = new GridPane();

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
        buttonContainer = new HBox(10);

        errorMessageContainer = new HBox();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        container.setAlignment(Pos.CENTER);
        container.setHgap(15);
        container.setVgap(20);
        container.setPadding(new Insets(20));

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

        initialQuantitySpinner.setMaxWidth(105);

        expirationDatePicker.setMaxWidth(105);

        orderedDatePicker.setMaxWidth(105);

        receivedDatePicker.setMaxWidth(105);

        clearFieldsButton.setOnAction(event -> clearFields());

        buttonContainer.setAlignment(Pos.CENTER);

        errorMessageContainer.setAlignment(Pos.CENTER);
    }

    private void addComponents()
    {
        container.add(nameTextField, 0, 0);
        GridPane.setColumnSpan(nameTextField, 2);
        GridPane.setHalignment(nameTextField, HPos.CENTER);

        container.add(categoryLabel, 0, 1);
        container.add(categoryComboBox, 1, 1);
        GridPane.setHalignment(categoryLabel, HPos.RIGHT);
        GridPane.setHalignment(categoryComboBox, HPos.LEFT);

        container.add(purchasePriceLabel, 0, 2);
        container.add(purchasePriceSpinner, 1, 2);
        GridPane.setHalignment(purchasePriceLabel, HPos.RIGHT);
        GridPane.setHalignment(purchasePriceSpinner, HPos.LEFT);

        container.add(salePriceLabel, 0, 3);
        container.add(salePriceSpinner, 1, 3);
        GridPane.setHalignment(salePriceLabel, HPos.RIGHT);
        GridPane.setHalignment(salePriceSpinner, HPos.LEFT);

        container.add(initialQuantityLabel, 0, 4);
        container.add(initialQuantitySpinner, 1, 4);
        GridPane.setHalignment(initialQuantityLabel, HPos.RIGHT);
        GridPane.setHalignment(initialQuantitySpinner, HPos.LEFT);

        container.add(expirationDateLabel, 0, 5);
        container.add(expirationDatePicker, 1, 5);
        GridPane.setHalignment(expirationDateLabel, HPos.RIGHT);
        GridPane.setHalignment(expirationDatePicker, HPos.LEFT);

        container.add(orderedDateLabel, 0, 6);
        container.add(orderedDatePicker, 1, 6);
        GridPane.setHalignment(orderedDateLabel, HPos.RIGHT);
        GridPane.setHalignment(orderedDatePicker, HPos.LEFT);

        container.add(receivedDateLabel, 0, 7);
        container.add(receivedDatePicker, 1, 7);
        GridPane.setHalignment(receivedDateLabel, HPos.RIGHT);
        GridPane.setHalignment(receivedDatePicker, HPos.LEFT);

        buttonContainer.getChildren().addAll(clearFieldsButton/*, addProductButton, closeButton*/);
        container.add(buttonContainer, 0, 8);
        GridPane.setColumnSpan(buttonContainer, 2);

        GridPane.setColumnSpan(errorMessageContainer, 2);
        container.add(errorMessageContainer, 0, 9);

        getDialogPane().setContent(container);
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

        boolean validPurchasePrice = (purchasePriceSpinner.getValue().compareTo(zero) == 1);

        boolean validSalePrice = (salePriceSpinner.getValue().compareTo(zero) == 1);

        boolean validQuantity = (initialQuantitySpinner.getValue() > 0);

        return validName && validPurchasePrice && validSalePrice && validQuantity;
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

    public void setFields(Product product)
    {
        nameTextField.setText(product.getName());
        categoryComboBox.setValue(Category.valueOf(product.getCategory()));
        purchasePriceSpinner.getValueFactory().setValue(product.getPurchasePrice());
        salePriceSpinner.getValueFactory().setValue(product.getSalePrice());
        initialQuantitySpinner.getValueFactory().setValue(product.getInitialQuantity());
        expirationDatePicker.setValue(product.getExpirationDate().toLocalDate());
        orderedDatePicker.setValue(product.getDateOrdered().toLocalDate());
        receivedDatePicker.setValue(product.getDateReceived().toLocalDate());
    }
}