package database.tables;

/*
 * Created by Jonah on 11/11/2016.
 */

import database.DatabaseExecutor;
import jooq.public_.tables.Products;
import jooq.public_.tables.records.ProductsRecord;
import mutual.types.Product;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep12;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.util.h2.H2DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

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
                                        String,
                                        String,
                                        BigDecimal,
                                        BigDecimal,
                                        BigDecimal,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Date,
                                        Date,
                                        Date,
                                        Date>
                        addProductStep = database.insertInto(products,
                                                             products.NAME,
                                                             products.CATEGORY,
                                                             products.PURCHASE_PRICE,
                                                             products.PRICE,
                                                             products.SALE_PRICE,
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
                        product.setDiscountPrice(r.get(products.SALE_PRICE));
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

    public static boolean updateProduct(String productToUpdate, Product product)
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
                                         products.SALE_PRICE,
                                         products.INIT_QUANTITY,
                                         products.CUR_QUANTITY,
                                         products.TOTAL_SOLD,
                                         products.EXPIRATION_DATE,
                                         products.LAST_SOLD_DATE,
                                         products.ORDERED_DATE,
                                         products.RECEIVED_DATE),

                                     row(product.getName(),
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
                                         product.getDateReceived())
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

                ArrayList<Product> productsList = new ArrayList<>();

                for (Record record : fetchedProducts)
                {
                    Product product = new Product();
                    product.setName(record.get(products.NAME));
                    product.setCategory(record.get(products.CATEGORY));
                    product.setPurchasePrice(record.get(products.PURCHASE_PRICE));
                    product.setSalePrice(record.get(products.PRICE));
                    product.setDiscountPrice(record.get(products.SALE_PRICE));
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
                    product.setDiscountPrice(record.get(products.SALE_PRICE));
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
                    product.setDiscountPrice(record.get(products.SALE_PRICE));
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
                    product.setDiscountPrice(record.get(products.SALE_PRICE));
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
}