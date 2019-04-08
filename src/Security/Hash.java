package Security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Hash {

    /**
     * Creates a salted hash from the given string and salt
     *
     * @param plainText the plaintext to hash
     * @param salt      the salt to add to the hash
     * @return the salted hash
     */
    public static byte[] CreateHash(String plainText, String salt) {
        byte[] byteSalt = salt.getBytes(StandardCharsets.UTF_8);
        KeySpec spec = new PBEKeySpec(plainText.toCharArray(), byteSalt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The encryption algorithm specified doesn't exist.");
        } catch (InvalidKeySpecException e) {
            System.out.println("The keyspec is invalid.");
        }
        System.out.println("There was an error hashing the text. Emery did something wrong...");
        return null;
    }

}
