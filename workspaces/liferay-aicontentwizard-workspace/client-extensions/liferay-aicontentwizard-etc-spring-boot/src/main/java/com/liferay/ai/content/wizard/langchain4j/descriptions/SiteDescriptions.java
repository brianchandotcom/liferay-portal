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
public class SiteDescriptions {

	@Description(
		"The site's external reference code; auto-generated if not specified"
	)
	public String externalReferenceCode;

	@Description(
		"Membership type (open, restricted, or private). Defaults to 'open'."
	)
	public MembershipType membershipType;

	@Description("Site name")
	public String name;

	@Description("Foreign key to a linked site template")
	public TemplateKey templateKey;

	public enum MembershipType {

		@Description(
			"The site appears in My Sites. Users can join and leave at will."
		)
		Open,
		@Description(
			"The site appears in My Sites. Users must request membership to join."
		)
		Private,
		@Description(
			"The site is not in My Sites. Users cannot join or request membership; site administrators must add them."
		)
		Restricted

	}

	public enum TemplateKey {

		BLANK(""), MASTERCLASS("com.liferay.site.initializer.masterclass"),
		MINIUM("minium-initializer"), MINIUM_FULL("minium-full-initializer"),
		RAYLIFE_AP("com.liferay.site.initializer.raylife.ap"),
		RAYLIFE_D2C("com.liferay.site.initializer.raylife.d2c"),
		SPEEDWELL("speedwell-initializer"),
		TEAM_EXTRANET("com.liferay.site.initializer.team.extranet"),
		WELCOME("com.liferay.site.initializer.welcome");

		public String toString() {
			return _string;
		}

		private TemplateKey(String string) {
			_string = string;
		}

		private final String _string;

	}

}