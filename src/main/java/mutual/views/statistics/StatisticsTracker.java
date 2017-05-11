package mutual.views.statistics;

/*
 * Created by Jonah on 3/3/2017.
 */

import database.tables.ProductsTable;
import javafx.collections.ObservableList;
import mutual.types.Category;
import mutual.types.OrderFragment;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class StatisticsTracker
{
    private Date day;
    private int foodSold = 0;
    private int bakerySold = 0;
    private int candySold = 0;
    private int chipsSold = 0;
    private int drinksSold = 0;
    private int sodaSold = 0;
    private int waterSold = 0;
    private int juiceSold = 0;
    private int coffeeSold = 0;
    private int frozenSold = 0;
    private int miscSold = 0;
    private int numOfSales = 0;
    private int numOfItemsSold = 0;
    private BigDecimal totalIncome;
    private BigDecimal totalProfit;

    public StatisticsTracker()
    {
        day = Date.valueOf(LocalDate.now());
        totalIncome = new BigDecimal("0.00");
        totalProfit = new BigDecimal("0.00");
    }

    public void logSale(ObservableList<OrderFragment> orderFragments)
    {
        numOfSales += 1;

        for(OrderFragment orderFragment : orderFragments)
        {
            ProductsTable.updateAmountAndDateSold(orderFragment.getProductName(), orderFragment.getQuantity());

            numOfItemsSold += orderFragment.getQuantity();

            totalIncome = totalIncome.add(calculateIncome(orderFragment));
            totalProfit = totalProfit.add(calculateProfit(orderFragment));

            Category category = Category.valueOf(orderFragment.getProduct().getCategory());

            switch(category)
            {
                case FOOD:
                    foodSold += orderFragment.getQuantity();
                    break;

                case BAKERY:
                    bakerySold += orderFragment.getQuantity();
                    break;

                case CANDY:
                    candySold += orderFragment.getQuantity();
                    break;

                case CHIPS:
                    chipsSold += orderFragment.getQuantity();
                    break;

                case DRINK:
                    drinksSold += orderFragment.getQuantity();
                    break;

                case SODA:
                    sodaSold += orderFragment.getQuantity();
                    break;

                case WATER:
                    waterSold += orderFragment.getQuantity();
                    break;

                case JUICE:
                    juiceSold += orderFragment.getQuantity();
                    break;

                case COFFEE:
                    coffeeSold += orderFragment.getQuantity();
                    break;

                case FROZEN:
                    frozenSold += orderFragment.getQuantity();
                    break;

                case MISC:
                    miscSold += orderFragment.getQuantity();
                    break;

                default:
                    break;
            }
        }
    }

    private BigDecimal calculateIncome(OrderFragment orderFragment)
    {
        BigDecimal income = new BigDecimal("0.00");

        if(orderFragment.hasDiscount())
        {
            income = income.add(orderFragment.getDiscountPrice().multiply(new BigDecimal(orderFragment.getQuantity())));
        }
        else
        {
            income = income.add(orderFragment.getSalePrice().multiply(new BigDecimal(orderFragment.getQuantity())));
        }

        return income;
    }

    private BigDecimal calculateProfit(OrderFragment orderFragment)
    {
        BigDecimal profit = new BigDecimal("0.00");

        if(orderFragment.hasDiscount())
        {
            BigDecimal discountPrice = orderFragment.getDiscountPrice().multiply(BigDecimal.valueOf(orderFragment.getQuantity()));
            BigDecimal purchasePrice = orderFragment.getProduct().getPurchasePrice().multiply(BigDecimal.valueOf(orderFragment.getQuantity()));

            profit = profit.add(discountPrice.subtract(purchasePrice));
        }
        else
        {
            BigDecimal salePrice = orderFragment.getSalePrice().multiply(BigDecimal.valueOf(orderFragment.getQuantity()));
            BigDecimal purchasePrice = orderFragment.getProduct().getPurchasePrice().multiply(BigDecimal.valueOf(orderFragment.getQuantity()));

            profit = profit.add(salePrice.subtract(purchasePrice));
        }

        return profit;
    }

    public int getLargestNumInCategory()
    {
        ArrayList<Integer> possibilities = new ArrayList<>();
        possibilities.add(bakerySold);
        possibilities.add(candySold);
        possibilities.add(chipsSold);
        possibilities.add(drinksSold);
        possibilities.add(sodaSold);
        possibilities.add(waterSold);
        possibilities.add(juiceSold);
        possibilities.add(coffeeSold);
        possibilities.add(frozenSold);
        possibilities.add(miscSold);

        int largest = foodSold;

        for(Integer possibility : possibilities)
        {
            if(largest < possibility)
            {
                largest = possibility;
            }
        }

        return largest;
    }

    public void setDay(Date newValue)
    {
        day = newValue;
    }

    public Date getDay()
    {
        return day;
    }

    public void setFoodSold(int newValue)
    {
        foodSold = newValue;
    }

    public int getFoodSold()
    {
        return foodSold;
    }

    public void setBakerySold(int newValue)
    {
        bakerySold = newValue;
    }

    public int getBakerySold()
    {
        return bakerySold;
    }

    public void setCandySold(int newValue)
    {
        candySold = newValue;
    }

    public int getCandySold()
    {
        return candySold;
    }

    public void setChipsSold(int newValue)
    {
        chipsSold = newValue;
    }

    public int getChipsSold()
    {
        return chipsSold;
    }

    public void setDrinksSold(int newValue)
    {
        drinksSold = newValue;
    }

    public int getDrinksSold()
    {
        return drinksSold;
    }

    public void setSodaSold(int newValue)
    {
        sodaSold = newValue;
    }

    public int getSodaSold()
    {
        return sodaSold;
    }

    public void setWaterSold(int newValue)
    {
        waterSold = newValue;
    }

    public int getWaterSold()
    {
        return waterSold;
    }

    public void setJuiceSold(int newValue)
    {
        juiceSold = newValue;
    }

    public int getJuiceSold()
    {
        return juiceSold;
    }

    public void setCoffeeSold(int newValue)
    {
        coffeeSold = newValue;
    }

    public int getCoffeeSold()
    {
        return coffeeSold;
    }

    public void setFrozenSold(int newValue)
    {
        frozenSold = newValue;
    }

    public int getFrozenSold()
    {
        return frozenSold;
    }

    public void setMiscSold(int newValue)
    {
        miscSold = newValue;
    }

    public int getMiscSold()
    {
        return miscSold;
    }

    public void setNumOfSales(int newValue)
    {
        numOfSales = newValue;
    }

    public int getNumOfSales()
    {
        return numOfSales;
    }

    public void setNumOfItemsSold(int newValue)
    {
        numOfItemsSold = newValue;
    }

    public int getNumOfItemsSold()
    {
        return numOfItemsSold;
    }

    public void setTotalIncome(BigDecimal newValue)
    {
        totalIncome = newValue;
    }

    public BigDecimal getTotalIncome()
    {
        return totalIncome;
    }

    public void setTotalProfit(BigDecimal newValue)
    {
        totalProfit = newValue;
    }

    public BigDecimal getTotalProfit()
    {
        return totalProfit;
    }
}