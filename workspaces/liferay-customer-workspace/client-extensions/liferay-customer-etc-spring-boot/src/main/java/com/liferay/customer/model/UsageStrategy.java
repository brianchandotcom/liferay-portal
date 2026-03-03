/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.JSONObject;

/**
 * @author Felipe Veloso
 */
public interface UsageStrategy {

	public static final String UNIT_GIB = "GiB";

	public static final String UNIT_TIB = "TiB";

	public static JSONObject createUsageJSONObject(
		BigDecimal usedCount, long maxCount, String maxCountUnit) {

		String unit = UNIT_GIB;

		if (Validator.isNotNull(maxCountUnit)) {
			if (usedCount.compareTo(new BigDecimal("1024")) >= 0) {
				usedCount = usedCount.divide(new BigDecimal(1024));
				unit = UNIT_TIB;
			}
		}
		else {
			unit = maxCountUnit;
		}

		BigDecimal dividend = usedCount;

		if (!unit.equals(maxCountUnit)) {
			dividend = usedCount.divide(
				new BigDecimal("1024"), 10, RoundingMode.HALF_UP);
		}

		BigDecimal percentage = BigDecimal.ZERO;

		if (maxCount > 0) {
			percentage = dividend.multiply(
				new BigDecimal("100")
			).divide(
				new BigDecimal(maxCount), 2, RoundingMode.HALF_UP
			);
		}

		JSONObject jsonObject = new JSONObject();

		return jsonObject.put(
			"maxCount", maxCount
		).put(
			"maxCountUnits", maxCountUnit
		).put(
			"percentage",
			percentage.setScale(
				4, RoundingMode.DOWN
			).toPlainString()
		).put(
			"usedCount",
			usedCount.setScale(
				2, RoundingMode.DOWN
			).floatValue()
		).put(
			"usedCountUnits", unit
		);
	}

	public JSONObject toJSONObject();

}