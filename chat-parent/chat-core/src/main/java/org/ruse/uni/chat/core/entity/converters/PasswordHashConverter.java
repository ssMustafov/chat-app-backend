package org.ruse.uni.chat.core.entity.converters;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.ruse.uni.chat.core.crypto.CryptographyService;

/**
 *
 * @author sinan
 */
@Converter
public class PasswordHashConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		// can't use cdi directly in attribute converter
		CryptographyService cryptographyService = CDI.current().select(CryptographyService.class).get();
		return cryptographyService.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		CryptographyService cryptographyService = CDI.current().select(CryptographyService.class).get();
		return cryptographyService.decrypt(dbData);
	}

}
