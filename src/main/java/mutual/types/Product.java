package mutual.types;

/*
 * Created by Jonah on 11/9/2016.
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class Product
{
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty category;
    private SimpleObjectProperty<BigDecimal> purchasePrice;
    private SimpleObjectProperty<BigDecimal> salePrice;
    private SimpleObjectProperty<BigDecimal> discountPrice;
    private SimpleIntegerProperty initialQuantity;
    private SimpleIntegerProperty currentQuantity;
    private SimpleIntegerProperty totalSold;
    private SimpleObjectProperty<Date> expirationDate;
    private SimpleObjectProperty<Date> dateLastSold;
    private SimpleObjectProperty<Date> dateOrdered;
    private SimpleObjectProperty<Date> dateReceived;

    private SimpleIntegerProperty idProperty() // TODO: Do I Even Need An ID?
    {
        if(id == null)
        {
            id = new SimpleIntegerProperty(this, "id", -1);
        }
        return id;
    }

    public void setId(int id)
    {
        idProperty().set(id);
    }

    public int getId()
    {
        return idProperty().get();
    }

    private SimpleStringProperty nameProperty()
    {
        if(name == null)
        {
            name = new SimpleStringProperty(this, "name", "Product");
        }
        return name;
    }

    public void setName(String name)
    {
        nameProperty().set(name);
    }

    public String getName()
    {
        return nameProperty().get();
    }

    private SimpleStringProperty categoryProperty()
    {
        if(category == null)
        {
            category = new SimpleStringProperty(this, "category", "Null");
        }
        return category;
    }

    public void setCategory(String category)
    {
        categoryProperty().set(category);
    }

    public String getCategory()
    {
        return categoryProperty().get();
    }

    private SimpleObjectProperty<BigDecimal> purchasePriceProperty()
    {
        if(purchasePrice == null)
        {
            purchasePrice = new SimpleObjectProperty<>(this, "purchasePrice", new BigDecimal("0.00"));
        }
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal price)
    {
        purchasePriceProperty().set(price);
    }

    public BigDecimal getPurchasePrice()
    {
        return purchasePriceProperty().get();
    }

    private SimpleObjectProperty<BigDecimal> salePriceProperty()
    {
        if(salePrice == null)
        {
            salePrice = new SimpleObjectProperty<>(this, "salePrice", new BigDecimal("0.00"));
        }
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice)
    {
        salePriceProperty().set(salePrice);
    }

    public BigDecimal getSalePrice()
    {
        return salePriceProperty().get();
    }

    private SimpleObjectProperty<BigDecimal> discountPriceProperty()
    {
        if(discountPrice == null)
        {
            discountPrice = new SimpleObjectProperty<>(this, "discountPrice", new BigDecimal("0.00"));
        }
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice)
    {
        discountPriceProperty().set(discountPrice);
    }

    public BigDecimal getDiscountPrice()
    {
        return discountPriceProperty().get();
    }

    private SimpleIntegerProperty initialQuantityProperty()
    {
        if(initialQuantity == null)
        {
            initialQuantity = new SimpleIntegerProperty(this, "initialQuantity", 0);
        }
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity)
    {
        initialQuantityProperty().set(initialQuantity);
    }

    public int getInitialQuantity()
    {
        return initialQuantityProperty().get();
    }

    private SimpleIntegerProperty currentQuantityProperty()
    {
        if(currentQuantity == null)
        {
            currentQuantity = new SimpleIntegerProperty(this, "currentQuantity", 0);
        }
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity)
    {
        currentQuantityProperty().set(currentQuantity);
    }

    public int getCurrentQuantity()
    {
        return currentQuantityProperty().get();
    }

    private SimpleIntegerProperty totalSoldProperty()
    {
        if(totalSold == null)
        {
            totalSold = new SimpleIntegerProperty(this, "totalSold", 0);
        }
        return totalSold;
    }

    public void setTotalSold(int totalSold)
    {
        totalSoldProperty().set(totalSold);
    }

    public int getTotalSold()
    {
        return totalSoldProperty().get();
    }

    private SimpleObjectProperty<Date> expirationDateProperty()
    {
        if(expirationDate == null)
        {
            expirationDate = new SimpleObjectProperty<>(this, "expirationDate", Date.valueOf(LocalDate.now()));
        }
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
    {
        expirationDateProperty().set(expirationDate);
    }

    public Date getExpirationDate()
    {
        return expirationDateProperty().get();
    }

    public SimpleObjectProperty<Date> dateLastSoldProperty()
    {
        if(dateLastSold == null)
        {
            dateLastSold = new SimpleObjectProperty<>(this, "dateLastSold", Date.valueOf(LocalDate.now()));
        }
        return dateLastSold;
    }

    public void setDateLastSold(Date dateLastSold)
    {
        dateLastSoldProperty().set(dateLastSold);
    }

    public Date getDateLastSold()
    {
        return dateLastSoldProperty().get();
    }

    private SimpleObjectProperty<Date> dateOrderedProperty()
    {
        if(dateOrdered == null)
        {
            dateOrdered = new SimpleObjectProperty<>(this, "dateOrdered", Date.valueOf(LocalDate.now()));
        }
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered)
    {
        dateOrderedProperty().set(dateOrdered);
    }

    public Date getDateOrdered()
    {
        return dateOrderedProperty().get();
    }

    private SimpleObjectProperty<Date> dateReceivedProperty()
    {
        if(dateReceived == null)
        {
            dateReceived = new SimpleObjectProperty<>(this, "dateReceived", Date.valueOf(LocalDate.now()));
        }
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived)
    {
        dateReceivedProperty().set(dateReceived);
    }

    public Date getDateReceived()
    {
        return dateReceivedProperty().get();
    }
}