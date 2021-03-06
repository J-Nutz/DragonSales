package worker;

/*
 * Created by Jonah on 1/6/2017.
 */

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Timer;
import java.util.TimerTask;

public class ErrorMessageShower
{
    public static void showErrorMessage(Pane pane, String message)
    {
        Timer timer = new Timer(true);
        Label label = new Label(message);

        label.setMaxWidth(125);
        label.setWrapText(true);
        label.setTextFill(Color.RED);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(new Font(13));

        timer.schedule(new TimerTask()
        {
            public void run()
            {
                try
                {
                    Platform.runLater(() -> pane.getChildren().add(label));

                    Thread.sleep(4000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Platform.runLater(() -> pane.getChildren().remove(label));
                    timer.cancel();
                }
            }
        }, 100);
    }
}