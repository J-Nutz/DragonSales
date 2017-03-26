package mutual.types;

/*
 * Created by Jonah on 2/10/2017.
 */

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;

public class ToggleButton2 extends ToggleButton
{
    private boolean toggleable = true;

    public ToggleButton2(String text)
    {
        super(text);
    }

    public void setToggleable(boolean toggleable)
    {
        this.toggleable = toggleable;
    }

    public boolean isToggleable()
    {
        return this.toggleable;
    }

    @Override
    public void fire()
    {
        if (!isDisabled())
        {
            if(isToggleable())
            {
                setSelected(!isSelected());
            }
            else
            {
                setSelected(isSelected());
            }

            fireEvent(new ActionEvent());
        }
    }
}