/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/portal/no-global-fetch */

/* eslint-disable no-undef */

const editMode = layoutMode === 'edit';
const previewMode = layoutMode === 'preview';
const siteURL = Liferay.ThemeDisplay.getLayoutURL().split('/release-notes')[0];

const fetchRequest = async (input) => {
	const response = await fetch(input, {
		headers: {
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Cache-Control': 'max-age=30, stale-while-revalidate=30',
			'x-csrf-token': Liferay.authToken,
		},
	});

	return response.json();
};

const handleRedirect = async (classnameId) => {
	const vocabulary = await fetchRequest(
		`/o/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getSiteGroupId()}/taxonomy-vocabularies/by-external-reference-code/QUARTERLY-RELEASES`
	);

	if (vocabulary) {
		const taxonomyCategoriesResponse = await fetchRequest(
			`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${vocabulary.id}/taxonomy-categories?pageSize=4&sort=name:desc`
		);

		if (taxonomyCategoriesResponse) {
			const releaseCategory = taxonomyCategoriesResponse.items[0];

			if (releaseCategory) {
				location.assign(
					`${siteURL}/e/release-notes/release-highlights/${classnameId}/${releaseCategory.id}?r=${releaseCategory.id}`
				);
			}
		}
	}
};

if (!previewMode && !editMode && configuration.classnameId) {
	handleRedirect(configuration.classnameId);
}
