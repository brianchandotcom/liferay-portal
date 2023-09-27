/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

/**
 * @author Brian Wing Shun Chan
 */
public class Distance {

	public static double calculate(
		double lat1, double lon1, double lat2, double lon2) {

		// Convert from radians to degrees

		lat1 = (Math.PI * lat1) / 180;
		lon1 = (Math.PI * lon1) / 180;
		lat2 = (Math.PI * lat2) / 180;
		lon2 = (Math.PI * lon2) / 180;

		double acos = Math.acos(
			(Math.sin(lat1) * Math.sin(lat2)) +
				(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2)));

		return 3963.4 * acos;
	}

	public static double kmToMiles(double km) {
		return km * 0.621;
	}

	public static double milesToKm(double miles) {
		return miles / 0.621;
	}

}