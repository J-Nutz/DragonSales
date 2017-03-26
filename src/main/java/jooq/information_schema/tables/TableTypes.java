/**
 * This class is generated by jOOQ
 */
package jooq.information_schema.tables;


import jooq.information_schema.InformationSchema;
import jooq.information_schema.tables.records.TableTypesRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class TableTypes extends TableImpl<TableTypesRecord> {

    private static final long serialVersionUID = 1597768398;

    /**
     * The reference instance of <code>INFORMATION_SCHEMA.TABLE_TYPES</code>
     */
    public static final TableTypes TABLE_TYPES = new TableTypes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TableTypesRecord> getRecordType() {
        return TableTypesRecord.class;
    }

    /**
     * The column <code>INFORMATION_SCHEMA.TABLE_TYPES.TYPE</code>.
     */
    public final TableField<TableTypesRecord, String> TYPE = createField("TYPE", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

    /**
     * Create a <code>INFORMATION_SCHEMA.TABLE_TYPES</code> table reference
     */
    public TableTypes() {
        this("TABLE_TYPES", null);
    }

    /**
     * Create an aliased <code>INFORMATION_SCHEMA.TABLE_TYPES</code> table reference
     */
    public TableTypes(String alias) {
        this(alias, TABLE_TYPES);
    }

    private TableTypes(String alias, Table<TableTypesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TableTypes(String alias, Table<TableTypesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return InformationSchema.INFORMATION_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableTypes as(String alias) {
        return new TableTypes(alias, this);
    }

    /**
     * Rename this table
     */
    public TableTypes rename(String name) {
        return new TableTypes(name, null);
    }
}
