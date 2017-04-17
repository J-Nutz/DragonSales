/*
 * Created by Jonah on 4/11/2017.
 */

public class Dog implements Animal
{
    public Dog()
    {
        //age = 6; //Doesn't work. Age is final.
        System.out.println("Dog Is " + age + " Years Old...");
    }

    //Can also have methods that aren't in the Animal Interface
    public void ageInHumanYears()
    {
        int humanYears = age * 7;

        System.out.println("Dog Is " + humanYears + " In Human Years...");
    }

    @Override
    public void eat()
    {
        System.out.println("Dog Is Eating...");
    }

    @Override
    public void move()
    {
        System.out.println("Dog Is Moving...");
    }

    @Override
    public void sleep(int hours)
    {
        System.out.println("Dog Is Sleeping For " + hours + " Hours...");
    }
}