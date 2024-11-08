/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.tools;

import com.liferay.ai.content.wizard.langchain4j.descriptions.AccountDescriptions;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class AccountTools extends BaseTools {

	public AccountTools(ToolsContext toolsContext) {
		super(toolsContext);

		_accountResource = AccountResource.builder(
		).endpoint(
			toolsContext.liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION, toolsContext.authorization
		).build();
	}

	@Tool(
		"Account Deletion,use this tool only if you know the account external reference code, if you are uncertain call the list of accounts to retrieve this information"
	)
	public void deleteAccountByExternalReferenceCode(
			@P(value = "The account external reference code") String
				externalReferenceCode)
		throws Exception {

		_accountResource.deleteAccountByExternalReferenceCode(
			externalReferenceCode);
	}

	@Tool("Returns a list of accounts")
	public Page<Account> getAccountsPage() throws Exception {
		return _accountResource.getAccountsPage(
			"", "", Pagination.of(1, 20), "");
	}

	@Tool("Create Liferay Account")
	public Account postAccount(AccountDescriptions accountDescriptions)
		throws Exception {

		return _accountResource.postAccount(
			Account.toDTO(
				new JSONObject(
				).put(
					"description", accountDescriptions.description
				).put(
					"name", accountDescriptions.name
				).put(
					"type", accountDescriptions.type
				).toString()));
	}

	private final AccountResource _accountResource;

}