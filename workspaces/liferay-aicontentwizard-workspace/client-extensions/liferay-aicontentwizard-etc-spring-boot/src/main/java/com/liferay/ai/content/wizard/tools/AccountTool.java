/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.tools;

import com.liferay.ai.content.wizard.models.AIContext;
import com.liferay.ai.content.wizard.schemas.Account;
import com.liferay.headless.admin.user.client.pagination.Page;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class AccountTool extends AITools {

	public AccountTool(AIContext aiContext) {
		super(aiContext);
	}

	@Tool("Create Liferay Account")
	private Account _createLiferayAccount(Account account) throws Exception {
		liferayService.createAccount(account.toAccountDTO());

		if (_log.isInfoEnabled()) {
			_log.info("Account created");
		}

		return account;
	}

	@Tool(
		"Account Deletion,use this tool only if you know the account external reference code, if you are uncertain call the list of accounts to retrieve this information"
	)
	private void _deleteAccount(
			@P(value = "The account external reference code") String
				externalReferenceCode)
		throws Exception {

		liferayService.deleteAccountByExternalReferenceCode(
			externalReferenceCode);
	}

	@Tool("Returns a list of accounts")
	private Page<com.liferay.headless.admin.user.client.dto.v1_0.Account>
			_getAccountsPage()
		throws Exception {

		return liferayService.getAccountsPage();
	}

	private static final Log _log = LogFactory.getLog(AccountTool.class);

}