package mutual.views.sale.payment;

/*
 * Created by Jonah on 2/1/2017.
 */

import database.tables.AllTimeStatsTable;
import database.tables.DailyStatsTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.types.OrderFragment;
import mutual.views.View;
import mutual.views.statistics.StatisticsTracker;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static admin.home.ViewContainer.switchView;
import static mutual.views.sale.components.OrderPanel.getOrderFragments;

public class TotalView extends VBox
{
    private Label totalLabel;
    private Label paidLabel;
    private Label returnLabel;

    private BigDecimal total = new BigDecimal(0);
    private BigDecimal totalPaid = new BigDecimal(0);
    private BigDecimal returnAmount = new BigDecimal(0);

    private Font font = new Font(18);

    private HBox bottomContainer;
    private Button finishedBtn;
    private Button resetBtn;
    private Button cancelBtn;

    private StatisticsTracker statisticsTracker;

    public TotalView(BigDecimal total1)
    {
        total = total1;

        totalLabel = new Label("Total: $" + total.toString());
        paidLabel = new Label("Paid: $0.00");
        returnLabel = new Label("Remaining: $" + total.toString());

        bottomContainer = new HBox(15);
        finishedBtn = new Button("Finished");
        resetBtn = new Button("Reset");
        cancelBtn = new Button("Cancel");

        statisticsTracker = new StatisticsTracker();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(10));

        totalLabel.setFont(font);
        paidLabel.setFont(font);
        returnLabel.setFont(font);
        returnLabel.setTextFill(Color.RED);

        bottomContainer.setAlignment(Pos.BOTTOM_CENTER);

        finishedBtn.setOnAction(event ->
        {
            ObservableList<OrderFragment> orderFragments = getOrderFragments();

            statisticsTracker.logSale(orderFragments);

            DailyStatsTable.logSale(statisticsTracker);
            AllTimeStatsTable.updateStats(statisticsTracker);

            switchView(getParent(), View.SALE);
        });

        cancelBtn.setOnAction(event -> switchView(getParent(), View.SALE));
    }

    private void addComponents()
    {
        bottomContainer.getChildren().addAll(finishedBtn, resetBtn, cancelBtn);

        getChildren().add(0, new Separator(Orientation.HORIZONTAL));
        getChildren().add(1, totalLabel);
        getChildren().add(2, paidLabel);
        getChildren().add(3, new Separator(Orientation.HORIZONTAL));
        getChildren().add(4, returnLabel);
        getChildren().add(5, new Separator(Orientation.HORIZONTAL));
        getChildren().add(6, bottomContainer);
    }

    public void setAmountPaid(BigDecimal paid)
    {
        totalPaid = totalPaid.add(paid);
        totalPaid = totalPaid.setScale(2, RoundingMode.HALF_DOWN);

        returnAmount = totalPaid.subtract(total);
        returnAmount = returnAmount.setScale(2, RoundingMode.HALF_DOWN);

        if(returnAmount.compareTo(new BigDecimal(0)) == -1)
        {
            returnLabel.setText("Remaining: $" + returnAmount.multiply(new BigDecimal(-1)).toString());
            returnLabel.setTextFill(Color.RED);
        }
        else
        {
            returnLabel.setTextFill(Color.GREEN);
            returnLabel.setText("Return: $" + returnAmount.toString());
        }

        paidLabel.setText("Paid: $" + totalPaid.toString());
    }

    public void addResetAction(EventHandler<ActionEvent> event)
    {
        resetBtn.setOnAction(event);
    }

    public void resetFields()
    {
        BigDecimal zero = new BigDecimal(0);

        totalPaid = totalPaid.multiply(zero);
        returnAmount = returnAmount.multiply(zero);

        paidLabel.setText("Paid: $0.00");

        returnLabel.setTextFill(Color.RED);
        returnLabel.setText("Remaining: $" + total.toString());
    }
}