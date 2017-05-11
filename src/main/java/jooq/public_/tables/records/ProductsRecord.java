/**
 * This class is generated by jOOQ
 */
package jooq.public_.tables.records;


import jooq.public_.tables.Products;

import java.math.BigDecimal;
import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.TableRecordImpl;


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
public class ProductsRecord extends TableRecordImpl<ProductsRecord> implements Record12<String, String, BigDecimal, BigDecimal, BigDecimal, Integer, Integer, Integer, Date, Date, Date, Date> {

    private static final long serialVersionUID = -2146750316;

    /**
     * Setter for <code>PUBLIC.PRODUCTS.NAME</code>.
     */
    public void setName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.NAME</code>.
     */
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.CATEGORY</code>.
     */
    public void setCategory(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.CATEGORY</code>.
     */
    public String getCategory() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.PURCHASE_PRICE</code>.
     */
    public void setPurchasePrice(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.PURCHASE_PRICE</code>.
     */
    public BigDecimal getPurchasePrice() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.PRICE</code>.
     */
    public void setPrice(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.PRICE</code>.
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.DISCOUNT_PRICE</code>.
     */
    public void setDiscountPrice(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.DISCOUNT_PRICE</code>.
     */
    public BigDecimal getDiscountPrice() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.CUR_QUANTITY</code>.
     */
    public void setCurQuantity(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.CUR_QUANTITY</code>.
     */
    public Integer getCurQuantity() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.INIT_QUANTITY</code>.
     */
    public void setInitQuantity(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.INIT_QUANTITY</code>.
     */
    public Integer getInitQuantity() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.TOTAL_SOLD</code>.
     */
    public void setTotalSold(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.TOTAL_SOLD</code>.
     */
    public Integer getTotalSold() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.EXPIRATION_DATE</code>.
     */
    public void setExpirationDate(Date value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.EXPIRATION_DATE</code>.
     */
    public Date getExpirationDate() {
        return (Date) get(8);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.LAST_SOLD_DATE</code>.
     */
    public void setLastSoldDate(Date value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.LAST_SOLD_DATE</code>.
     */
    public Date getLastSoldDate() {
        return (Date) get(9);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.ORDERED_DATE</code>.
     */
    public void setOrderedDate(Date value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.ORDERED_DATE</code>.
     */
    public Date getOrderedDate() {
        return (Date) get(10);
    }

    /**
     * Setter for <code>PUBLIC.PRODUCTS.RECEIVED_DATE</code>.
     */
    public void setReceivedDate(Date value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.PRODUCTS.RECEIVED_DATE</code>.
     */
    public Date getReceivedDate() {
        return (Date) get(11);
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, BigDecimal, BigDecimal, BigDecimal, Integer, Integer, Integer, Date, Date, Date, Date> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, BigDecimal, BigDecimal, BigDecimal, Integer, Integer, Integer, Date, Date, Date, Date> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Products.PRODUCTS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Products.PRODUCTS.CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return Products.PRODUCTS.PURCHASE_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field4() {
        return Products.PRODUCTS.PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return Products.PRODUCTS.DISCOUNT_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Products.PRODUCTS.CUR_QUANTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Products.PRODUCTS.INIT_QUANTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Products.PRODUCTS.TOTAL_SOLD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field9() {
        return Products.PRODUCTS.EXPIRATION_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field10() {
        return Products.PRODUCTS.LAST_SOLD_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field11() {
        return Products.PRODUCTS.ORDERED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field12() {
        return Products.PRODUCTS.RECEIVED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCategory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getPurchasePrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value4() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getDiscountPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getCurQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getInitQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getTotalSold();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value9() {
        return getExpirationDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value10() {
        return getLastSoldDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value11() {
        return getOrderedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value12() {
        return getReceivedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value1(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value2(String value) {
        setCategory(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value3(BigDecimal value) {
        setPurchasePrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value4(BigDecimal value) {
        setPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value5(BigDecimal value) {
        setDiscountPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value6(Integer value) {
        setCurQuantity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value7(Integer value) {
        setInitQuantity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value8(Integer value) {
        setTotalSold(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value9(Date value) {
        setExpirationDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value10(Date value) {
        setLastSoldDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value11(Date value) {
        setOrderedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord value12(Date value) {
        setReceivedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsRecord values(String value1, String value2, BigDecimal value3, BigDecimal value4, BigDecimal value5, Integer value6, Integer value7, Integer value8, Date value9, Date value10, Date value11, Date value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductsRecord
     */
    public ProductsRecord() {
        super(Products.PRODUCTS);
    }

    /**
     * Create a detached, initialised ProductsRecord
     */
    public ProductsRecord(String name, String category, BigDecimal purchasePrice, BigDecimal price, BigDecimal discountPrice, Integer curQuantity, Integer initQuantity, Integer totalSold, Date expirationDate, Date lastSoldDate, Date orderedDate, Date receivedDate) {
        super(Products.PRODUCTS);

        set(0, name);
        set(1, category);
        set(2, purchasePrice);
        set(3, price);
        set(4, discountPrice);
        set(5, curQuantity);
        set(6, initQuantity);
        set(7, totalSold);
        set(8, expirationDate);
        set(9, lastSoldDate);
        set(10, orderedDate);
        set(11, receivedDate);
    }
}
