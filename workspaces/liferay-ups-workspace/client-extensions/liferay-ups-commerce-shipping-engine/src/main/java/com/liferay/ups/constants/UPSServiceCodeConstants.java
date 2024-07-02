/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ups.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class UPSServiceCodeConstants {

	public static final String CODE_01 = "01";

	public static final String CODE_02 = "02";

	public static final String CODE_03 = "03";

	public static final String CODE_07 = "07";

	public static final String CODE_08 = "08";

	public static final String CODE_11 = "11";

	public static final String CODE_12 = "12";

	public static final String CODE_13 = "13";

	public static final String CODE_14 = "14";

	public static final String CODE_17 = "17";

	public static final String CODE_54 = "54";

	public static final String CODE_59 = "59";

	public static final String CODE_65 = "65";

	public static final String CODE_70 = "70";

	public static final String CODE_71 = "71";

	public static final String CODE_72 = "72";

	public static final String CODE_74 = "74";

	public static final String CODE_82 = "82";

	public static final String CODE_83 = "83";

	public static final String CODE_85 = "85";

	public static final String CODE_86 = "86";

	public static final String CODE_96 = "96";

	public static String getCodeName(String code) {
		if (StringUtil.equals(code, CODE_01)) {
			return "UPS Express";
		}
		else if (StringUtil.equals(code, CODE_02)) {
			return "UPS Expedited";
		}
		else if (StringUtil.equals(code, CODE_03)) {
			return "UPS Ground";
		}
		else if (StringUtil.equals(code, CODE_07)) {
			return "UPS Worldwide Express";
		}
		else if (StringUtil.equals(code, CODE_08)) {
			return "UPS Worldwide Expedited";
		}
		else if (StringUtil.equals(code, CODE_11)) {
			return "UPS Standard";
		}
		else if (StringUtil.equals(code, CODE_12)) {
			return "UPS 3 Day Select";
		}
		else if (StringUtil.equals(code, CODE_13)) {
			return "UPS Next Day Air Saver";
		}
		else if (StringUtil.equals(code, CODE_14)) {
			return "UPS Next Day Air Early";
		}
		else if (StringUtil.equals(code, CODE_17)) {
			return "UPS Worldwide Economy DDU";
		}
		else if (StringUtil.equals(code, CODE_54)) {
			return "UPS Worldwide Express Plus";
		}
		else if (StringUtil.equals(code, CODE_59)) {
			return "UPS 2nd Day Air A.M.";
		}
		else if (StringUtil.equals(code, CODE_65)) {
			return "UPS Worldwide Saver";
		}
		else if (StringUtil.equals(code, CODE_70)) {
			return "UPS Access Point Economy";
		}
		else if (StringUtil.equals(code, CODE_71)) {
			return "UPS Worldwide Express Freight Midday";
		}
		else if (StringUtil.equals(code, CODE_72)) {
			return "UPS Worldwide Economy DDP";
		}
		else if (StringUtil.equals(code, CODE_74)) {
			return "UPS Express 12:00";
		}
		else if (StringUtil.equals(code, CODE_82)) {
			return "UPS Today Standard";
		}
		else if (StringUtil.equals(code, CODE_83)) {
			return "UPS Today Dedicated Courrier";
		}
		else if (StringUtil.equals(code, CODE_85)) {
			return "UPS Today Express";
		}
		else if (StringUtil.equals(code, CODE_86)) {
			return "UPS Today Express Saver";
		}
		else if (StringUtil.equals(code, CODE_96)) {
			return "UPS Worldwide Express Freight";
		}

		return StringPool.BLANK;
	}

}