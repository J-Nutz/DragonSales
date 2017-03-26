package mutual.views.sale.components;

/*
 * Created by Jonah on 11/29/2016.
 */

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import mutual.views.sale.payment.AmountPaidSelector;
import mutual.views.sale.selector.ProductSelectorPanel;

public class SaleControlsPanel extends GridPane
{
    private static Label totalLabel;
    private Button payBtn;
    private Button cancelOrderBtn;

    public SaleControlsPanel()
    {
        totalLabel = new Label("Total: $0.00");
        payBtn = new Button("Pay");
        cancelOrderBtn = new Button("Cancel");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        setVgap(10);
        setHgap(15);

        totalLabel.setFont(new Font(16));

        payBtn.setOnAction(event1 ->
        {
            if(OrderPanel.hasOrders())
            {
                BorderPane parentParent = (BorderPane) getParent().getParent();
                parentParent.setCenter(new AmountPaidSelector());

                BorderPane parent = (BorderPane) getParent();
                parent.setBottom(null);
            }
        });

        cancelOrderBtn.setOnAction(event ->
        {
            OrderPanel.cancelOrder();
            totalLabel.setText("Total: $0.00");

            BorderPane borderPane = (BorderPane) getParent().getParent();
            borderPane.setCenter(new ProductSelectorPanel());
        });
    }

    private void addComponents()
    {
        GridPane.setColumnSpan(totalLabel, 2);;
        GridPane.setHalignment(totalLabel, HPos.CENTER);

        add(totalLabel, 0, 0);
        add(payBtn, 0, 1);
        add(cancelOrderBtn, 1, 1);
    }

    public static void updateTotal()
    {
        totalLabel.setText("Total: $" + OrderPanel.calculateTotal());
    }
}