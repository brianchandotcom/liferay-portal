/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsLayoutTemplateDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;

import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ContactsLayoutDisplay {

	public ContactsLayoutDisplay() {
	}

	public ContactsLayoutDisplay(
		FaroEntityDisplay faroEntityDisplay,
		ContactsLayoutTemplateDisplay contactsLayoutTemplateDisplay,
		Map<String, Map<String, Object>> contactsCardData) {

		_faroEntityDisplay = faroEntityDisplay;
		_contactsLayoutTemplateDisplay = contactsLayoutTemplateDisplay;
		_contactsCardData = contactsCardData;
	}

	private Map<String, Map<String, Object>> _contactsCardData;
	private ContactsLayoutTemplateDisplay _contactsLayoutTemplateDisplay;
	private FaroEntityDisplay _faroEntityDisplay;

}