/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.descriptions;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class AccountDescriptions {

	@Description("Account Description")
	public String description;

	@Description("Account Name")
	public String name;

	@Description(
		"Liferay provides three account types: Business, Person, and Supplier."
	)
	public Type type;

	public enum Type {

		@Description(
			"Business accounts are used by default in B2C or B2X sites."
		)
		business,
		@Description("Used based on the site type (i.e., B2B, or B2C)")
		person,
		@Description(
			"Can publish products on behalf of catalog supplier accounts."
		)
		supplier

	}

}