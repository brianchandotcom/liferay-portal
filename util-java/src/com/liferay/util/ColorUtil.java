/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.awt.Color;

/**
 * @author Brian Wing Shun Chan
 * @author Ming-Gih Lam
 * @author David Truong
 */
public class ColorUtil {

	public static Color blend(Color color1, Color color2, double ratio) {
		return blend(
			new int[] {color1.getRed(), color1.getGreen(), color1.getBlue()},
			new int[] {color2.getRed(), color2.getGreen(), color2.getBlue()},
			ratio);
	}

	public static Color blend(int[] color1, int[] color2, double ratio) {
		return new Color(
			(int)(((color2[0] - color1[0]) * ratio) + color1[0]),
			(int)(((color2[1] - color1[1]) * ratio) + color1[1]),
			(int)(((color2[2] - color1[2]) * ratio) + color1[2]));
	}

	public static Color darker(int[] color, double ratio) {
		return new Color(
			(int)(color[0] - (color[0] * ratio)),
			(int)(color[1] - (color[1] * ratio)),
			(int)(color[2] - (color[2] * ratio)));
	}

	public static String getHex(int[] rgb) {
		return StringBundler.concat(
			"#",
			_KEY.substring(
				(int)Math.floor(rgb[0] / 16), (int)Math.floor(rgb[0] / 16) + 1),
			_KEY.substring(rgb[0] % 16, (rgb[0] % 16) + 1),
			_KEY.substring(
				(int)Math.floor(rgb[1] / 16), (int)Math.floor(rgb[1] / 16) + 1),
			_KEY.substring(rgb[1] % 16, (rgb[1] % 16) + 1),
			_KEY.substring(
				(int)Math.floor(rgb[2] / 16), (int)Math.floor(rgb[2] / 16) + 1),
			_KEY.substring(rgb[2] % 16, (rgb[2] % 16) + 1));
	}

	public static int[] getRGB(String hex) {
		if (hex.startsWith("#")) {
			hex = StringUtil.toUpperCase(hex.substring(1));
		}
		else {
			hex = StringUtil.toUpperCase(hex);
		}

		int[] hexArray = new int[6];

		if (hex.length() == 6) {
			char[] c = hex.toCharArray();

			for (int i = 0; i < hex.length(); i++) {
				if (c[i] == 'A') {
					hexArray[i] = 10;
				}
				else if (c[i] == 'B') {
					hexArray[i] = 11;
				}
				else if (c[i] == 'C') {
					hexArray[i] = 12;
				}
				else if (c[i] == 'D') {
					hexArray[i] = 13;
				}
				else if (c[i] == 'E') {
					hexArray[i] = 14;
				}
				else if (c[i] == 'F') {
					hexArray[i] = 15;
				}
				else {
					Character characterValue = Character.valueOf(c[i]);

					hexArray[i] = GetterUtil.getInteger(
						characterValue.toString());
				}
			}
		}

		int[] rgb = new int[3];

		rgb[0] = (hexArray[0] * 16) + hexArray[1];
		rgb[1] = (hexArray[2] * 16) + hexArray[3];
		rgb[2] = (hexArray[4] * 16) + hexArray[5];

		return rgb;
	}

	public static Color lighter(int[] color, double ratio) {
		return new Color(
			(int)(((0xFF - color[0]) * ratio) + color[0]),
			(int)(((0xFF - color[1]) * ratio) + color[1]),
			(int)(((0xFF - color[2]) * ratio) + color[2]));
	}

	private static final String _KEY = "0123456789ABCDEF";

}