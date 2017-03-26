package mutual.types;

/*
 * Created by Jonah on 2/6/2017.
 */

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.HashMap;

public class Discount
{
    private Product product;
    private BigDecimal discountPrice;
    private BigDecimal oldPrice;
    private BigDecimal percentOff;
    private HashMap<DayOfWeek, Boolean> daysOfDiscount;
    private Repeat repeat;

    public Discount()
    {
        daysOfDiscount = new HashMap<>();
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public String getProductName()
    {
        return this.product.getName();
    }

    public BigDecimal getDiscountPrice()
    {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice)
    {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getOldPrice()
    {
        return this.oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice)
    {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getPercentOff()
    {
        return percentOff;
    }

    public void setPercentOff(BigDecimal percentOff)
    {
        this.percentOff = percentOff;
    }

    public HashMap<DayOfWeek, Boolean> getDaysOfDiscount()
    {
        return daysOfDiscount;
    }

    public void setDaysOfDiscount(HashMap<DayOfWeek, Boolean> daysOfDiscount)
    {
        this.daysOfDiscount = daysOfDiscount;
    }

    public Repeat getRepeat()
    {
        return repeat;
    }

    public void setRepeat(Repeat repeat)
    {
        this.repeat = repeat;
    }

    public boolean getDayOfSale(DayOfWeek dayOfWeek)
    {
        return daysOfDiscount.get(dayOfWeek);
    }

    public void setDayOfDiscount(DayOfWeek dayOfWeek, boolean hasDiscount)
    {
        if(daysOfDiscount == null)
        {
            daysOfDiscount = new HashMap<>();
        }

        daysOfDiscount.put(dayOfWeek, hasDiscount);
    }
}