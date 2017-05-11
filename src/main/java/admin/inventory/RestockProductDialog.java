package admin.inventory;

/*
 * Created by Jonah on 5/9/2017.
 */

import javafx.scene.control.Dialog;
import mutual.types.Product;

public class RestockProductDialog extends Dialog<Product>
{
    private Product product;

    public RestockProductDialog(Product product)
    {
        this.product = product;

        initComponents();
        addComponents();
    }

    private void initComponents()
    {

    }

    private void addComponents()
    {

    }

    private void constructProduct()
    {

    }
}