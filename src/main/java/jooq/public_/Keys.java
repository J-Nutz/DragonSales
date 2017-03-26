/**
 * This class is generated by jOOQ
 */
package jooq.public_;


import jooq.public_.tables.Products;
import jooq.public_.tables.records.ProductsRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>PUBLIC</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ProductsRecord, Long> IDENTITY_PRODUCTS = Identities0.IDENTITY_PRODUCTS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ProductsRecord> CONSTRAINT_F = UniqueKeys0.CONSTRAINT_F;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<ProductsRecord, Long> IDENTITY_PRODUCTS = createIdentity(Products.PRODUCTS, Products.PRODUCTS.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ProductsRecord> CONSTRAINT_F = createUniqueKey(Products.PRODUCTS, "CONSTRAINT_F", Products.PRODUCTS.ID);
    }
}
