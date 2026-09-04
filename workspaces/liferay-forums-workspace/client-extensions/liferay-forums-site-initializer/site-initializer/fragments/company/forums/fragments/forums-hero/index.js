/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* The New Discussion button is revealed only for users who may create a
   thread. Mirrors the forums-message-list ask button: the forumthreads
   collection actions must be read client-side, as the viewing user. */
const askBtn = fragmentElement.querySelector('#forumsHeroAskBtn');

if (askBtn) {
	Liferay.Util.fetch(
		Liferay.ThemeDisplay.getPortalURL() +
			'/o/c/forumthreads/scopes/' +
			Liferay.ThemeDisplay.getScopeGroupId() +
			'?page=1&pageSize=1',
		{
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'GET',
		}
	)
		.then((r) => {
			return r.json();
		})
		.then((data) => {
			if (
				data &&
				data.actions &&
				(data.actions['post'] || data.actions['create'])
			) {
				askBtn.style.display = '';
			}
		})
		.catch(() => {});
}

const heroEl = fragmentElement.querySelector('#forumsHero');
const searchInput = fragmentElement.querySelector('#forumsHeroSearchInput');
const suggestionsEl = fragmentElement.querySelector(
	'#forumsHeroSearchSuggestions'
);

if (heroEl && searchInput && suggestionsEl) {
	const sitePrefix = heroEl.dataset.sitePrefix || '';
	const labelSearching = heroEl.dataset.labelSearching || 'Searching...';
	const labelNoResultsFound =
		heroEl.dataset.labelNoResultsFound || 'No results found';
	const searchIconUrl =
		Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg#search';

	const MIN_QUERY_LENGTH = 2;
	const DEBOUNCE_MS = 300;
	const OPTION_SELECTOR = '.forums-hero__search-suggestion';

	let debounceTimer = null;
	let requestSeq = 0;
	let activeIndex = -1;

	const hideSuggestions = function () {
		suggestionsEl.style.display = 'none';
		suggestionsEl.innerHTML = '';
		activeIndex = -1;
		searchInput.setAttribute('aria-expanded', 'false');
		searchInput.removeAttribute('aria-activedescendant');
	};

	const setActiveIndex = function (index) {
		const options = suggestionsEl.querySelectorAll(OPTION_SELECTOR);

		if (!options.length) {
			activeIndex = -1;

			return;
		}

		activeIndex =
			((index % options.length) + options.length) % options.length;

		options.forEach((option, i) => {
			const active = i === activeIndex;

			option.classList.toggle(
				'forums-hero__search-suggestion--active',
				active
			);

			if (active) {
				searchInput.setAttribute('aria-activedescendant', option.id);
				option.scrollIntoView({block: 'nearest'});
			}
		});
	};

	/* Renders text as a DOM fragment with the matched substring wrapped in
	   <mark>, built from text nodes rather than innerHTML so forum content
	   can never be interpreted as markup. */
	const highlightMatch = function (text, query) {
		const fragment = document.createDocumentFragment();
		const matchIndex = query
			? text.toLowerCase().indexOf(query.toLowerCase())
			: -1;

		if (matchIndex === -1) {
			fragment.appendChild(document.createTextNode(text));

			return fragment;
		}

		const matchEnd = matchIndex + query.length;

		fragment.appendChild(
			document.createTextNode(text.substring(0, matchIndex))
		);

		const mark = document.createElement('mark');
		mark.textContent = text.substring(matchIndex, matchEnd);
		fragment.appendChild(mark);

		fragment.appendChild(document.createTextNode(text.substring(matchEnd)));

		return fragment;
	};

	const renderStatus = function (text) {
		suggestionsEl.innerHTML = '';
		activeIndex = -1;

		const statusEl = document.createElement('div');
		statusEl.className = 'forums-hero__search-suggestions-message';
		statusEl.textContent = text;
		suggestionsEl.appendChild(statusEl);

		suggestionsEl.style.display = '';
		searchInput.setAttribute('aria-expanded', 'true');
	};

	const renderSuggestions = function (messages, query) {
		suggestionsEl.innerHTML = '';
		activeIndex = -1;

		messages.forEach((message, index) => {
			const {friendlyUrlPath} = message;

			if (!friendlyUrlPath) {
				return;
			}

			const link = document.createElement('a');
			link.className = 'forums-hero__search-suggestion';
			link.id = 'forumsHeroSearchSuggestion' + index;
			link.setAttribute('role', 'option');
			link.href = sitePrefix + '/c_forumthread/' + friendlyUrlPath;

			const icon = document.createElementNS(
				'http://www.w3.org/2000/svg',
				'svg'
			);
			icon.setAttribute(
				'class',
				'lexicon-icon forums-hero__search-suggestion-icon'
			);
			icon.setAttribute('focusable', 'false');
			icon.setAttribute('role', 'presentation');

			const use = document.createElementNS(
				'http://www.w3.org/2000/svg',
				'use'
			);
			use.setAttribute('href', searchIconUrl);
			icon.appendChild(use);
			link.appendChild(icon);

			const textEl = document.createElement('span');
			textEl.className = 'forums-hero__search-suggestion-text';
			textEl.appendChild(highlightMatch(message.subject || '', query));
			link.appendChild(textEl);

			link.addEventListener('mouseenter', () => {
				setActiveIndex(index);
			});

			suggestionsEl.appendChild(link);
		});

		if (!suggestionsEl.children.length) {
			renderStatus(labelNoResultsFound);

			return;
		}

		suggestionsEl.style.display = '';
		searchInput.setAttribute('aria-expanded', 'true');
	};

	const fetchSuggestions = function (query) {
		const seq = ++requestSeq;
		const portalURL = Liferay.ThemeDisplay.getPortalURL();
		const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
		const headers = {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		};

		renderStatus(labelSearching);

		Liferay.Util.fetch(
			portalURL +
				'/o/c/forummessages/scopes/' +
				scopeGroupId +
				'/approved?fields=' +
				encodeURIComponent('subject,friendlyUrlPath') +
				'&filter=' +
				encodeURIComponent("not startswith(subject, 'RE')") +
				'&pageSize=5' +
				'&search=' +
				encodeURIComponent(query),
			{headers, method: 'GET'}
		)
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				if (seq !== requestSeq) {
					return;
				}

				renderSuggestions((data && data.items) || [], query);
			})
			.catch(() => {
				if (seq !== requestSeq) {
					return;
				}

				renderSuggestions([], query);
			});
	};

	searchInput.addEventListener('input', () => {
		const query = searchInput.value.trim();

		clearTimeout(debounceTimer);

		if (query.length < MIN_QUERY_LENGTH) {

			/* Invalidate any in-flight request so its response cannot land
			   after the box has been cleared. */
			requestSeq++;
			hideSuggestions();

			return;
		}

		debounceTimer = setTimeout(() => {
			fetchSuggestions(query);
		}, DEBOUNCE_MS);
	});

	/* Arrow keys move the highlighted suggestion; Enter on a highlighted
	   suggestion navigates to it exactly as a click would, and Enter with
	   nothing highlighted falls through to the form's own submit (the
	   existing keyword search page). */
	searchInput.addEventListener('keydown', (event) => {
		const options = suggestionsEl.querySelectorAll(OPTION_SELECTOR);

		if (event.key === 'Escape') {
			hideSuggestions();

			return;
		}

		if (!options.length) {
			return;
		}

		if (event.key === 'ArrowDown') {
			event.preventDefault();
			setActiveIndex(activeIndex + 1);
		}
		else if (event.key === 'ArrowUp') {
			event.preventDefault();
			setActiveIndex(activeIndex - 1);
		}
		else if (event.key === 'Enter' && activeIndex !== -1) {
			event.preventDefault();
			window.location.href = options[activeIndex].href;
		}
	});

	document.addEventListener('click', (event) => {
		if (
			!searchInput.contains(event.target) &&
			!suggestionsEl.contains(event.target)
		) {
			hideSuggestions();
		}
	});
}
