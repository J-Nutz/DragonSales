package worker;

/*
 * Created by Jonah on 2/8/2017.
 */

import javafx.scene.control.SpinnerValueFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalSpinnerValueFactory extends SpinnerValueFactory<BigDecimal>
{
    private  BigDecimal step;

    public BigDecimalSpinnerValueFactory(BigDecimal step)
    {
        this.step = step;
    }

    @Override
    public void decrement(int steps)
    {
        BigDecimal currentValue;

        if(this.getValue() != null)
        {
            currentValue = this.getValue();
        }
        else
        {
            currentValue = BigDecimal.ZERO;
        }

        while(steps > 0)
        {
            if(currentValue.compareTo(BigDecimal.ZERO) > 0)
            {
                currentValue = currentValue.subtract(step);
            }
            steps--;
        }

        this.setValue(currentValue.setScale(2, RoundingMode.CEILING));
    }

    @Override
    public void increment(int steps)
    {
        BigDecimal currentValue;

        if(this.getValue() != null)
        {
            currentValue = this.getValue();
        }
        else
        {
            currentValue = BigDecimal.ZERO;
        }

        while(steps > 0)
        {
            if(currentValue.compareTo(BigDecimal.valueOf(100)) == -1)
            {
                currentValue = currentValue.add(step);
            }
            steps--;
        }

        this.setValue(currentValue.setScale(2, RoundingMode.CEILING));
    }
}