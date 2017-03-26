/*
 * Created by Jonah on 3/15/2017.
 */

public class Animal
{
    //Two Attributes (Variables)
    public String name;
    public int hungerLevel;

    //One Constructor
    public Animal(String name)
    {
        this.name = name;
        hungerLevel = 7;
    }

    //Six Behaviors (Methods)
    public void setName(String newName)
    {
        this.name = newName;
    }

    public String getName()
    {
        return name;
    }

    public void setHungerLevel(int newHungerLevel)
    {
        this.hungerLevel = newHungerLevel;
    }

    public int getHungerLevel()
    {
        return hungerLevel;
    }

    public void feed()
    {
        if(hungerLevel < 10)
        {
            hungerLevel += 1;
            System.out.println("Fed " + name + " 1 Meal");
        }
        else
        {
            System.out.println(name + " is full");
        }
    }

    public void speak()
    {
        System.out.println(name + " says s/he is an Animal");
    }
}