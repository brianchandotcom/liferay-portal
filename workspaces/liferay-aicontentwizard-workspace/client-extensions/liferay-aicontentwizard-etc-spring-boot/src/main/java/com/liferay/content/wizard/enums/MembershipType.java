/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.enums;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 */
public enum MembershipType {

	@Description(
		"Users can join and leave whenever they want. The site is visible to all users in the My Sites tab"
	)
	Open,
	@Description(
		"The site appears in the My Sites application, but users must request membership to join"
	)
	Private,
	@Description(
		"A site administrator must explicitly add users to the site. Private membership sites don’t appear in the My Sites app"
	)
	Restricted

}