package admin.home;

/*
 * Created by Jonah on 11/20/2016.
 */

import admin.employees.EmployeeScheduler;
import admin.employees.HireEmployeeView;
import admin.employees.ManageEmployeesView;
import admin.inventory.AdminInventoryView;
import admin.inventory.StockInventoryView;
import database.tables.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import mutual.types.Discount;
import mutual.types.OrderFragment;
import mutual.types.Product;
import mutual.views.FullAccess;
import mutual.views.discounts.AddDiscountDialog;
import mutual.views.discounts.ProductDiscountsView;
import mutual.views.login.LockedView;
import mutual.views.login.LoginView;
import mutual.views.login.NewUserView;
import mutual.views.sale.QuickSaleDialog;
import mutual.views.sale.SaleView;
import mutual.views.sale.selector.ProductSelectorPanel;
import mutual.views.sale.selector.ProductView;
import mutual.views.statistics.SaleStatsView;
import mutual.views.statistics.StatisticsTracker;

import java.util.Optional;

public class ViewContainer extends BorderPane
{
    private TopAdminHomeView topAdminHomeView;
    private static FullAccess currentView;

    private ProductSelectorPanel productSelectorPanel;

    public ViewContainer(FullAccess initialView)
    {
        currentView = initialView;
        topAdminHomeView = new TopAdminHomeView();

        productSelectorPanel = new ProductSelectorPanel();

        initComponents();
    }

    private void initComponents()
    {
        setId("viewContainer");
        setView(currentView);

        productSelectorPanel.setOnProductViewClicked(event ->
        {
            ProductView productView = (ProductView) event.getSource();
            Product product = productView.getProduct();
            AddDiscountDialog addDiscountDialog = new AddDiscountDialog(product);
            Optional<Discount> productDiscount = addDiscountDialog.showAndWait();

            if(productDiscount.isPresent())
            {
                DiscountsTable.addDiscount(productDiscount.get());
            }
            else
            {
                addDiscountDialog.close();
            }

            setView(FullAccess.ADD_ITEM_DISCOUNT);
        });
    }

    private void checkLock(Node view, boolean locked)
    {
        if(locked)
        {
            setCenter(view);
            setTop(null);
        }
        else
        {
            setCenter(view);
            setTop(topAdminHomeView);
        }
    }

    private boolean setView(FullAccess view)
    {
        //Use fullAccess like 'new HomeView(fullAccess);'

        switch(view)
        {
            case NEW_USER:
                checkLock(new NewUserView(), true);
                break;

            case LOGIN:
                checkLock(new LoginView(), true);
                break;

            case LOCKED:
                checkLock(new LockedView(getCurrentView()), true);
                break;

            //////////////////////////////////////////////////////

            case SALE:
                checkLock(new SaleView(), false);
                break;

            case QUICK_SALE:
                showQuickSale();
                break;

            case ADD_ITEM_DISCOUNT:
                checkLock(new ProductDiscountsView(), false);
                break;

            case PRODUCT_DISCOUNT_SELECTOR:
                checkLock(productSelectorPanel, false);
                break;

            /////////////////////////////////////////////////////

            case HOME:
                checkLock(new AdminHomeView(), false);
                break;

            case INVENTORY:
                checkLock(new AdminInventoryView(), false);
                break;

            case RESTOCK:
                AdminInventoryView adminInventoryView = new AdminInventoryView();
                adminInventoryView.setRight(new StockInventoryView());
                checkLock(adminInventoryView, false);
                break;

            /////////////////////////////////////////////////////

            case MANAGE:
                checkLock(new ManageEmployeesView(), false);
                break;

            case SCHEDULE:
                checkLock(new EmployeeScheduler(), false);
                break;

            case HIRE_EMPLOYEE:
                checkLock(new HireEmployeeView(), false);
                break;

            case EMPLOYEE_STATS:
                checkLock(new BorderPane(), false);
                break;

            /////////////////////////////////////////////////////

            case STATS:
                checkLock(new BorderPane(), false);
                break;

            case SALE_STATS:
                checkLock(new SaleStatsView(), false);
                break;

            case PRODUCT_STATS:
                checkLock(new BorderPane(), false);
                break;

            /////////////////////////////////////////////////////

            case SETTINGS:
                checkLock(new BorderPane(), false);
                break;

            default:
                return false;
        }

        currentView = view;
        return true;
    }

    public static void switchView(Node node, FullAccess view)
    {
        ViewContainer viewContainer = (ViewContainer) node.getScene().getRoot().lookup("#viewContainer");

        if(viewContainer == null)
        {
            System.out.println("No Node Found");
        }
        else
        {
            viewContainer.setView(view);
        }
    }

    public static FullAccess getCurrentView()
    {
        return currentView;
    }

    private void showQuickSale()
    {
        QuickSaleDialog quickSaleDialog = new QuickSaleDialog();
        quickSaleDialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        Optional<ObservableList<OrderFragment>> orderFragmentResult = quickSaleDialog.showAndWait();

        if(orderFragmentResult.isPresent())
        {
            for(OrderFragment orderFragment : orderFragmentResult.get())
            {
                Product product = orderFragment.getProduct();
                String productName = product.getName();
                int quantityPurchased = orderFragment.getQuantity();
                int currentQuantity = product.getCurrentQuantity();
                int updatedQuantity = currentQuantity - quantityPurchased;
                product.setCurrentQuantity(updatedQuantity);

                ProductsTable.updateProduct(productName, product);
            }

            StatisticsTracker statisticsTracker = new StatisticsTracker();
            statisticsTracker.logSale(orderFragmentResult.get());

            DailyStatsTable.logSale(statisticsTracker);
            AllTimeStatsTable.updateStats(statisticsTracker);

            quickSaleDialog.close();
        }
        else
        {
            quickSaleDialog.close();
        }
    }
}