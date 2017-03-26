package mutual.types;

/*
 * Created by Jonah on 11/9/2016.
 */

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class User
{
    private SimpleStringProperty email;
    private SimpleStringProperty name;
    private SimpleStringProperty username;
    private SimpleObjectProperty<byte[]> salt;
    private SimpleObjectProperty<byte[]> password;

    public User()
    {
        emailProperty();
        nameProperty();
        usernameProperty();
        saltProperty();
        passwordProperty();
    }

    private SimpleStringProperty emailProperty()
    {
        if(email == null)
        {
            email = new SimpleStringProperty(this, "email", "null@nullmail.com");
        }
        return email;
    }

    public void setEmail(String email)
    {
        emailProperty().set(email);
    }

    public String getEmail()
    {
        return emailProperty().get();
    }

    private SimpleStringProperty nameProperty()
    {
        if(name == null)
        {
            name = new SimpleStringProperty(this, "name", "John Doe");
        }
        return name;
    }

    public void setName(String name)
    {
        nameProperty().set(name);
    }

    public String getName()
    {
        return nameProperty().get();
    }

    private SimpleStringProperty usernameProperty()
    {
        if(username == null)
        {
            username = new SimpleStringProperty(this, "username", "J. Doe");
        }
        return username;
    }

    public void setUsername(String username)
    {
        usernameProperty().set(username);
    }

    public String getUsername()
    {
        return usernameProperty().get();
    }

    private SimpleObjectProperty<byte[]> saltProperty()
    {
        if(salt == null)
        {
            salt = new SimpleObjectProperty<>(this, "salt", new byte[]{'N', 'U', 'L', 'L'});
        }
        return salt;
    }

    public void setSalt(byte[] salt)
    {
        saltProperty().set(salt);
    }

    public byte[] getSalt()
    {
        return saltProperty().get();
    }

    private SimpleObjectProperty<byte[]> passwordProperty()
    {
        if(password == null)
        {
            password = new SimpleObjectProperty<>(this, "password", new byte[] {'N', 'U', 'L', 'L'});
        }
        return password;
    }

    public void setPassword(byte[] encryptedPassword)
    {
        passwordProperty().set(encryptedPassword);
    }

    public byte[] getPassword()
    {
        return passwordProperty().get();
    }
}