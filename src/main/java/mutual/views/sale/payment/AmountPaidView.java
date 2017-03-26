package mutual.views.sale.payment;

/*
 * Created by Jonah on 2/1/2017.
 */

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mutual.views.sale.components.OrderPanel;

import java.math.BigDecimal;

public class AmountPaidView extends VBox
{
    private GridPane amountPaidContainer;

    private Label twentyDollarLabel;
    private Label numOfTwentiesLabel;
    private int numOfTwenties = 0;

    private Label tenDollarLabel;
    private Label numOfTensLabel;
    private int numOfTens = 0;

    private Label fiveDollarLabel;
    private Label numOfFivesLabel;
    private int numOfFives = 0;

    private Label oneDollarLabel;
    private Label numOfOnesLabel;
    private int numOfOnes = 0;

    private Label quarterLabel;
    private Label numOfQuartersLabel;
    private int numOfQuarters = 0;

    private Label dimeLabel;
    private Label numOfDimesLabel;
    private int numOfDimes = 0;

    private Label nickleLabel;
    private Label numOfNicklesLabel;
    private int numOfNickles = 0;

    private Label pennyLabel;
    private Label numOfPenniesLabel;
    private int numOfPennies = 0;

    private Font font;

    private TotalView totalView;

    public AmountPaidView()
    {
        amountPaidContainer = new GridPane();

        twentyDollarLabel = new Label("$20.00");
        numOfTwentiesLabel = new Label(" x " + numOfTwenties);

        tenDollarLabel = new Label("$10.00");
        numOfTensLabel = new Label(" x " + numOfTens);

        fiveDollarLabel = new Label("$5.00");
        numOfFivesLabel = new Label(" x " + numOfFives);

        oneDollarLabel = new Label("$1.00");
        numOfOnesLabel = new Label(" x " + numOfOnes);

        quarterLabel = new Label("$0.25");
        numOfQuartersLabel = new Label(" x " + numOfQuarters);

        dimeLabel = new Label("$0.10");
        numOfDimesLabel = new Label(" x " + numOfDimes);

        nickleLabel = new Label("$0.05");
        numOfNicklesLabel = new Label(" x " + numOfNickles);

        pennyLabel = new Label("$0.01");
        numOfPenniesLabel = new Label(" x " + numOfPennies);

        font = new Font(16);

        totalView = new TotalView(OrderPanel.calculateTotal());

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        amountPaidContainer.setAlignment(Pos.CENTER);
        amountPaidContainer.setHgap(15);
        amountPaidContainer.setVgap(15);

        totalView.addResetAction(event ->
        {
            numOfTwenties = 0;
            numOfTens = 0;
            numOfFives = 0;
            numOfOnes = 0;

            numOfQuarters = 0;
            numOfDimes = 0;
            numOfNickles = 0;
            numOfPennies = 0;

            numOfTwentiesLabel.setText(" x " + numOfTwenties);
            numOfTensLabel.setText(" x " + numOfTens);
            numOfFivesLabel.setText(" x " + numOfFives);
            numOfOnesLabel.setText(" x " + numOfOnes);

            numOfQuartersLabel.setText(" x " + numOfQuarters);
            numOfDimesLabel.setText(" x " + numOfDimes);
            numOfNicklesLabel.setText(" x " + numOfNickles);
            numOfPenniesLabel.setText(" x " + numOfPennies);

            totalView.resetFields();
        });
    }

    private void addComponents()
    {
        amountPaidContainer.add(twentyDollarLabel, 0, 0);
        amountPaidContainer.add(numOfTwentiesLabel, 1, 0);

        amountPaidContainer.add(tenDollarLabel, 0, 1);
        amountPaidContainer.add(numOfTensLabel, 1, 1);

        amountPaidContainer.add(fiveDollarLabel, 0, 2);
        amountPaidContainer.add(numOfFivesLabel, 1, 2);

        amountPaidContainer.add(oneDollarLabel, 0, 3);
        amountPaidContainer.add(numOfOnesLabel, 1, 3);



        amountPaidContainer.add(quarterLabel, 0, 4);
        amountPaidContainer.add(numOfQuartersLabel, 1, 4);

        amountPaidContainer.add(dimeLabel, 0, 5);
        amountPaidContainer.add(numOfDimesLabel, 1, 5);

        amountPaidContainer.add(nickleLabel, 0, 6);
        amountPaidContainer.add(numOfNicklesLabel, 1, 6);

        amountPaidContainer.add(pennyLabel, 0, 7);
        amountPaidContainer.add(numOfPenniesLabel, 1, 7);

        getChildren().add(amountPaidContainer);
        getChildren().add(totalView);

        amountPaidContainer.getChildren().forEach(node ->
        {
            if(node instanceof Label)
            {
                Label label = (Label) node;
                label.setFont(font);
            }
        });
    }

    public void addPayment(BigDecimal paid)
    {
        if(paid.intValue() == 20)
        {
            numOfTwenties++;
            numOfTwentiesLabel.setText(" x " + numOfTwenties);
        }
        else if(paid.intValue() == 10)
        {
            numOfTens++;
            numOfTensLabel.setText(" x " + numOfTens);
        }
        else if(paid.intValue() == 5)
        {
            numOfFives++;
            numOfFivesLabel.setText(" x " + numOfFives);
        }
        else if(paid.intValue() == 1)
        {
            numOfOnes++;
            numOfOnesLabel.setText(" x " + numOfOnes);
        }
        else if(paid.compareTo(new BigDecimal(0.25)) == 0)
        {
            numOfQuarters++;
            numOfQuartersLabel.setText(" x " + numOfQuarters);
        }
        else if(paid.compareTo(new BigDecimal(0.10)) == 0)
        {
            numOfDimes++;
            numOfDimesLabel.setText(" x " + numOfDimes);
        }
        else if(paid.compareTo(new BigDecimal(0.05)) == 0)
        {
            numOfNickles++;
            numOfNicklesLabel.setText(" x " + numOfNickles);
        }
        else if(paid.compareTo(new BigDecimal(0.01)) == 0)
        {
            numOfPennies++;
            numOfPenniesLabel.setText(" x " + numOfPennies);
        }

        totalView.setAmountPaid(paid);
    }
}