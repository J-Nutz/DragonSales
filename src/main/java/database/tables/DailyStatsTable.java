package database.tables;

/*
 * Created by Jonah on 3/16/2017.
 */

import database.DatabaseExecutor;
import jooq.public_.tables.DailyStats;
import jooq.public_.tables.records.DailyStatsRecord;
import mutual.views.statistics.StatisticsTracker;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep16;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.util.h2.H2DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.jooq.impl.DSL.row;

public class DailyStatsTable
{
    private static final DailyStats dailyStats = DailyStats.DAILY_STATS;

    public static boolean startNewLog(StatisticsTracker saleLog)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
           try(Connection connection = DatabaseExecutor.getConnection();
               DSLContext database = H2DSL.using(connection))
           {
               if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) //Causes Hangup/Crash On Weekends
                  || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY))
               {
                   System.out.println("Weekend - Not Starting New Log");
                   return false;
               }
               else
               {
                   System.out.println("Weekday - Starting Empty Log...");

                   InsertValuesStep16<DailyStatsRecord, Date, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, BigDecimal, BigDecimal>

                           logSaleStep = database.insertInto(dailyStats,
                                                             dailyStats.DAY,
                                                             dailyStats.FOOD_SOLD,
                                                             dailyStats.BAKERY_SOLD,
                                                             dailyStats.CANDY_SOLD,
                                                             dailyStats.CHIPS_SOLD,
                                                             dailyStats.DRINKS_SOLD,
                                                             dailyStats.SODAS_SOLD,
                                                             dailyStats.WATERS_SOLD,
                                                             dailyStats.JUICES_SOLD,
                                                             dailyStats.COFFEES_SOLD,
                                                             dailyStats.FROZEN_SOLD,
                                                             dailyStats.MISC_SOLD,
                                                             dailyStats.TOTAL_SALES,
                                                             dailyStats.TOTAL_ITEMS_SOLD,
                                                             dailyStats.TOTAL_INCOME,
                                                             dailyStats.TOTAL_PROFIT);

                   logSaleStep.values(saleLog.getDay(),
                                      saleLog.getFoodSold(),
                                      saleLog.getBakerySold(),
                                      saleLog.getCandySold(),
                                      saleLog.getChipsSold(),
                                      saleLog.getDrinksSold(),
                                      saleLog.getSodaSold(),
                                      saleLog.getWaterSold(),
                                      saleLog.getJuiceSold(),
                                      saleLog.getCoffeeSold(),
                                      saleLog.getFrozenSold(),
                                      saleLog.getMiscSold(),
                                      saleLog.getNumOfSales(),
                                      saleLog.getNumOfItemsSold(),
                                      saleLog.getTotalIncome(),
                                      saleLog.getTotalProfit());

                   int logSaleResult = logSaleStep.execute();

                   System.out.println("Empty Log Created");

                   return logSaleResult == 1;
               }
           }
        });
    }

    public static StatisticsTracker getDayStats(Date date)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedStats =
                        database.select()
                                .from(dailyStats)
                                .where(dailyStats.DAY.eq(date))
                                .fetch();

                if(fetchedStats.isNotEmpty())
                {
                    StatisticsTracker currentStats = new StatisticsTracker();

                    for(Record r : fetchedStats)
                    {
                        currentStats.setDay(r.get(dailyStats.DAY));
                        currentStats.setFoodSold(r.get(dailyStats.FOOD_SOLD));
                        currentStats.setBakerySold(r.get(dailyStats.BAKERY_SOLD));
                        currentStats.setCandySold(r.get(dailyStats.CANDY_SOLD));
                        currentStats.setChipsSold(r.get(dailyStats.CHIPS_SOLD));
                        currentStats.setDrinksSold(r.get(dailyStats.DRINKS_SOLD));
                        currentStats.setSodaSold(r.get(dailyStats.SODAS_SOLD));
                        currentStats.setWaterSold(r.get(dailyStats.WATERS_SOLD));
                        currentStats.setJuiceSold(r.get(dailyStats.JUICES_SOLD));
                        currentStats.setCoffeeSold(r.get(dailyStats.COFFEES_SOLD));
                        currentStats.setFrozenSold(r.get(dailyStats.FROZEN_SOLD));
                        currentStats.setMiscSold(r.get(dailyStats.MISC_SOLD));
                        currentStats.setNumOfSales(r.get(dailyStats.TOTAL_SALES));
                        currentStats.setNumOfItemsSold(r.get(dailyStats.TOTAL_ITEMS_SOLD));
                        currentStats.setTotalIncome(r.get(dailyStats.TOTAL_INCOME));
                        currentStats.setTotalProfit(r.get(dailyStats.TOTAL_PROFIT));
                    }

                    System.out.println("Day Stats Found. Returning Log...");

                    return currentStats;
                }
                else
                {
                    StatisticsTracker emptyLog = new StatisticsTracker();

                    emptyLog.setDay(date);
                    emptyLog.setFoodSold(0);
                    emptyLog.setBakerySold(0);
                    emptyLog.setCandySold(0);
                    emptyLog.setChipsSold(0);
                    emptyLog.setDrinksSold(0);
                    emptyLog.setSodaSold(0);
                    emptyLog.setWaterSold(0);
                    emptyLog.setJuiceSold(0);
                    emptyLog.setCoffeeSold(0);
                    emptyLog.setFrozenSold(0);
                    emptyLog.setMiscSold(0);
                    emptyLog.setNumOfSales(0);
                    emptyLog.setNumOfItemsSold(0);
                    emptyLog.setTotalIncome(new BigDecimal("0.00"));
                    emptyLog.setTotalProfit(new BigDecimal("0.00"));

                    if(date.toLocalDate().isEqual(LocalDate.now()))
                    {
                        System.out.println("Stats For Today Not Found. Starting New Log...");
                        startNewLog(emptyLog);

                        return getDayStats(emptyLog.getDay());
                    }
                    else
                    {
                        System.out.println("Trying To Get Early Date - Returning Empty Log...");

                        return emptyLog;
                    }

                }
            }
        });
    }

    public static boolean logSale(StatisticsTracker saleLog)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<DailyStatsRecord> dayStatsStarted =
                        database.selectFrom(dailyStats)
                                .where(dailyStats.DAY.eq(Date.valueOf(LocalDate.now())))
                                .fetch();

                if(dayStatsStarted.isNotEmpty())
                {
                    StatisticsTracker currentStats = getDayStats(saleLog.getDay());

                    System.out.println("Day Stats Started - Updating...");

                    int result = database.update(dailyStats)
                                         .set(row(dailyStats.DAY,
                                                  dailyStats.FOOD_SOLD,
                                                  dailyStats.BAKERY_SOLD,
                                                  dailyStats.CANDY_SOLD,
                                                  dailyStats.CHIPS_SOLD,
                                                  dailyStats.DRINKS_SOLD,
                                                  dailyStats.SODAS_SOLD,
                                                  dailyStats.WATERS_SOLD,
                                                  dailyStats.JUICES_SOLD,
                                                  dailyStats.COFFEES_SOLD,
                                                  dailyStats.FROZEN_SOLD,
                                                  dailyStats.MISC_SOLD,
                                                  dailyStats.TOTAL_SALES,
                                                  dailyStats.TOTAL_ITEMS_SOLD,
                                                  dailyStats.TOTAL_INCOME,
                                                  dailyStats.TOTAL_PROFIT),

                                              row(saleLog.getDay(),
                                                  currentStats.getFoodSold() + saleLog.getFoodSold(),
                                                  currentStats.getBakerySold() + saleLog.getBakerySold(),
                                                  currentStats.getCandySold() + saleLog.getCandySold(),
                                                  currentStats.getChipsSold() + saleLog.getChipsSold(),
                                                  currentStats.getDrinksSold() + saleLog.getDrinksSold(),
                                                  currentStats.getSodaSold() + saleLog.getSodaSold(),
                                                  currentStats.getWaterSold() + saleLog.getWaterSold(),
                                                  currentStats.getJuiceSold() + saleLog.getJuiceSold(),
                                                  currentStats.getCoffeeSold() + saleLog.getCoffeeSold(),
                                                  currentStats.getFrozenSold() + saleLog.getFrozenSold(),
                                                  currentStats.getMiscSold() + saleLog.getMiscSold(),
                                                  currentStats.getNumOfSales() + saleLog.getNumOfSales(),
                                                  currentStats.getNumOfItemsSold() + saleLog.getNumOfItemsSold(),
                                                  currentStats.getTotalIncome().add(saleLog.getTotalIncome()),
                                                  currentStats.getTotalProfit().add(saleLog.getTotalProfit())))

                                         .where(dailyStats.DAY.equal(saleLog.getDay()))
                                         .execute();

                    return result == 1;
                }
                else
                {
                    System.out.println("Day Stats Not Found. Starting New Log...");

                    return startNewLog(saleLog);
                }
            }
        });
    }

    public static Date getDateOfFirstStat()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                DailyStatsRecord fetchedDate = database.selectFrom(dailyStats)
                        .limit(1)
                        .fetchOne();

                if(fetchedDate.getDay() != null)
                {
                    return fetchedDate.getDay();
                }
                else
                {
                    return null;
                }
            }
        });
    }

    public static Date getDateOfLastStat()
    {
        return DatabaseExecutor.submitObject(() ->
        {
             try(Connection connection = DatabaseExecutor.getConnection();
                 DSLContext database = H2DSL.using(connection))
             {
                 DailyStatsRecord fetchedDate = database.selectFrom(dailyStats)
                                                        .orderBy(dailyStats.DAY.desc())
                                                        .limit(1)
                                                        .fetchOne();

                 if(fetchedDate.getDay() != null)
                 {
                     System.out.println("Last Date Found!");

                     return fetchedDate.getDay();
                 }
                 else
                 {
                     return null;
                 }
             }
        });
    }
}