/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFINE_QUERY_PARAMS = `
var queryParams = '';

if (window.faroConstants && window.faroConstants.locale) {
	queryParams = '?languageId=' + encodeURIComponent(window.faroConstants.locale);
}
`;

class BundleQueryStringPlugin {
	apply(compiler) {
		compiler.hooks.compilation.tap(
			'BundleQueryStringPlugin',
			(compilation) => {
				if (compilation.mainTemplate.hooks.jsonpScript) {
					compilation.mainTemplate.hooks.jsonpScript.tap(
						'BundleQueryStringPlugin',
						(result) =>
							DEFINE_QUERY_PARAMS +
							result.replace(
								/(script\.src.*);/g,
								'$1 + queryParams;'
							)
					);
				}
			}
		);
	}
}

module.exports = BundleQueryStringPlugin;
