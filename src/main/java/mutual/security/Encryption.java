package mutual.security;

/*
 * Created by Jonah on 12/2/2016.
 */

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Encryption
{
    public static byte[] encrypt(char[] password, byte[] salt)
    {
        try
        {
            // Remember To Reset Passwords If Iterations Or KeyLength Change
            // 10,000 Iterations Recommended
            // 512    KeyLength  "Forced" - (Sha512)

            PBEKeySpec keySpec = new PBEKeySpec(password, salt, 5000, 512);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

            byte[] encrypted = keyFactory.generateSecret(keySpec).getEncoded();

            Arrays.fill(password, '0'); // TODO: Necessary?

            return encrypted;
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new RuntimeException(e);
        }
    }
}