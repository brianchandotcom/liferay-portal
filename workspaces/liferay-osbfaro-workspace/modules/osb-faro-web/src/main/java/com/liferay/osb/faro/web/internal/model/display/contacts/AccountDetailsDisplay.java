/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.AccountDetails;

import java.util.List;

/**
 * @author Caio Pinheiro
 */
public class AccountDetailsDisplay {

	public AccountDetailsDisplay() {
	}

	public AccountDetailsDisplay(AccountDetails accountDetails) {
		_fields = accountDetails.getFields();
	}

	public List<AccountDetails.Field> getFields() {
		return _fields;
	}

	private List<AccountDetails.Field> _fields;

}