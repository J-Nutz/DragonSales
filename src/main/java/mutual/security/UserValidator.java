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
        if(!email.isEmpty())
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
        else
        {
            return false;
        }
    }

    public static boolean validName(String name)
    {
        return (name.length() > 3) && (name.contains(" "));
    }

    public static boolean validUsername(String username)
    {
        return (username.length() > 3) && (UsersTable.usernameAvailable(username));
    }

    public static boolean validPasswords(char[] pass1, char[] pass2)
    {
        return (pass1.length > 5) && (Arrays.equals(pass2, pass1));
    }

    public static boolean validPassword(String username, byte[] attemptedPassword)
    {
        User correctUser = UsersTable.getUser(username);

        return (correctUser != null) && (Arrays.equals(attemptedPassword, correctUser.getPassword()));
    }

    public boolean validLockedLogin(char[] attemptedPassword)
    {
        User correctUser = LoggedInUserTable.getLoggedInUser();

        return Arrays.equals(correctUser.getPassword(), encrypt(attemptedPassword, correctUser.getSalt()));
    }

    public static String[] validLogin(User attemptedUser)
    {
        String[] loginInfo = new String[2];

        if(!validUsername(attemptedUser.getName()))
        {
            loginInfo[0] = "false";
            loginInfo[1] = "Invalid Username";
        }
        else if(!validPassword(attemptedUser.getUsername(), attemptedUser.getPassword()))
        {
            loginInfo[0] = "false";
            loginInfo[1] = "Invalid Password";
        }
        else
        {
            loginInfo[0] = "true";
            loginInfo[1] = "Success";
        }

        return loginInfo;
    }
}