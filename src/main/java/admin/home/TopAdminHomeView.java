package admin.home;

/*
 * Created by Jonah on 12/4/2016.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mutual.views.FullAccess;

import static admin.home.ViewContainer.switchView;

public class TopAdminHomeView extends BorderPane
{
    private Button lockButton;
    private Label dragonSalesLabel;
    private Button quickSaleButton;

    public TopAdminHomeView()
    {
        lockButton = new Button("Lock");
        dragonSalesLabel = new Label("Dragon Sales");
        quickSaleButton = new Button("Quick Sale");

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setPadding(new Insets(10, 20, 10, 20));
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        setOnMouseClicked(e -> switchView(getParent(), FullAccess.HOME));
        setOnMouseEntered(e -> setCursor(Cursor.HAND));
        setOnMouseExited(e -> setCursor(Cursor.DEFAULT));

        lockButton.setOnAction(event -> switchView(getParent(), FullAccess.LOCKED));
        lockButton.setFont(new Font(16));

        dragonSalesLabel.setFont(new Font(36));

        quickSaleButton.setOnAction(event -> switchView(getParent(), FullAccess.QUICK_SALE)); // TODO: Make Dialog?
        quickSaleButton.setFont(new Font(16));
    }

    private void addComponents()
    {
        setLeft(lockButton);
        setAlignment(lockButton, Pos.CENTER);

        setCenter(dragonSalesLabel);

        setRight(quickSaleButton);
        setAlignment(quickSaleButton, Pos.CENTER);
    }
}