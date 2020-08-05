package com.web.curation.api;

import javax.crypto.*;

import javax.crypto.spec.*;

import java.io.*;

import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

public class EncryptoApi
{
//	public static void main(String[] args)
//	{
//		try
//		{
//			String originalText = "plain text";
//			String key = "key";
//			String en = Encrypt(originalText, key);
//			String de = Decrypt(en, key);
//			System.out.println("Original Text is " + originalText);
//			System.out.println("Encrypted Text is " + en);
//			System.out.println("Decrypted Text is " + de);
//		}
//
//		catch (Exception ex)
//		{
//			System.out.println("오류");
//		}
//	}

	public static String Decrypt(String text, String key) throws Exception

	{

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] results = cipher.doFinal(decoder.decodeBuffer(text));

		return new String(results, "UTF-8");
	}

	public static String Encrypt(String text, String key) throws Exception

	{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;

		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(results);

	}

}