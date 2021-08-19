package org.vaccom.vcmgt.security;

/**
 * @author trungnt
 *
 */
public interface TextDataEncryptor {
	public String encrypt(String key, String value);
}
