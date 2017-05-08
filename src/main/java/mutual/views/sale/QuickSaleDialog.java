package mutual.views.sale;

/*
 * Created by Jonah on 5/5/2017.
 */

import database.tables.DiscountsTable;
import database.tables.ProductsTable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.models.order.OrderTableModel;
import mutual.types.Discount;
import mutual.types.OrderFragment;
import mutual.types.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class QuickSaleDialog extends Dialog<ObservableList<OrderFragment>>
{
    private static BigDecimal total;
    private static BigDecimal returnAmount;
    private DayOfWeek dayOfWeek;
    private Product selectedProduct;

    private HBox contentContainer;

    private ScrollPane productResultsScrollPane;
    private GridPane productSelectorPane;
    private TextField productSearchField;
    private Button searchBtn;
    private TilePane productResultsPane;
    private Label selectedLabel;
    private Label productLabel;
    private Label quantityLabel;
    private Spinner<Integer> quantitySpinner;
    private Button addBtn;
    private Button clearBtn;



    private GridPane checkoutBox;
    private TableView<OrderFragment> purchasesTable;
    private static Label totalLabel;
    private Label amountPaidLabel;
    private Spinner<BigDecimal> amountPaidSpinner;
    private static Label returnAmountLabel;

    public QuickSaleDialog()
    {
        returnAmount = new BigDecimal("0.00");
        total = new BigDecimal("0.00");
        dayOfWeek = LocalDate.now().getDayOfWeek();

        contentContainer = new HBox(15);

        productResultsScrollPane = new ScrollPane();
        productSelectorPane = new GridPane();
        productSearchField = new TextField();
        searchBtn = new Button("Search");
        productResultsPane = new TilePane(5, 5);
        selectedLabel = new Label("Selected:");
        productLabel = new Label();
        quantityLabel = new Label("Quantity:");
        quantitySpinner = new Spinner<>(0, 99, 1, 1);
        addBtn = new Button("Add");
        clearBtn = new Button("Clear");

        checkoutBox = new GridPane();
        purchasesTable = new TableView<>();
        totalLabel = new Label("Total: $0.00");
        amountPaidLabel = new Label("Paid:");
        amountPaidSpinner = new Spinner<>();
        returnAmountLabel = new Label("Remaining: $0.00");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setTitle("Quick Sale");
        setResultConverter(param -> purchasesTable.getItems());

        productSelectorPane.setVgap(10);
        productSelectorPane.setHgap(10);

        productSearchField.setPromptText("Search...");

        productResultsScrollPane.setContent(productResultsPane);
        productResultsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        productResultsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        productResultsScrollPane.setMinHeight(120);
        productResultsScrollPane.setMinWidth(220);
        productResultsScrollPane.setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));

        productResultsPane.setPrefColumns(3);
        productResultsPane.setPadding(new Insets(5));

        quantitySpinner.setMaxWidth(60);

        searchBtn.setDefaultButton(true);
        searchBtn.setOnAction(event -> setProducts(ProductsTable.getProductsLike(productSearchField.getText())));

        checkoutBox.setHgap(5);
        checkoutBox.setVgap(5);

        OrderTableModel orderTableModel = new OrderTableModel();
        orderTableModel.useQuickSaleAction(true);
        purchasesTable.getColumns().addAll(orderTableModel.getTableColumns());
        setProducts(ProductsTable.getProducts());
        purchasesTable.getColumns().addListener(new ListChangeListener<TableColumn>()
        {
            public boolean suspended;

            @Override
            public void onChanged(Change change)
            {
                change.next();

                if(change.wasReplaced() && !suspended)
                {
                    this.suspended = true;

                    purchasesTable.getColumns().setAll(orderTableModel.getTableColumns());

                    this.suspended = false;
                }
            }
        });

        addBtn.setOnAction(event ->
        {
            if(selectedProduct != null)
            {
                OrderFragment orderFragment;

                try
                {
                    Discount discount = DiscountsTable.getDiscount(selectedProduct.getName());
                    boolean hasDiscount = discount.getDayOfSale(dayOfWeek);

                    orderFragment = new OrderFragment(quantitySpinner.getValue(), selectedProduct, hasDiscount);
                }
                catch(NullPointerException npe)
                {
                    orderFragment = new OrderFragment(quantitySpinner.getValue(), selectedProduct, false);
                }

                purchasesTable.getItems().add(orderFragment);
                clearBtn.fire();

                calculateTotal(purchasesTable.getItems());
                amountPaidSpinner.getValueFactory().setValue(new BigDecimal("0.00"));
                returnAmountLabel.setText("Remaining: $" + total.toString());
                returnAmountLabel.setTextFill(Color.RED);
            }
        });

        clearBtn.setOnAction(event ->
        {
            setProducts(ProductsTable.getProducts());
            productSearchField.setText("");
            selectedProduct = null;
            productLabel.setText("");
            quantitySpinner.getValueFactory().setValue(1);
        });

        purchasesTable.setPrefHeight(300);

        amountPaidSpinner.setValueFactory(new SpinnerValueFactory<BigDecimal>()
        {
            BigDecimal step = new BigDecimal("0.25");

            @Override
            public void decrement(int steps)
            {
                BigDecimal currentValue = this.getValue();

                while(steps > 0)
                {
                    if(currentValue.compareTo(BigDecimal.ZERO) > 0)
                    {
                        currentValue = currentValue.subtract(step);
                        returnAmount = returnAmount.add(step);

                        setReturnLabelText();
                        this.setValue(currentValue.setScale(2, RoundingMode.CEILING));
                    }
                    steps--;
                }
            }

            @Override
            public void increment(int steps)
            {
                BigDecimal currentValue = this.getValue();

                while(steps > 0)
                {
                    if(currentValue.compareTo(BigDecimal.TEN) < 0)
                    {
                        currentValue = currentValue.add(step);
                        returnAmount = returnAmount.subtract(step);

                        setReturnLabelText();
                        this.setValue(currentValue.setScale(2, RoundingMode.CEILING));
                    }
                    steps--;
                }
            }
        });
        amountPaidSpinner.getValueFactory().setValue(new BigDecimal("0.00"));

        returnAmountLabel.setTextFill(Color.GREEN);
    }

    private void addComponents()
    {
        GridPane.setHalignment(clearBtn, HPos.RIGHT);
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(7);
        gridPane.add(selectedLabel, 0, 0);
        gridPane.add(productLabel, 1, 0);
        gridPane.add(quantityLabel, 0, 1);
        gridPane.add(quantitySpinner, 1, 1);
        gridPane.add(addBtn, 0, 2);
        gridPane.add(clearBtn, 1, 2);

        GridPane.setColumnSpan(productResultsScrollPane, 2);
        GridPane.setHalignment(searchBtn, HPos.RIGHT);
        productSelectorPane.add(productSearchField, 0, 0);
        productSelectorPane.add(searchBtn, 1, 0);
        productSelectorPane.add(productResultsScrollPane, 0, 1);
        productSelectorPane.add(gridPane, 2, 1);

        GridPane.setColumnSpan(purchasesTable, 2);
        GridPane.setColumnSpan(totalLabel, 2);
        GridPane.setColumnSpan(returnAmountLabel, 2);
        checkoutBox.add(purchasesTable, 0, 0);
        checkoutBox.add(totalLabel, 0, 1);
        checkoutBox.add(amountPaidLabel, 0, 2);
        checkoutBox.add(amountPaidSpinner, 1, 2);
        checkoutBox.add(returnAmountLabel, 0, 3);

        contentContainer.getChildren().addAll(productSelectorPane, checkoutBox);

        getDialogPane().setContent(contentContainer);
    }

    private void setProducts(ArrayList<Product> products)
    {
        productResultsPane.getChildren().clear();

        for(Product product : products)
        {
            VBox productContainer = new VBox(5);
            Label productName = new Label(product.getName());
            Label productPrice = new Label();

            try
            {
                Discount discount = DiscountsTable.getDiscount(product.getName());
                if(discount.getDayOfSale(LocalDate.now().getDayOfWeek()))
                {
                    productPrice.setText("$" + product.getDiscountPrice().toString());
                }
                else
                {
                    productPrice.setText("$" + product.getSalePrice().toString());
                }
            }
            catch(NullPointerException npe)
            {
                productPrice.setText("$" + product.getSalePrice().toString());
            }

            productContainer.setPadding(new Insets(2));
            productContainer.setSpacing(3);
            productContainer.setAlignment(Pos.CENTER);
            productContainer.setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            productContainer.setOnMouseEntered(event -> productContainer.setCursor(Cursor.HAND));
            productContainer.setOnMouseExited(event -> productContainer.setCursor(Cursor.DEFAULT));
            productContainer.setOnMouseClicked(event ->
            {
                selectedProduct = ProductsTable.getProduct(product.getName());
                productLabel.setText(product.getName());
            });

            productName.setFont(new Font(13));
            productPrice.setFont(new Font(12));

            productContainer.getChildren().addAll(productName, productPrice);
            productResultsPane.getChildren().add(productContainer);
        }
    }

    public static void calculateTotal(ObservableList<OrderFragment> orderFragments)
    {
        total = new BigDecimal("0.00");

        for(OrderFragment orderFragment : orderFragments)
        {
            if(orderFragment.hasDiscount())
            {
                total = total.add(orderFragment.getDiscountPrice().multiply(new BigDecimal(orderFragment.getQuantity())));
            }
            else
            {
                total = total.add(orderFragment.getSalePrice().multiply(new BigDecimal(orderFragment.getQuantity())));
            }
        }

        total = total.setScale(2, RoundingMode.CEILING);
        returnAmount = total;

        returnAmountLabel.setText("Remaining: $" + returnAmount.toString());
        totalLabel.setText("Total: $" + total.toString());

        if(returnAmount.compareTo(BigDecimal.ZERO) == 0)
        {
            returnAmountLabel.setTextFill(Color.GREEN);
        }
    }

    private void setReturnLabelText()
    {
        if(returnAmount.compareTo(BigDecimal.ZERO) == -1)
        {
            returnAmountLabel.setText("Return: $" + returnAmount.toString());
            returnAmountLabel.setTextFill(Color.GREEN);
        }
        else if(returnAmount.compareTo(BigDecimal.ZERO) == 0)
        {
            returnAmountLabel.setText("Remaining: $" + returnAmount.toString());
            returnAmountLabel.setTextFill(Color.GREEN);
        }
        else
        {
            returnAmountLabel.setText("Remaining: $" + returnAmount.toString());
            returnAmountLabel.setTextFill(Color.RED);
        }
    }
}