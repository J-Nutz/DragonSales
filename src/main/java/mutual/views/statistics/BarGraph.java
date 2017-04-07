package mutual.views.statistics;

/*
 * Created by Jonah on 4/5/2017.
 */

import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Map;

public class BarGraph<X, Y> extends BarChart<X, Y>
{
    private boolean rotated = false;
    private boolean valuesAreCurrency = false;
    private Map<Node, TextFlow> nodeMap = new HashMap<>();

    public BarGraph(Axis<X> xAxis, Axis<Y> yAxis)
    {
        super(xAxis, yAxis);
    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex)
    {
        super.seriesAdded(series, seriesIndex);

        for(int j = 0; j < series.getData().size(); j++)
        {
            Data<X, Y> item = series.getData().get(j);
            Text text;

            if(valuesAreCurrency)
            {
                text = new Text("$" + item.getYValue().toString());
            }
            else
            {
                text = new Text("" + item.getYValue().toString());
            }

            if(series.getData().size() > 13)
            {
                rotated = true;
                text.setRotate(270);
                text.setStyle("-fx-font-size: 8pt;");
            }
            else
            {
                rotated = false;
                text.setStyle("-fx-font-size: 12pt;");
            }

            TextFlow textFlow = new TextFlow(text);
            textFlow.setTextAlignment(TextAlignment.CENTER);

            nodeMap.put(item.getNode(), textFlow);
            this.getPlotChildren().add(textFlow);
        }
    }

    @Override
    protected void seriesRemoved(final Series<X, Y> series)
    {
        super.seriesRemoved(series);

        for(Node node : nodeMap.keySet())
        {
            Node text = nodeMap.get(node);
            this.getPlotChildren().remove(text);
        }

        nodeMap.clear();
    }

    @Override
    protected void layoutPlotChildren()
    {
        super.layoutPlotChildren();

        for(Node node : nodeMap.keySet())
        {
            TextFlow textFlow = nodeMap.get(node);
            ((Text) textFlow.getChildren().get(0)).setFill(Color.GRAY);

            if(rotated)
            {
                textFlow.resize(node.getBoundsInParent().getWidth() + 25, 200);
                textFlow.relocate(node.getBoundsInParent().getMinX() - 13, node.getBoundsInParent().getMinY() - 25);
            }
            else
            {
                textFlow.resize(node.getBoundsInParent().getWidth(), 200);
                textFlow.relocate(node.getBoundsInParent().getMinX(), node.getBoundsInParent().getMinY() - 25);
            }
        }
    }

    public void areValuesCurrency(boolean valuesAreCurrency)
    {
        this.valuesAreCurrency = valuesAreCurrency;
    }

    public boolean getAreValuesCurrency()
    {
        return valuesAreCurrency;
    }
}