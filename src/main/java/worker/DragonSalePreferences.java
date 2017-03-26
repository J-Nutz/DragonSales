package worker;

/*
 * Created by Jonah on 1/12/2017.
 */

import java.util.prefs.Preferences;

public class DragonSalePreferences
{
    private final String OPENED_KEY = "OPENED";

    public DragonSalePreferences() {}

    public void setOpened(boolean opened)
    {
        Preferences preferences = Preferences.userRoot().node(getClass().getName());
        preferences.putBoolean(OPENED_KEY, opened);
        System.out.println("Opened: " + opened);
    }

    public boolean getOpened()
    {
        Preferences preferences = Preferences.userRoot().node(getClass().getName());
        return preferences.getBoolean(OPENED_KEY, false);
    }
}