package database.tables;

/*
 * Created by Jonah on 4/5/2017.
 */

import database.DatabaseExecutor;
import jooq.public_.tables.AllTimeStats;
import mutual.views.statistics.StatisticsTracker;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.util.h2.H2DSL;

import java.math.BigDecimal;
import java.sql.Connection;

import static org.jooq.impl.DSL.row;

public class AllTimeStatsTable
{
    private static final AllTimeStats allStats = AllTimeStats.ALL_TIME_STATS;

    public static boolean updateStats(StatisticsTracker saleLog)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
              DSLContext database = H2DSL.using(connection))
            {
                int numOfStatRows = database.selectCount().from(allStats).fetchOne(0, int.class);

                if(numOfStatRows == 0)
                {
                    return createFirstRow(saleLog);
                }
                else if(numOfStatRows == 1)
                {
                    StatisticsTracker currentStats = getAllTimeStats();

                    return database.update(allStats)
                                   .set(row(allStats.FOOD_SOLD,
                                            allStats.BAKERY_SOLD,
                                            allStats.CANDY_SOLD,
                                            allStats.CHIPS_SOLD,
                                            allStats.DRINKS_SOLD,
                                            allStats.SODAS_SOLD,
                                            allStats.WATERS_SOLD,
                                            allStats.JUICES_SOLD,
                                            allStats.COFFEES_SOLD,
                                            allStats.FROZEN_SOLD,
                                            allStats.MISC_SOLD,
                                            allStats.TOTAL_SALES,
                                            allStats.TOTAL_ITEMS_SOLD,
                                            allStats.TOTAL_INCOME,
                                            allStats.TOTAL_PROFIT),

                                        row(currentStats.getFoodSold() + saleLog.getFoodSold(),
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
                                   .execute() == 1;
                }
                else if(numOfStatRows > 1)
                {
                    database.dropTableIfExists(allStats).execute();
                    database.createTable(allStats);

                    return createFirstRow(saleLog);
                }
                else
                {
                  return false;
                }
            }
        });
    }

    public static StatisticsTracker getAllTimeStats()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedStats =
                     database.select()
                             .from(allStats)
                             .fetch();

                if(fetchedStats.isNotEmpty())
                {
                    StatisticsTracker currentStats = new StatisticsTracker();

                    for(Record r : fetchedStats)
                    {
                        currentStats.setFoodSold(r.get(allStats.FOOD_SOLD));
                        currentStats.setBakerySold(r.get(allStats.BAKERY_SOLD));
                        currentStats.setCandySold(r.get(allStats.CANDY_SOLD));
                        currentStats.setChipsSold(r.get(allStats.CHIPS_SOLD));
                        currentStats.setDrinksSold(r.get(allStats.DRINKS_SOLD));
                        currentStats.setSodaSold(r.get(allStats.SODAS_SOLD));
                        currentStats.setWaterSold(r.get(allStats.WATERS_SOLD));
                        currentStats.setJuiceSold(r.get(allStats.JUICES_SOLD));
                        currentStats.setCoffeeSold(r.get(allStats.COFFEES_SOLD));
                        currentStats.setFrozenSold(r.get(allStats.FROZEN_SOLD));
                        currentStats.setMiscSold(r.get(allStats.MISC_SOLD));
                        currentStats.setNumOfSales(r.get(allStats.TOTAL_SALES));
                        currentStats.setNumOfItemsSold(r.get(allStats.TOTAL_ITEMS_SOLD));
                        currentStats.setTotalIncome(r.get(allStats.TOTAL_INCOME));
                        currentStats.setTotalProfit(r.get(allStats.TOTAL_PROFIT));
                    }

                    return currentStats;
                }
                else
                {
                    StatisticsTracker emptyLog = new StatisticsTracker();

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
                    createFirstRow(emptyLog);

                    return emptyLog;
                }
            }
        });
    }

    private static boolean createFirstRow(StatisticsTracker saleLog)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                return database.insertInto(allStats,
                                           allStats.FOOD_SOLD,
                                           allStats.BAKERY_SOLD,
                                           allStats.CANDY_SOLD,
                                           allStats.CHIPS_SOLD,
                                           allStats.DRINKS_SOLD,
                                           allStats.SODAS_SOLD,
                                           allStats.WATERS_SOLD,
                                           allStats.JUICES_SOLD,
                                           allStats.COFFEES_SOLD,
                                           allStats.FROZEN_SOLD,
                                           allStats.MISC_SOLD,
                                           allStats.TOTAL_SALES,
                                           allStats.TOTAL_ITEMS_SOLD,
                                           allStats.TOTAL_INCOME,
                                           allStats.TOTAL_PROFIT)

                               .values(saleLog.getFoodSold(),
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
                                              saleLog.getTotalProfit())
                               .execute() == 1;
            }
        });
    }
}