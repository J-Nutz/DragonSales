package mutual.types;

/*
 * Created by Jonah on 1/5/2017.
 */

public class Result
{
    private boolean completed = false;
    private String message = "";

    public Result() {}

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}