package org.vaccom.vcmgt.security.impl;

import org.jasypt.util.text.StrongTextEncryptor;
import org.vaccom.vcmgt.security.TextDataDecryptor;

public class StrongTextDataDecryptor implements TextDataDecryptor{

	@Override
	public String decrypt(String key, String value) {
		StrongTextEncryptor encryptor = new StrongTextEncryptor();
		encryptor.setPassword(key);
		return encryptor.decrypt(value);
	}

}
