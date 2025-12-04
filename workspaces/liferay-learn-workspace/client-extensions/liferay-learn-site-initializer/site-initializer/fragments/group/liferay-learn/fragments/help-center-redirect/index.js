/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

async function redirectToArticle() {
	const urlParams = new URLSearchParams(window.location.search);
	const externalReferenceCode = urlParams.get('erc');

	if (!externalReferenceCode) {
		handleError('ERC not found in URL parameters');

		return;
	}

	try {
		const response = await fetch(
			`/o/c/p2s3knowledgearticles/by-external-reference-code/${externalReferenceCode}?fields=id,content_i18n`,
			{
				credentials: 'include',
				headers: {
					Accept: 'application/json',
				},
				method: 'GET',
			}
		);

		if (!response.ok) {
			handleError(`Request failed with status: ${response.status}`);

			return;
		}

		const data = await response.json();

		const knowledgeArticleId = data.id;

		if (!knowledgeArticleId) {
			handleError('Knowledge article ID not found in the response');

			return;
		}

		const language = resolveLanguage(
			data.content_i18n,
			urlParams.get('lang')?.toLowerCase()
		);

		window.location.href = `${Liferay.ThemeDisplay.getCDNBaseURL()}/${language}/kb-article/${knowledgeArticleId}`;
	}
	catch (error) {
		handleError(`An error occurred during redirect: ${error}`);
	}
}

function handleError(errorMessage) {
	console.error(errorMessage);

	window.location.href = `/not-found-404`;
}

function resolveLanguage(content, paramLang) {
	if (
		(paramLang === 'ja' && content?.ja_JP) ||
		(!content?.en_US && content?.ja_JP)
	) {
		return 'ja';
	}

	return 'en';
}

redirectToArticle();
