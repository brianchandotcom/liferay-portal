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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray-file-manager",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-file-manager"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayFileManagerDispatchTaskExecutor
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
				unicodeProperties.getProperty("s3InboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3LargeFilesInboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3MediumFileSizeLimit")) ||
			Validator.isNull(
				unicodeProperties.getProperty(
					"s3MediumFilesInboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3SmallFileSizeLimit")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3SmallFilesInboxFolderName"))) {

			_log.error("The required properties are not set");

			return;
		}

		String s3APIKey = unicodeProperties.getProperty("s3APIKey");

		try (InputStream inputStream = new ByteArrayInputStream(
				s3APIKey.getBytes())) {

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
				String name = blob.getName();

				if (name.equals(s3InboxFolderName + "/")) {
					continue;
				}

				if (blob.getSize() <= GetterUtil.getLong(
						unicodeProperties.getProperty(
							"s3SmallFileSizeLimit"))) {

					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3SmallFilesInboxFolderName")));
					blob.delete();

					continue;
				}

				if (blob.getSize() <= GetterUtil.getLong(
						unicodeProperties.getProperty(
							"s3MediumFileSizeLimit"))) {

					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3MediumFilesInboxFolderName")));
					blob.delete();

					continue;
				}

				blob.copyTo(
					unicodeProperties.getProperty("s3BucketName"),
					name.replaceFirst(
						s3InboxFolderName,
						unicodeProperties.getProperty(
							"s3LargeFilesInboxFolderName")));
				blob.delete();
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with GCP");

			throw new PortalException(
				"Unable to authenticate with GCP", ioException);
		}
	}

	@Override
	public String getName() {
		return "testray-file-manager";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayFileManagerDispatchTaskExecutor.class);

}