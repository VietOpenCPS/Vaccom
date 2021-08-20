package org.vaccom.vcmgt.security.impl;

import org.jasypt.util.text.StrongTextEncryptor;
import org.vaccom.vcmgt.security.TextDataEncryptor;

public class StrongTextDataEncryptor implements TextDataEncryptor {

	@Override
	public String encrypt(String key, String value) {
		StrongTextEncryptor encryptor = new StrongTextEncryptor();
		encryptor.setPassword(key);
		return encryptor.encrypt(value);
	}

}
