package com.regur.jpaencrypt.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class CryptoUtil {

	/**
	 * MD5 로 해시 한다.
	 *
	 * @param msg
	 * @return
	 */
	public static String md5(String msg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(msg.getBytes());
		return CryptoUtil.byteToHexString(md.digest());
	}

	/**
	 * SHA-256으로 해시한다.
	 *
	 * @param msg
	 * @return
	 */
	public static String sha256(String msg)  throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());
		return CryptoUtil.byteToHexString(md.digest());
	}

	/**
	 * 바이트 배열을 HEX 문자열로 변환한다.
	 * @param data
	 * @return
	 */
	public static String byteToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte b : data) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * AES 256 으로 암호화 한다.
	 * @param msg
	 * @return
	 */
	public static String encryptAES256(String msg){
		byte[] buffer = null;
		try{
			SecureRandom random = new SecureRandom();
			byte bytes[] = new byte[20];
			random.nextBytes(bytes);
			byte[] saltBytes = bytes;

			// Password-Based Key Derivation function 2
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			// 70000번 해시하여 256 bit 길이의 키를 만든다.
			PBEKeySpec spec = new PBEKeySpec(GlobalValue.SECRET_KEY.toCharArray(), saltBytes, 70000, 256);

			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

			// 알고리즘/모드/패딩
			// CBC : Cipher Block Chaining Mode
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			AlgorithmParameters params = cipher.getParameters();
			// Initial Vector(1단계 암호화 블록용)
			byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

			byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));

			buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
			System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
			System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
			System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
		} catch (Exception e){
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(buffer);
	}

	/**
	 * AES 256 으로 복호화 한다.
	 * @param msg
	 * @return
	 */
	public static String decryptAES256(String msg){
		byte[] decryptedTextBytes = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

			byte[] saltBytes = new byte[20];
			buffer.get(saltBytes, 0, saltBytes.length);
			byte[] ivBytes = new byte[cipher.getBlockSize()];
			buffer.get(ivBytes, 0, ivBytes.length);
			byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
			buffer.get(encryoptedTextBytes);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			PBEKeySpec spec = new PBEKeySpec(GlobalValue.SECRET_KEY.toCharArray(), saltBytes, 70000, 256);

			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

			decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
		} catch (Exception e){
			e.printStackTrace();
		}

		return new String(decryptedTextBytes);
	}
}
