package mutual.views.sale.quickSale;

/*
 * Created by Jonah on 11/19/2016.
 */

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class QuickSaleView extends GridPane
{

    public QuickSaleView()
    {

        initComponents();
        addComponents();
    }

    private void initComponents()
    {

    }

    private void addComponents()
    {
        add(new Label("Hello"), 0, 0);
    }
}