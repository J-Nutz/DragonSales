import admin.home.ViewContainer;
import database.DatabaseExecutor;
import database.tables.UsersTable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mutual.views.FullAccess;

public class Main extends Application
{
    /**
     * TODO:
     *       * Settings
     *           Theme
     *           Tax ???
     *
     *       * Error Messages
     *           No internet for email
     *           Username taken
     *           Product not added
     *           Etc.
     *
     *        * Look into Proguard or something similar
     *           Removes all unused code in jars
     *
     *        * Remove everything on delete!! - If(employee.fire()) Delete User row, Employee row, Employee image, Schedule
     *
     *        * Make Product in InventoryView Look better
     */

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        int numOfUsers = UsersTable.numberOfUsers();

        ViewContainer viewContainer = numOfUsers < 1 ? new ViewContainer(FullAccess.NEW_USER) : new ViewContainer(FullAccess.LOGIN);
        Scene container = new Scene(viewContainer);

        primaryStage.setOnCloseRequest(e -> DatabaseExecutor.close());
        primaryStage.setTitle("Dragon Sales");
        primaryStage.setMaximized(true);
        primaryStage.setScene(container);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        try
        {
            Class.forName("org.h2.Driver");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        launch(args);
    }
}