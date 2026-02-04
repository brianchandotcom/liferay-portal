/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.permission;

import com.liferay.headless.admin.user.client.dto.v1_0.AccountBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class AccountMemberPermission {

	public void check(String accountExternalReferenceCode, Jwt jwt)
		throws Exception {

		if (!_contains(accountExternalReferenceCode, jwt)) {
			throw new PrincipalException();
		}
	}

	private boolean _contains(String accountExternalReferenceCode, Jwt jwt)
		throws Exception {

		if (_defaultServiceAccountPermission.contains(jwt)) {
			return true;
		}

		UserAccount userAccount = _marketplaceService.getMyUserAccount(jwt);

		for (AccountBrief accountBrief : userAccount.getAccountBriefs()) {
			String accountBriefExternalReferenceCode =
				accountBrief.getExternalReferenceCode();

			if (Objects.equals(
					accountBriefExternalReferenceCode,
					accountExternalReferenceCode)) {

				return true;
			}
		}

		for (RoleBrief roleBrief : userAccount.getRoleBriefs()) {
			String roleBriefName = roleBrief.getName();

			if (roleBriefName.equals(RoleConstants.ADMINISTRATOR)) {
				return true;
			}
		}

		return false;
	}

	@Autowired
	private DefaultServiceAccountPermission _defaultServiceAccountPermission;

	@Autowired
	private MarketplaceService _marketplaceService;

}