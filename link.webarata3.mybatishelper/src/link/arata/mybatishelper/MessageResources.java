/**
 * MyBatis Helper
 *
 * Copyright (c) 2016 Shinichi ARATA
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package link.arata.mybatishelper;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageResources {
	private static final String RESOURCE_NAME = "messageResources";

	public static String getMessage(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_NAME);
		return bundle.getString(key);
	}

	public static String getMessage(String key, Object... args) {
		return MessageFormat.format(getMessage(key), args);
	}

}