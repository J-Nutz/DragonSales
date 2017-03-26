package mutual.views.sale.payment;

/*
 * Created by Jonah on 1/30/2017.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.math.BigDecimal;

public class AmountPaidSelector extends BorderPane
{
    private GridPane paidContainer;

    private Button oneDollarBtn;
    private Button fiveDollarBtn;
    private Button tenDollarBtn;
    private Button twentyDollarBtn;

    private Button pennyBtn;
    private Button nickleBtn;
    private Button dimeBtn;
    private Button quarterBtn;

    private Font buttonFont;

    private AmountPaidView amountPaidView;

    public AmountPaidSelector()
    {
        paidContainer = new GridPane();

        oneDollarBtn = new Button("$1");
        fiveDollarBtn = new Button("$5");
        tenDollarBtn = new Button("$10");
        twentyDollarBtn = new Button("$20");

        pennyBtn = new Button("$0.01");
        nickleBtn = new Button("$0.05");
        dimeBtn = new Button("$0.10");
        quarterBtn = new Button("$0.25");

        buttonFont = new Font(18);

        amountPaidView = new AmountPaidView();

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        paidContainer.setAlignment(Pos.CENTER);
        paidContainer.setPadding(new Insets(15));
        paidContainer.setHgap(15);
        paidContainer.setVgap(15);

        oneDollarBtn.setFont(buttonFont);
        oneDollarBtn.setMaxWidth(Double.MAX_VALUE);
        oneDollarBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(1)));

        fiveDollarBtn.setFont(buttonFont);
        fiveDollarBtn.setMaxWidth(Double.MAX_VALUE);
        fiveDollarBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(5)));

        tenDollarBtn.setFont(buttonFont);
        tenDollarBtn.setMaxWidth(Double.MAX_VALUE);
        tenDollarBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(10)));

        twentyDollarBtn.setFont(buttonFont);
        twentyDollarBtn.setMaxWidth(Double.MAX_VALUE);
        twentyDollarBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(20)));

        pennyBtn.setFont(buttonFont);
        pennyBtn.setMaxWidth(Double.MAX_VALUE);
        pennyBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(0.01)));

        nickleBtn.setFont(buttonFont);
        nickleBtn.setMaxWidth(Double.MAX_VALUE);
        nickleBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(0.05)));

        dimeBtn.setFont(buttonFont);
        dimeBtn.setMaxWidth(Double.MAX_VALUE);
        dimeBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(0.10)));

        quarterBtn.setFont(buttonFont);
        quarterBtn.setMaxWidth(Double.MAX_VALUE);
        quarterBtn.setOnAction(event -> amountPaidView.addPayment(new BigDecimal(0.25)));
    }

    private void addComponents()
    {
        paidContainer.add(oneDollarBtn, 0, 0);
        paidContainer.add(fiveDollarBtn, 1, 0);
        paidContainer.add(tenDollarBtn, 2, 0);
        paidContainer.add(twentyDollarBtn, 3, 0);

        paidContainer.add(pennyBtn, 0, 1);
        paidContainer.add(nickleBtn, 1, 1);
        paidContainer.add(dimeBtn, 2, 1);
        paidContainer.add(quarterBtn, 3, 1);

        setTop(paidContainer);
        setCenter(amountPaidView);
    }
}