package org.ruse.uni.chat.core.crypto;

/**
 *
 * @author sinan
 */
public interface CryptographyService {

	String encrypt(String text);

	String decrypt(String encrypted);

}
