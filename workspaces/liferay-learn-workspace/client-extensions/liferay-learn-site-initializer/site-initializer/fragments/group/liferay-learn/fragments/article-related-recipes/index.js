/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function createRecipeCard(title, datePublished, description, friendlyUrl) {
	const recipeCardDiv = document.createElement('div');

	recipeCardDiv.classList.add('recipe-card');
	recipeCardDiv.onclick = function () {
		window.location.href = `${Liferay.ThemeDisplay.getCDNBaseURL()}/w/${friendlyUrl}/`;
	};
	recipeCardDiv.innerHTML = `
		<div class="recipe-card-header">${title}</div>
		<div class="recipe-card-description">${description || ''}</div>
		<div class="recipe-card-date-published">Published Date: ${formatDate(datePublished)}</div>
	`;

	const displayContainer = document.getElementById('recipes-cards-container');

	displayContainer.appendChild(recipeCardDiv);
}

async function createRecipeSuggestions() {
	const articleId = document.querySelector('.article-related-recipes').dataset
		.articleId;

	const structuredContent = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/structured-contents/by-key/${articleId}`
	).then((response) => response.json());

	if (structuredContent.keywords.length) {
		createRecipesContainer();

		const structuredContentRecipes = await getRecipesKeywords(
			structuredContent.keywords
		);

		structuredContentRecipes.forEach((item) =>
			createRecipeCard(
				item.title,
				item.datePublished,
				item.description,
				item.friendlyUrlPath
			)
		);
	}
}

function createRecipesContainer() {
	const recipes = document.getElementById('article-related-recipes');

	recipes.innerHTML = `
		<div class="recipes-container">
			<div class="recipes-container-header">Recipes related to this article</div>
			<div class="recipes-cards-container" id="recipes-cards-container"></div>
		</div>
	`;
}

function formatDate(datePublished) {
	const date = new Date(datePublished);

	return date.toLocaleString('en-US', {
		day: 'numeric',
		hour: 'numeric',
		hour12: true,
		minute: '2-digit',
		month: 'short',
		year: '2-digit',
	});
}

async function getRecipesFolderId() {
	const searchParams = new URLSearchParams({
		filter: "name eq 'recipes'",
	}).toString();

	const response = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/structured-content-folders?${searchParams}`
	).then((response) => response.json());

	return response.items[0].id;
}

async function getRecipesKeywords(articleKeywords) {
	const recipesFolderId = await getRecipesFolderId();

	const searchParams = new URLSearchParams({
		fields: 'friendlyUrlPath,title,datePublished,description',
		pageSize: '3',
		search: articleKeywords.slice(0, articleKeywords.length).join(','),
		sort: 'datePublished:desc',
	}).toString();

	const response = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/structured-content-folders/${recipesFolderId}/structured-contents?${searchParams}`
	).then((response) => response.json());

	return response.items;
}

createRecipeSuggestions();
