package com.artcweb.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {
	public static void main(String[] args) {
		Object plaintext = System.currentTimeMillis();
		System.out.println(plaintext);
		String Key = "8NONwyJtHesysWpM";
		String EncryptMode = "ECB";
		String cipertext =  Encrypt(plaintext, Key, EncryptMode);

		System.out.println(cipertext);
		
		System.out.println(Decrypt(cipertext, Key, EncryptMode));
	}
	
    /**
     * AES加密
     *
     * @param plaintext 明文
     * @param Key 密钥
     * @param EncryptMode AES加密模式，CBC或ECB
     * @return 该字符串的AES密文值
     */
    public static String Encrypt(Object plaintext, String Key,String EncryptMode) {
        String PlainText=null;
        try {
            PlainText=plaintext.toString();
            if (Key == null) {
                return null;
            }
            Key = MD5Util.MD5Encode(Key);
            byte[] raw = Key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/"+EncryptMode+"/PKCS5Padding");
            if(EncryptMode=="ECB") {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            }else {
                IvParameterSpec iv = new IvParameterSpec(Key.getBytes("utf-8"));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            }
            byte[] encrypted = cipher.doFinal(PlainText.getBytes("utf-8"));
            String encryptedStr=new String(new BASE64Encoder().encode(encrypted));
            return encryptedStr;
            //return new String(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
 
    /**
     * AES解密
     *
     * @param cipertext 密文
     * @param Key 密钥
     * @param EncryptMode AES加密模式，CBC或ECB
     * @return 该密文的明文
     */
    public static String Decrypt(Object cipertext, String Key,String EncryptMode) {
        String CipherText=null;
        try {
            CipherText=cipertext.toString();
            // 判断Key是否正确
            if (Key == null) {
                //System.out.print("Key为空null");
                return null;
            }
            Key=MD5Util.MD5Encode(Key);
            byte[] raw = Key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES/"+EncryptMode+"/PKCS5Padding");
            if(EncryptMode=="ECB") {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            }
            else
            {
                IvParameterSpec iv = new IvParameterSpec(Key.getBytes("utf-8"));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            }
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(CipherText);//先用base64解密
            //byte[] encrypted1 = CipherText.getBytes();
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}