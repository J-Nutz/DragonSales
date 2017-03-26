package database.data;

/*
 * Created by Jonah on 2/13/2017.
 */

import database.tables.ProductsTable;
import mutual.types.Product;

import java.util.ArrayList;

public class ProductsHeld
{
    //TODO: Queue of added products that pushes to db every 10 mins? (And before shutdown)

    public static ArrayList<Product> products;

    static
    {
        products = ProductsTable.getProducts();
    }

    public static ArrayList<Product> getProducts()
    {
        return products;
    }

    public static Product getProduct(String productName)
    {
        Product product = null;

        if(!products.isEmpty())
        {
            for(Product product1 : products)
            {
                if(product1.getName().equals(productName))
                {
                    product = product1;
                }
            }
        }

        return product;
    }

    public static void addProduct(Product product)
    {
        products.add(product);
    }

    public static void removeProduct(Product product)
    {
        products.remove(product);
    }

    public static void updateProducts()
    {
        products = ProductsTable.getProducts();
    }
}