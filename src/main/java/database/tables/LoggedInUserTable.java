package database.tables;

/*
 * Created by Jonah on 12/8/2016.
 */

import jooq.public_.tables.CurrentUser;
import database.DatabaseExecutor;
import mutual.types.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.util.h2.H2DSL;

import java.sql.Connection;

import static org.jooq.impl.DSL.row;

public class LoggedInUserTable
{
    private static final CurrentUser currentUser = CurrentUser.CURRENT_USER;

    public static boolean setLoggedInUser(User user)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                int loggedInUsers = database.selectCount().from(currentUser).fetchOne(0, int.class);

                if(loggedInUsers == 0)
                {
                    database.insertInto(currentUser, currentUser.USER_NAME, currentUser.SALT, currentUser.PASSWORD)
                            .values(user.getUsername(), user.getSalt(), user.getPassword())
                            .execute();

                    return true;
                }
                else if(loggedInUsers == 1)
                {
                    database.update(currentUser)
                            .set(row(currentUser.USER_NAME, currentUser.SALT, currentUser.PASSWORD),
                                 row(user.getUsername(), user.getSalt(),user.getPassword()))
                            .where(currentUser.USER_NAME.isNotNull());

                    return true;
                }
                else if(loggedInUsers > 1)
                {
                    database.dropTableIfExists(currentUser).execute();
                    database.createTable(currentUser);

                    database.insertInto(currentUser, currentUser.USER_NAME, currentUser.SALT, currentUser.PASSWORD)
                            .values(user.getUsername(), user.getSalt(), user.getPassword())
                            .execute();

                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public static User getLoggedInUser()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                User loggedInUser = new User();

                Result<Record> loggedInUserData = database.select()
                        .from(currentUser)
                        .limit(1)
                        .fetch();

                for(Record r : loggedInUserData)
                {
                    loggedInUser.setUsername(r.getValue(currentUser.USER_NAME));
                    loggedInUser.setSalt(r.getValue(currentUser.SALT));
                    loggedInUser.setPassword(r.getValue(currentUser.PASSWORD));
                }

                return loggedInUser;
            }
        });
    }
}