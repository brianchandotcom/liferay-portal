/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.customer.google.service.GoogleCloudStorageWebService;
import com.liferay.customer.object.model.TicketAttachment;
import com.liferay.customer.object.service.TicketAttachmentWebService;
import com.liferay.customer.zendesk.model.ZendeskOrganization;
import com.liferay.customer.zendesk.model.ZendeskTicket;
import com.liferay.customer.zendesk.service.ZendeskWebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/ticket-attachment/initiate-upload")
@RestController
public class TicketAttachmentInitiateUploadRestController
	extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(
			@AuthenticationPrincipal Jwt jwt,
			@RequestParam(name = "zendeskTicketId") long zendeskTicketId,
			@RequestParam(name = "fileName") String fileName,
			@RequestParam(name = "fileSize") String fileSize,
			@RequestParam(name = "md5Checksum") String md5Checksum,
			@RequestParam(name = "type", required = false) String type)
		throws Exception {

		try {
			TicketAttachment ticketAttachment =
				_ticketAttachmentWebService.fetchTicketAttachment(
					jwt, zendeskTicketId, fileName, md5Checksum);

			if (ticketAttachment != null) {
				if (ticketAttachment.isApproved()) {
					return new ResponseEntity(
						"This attachment already exists with ID " +
							ticketAttachment.getTicketAttachmentId(),
						HttpStatus.CONFLICT);
				}
			}
			else {
				ticketAttachment =
					_ticketAttachmentWebService.addTicketAttachment(
						jwt, _getAccountKey(zendeskTicketId), zendeskTicketId,
						fileName, fileSize, md5Checksum, type,
						TicketAttachment.STATUS_DRAFT);
			}

			return new ResponseEntity<>(
				_googleCloudStorageWebService.getUploadSessionURL(
					ticketAttachment.getGCSBucketName(),
					ticketAttachment.getGCSObjectName()),
				HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception);

			return new ResponseEntity(
				"There was an unexpected error",
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String _getAccountKey(long zendeskTicketId) throws Exception {
		ZendeskTicket zendeskTicket = _zendeskWebService.getZendeskTicket(
			zendeskTicketId);

		if (zendeskTicket.isClosed()) {
			throw new Exception("This ticket is closed");
		}

		ZendeskOrganization zendeskOrganization =
			_zendeskWebService.getZendeskOrganization(
				zendeskTicket.getZendeskOrganizationId());

		return zendeskOrganization.getAccountKey();
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentInitiateUploadRestController.class);

	@Autowired
	private GoogleCloudStorageWebService _googleCloudStorageWebService;

	@Autowired
	private TicketAttachmentWebService _ticketAttachmentWebService;

	@Autowired
	private ZendeskWebService _zendeskWebService;

}