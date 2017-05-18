package admin.home;

/*
 * Created by Jonah on 12/4/2016.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import mutual.views.View;

public class ViewSelector extends HBox
{
    private ViewSelection saleView;
    private ViewSelection inventoryView;
    private ViewSelection employeeView;
    private ViewSelection statisticsView;
    private ViewSelection settingsView;
    private ViewSelection closeView;

    public ViewSelector()
    {
        String[] saleViewTitles = new String[]{"Sales", "Quick Sale", "Regular Sale", "View Discounts", "Add Discount"};
        View[] saleViewViews = new View[]{View.SALE, View.QUICK_SALE, View.SALE, View.DISCOUNTS, View.ADD_DISCOUNT};
        saleView = new ViewSelection("/sale.png", saleViewTitles, saleViewViews);

        String[] inventoryViewTitles = new String[]{"Inventory", "View Products", "Add Product"};
        View[] inventoryViewViews = new View[]{View.INVENTORY, View.INVENTORY, View.STOCK};
        inventoryView = new ViewSelection("/inventory.png", inventoryViewTitles, inventoryViewViews);

        String[] employeeViewTitles = new String[]{"Employees", "Manage", "Schedule", "Hire"};
        View[] employeeViewViews = new View[]{View.MANAGE, View.MANAGE, View.SCHEDULE, View.HIRE_EMPLOYEE};
        employeeView = new ViewSelection("/employees.png", employeeViewTitles, employeeViewViews);

        String[] statisticsViewTitles = new String[]{"Statistics", "Sales", "Products"};
        View[] statisticsViewViews = new View[]{View.STATS, View.SALE_STATS, View.PRODUCT_STATS};
        statisticsView = new ViewSelection("/statistics.png", statisticsViewTitles, statisticsViewViews);

        String[] settingsViewTitles = new String[]{"Settings", "Coming Soon!"};
        View[] settingsViewViews = new View[]{View.SETTINGS, View.NULL};
        settingsView = new ViewSelection("/settings.png", settingsViewTitles, settingsViewViews);

        Alert closeAlert = new Alert(Alert.AlertType.WARNING, "About To Close Dragon Sales!\n\nDo You Want To Continue?", ButtonType.YES, ButtonType.NO);
        String[] closeViewTitles = new String[]{"Close", "Lock", "Logout"};
        View[] closeViewViews = new View[]{View.NULL, View.LOCKED, View.LOGIN};
        closeView = new ViewSelection("/close.png", closeAlert, closeViewTitles, closeViewViews);

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