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

const featuredSectionContainer = document.querySelector('.featured-section');
let listSectionContainers = document.querySelectorAll(
	'.list-section-container'
);

function getClayIconSVG(icon, options = {height: 16, width: 16}) {
	return `<svg class="lexicon-icon lexicon-icon-${icon} mr-2" height="${options.height}" width="${options.width}">
				<use href="${spritemap}#${icon}"></use>
			</svg>`;
}

function getDecodedJSONParse(value, defaultValue = []) {
	return value ? JSON.parse(decodeURIComponent(value)) : defaultValue;
}

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

	async show() {
		state.isSearchExpanded = true;

		menu.classList.add('hidden');
		results.style.display = 'block';
		searchDropdown.style.display = 'block';

		scrollControl.lock();

		renderRecentSearches();

		await renderFeaturedSection(JSON.parse(configuration.featuredSection1));
		await renderFeaturedSection(JSON.parse(configuration.featuredSection2));

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
	clear(storageKey) {
		document.cookie = `${storageKey}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/`;
	},

	get(storageKey) {
		return document.cookie
			.split('; ')
			.find((row) => row.startsWith(storageKey + '='))
			?.split('=')[1];
	},

	save(name, value, days = 7) {
		const expires = new Date(Date.now() + days * 864e5).toUTCString();

		document.cookie = `${name}=${encodeURIComponent(
			value
		)}; expires=${expires}; path=/`;
	},
};

function setSearchInput(value) {
	const items = getDecodedJSONParse(searchStorage.get(SEARCH_STORAGE_KEY));

	const searchItemsLimited = [
		value,
		...items.filter((item) => item.toLowerCase() !== value.toLowerCase()),
	].slice(0, 5);

	searchStorage.save(
		SEARCH_STORAGE_KEY,
		JSON.stringify(searchItemsLimited),
		30
	);
}

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

async function fetchSearchResults(category, searchValue) {
	searchValue = searchValue.trim();

	if (!searchValue) {
		searchResultsContainer.innerHTML = '';
		searchResultsContainer.style.display = 'none';

		return;
	}

	const {items = []} = await getProducts(category, searchValue);

	if (!items.length) {
		return renderNoResults(searchValue);
	}

	setSearchInput(searchValue);

	const resultsList = document.createElement('ul');

	resultsList.className = 'recent-searches-list w-100';

	const searchRegex = new RegExp(
		`(${searchValue.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`,
		'gi'
	);

	for (const product of items) {
		const itemHTML = `
				<li class="results-items-list w-100">
					<a class="align-items-center border-radius-medium d-flex flex-row mb-0 text-dark text-decoration-none w-100" href="/web/marketplace/p/${product.slug}?keyword=${searchValue}">
						<div class="image-container mr-2 rounded">
							<img alt="${product.name}" class="app-search-bar-image" draggable="false" height="56" src="${product.urlImage?.replace('https://', 'http://') || ''}" width="56" />
						</div>

						<div class="app-name font-weight-bold w-100">${product.name.replace(searchRegex, '<mark>$1</mark>')}</div>
					</a>
				</li>
			`;

		resultsList.insertAdjacentHTML('beforeend', itemHTML);
	}

	listSectionContainers = document.querySelectorAll(
		'.list-section-container'
	);

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
		filter: `categoryNames/any(x:(x eq 'App'))`,
		pageSize: 12,
	});

	let currentFilter = params.get('filter');

	if (category && category !== 'All Categories') {
		const categoryFilter = `categoryNames/any(x:(x eq '${category}'))`;

		currentFilter = `${currentFilter} and ${categoryFilter}`;

		params.set('filter', currentFilter);
	}

	if (query) {
		params.set('search', query);
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
	renderRecentSearches();

	window.addEventListener('popstate', () => {
		syncContextParams();
	});

	syncContextParams();

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

	search.addEventListener('click', (event) => {
		event.stopPropagation();

		if (!state.isSearchExpanded) {
			state.isSearchExpanded = true;
			searchToggle.show();
		}

		if (state.isSearchExpanded && state.isDropdownExpanded) {
			state.isDropdownExpanded = false;
			searchDropdown.classList.remove('expanded');
			state.isSearchExpanded = true;
			searchToggle.show();
		}
	});

	navContainer.addEventListener('click', (event) => {
		if (
			!results.contains(event.target) &&
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

		const searchValue = searchInput.value.trim();

		clearInputButton.classList.toggle('visible', !!searchValue);

		state.searchTimeout = setTimeout(() => {
			fetchSearchResults(state.categorySelected, searchValue);

			if (!searchValue.length) {
				listSectionContainers.forEach((container) =>
					container.classList.remove('hidden')
				);
			}
		}, SEARCH_DELAY);
	});

	search.addEventListener('keydown', async (event) => {
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

		const key = event.key;

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
					searchToggle.hide();
					clickable.click();
				}
				else if (typeof selectedItem.onclick === 'function') {
					searchToggle.hide();
					selectedItem.onclick();
				}
				else {
					searchToggle.hide();
					selectedItem.click();
				}

				return;
			}

			const searchValue = searchInput.value.trim();

			if (!searchValue) {
				return;
			}

			setSearchInput(searchValue);

			const data = await getProducts(
				state.categorySelected !== 'All Categories'
					? state.categorySelected
					: '',
				searchValue
			);

			state.enterSelection = null;

			const queryParam = data.items.length ? 'q' : 'n';

			searchToggle.hide();

			window.location.href = `/web/marketplace/applications?${queryParam}=${searchValue}${
				state.categorySelected !== 'All Categories'
					? `&category=${encodeURIComponent(state.categorySelected)}`
					: ''
			}`;
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

	searchToggle.hide();

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
	listSectionContainers = document.querySelectorAll(
		'.list-section-container'
	);

	listSectionContainers.forEach((sectionElement) =>
		sectionElement.classList.add('hidden')
	);

	searchResultsContainer.innerHTML = `
		<ul class="recent-searches-list w-100">
			<li class="py-3 results-items-list w-100">
				<div class="align-items-center d-flex search-no-results-container">
					${getClayIconSVG('warning')}

					<span>Oops! No results for <strong>"${query}"</strong></span>
				</div>
			</li>
		</ul>
	`;

	searchResultsContainer.style.display = 'block';
}

function renderRecentSearches() {
	const searchItems = getDecodedJSONParse(
		searchStorage.get(SEARCH_STORAGE_KEY)
	);

	recentSearchesContainer.innerHTML = '';

	if (!searchItems.length) {
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
			searchStorage.clear(SEARCH_STORAGE_KEY);

			renderRecentSearches();
		});

	const list = document.createElement('ul');

	list.className = 'results-list-container w-100';

	searchItems.slice(0, 3).forEach((searchItem) => {
		const li = document.createElement('li');

		li.className = 'results-items-list w-100';

		li.innerHTML = `
			<a class="align-items-center d-flex text-dark text-decoration-none w-100">
				${getClayIconSVG('restore')}

				<span class="font-weight-bold w-100">${searchItem}</span>
			</a>

			<button class="bg-transparent border-0 btn btn-sm text-muted">
				${getClayIconSVG('times', {height: 14, width: 14})}
			</button>
		`;

		li.querySelector('a').addEventListener('click', () =>
			onclickNavigateTo(searchItem)
		);

		li.querySelector('button').addEventListener('click', (event) => {
			event.stopPropagation();

			const items = getDecodedJSONParse(
				searchStorage.get(SEARCH_STORAGE_KEY)
			).filter((item) => item !== searchItem);

			searchStorage.save(SEARCH_STORAGE_KEY, JSON.stringify(items));

			renderRecentSearches();
		});

		list.appendChild(li);
	});

	recentSearchesContainer.appendChild(list);
}

async function syncContextParams() {
	const categoryParam = getFirstParam('category', 'type');
	const keywordParam = getFirstParam('q', 'n', 'keyword');
	const keywordValue = keywordParam?.value || '';

	selectCategory(
		categoryParam?.value ? categoryParam.value : 'All Categories',
		false
	);

	searchInput.value = keywordValue;

	if (keywordValue && keywordParam.key !== 'keyword') {
		try {
			const data = await getProducts(
				state.categorySelected,
				keywordValue
			);

			showFeedbackAlert(
				data.items.length
					? `<strong class="mx-1">${data.totalCount}</strong> results for <strong class="mx-1">${keywordValue}</strong>`
					: `No results for <strong class="mx-1">${keywordValue}</strong>. Feel free to browse the catalog.`
			);
		}
		catch (error) {
			console.error('Error fetching products', error);
		}
	}
}

function selectCategory(category, updateHistory = true) {
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
		categoriesTrigger.textContent = 'All Categories';
		url.searchParams.delete('category');
		url.searchParams.delete('type');

		if (updateHistory) {
			window.history.pushState({}, '', url);
		}

		state.categorySelected = category;

		return;
	}

	if (category && category !== 'All Categories') {
		categoriesTrigger.textContent = category;
		url.searchParams.set('category', category);
		url.searchParams.set('type', category);
	}
	else {
		url.searchParams.delete('category');
		url.searchParams.delete('type');
	}

	if (search.classList.contains('expanded')) {
		searchInput.focus();

		results.classList.add('expanded');
		searchDropdown.classList.remove('expanded');
		state.isDropdownExpanded = false;
		state.isResultsExpanded = true;
	}

	if (updateHistory) {
		window.history.pushState({}, '', url);
	}

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
				${getClayIconSVG('times', {height: 14, width: 14})}
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

async function getCatalogProducts(options = {}) {
	const searchParams = new URLSearchParams({
		accountId: options.accountId || '-1',
		pageSize: options.pageSize || 12,
	});

	if (options.filter) {
		searchParams.set('filter', options.filter);
	}

	if (options.search) {
		searchParams.set('search', encodeURIComponent(options.search));
	}

	const response = await Liferay.Util.fetch(
		`/o/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products?${searchParams}`
	);

	return response.json();
}

async function renderFeaturedSection({
	pageSize = 3,
	specificationValue = '',
	title = 'Section',
} = {}) {
	const featuredSectionKey = `@marketplace/featured/${specificationValue.toLowerCase()}`;

	const normalizedClass = specificationValue
		.toLowerCase()
		.replace(/\s+/g, '-');

	if (featuredSectionContainer.querySelector(`.${normalizedClass}`)) {
		return;
	}

	const featuredSectionCache = searchStorage.get(featuredSectionKey);

	let featuredSectionProducts = featuredSectionCache
		? getDecodedJSONParse(featuredSectionCache)
		: [];

	if (!featuredSectionProducts.length) {
		const response = await getCatalogProducts({
			accountId: '-1',
			filter: `specificationValues/any(x:(x eq '${specificationValue}'))`,
			pageSize,
		});

		if (!response?.items?.length) {
			return;
		}

		searchStorage.save(
			featuredSectionKey,
			JSON.stringify(response?.items),
			14
		);

		featuredSectionProducts = response?.items;
	}

	const featuredSectionHTML = document.createElement('div');

	featuredSectionHTML.classList.add(
		'align-items-start',
		'd-flex',
		'flex-column',
		'justify-content-start',
		'list-section-container',
		'w-100',
		`${normalizedClass}`
	);

	const featuredTitleContainer = document.createElement('div');

	featuredTitleContainer.classList.add(
		'align-items-center',
		'd-flex',
		'justify-content-between',
		'results-title-container',
		'w-100'
	);

	const featuredTitle = document.createElement('h4');

	featuredTitle.classList.add('m-0', 'text-black-50', 'text-nowrap');
	featuredTitle.textContent = title;

	const divider = document.createElement('div');

	divider.classList.add('divider-horizontal', 'mx-3');

	const viewAllLink = document.createElement('a');

	viewAllLink.classList.add(
		'font-weight-bold',
		'p-0',
		'section-action-button',
		'text-nowrap'
	);

	viewAllLink.href = `/web/marketplace/applications?our-selection=${specificationValue}`;

	const viewAllSpan = document.createElement('span');

	viewAllSpan.classList.add('text-black-50', 'text-nowrap');
	viewAllSpan.textContent = 'View All';

	viewAllLink.appendChild(viewAllSpan);

	featuredTitleContainer.appendChild(featuredTitle);
	featuredTitleContainer.appendChild(divider);
	featuredTitleContainer.appendChild(viewAllLink);

	const resultsListContainer = document.createElement('ul');
	resultsListContainer.classList.add('results-list-container', 'w-100');

	for (const product of featuredSectionProducts) {
		const listItem = document.createElement('li');

		listItem.classList.add('results-items-list', 'w-100');

		const productLink = document.createElement('a');

		productLink.classList.add(
			'align-items-center',
			'd-flex',
			'flex-row',
			'mb-0',
			'text-dark',
			'text-decoration-none',
			'w-100'
		);
		productLink.href = `/web/marketplace/p/${product.slug}`;
		productLink.target = '_self';

		const imageContainer = document.createElement('div');
		imageContainer.classList.add('image-container', 'mr-2', 'rounded');

		const productImage = document.createElement('img');
		productImage.alt = product.name;
		productImage.classList.add('app-search-bar-image');
		productImage.draggable = false;
		productImage.height = 56;
		productImage.width = 56;
		productImage.src =
			product.urlImage?.replace('https://', 'http://') || '';

		imageContainer.appendChild(productImage);

		const productNameDiv = document.createElement('div');
		productNameDiv.classList.add('font-weight-bold', 'w-100');
		productNameDiv.textContent = product.name;

		productLink.appendChild(imageContainer);
		productLink.appendChild(productNameDiv);

		listItem.appendChild(productLink);
		resultsListContainer.appendChild(listItem);
	}

	featuredSectionHTML.appendChild(featuredTitleContainer);
	featuredSectionHTML.appendChild(resultsListContainer);

	featuredSectionContainer.appendChild(featuredSectionHTML);
}

main();
