/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.constants;

/**
 * @author Felipe Franca
 */
public interface RoleConstants {

	public static final String NAME_ACCOUNT_ADMINISTRATOR =
		"Account Administrator";

	public static final String NAME_ACCOUNT_MEMBER = "Account Member";

	public static final String NAME_ADMINISTRATOR = "Administrator";

	public static final String NAME_LIFERAY_STAFF = "Liferay Staff";

	public static final String NAME_REQUESTER = "Requester";

	public static final String[] NAMES_SUPPORT_ACCOUNT = {
		NAME_ACCOUNT_ADMINISTRATOR, NAME_ACCOUNT_MEMBER, NAME_REQUESTER
	};

	public static final String[] NAMES_SUPPORT_ACCOUNT_TICKET = {
		NAME_ACCOUNT_ADMINISTRATOR, NAME_REQUESTER
	};

}