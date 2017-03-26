package mutual.views;

/*
 * Created by Jonah on 1/3/2017.
 */

public enum LimitedAccess
{
    NULL,

    NEW_USER,
    LOGIN,
    LOCKED,

    SALE,
    QUICK_SALE,
    ADD_ITEM_SALE,

    HOME,

    INVENTORY,
    RESTOCK,

    //EMPLOYEES,
    //MANAGE, // Hire/Fire/Promotion/Demotion/Etc of Employees
    //EMPLOYEE_STATS,  // Sales, Individual profit, etc.

    STATS,
    PRODUCT_STATS,
    SALE_STATS,
    PROFIT_STATS,

    SETTINGS
}
