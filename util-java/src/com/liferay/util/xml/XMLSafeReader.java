/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

/**
 * @author Brian Wing Shun Chan
 */
public class XMLSafeReader extends UnsyncStringReader {

	public XMLSafeReader(String xml) {
		super(_fixProlog(xml));
	}

	private static String _fixProlog(String xml) {

		// LEP-1921

		if (xml != null) {
			int pos = xml.indexOf(CharPool.LESS_THAN);

			if (pos > 0) {
				xml = xml.substring(pos);
			}
		}

		return xml;
	}

}