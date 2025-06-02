/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.customer.model.TicketAttachment;
import com.liferay.petra.string.StringBundler;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class TicketAttachmentService extends BaseService {

	public TicketAttachment addTicketAttachment(
			String authorization, String accountKey,
			String externalReferenceCode, String fileName, String fileSize,
			String md5Checksum, int statusCode, String type,
			long zendeskTicketId)
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
				authorization, requestJSONObject.toString(),
				URI.create("/o/c/ticketattachments")));

		return new TicketAttachment(jsonObject);
	}

	public TicketAttachment approveTicketAttachment(
			String authorization, long ticketAttachmentId)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		JSONObject statusJSONObject = new JSONObject();

		statusJSONObject.put("code", TicketAttachment.STATUS_APPROVED);

		requestJSONObject.put("status", statusJSONObject);

		JSONObject jsonObject = new JSONObject(
			patch(
				authorization, requestJSONObject.toString(),
				URI.create("/o/c/ticketattachments/" + ticketAttachmentId)));

		return new TicketAttachment(jsonObject);
	}

	public void deleteTicketAttachment(
			String authorization, long ticketAttachmentId)
		throws Exception {

		delete(
			authorization, "",
			URI.create("/o/c/ticketattachments/" + ticketAttachmentId));
	}

	public TicketAttachment fetchTicketAttachment(
		String authorization, long ticketAttachmentId) {

		try {
			JSONObject jsonObject = new JSONObject(
				get(
					authorization,
					URI.create(
						"/o/c/ticketattachments/" + ticketAttachmentId)));

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
			String authorization, String fileName, String md5Checksum,
			long zendeskTicketId)
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
			get(authorization, URI.create(sb.toString())));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		if (jsonArray.length() > 0) {
			return new TicketAttachment(jsonArray.getJSONObject(0));
		}

		return null;
	}

	public List<TicketAttachment> searchTicketAttachments(
			String authorization, String filter)
		throws Exception {

		List<TicketAttachment> ticketAttachments = new ArrayList<>();

		JSONObject jsonObject = new JSONObject(
			get(
				authorization,
				URI.create("/o/c/ticketattachments?filter=" + filter)));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		for (int i = 0; i < jsonArray.length(); i++) {
			ticketAttachments.add(
				new TicketAttachment(jsonArray.getJSONObject(i)));
		}

		return ticketAttachments;
	}

	public TicketAttachment updateTicketAttachmentDraftCommentBody(
			String authorization, long ticketAttachmentId,
			String draftCommentBody)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("draftCommentBody", draftCommentBody);

		JSONObject jsonObject = new JSONObject(
			patch(
				authorization, requestJSONObject.toString(),
				URI.create("/o/c/ticketattachments/" + ticketAttachmentId)));

		return new TicketAttachment(jsonObject);
	}

	public TicketAttachment updateTicketAttachmentState(
			String authorization, long ticketAttachmentId, long state)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("state", state);

		JSONObject jsonObject = new JSONObject(
			patch(
				authorization, requestJSONObject.toString(),
				URI.create("/o/c/ticketattachments/" + ticketAttachmentId)));

		return new TicketAttachment(jsonObject);
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentService.class);

	@Value("${liferay.customer.gcs.bucket.name}")
	private String _gcsBucketName;

}