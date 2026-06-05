/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.Organization;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class OrganizationDisplay {

	public OrganizationDisplay() {
	}

	public OrganizationDisplay(Organization organization) {
		_id = organization.getId();
		_name = organization.getName();
		_organizationPK = organization.getOrganizationPK();
		_parentName = organization.getParentName();
		_type = organization.getType();
	}

	private String _id;
	private String _name;
	private long _organizationPK;
	private String _parentName;
	private String _type;

}