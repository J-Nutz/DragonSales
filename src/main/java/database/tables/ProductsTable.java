package database.tables;

/*
 * Created by Jonah on 11/11/2016.
 */

import database.DatabaseExecutor;
import jooq.public_.tables.Products;
import jooq.public_.tables.records.ProductsRecord;
import mutual.types.Product;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.util.h2.H2DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.row;

public class ProductsTable
{
    private static final Products products = Products.PRODUCTS;

    public ProductsTable() {}

    public static boolean addProduct(Product product)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                InsertValuesStep12<ProductsRecord,
                                        String, String,
                                        BigDecimal, BigDecimal, BigDecimal,
                                        Integer, Integer, Integer,
                                        Date, Date, Date, Date>

                        addProductStep = database.insertInto(products,
                                                             products.NAME,
                                                             products.CATEGORY,
                                                             products.PURCHASE_PRICE,
                                                             products.PRICE,
                                                             products.DISCOUNT_PRICE,
                                                             products.INIT_QUANTITY,
                                                             products.CUR_QUANTITY,
                                                             products.TOTAL_SOLD,
                                                             products.EXPIRATION_DATE,
                                                             products.LAST_SOLD_DATE,
                                                             products.ORDERED_DATE,
                                                             products.RECEIVED_DATE);

                addProductStep.values(product.getName(),
                                      product.getCategory(),
                                      product.getPurchasePrice(),
                                      product.getSalePrice(),
                                      product.getDiscountPrice(),
                                      product.getInitialQuantity(),
                                      product.getCurrentQuantity(),
                                      product.getTotalSold(),
                                      product.getExpirationDate(),
                                      product.getDateLastSold(),
                                      product.getDateOrdered(),
                                      product.getDateReceived());

                int addUserResult = addProductStep.execute();

                return addUserResult == 1;
            }
        });
    }

    public static Product getProduct(String productName)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedProduct =
                        database.select()
                                .from(products)
                                .where(products.NAME.equal(productName))
                                .fetch();

                if(fetchedProduct.isNotEmpty())
                {
                    Product product = new Product();

                    for (Record r : fetchedProduct)
                    {
                        product.setName(r.get(products.NAME));
                        product.setCategory(r.get(products.CATEGORY));
                        product.setPurchasePrice(r.get(products.PURCHASE_PRICE));
                        product.setSalePrice(r.get(products.PRICE));
                        product.setDiscountPrice(r.get(products.DISCOUNT_PRICE));
                        product.setInitialQuantity(r.get(products.INIT_QUANTITY));
                        product.setCurrentQuantity(r.get(products.CUR_QUANTITY));
                        product.setTotalSold(r.get(products.TOTAL_SOLD));
                        product.setExpirationDate(r.get(products.EXPIRATION_DATE));
                        product.setDateLastSold(r.get(products.LAST_SOLD_DATE));
                        product.setDateOrdered(r.get(products.ORDERED_DATE));
                        product.setDateReceived(r.get(products.RECEIVED_DATE));
                    }

                    return product;
                }
                else
                {
                    return null;
                }
            }
        });
    }

    public static boolean updateProduct(String productToUpdate, Product newValues)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                int result =
                        database.update(products)
                                .set(row(products.NAME,
                                         products.CATEGORY,
                                         products.PURCHASE_PRICE,
                                         products.PRICE,
                                         products.DISCOUNT_PRICE,
                                         products.INIT_QUANTITY,
                                         products.CUR_QUANTITY,
                                         products.TOTAL_SOLD,
                                         products.EXPIRATION_DATE,
                                         products.LAST_SOLD_DATE,
                                         products.ORDERED_DATE,
                                         products.RECEIVED_DATE),

                                     row(newValues.getName(),
                                         newValues.getCategory(),
                                         newValues.getPurchasePrice(),
                                         newValues.getSalePrice(),
                                         newValues.getDiscountPrice(),
                                         newValues.getInitialQuantity(),
                                         newValues.getCurrentQuantity(),
                                         newValues.getTotalSold(),
                                         newValues.getExpirationDate(),
                                         newValues.getDateLastSold(),
                                         newValues.getDateOrdered(),
                                         newValues.getDateReceived())
                                )
                                .where(products.NAME.equal(productToUpdate))
                                .execute();

                return result == 1;
            }
        });
    }

    public static boolean deleteProduct(String productToDelete)
{
    return DatabaseExecutor.submitBoolean(() ->
    {
        try(Connection connection = DatabaseExecutor.getConnection();
            DSLContext database = H2DSL.using(connection))
        {
            int productDeleted =
                    database.deleteFrom(products)
                            .where(products.NAME.equal(productToDelete))
                            .execute();

            return productDeleted == 1;
        }
    });
}

    public static ArrayList<Product> getProducts()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedProducts =
                        database.select()
                                .from(products)
                                .fetch();

                if(fetchedProducts.isNotEmpty())
                {
                    ArrayList<Product> productsList = new ArrayList<>();

                    for(Record record : fetchedProducts)
                    {
                        Product product = new Product();
                        product.setName(record.get(products.NAME));
                        product.setCategory(record.get(products.CATEGORY));
                        product.setPurchasePrice(record.get(products.PURCHASE_PRICE));
                        product.setSalePrice(record.get(products.PRICE));
                        product.setDiscountPrice(record.get(products.DISCOUNT_PRICE));
                        product.setInitialQuantity(record.get(products.INIT_QUANTITY));
                        product.setCurrentQuantity(record.get(products.CUR_QUANTITY));
                        product.setTotalSold(record.get(products.TOTAL_SOLD));
                        product.setExpirationDate(record.get(products.EXPIRATION_DATE));
                        product.setDateLastSold(record.get(products.LAST_SOLD_DATE));
                        product.setDateOrdered(record.get(products.ORDERED_DATE));
                        product.setDateReceived(record.get(products.RECEIVED_DATE));

                        productsList.add(product);
                    }

                    System.out.println("Returning Products");

                    return productsList;
                }
                else
                {
                    return null;
                }
            }
            catch(DataAccessException dae)
            {
                System.out.println("What");
                return null;
            }
        });
    }

    public static ArrayList<String> getProductNames()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
             DSLContext database = H2DSL.using(connection))
            {
                Result<Record1<String>> fetchedNames =
                     database.select(products.NAME)
                             .from(products)
                             .fetch();

                if(fetchedNames.isNotEmpty())
                {
                    return fetchedNames.stream()
                                       .map(Record1<String>::value1)
                                       .collect(Collectors.toCollection(ArrayList::new));
                }
                else
                {
                    ArrayList<String> empty = new ArrayList<String>();
                    empty.add("Empty");

                    return empty;
                }
            }
        });
    }

    public static ArrayList<Product> getProductsInCategory(String category)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedProducts =
                        database.select()
                                .from(products)
                                .where(products.CATEGORY.equal(category))
                                .fetch();

                ArrayList<Product> productsList = new ArrayList<>();

                for (Record record : fetchedProducts)
                {
                    Product product = new Product();
                    product.setName(record.get(products.NAME));
                    product.setCategory(record.get(products.CATEGORY));
                    product.setPurchasePrice(record.get(products.PURCHASE_PRICE));
                    product.setSalePrice(record.get(products.PRICE));
                    product.setDiscountPrice(record.get(products.DISCOUNT_PRICE));
                    product.setInitialQuantity(record.get(products.INIT_QUANTITY));
                    product.setCurrentQuantity(record.get(products.CUR_QUANTITY));
                    product.setTotalSold(record.get(products.TOTAL_SOLD));
                    product.setExpirationDate(record.get(products.EXPIRATION_DATE));
                    product.setDateLastSold(record.get(products.LAST_SOLD_DATE));
                    product.setDateOrdered(record.get(products.ORDERED_DATE));
                    product.setDateReceived(record.get(products.RECEIVED_DATE));

                    productsList.add(product);
                }

                return productsList;
            }
        });
    }

    public static ArrayList<Product> getProductsLike(String like)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedProducts =
                        database.select()
                                .from(products)
                                .where(products.NAME.like(like + "%"))
                                .fetch();

                ArrayList<Product> productsList = new ArrayList<>();

                for (Record record : fetchedProducts)
                {
                    Product product = new Product();
                    product.setName(record.get(products.NAME));
                    product.setCategory(record.get(products.CATEGORY));
                    product.setPurchasePrice(record.get(products.PURCHASE_PRICE));
                    product.setSalePrice(record.get(products.PRICE));
                    product.setDiscountPrice(record.get(products.DISCOUNT_PRICE));
                    product.setInitialQuantity(record.get(products.INIT_QUANTITY));
                    product.setCurrentQuantity(record.get(products.CUR_QUANTITY));
                    product.setTotalSold(record.get(products.TOTAL_SOLD));
                    product.setExpirationDate(record.get(products.EXPIRATION_DATE));
                    product.setDateLastSold(record.get(products.LAST_SOLD_DATE));
                    product.setDateOrdered(record.get(products.ORDERED_DATE));
                    product.setDateReceived(record.get(products.RECEIVED_DATE));

                    productsList.add(product);
                }

                return productsList;
            }
        });
    }

    public static ArrayList<Product> getProductsLikeAndInCategory(String like, String category)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedProducts =
                        database.select()
                                .from(products)
                                .where(products.NAME.like(like + "%").and(products.CATEGORY.equal(category)))
                                .fetch();

                ArrayList<Product> productsList = new ArrayList<>();

                for (Record record : fetchedProducts)
                {
                    Product product = new Product();
                    product.setName(record.get(products.NAME));
                    product.setCategory(record.get(products.CATEGORY));
                    product.setPurchasePrice(record.get(products.PURCHASE_PRICE));
                    product.setSalePrice(record.get(products.PRICE));
                    product.setDiscountPrice(record.get(products.DISCOUNT_PRICE));
                    product.setInitialQuantity(record.get(products.INIT_QUANTITY));
                    product.setCurrentQuantity(record.get(products.CUR_QUANTITY));
                    product.setTotalSold(record.get(products.TOTAL_SOLD));
                    product.setExpirationDate(record.get(products.EXPIRATION_DATE));
                    product.setDateLastSold(record.get(products.LAST_SOLD_DATE));
                    product.setDateOrdered(record.get(products.ORDERED_DATE));
                    product.setDateReceived(record.get(products.RECEIVED_DATE));

                    productsList.add(product);
                }

                return productsList;
            }
        });
    }

    public static Integer getAmountSold(String productName)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
             DSLContext database = H2DSL.using(connection))
            {
                Record1<Integer> fetchedAmount =
                     database.select(products.TOTAL_SOLD)
                             .from(products)
                             .where(products.NAME.equal(productName))
                             .fetchOne();

                return fetchedAmount.value1();
            }
        });
    }

    public static HashMap<String, Integer> getProductsAndAmountsSold()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
             DSLContext database = H2DSL.using(connection))
            {
                HashMap<String, Integer> namesAndAmounts = new HashMap<>();

                Result<Record2<String, Integer>> namesAndAmountsResult = database.select(products.NAME, products.TOTAL_SOLD)
                        .from(products)
                        .fetch();

                if(namesAndAmountsResult.isNotEmpty())
                {
                    for(Record2<String, Integer> record2 : namesAndAmountsResult)
                    {
                        namesAndAmounts.put(record2.value1(), record2.value2());
                    }

                    return namesAndAmounts;
                }
                else
                {
                    namesAndAmounts.put("Empty", 0);

                    return namesAndAmounts;
                }
            }
        });
    }

    public static boolean updateAmountAndDateSold(String productName, int amountToAdd)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
              DSLContext database = H2DSL.using(connection))
            {
                int currentAmount = getAmountSold(productName);

                int result =
                      database.update(products)
                              .set(row(products.TOTAL_SOLD,
                                       products.LAST_SOLD_DATE),
                                   row((currentAmount + amountToAdd),
                                       Date.valueOf(LocalDate.now())))
                              .where(products.NAME.equal(productName))
                              .execute();

                return result == 1;
            }
        });
    }

}