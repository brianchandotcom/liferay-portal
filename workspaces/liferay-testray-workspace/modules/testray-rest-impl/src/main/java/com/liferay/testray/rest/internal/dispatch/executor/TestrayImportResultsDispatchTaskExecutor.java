/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.dispatch.executor;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.exception.PortalException;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray-import-results",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-import-results"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayImportResultsDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("s3APIKey")) ||
			Validator.isNull(unicodeProperties.getProperty("s3BucketName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ErroredFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3InboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ProcessedFolderName"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			_uploadToTestray(
				dispatchTrigger.getCompanyId(), serviceContext,
				unicodeProperties, user.getUserId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray-import-results";
	}

	@Override
	public boolean isClusterModeSingle() {
		return true;
	}

	private void _uploadToTestray(
			long companyId, ServiceContext serviceContext,
			UnicodeProperties unicodeProperties, long userId)
		throws Exception {

		String s3APIKey = unicodeProperties.getProperty("s3APIKey");

		try (InputStream inputStream = new ByteArrayInputStream(
				s3APIKey.getBytes())) {

			long filesCountThreshold = GetterUtil.getLong(
				unicodeProperties.getProperty("filesCountThreshold"), -1);

			Storage storage = StorageOptions.newBuilder(
			).setCredentials(
				GoogleCredentials.fromStream(inputStream)
			).build(
			).getService();

			String s3InboxFolderName = unicodeProperties.getProperty(
				"s3InboxFolderName");

			Page<Blob> page = storage.list(
				unicodeProperties.getProperty("s3BucketName"),
				Storage.BlobListOption.prefix(s3InboxFolderName + "/"));

			for (Blob blob : page.iterateAll()) {
				if (filesCountThreshold == 0) {
					break;
				}

				String name = blob.getName();

				if (name.equals(s3InboxFolderName + "/")) {
					continue;
				}

				if (_log.isInfoEnabled()) {
					_log.info("Processing " + name);
				}

				try {
					_testrayManager.processArchive(
						companyId, blob.getContent(), name, serviceContext,
						userId);

					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3ProcessedFolderName")));
				}
				catch (Exception exception) {
					_log.error(exception);
					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3ErroredFolderName")));
				}

				blob.delete();

				filesCountThreshold--;
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with GCP");

			throw new PortalException(
				"Unable to authenticate with GCP", ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayImportResultsDispatchTaskExecutor.class);

	@Reference
	private TestrayManager _testrayManager;

	@Reference
	private UserLocalService _userLocalService;

}