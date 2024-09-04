/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.dispatch.executor;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.testray.rest.manager.TestrayManager;

import java.io.Serializable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray-case-flaky-classifier",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-case-flaky-classifier"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayCaseFlakyClassifierDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(
				unicodeProperties.getProperty("testrayCaseFlakyThreshold")) ||
			Validator.isNull(
				unicodeProperties.getProperty("testrayCaseFlakyLimitDays"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.fetchUser(dispatchTrigger.getUserId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			_process(dispatchTrigger, unicodeProperties);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray-case-flaky-classifier";
	}

	private void _process(
			DispatchTrigger dispatchTrigger,
			UnicodeProperties unicodeProperties)
		throws Exception {

		List<Map<String, Serializable>> testrayCases =
			_objectEntryLocalService.getValuesList(
				0, dispatchTrigger.getCompanyId(), dispatchTrigger.getUserId(),
				_objectDefinitionLocalService.fetchObjectDefinition(
					dispatchTrigger.getCompanyId(), "C_Case"
				).getObjectDefinitionId(),
				null, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (_log.isInfoEnabled()) {
			_log.info("Checking " + testrayCases.size() + " tests");
		}

		int flaky = 0;

		for (Map<String, Serializable> testrayCase : testrayCases) {
			Map<String, Object> testrayCaseFlakyParameters =
				_testrayManager.fetchTestrayCaseFlakyParameters(
					dispatchTrigger.getCompanyId(),
					_currentDateTime.minusDays(
						GetterUtil.getLong(
							unicodeProperties.getProperty(
								"testrayCaseFlakyLimitDays"))),
					GetterUtil.getLong(testrayCase.get("c_caseId")));

			if (testrayCaseFlakyParameters == null) {
				continue;
			}

			double totalChanges = GetterUtil.getDouble(
				testrayCaseFlakyParameters.get("totalchanges"));
			double totalCases = GetterUtil.getDouble(
				testrayCaseFlakyParameters.get("totalcases"));

			if ((totalChanges / totalCases) > GetterUtil.getDouble(
					unicodeProperties.getProperty(
						"testrayCaseFlakyThreshold"))) {

				if (Objects.equals(
						String.valueOf(testrayCase.get("flaky")), "true")) {

					continue;
				}

				testrayCase.put("flaky", true);
			}
			else {
				if (Objects.equals(
						String.valueOf(testrayCase.get("flaky")), "false")) {

					continue;
				}

				testrayCase.put("flaky", false);
			}

			_objectEntryLocalService.updateObjectEntry(
				dispatchTrigger.getUserId(), (Long)testrayCase.get("c_caseId"),
				testrayCase, new ServiceContext());

			flaky++;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Updated " + flaky + " tests");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayCaseFlakyClassifierDispatchTaskExecutor.class);

	private final OffsetDateTime _currentDateTime = OffsetDateTime.now(
		ZoneOffset.UTC
	).truncatedTo(
		ChronoUnit.SECONDS
	);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private TestrayManager _testrayManager;

	@Reference
	private UserLocalService _userLocalService;

}