package mutual.types;

/*
 * Created by Jonah on 1/18/2017.
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class OrderFragment
{
    private SimpleIntegerProperty quantity;
    private SimpleObjectProperty<Product> product;
    private SimpleStringProperty productName;
    private SimpleObjectProperty<BigDecimal> price;
    private SimpleObjectProperty<BigDecimal> discountPrice;

    private boolean hasDiscount;

    public OrderFragment(int quantity, Product product, boolean hasDiscount)
    {
        this.hasDiscount = hasDiscount;

        setQuantity(quantity);
        setProduct(product);
        setProductName(product.getName());
    }

    private SimpleIntegerProperty quantityProperty()
    {
        if(quantity == null)
        {
            quantity = new SimpleIntegerProperty(this, "quantity", 0);
        }
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        quantityProperty().set(quantity);
    }

    public int getQuantity()
    {
        return quantityProperty().get();
    }

    private SimpleObjectProperty<Product> productProperty()
    {
        if(product == null)
        {
            product = new SimpleObjectProperty<>(this, "product", new Product());
        }
        return product;
    }

    public void setProduct(Product product)
    {
        productProperty().set(product);
    }

    public Product getProduct()
    {
        return productProperty().get();
    }

    public SimpleStringProperty productNameProperty()
    {
        if(productName == null)
        {
            productName = new SimpleStringProperty(this, "productName", getProduct().getName());
        }

        return productName;
    }

    public String getProductName()
    {
        return productNameProperty().get();
    }

    public void setProductName(String name)
    {
        productNameProperty().set(name);
    }

    public SimpleObjectProperty<BigDecimal> priceProperty()
    {
        if(price == null)
        {
            price = new SimpleObjectProperty<>(this, "price", getProduct().getPrice());
        }
        return price;
    }

    public BigDecimal getPrice()
    {
        return priceProperty().get();
    }

    public SimpleObjectProperty<BigDecimal> discountPriceProperty()
    {
        if(discountPrice == null)
        {
            discountPrice = new SimpleObjectProperty<>(this, "discountPrice", getProduct().getSalePrice());
        }
        return discountPrice;
    }

    public BigDecimal getDiscountPrice()
    {
        return discountPriceProperty().get();
    }

    public boolean hasDiscount()
    {
        return hasDiscount;
    }
}