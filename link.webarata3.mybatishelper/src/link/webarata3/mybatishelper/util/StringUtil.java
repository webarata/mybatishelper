/**
 * MyBatis Helper
 *
 * Copyright (c) 2016 Shinichi ARATA
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package link.webarata3.mybatishelper.util;

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
