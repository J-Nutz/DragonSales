package mutual.views.sale;

/*
 * Created by Jonah on 11/16/2016.
 */

import javafx.scene.layout.BorderPane;
import mutual.views.sale.components.OrderPanel;
import mutual.views.sale.selector.ProductSelectorPanel;

public class SaleView extends BorderPane
{
    ProductSelectorPanel productSelectorPanel;
    OrderPanel orderPanel;

    public SaleView()
    {
        productSelectorPanel = new ProductSelectorPanel();
        orderPanel = new OrderPanel();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {

    }

    private void addComponents()
    {
        setCenter(productSelectorPanel);
        setRight(orderPanel);
    }
}