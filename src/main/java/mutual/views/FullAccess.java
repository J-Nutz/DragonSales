package mutual.views;

/*
 * Created by Jonah on 12/17/2016.
 */
public enum FullAccess
{
    NULL,

    NEW_USER,
    LOGIN,
    LOCKED,

    SALE,
    QUICK_SALE,
    ADD_ITEM_DISCOUNT,
    PRODUCT_DISCOUNT_SELECTOR,

    HOME,

    INVENTORY,
    RESTOCK,

    MANAGE, // Hire/Fire/Promotion/Demotion/Etc of Employees
    SCHEDULE,
    HIRE_EMPLOYEE,
    //EMPLOYEE_STATS,  // Sales, Individual profit, etc.

    STATS,
    DAILY_STATS,
    WEEKLY_STATS,
    MONTHLY_STATS,
    YEARLY_STATS,
    ALL_TIME_STATS,

    SALE_STATS,
    PRODUCT_STATS,
    EMPLOYEE_STATS,

    SETTINGS
}