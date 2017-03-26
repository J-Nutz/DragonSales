package mutual.security;

/*
 * Created by Jonah on 12/3/2016.
 */

import database.tables.LoggedInUserTable;
import database.tables.UsersTable;
import mutual.types.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;

import static mutual.security.Encryption.encrypt;

public class UserValidator
{
    public UserValidator() {}

    public static boolean validEmail(String email)
    {
        try
        {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            return true;
        }
        catch(AddressException e)
        {
            return false;
        }
    }

    public static boolean validUsername(String username)
    {
        return UsersTable.usernameAvailable(username);
    }

    public static boolean validPassword(String username, byte[] attemptedPassword)
    {
        User correctUser = UsersTable.getUser(username);

        return correctUser != null && Arrays.equals(attemptedPassword, correctUser.getPassword());
    }

    public boolean validLockedLogin(char[] attemptedPassword)
    {
        User correctUser = LoggedInUserTable.getLoggedInUser();

        return Arrays.equals(correctUser.getPassword(), encrypt(attemptedPassword, correctUser.getSalt()));
    }

    public static String[] validLogin(User attemptedUser)
    {
        //boolean valid, boolean fullAccess, String message
        String[] loginInfo = new String[3];

        if(!validUsername(attemptedUser.getName()))
        {
            loginInfo[0] = "false";
            loginInfo[1] = "false";
            loginInfo[2] = "Invalid Username";
        }
        else if(!validPassword(attemptedUser.getUsername(), attemptedUser.getPassword()))
        {
            loginInfo[0] = "false";
            loginInfo[1] = "false";
            loginInfo[2] = "Invalid Password";
        }
        else
        {
            loginInfo[0] = "true";
            loginInfo[1] = "true";
            loginInfo[2] = "Success";

            /*if(EmployeesTable.getEmployeePosition(attemptedUser.getUsername()).getAccessLevel() >= 2)
            {
                loginInfo[0] = "true";
                loginInfo[1] = "true";
                loginInfo[2] = "Success";
            }
            else
            {
                loginInfo[0] = "true";
                loginInfo[1] = "false";
                loginInfo[2] = "Success";
            }*/
        }

        return loginInfo;
    }
}