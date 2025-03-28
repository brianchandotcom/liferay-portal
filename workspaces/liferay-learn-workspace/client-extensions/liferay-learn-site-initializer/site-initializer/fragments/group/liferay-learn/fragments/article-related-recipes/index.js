/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

async function createRecipeSuggestions() {
	const articleId = document.querySelector(
		'.article-related-recipes'
	).dataset.articleId;

	const structuredContent = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/structured-contents/by-key/${articleId}`
	).then((response) => response.json());

	if (structuredContent.keywords.length) {
		createRecipesContainer();
		await getRecipeKeywordsSearch(structuredContent.keywords);
	}
}

async function getRecipeKeywordsSearch(articleKeywords) {
	const searchParams = new URLSearchParams({
		fields: 'friendlyUrlPath,title,datePublished,description',
		search: articleKeywords.slice(0, articleKeywords.length).join(","),
		sort: 'datePublished:desc',
		pageSize: '3',
	}).toString();

	const response = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/structured-content-folders/34105240/structured-contents?${searchParams}`
	).then((response) => response.json());

	const responseItems = response.items;

	responseItems.forEach((item) => 
		createRecipeLinkCard(item.title, item.datePublished, item.description, item.friendlyUrlPath)
	);
}

function createRecipesContainer() {
	const recipesContainer = document.createElement('div');
	
	recipesContainer.classList.add('recipes-container');
	
	const recipesContainerHeader = document.createElement('div');
	
	recipesContainerHeader.classList.add('recipes-container-header');
	recipesContainerHeader.innerText = 'Recipes related to this article';
	
	const recipesCardsContainer = document.createElement('div');
	
	recipesCardsContainer.classList.add('recipes-cards-container');
	recipesCardsContainer.setAttribute('id', 'recipes-cards-container');

	recipesContainer.appendChild(recipesContainerHeader);
	recipesContainer.appendChild(recipesCardsContainer);
	
	const recipes = document.getElementById('article-related-recipes');
	
	recipes.appendChild(recipesContainer);
}

function createRecipeLinkCard(title, datePublished, description, friendlyUrl) {
	const recipeCardDiv = document.createElement('div');
	
	recipeCardDiv.classList.add('recipe-card');
	recipeCardDiv.onclick = function () {
		window.location.href =
		`${Liferay.ThemeDisplay.getCDNBaseURL()}` +
		'/w/' +
		`${friendlyUrl}/`;
	};
	
	recipeCardDiv.innerHTML = `
	<div class="recipe-card-header">${title}</div>
	<div class="recipe-card-description">${description || ''}</div>
	<div class="recipe-card-date-published">Published Date: ${formatDate(datePublished)}</div>
	`;
	
	const displayContainer = document.getElementById('recipes-cards-container');

	displayContainer.appendChild(recipeCardDiv);
}

function formatDate(datePublished) {
	const date = new Date(datePublished);
	const options = {
		day: 'numeric',
		hour: 'numeric',
		hour12: true,
		minute: '2-digit',
		month: 'short',
		year: '2-digit',
	};

	return date.toLocaleString('en-US', options);
}

createRecipeSuggestions();
