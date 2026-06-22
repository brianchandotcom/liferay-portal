/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsCardTemplateDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;

import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ContactsCardDisplay {

	public ContactsCardDisplay() {
	}

	public ContactsCardDisplay(
		FaroEntityDisplay faroEntityDisplay,
		ContactsCardTemplateDisplay contactsCardTemplateDisplay,
		Map<String, Object> contactsCardData) {

		_faroEntityDisplay = faroEntityDisplay;
		_contactsCardTemplateDisplay = contactsCardTemplateDisplay;
		_contactsCardData = contactsCardData;
	}

	private Map<String, Object> _contactsCardData;
	private ContactsCardTemplateDisplay _contactsCardTemplateDisplay;
	private FaroEntityDisplay _faroEntityDisplay;

}