/**
 * This class is generated by jOOQ
 */
package jooq.information_schema;


import jooq.information_schema.tables.Catalogs;
import jooq.information_schema.tables.Collations;
import jooq.information_schema.tables.ColumnPrivileges;
import jooq.information_schema.tables.Columns;
import jooq.information_schema.tables.Constants;
import jooq.information_schema.tables.Constraints;
import jooq.information_schema.tables.CrossReferences;
import jooq.information_schema.tables.Domains;
import jooq.information_schema.tables.FunctionAliases;
import jooq.information_schema.tables.FunctionColumns;
import jooq.information_schema.tables.Help;
import jooq.information_schema.tables.InDoubt;
import jooq.information_schema.tables.Indexes;
import jooq.information_schema.tables.Locks;
import jooq.information_schema.tables.QueryStatistics;
import jooq.information_schema.tables.Rights;
import jooq.information_schema.tables.Roles;
import jooq.information_schema.tables.Schemata;
import jooq.information_schema.tables.Sequences;
import jooq.information_schema.tables.SessionState;
import jooq.information_schema.tables.Sessions;
import jooq.information_schema.tables.Settings;
import jooq.information_schema.tables.TablePrivileges;
import jooq.information_schema.tables.TableTypes;
import jooq.information_schema.tables.Triggers;
import jooq.information_schema.tables.TypeInfo;
import jooq.information_schema.tables.Users;
import jooq.information_schema.tables.Views;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in INFORMATION_SCHEMA
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>INFORMATION_SCHEMA.QUERY_STATISTICS</code>.
     */
    public static final QueryStatistics QUERY_STATISTICS = jooq.information_schema.tables.QueryStatistics.QUERY_STATISTICS;

    /**
     * The table <code>INFORMATION_SCHEMA.SESSION_STATE</code>.
     */
    public static final SessionState SESSION_STATE = jooq.information_schema.tables.SessionState.SESSION_STATE;

    /**
     * The table <code>INFORMATION_SCHEMA.LOCKS</code>.
     */
    public static final Locks LOCKS = jooq.information_schema.tables.Locks.LOCKS;

    /**
     * The table <code>INFORMATION_SCHEMA.SESSIONS</code>.
     */
    public static final Sessions SESSIONS = jooq.information_schema.tables.Sessions.SESSIONS;

    /**
     * The table <code>INFORMATION_SCHEMA.TRIGGERS</code>.
     */
    public static final Triggers TRIGGERS = jooq.information_schema.tables.Triggers.TRIGGERS;

    /**
     * The table <code>INFORMATION_SCHEMA.DOMAINS</code>.
     */
    public static final Domains DOMAINS = jooq.information_schema.tables.Domains.DOMAINS;

    /**
     * The table <code>INFORMATION_SCHEMA.CONSTANTS</code>.
     */
    public static final Constants CONSTANTS = jooq.information_schema.tables.Constants.CONSTANTS;

    /**
     * The table <code>INFORMATION_SCHEMA.FUNCTION_COLUMNS</code>.
     */
    public static final FunctionColumns FUNCTION_COLUMNS = jooq.information_schema.tables.FunctionColumns.FUNCTION_COLUMNS;

    /**
     * The table <code>INFORMATION_SCHEMA.CONSTRAINTS</code>.
     */
    public static final Constraints CONSTRAINTS = jooq.information_schema.tables.Constraints.CONSTRAINTS;

    /**
     * The table <code>INFORMATION_SCHEMA.CROSS_REFERENCES</code>.
     */
    public static final CrossReferences CROSS_REFERENCES = jooq.information_schema.tables.CrossReferences.CROSS_REFERENCES;

    /**
     * The table <code>INFORMATION_SCHEMA.IN_DOUBT</code>.
     */
    public static final InDoubt IN_DOUBT = jooq.information_schema.tables.InDoubt.IN_DOUBT;

    /**
     * The table <code>INFORMATION_SCHEMA.VIEWS</code>.
     */
    public static final Views VIEWS = jooq.information_schema.tables.Views.VIEWS;

    /**
     * The table <code>INFORMATION_SCHEMA.COLLATIONS</code>.
     */
    public static final Collations COLLATIONS = jooq.information_schema.tables.Collations.COLLATIONS;

    /**
     * The table <code>INFORMATION_SCHEMA.COLUMN_PRIVILEGES</code>.
     */
    public static final ColumnPrivileges COLUMN_PRIVILEGES = jooq.information_schema.tables.ColumnPrivileges.COLUMN_PRIVILEGES;

    /**
     * The table <code>INFORMATION_SCHEMA.TABLE_PRIVILEGES</code>.
     */
    public static final TablePrivileges TABLE_PRIVILEGES = jooq.information_schema.tables.TablePrivileges.TABLE_PRIVILEGES;

    /**
     * The table <code>INFORMATION_SCHEMA.SCHEMATA</code>.
     */
    public static final Schemata SCHEMATA = jooq.information_schema.tables.Schemata.SCHEMATA;

    /**
     * The table <code>INFORMATION_SCHEMA.FUNCTION_ALIASES</code>.
     */
    public static final FunctionAliases FUNCTION_ALIASES = jooq.information_schema.tables.FunctionAliases.FUNCTION_ALIASES;

    /**
     * The table <code>INFORMATION_SCHEMA.RIGHTS</code>.
     */
    public static final Rights RIGHTS = jooq.information_schema.tables.Rights.RIGHTS;

    /**
     * The table <code>INFORMATION_SCHEMA.ROLES</code>.
     */
    public static final Roles ROLES = jooq.information_schema.tables.Roles.ROLES;

    /**
     * The table <code>INFORMATION_SCHEMA.USERS</code>.
     */
    public static final Users USERS = jooq.information_schema.tables.Users.USERS;

    /**
     * The table <code>INFORMATION_SCHEMA.SEQUENCES</code>.
     */
    public static final Sequences SEQUENCES = jooq.information_schema.tables.Sequences.SEQUENCES;

    /**
     * The table <code>INFORMATION_SCHEMA.HELP</code>.
     */
    public static final Help HELP = jooq.information_schema.tables.Help.HELP;

    /**
     * The table <code>INFORMATION_SCHEMA.SETTINGS</code>.
     */
    public static final Settings SETTINGS = jooq.information_schema.tables.Settings.SETTINGS;

    /**
     * The table <code>INFORMATION_SCHEMA.CATALOGS</code>.
     */
    public static final Catalogs CATALOGS = jooq.information_schema.tables.Catalogs.CATALOGS;

    /**
     * The table <code>INFORMATION_SCHEMA.TYPE_INFO</code>.
     */
    public static final TypeInfo TYPE_INFO = jooq.information_schema.tables.TypeInfo.TYPE_INFO;

    /**
     * The table <code>INFORMATION_SCHEMA.TABLE_TYPES</code>.
     */
    public static final TableTypes TABLE_TYPES = jooq.information_schema.tables.TableTypes.TABLE_TYPES;

    /**
     * The table <code>INFORMATION_SCHEMA.INDEXES</code>.
     */
    public static final Indexes INDEXES = jooq.information_schema.tables.Indexes.INDEXES;

    /**
     * The table <code>INFORMATION_SCHEMA.COLUMNS</code>.
     */
    public static final Columns COLUMNS = jooq.information_schema.tables.Columns.COLUMNS;

    /**
     * The table <code>INFORMATION_SCHEMA.TABLES</code>.
     */
    public static final jooq.information_schema.tables.Tables TABLES = jooq.information_schema.tables.Tables.TABLES;
}
