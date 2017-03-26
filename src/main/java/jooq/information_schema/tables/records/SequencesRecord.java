/**
 * This class is generated by jOOQ
 */
package jooq.information_schema.tables.records;


import jooq.information_schema.tables.Sequences;

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
public class SequencesRecord extends TableRecordImpl<SequencesRecord> implements Record12<String, String, String, Long, Long, Boolean, String, Long, Long, Long, Boolean, Integer> {

    private static final long serialVersionUID = -540991917;

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_CATALOG</code>.
     */
    public void setSequenceCatalog(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_CATALOG</code>.
     */
    public String getSequenceCatalog() {
        return (String) get(0);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_SCHEMA</code>.
     */
    public void setSequenceSchema(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_SCHEMA</code>.
     */
    public String getSequenceSchema() {
        return (String) get(1);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_NAME</code>.
     */
    public void setSequenceName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_NAME</code>.
     */
    public String getSequenceName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.CURRENT_VALUE</code>.
     */
    public void setCurrentValue(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.CURRENT_VALUE</code>.
     */
    public Long getCurrentValue() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.INCREMENT</code>.
     */
    public void setIncrement(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.INCREMENT</code>.
     */
    public Long getIncrement() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.IS_GENERATED</code>.
     */
    public void setIsGenerated(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.IS_GENERATED</code>.
     */
    public Boolean getIsGenerated() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.REMARKS</code>.
     */
    public void setRemarks(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.REMARKS</code>.
     */
    public String getRemarks() {
        return (String) get(6);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.CACHE</code>.
     */
    public void setCache(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.CACHE</code>.
     */
    public Long getCache() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.MIN_VALUE</code>.
     */
    public void setMinValue(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.MIN_VALUE</code>.
     */
    public Long getMinValue() {
        return (Long) get(8);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.MAX_VALUE</code>.
     */
    public void setMaxValue(Long value) {
        set(9, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.MAX_VALUE</code>.
     */
    public Long getMaxValue() {
        return (Long) get(9);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.IS_CYCLE</code>.
     */
    public void setIsCycle(Boolean value) {
        set(10, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.IS_CYCLE</code>.
     */
    public Boolean getIsCycle() {
        return (Boolean) get(10);
    }

    /**
     * Setter for <code>INFORMATION_SCHEMA.SEQUENCES.ID</code>.
     */
    public void setId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>INFORMATION_SCHEMA.SEQUENCES.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(11);
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, String, Long, Long, Boolean, String, Long, Long, Long, Boolean, Integer> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, String, Long, Long, Boolean, String, Long, Long, Long, Boolean, Integer> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Sequences.SEQUENCES.SEQUENCE_CATALOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Sequences.SEQUENCES.SEQUENCE_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Sequences.SEQUENCES.SEQUENCE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return Sequences.SEQUENCES.CURRENT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return Sequences.SEQUENCES.INCREMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return Sequences.SEQUENCES.IS_GENERATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Sequences.SEQUENCES.REMARKS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field8() {
        return Sequences.SEQUENCES.CACHE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field9() {
        return Sequences.SEQUENCES.MIN_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field10() {
        return Sequences.SEQUENCES.MAX_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field11() {
        return Sequences.SEQUENCES.IS_CYCLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Sequences.SEQUENCES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getSequenceCatalog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSequenceSchema();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSequenceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getCurrentValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getIncrement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getIsGenerated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getRemarks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value8() {
        return getCache();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value9() {
        return getMinValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value10() {
        return getMaxValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value11() {
        return getIsCycle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value1(String value) {
        setSequenceCatalog(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value2(String value) {
        setSequenceSchema(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value3(String value) {
        setSequenceName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value4(Long value) {
        setCurrentValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value5(Long value) {
        setIncrement(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value6(Boolean value) {
        setIsGenerated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value7(String value) {
        setRemarks(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value8(Long value) {
        setCache(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value9(Long value) {
        setMinValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value10(Long value) {
        setMaxValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value11(Boolean value) {
        setIsCycle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord value12(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequencesRecord values(String value1, String value2, String value3, Long value4, Long value5, Boolean value6, String value7, Long value8, Long value9, Long value10, Boolean value11, Integer value12) {
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
     * Create a detached SequencesRecord
     */
    public SequencesRecord() {
        super(Sequences.SEQUENCES);
    }

    /**
     * Create a detached, initialised SequencesRecord
     */
    public SequencesRecord(String sequenceCatalog, String sequenceSchema, String sequenceName, Long currentValue, Long increment, Boolean isGenerated, String remarks, Long cache, Long minValue, Long maxValue, Boolean isCycle, Integer id) {
        super(Sequences.SEQUENCES);

        set(0, sequenceCatalog);
        set(1, sequenceSchema);
        set(2, sequenceName);
        set(3, currentValue);
        set(4, increment);
        set(5, isGenerated);
        set(6, remarks);
        set(7, cache);
        set(8, minValue);
        set(9, maxValue);
        set(10, isCycle);
        set(11, id);
    }
}
