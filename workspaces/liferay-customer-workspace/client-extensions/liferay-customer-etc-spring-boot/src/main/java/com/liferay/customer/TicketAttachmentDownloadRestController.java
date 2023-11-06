/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Amos Fong
 */
@RequestMapping("/ticket-attachment/{ticketAttachmentId}/download")
@RestController
public class TicketAttachmentDownloadRestController extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("ticketAttachmentId") String ticketAttachmentId)
		throws Exception {

		log(jwt, _log);

		JSONObject jsonObject = _fetchTicketAttachment(jwt, ticketAttachmentId);

		if (jsonObject == null) {
			return new ResponseEntity<>(
				"Ticket attachment does not exist", HttpStatus.NOT_FOUND);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("tickets/");
		sb.append(jsonObject.optString("zendeskTicketId"));
		sb.append("/");
		sb.append(jsonObject.optString("fileName"));

		return new ResponseEntity<>(
			_generateV4GetObjectSignedUrl(
				jsonObject.optString("storageBucket"), sb.toString()),
			HttpStatus.OK);
	}

	private JSONObject _fetchTicketAttachment(
		Jwt jwt, String ticketAttachmentId) {

		try {
			String response = WebClient.create(
				lxcDXPServerProtocol + "://" + lxcDXPMainDomain
			).get(
			).uri(
				"/o/c/ticketattachments/" + Long.valueOf(ticketAttachmentId)
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block();

			return new JSONObject(response);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find ticket attachment with ID " +
						ticketAttachmentId,
					exception);
			}
		}

		return null;
	}

	private String _generateV4GetObjectSignedUrl(
			String bucketName, String objectName)
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

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentDownloadRestController.class);

	@Value("${liferay.customer.gcs.service.account.key}")
	private String _gcsServiceAccountKey;

}