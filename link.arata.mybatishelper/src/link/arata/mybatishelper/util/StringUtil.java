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
package link.arata.mybatishelper.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class StringUtil {
	public static final String CRLF = "\r\f";
	public static final String LF = "\n";

	public static String normalizeNewLine(String target) {
		return normalizeNewLine(target, LF);
	}

	public static String normalizeNewLine(String target, String newLine) {
		BufferedReader reader = new BufferedReader(new StringReader(target));

		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(newLine);
			}
		} catch (IOException e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return sb.toString();
	}
}
