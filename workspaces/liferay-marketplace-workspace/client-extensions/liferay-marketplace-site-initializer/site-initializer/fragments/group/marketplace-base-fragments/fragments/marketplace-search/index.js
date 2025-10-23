/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc.
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const SEARCH_DELAY = 800;
const SEARCH_STORAGE_KEY = '@marketplace/search';

const categoriesListItems = document.querySelectorAll(
	'.search-dropdown-list-item'
);
const categoriesTrigger = document.querySelector('.categories-trigger');
const channelId = Liferay.CommerceContext.commerceChannelId;
const clearInputButton = document.querySelector('.clear-input-button');
const listSectionContainers = document.querySelectorAll(
	'.list-section-container'
);
const menu = document.querySelector('.marketplace-nav-menu-container');
const navContainer = document.querySelector('.search-nav-container-full');
const overlay = document.querySelector('.results-overlay');
const recentSearchesContainer = document.querySelector(
	'.recent-searches-list-container'
);
const results = document.querySelector('.results');
const search = document.querySelector('.search');
const searchContainer = document.querySelector('.marketplace-search-container');
const searchDropdown = document.querySelector(
	'.search-dropdown-menu-container'
);
const searchDropdownTrigger = document.querySelector(
	'.search-dropdown-trigger'
);
const searchIcon = document.querySelector('.search-icon');
const searchInput = document.querySelector('.search-input');
const searchResultsContainer = document.querySelector(
	'.search-results-container'
);
const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`;

const searchToggle = {
	hide() {
		state.isSearchExpanded = false;

		overlay.classList.remove('active');
		results.classList.remove('expanded');
		search.classList.remove('expanded');
		searchContainer.classList.remove('expanded');
		searchIcon.classList.remove('expanded');
		scrollControl.unlock();

		setTimeout(() => {
			menu.classList.remove('hidden');
			results.style.display = 'block';
			searchDropdown.classList.remove('expanded');
		}, 300);
	},

	show() {
		state.isSearchExpanded = true;

		menu.classList.add('hidden');
		results.style.display = 'block';
		searchDropdown.style.display = 'block';
		scrollControl.lock();
		renderRecentSearches();

		setTimeout(() => {
			searchIcon.classList.add('expanded');
			searchContainer.classList.add('expanded');
			search.classList.add('expanded');

			setTimeout(() => {
				results.classList.add('expanded');
				overlay.classList.add('active');
				searchInput.focus();
			}, 100);
		}, 300);
	},
};

const searchStorage = {
	clear() {
		localStorage.removeItem(SEARCH_STORAGE_KEY);
	},

	delete(term) {
		localStorage.setItem(
			SEARCH_STORAGE_KEY,
			JSON.stringify(this.get().filter((item) => item !== term))
		);
	},

	get() {
		return JSON.parse(localStorage.getItem(SEARCH_STORAGE_KEY)) || [];
	},

	save(term) {
		term = term.trim();

		if (!term) {
			return;
		}

		const searchItems =
			JSON.parse(localStorage.getItem(SEARCH_STORAGE_KEY)) || [];

		const searchItemsLimited = [
			term,
			...searchItems.filter(
				(item) => item.toLowerCase() !== term.toLowerCase()
			),
		].slice(0, 5);

		localStorage.setItem(
			SEARCH_STORAGE_KEY,
			JSON.stringify(searchItemsLimited)
		);
	},
};

const scrollControl = {
	lock() {
		const scrollBarWidth =
			window.innerWidth - document.documentElement.clientWidth;
		document.body.style.paddingRight = `${scrollBarWidth}px`;
		document.body.style.overflow = 'hidden';
	},

	unlock() {
		document.body.style.paddingRight = '';
		document.body.style.overflow = '';
	},
};

const state = {
	categorySelected: '',
	enterSelection: null,
	isDropdownExpanded: false,
	isResultsExpanded: false,
	isSearchExpanded: false,
	resultsItemsList: '',
	searchTimeout: null,
	selectedIndex: -1,
};

async function fetchSearchResults(category, query) {
	const trimmed = query.trim();

	if (!trimmed) {
		searchResultsContainer.innerHTML = '';
		searchResultsContainer.style.display = 'none';

		return;
	}

	const {items = []} = await getProducts(category, trimmed);

	if (!items.length) {
		return renderNoResults(trimmed);
	}

	searchStorage.save(trimmed);

	const resultsList = document.createElement('ul');

	resultsList.className = 'recent-searches-list w-100';

	const searchRegex = new RegExp(
		`(${trimmed.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`,
		'gi'
	);

	for (const product of items) {
		const itemHTML = `
            <li class="results-items-list w-100">
                <a class="align-items-center border-radius-medium d-flex flex-row mb-0 text-dark text-decoration-none w-100" href="/web/marketplace/p/${product.slug}?keyword=${trimmed}">
                    <div class="image-container mr-2 rounded">
                        <img alt="${product.name}" class="app-search-bar-image" draggable="false" height="56" src="${product.urlImage?.replace('https://', 'http://') || ''}" width="56" />
                    </div>

                    <div class="app-name font-weight-bold w-100">${product.name.replace(searchRegex, '<mark>$1</mark>')}</div>
                </a>
            </li>
        `;

		resultsList.insertAdjacentHTML('beforeend', itemHTML);
	}

	listSectionContainers.forEach((sectionContainer) =>
		sectionContainer.classList.add('hidden')
	);
	searchResultsContainer.innerHTML = resultsList.outerHTML;
	searchResultsContainer.style.display = 'block';
}

const getFirstParam = (...keys) => {
	const params = new URLSearchParams(window.location.search);

	for (const key of keys) {
		const value = params.get(key);

		if (value) {
			return {key, value: decodeURIComponent(value)};
		}
	}

	return null;
};

async function getProducts(category, query) {
	const params = new URLSearchParams({
		accountId: '-1',
		pageSize: 12,
	});

	if (category && category !== 'All Categories') {
		params.set('filter', `categoryNames/any(x:(x eq '${category}'))`);
	}

	if (query) {
		params.set('search', encodeURIComponent(query));
	}

	const response = await Liferay.Util.fetch(
		`/o/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products?${params}`
	);

	return response.json();
}

function getTypeParam(param) {
	const params = new URLSearchParams(window.location.search);
	const URLParam = params.get(param);

	return URLParam ? decodeURIComponent(URLParam) : null;
}

async function main() {
	const categoryParam = getFirstParam('category');
	const queryParamData = getFirstParam('q', 'n', 'keyword');
	const query = queryParamData?.value || '';

	renderRecentSearches();

	selectCategory(categoryParam?.value || 'All Categories');

	searchInput.value = query;

	if (query && queryParamData.key !== 'keyword') {
		const data = await getProducts(state.categorySelected, query);
		showFeedbackAlert(
			data.items.length
				? `<strong class="mx-1">${data.totalCount}</strong> results for <strong class="mx-1">${query}</strong>`
				: `No results for <strong class="mx-1">${query}</strong>. Feel free to browse the catalog.`
		);
	}

	categoriesListItems.forEach((item) => {
		const typeParam = getTypeParam('type');

		if (
			item.dataset.category === 'All Categories' &&
			!getTypeParam('type')
		) {
			item.classList.add('selected');
		}

		if (typeParam && item.dataset.category === typeParam) {
			item.classList.add('selected');
		}

		item.addEventListener('click', () => {
			const category = item.dataset.category;
			const typeParam = getTypeParam('type');

			categoriesListItems.forEach((item) => {
				if (typeParam && item.dataset.category === typeParam) {
					item.classList.add('selected');
				}
			});

			selectCategory(category);

			state.isDropdownExpanded = false;
			searchDropdown.classList.remove('expanded');
		});
	});

	search.addEventListener('click', () => {
		if (!state.isSearchExpanded) {
			state.isSearchExpanded = true;
			searchToggle.show();
		}
	});

	navContainer.addEventListener('click', (event) => {
		if (
			!search.contains(event.target) &&
			!searchDropdown.contains(event.target)
		) {
			listSectionContainers.forEach((sectionContainer) =>
				sectionContainer.classList.remove('hidden')
			);

			searchResultsContainer.innerHTML = '';
			searchResultsContainer.style.display = 'none';
			searchInput.value = '';
			searchToggle.hide();
		}
	});

	clearInputButton.addEventListener('click', () => {
		clearInputButton.classList.remove('visible');
		searchInput.value = '';
		searchInput.focus();
	});

	overlay.addEventListener('click', (event) => {
		event.stopPropagation();
		searchToggle.hide();
	});

	searchDropdownTrigger.addEventListener('click', (event) => {
		event.stopPropagation();
		state.isResultsExpanded = false;
		state.isDropdownExpanded = true;
		results.classList.remove('expanded');
		searchDropdown.classList.add('expanded');
	});

	searchInput.addEventListener('focus', () => {
		if (!search.classList.contains('expanded')) {
			state.isSearchExpanded = true;
			searchToggle.show();
		}
	});

	searchInput.addEventListener('input', () => {
		clearTimeout(state.searchTimeout);

		const value = searchInput.value.trim();

		clearInputButton.classList.toggle('visible', !!value);

		state.searchTimeout = setTimeout(() => {
			fetchSearchResults(state.categorySelected, value);

			if (!searchInput.value.trim().length) {
				listSectionContainers.forEach((container) =>
					container.classList.remove('hidden')
				);
			}
		}, SEARCH_DELAY);
	});

	search.addEventListener('keydown', async (event) => {
		const key = event.key;

		if (!search.classList.contains('expanded')) {
			return;
		}
		state.resultsItemsList = document.querySelectorAll(
			'.results-items-list'
		);
		const items = state.resultsItemsList;

		if (!items || !items.length) {
			return;
		}

		const clearSelection = () => {
			items.forEach((item) => item.classList.remove('selected'));
		};

		if (key === 'ArrowDown') {
			event.preventDefault();

			clearSelection();

			state.selectedIndex = (state.selectedIndex + 1) % items.length;
			items[state.selectedIndex].classList.add('selected');
			items[state.selectedIndex].scrollIntoView({block: 'nearest'});
			state.enterSelection = items[state.selectedIndex];
		}

		if (key === 'ArrowUp') {
			event.preventDefault();

			clearSelection();

			state.selectedIndex =
				(state.selectedIndex - 1 + items.length) % items.length;
			items[state.selectedIndex].classList.add('selected');
			items[state.selectedIndex].scrollIntoView({block: 'nearest'});
			state.enterSelection = items[state.selectedIndex];
		}

		if (key === 'Enter') {
			const listItems = Array.from(state.resultsItemsList);

			if (!listItems.length) {
				return;
			}

			const selectedItem = state.enterSelection;

			if (selectedItem && selectedItem.classList.contains('selected')) {
				const clickable =
					selectedItem.querySelector('[onclick]') ||
					selectedItem.querySelector(
						"button, a, div[role='button']"
					) ||
					selectedItem.firstElementChild;

				if (clickable) {
					clickable.click();
				}
				else if (typeof selectedItem.onclick === 'function') {
					selectedItem.onclick();
				}
				else {
					selectedItem.click();
				}

				return;
			}

			if (searchInput.value.trim()) {
				searchStorage.save(searchInput.value.trim());

				const data = await getProducts(
					state.categorySelected !== 'All Categories'
						? state.categorySelected
						: '',
					searchInput.value.trim()
				);

				state.enterSelection = null;

				const queryParam = data.items.length ? 'q' : 'n';

				window.location.href = `/web/marketplace/applications?${queryParam}=${searchInput.value.trim()}${
					state.categorySelected !== 'All Categories'
						? `&category=${encodeURIComponent(
								state.categorySelected
							)}`
						: ''
				}`;
			}
		}
	});

	document.addEventListener('keydown', async (event) => {
		const key = event.key;

		if ((event.ctrlKey || event.metaKey) && key.toLowerCase() === 'k') {
			event.preventDefault();
			searchToggle.show();
		}

		if (key === 'Escape') {
			if (state.isDropdownExpanded) {
				state.isDropdownExpanded = false;
				searchDropdown.classList.remove('expanded');
				results.classList.add('expanded');
			}
			else if (state.isResultsExpanded) {
				state.isResultsExpanded = false;
				results.classList.remove('expanded');
			}
			else if (state.isSearchExpanded) {
				searchToggle.hide();
			}
		}
	});
}

async function onclickNavigateTo(term) {
	const data = await getProducts(
		state.categorySelected !== 'All Categories'
			? state.categorySelected
			: '',
		term.trim()
	);

	state.enterSelection = null;
	const queryParam = data.items.length ? 'q' : 'n';
	window.location.href = `/web/marketplace/applications?${queryParam}=${term}`;
}

const removeAllQueryParams = (url) => {
	const _url = new URL(url, window.location.origin);

	_url.search = '';

	return _url.toString();
};

function renderNoResults(query) {
	listSectionContainers.forEach((sectionElement) =>
		sectionElement.classList.add('hidden')
	);

	searchResultsContainer.innerHTML = `
        <ul class="recent-searches-list w-100">
            <li class="py-3 results-items-list w-100">
            	<div class="align-items-center d-flex search-no-results-container">
                	<svg class="lexicon-icon lexicon-icon-warning mr-2" width="16" height="16">
                		<use href="${spritemap}#warning"></use>
                	</svg>

                	<span>Oops! No results for <strong>"${query}"</strong></span>
           		</div>
            </li>
        </ul>
    `;

	searchResultsContainer.style.display = 'block';
}

function renderRecentSearches() {
	const searches = searchStorage.get();
	recentSearchesContainer.innerHTML = '';

	if (!searches.length) {
		recentSearchesContainer.style.display = 'none';

		return;
	}

	recentSearchesContainer.style.display = 'block';

	const titleHTML = `
        <div class="align-items-center d-flex justify-content-between results-title-container w-100">
            <h4 class="m-0 text-black-50 text-nowrap">Recent Searches</h4>
            <div class="divider-horizontal flex-grow-1 mx-3"></div>
            <button class="btn font-weight-bold p-0 section-action-button text-nowrap">Clear All</button>
        </div>
    `;

	recentSearchesContainer.insertAdjacentHTML('beforeend', titleHTML);

	recentSearchesContainer
		.querySelector('button')
		.addEventListener('click', () => {
			searchStorage.clear();
			renderRecentSearches();
		});

	const list = document.createElement('ul');

	list.className = 'results-list-container w-100';

	searches.slice(0, 3).forEach((term) => {
		const li = document.createElement('li');

		li.className = 'results-items-list w-100';

		li.innerHTML = `
            <a class="align-items-center d-flex text-dark text-decoration-none w-100">
                <svg class="lexicon-icon lexicon-icon-restore mr-2" width="16" height="16">
                    <use href="${spritemap}#restore"></use>
                </svg>
                <span class="font-weight-bold w-100">${term}</span>
            </a>

            <button class="bg-transparent border-0 btn btn-sm text-muted">
                <svg class="lexicon-icon lexicon-icon-times" width="14" height="14">
                    <use href="${spritemap}#times"></use>
                </svg>
            </button>
        `;

		li.querySelector('a').addEventListener('click', () =>
			onclickNavigateTo(term)
		);

		li.querySelector('button').addEventListener('click', (event) => {
			event.stopPropagation();
			searchStorage.delete(term);
			renderRecentSearches();
		});

		list.appendChild(li);
	});

	recentSearchesContainer.appendChild(list);
}

function selectCategory(category) {
	const currentUrl = window.location.href;
	const url = new URL(currentUrl);

	categoriesListItems.forEach((item) => {
		item.classList.remove('selected');
	});

	categoriesListItems.forEach((item) => {
		if (item.dataset.category === category) {
			item.classList.add('selected');
		}
	});

	if (category === 'All Categories') {
		url.searchParams.delete('type');
		categoriesTrigger.textContent = 'All Categories';
		window.history.replaceState({}, '', url.toString());
		state.categorySelected = category;

		return;
	}

	if (category && category !== 'All Categories') {
		categoriesTrigger.textContent = category;
		url.searchParams.set('type', category);
	}
	else {
		url.searchParams.delete('type');
	}

	if (search.classList.contains('expanded')) {
		searchInput.focus();

		results.classList.add('expanded');
		searchDropdown.classList.remove('expanded');
		state.isDropdownExpanded = false;
		state.isResultsExpanded = true;
	}

	window.history.replaceState({}, '', url.toString());
	state.categorySelected = category;
}

function showFeedbackAlert(text) {
	const panel = document.createElement('div');
	panel.className =
		'search-info-panel expanded d-flex align-items-center justify-content-between';

	panel.innerHTML = `
        <div class="container-fluid container-fluid-max-xl d-flex justify-content-between">
            <div class="align-items-center d-flex">${text}</div>
            <button class="btn btn-sm border-0 bg-transparent text-muted" style="cursor:pointer">
                <svg class="lexicon-icon lexicon-icon-times" width="14" height="14">
                    <use href="${spritemap}#times"></use>
                </svg>
            </button>
        </div>
    `;

	panel.querySelector('button').addEventListener('click', (event) => {
		event.stopPropagation();
		panel.classList.remove('expanded');
		navContainer.classList.remove('expanded');
		searchInput.value = '';
		window.history.replaceState(
			{},
			'',
			removeAllQueryParams(window.location.href)
		);
	});

	navContainer.appendChild(panel);
	navContainer.classList.add('expanded');
}

main();
