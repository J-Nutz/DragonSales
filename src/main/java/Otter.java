/*
 * Created by Jonah on 3/15/2017.
 */

public class Otter extends Animal
{
    public Otter(String name)
    {
        super(name); //Invokes the constructor in Animal.java
    }

    @Override //Overrides the default implementation of feed() in Animal
    public void feed()
    {
        if(getHungerLevel() < 10)
        {
            setHungerLevel(getHungerLevel() + 2);
            System.out.println("Fed " + name + " 2 Meals");
        }
        else
        {
            System.out.println(name + " is full");
        }
    }

    @Override //Overrides the default implementation of speak() in Animal
    public void speak()
    {
        System.out.println(name + "says s/he is an Otter");
    }
}