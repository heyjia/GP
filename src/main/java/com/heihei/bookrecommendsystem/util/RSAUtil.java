package com.heihei.bookrecommendsystem.util;

import jdk.nashorn.internal.runtime.options.LoggingOption;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RSAUtil
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/24 9:31
 **/
public class RSAUtil {

    static Logger logger = LoggerFactory.getLogger(RSAUtil.class);
    public static final String ENCRYPT_TYPE = "RSA";
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi5CpeoSjOduO7BNSlCRErGJwNue2PcC7C/axn+WYufObkRCiyeW7SXFo56He2yLE0o+CLSSAuTwbam2yPx92p3Jt7pUzNvaGqBSTJSmzJzxtDsBvb9Z+Bw5t38M3DSD2FujThRVclU80OHQJvVaanr1e7ebl9lCdcvMpsFrFXLQIDAQAB";
    public static String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKLkKl6hKM5247sE1KUJESsYnA25" +
            "7Y9wLsL9rGf5Zi585uREKLJ5btJcWjnod7bIsTSj4ItJIC5PBtqbbI/H3ancm3ulTM29oaoFJMlK" +
            "bMnPG0OwG9v1n4HDm3fwzcNIPYW6NOFFVyVTzQ4dAm9VpqevV7t5uX2UJ1y8ymwWsVctAgMBAAEC" +
            "gYEAhI7zoAC8qZB88RQUTJ3dV9n2HQGCvukHVO2FYTPHoOtKJtcE24EQ26YGUvvIeHpII1N9hzwi" +
            "MS1koYA8T6HR03oDJWcP/ouHWB4ieAKihrICvbISQzEAjAGtx1hnXEGu1c22WXmXbKTO1Ig7I+BI" +
            "7G//wzYdIjCjoygz5o26cK0CQQDwbEVeoaWHfT6d+YuVQoMEgHUN5MMc52R86RLoCVMis4V/+HsO" +
            "WB+laZ9PFW9qwvN9Bi9TFnv6NyoEYm32owhrAkEArXHuMWhHt2So+H8hZsJFz0SsCm3KyYYH4to6" +
            "4SdzLhefmaoi/GNncnUvvwEKDtZ9oVPv1qqlfWXreqOf5wRkxwJAVyCFiyO9XvpuZMV4ZiDyZgE/" +
            "akeKxcC9U98LMPegHyU7rgKYZbxdy44ZY4l7FQ+NObOyFsRBLY4sNeaNvyGZ/QJBAKgHEZyny6Jt" +
            "UAy9DJzCZGLxwkGwL56fzBAHHrd1Qm5K0IFLg+1CV7tYr9K4rQfG35pk+JrcYspi7Ie48HYbmY0C" +
            "QQCjS3QFk5CXiAOo+xWFWtIpcGsiJ7183U/Wzjg9gyzJiszgundkQ2stO+s1nqereAsfVj4/W04I" +
            "jGOrziSyextc";

//    static {
//        //genKeyPair();
//    }
    //生成密钥对
    public static void genKeyPair(){
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化密钥生成器 96-1024
        keyPairGen.initialize(1024,new SecureRandom());
        KeyPair keyPair = keyPairGen.genKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        String pulicKeyString = byte2Base64(publicKey.getEncoded());
        String privateKeyStrig = byte2Base64(privateKey.getEncoded());
        PUBLIC_KEY = pulicKeyString;
        PRIVATE_KEY = privateKeyStrig;
    }

    // 字节数组转换成base64
    public static String byte2Base64(byte [] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    // base64解码成字节数组
    public static byte[] base642bytes(String base64Str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Str);
    }

    //公钥加密，返回密文，每次返回的结果不一样
    public static String encrypt (String str, String publicKey) {
        RSAPublicKey pubKey = null;
        String outStr = null;
        byte[] decoded = null;
        try {
            decoded = base642bytes(publicKey);
            pubKey = (RSAPublicKey) KeyFactory.getInstance(ENCRYPT_TYPE).generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE,pubKey);
            outStr = byte2Base64(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException | InvalidKeySpecException e) {
            logger.error("[encrypt]  exception : " + e);
        }
        return outStr;
    }

    /*
     * @Description
     * @Author CHENZEJIA
     * @Date 2019/12/24
     * @Param [str, privateKey] 加密字符串  私钥
     * @return java.lang.String 明文
     **/
    public static String decrypt(String str,String privateKey) {
        byte[] inputByte = null;
        //basa64编码的私钥
        byte[] decoded = null;
        RSAPrivateKey priKey = null;
        Cipher cipher = null;
        String outStr = null;
        try {
            inputByte = base642bytes(str);
            decoded = base642bytes(privateKey);
            priKey = (RSAPrivateKey)KeyFactory.getInstance(ENCRYPT_TYPE).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            cipher = Cipher.getInstance(ENCRYPT_TYPE);
            cipher.init(Cipher.DECRYPT_MODE,priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            logger.error("[encrypt]  decrypt : " + e);
        }
        return outStr;
    }
    public static void main(String [] args) {
        System.out.println("加密方式：" + ENCRYPT_TYPE);
        String message = "123";
        //genKeyPair();
        System.out.println("随机生成的公钥为:" + PUBLIC_KEY);
        System.out.println("随机生成的私钥为:" + PRIVATE_KEY);
        String messageEn = encrypt(message,RSAUtil.PUBLIC_KEY);
        messageEn = "mgz/chn3OS8lGlvz2Wr3QlHyNLJe0wNqeBmK+WWhxVtzH2OIIeDFH1L9BQqAq0E0BVifrR6OQuK7Tga3VCJX0ERH8L+4fLmEokep8o7p33z26aOsJIU1qszTt3ZAqR9EG9aV/yPwiZ43HOc+pAms3kFC+HZvIKSN5vg5ObdqDA4=";
        System.out.println("加密后的字符串为：" + messageEn);
        System.out.println("加密后的字符串长度为：" + messageEn.length());
        String mseeageDe = decrypt(messageEn,RSAUtil.PRIVATE_KEY);
        System.out.println("还原后的字符串为：" + mseeageDe);
    }
}
