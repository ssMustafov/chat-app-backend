package org.ruse.uni.chat.core.entity.converters;

import javax.inject.Inject;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.ruse.uni.chat.core.crypto.CryptographyService;

/**
 *
 * @author sinan
 */
@Converter
public class PasswordHashConverter implements AttributeConverter<String, String> {

	@Inject
	private CryptographyService cryptographyService;

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return cryptographyService.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		// do not decrypt
		return dbData;
	}

}
