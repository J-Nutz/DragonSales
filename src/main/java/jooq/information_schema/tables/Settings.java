/**
 * This class is generated by jOOQ
 */
package jooq.information_schema.tables;


import jooq.information_schema.InformationSchema;
import jooq.information_schema.tables.records.SettingsRecord;

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
public class Settings extends TableImpl<SettingsRecord> {

    private static final long serialVersionUID = 1896860294;

    /**
     * The reference instance of <code>INFORMATION_SCHEMA.SETTINGS</code>
     */
    public static final Settings SETTINGS = new Settings();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SettingsRecord> getRecordType() {
        return SettingsRecord.class;
    }

    /**
     * The column <code>INFORMATION_SCHEMA.SETTINGS.NAME</code>.
     */
    public final TableField<SettingsRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.SETTINGS.VALUE</code>.
     */
    public final TableField<SettingsRecord, String> VALUE = createField("VALUE", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

    /**
     * Create a <code>INFORMATION_SCHEMA.SETTINGS</code> table reference
     */
    public Settings() {
        this("SETTINGS", null);
    }

    /**
     * Create an aliased <code>INFORMATION_SCHEMA.SETTINGS</code> table reference
     */
    public Settings(String alias) {
        this(alias, SETTINGS);
    }

    private Settings(String alias, Table<SettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Settings(String alias, Table<SettingsRecord> aliased, Field<?>[] parameters) {
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
    public Settings as(String alias) {
        return new Settings(alias, this);
    }

    /**
     * Rename this table
     */
    public Settings rename(String name) {
        return new Settings(name, null);
    }
}
