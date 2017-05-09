package database.tables;

/*
 * Created by Jonah on 2/25/2017.
 */

import database.DatabaseExecutor;
import jooq.public_.tables.Schedule;
import jooq.public_.tables.records.ScheduleRecord;
import org.jooq.*;
import org.jooq.util.h2.H2DSL;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import static org.jooq.impl.DSL.row;

public class ScheduleTable
{
    private static final Schedule schedule = Schedule.SCHEDULE;

    public static boolean setScheduleFor(Date date, ArrayList<String> scheduleList)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<ScheduleRecord> result = database.selectFrom(schedule).where(schedule.S_DATE.eq(date)).fetch();

                if(result.isNotEmpty())
                {
                    System.out.println("Schedule Exists - Updating...");

                    return database.update(schedule)
                            .set(row(schedule.S_DATE,
                                     schedule.B1_E1,
                                     schedule.B1_E2,
                                     schedule.LU_E1,
                                     schedule.LU_E2,
                                     schedule.B2_E1,
                                     schedule.B2_E2,
                                     schedule.ST_E1,
                                     schedule.ST_E2,
                                     schedule.AD_E1,
                                     schedule.AD_E2),

                                    row(date,
                                        scheduleList.get(0),
                                        scheduleList.get(1),
                                        scheduleList.get(2),
                                        scheduleList.get(3),
                                        scheduleList.get(4),
                                        scheduleList.get(5),
                                        scheduleList.get(6),
                                        scheduleList.get(7),
                                        scheduleList.get(8),
                                        scheduleList.get(9)))
                            .where(schedule.S_DATE.equal(date))
                            .execute() == 1;
                }
                else
                {
                    System.out.println("Schedule Doesn't Exist - Creating...");

                    InsertValuesStep11<ScheduleRecord, Date, String, String, String, String, String, String, String, String, String, String> insertSchedule =
                            database.insertInto
                                    (
                                            schedule,
                                            schedule.S_DATE,
                                            schedule.B1_E1,
                                            schedule.B1_E2,
                                            schedule.LU_E1,
                                            schedule.LU_E2,
                                            schedule.B2_E1,
                                            schedule.B2_E2,
                                            schedule.ST_E1,
                                            schedule.ST_E2,
                                            schedule.AD_E1,
                                            schedule.AD_E2
                                    );

                    insertSchedule.values
                            (
                                    date,
                                    scheduleList.get(0),
                                    scheduleList.get(1),
                                    scheduleList.get(2),
                                    scheduleList.get(3),
                                    scheduleList.get(4),
                                    scheduleList.get(5),
                                    scheduleList.get(6),
                                    scheduleList.get(7),
                                    scheduleList.get(8),
                                    scheduleList.get(9)
                            );

                    int addScheduleResult = insertSchedule.execute();

                    return addScheduleResult == 1;
                }
            }
        });
    }

    public static ArrayList<String> getScheduleFor(Date date)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<ScheduleRecord> fetchedSchedule =
                        database.selectFrom(schedule)
                                .where(schedule.S_DATE.equal(date))
                                .fetch();

                if(fetchedSchedule.isNotEmpty())
                {
                    ArrayList<String> scheduleList = new ArrayList<>();

                    for (Record r : fetchedSchedule)
                    {
                        scheduleList.add(r.get(schedule.B1_E1));
                        scheduleList.add(r.get(schedule.B1_E2));
                        scheduleList.add(r.get(schedule.LU_E1));
                        scheduleList.add(r.get(schedule.LU_E2));
                        scheduleList.add(r.get(schedule.B2_E1));
                        scheduleList.add(r.get(schedule.B2_E2));
                        scheduleList.add(r.get(schedule.ST_E1));
                        scheduleList.add(r.get(schedule.ST_E2));
                        scheduleList.add(r.get(schedule.AD_E1));
                        scheduleList.add(r.get(schedule.AD_E2));
                    }

                    return scheduleList;
                }
                else
                {
                    ArrayList<String> emptySchedule = new ArrayList<>();
                    emptySchedule.add("Empty");
                    return emptySchedule;
                }
            }
        });
    }
}