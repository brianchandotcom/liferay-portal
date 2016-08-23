/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.awt.Color;

/**
 * @author Brian Wing Shun Chan
 * @author Ming-Gih Lam
 * @author David Truong
 */
public class ColorUtil {

	public static Color blend(Color color1, Color color2, double ratio) {
		int[] rgb1 = {color1.getRed(), color1.getGreen(), color1.getBlue()};
		int[] rgb2 = {color2.getRed(), color2.getGreen(), color2.getBlue()};

		return blend(rgb1, rgb2, ratio);
	}

	public static Color blend(int[] color1, int[] color2, double ratio) {
		Color blended = new Color(
			(int)(((color2[0] - color1[0]) * ratio) + color1[0]),
			(int)(((color2[1] - color1[1]) * ratio) + color1[1]),
			(int)(((color2[2] - color1[2]) * ratio) + color1[2]));

		return blended;
	}

	public static Color darker(int[] color, double ratio) {
		Color darkened = new Color(
			(int)(color[0] - (color[0] * ratio)),
			(int)(color[1] - (color[1] * ratio)),
			(int)(color[2] - (color[2] * ratio)));

		return darkened;
	}

	public static String getHex(int[] rgb) {
		StringBundler sb = new StringBundler(7);

		sb.append("#");

		sb.append(
			_KEY.substring(
				(int)Math.floor(rgb[0] / 16),
				(int)Math.floor(rgb[0] / 16) + 1));

		sb.append(_KEY.substring(rgb[0] % 16, (rgb[0] % 16) + 1));

		sb.append(
			_KEY.substring(
				(int)Math.floor(rgb[1] / 16),
				(int)Math.floor(rgb[1] / 16) + 1));

		sb.append(_KEY.substring(rgb[1] % 16, (rgb[1] % 16) + 1));

		sb.append(
			_KEY.substring(
				(int)Math.floor(rgb[2] / 16),
				(int)Math.floor(rgb[2] / 16) + 1));

		sb.append(_KEY.substring(rgb[2] % 16, (rgb[2] % 16) + 1));

		return sb.toString();
	}

	public static int[] getRGB(String hex) {
		if (hex.startsWith("#")) {
			hex = StringUtil.toUpperCase(hex.substring(1, hex.length()));
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
					hexArray[i] = GetterUtil.getInteger(
						Character.valueOf(c[i]).toString());
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
		Color lightened = new Color(
			(int)(((0xFF - color[0]) * ratio) + color[0]),
			(int)(((0xFF - color[1]) * ratio) + color[1]),
			(int)(((0xFF - color[2]) * ratio) + color[2]));

		return lightened;
	}

	private static final String _KEY = "0123456789ABCDEF";

}