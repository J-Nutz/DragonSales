package mutual.types;

/*
 * Created by Jonah on 1/3/2017.
 */
public enum Position
{
    OWNER(3),
    MANAGER(2),
    CASHIER(1),
    STOCKER(0);

    int accessLevel;

    Position(int accessLevel)
    {
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel()
    {
        return accessLevel;
    }

    public static Position asPosition(String position)
    {
        switch(position.toLowerCase())
        {
            case "owner":
                return OWNER;

            case "manager":
                return MANAGER;

            case "cashier":
                return CASHIER;

            case "stocker":
                return STOCKER;

            default:
                return CASHIER;
        }
    }
}
