package rz.com.catolico.utils;


import org.apache.axis.encoding.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by rafas on 01/06/2017.
 */

public class Encrypts {

    final static public String CHAVE = "E!09#x*%&aTe$lks";
    private static final String ALGORITMO = "AES";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static String encodeBase64URLSafeString(byte[] binaryData) {
    	
        return Base64.encode(binaryData);

    }

    public static String criptografar(String mensagem, String chave) throws Exception {

        final Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, chave);

        final byte[] criptografado = cipher.doFinal(mensagem.getBytes());

        return encodeBase64URLSafeString(criptografado).trim();

        //return StringUtils.trim(Base64.encodeBase64String(criptografado));
    }

//    public static String descriptografar(String mensagem, String chave) throws Exception {
//
//        final Cipher cipher = getCipher(Cipher.DECRYPT_MODE, chave);
//        
//        final byte[] descriptografado = cipher.doFinal(Base64.decodeBase64(mensagem))
//        return new String(descriptografado, "UTF-8");
//    }

    private static Cipher getCipher(final int encryptMode, final String chave) throws Exception {

        final Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(encryptMode, buildKey(chave));

        return cipher;
    }

    private static Key buildKey(String chave) throws Exception {

        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        final byte[] key = Arrays.copyOf(messageDigest.digest(chave.getBytes("UTF-8")), 16);

        return new SecretKeySpec(key, ALGORITMO);
    }
}
