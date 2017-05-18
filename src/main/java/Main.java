import admin.home.ViewContainer;
import database.DatabaseExecutor;
import database.tables.UsersTable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mutual.views.View;

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
        final long startTime = System.currentTimeMillis();

        int numOfUsers = UsersTable.numberOfUsers();

        ViewContainer viewContainer = numOfUsers < 1 ? new ViewContainer(View.SIGN_UP) : new ViewContainer(View.LOGIN);
        Scene container = new Scene(viewContainer);

        primaryStage.setOnCloseRequest(e -> DatabaseExecutor.close());
        primaryStage.setTitle("Dragon Sales");
        primaryStage.setMaximized(true);
        primaryStage.setScene(container);
        primaryStage.show();

        final long endTime = System.currentTimeMillis();

        System.out.println("Launched In: " + ((endTime - startTime)) + "ms");
    }

    public static void main(String[] args)
    {
        try
        {
            final long startTime = System.currentTimeMillis();

            Class.forName("org.h2.Driver");

            final long endTime = System.currentTimeMillis();

            System.out.println("Loaded Database Driver In: " + ((endTime - startTime)) + "ms");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        launch(args);
    }
}