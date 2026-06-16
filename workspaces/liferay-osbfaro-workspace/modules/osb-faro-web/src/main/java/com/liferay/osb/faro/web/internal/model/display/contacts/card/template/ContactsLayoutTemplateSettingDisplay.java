/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

/**
 * @author Matthew Kong
 */
public class ContactsLayoutTemplateSettingDisplay {

	public ContactsLayoutTemplateSettingDisplay() {
	}

	public ContactsLayoutTemplateSettingDisplay(
		long contactsCardTemplateId, int size) {

		_contactsCardTemplateId = contactsCardTemplateId;
		_size = size;
	}

	public long getContactsCardTemplateId() {
		return _contactsCardTemplateId;
	}

	public int getSize() {
		return _size;
	}

	private long _contactsCardTemplateId;
	private int _size;

}