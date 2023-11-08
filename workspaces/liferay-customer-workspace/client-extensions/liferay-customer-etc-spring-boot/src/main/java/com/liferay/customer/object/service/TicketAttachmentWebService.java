/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.object.service;

import com.liferay.customer.object.model.TicketAttachment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Amos Fong
 */
@Component
public class TicketAttachmentWebService {

	public TicketAttachment addTicketAttachment(
			Jwt jwt, String accountKey, long zendeskTicketId, String fileName,
			String fileSize, String md5Checksum, String type, int statusCode)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"accountKey", accountKey
		).put(
			"fileName", fileName
		).put(
			"fileSize", fileSize
		).put(
			"gcsBucketName", _gcsBucketName
		).put(
			"md5Checksum", md5Checksum
		).put(
			"r_accountEntryToTicketAttachment_accountEntryERC", accountKey
		).put(
			"storageProvider", TicketAttachment.STORAGE_PROVIDER_GCS
		).put(
			"type", type
		).put(
			"zendeskTicketId", zendeskTicketId
		);

		JSONObject statusJSONObject = new JSONObject();

		statusJSONObject.put("code", statusCode);

		requestJSONObject.put("status", statusJSONObject);

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).post(
			).uri(
				"/o/c/ticketattachments"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).body(
				BodyInserters.fromValue(requestJSONObject.toString())
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return new TicketAttachment(jsonObject);
	}

	public TicketAttachment fetchTicketAttachment(
		Jwt jwt, long ticketAttachmentId) {

		try {
			JSONObject jsonObject = new JSONObject(
				WebClient.create(
					_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
				).get(
				).uri(
					"/o/c/ticketattachments/" + ticketAttachmentId
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
				).retrieve(
				).bodyToMono(
					String.class
				).block());

			return new TicketAttachment(jsonObject);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch ticket attachment with ID " +
						ticketAttachmentId,
					exception);
			}
		}

		return null;
	}

	public TicketAttachment fetchTicketAttachment(
			Jwt jwt, long zendeskTicketId, String fileName, String md5Checksum)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("/o/c/ticketattachments?filter=fileName eq '");
		sb.append(fileName);
		sb.append("' and md5Checksum eq '");
		sb.append(md5Checksum);
		sb.append("' and zendeskTicketId eq ");
		sb.append(zendeskTicketId);

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).get(
			).uri(
				sb.toString()
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		if (jsonArray.length() > 0) {
			return new TicketAttachment(jsonArray.getJSONObject(0));
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentWebService.class);

	@Value("${liferay.customer.gcs.bucket.name}")
	private String _gcsBucketName;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}