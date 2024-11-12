/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.customer.model.TicketAttachment;
import com.liferay.petra.string.StringBundler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class TicketAttachmentService extends BaseRestController {

	public TicketAttachment addTicketAttachment(
			Jwt jwt, String accountKey, String externalReferenceCode,
			String fileName, String fileSize, String md5Checksum,
			int statusCode, String type, long zendeskTicketId)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"accountKey", accountKey
		).put(
			"externalReferenceCode", externalReferenceCode
		).put(
			"fileName", fileName
		).put(
			"fileSize", fileSize
		).put(
			"gcsBucketName", _gcsBucketName
		).put(
			"r_accountEntryToTicketAttachment_accountEntryERC", accountKey
		).put(
			"storageProvider", TicketAttachment.STORAGE_PROVIDER_GCS
		).put(
			"type", type
		).put(
			"zendeskTicketId", zendeskTicketId
		);

		if (!md5Checksum.equals("")) {
			requestJSONObject.put("md5Checksum", md5Checksum);
		}

		JSONObject statusJSONObject = new JSONObject();

		statusJSONObject.put("code", statusCode);

		requestJSONObject.put("status", statusJSONObject);

		JSONObject jsonObject = new JSONObject(
			post(
				"Bearer " + jwt.getTokenValue(), requestJSONObject.toString(),
				"/o/c/ticketattachments"));

		return new TicketAttachment(jsonObject);
	}

	public TicketAttachment approveTicketAttachment(
			Jwt jwt, long ticketAttachmentId)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		JSONObject statusJSONObject = new JSONObject();

		statusJSONObject.put("code", TicketAttachment.STATUS_APPROVED);

		requestJSONObject.put("status", statusJSONObject);

		JSONObject jsonObject = new JSONObject(
			patch(
				"Bearer " + jwt.getTokenValue(), requestJSONObject.toString(),
				"/o/c/ticketattachments/" + ticketAttachmentId));

		return new TicketAttachment(jsonObject);
	}

	public void deleteTicketAttachment(Jwt jwt, long ticketAttachmentId)
		throws Exception {

		delete(
			"Bearer " + jwt.getTokenValue(), null,
			"/o/c/ticketattachments/" + ticketAttachmentId);
	}

	public TicketAttachment fetchTicketAttachment(
		Jwt jwt, long ticketAttachmentId) {

		try {
			JSONObject jsonObject = new JSONObject(
				get(
					"Bearer " + jwt.getTokenValue(),
					"/o/c/ticketattachments/" + ticketAttachmentId));

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
			Jwt jwt, String fileName, String md5Checksum, long zendeskTicketId)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("/o/c/ticketattachments?filter=fileName eq '");
		sb.append(fileName);

		if (!md5Checksum.equals("")) {
			sb.append("' and md5Checksum eq '");
			sb.append(md5Checksum);
		}

		sb.append("' and zendeskTicketId eq ");
		sb.append(zendeskTicketId);

		JSONObject jsonObject = new JSONObject(
			get("Bearer " + jwt.getTokenValue(), sb.toString()));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		if (jsonArray.length() > 0) {
			return new TicketAttachment(jsonArray.getJSONObject(0));
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentService.class);

	@Value("${liferay.customer.gcs.bucket.name}")
	private String _gcsBucketName;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}