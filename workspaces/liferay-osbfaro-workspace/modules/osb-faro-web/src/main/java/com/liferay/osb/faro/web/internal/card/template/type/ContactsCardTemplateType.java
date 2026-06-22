/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.card.template.type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsCardTemplateDisplay;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public interface ContactsCardTemplateType {

	public String getDefaultName();

	public Map<String, Object> getDefaultSettings();

	@JsonIgnore
	public Class<? extends ContactsCardTemplateDisplay> getDisplayClass();

	public int getType();

}