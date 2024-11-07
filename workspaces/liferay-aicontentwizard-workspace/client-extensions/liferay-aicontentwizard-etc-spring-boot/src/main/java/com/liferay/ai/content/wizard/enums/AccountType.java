/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.enums;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 */
public enum AccountType {

	@Description(
		"Business accounts are used in B2C or B2X sites, default option."
	)
	business,
	@Description("Used based on the site type (i.e., B2B, or B2C)")
	person,
	@Description(
		"For accounts that are catalog suppliers, can publish products on their behalf"
	)
	supplier

}