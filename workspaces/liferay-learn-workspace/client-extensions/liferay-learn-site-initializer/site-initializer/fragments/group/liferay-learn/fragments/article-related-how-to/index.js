/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function createHowToCard(title, dateModified, howToId) {
	const howToCardDiv = document.createElement('div');

	howToCardDiv.classList.add('how-to-card');
	howToCardDiv.onclick = function () {
		window.location.href = `${Liferay.ThemeDisplay.getCDNBaseURL()}/l/${howToId}/`;
	};
	howToCardDiv.innerHTML = `
		<div class="how-to-card-header">${title}</div>
		<div class="how-to-card-date-published">Published Date: ${formatDate(dateModified)}</div>
	`;

	const displayContainer = document.getElementById('how-to-cards-container');

	displayContainer.appendChild(howToCardDiv);
}

function createHowToContainer() {
	const howTo = document.getElementById('article-related-how-to');

	howTo.innerHTML = `
		<div class="how-to-container">
			<div class="how-to-container-header">How To related to this article</div>
			<div class="how-to-cards-container" id="how-to-cards-container"></div>
		</div>
	`;
}

async function createHowToSuggestions() {
	const articleId = document.querySelector('.article-related-how-to').dataset.articleId;

	const structuredContent = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/structured-contents/by-key/${articleId}`
	).then((response) => response.json());

	if (structuredContent.keywords.length) {
		const structuredContentHowTo = await getHowToKeywords(
			structuredContent.keywords
		);

		if(structuredContentHowTo.totalCount > 0) {
			createHowToContainer();

			structuredContentHowTo.items.forEach((item) =>
				createHowToCard(
					item.title,
					item.dateModified,
					item.id
				)
			);
		}
	}
}

function formatDate(dateModified) {
	const date = new Date(dateModified);

	return date.toLocaleString('en-US', {
		day: 'numeric',
		hour: 'numeric',
		hour12: true,
		minute: '2-digit',
		month: 'short',
		year: '2-digit',
	});
}

async function getHowToKeywords(articleKeywords) {
	const searchParams = new URLSearchParams({
		fields: 'dateModified,id,title',
		filter: "(knowledgeArticleType eq 'howTo') and (status eq 0) and (sourceTeam eq 'Enablement')",
		pageSize: '3',
		search: articleKeywords.slice(0, articleKeywords.length).join(','),
		sort: 'dateModified:desc',
	}).toString();

	const response = await Liferay.Util.fetch(
		`/o/c/p2s3knowledgearticles/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}?${searchParams}`
	).then((response) => response.json());

	return response;
}

createHowToSuggestions();
