package org.ruse.uni.chat.core.configuration;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;

/**
 *
 * @author sinan
 */
public class ConfigurationPropertyProducer {

	@Inject
	private PropertyFileResolver fileResolver;

	@Produces
	@ConfigurationProperty(name = "")
	public String getPropertyAsString(InjectionPoint injectionPoint) {
		String propertyName = injectionPoint.getAnnotated().getAnnotation(ConfigurationProperty.class).name();
		String value = fileResolver.getProperty(propertyName);

		if (value == null || propertyName.trim().length() == 0) {
			throw new ChatRuntimeException("No configuration property found with name " + value);
		}
		return value;
	}

	@Produces
	@ConfigurationProperty(name = "")
	public Integer getPropertyAsInteger(InjectionPoint injectionPoint) {
		String value = getPropertyAsString(injectionPoint);
		return value == null ? null : Integer.valueOf(value);
	}

}
