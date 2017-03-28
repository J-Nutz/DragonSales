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
import java.time.LocalDate;

import static org.jooq.impl.DSL.row;

public class DailyStatsTable
{
    private static final DailyStats dailyStats = DailyStats.DAILY_STATS;

    public static boolean addEmptySale()
    {
        return DatabaseExecutor.submitBoolean(() ->
               {
                   try(Connection connection = DatabaseExecutor.getConnection();
                       DSLContext database = H2DSL.using(connection))
                   {
                       StatisticsTracker emptyStats = new StatisticsTracker();

                       emptyStats.setDay(Date.valueOf(LocalDate.now()));
                       emptyStats.setFoodSold(0);
                       emptyStats.setBakerySold(0);
                       emptyStats.setCandySold(0);
                       emptyStats.setChipsSold(0);
                       emptyStats.setDrinksSold(0);
                       emptyStats.setSodaSold(0);
                       emptyStats.setWaterSold(0);
                       emptyStats.setJuiceSold(0);
                       emptyStats.setCoffeeSold(0);
                       emptyStats.setFrozenSold(0);
                       emptyStats.setMiscSold(0);
                       emptyStats.setNumOfSales(0);
                       emptyStats.setNumOfItemsSold(0);
                       emptyStats.setTotalIncome(new BigDecimal("0.00"));
                       emptyStats.setTotalProfit(new BigDecimal("0.00"));

                       InsertValuesStep16<DailyStatsRecord,
                               Date,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               Integer,
                               BigDecimal,
                               BigDecimal>

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

                       logSaleStep.values(emptyStats.getDay(),
                                          emptyStats.getFoodSold(),
                                          emptyStats.getBakerySold(),
                                          emptyStats.getCandySold(),
                                          emptyStats.getChipsSold(),
                                          emptyStats.getDrinksSold(),
                                          emptyStats.getSodaSold(),
                                          emptyStats.getWaterSold(),
                                          emptyStats.getJuiceSold(),
                                          emptyStats.getCoffeeSold(),
                                          emptyStats.getFrozenSold(),
                                          emptyStats.getMiscSold(),
                                          emptyStats.getNumOfSales(),
                                          emptyStats.getNumOfItemsSold(),
                                          emptyStats.getTotalIncome(),
                                          emptyStats.getTotalProfit());

                       int logSaleResult = logSaleStep.execute();

                       return logSaleResult == 1;
                   }
               }
        );
    }

    public static StatisticsTracker getDayStats()
    {
        return DatabaseExecutor.submitObject(() ->
               {
                    try(Connection connection = DatabaseExecutor.getConnection();
                        DSLContext database = H2DSL.using(connection))
                    {
                        int numOfStats = database.selectCount().from(dailyStats).fetchOne(0, int.class);

                        if(numOfStats == 0)
                        {
                            addEmptySale();
                        }

                        Result<Record> fetchedStats =
                                database.select()
                                        .from(dailyStats)
                                        .where()
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

                            return currentStats;
                        }
                        else
                        {
                            return null;
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
                StatisticsTracker currentStats = getDayStats();

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
        });
    }
}