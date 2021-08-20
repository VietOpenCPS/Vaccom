package org.vaccom.vcmgt.security.impl;

import org.jasypt.util.text.BasicTextEncryptor;
import org.vaccom.vcmgt.security.TextDataEncryptor;

public class BasicTextDataEncryptor implements TextDataEncryptor {

	@Override
	public String encrypt(String key, String value) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(key);
		return encryptor.encrypt(value);

	}

}
