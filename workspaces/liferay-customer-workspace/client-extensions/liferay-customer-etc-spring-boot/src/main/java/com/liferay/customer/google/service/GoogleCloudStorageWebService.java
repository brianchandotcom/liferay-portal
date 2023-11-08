/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.google.service;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Amos Fong
 */
@Component
public class GoogleCloudStorageWebService {

	public String getDownloadURL(String bucketName, String objectName)
		throws Exception {

		try (InputStream inputStream = new ByteArrayInputStream(
				_gcsServiceAccountKey.getBytes())) {

			ServiceAccountCredentials serviceAccountCredentials =
				ServiceAccountCredentials.fromStream(inputStream);

			StorageOptions storageOptions = StorageOptions.newBuilder(
			).setCredentials(
				serviceAccountCredentials
			).setProjectId(
				serviceAccountCredentials.getProjectId()
			).build();

			Storage storage = storageOptions.getService();

			URL url = storage.signUrl(
				BlobInfo.newBuilder(
					BlobId.of(bucketName, objectName)
				).build(),
				15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());

			return url.toString();
		}
	}

	public String getUploadSessionURL(String bucketName, String objectName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("https://storage.googleapis.com/upload/storage/v1/b/");
		sb.append(bucketName);
		sb.append("/o?uploadType=resumable&name=");
		sb.append(objectName);

		try (InputStream inputStream = new ByteArrayInputStream(
				_gcsServiceAccountKey.getBytes())) {

			ServiceAccountCredentials serviceAccountCredentials =
				ServiceAccountCredentials.fromStream(inputStream);

			Set<String> scopes = new HashSet<>();

			scopes.add("https://www.googleapis.com/auth/cloud-platform");

			GoogleCredentials googleCredentials =
				serviceAccountCredentials.createScoped(scopes);

			AccessToken accessToken = googleCredentials.refreshAccessToken();

			ResponseEntity<String> responseEntity = WebClient.create(
			).post(
			).uri(
				sb.toString()
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + accessToken.getTokenValue()
			).retrieve(
			).toEntity(
				String.class
			).block();

			HttpHeaders httpHeaders = responseEntity.getHeaders();

			URI uri = httpHeaders.getLocation();

			return uri.toString();
		}
	}

	@Value("${liferay.customer.gcs.service.account.key}")
	private String _gcsServiceAccountKey;

}