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
    Map<Node, TextFlow> nodeMap = new HashMap<>();

    public BarGraph(Axis<X> xAxis, Axis<Y> yAxis)
    {
        super(xAxis, yAxis);
    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex)
    {
        super.seriesAdded(series, seriesIndex);

        for (int j = 0; j < series.getData().size(); j++)
        {
            Data<X, Y> item = series.getData().get(j);

            Text text = new Text(item.getYValue().toString());
            text.setStyle("-fx-font-size: 10pt;");

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

            //if(node.getBoundsInParent().getHeight() > 30)
            //{
                ((Text) textFlow.getChildren().get(0)).setFill(Color.GRAY);
                textFlow.resize(node.getBoundsInParent().getWidth(), 200);
                textFlow.relocate(node.getBoundsInParent().getMinX(), node.getBoundsInParent().getMinY() - 20);
            //}
        }
    }
}