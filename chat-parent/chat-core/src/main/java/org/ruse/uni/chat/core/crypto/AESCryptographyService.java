package org.ruse.uni.chat.core.crypto;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.logging.Logger;
import org.ruse.uni.chat.core.configuration.ConfigurationProperty;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;

/**
 *
 * @author sinan
 */
@Singleton
public class AESCryptographyService implements CryptographyService {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	@ConfigurationProperty(name = "security.secret.key")
	private String secretKeyPropertyValue;

	private SecretKeySpec secretKey;

	@PostConstruct
	public void init() {
		setKey(secretKeyPropertyValue);
		LOGGER.info("Secret key loaded successfully");
	}

	private void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Wrong algorithm for crypting", e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public String encrypt(String strToEncrypt) {
		String local = strToEncrypt.trim();
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			return Base64.getEncoder().encodeToString(cipher.doFinal(local.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			LOGGER.errorf("Can't encrypt: {}", e.getMessage());
			throw new ChatRuntimeException("Unable to encrypt", e);
		}
	}

	@Override
	public String decrypt(String strToDecrypt) {
		String local = strToDecrypt.trim();
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(local)));
		} catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException
				| NoSuchPaddingException e) {
			LOGGER.errorf("Can't decrypt: {}", e.getMessage());
			throw new ChatRuntimeException("Unable to decrypt", e);
		}
	}

}