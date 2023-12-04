/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.customer.google.service.GoogleCloudStorageWebService;
import com.liferay.customer.object.model.TicketAttachment;
import com.liferay.customer.object.service.TicketAttachmentWebService;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskOrganization;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskTicket;
import com.liferay.osb.spring.boot.client.zendesk.service.ZendeskWebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Amos Fong
 */
@ComponentScan(basePackages = "com.liferay.osb")
@RequestMapping("/ticket-attachments/initiate-upload")
@RestController
public class TicketAttachmentsInitiateUploadRestController
	extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(
			@AuthenticationPrincipal Jwt jwt,
			@RequestParam(name = "fileName") String fileName,
			@RequestParam(name = "fileSize") String fileSize,
			@RequestParam(name = "md5Checksum") String md5Checksum,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "zendeskTicketId") long zendeskTicketId)
		throws Exception {

		try {
			TicketAttachment ticketAttachment =
				_ticketAttachmentWebService.fetchTicketAttachment(
					jwt, fileName, md5Checksum, zendeskTicketId);

			if (ticketAttachment != null) {
				if (ticketAttachment.isApproved()) {
					return new ResponseEntity(
						"Ticket attachment " +
							ticketAttachment.getTicketAttachmentId() +
								" already exists",
						HttpStatus.CONFLICT);
				}
			}
			else {
				ticketAttachment =
					_ticketAttachmentWebService.addTicketAttachment(
						jwt, _getAccountKey(zendeskTicketId), fileName,
						fileSize, md5Checksum, TicketAttachment.STATUS_DRAFT,
						type, zendeskTicketId);
			}

			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"gcsSessionURL",
				_googleCloudStorageWebService.getUploadSessionURL(
					ticketAttachment.getGCSBucketName(),
					ticketAttachment.getGCSObjectName())
			).put(
				"ticketAttachmentId", ticketAttachment.getTicketAttachmentId()
			);

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String _getAccountKey(long zendeskTicketId) throws Exception {
		ZendeskTicket zendeskTicket = _zendeskWebService.getZendeskTicket(
			zendeskTicketId);

		if (zendeskTicket.isClosed()) {
			throw new Exception(
				"Zendesk ticket " + zendeskTicketId + " is closed");
		}

		ZendeskOrganization zendeskOrganization =
			_zendeskWebService.getZendeskOrganization(
				zendeskTicket.getZendeskOrganizationId());

		return zendeskOrganization.getAccountKey();
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentsInitiateUploadRestController.class);

	@Autowired
	private GoogleCloudStorageWebService _googleCloudStorageWebService;

	@Autowired
	private TicketAttachmentWebService _ticketAttachmentWebService;

	@Autowired
	private ZendeskWebService _zendeskWebService;

}