/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Jenny Chen
 */
public class ProductGroupConstants {

	public static final String COMMERCE = "commerce";

	public static final String DXP = "dxp";

	public static final String PORTAL = "portal";

	public static String getProductGroup(String productName) {
		if (productName.contains("Commerce Subscription")) {
			return COMMERCE;
		}
		else if (productName.startsWith("DXP") ||
				 productName.startsWith("Liferay Self-Hosted")) {

			return DXP;
		}
		else if ((productName.contains("Portal") &&
				  !productName.contains("Early Access Program")) ||
				 productName.startsWith("TCAT Portal")) {

			return PORTAL;
		}

		return StringPool.BLANK;
	}

	public static String getProductGroupLabel(String productGroup) {
		if (productGroup.equals(COMMERCE)) {
			return "Commerce";
		}
		else if (productGroup.equals(DXP)) {
			return "DXP";
		}
		else if (productGroup.equals(PORTAL)) {
			return "Portal";
		}

		return StringPool.BLANK;
	}

}