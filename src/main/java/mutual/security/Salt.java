package mutual.security;

/*
 * Created by Jonah on 12/2/2016.
 */

import java.security.SecureRandom;

public class Salt
{
    public byte[] addSalt(int chars) // TODO: Move This To Some Other Class?
    {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[chars];
        random.nextBytes(bytes);

        return bytes;
    }
}