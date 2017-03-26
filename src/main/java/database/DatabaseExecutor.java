package database;

/*
 * Created by Jonah on 1/1/2017.
 */

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

public class DatabaseExecutor
{
    private static JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/JDALSchoolStore", "JDAL", "johndewey");
    private static ExecutorService executor = Executors.newFixedThreadPool(20);

    static
    {
        Database.createTables();
    }

    public DatabaseExecutor() {}

    public static Connection getConnection()
    {
        try
        {
            return connectionPool.getConnection();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new NullPointerException("Connection Not Returned");
        }
    }

    public static <T> T submitObject(Callable<T> callable)
    {
        try
        {
            long startTime = System.nanoTime();

            Future<T> future = executor.submit(callable);
            T result = future.get();

            long endTime = System.nanoTime();
            System.out.println("T Call Done In: " + ((endTime - startTime) / 1000000) + "ms");

            return result;
        }
        catch(InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean submitBoolean(Callable<Boolean> callable)
    {
        try
        {
            long startTime = System.nanoTime();

            Future<Boolean> future = executor.submit(callable);
            boolean result = future.get();

            long endTime = System.nanoTime();
            System.out.println("Boolean Call Done In: " + ((endTime - startTime) / 1000000) + "ms");

            return result;
        }
        catch(InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void close()
    {
        try
        {
            executor.shutdown();
            connectionPool.dispose();

            if(!executor.awaitTermination(2, TimeUnit.SECONDS))
            {
                executor.shutdownNow();
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}