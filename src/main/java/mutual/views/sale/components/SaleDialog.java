package mutual.views.sale.components;

/*
 * Created by Jonah on 1/30/2017.
 */

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import mutual.types.OrderFragment;
import mutual.types.Product;

import java.math.BigDecimal;

public class SaleDialog extends Dialog<OrderFragment>
{
    private Product product;
    private boolean hasDiscount;

    private GridPane container;
    private Label productName;
    private Spinner<Integer> quantitySelector;
    private Label productPrice;
    private ButtonType addToOrderBtn;

    public SaleDialog(Product product, boolean hasDiscount)
    {
        this.product = product;
        this.hasDiscount = hasDiscount;

        container = new GridPane();
        productName = new Label(product.getName());
        quantitySelector = new Spinner<>(1, 99, 1);
        productPrice = new Label();
        addToOrderBtn = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setTitle("Sale Dialog");
        setResultConverter(dialogButton -> dialogButton == addToOrderBtn ? new OrderFragment(quantitySelector.getValue(),
                                                                                     product,
                                                                                     hasDiscount) : null);

        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(15));
        container.setHgap(15);
        container.setVgap(15);

        productName.setFont(new Font(16));

        quantitySelector.setMaxWidth(60);
        quantitySelector.setValueFactory(new SpinnerValueFactory<Integer>()
        {
            @Override
            public void decrement(int steps)
            {
                int step = 1;
                int currentValue = getValue();

                while(steps > 0)
                {
                    if(currentValue > 1)
                    {
                        currentValue = currentValue - step;
                    }
                    steps--;
                }

                this.setValue(currentValue);
                if(hasDiscount)
                {
                    productPrice.setText("$" + product.getDiscountPrice().multiply(new BigDecimal(currentValue)));
                }
                else
                {
                    productPrice.setText("$" + product.getSalePrice().multiply(new BigDecimal(currentValue)));
                }
            }

            @Override
            public void increment(int steps)
            {
                int step = 1;
                int currentValue = getValue();

                while(steps > 0)
                {
                    if(currentValue < 99)
                    {
                        currentValue = currentValue + step;
                    }
                    steps--;
                }

                this.setValue(currentValue);

                if(hasDiscount)
                {
                    productPrice.setText("$" + product.getDiscountPrice().multiply(new BigDecimal(currentValue)));
                }
                else
                {
                    productPrice.setText("$" + product.getSalePrice().multiply(new BigDecimal(currentValue)));
                }
            }
        });
        quantitySelector.getValueFactory().setValue(1);

        if(this.hasDiscount)
        {
            productPrice.setText("$" + product.getDiscountPrice().toString());
        }
        else
        {
            productPrice.setText("$" + product.getSalePrice().toString());
        }
    }

    private void addComponents()
    {
        GridPane.setColumnSpan(productName, 2);
        GridPane.setHalignment(productName, HPos.CENTER);
        container.add(productName, 0, 0);

        container.add(quantitySelector, 0, 1);
        container.add(productPrice, 1, 1);

        getDialogPane().setContent(container);
        getDialogPane().getButtonTypes().addAll(addToOrderBtn, ButtonType.CANCEL);
    }
}