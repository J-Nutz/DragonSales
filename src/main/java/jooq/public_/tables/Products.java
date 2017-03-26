/**
 * This class is generated by jOOQ
 */
package jooq.public_.tables;


import jooq.public_.Keys;
import jooq.public_.Public;
import jooq.public_.tables.records.ProductsRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Products extends TableImpl<ProductsRecord> {

    private static final long serialVersionUID = -1342854466;

    /**
     * The reference instance of <code>PUBLIC.PRODUCTS</code>
     */
    public static final Products PRODUCTS = new Products();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductsRecord> getRecordType() {
        return ProductsRecord.class;
    }

    /**
     * The column <code>PUBLIC.PRODUCTS.ID</code>.
     */
    public final TableField<ProductsRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("(NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_3035F31C_4490_4273_8FBC_2D85ECCE6AE2)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.NAME</code>.
     */
    public final TableField<ProductsRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.CATEGORY</code>.
     */
    public final TableField<ProductsRecord, String> CATEGORY = createField("CATEGORY", org.jooq.impl.SQLDataType.VARCHAR.length(16), this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.PURCHASE_PRICE</code>.
     */
    public final TableField<ProductsRecord, BigDecimal> PURCHASE_PRICE = createField("PURCHASE_PRICE", org.jooq.impl.SQLDataType.DECIMAL.precision(65535, 32767), this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.PRICE</code>.
     */
    public final TableField<ProductsRecord, BigDecimal> PRICE = createField("PRICE", org.jooq.impl.SQLDataType.DECIMAL.precision(65535, 32767), this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.SALE_PRICE</code>.
     */
    public final TableField<ProductsRecord, BigDecimal> SALE_PRICE = createField("SALE_PRICE", org.jooq.impl.SQLDataType.DECIMAL.precision(65535, 32767), this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.CUR_QUANTITY</code>.
     */
    public final TableField<ProductsRecord, Integer> CUR_QUANTITY = createField("CUR_QUANTITY", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.INIT_QUANTITY</code>.
     */
    public final TableField<ProductsRecord, Integer> INIT_QUANTITY = createField("INIT_QUANTITY", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.TOTAL_SOLD</code>.
     */
    public final TableField<ProductsRecord, Integer> TOTAL_SOLD = createField("TOTAL_SOLD", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.EXPIRATION_DATE</code>.
     */
    public final TableField<ProductsRecord, Date> EXPIRATION_DATE = createField("EXPIRATION_DATE", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.LAST_SOLD_DATE</code>.
     */
    public final TableField<ProductsRecord, Date> LAST_SOLD_DATE = createField("LAST_SOLD_DATE", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.ORDERED_DATE</code>.
     */
    public final TableField<ProductsRecord, Date> ORDERED_DATE = createField("ORDERED_DATE", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>PUBLIC.PRODUCTS.RECEIVED_DATE</code>.
     */
    public final TableField<ProductsRecord, Date> RECEIVED_DATE = createField("RECEIVED_DATE", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * Create a <code>PUBLIC.PRODUCTS</code> table reference
     */
    public Products() {
        this("PRODUCTS", null);
    }

    /**
     * Create an aliased <code>PUBLIC.PRODUCTS</code> table reference
     */
    public Products(String alias) {
        this(alias, PRODUCTS);
    }

    private Products(String alias, Table<ProductsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Products(String alias, Table<ProductsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProductsRecord, Long> getIdentity() {
        return Keys.IDENTITY_PRODUCTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProductsRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_F;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProductsRecord>> getKeys() {
        return Arrays.<UniqueKey<ProductsRecord>>asList(Keys.CONSTRAINT_F);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Products as(String alias) {
        return new Products(alias, this);
    }

    /**
     * Rename this table
     */
    public Products rename(String name) {
        return new Products(name, null);
    }
}
