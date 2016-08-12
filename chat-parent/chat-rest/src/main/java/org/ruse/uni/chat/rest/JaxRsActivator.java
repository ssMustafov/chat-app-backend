package org.ruse.uni.chat.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author sinan
 */
@ApplicationPath(JaxRsActivator.REST_ROOT_PATH)
public class JaxRsActivator extends Application {

	public static final String REST_ROOT_PATH = "/api";

}
