package org.vaccom.vcmgt.security.impl;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.vaccom.vcmgt.security.TextDataEncryptor;


public class StandardTextDataEncryptor implements TextDataEncryptor {

	@Override
	public String encrypt(String key, String value) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);
		return encryptor.encrypt(value);
	}
}
