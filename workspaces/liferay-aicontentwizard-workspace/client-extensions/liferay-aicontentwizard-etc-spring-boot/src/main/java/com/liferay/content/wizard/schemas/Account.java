/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.schemas;

import com.liferay.content.wizard.enums.AccountType;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class Account {

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public AccountType getType() {
		return type;
	}

	public com.liferay.headless.admin.user.client.dto.v1_0.Account
		toAccountDTO() {

		return com.liferay.headless.admin.user.client.dto.v1_0.Account.toDTO(
			new JSONObject(
			).put(
				"description", description
			).put(
				"name", name
			).put(
				"type", type
			).toString());
	}

	@Description("Account Description")
	public String description;

	@Description("Account Name")
	public String name;

	@Description(
		"Liferay provides three account types: Business, Person, and Supplier"
	)
	public AccountType type;

}