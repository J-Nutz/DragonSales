package database.tables;

/*
 * Created by Jonah on 12/31/2016.
 */

import jooq.public_.tables.Users;
import jooq.public_.tables.records.UsersRecord;
import database.DatabaseExecutor;
import mutual.types.User;
import org.jooq.*;
import org.jooq.util.h2.H2DSL;

import java.sql.Connection;

import static org.jooq.impl.DSL.row;

public class UsersTable
{
    private static Users users = Users.USERS;

    public UsersTable() {}

    public static boolean addUser(User user)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                InsertValuesStep5<UsersRecord, String, String, String, byte[], byte[]> addUserStep =
                        database.insertInto(users, users.EMAIL, users.NAME, users.USER_NAME, users.SALT, users.PASSWORD);

                addUserStep.values(user.getEmail(), user.getName(), user.getUsername(), user.getSalt(), user.getPassword());

                int addUserResult = addUserStep.execute();

                return addUserResult == 1;
            }
        });
    }

    public static User getUser(String username)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedUser =
                        database.select()
                                .from(users)
                                .where(users.USER_NAME.equal(username))
                                .fetch();

                if(fetchedUser.isNotEmpty())
                {
                    User user = new User();

                    for (Record r : fetchedUser)
                    {
                        user.setName(r.getValue(users.NAME));
                        user.setUsername(r.getValue(users.USER_NAME));
                        user.setEmail(r.getValue(users.EMAIL));
                        user.setSalt(r.getValue(users.SALT));
                        user.setPassword(r.getValue(users.PASSWORD));
                    }

                    return user;
                }
                else
                {
                    return null;
                }
            }
        });
    }

    public static boolean deleteUser(String username)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
          try(Connection connection = DatabaseExecutor.getConnection();
              DSLContext database = H2DSL.using(connection))
          {
              int userDeleted = database.deleteFrom(users)
                                          .where(users.EMAIL.eq(username))
                                          .execute();

              return userDeleted == 1;
          }
        });
    }

    public static boolean updateUser(String usernameToUpdate, User user)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                int result =
                        database.update(users)
                                .set(row(users.EMAIL, users.NAME, users.USER_NAME, users.SALT, users.PASSWORD),
                                     row(user.getEmail(), user.getName(), user.getUsername(), user.getSalt(), user.getPassword()))
                                .where(users.USER_NAME.equal(usernameToUpdate))
                                .execute();

                return result == 1;
            }
        });
    }

    public static boolean userExists(String username)
    {
        return true;
    }

    public static Integer numberOfUsers()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record1<Integer>> result =
                        database.selectCount()
                                .from(users)
                                .fetch();

                return result.get(0).value1();
            }
        });
    }

    public static boolean usernameAvailable(String username)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> usernameCount =
                        database.select()
                                .from(users)
                                .where(users.USER_NAME.equalIgnoreCase(username))
                                .limit(1)
                                .fetch();

                return usernameCount.isEmpty();
            }
        });
    }
}