/*
 * Copyright 2015 Shinichi ARATA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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