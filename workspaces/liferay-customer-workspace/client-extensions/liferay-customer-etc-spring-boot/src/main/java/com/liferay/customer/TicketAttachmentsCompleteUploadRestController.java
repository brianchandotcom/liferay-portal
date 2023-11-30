/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.customer.object.model.TicketAttachment;
import com.liferay.customer.object.service.TicketAttachmentWebService;
import com.liferay.osb.spring.boot.zendesk.client.model.ZendeskUser;
import com.liferay.osb.spring.boot.zendesk.client.service.ZendeskWebService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Amos Fong
 */
@ComponentScan(basePackages = "com.liferay.osb")
@RequestMapping("/ticket-attachments/{ticketAttachmentId}/complete-upload")
@RestController
public class TicketAttachmentsCompleteUploadRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json,
			@PathVariable("ticketAttachmentId") long ticketAttachmentId)
		throws Exception {

		try {
			ZendeskUser zendeskUser = _zendeskWebService.fetchZendeskUser(
				jwt.getClaimAsString("username"));

			if (zendeskUser == null) {
				return new ResponseEntity<>(
					"Zendesk user " + jwt.getClaimAsString("username") +
						" does not exist",
					HttpStatus.FORBIDDEN);
			}

			TicketAttachment ticketAttachment =
				_ticketAttachmentWebService.approveTicketAttachment(
					jwt, ticketAttachmentId);
			JSONObject jsonObject = new JSONObject(json);

			String zendeskTicketCommentBody = _buildZendeskTicketCommentBody(
				ticketAttachment,
				jsonObject.optString("zendeskTicketCommentBody"));

			if (zendeskUser.isEndUser()) {
				_zendeskWebService.addEndUserZendeskTicketComment(
					zendeskUser.getEmailAddress(), zendeskTicketCommentBody,
					ticketAttachment.getZendeskTicketId());
			}
			else {
				_zendeskWebService.addAgentZendeskTicketComment(
					zendeskTicketCommentBody,
					ticketAttachment.getZendeskTicketId(),
					zendeskUser.getZendeskUserId());
			}

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String _buildZendeskTicketCommentBody(
			TicketAttachment ticketAttachment, String zendeskTicketCommentBody)
		throws Exception {

		StringBundler sb = new StringBundler(11);

		if (Validator.isNotNull(zendeskTicketCommentBody)) {
			sb.append(
				StringUtil.replace(
					zendeskTicketCommentBody, CharPool.NEW_LINE, "<br />"));
			sb.append("<br /><br />");
		}

		sb.append("<a href=\"");
		sb.append(_lxcDXPServerProtocol);
		sb.append("://");
		sb.append(_lxcDXPMainDomain);
		sb.append("/placeholder/");
		sb.append(ticketAttachment.getTicketAttachmentId());
		sb.append("\">");
		sb.append(ticketAttachment.getFileName());
		sb.append("</a>");

		return sb.toString();
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentsCompleteUploadRestController.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private TicketAttachmentWebService _ticketAttachmentWebService;

	@Autowired
	private ZendeskWebService _zendeskWebService;

}