package mutual.views.sale.components;

/*
 * Created by Jonah on 11/19/2016.
 */

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mutual.models.order.OrderTableModel;
import mutual.types.OrderFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderPanel extends BorderPane
{
    private static TableView<OrderFragment> orderTable;
    private OrderTableModel orderTableModel;

    private SaleControlsPanel saleControlsPanel;

    public OrderPanel()
    {
        orderTable = new TableView<>();
        orderTableModel = new OrderTableModel();

        saleControlsPanel = new SaleControlsPanel();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 2))));

        for(TableColumn<OrderFragment, ?> tableColumn : orderTableModel.getTableColumns())
        {
            orderTable.getColumns().add(tableColumn);
        }

        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTable.setEditable(false);
        orderTable.getColumns().addListener(new ListChangeListener<TableColumn>()
        {
            public boolean suspended;

            @Override
            public void onChanged(Change change)
            {
                change.next();

                if(change.wasReplaced() && !suspended)
                {
                    this.suspended = true;

                    orderTable.getColumns().setAll(orderTableModel.getTableColumns());

                    this.suspended = false;
                }
            }
        });
    }

    private void addComponents()
    {
        setCenter(orderTable);
        setBottom(saleControlsPanel);
    }

    public static void addToOrder(OrderFragment orderFragment)
    {
        if(orderFragment.hasDiscount())
        {
            orderTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        }
        else
        {
            orderTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        }

        boolean containsOrder = false;

        for(OrderFragment orderFragment1 : orderTable.getItems())
        {
            if(orderFragment1.getProductName()
                             .equals(orderFragment.getProductName()))
            {
                orderFragment1.setQuantity(orderFragment1.getQuantity() + orderFragment.getQuantity());
                orderTable.refresh();
                containsOrder = true;
                break;
            }
        }

        if(!containsOrder)
        {
            orderTable.getItems().add(orderFragment);
        }
    }

    public static void cancelOrder()
    {
        orderTable.getItems().clear();
    }

    public static boolean hasOrders()
    {
        return !orderTable.getItems().isEmpty();
    }

    public static BigDecimal calculateTotal()
    {
        BigDecimal total = new BigDecimal(0);

        for(OrderFragment orderFragment : orderTable.getItems())
        {
            if(orderFragment.hasDiscount())
            {
                total = total.add(orderFragment.getDiscountPrice().multiply(new BigDecimal(orderFragment.getQuantity())));
            }
            else
            {
                total = total.add(orderFragment.getSalePrice().multiply(new BigDecimal(orderFragment.getQuantity())));
            }
        }

        total = total.setScale(2, RoundingMode.CEILING);

        return total;
    }

    public static ObservableList<OrderFragment> getProducts()
    {
        return orderTable.getItems();
    }
}