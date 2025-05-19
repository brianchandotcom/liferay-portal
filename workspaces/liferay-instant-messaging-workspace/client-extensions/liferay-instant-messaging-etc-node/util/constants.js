/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lookupConfig} = require('@rotty3000/config-node');

const applicationExternalReferenceCodes = {
	OAUTH_AGENT_EXTERNAL_REFERENCE_CODE: lookupConfig(
		'main.liferay.agent.oauth.application'
	),

	OAUTH_SERVER_EXTERNAL_REFERENCE_CODE: lookupConfig(
		'main.liferay.server.oauth.application'
	),
};

module.exports = {
	applicationExternalReferenceCodes,
};
