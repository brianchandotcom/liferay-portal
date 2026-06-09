/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.constants;

/**
 * @author Kyle Bischof
 */
public class CommerceCatalogConstants {

	public static final String DEFAULT_LANGUAGE_ID = "en_US";

	public static final String PRICE_LIST_ERC_PREFIX = "SALESFORCE_PRICE_LIST_";

	public static final String PRODUCT_TYPE_SIMPLE = "simple";

	public static final String SALESFORCE_CATALOG = "SALESFORCE_CATALOG";

	public static String priceListErc(String currencyIsoCode) {
		return PRICE_LIST_ERC_PREFIX + currencyIsoCode;
	}

}