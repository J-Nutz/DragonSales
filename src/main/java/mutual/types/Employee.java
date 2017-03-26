package mutual.types;

/*
 * Created by Jonah on 12/20/2016.
 */

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class Employee
{
    private SimpleStringProperty imagePath;
    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleObjectProperty<Position> position;
    private SimpleObjectProperty<BigDecimal> wage;
    private SimpleObjectProperty<Date> dateHired;

    public Employee()
    {

    }

    public SimpleStringProperty imagePathProperty()
    {
        if(imagePath == null)
        {
            imagePath = new SimpleStringProperty(this, "imagePath", "/null.png");
        }
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        imagePathProperty().set(imagePath);
    }

    public File getImagePath()
    {
        return new File(imagePathProperty().get());
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

    private SimpleObjectProperty<Position> positionProperty()
    {
        if(position == null)
        {
            position = new SimpleObjectProperty<>(this, "position", Position.CASHIER);
        }
        return position;
    }

    public void setPosition(Position position)
    {
        positionProperty().set(position);
    }

    public Position getPosition()
    {
        return positionProperty().get();
    }

    private SimpleObjectProperty<BigDecimal> wageProperty()
    {
        if(wage == null)
        {
            wage = new SimpleObjectProperty<>(this, "wage", new BigDecimal(0));
        }
        return wage;
    }

    public void setWage(BigDecimal wage)
    {
        wageProperty().set(wage);
    }

    public BigDecimal getWage()
    {
        return wageProperty().get();
    }

    private SimpleObjectProperty<Date> dateHiredProperty()
    {
        if(dateHired == null)
        {
            dateHired = new SimpleObjectProperty<>(this, "dateHired", Date.valueOf(LocalDate.now()));
        }
        return dateHired;
    }

    public void setDateHired(Date dateHired)
    {
        dateHiredProperty().set(dateHired);
    }

    public Date getDateHired()
    {
        return dateHiredProperty().get();
    }
}