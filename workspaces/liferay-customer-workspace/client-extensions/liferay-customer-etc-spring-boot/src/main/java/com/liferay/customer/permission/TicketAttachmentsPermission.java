/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.permission;

import com.liferay.customer.constants.RoleConstants;
import com.liferay.customer.exception.TicketAccessPermissionException;
import com.liferay.customer.exception.ZendeskOrganizationNotFoundException;
import com.liferay.customer.exception.ZendeskTicketClosedException;
import com.liferay.customer.exception.ZendeskTicketNotFoundException;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskOrganization;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskTicket;
import com.liferay.osb.spring.boot.client.zendesk.service.ZendeskService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * @author Karoline Silva
 */
@Component
public class TicketAttachmentsPermission {

	public void check(Jwt jwt, long zendeskTicketId) throws Exception {
		String ticketAccountKey = _getTicketAccountKey(zendeskTicketId);

		UserAccount userAccount = _getUserAccount(jwt);

		if (!_hasAccountAccess(jwt, userAccount, ticketAccountKey)) {
			throw new TicketAccessPermissionException();
		}
	}

	public void checkUploadPermission(Jwt jwt, long zendeskTicketId)
		throws Exception {

		if (!_hasRoleForTicket(
				_getUserAccount(jwt), _getTicketAccountKey(zendeskTicketId))) {

			throw new TicketAccessPermissionException();
		}
	}

	private String _getTicketAccountKey(long zendeskTicketId) throws Exception {
		try {
			ZendeskTicket zendeskTicket = _zendeskService.getZendeskTicket(
				zendeskTicketId);

			if (zendeskTicket == null) {
				throw new ZendeskTicketNotFoundException();
			}

			if (zendeskTicket.isClosed()) {
				throw new ZendeskTicketClosedException();
			}

			ZendeskOrganization zendeskOrganization =
				_zendeskService.getZendeskOrganization(
					zendeskTicket.getZendeskOrganizationId());

			if (zendeskOrganization == null) {
				throw new ZendeskOrganizationNotFoundException();
			}

			return zendeskOrganization.getAccountKey();
		}
		catch (WebClientResponseException.NotFound webClientResponseException) {
			_log.error(webClientResponseException, webClientResponseException);

			throw new ZendeskTicketNotFoundException(
				webClientResponseException);
		}
	}

	private UserAccount _getUserAccount(Jwt jwt) throws Exception {
		UserAccountResource userAccountResource = UserAccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			new URL(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
		).build();

		UserAccount userAccount = userAccountResource.getMyUserAccount();

		if (userAccount == null) {
			throw new TicketAccessPermissionException();
		}

		return userAccount;
	}

	private boolean _hasAccountAccess(
			Jwt jwt, UserAccount userAccount, String ticketAccountKey)
		throws Exception {

		for (AccountBrief accountBrief : userAccount.getAccountBriefs()) {
			if (ticketAccountKey.equals(
					accountBrief.getExternalReferenceCode())) {

				return true;
			}
		}

		AccountResource accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			new URL(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
		).build();

		Account ticketAccount =
			accountResource.getAccountByExternalReferenceCode(ticketAccountKey);

		if ((ticketAccount == null) ||
			(ticketAccount.getOrganizationIds() == null)) {

			return false;
		}

		Long[] ticketOrganizationIds = ticketAccount.getOrganizationIds();

		for (OrganizationBrief userOrganizationBrief :
				userAccount.getOrganizationBriefs()) {

			for (long ticketOrganizationId : ticketOrganizationIds) {
				if (userOrganizationBrief.getId() == ticketOrganizationId) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _hasRoleForTicket(
		UserAccount userAccount, String ticketAccountKey) {

		for (AccountBrief accountBrief : userAccount.getAccountBriefs()) {
			if (ticketAccountKey.equals(
					accountBrief.getExternalReferenceCode())) {

				for (RoleBrief roleBrief : accountBrief.getRoleBriefs()) {
					if (ArrayUtil.contains(
							RoleConstants.SUPPORT_ACCOUNT_TICKET_ROLES,
							roleBrief.getName())) {

						return true;
					}
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentsPermission.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private ZendeskService _zendeskService;

}