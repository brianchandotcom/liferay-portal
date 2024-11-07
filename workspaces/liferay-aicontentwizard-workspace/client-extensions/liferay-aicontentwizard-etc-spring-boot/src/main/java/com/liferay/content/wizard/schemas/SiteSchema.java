/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.schemas;

import com.liferay.content.wizard.enums.MembershipType;
import com.liferay.content.wizard.enums.TemplateKeys;
import com.liferay.headless.site.client.dto.v1_0.Site;

import dev.langchain4j.model.output.structured.Description;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class SiteSchema {

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public MembershipType getMembershipType() {
		return _membershipType;
	}

	public String getName() {
		return _name;
	}

	public String getTemplateKey() {
		if (Objects.equals(_templateKey, TemplateKeys.BLANK)) {
			return "blank-site-initializer";
		}

		if (Objects.equals(_templateKey, TemplateKeys.MASTERCLASS)) {
			return "com.liferay.site.initializer.masterclass";
		}

		if (Objects.equals(_templateKey, TemplateKeys.MINIUM)) {
			return "minium-initializer";
		}

		if (Objects.equals(_templateKey, TemplateKeys.MINIUM_FULL)) {
			return "minium-full-initializer";
		}

		if (Objects.equals(_templateKey, TemplateKeys.RAYLIFE_AP)) {
			return "com.liferay.site.initializer.raylife.ap";
		}

		if (Objects.equals(_templateKey, TemplateKeys.RAYLIFE_D2C)) {
			return "com.liferay.site.initializer.raylife.d2c";
		}

		if (Objects.equals(_templateKey, TemplateKeys.SPEEDWELL)) {
			return "speedwell-initializer";
		}

		if (Objects.equals(_templateKey, TemplateKeys.TEAM_EXTRANET)) {
			return "com.liferay.site.initializer.team.extranet";
		}

		if (Objects.equals(_templateKey, TemplateKeys.WELCOME)) {
			return "com.liferay.site.initializer.welcome";
		}

		return null;
	}

	public Site toSite() {
		String templateKey = getTemplateKey();

		String templateType = "site-template";

		if (templateKey.contains("site.initializer") ||
			templateKey.contains("site-initializer")) {

			templateType = "site-initializer";
		}

		return Site.toDTO(
			new JSONObject(
			).put(
				"externalReferenceCode", _externalReferenceCode
			).put(
				"membershipType", _membershipType
			).put(
				"name", _name
			).put(
				"templateKey", getTemplateKey()
			).put(
				"templateType", templateType
			).toString());
	}

	@Description(
		"Site ERC, if not specified by the user is auto generated UUID"
	)
	private String _externalReferenceCode;

	@Description(
		"Membership type, value is lower case, default option is 'OPEN'"
	)
	private MembershipType _membershipType;

	@Description("Site Name")
	private String _name;

	@Description("Site Template Key, default is BLANK")
	private TemplateKeys _templateKey;

}