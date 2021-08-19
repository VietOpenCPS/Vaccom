package org.vaccom.vcmgt.security.impl;

import org.jasypt.util.text.BasicTextEncryptor;
import org.vaccom.vcmgt.security.TextDataDecryptor;

public class BasicTextDataDecryptor implements TextDataDecryptor {

	@Override
	public String decrypt(String key, String value) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(key);
		return encryptor.decrypt(value);

	}

}
