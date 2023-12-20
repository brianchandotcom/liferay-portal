/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample.shipping.engine.util;

import com.liferay.sample.shipping.engine.dto.ShippingEngineOption;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author Luca Pellizzon
 */
public class ShippingEngineOptionsUtil {

	public static List<ShippingEngineOption> getShippingEngineOptionList() {
		return _shippingEngineOptions;
	}

	private static ShippingEngineOption _getRandomShippingEngineOption() {
		Random random = new Random();

		double randomDouble = random.nextDouble();
		int randomInt = random.nextInt();

		return new ShippingEngineOption(
			BigDecimal.valueOf(randomDouble),
			UUID.randomUUID(
			).toString(),
			UUID.randomUUID(
			).toString(),
			UUID.randomUUID(
			).toString(),
			randomInt);
	}

	private static final List<ShippingEngineOption> _shippingEngineOptions =
		new ArrayList<>(3);

	static {
		_shippingEngineOptions.add(_getRandomShippingEngineOption());
		_shippingEngineOptions.add(_getRandomShippingEngineOption());
		_shippingEngineOptions.add(_getRandomShippingEngineOption());
	}

}