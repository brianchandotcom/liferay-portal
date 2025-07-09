/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.exception.TicketAccessPermissionException;
import com.liferay.customer.exception.ZendeskOrganizationNotFoundException;
import com.liferay.customer.exception.ZendeskTicketClosedException;
import com.liferay.customer.exception.ZendeskTicketNotFoundException;
import com.liferay.customer.permission.TicketAttachmentsPermission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karoline Silva
 */
@ComponentScan(basePackages = {"com.liferay.osb", "com.liferay.customer"})
@RequestMapping("/ticket-attachments/validate-access/")
@RestController
public class TicketAttachmentsAccessValidationRestController
	extends BaseRestController {

	@GetMapping("/{ticketId}")
	public ResponseEntity<String> get(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("ticketId") long zendeskTicketId) {

		try {
			_ticketAttachmentsPermission.check(jwt, zendeskTicketId);

			return new ResponseEntity<>("", HttpStatus.OK);
		}
		catch (TicketAccessPermissionException
					ticketAccessPermissionException) {

			_log.error(
				ticketAccessPermissionException,
				ticketAccessPermissionException);

			return new ResponseEntity<>(
				"FORBIDDEN_ACCESS", HttpStatus.FORBIDDEN);
		}
		catch (ZendeskTicketNotFoundException zendeskTicketNotFoundException) {
			_log.error(
				zendeskTicketNotFoundException, zendeskTicketNotFoundException);

			return new ResponseEntity<>(
				"INVALID_TICKET_NUMBER", HttpStatus.NOT_FOUND);
		}
		catch (ZendeskTicketClosedException zendeskTicketClosedException) {
			_log.error(
				zendeskTicketClosedException, zendeskTicketClosedException);

			return new ResponseEntity<>(
				"TICKET_IS_CLOSED", HttpStatus.BAD_REQUEST);
		}
		catch (ZendeskOrganizationNotFoundException
					zendeskOrganizationNotFoundException) {

			_log.error(
				zendeskOrganizationNotFoundException,
				zendeskOrganizationNotFoundException);

			return new ResponseEntity<>(
				"ZENDESK_ORGANIZATION_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				"UNEXPECTED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentsAccessValidationRestController.class);

	@Autowired
	private TicketAttachmentsPermission _ticketAttachmentsPermission;

}