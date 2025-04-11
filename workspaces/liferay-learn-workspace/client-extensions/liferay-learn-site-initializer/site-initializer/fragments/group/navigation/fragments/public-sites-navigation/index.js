/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */
['DOMContentLoaded', 'resize'].forEach((event) =>
	window.addEventListener(event, checkScreenSize)
);

const controlMenu = document.getElementById('ControlMenu');
const documentationEducationDropdown = document.querySelector(
	'.documentation-education-dropdown'
);
const fragmentSearchElements = {
	searchSubmitLink: fragmentElement.querySelector('.search-submit'),
	searchSubmitURL: fragmentElement.querySelector('.search-submit').href,
	searchSuggestionItemTemplate: fragmentElement.querySelector(
		'.suggestions template'
	),
	searchSuggestions: fragmentElement.querySelector('.search-suggestions'),
	searchSuggestionsInput: fragmentElement.querySelector(
		'.search-suggestions-input'
	),
	seeAllResultsLink: fragmentElement.querySelector(
		'.search-suggestions-see-all-results-text'
	),
	suggestions: fragmentElement.querySelector('.suggestions'),
};
const inputElements = ['input', 'textarea'];
const menuElements = {
	menuButton: document.querySelector('.cta-menu-hamburguer'),
	menuIconLines: document.querySelectorAll(
		'.nav-items-menu-button-icon-line'
	),
	menuTextClose: document.querySelector('.menu-button-text-close'),
	menuTextOpen: document.querySelector('.menu-button-text-open'),
};
const searchInput = document.getElementById('searchInput');
const siteSearchWrapper = document.getElementById('siteSearchWrapper');

let debounceTimer;

if (controlMenu) {
	const publicSiteNavigationContainer = document.querySelector(
		'.public-site-navigation-container'
	);

	if (publicSiteNavigationContainer) {
		publicSiteNavigationContainer.style.top = '3.5rem';
	}
}

async function postData(data = {}, url = '') {
	const response = await Liferay.Util.fetch(url, {
		body: JSON.stringify(data),
		credentials: 'include',
		headers: {
			'Accept': 'application/json',
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'POST',
	});

	return response.json();
}

function changeFocus() {
	searchInput.focus();
}

function checkScreenSize() {
	menuElements.menuTextOpen.style.display = 'inline';
	menuElements.menuTextClose.style.display = 'none';
	menuElements.menuButton.classList.remove('open');

	if (window.innerWidth <= 1000) {
		documentationEducationDropdown.classList.add('hide');
	}
	else {
		documentationEducationDropdown.classList.remove('hide');
		resetMenuIcon();
	}
}

function debounce(callback, time) {
	window.clearTimeout(debounceTimer);

	debounceTimer = window.setTimeout(callback, time);
}

function getBreadcrumbFromURL(url) {
	if (!url) {
		return '';
	}

	url = url
		.replaceAll('/web/guest/w/', 'home/')
		.replaceAll('/web/guest/', 'home/')
		.replaceAll('/', ' / ')
		.replaceAll('-', ' ');

	const ancronymList = ['api', 'ccr', 'dxp', 'mvc', ' ui ', 'url'];

	ancronymList.forEach((word) => {
		url = url.replace(new RegExp(word, 'ig'), word.toUpperCase());
	});

	const excludeWords = ['a', 'and', 'of', 'the', 'to', 'via'];

	return url
		.split(' ')
		.map((word, i) =>
			excludeWords.includes(word) && i !== 0
				? word
				: word.charAt(0).toUpperCase() + word.slice(1)
		)
		.join(' ');
}

function performSearch(query) {
	postData(
		[
			{
				attributes: {
					includeAssetSearchSummary: true,
					includeAssetURL: true,
					sxpBlueprintId: configuration.searchBlueprintId,
				},
				contributorName: 'sxpBlueprint',
				displayGroupName: 'Public Nav Search Recommendations',
				size: '3',
			},
		],
		`/o/portal-search-rest/v1.0/suggestions?currentURL=
			${window.location.href}&destinationFriendlyURL=/search&groupId=
			${Liferay.ThemeDisplay.getScopeGroupId()}&plid=
			${Liferay.ThemeDisplay.getPlid()}&scope=this-site&search=
			${query}`
	)
		.then((data) => {
			if (data?.items?.[0]) {
				fragmentSearchElements.searchSuggestions.innerHTML = '';

				const items = JSON.parse(JSON.stringify(data.items[0]));

				items.suggestions.forEach((suggestion) => {
					const suggestionLink = document.importNode(
						fragmentSearchElements.searchSuggestionItemTemplate.content.querySelector(
							'a'
						),
						true
					);
					const assetURL = suggestion.attributes.assetURL.replace(
						/\?.*$/,
						''
					);

					suggestionLink.href = assetURL;
					const suggestionTitle = suggestionLink.querySelector(
						'.search-suggestion-item-title'
					);

					suggestionTitle.textContent = suggestion.text;
					let contentText = suggestion.attributes.assetSearchSummary;

					if (contentText) {
						contentText = contentText.substring(0, 500);

						const suggestionContent = suggestionLink.querySelector(
							'.search-suggestion-item-content'
						);

						suggestionContent.innerHTML = contentText.replace(
							new RegExp('(' + query + ')', 'gi'),
							`<b>$1</b>`
						);
					}

					const suggestionURL = suggestionLink.querySelector(
						'.search-suggestion-item-link'
					);

					suggestionURL.textContent = getBreadcrumbFromURL(assetURL);
					fragmentSearchElements.searchSuggestions.appendChild(
						suggestionLink
					);
					fragmentSearchElements.suggestions.classList.add(
						'search-results-found'
					);
				});
			}
			else {
				fragmentSearchElements.suggestions.classList.remove(
					'search-results-found'
				);
			}

			fragmentSearchElements.suggestions.classList.remove(
				'loading-search',
				'search-error'
			);
		})
		.catch(() => {
			fragmentSearchElements.suggestions.classList.add('search-error');
		});
}

function resetMenuIcon() {
	menuElements.menuIconLines.forEach((line) => {
		line.style.transform = '';
		line.style.opacity = '';
	});
}

function toggleMenuAnimation() {
	const isOpen = menuElements.menuButton.classList.contains('open');

	menuElements.menuIconLines.forEach((line, index) => {
		line.style.opacity = index === 1 && isOpen ? '0' : '1';
		line.style.transform = isOpen
			? `rotate(${index === 0 ? 45 : -45}deg) translateY(${index === 0 ? 8 : -8}px)`
			: '';
	});

	menuElements.menuTextOpen.style.display = isOpen ? 'none' : 'inline';
	menuElements.menuTextClose.style.display = isOpen ? 'inline' : 'none';
}

function updateSearch() {
	fragmentSearchElements.searchSuggestions.innerHTML = '';
	const searchValue = fragmentSearchElements.searchSuggestionsInput.value;

	if (searchValue) {
		fragmentSearchElements.seeAllResultsLink.href =
			fragmentSearchElements.searchSubmitURL + '?q=' + searchValue;
		fragmentSearchElements.searchSubmitLink.href =
			fragmentSearchElements.searchSubmitURL + '?q=' + searchValue;
		fragmentSearchElements.suggestions.classList.add('performing-search');

		performSearch(searchValue);
	}
	else {
		fragmentSearchElements.suggestions.classList.remove(
			'loading-search',
			'performing-search',
			'search-error',
			'search-results-found'
		);
	}
}

window.addEventListener('load', () => {
	if (!navigation?.default?.DropdownProvider) {
		return;
	}

	fragmentSearchElements.searchSuggestionsInput.value = '';
	[
		['.account-info', '.account-info', 'menu-open'],
		['.account-info', '.account-dropdown-nav', 'show', true],
		['.sites', '.liferay-sites-dropdown', 'show', true],
		['.sites', '.sites', 'show', true],
		['.menu-button-group', '.menu-button-group', 'menu-open'],
		['.menu-button-group', '.tablet-mobile-nav-section', 'menu-open', true],
		['.language', '.language-selector', 'list-open', true],
		['.language', '.language-dropdown-list-container', 'list-open', true],
		['.search-icon, .close-search', '.search-wrapper', 'search-open', true],
	].forEach((args) => new navigation.default.DropdownProvider(...args));
});

menuElements.menuButton.addEventListener('click', () => {
	documentationEducationDropdown.classList.toggle('hide');
	menuElements.menuButton.classList.toggle('open');
	toggleMenuAnimation();
});

fragmentSearchElements.searchSuggestionsInput.addEventListener('input', () => {
	fragmentSearchElements.suggestions.classList.add('loading-search');
	debounce(updateSearch, 250);
});

document.getElementById('searchIcon').addEventListener('click', changeFocus);

window.addEventListener('keyup', (event) => {
	if (event.code === 'Escape' || event.key === 'Escape') {
		if (!siteSearchWrapper.classList.contains('search-open')) {
			return;
		}

		searchInput.blur();
		siteSearchWrapper.classList.remove('search-open');
	}
	if (
		(event.code === 'Slash' || event.key === '/') &&
		inputElements.indexOf(document.activeElement.tagName.toLowerCase()) ===
			-1
	) {
		searchInput.focus();
		if (siteSearchWrapper.classList.contains('search-open')) {
			return;
		}

		siteSearchWrapper.classList.add('search-open');
	}
});
