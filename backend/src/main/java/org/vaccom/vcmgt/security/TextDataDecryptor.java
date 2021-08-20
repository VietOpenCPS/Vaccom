package org.vaccom.vcmgt.security;

/**
 * @author trungnt
 *
 */
public interface TextDataDecryptor {
	public String decrypt(String key, String value);
}
