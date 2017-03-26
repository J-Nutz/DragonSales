package admin.home;

/*
 * Created by Jonah on 11/8/2016.
 */

import javafx.scene.layout.BorderPane;

public class AdminHomeView extends BorderPane
{
    public AdminHomeView()
    {
        setCenter(new ViewSelector());
    }
}