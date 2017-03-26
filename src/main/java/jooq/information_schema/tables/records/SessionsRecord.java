/**
 * This class is generated by jOOQ
 */
package jooq.information_schema.tables.records;


import jooq.information_schema.tables.Sessions;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class SessionsRecord extends TableRecordImpl<SessionsRecord> implements Record6<Integer, String, String, String, String, String> {

    private static final long serialVersionUID = 1335223120;

    /**
     * Setter for <code>INFORMATION_SCHEMA.SESSIONS.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SESSIONS.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SESSIONS.USER_NAME</code>.
     */
    public void setUserName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SESSIONS.USER_NAME</code>.
     */
    public String getUserName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SESSIONS.SESSION_START</code>.
     */
    public void setSessionStart(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SESSIONS.SESSION_START</code>.
     */
    public String getSessionStart() {
        return (String) get(2);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SESSIONS.STATEMENT</code>.
     */
    public void setStatement(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SESSIONS.STATEMENT</code>.
     */
    public String getStatement() {
        return (String) get(3);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SESSIONS.STATEMENT_START</code>.
     */
    public void setStatementStart(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SESSIONS.STATEMENT_START</code>.
     */
    public String getStatementStart() {
        return (String) get(4);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SESSIONS.CONTAINS_UNCOMMITTED</code>.
     */
    public void setContainsUncommitted(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SESSIONS.CONTAINS_UNCOMMITTED</code>.
     */
    public String getContainsUncommitted() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Sessions.SESSIONS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Sessions.SESSIONS.USER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Sessions.SESSIONS.SESSION_START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Sessions.SESSIONS.STATEMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Sessions.SESSIONS.STATEMENT_START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Sessions.SESSIONS.CONTAINS_UNCOMMITTED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSessionStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getStatement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getStatementStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getContainsUncommitted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord value2(String value) {
        setUserName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord value3(String value) {
        setSessionStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord value4(String value) {
        setStatement(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord value5(String value) {
        setStatementStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord value6(String value) {
        setContainsUncommitted(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionsRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SessionsRecord
     */
    public SessionsRecord() {
        super(Sessions.SESSIONS);
    }

    /**
     * Create a detached, initialised SessionsRecord
     */
    public SessionsRecord(Integer id, String userName, String sessionStart, String statement, String statementStart, String containsUncommitted) {
        super(Sessions.SESSIONS);

        set(0, id);
        set(1, userName);
        set(2, sessionStart);
        set(3, statement);
        set(4, statementStart);
        set(5, containsUncommitted);
    }
}
