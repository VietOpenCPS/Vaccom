package org.vaccom.vcmgt.security.impl;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.vaccom.vcmgt.security.TextDataDecryptor;

public class StandardTextDataDecryptor implements TextDataDecryptor {

	@Override
	public String decrypt(String key, String value) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);
		return encryptor.decrypt(value);
	}
}
