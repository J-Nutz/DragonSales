package database;

/*
 * Created by Jonah on 11/11/2016.
 */

import org.jooq.DSLContext;
import org.jooq.util.h2.H2DSL;
import org.jooq.util.h2.H2DataType;

import java.sql.Connection;
import java.sql.SQLException;

public class Database
{
    public Database() {}

    public static synchronized void createTables()
    {
        try(Connection connection = DatabaseExecutor.getConnection();
            DSLContext database = H2DSL.using(connection))
        {
            database.createTableIfNotExists("PRODUCTS")
                    .column("ID", H2DataType.IDENTITY)
                    .column("NAME", H2DataType.VARCHAR_IGNORECASE.length(64))
                    .column("CATEGORY", H2DataType.VARCHAR_IGNORECASE.length(16))
                    .column("PURCHASE_PRICE", H2DataType.DECIMAL)
                    .column("PRICE", H2DataType.DECIMAL)
                    .column("SALE_PRICE", H2DataType.DECIMAL)
                    .column("CUR_QUANTITY", H2DataType.INTEGER)
                    .column("INIT_QUANTITY", H2DataType.INTEGER)
                    .column("TOTAL_SOLD", H2DataType.INTEGER)
                    .column("EXPIRATION_DATE", H2DataType.DATE)
                    .column("LAST_SOLD_DATE", H2DataType.DATE)
                    .column("ORDERED_DATE", H2DataType.DATE)
                    .column("RECEIVED_DATE", H2DataType.DATE)
                    .execute();

            database.createTableIfNotExists("USERS")
                    .column("NAME", H2DataType.VARCHAR_IGNORECASE) // TODO: Setting to show first initial of last name or not
                    .column("USER_NAME", H2DataType.VARCHAR_CASESENSITIVE) // TODO: Let users know about char limits
                    .column("EMAIL", H2DataType.VARCHAR_CASESENSITIVE) // TODO: Trim spaces/toLowercase/etc.
                    .column("SALT", H2DataType.BINARY)
                    .column("PASSWORD", H2DataType.BINARY)
                    .execute();

            database.createTableIfNotExists("EMPLOYEES") // TODO: Talk To Ms. Molly
                    .column("IMAGE_PATH", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("NAME", H2DataType.VARCHAR_IGNORECASE)
                    .column("EMAIL", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("POSITION", H2DataType.VARCHAR_IGNORECASE) //Determine view with position
                    .column("WAGE", H2DataType.DECIMAL)
                    .column("HIRED_DATE", H2DataType.DATE)
                    .execute();

            database.createTableIfNotExists("CURRENT_USER")
                    .column("USER_NAME", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("SALT", H2DataType.BINARY)
                    .column("PASSWORD", H2DataType.BINARY)
                    .execute();

            database.createTableIfNotExists("DISCOUNTS")
                    .column("PRODUCT", H2DataType.VARCHAR_IGNORECASE)
                    .column("OLD_PRICE", H2DataType.DECIMAL)
                    .column("DISCOUNT_PRICE", H2DataType.DECIMAL)
                    .column("MONDAY", H2DataType.BOOLEAN)
                    .column("TUESDAY", H2DataType.BOOLEAN)
                    .column("WEDNESDAY", H2DataType.BOOLEAN)
                    .column("THURSDAY", H2DataType.BOOLEAN)
                    .column("FRIDAY", H2DataType.BOOLEAN)
                    .column("REPEAT", H2DataType.VARCHAR)
                    .execute();

            database.createTableIfNotExists("SCHEDULE")
                    .column("DAY", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("B1_E1", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("B1_E2", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("LU_E1", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("LU_E2", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("B2_E1", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("B2_E2", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("ST_E1", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("ST_E2", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("AD_E1", H2DataType.VARCHAR_CASESENSITIVE)
                    .column("AD_E2", H2DataType.VARCHAR_CASESENSITIVE)
                    .execute();

            database.createTableIfNotExists("DAILY_STATS")
                    .column("DAY", H2DataType.DATE)
                    .column("FOOD_SOLD", H2DataType.INTEGER)
                    .column("BAKERY_SOLD", H2DataType.INTEGER)
                    .column("CANDY_SOLD", H2DataType.INTEGER)
                    .column("CHIPS_SOLD", H2DataType.INTEGER)
                    .column("DRINKS_SOLD", H2DataType.INTEGER)
                    .column("SODAS_SOLD", H2DataType.INTEGER)
                    .column("WATERS_SOLD", H2DataType.INTEGER)
                    .column("JUICES_SOLD", H2DataType.INTEGER)
                    .column("COFFEES_SOLD", H2DataType.INTEGER)
                    .column("FROZEN_SOLD", H2DataType.INTEGER)
                    .column("MISC_SOLD", H2DataType.INTEGER)
                    .column("TOTAL_SALES", H2DataType.INTEGER)
                    .column("TOTAL_ITEMS_SOLD", H2DataType.INTEGER)
                    .column("TOTAL_INCOME", H2DataType.DECIMAL) // Total Income
                    .column("TOTAL_PROFIT", H2DataType.DECIMAL)   // Total profit
                    .execute();

            // TODO: Tables Deleted To Be Created At A Later Dated
            // TODO: Settings, DAILY_STATS, WEEKLY_STATS, MONTHLY_STATS, YEARLY_STATS, TOTAL_STATS <- Store each year stats in
            // TODO: Screen Shot Saved Just In Case
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new NullPointerException("\n Issue Checking Or Creating Tables \n");
        }
    }
}