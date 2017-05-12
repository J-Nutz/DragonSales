package admin.home;

/*
 * Created by Jonah on 12/4/2016.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import mutual.views.FullAccess;

public class ViewSelector extends HBox
{
    private View saleView;
    private View inventoryView;
    private View employeeView;
    private View statisticsView;
    private View settingsView;
    private View closeView;

    public ViewSelector() // TODO: Recommend products to put on sale base on sales per week, last sold, frequency sold, etc.
    {
        saleView = new View("/sale.png", "Sales", FullAccess.SALE, new String[] {"Quick Sale", "Regular Sale", "View Discounts", "Add Discount"}, new FullAccess[] {FullAccess.QUICK_SALE, FullAccess.SALE, FullAccess.DISCOUNTS, FullAccess.ADD_DISCOUNT});
        inventoryView = new View("/inventory.png", "Inventory", FullAccess.INVENTORY, new String[] {"View Products", "Add Product"}, new FullAccess[]{FullAccess.INVENTORY, FullAccess.STOCK});
        employeeView = new View("/employees.png", "Employees", FullAccess.MANAGE, new String[] {"Manage", "Schedule", "Hire"}, new FullAccess[]{FullAccess.MANAGE, FullAccess.SCHEDULE, FullAccess.HIRE_EMPLOYEE});
        statisticsView = new View("/statistics.png", "Statistics", FullAccess.STATS, new String[] {"Sales", "Products"}, new FullAccess[]{FullAccess.SALE_STATS, FullAccess.PRODUCT_STATS});
        settingsView = new View("/settings.png", "Settings", FullAccess.SETTINGS, new String[] {"Coming Soon!"}, new FullAccess[] {FullAccess.NULL});
        closeView = new View("/close.png", "Close", new Alert(Alert.AlertType.WARNING, "About To Close Dragon Sales!\n\nDo You Want To Continue?", ButtonType.YES, ButtonType.NO), new String[]{"Lock", "Logout"}, new FullAccess[]{FullAccess.LOCKED, FullAccess.LOGIN});

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setSpacing(15);
        setPadding(new Insets(15, 0, 15, 0));
        setAlignment(Pos.CENTER);
    }

    private void addComponents()
    {
        getChildren().addAll(saleView, inventoryView, employeeView, statisticsView,  settingsView, closeView);
    }
}