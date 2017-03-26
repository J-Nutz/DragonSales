package mutual.models.order;

/*
 * Created by Jonah on 11/24/2016.
 */

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import mutual.types.OrderFragment;
import mutual.views.sale.components.SaleControlsPanel;
import mutual.views.sale.selector.ProductSelectorPanel;

import java.math.BigDecimal;
import java.util.ArrayList;

import static mutual.views.sale.components.SaleControlsPanel.updateTotal;

public class OrderTableModel
{
    public OrderTableModel() {}

    public ArrayList<TableColumn<OrderFragment, ?>> getTableColumns()
    {
        TableColumn<OrderFragment, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setStyle("-fx-alignment: CENTER;");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setResizable(false);
        quantityColumn.setEditable(false);

        TableColumn<OrderFragment, String> productNameColumn = new TableColumn<>("Product");
        productNameColumn.setStyle("-fx-alignment: CENTER;");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productNameColumn.setResizable(false);
        productNameColumn.setEditable(false);

        TableColumn<OrderFragment, BigDecimal> productPriceColumn = new TableColumn<>("Price");
        productPriceColumn.setStyle("-fx-alignment: CENTER;");
        productPriceColumn.setResizable(false);
        productPriceColumn.setEditable(false);

        TableColumn<OrderFragment, Button> removeProductColumn = new TableColumn<>("Remove");
        removeProductColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        removeProductColumn.setCellFactory(addButtonCellFactory());
        removeProductColumn.setResizable(false);
        removeProductColumn.setEditable(false);

        ArrayList<TableColumn<OrderFragment, ?>> tableColumns = new ArrayList<>();

        tableColumns.add(quantityColumn);
        tableColumns.add(productNameColumn);
        tableColumns.add(productPriceColumn);
        tableColumns.add(removeProductColumn);

        return tableColumns;
    }

    private Callback<TableColumn<OrderFragment, Button>, TableCell<OrderFragment, Button>> addButtonCellFactory()
    {
        return new Callback<TableColumn<OrderFragment, Button>, TableCell<OrderFragment, Button>>()
        {
            @Override
            public TableCell<OrderFragment, Button> call(final TableColumn<OrderFragment, Button> param)
            {
                return new TableCell<OrderFragment, Button>()
                {
                    final Button btn = new Button("X");

                    @Override
                    public void updateItem(Button item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if(!empty)
                        {
                            btn.setOnAction((ActionEvent event) ->
                            {
                                getTableView().getItems().remove(getIndex());
                                updateTotal();

                                if(getTableView().getItems().isEmpty())
                                {
                                    BorderPane centerView = (BorderPane) getTableView().getParent().getParent();
                                    centerView.setCenter(new ProductSelectorPanel());

                                    BorderPane rightView = (BorderPane) getTableView().getParent();
                                    rightView.setBottom(new SaleControlsPanel());
                                }
                            });

                            setGraphic(btn);
                            setText(null);
                            setAlignment(Pos.CENTER);
                        }
                        else
                        {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        };
    }
}