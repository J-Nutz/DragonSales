/*
 * Created by Jonah on 4/11/2017.
 */

public interface Animal
{
    int age = 5; //Can Only Change The Value Here. Same as final int age = 5;

    void eat();
    void move();
    void sleep(int hours);
}