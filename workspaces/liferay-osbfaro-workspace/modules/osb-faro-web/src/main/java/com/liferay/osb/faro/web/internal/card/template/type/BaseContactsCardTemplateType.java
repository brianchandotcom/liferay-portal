/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.card.template.type;

import java.util.Collections;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public abstract class BaseContactsCardTemplateType
	implements ContactsCardTemplateType {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return Collections.emptyMap();
	}

}