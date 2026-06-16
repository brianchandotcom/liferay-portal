/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ProfileContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public ProfileContactsCardTemplateDisplay() {
	}

	public ProfileContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		_layoutType = MapUtil.getInteger(settings, "layoutType");
	}

	@Override
	public List<String> getFieldMappingNames() {
		return _fieldMappingNames;
	}

	private static final int[] _SUPPORTED_SIZES = {1};

	private static final List<String> _fieldMappingNames = Arrays.asList(
		"country", "telephone", "email", "industry");

	private int _layoutType;

}