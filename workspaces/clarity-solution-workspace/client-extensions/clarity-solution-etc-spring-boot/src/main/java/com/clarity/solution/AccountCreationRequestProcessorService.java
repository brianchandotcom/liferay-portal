/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.clarity.solution;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Processes account creation requests.
 *
 * @author dnebing
 */
@Service
public class AccountCreationRequestProcessorService extends BaseService {

	/**
	 * Constructs a new AccountCreationRequestProcessorService.
	 *
	 * @param queueManager the queue manager
	 * @param taskExecutor the task executor
	 */
	@Autowired
	public AccountCreationRequestProcessorService(
		AccountCreationRequestQueueManager queueManager,
		@Qualifier("CreateAccountTaskExecutor") TaskExecutor taskExecutor) {

		_queueManager = queueManager;
		_taskExecutor = taskExecutor;

		_startProcessing();
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

	/**
	 * Processes an account creation request.
	 *
	 * @param accountCreationRequest the account creation request in JSON format
	 */
	private void _processRequest(
		AccountCreationRequest accountCreationRequest) {

		try {
			String token = accountCreationRequest.getJwt(
			).getTokenValue();

			String authorizationHeader = "Bearer " + token;
			String baseUrl = lxcDXPServerProtocol + "://" + lxcDXPMainDomain;

			JSONObject jsonObject = new JSONObject(
				accountCreationRequest.getAccountJSON());

			JSONObject propertiesJSONObject = jsonObject.getJSONObject(
				"objectEntryDTOU3A2DistributorApplication"
			).getJSONObject(
				"properties"
			);

			String accountName = propertiesJSONObject.getString("businessName");
			String accountEmailAddress = propertiesJSONObject.getString(
				"applicantEmailAddress");

			String accountExternalReferenceCode =
				"ACCOUNT_" +
					StringUtils.replace(
						StringUtil.toUpperCase(accountName), " ", "_");

			post(
				authorizationHeader,
				new JSONObject(
				).put(
					"externalReferenceCode", accountExternalReferenceCode
				).put(
					"name", accountName
				).put(
					"type", "business"
				).toString(),
				UriComponentsBuilder.fromUriString(
					baseUrl + "/o/headless-admin-user/v1.0/accounts"
				).build(
				).toUri());

			post(
				authorizationHeader, "",
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						baseUrl,
						"/o/headless-admin-user/v1.0/",
						"accounts/by-external-reference-code/",
						accountExternalReferenceCode,
						"/user-accounts/by-email-address/", accountEmailAddress)
				).build(
				).toUri());

			long adminAccountRoleId = new JSONObject(
				get(
					authorizationHeader,
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							baseUrl,
							"/o/headless-admin-user/v1.0",
							"/accounts/by-external-reference-code/",
							accountExternalReferenceCode, "/account-roles",
							"?filter=name eq 'Account Administrator'")
					).build(
					).toUri())
			).getJSONArray(
				"items"
			).getJSONObject(
				0
			).getLong(
				"id"
			);

			post(
				authorizationHeader, "",
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						baseUrl,
						"/o/headless-admin-user/v1.0",
						"/accounts/by-external-reference-code/",
						accountExternalReferenceCode, "/account-roles/",
						adminAccountRoleId, "/user-accounts/by-email-address/",
						accountEmailAddress)
				).build(
				).toUri());
		}
		catch (Exception exception) {
			_log.error(
				"Failed to process account: {}",
				accountCreationRequest.getAccountJSON(), exception);
		}
	}

	private void _startProcessing() {
		_taskExecutor.execute(
			() -> {
				while (true) {
					try {
						_queueManager.awaitWork();

						while (!_queueManager.isEmpty()) {
							AccountCreationRequest request =
								_queueManager.dequeue();

							_processRequest(request);
						}
					}
					catch (InterruptedException interruptedException) {
						Thread.currentThread(
						).interrupt();

						_log.error(
							"Queue processing interrupted",
							interruptedException);
					}
					catch (Exception exception) {
						_log.error("Error processing queue entry", exception);
					}
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountCreationRequestProcessorService.class);

	private final AccountCreationRequestQueueManager _queueManager;
	private final TaskExecutor _taskExecutor;

}