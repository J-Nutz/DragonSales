package admin.home;

/*
 * Created by Jonah on 12/4/2016.
 */

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;
import mutual.views.FullAccess;

import java.util.Optional;

import static admin.home.ViewContainer.switchView;

public class View extends GridPane
{
    private ImageView imageView;
    private Label viewLabel;
    private Alert alert;
    private FullAccess viewToGoTo;

    private String[] titles;
    private FullAccess[] subViews;

    public View(String imageName, String title, FullAccess mainView, String[] titles, FullAccess[] subViews)
    {
        Image image = new Image(getClass().getResourceAsStream(imageName));

        imageView = new ImageView(image);
        viewLabel = new Label(title);
        viewToGoTo = mainView;

        this.titles = titles;
        this.subViews = subViews;

        viewLabel.setOnMouseClicked(e -> switchView(getParent(), viewToGoTo));
        imageView.setOnMouseClicked(e -> switchView(getParent(), viewToGoTo));

        initComponents();
        addComponents();
    }

    public View(String imageName, String title, Alert confirmation, String[] titles, FullAccess[] subViews)
    {
        Image image = new Image(getClass().getResourceAsStream(imageName));

        imageView = new ImageView(image);
        viewLabel = new Label(title);
        alert = confirmation;

        this.titles = titles;
        this.subViews = subViews;

        viewLabel.setOnMouseClicked(e -> showAlert());
        imageView.setOnMouseClicked(e -> showAlert());

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        setVgap(5);
        setPadding(new Insets(15));

        viewLabel.setFont(new Font(24));
    }

    private void addComponents()
    {
        int column = 0;
        int row = 3;

        add(imageView, 0, 0);
        add(viewLabel, 0, 1);

        for(int i = 0; i < titles.length; i++)
        {
            final int finalI = i;

            Label subViewLabel = new Label(titles[finalI]);
            subViewLabel.setPadding(new Insets(0, 0, 0, 15));
            subViewLabel.setFont(new Font(18));
            subViewLabel.setOnMouseClicked(e -> switchView(getParent(), subViews[finalI]));

            add(subViewLabel, column, row);
            row++;
        }

        getChildren().forEach(node ->
        {
            node.setOnMouseEntered(e -> setCursor(Cursor.HAND));
            node.setOnMouseExited(e -> setCursor(Cursor.DEFAULT));
        });
    }

    public void showAlert()
    {
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get().equals(ButtonType.YES))
        {
            fireEvent(new WindowEvent(this.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
        }
        else
        {
            alert.close();
        }
    }
}