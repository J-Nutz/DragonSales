package database.tables;

/*
 * Created by Jonah on 2/8/2017.
 */

import database.DatabaseExecutor;
import database.data.ProductsHeld;
import jooq.public_.tables.Discounts;
import jooq.public_.tables.records.DiscountsRecord;
import mutual.types.Discount;
import mutual.types.Repeat;
import org.jooq.*;
import org.jooq.util.h2.H2DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.util.ArrayList;

import static org.jooq.impl.DSL.row;

public class DiscountsTable
{
    //TODO: Give ID Or Something - On Edit/Delete, edits/deletes all products with same name

    private static final Discounts discounts = Discounts.DISCOUNTS;

    public DiscountsTable() {}

    public static boolean addDiscount(Discount discount)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                InsertValuesStep9<DiscountsRecord,
                        String,
                        BigDecimal,
                        BigDecimal,
                        Boolean,
                        Boolean,
                        Boolean,
                        Boolean,
                        Boolean,
                        String>

                        addDiscountStep = database
                        .insertInto(discounts,
                                discounts.PRODUCT,
                                discounts.OLD_PRICE,
                                discounts.DISCOUNT_PRICE,
                                discounts.MONDAY,
                                discounts.TUESDAY,
                                discounts.WEDNESDAY,
                                discounts.THURSDAY,
                                discounts.FRIDAY,
                                discounts.REPEAT);

                addDiscountStep
                        .values(discount.getProductName(),
                                discount.getOldPrice(),
                                discount.getDiscountPrice(),
                                discount.getDayOfSale(DayOfWeek.MONDAY),
                                discount.getDayOfSale(DayOfWeek.TUESDAY),
                                discount.getDayOfSale(DayOfWeek.WEDNESDAY),
                                discount.getDayOfSale(DayOfWeek.THURSDAY),
                                discount.getDayOfSale(DayOfWeek.FRIDAY),
                                discount.getRepeat().toString());

                int addDiscountResult = addDiscountStep.execute();

                return addDiscountResult == 1;
            }
        });
    }

    public static Discount getDiscount(String product)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedDiscount =
                        database.select()
                                .from(discounts)
                                .where(discounts.PRODUCT.equal(product))
                                .fetch();

                if(fetchedDiscount.isNotEmpty())
                {
                    Discount discount = new Discount();

                    for(Record r : fetchedDiscount)
                    {
                        discount.setProduct(ProductsHeld.getProduct(r.get(discounts.PRODUCT)));
                        //discount.setProduct(ProductsTable.getProduct(r.get(discounts.PRODUCT)));
                        discount.setOldPrice(r.get(discounts.OLD_PRICE));
                        discount.setDiscountPrice(r.get(discounts.DISCOUNT_PRICE));
                        discount.setDayOfDiscount(DayOfWeek.MONDAY, r.get(discounts.MONDAY));
                        discount.setDayOfDiscount(DayOfWeek.TUESDAY, r.get(discounts.TUESDAY));
                        discount.setDayOfDiscount(DayOfWeek.WEDNESDAY, r.get(discounts.WEDNESDAY));
                        discount.setDayOfDiscount(DayOfWeek.THURSDAY, r.get(discounts.THURSDAY));
                        discount.setDayOfDiscount(DayOfWeek.FRIDAY, r.get(discounts.FRIDAY));
                        discount.setRepeat(Repeat.valueOf(r.get(discounts.REPEAT)));
                    }

                    return discount;
                }
                else
                {
                    return null;
                }
            }
        });
    }

    public static boolean editDiscount(String discountToUpdate, Discount discount)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                int result =
                        database.update(discounts)
                                .set(row(discounts.PRODUCT,
                                        discounts.OLD_PRICE,
                                        discounts.DISCOUNT_PRICE,
                                        discounts.MONDAY,
                                        discounts.TUESDAY,
                                        discounts.WEDNESDAY,
                                        discounts.THURSDAY,
                                        discounts.FRIDAY,
                                        discounts.REPEAT),

                                    row(discount.getProductName(),
                                        discount.getOldPrice(),
                                        discount.getDiscountPrice(),
                                        discount.getDayOfSale(DayOfWeek.MONDAY),
                                        discount.getDayOfSale(DayOfWeek.TUESDAY),
                                        discount.getDayOfSale(DayOfWeek.WEDNESDAY),
                                        discount.getDayOfSale(DayOfWeek.THURSDAY),
                                        discount.getDayOfSale(DayOfWeek.FRIDAY),
                                        discount.getRepeat().toString()))

                                .where(discounts.PRODUCT.equal(discountToUpdate))
                                .execute();

                return result == 1;
            }
        });
    }

    public static boolean deleteDiscount(String discountToDelete)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                int discountDeleted =
                        database.deleteFrom(discounts)
                                .where(discounts.PRODUCT.equal(discountToDelete))
                                .execute();

                return discountDeleted == 1;
            }
        });
    }

    public static ArrayList<Discount> getDiscounts()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedDiscounts =
                        database.select()
                                .from(discounts)
                                .fetch();

                ArrayList<Discount> discountsList = new ArrayList<>();

                for (Record r : fetchedDiscounts)
                {
                    Discount discount = new Discount();

                    discount.setProduct(ProductsHeld.getProduct(r.get(discounts.PRODUCT)));
                    //discount.setProduct(ProductsTable.getProduct(r.get(discounts.PRODUCT)));
                    discount.setOldPrice(r.get(discounts.OLD_PRICE));
                    discount.setDiscountPrice(r.get(discounts.DISCOUNT_PRICE));
                    discount.setDayOfDiscount(DayOfWeek.MONDAY, r.get(discounts.MONDAY));
                    discount.setDayOfDiscount(DayOfWeek.TUESDAY, r.get(discounts.TUESDAY));
                    discount.setDayOfDiscount(DayOfWeek.WEDNESDAY, r.get(discounts.WEDNESDAY));
                    discount.setDayOfDiscount(DayOfWeek.THURSDAY, r.get(discounts.THURSDAY));
                    discount.setDayOfDiscount(DayOfWeek.FRIDAY, r.get(discounts.FRIDAY));
                    discount.setRepeat(Repeat.valueOf(r.get(discounts.REPEAT)));

                    discountsList.add(discount);
                }

                return discountsList;
            }
        });
    }

    public static ArrayList<String> getProductsWithDiscount()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record1<String>> fetchedProductsOnDiscounts =
                        database.select(discounts.PRODUCT)
                                .from(discounts)
                                .fetch();

                ArrayList<String> productsOnDiscountsList = new ArrayList<>();

                for (Record1<String> r : fetchedProductsOnDiscounts)
                {
                    String productName = r.get(discounts.PRODUCT);
                    productsOnDiscountsList.add(productName);
                }

                return productsOnDiscountsList;
            }
        });
    }

    public static ArrayList<Discount> getDiscountsLike(String like)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
             DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedDiscounts =
                     database.select()
                             .from(discounts)
                             .where(discounts.PRODUCT.like(like + "%"))
                             .fetch();

                ArrayList<Discount> discountsList = new ArrayList<>();

                for(Record r : fetchedDiscounts)
                {
                    Discount discount = new Discount();

                    //discount.setProduct(ProductsHeld.getProduct(r.get(discounts.PRODUCT)));
                    discount.setProduct(ProductsTable.getProduct(r.get(discounts.PRODUCT)));
                    discount.setOldPrice(r.get(discounts.OLD_PRICE));
                    discount.setDiscountPrice(r.get(discounts.DISCOUNT_PRICE));
                    discount.setDayOfDiscount(DayOfWeek.MONDAY, r.get(discounts.MONDAY));
                    discount.setDayOfDiscount(DayOfWeek.TUESDAY, r.get(discounts.TUESDAY));
                    discount.setDayOfDiscount(DayOfWeek.WEDNESDAY, r.get(discounts.WEDNESDAY));
                    discount.setDayOfDiscount(DayOfWeek.THURSDAY, r.get(discounts.THURSDAY));
                    discount.setDayOfDiscount(DayOfWeek.FRIDAY, r.get(discounts.FRIDAY));
                    discount.setRepeat(Repeat.valueOf(r.get(discounts.REPEAT)));

                    discountsList.add(discount);
                }

                return discountsList;
            }
        });
    }
}