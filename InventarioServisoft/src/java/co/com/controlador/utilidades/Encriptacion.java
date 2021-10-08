package co.com.controlador.utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 *
 * @author Cristian Yepes
 */
public class Encriptacion {

    private static Logger logger = Logger.getLogger(Encriptacion.class);

    private Encriptacion() {
    }

    private static String encrypt(String texto, String hashType) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashType);
            byte[] array = messageDigest.digest(texto.getBytes());
            StringBuilder stringB = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                stringB.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return stringB.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            logger.error("Error al encriptar el texto: " + texto);
        }
        return null;
    }

   
}
