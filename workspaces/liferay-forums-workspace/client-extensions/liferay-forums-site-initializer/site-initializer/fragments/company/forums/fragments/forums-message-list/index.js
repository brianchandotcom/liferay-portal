/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const messageList = fragmentElement.querySelector('#forumsMessageList');

/* Category query string. pageSize and sort come from fragment configuration;
   a blank sort omits the parameter entirely, which is needed on databases
   that cannot sort on a Text object field (Hypersonic raises "data type cast
   needed for parameter or null literal"). */
function categoryQuery(dataset) {
	const size = dataset.categoryPageSize || '200';
	const sort = (dataset.categorySort || '').trim();

	return (
		'?pageSize=' +
		encodeURIComponent(size) +
		(sort ? '&sort=' + encodeURIComponent(sort) : '')
	);
}

if (messageList) {
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const pathFriendlyURLPublic =
		Liferay.ThemeDisplay.getPathFriendlyURLPublic();
	let sitePrefix = '';

	if (pathFriendlyURLPublic) {
		const pubPath = pathFriendlyURLPublic + '/';

		const {pathname} = window.location;
		const localeMatch = pathname.match(
			/^\/[a-zA-Z]{2}(?:-[a-zA-Z]{2})?(?=\/)/
		);
		const localePrefix = localeMatch ? localeMatch[0] : '';
		const pathAfterLocale = pathname.substring(localePrefix.length);

		if (pathAfterLocale.indexOf(pubPath) === 0) {
			const rest = pathAfterLocale.substring(pubPath.length);
			const slugEnd = rest.indexOf('/');
			const siteSlug = slugEnd === -1 ? rest : rest.substring(0, slugEnd);

			sitePrefix = pathFriendlyURLPublic + '/' + siteSlug;
		}
	}
	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
	};
	const clayIconsUrl =
		Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

	/* Point first breadcrumb crumb ("Forums") at the configured community home */
	const homeCrumb = messageList.querySelector(
		'#forumsMessageListBreadcrumbHome'
	);
	if (homeCrumb) {
		homeCrumb.href =
			sitePrefix +
			(typeof configuration !== 'undefined' && configuration.communityURL
				? configuration.communityURL
				: '/forums');
	}

	/* State */
	let currentSort = 'dateCreated:desc';
	let currentPage = 1;
	const pageSize = 20;
	let categoryId = null;
	let searchQuery = '';
	const currentUserId = Liferay.ThemeDisplay.getUserId();
	let isBanned = false;

	/* DOM refs */
	const cardsContainer = messageList.querySelector('#forumsMessageListCards');
	const loadingEl = messageList.querySelector('#forumsMessageListLoading');
	const skeletonHTML = loadingEl ? loadingEl.innerHTML : '';
	let skeletonStart = 0;
	const SKELETON_MIN_MS = 400;
	const paginationNav = messageList.querySelector(
		'#forumsMessageListPagination'
	);
	const paginationUl = messageList.querySelector(
		'#forumsMessageListPaginationUl'
	);
	const headingEl = messageList.querySelector('#forumsMessageListHeading');
	const breadcrumbName = messageList.querySelector(
		'#forumsMessageListCategoryName'
	);
	const searchInput = messageList.querySelector(
		'#forumsMessageListSearchInput'
	);
	const searchBtn = messageList.querySelector('#forumsMessageListSearchBtn');
	const tabLinks = messageList.querySelectorAll(
		'#forumsMessageListTabs .nav-link'
	);
	const askBtn = messageList.querySelector('#forumsMessageListAskBtn');
	const newDiscussionBaseUrl = askBtn ? askBtn.getAttribute('href') : '';
	const categoryFilter = messageList.querySelector(
		'#forumsMessageListCategoryFilter'
	);
	const showingEl = messageList.querySelector('#forumsMessageListShowing');
	const breadcrumbOl = messageList.querySelector(
		'#forumsMessageListBreadcrumb'
	);
	const subcatsContainer = messageList.querySelector(
		'#forumsMessageListSubcategories'
	);
	const subcatsRow = messageList.querySelector(
		'#forumsMessageListSubcategoriesRow'
	);
	const subcatsToggle = messageList.querySelector(
		'#forumsMessageListSubcategoriesToggle'
	);
	const subcatsToggleLabel = messageList.querySelector(
		'#forumsMessageListSubcategoriesToggleLabel'
	);
	const subcatsToggleIcon = messageList.querySelector(
		'#forumsMessageListSubcategoriesToggleIcon'
	);

	/* Collapsed subcategory box shows exactly two rows; expanded shows all */
	let subcatsExpanded = false;

	/* FK exposed by the ForumCategory self-relationship (0 / absent = top-level) */
	const PARENT_FK = 'r_categorySubcategories_c_forumCategoryId';

	/* Subcategories are capped at ONE level — see MAX_DEPTH in
	   forums-categories-admin. Kept as a constant, never a setting. */
	const MAX_DEPTH = 1;
	let categoryTree = null;

	/* Read URL params */
	const urlParams = new URLSearchParams(window.location.search);
	categoryId = urlParams.get('categoryId');
	searchQuery = urlParams.get('q') || '';
	if (searchInput && searchQuery) {
		searchInput.value = searchQuery;
	}

	/* Icons */
	const checkIcon =
		'<svg viewBox="0 0 16 16" fill="currentColor"><path d="M8 0C3.6 0 0 3.6 0 8s3.6 8 8 8 8-3.6 8-8-3.6-8-8-8zm3.7 6.3l-4 4c-.2.2-.4.3-.7.3s-.5-.1-.7-.3l-2-2c-.4-.4-.4-1 0-1.4s1-.4 1.4 0L7 8.2l3.3-3.3c.4-.4 1-.4 1.4 0s.4 1 0 1.4z"/></svg>';
	const replyIcon =
		'<svg viewBox="0 0 16 16" fill="currentColor"><path d="M14 1H2C.9 1 0 1.9 0 3v7c0 1.1.9 2 2 2h3l3 3 3-3h3c1.1 0 2-.9 2-2V3c0-1.1-.9-2-2-2zm0 9H2V3h12v7z"/></svg>';
	const clockIcon =
		'<svg viewBox="0 0 16 16" fill="currentColor"><path d="M8 0C3.6 0 0 3.6 0 8s3.6 8 8 8 8-3.6 8-8-3.6-8-8-8zm0 14c-3.3 0-6-2.7-6-6s2.7-6 6-6 6 2.7 6 6-2.7 6-6 6z"/><path d="M9 4H7v5l3.5 2.1.5-.9L9 8.5z"/></svg>';
	const eyeIcon =
		'<svg viewBox="0 0 16 16" fill="currentColor"><path d="M8 3C3.58 3 0 8 0 8s3.58 5 8 5 8-5 8-5-3.58-5-8-5zm0 8.5c-1.93 0-3.5-1.57-3.5-3.5S6.07 4.5 8 4.5 11.5 6.07 11.5 8 9.93 11.5 8 11.5z"/><circle cx="8" cy="8" r="2"/></svg>';

	/* Utility: relative time */
	const timeAgo = function (dateStr) {
		if (!dateStr) {
			return '';
		}
		const now = Date.now();
		const then = new Date(dateStr).getTime();
		const diff = Math.floor((now - then) / 1000);
		if (diff < 60) {
			return messageList.dataset.labelJustNow || 'just now';
		}
		if (diff < 3600) {
			return (
				messageList.dataset.labelXMinutesAgo || '{0} minutes ago.'
			).replace('{0}', Math.floor(diff / 60));
		}
		if (diff < 86400) {
			return (
				messageList.dataset.labelXHoursAgo || '{0} hours ago.'
			).replace('{0}', Math.floor(diff / 3600));
		}
		if (diff < 2592000) {
			return (
				messageList.dataset.labelXDaysAgo || '{0} days ago.'
			).replace('{0}', Math.floor(diff / 86400));
		}

		return new Date(dateStr).toLocaleDateString();
	};

	/* Full localized date + time (browser locale), e.g. "05/30/2026, 02:34:56 PM"
	   for en-US. Shown as the title tooltip and the screen-reader label on a
	   relative date. */
	const fullDateTime = function (dateStr) {
		if (!dateStr) {
			return '';
		}

		return new Date(dateStr).toLocaleString(undefined, {
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
			month: '2-digit',
			second: '2-digit',
			year: 'numeric',
		});
	};

	/* Utility: avatar initial */
	const avatarInitial = function (name) {
		if (!name) {
			return '?';
		}

		return name.charAt(0).toUpperCase();
	};

	/* Utility: stable avatar color from the Clay sticker-outline-0..9 palette */
	const avatarColorClass = function (creator) {
		const {id, name} = creator || {};
		const key = String(id || name || '');
		let n = 0;
		for (let i = 0; i < key.length; i++) {
			n = (n + key.charCodeAt(i)) % 10;
		}

		return 'sticker-outline-' + n;
	};

	const displayName = function (creator) {
		if (!creator) {
			return '';
		}
		const {familyName, givenName, name} = creator;
		const given = givenName || '';
		const family = familyName || '';

		return family && family !== 'User'
			? given + ' ' + family
			: given || name || '';
	};

	/* Thread priority badge (Message Boards parity: Urgent|bolt|3.0,
	   Sticky|pin|2.0, Announcement|comments|1.0). Values <= 0, missing, or
	   unknown render nothing, matching MBUtil.getThreadPriority. */
	const PRIORITY_LEVELS = {
		1: {
			fallback: 'Announcement',
			icon: 'comments',
			labelKey: 'labelAnnouncement',
			textClass: 'text-info',
		},
		2: {
			fallback: 'Sticky',
			icon: 'pin',
			labelKey: 'labelSticky',
			textClass: 'text-warning',
		},
		3: {
			fallback: 'Urgent',
			icon: 'bolt',
			labelKey: 'labelUrgent',
			textClass: 'text-danger',
		},
	};

	const priorityBadge = function (priority, dataset) {
		const level = PRIORITY_LEVELS[Math.round(parseFloat(priority)) || 0];
		if (!level) {
			return '';
		}
		const {fallback, icon, labelKey, textClass} = level;
		const label = dataset[labelKey] || fallback;

		return (
			'<span class="forums-message-card__solved ' +
			textClass +
			' ml-3 small">' +
			'<svg class="lexicon-icon lexicon-icon-' +
			icon +
			'" role="presentation"><use href="' +
			clayIconsUrl +
			'#' +
			icon +
			'"></use></svg> ' +
			Liferay.Util.escapeHTML(label) +
			'</span>'
		);
	};

	/* --- Category hierarchy -------------------------------------------- */

	const getParentId = function (cat) {
		return Number(cat[PARENT_FK]) || 0;
	};

	/* Link to another category on this same page */
	const categoryHref = function (id) {
		return window.location.pathname + '?categoryId=' + id;
	};

	/* Build {byId, childrenOf} from a flat list. Anything deeper than
	   MAX_DEPTH — only reachable by writing the FK directly through the REST
	   API — is normalized to top-level so it still renders somewhere. */
	const buildTree = function (items) {
		const byId = {};
		items.forEach((cat) => {
			byId[cat.id] = cat;
		});

		const depthOf = {};
		items.forEach((cat) => {
			let depth = 0;
			let pid = getParentId(cat);
			let guard = 0;
			while (pid && byId[pid] && guard < 50) {
				depth++;
				pid = getParentId(byId[pid]);
				guard++;
			}
			depthOf[cat.id] = depth;
		});

		const childrenOf = {};
		items.forEach((cat) => {
			let pid = getParentId(cat);
			if (!pid || !byId[pid] || depthOf[cat.id] > MAX_DEPTH) {
				pid = 0;
			}
			(childrenOf[pid] = childrenOf[pid] || []).push(cat);
		});

		return {byId, childrenOf, depthOf};
	};

	/* Rebuild the breadcrumb as Forums > [Parent >] Current.
	   With the one-level cap this is at most three crumbs. */
	const buildBreadcrumb = function (tree) {
		if (!breadcrumbName) {
			return;
		}

		const allLabel =
			messageList.dataset.labelAllCategories ||
			messageList.dataset.labelAllMessages ||
			'All Discussions';
		const activeLi = breadcrumbName.closest('li');

		/* Drop ancestor crumbs from a previous render */
		if (breadcrumbOl) {
			breadcrumbOl
				.querySelectorAll('.forums-breadcrumb-ancestor')
				.forEach((element) => {
					element.remove();
				});
		}

		const {byId} = tree;
		const current = categoryId ? byId[categoryId] : null;
		const name = current
			? current.categoryName ||
				messageList.dataset.labelCategory ||
				'Category'
			: allLabel;

		breadcrumbName.textContent = name;
		if (headingEl) {
			headingEl.textContent = name;
		}

		if (!current || !activeLi || !breadcrumbOl) {
			return;
		}

		/* Walk up the parent chain (cycle-guarded; at most one hop when capped) */
		const ancestors = [];
		let pid = getParentId(current);
		let guard = 0;
		while (pid && byId[pid] && guard < 50) {
			ancestors.unshift(byId[pid]);
			pid = getParentId(byId[pid]);
			guard++;
		}

		ancestors.forEach((anc) => {
			const li = document.createElement('li');
			li.className = 'breadcrumb-item forums-breadcrumb-ancestor';
			const a = document.createElement('a');
			a.className = 'breadcrumb-link';
			a.href = categoryHref(anc.id);
			a.textContent = anc.categoryName || '';
			li.appendChild(a);
			breadcrumbOl.insertBefore(li, activeLi);
		});
	};

	/* Fill the filter dropdown with an indented two-tier category tree */
	const populateCategoryFilter = function (tree) {
		if (!categoryFilter) {
			return;
		}
		const {childrenOf} = tree;
		(childrenOf[0] || []).forEach((cat) => {
			categoryFilter.appendChild(categoryOption(cat, 0));
			(childrenOf[cat.id] || []).forEach((child) => {
				categoryFilter.appendChild(categoryOption(child, 1));
			});
		});
	};

	const categoryOption = function ({categoryName, id}, depth) {
		const opt = document.createElement('option');
		opt.value = id;
		opt.textContent = (depth > 0 ? '— ' : '') + (categoryName || '');
		if (String(id) === String(categoryId)) {
			opt.selected = true;
		}

		return opt;
	};

	/* Render the current category's subcategories as navigable cards.
	   A parent lists only its OWN topics, so these cards are the way into
	   subcategory content — hidden entirely when there are none, which keeps
	   a flat forum looking exactly as it did before. */

	/* Cards per row implied by the column classes below (sm/lg only, matching
	   the rest of the fragment): 4 from lg up, 2 from sm up, 1 below. */
	const subcategoryColumnsPerRow = function () {
		if (window.matchMedia('(min-width: 992px)').matches) {
			return 4;
		}
		if (window.matchMedia('(min-width: 576px)').matches) {
			return 2;
		}

		return 1;
	};

	/* Two rows' worth of cards */
	const subcategoryVisibleLimit = function () {
		return subcategoryColumnsPerRow() * 2;
	};

	/* Hide everything past two rows and sync the toggle. Safe to re-run on
	   resize; the expanded state is deliberately preserved. */
	const applySubcategoryCollapse = function () {
		if (!subcatsRow) {
			return;
		}

		const cols = subcatsRow.children;
		const limit = subcategoryVisibleLimit();
		const overflows = cols.length > limit;

		for (let i = 0; i < cols.length; i++) {
			const hidden = !subcatsExpanded && overflows && i >= limit;
			cols[i].classList.toggle(
				'forums-message-list__subcategory-col--hidden',
				hidden
			);
		}

		if (!subcatsToggle) {
			return;
		}

		subcatsToggle.style.display = overflows ? '' : 'none';

		if (!overflows) {

			/* Nothing hidden, so the row is fully exposed */
			subcatsToggle.setAttribute('aria-expanded', 'true');

			return;
		}

		subcatsToggle.setAttribute(
			'aria-expanded',
			subcatsExpanded ? 'true' : 'false'
		);

		if (subcatsToggleLabel) {
			subcatsToggleLabel.textContent = subcatsExpanded
				? messageList.dataset.labelCollapse || 'Collapse'
				: messageList.dataset.labelExpand || 'Expand';
		}

		if (subcatsToggleIcon) {
			const use = subcatsToggleIcon.querySelector('use');
			if (use) {
				const href = use.getAttribute('href') || '';
				use.setAttribute(
					'href',
					href.replace(
						/#angle-(down|up)$/,
						subcatsExpanded ? '#angle-up' : '#angle-down'
					)
				);
			}
		}
	};

	const renderSubcategories = function (tree) {
		if (!subcatsContainer || !subcatsRow) {
			return;
		}
		subcatsRow.innerHTML = '';

		const children = categoryId ? tree.childrenOf[categoryId] || [] : [];
		if (!children.length) {
			subcatsContainer.style.display = 'none';

			return;
		}

		children.forEach(({categoryDescription, categoryName, id}) => {
			const col = document.createElement('div');
			col.className = 'col-sm-6 col-lg-3 mb-4';

			const card = document.createElement('a');
			card.href = categoryHref(id);
			card.className =
				'card card-interactive card-interactive-secondary h-100 text-decoration-none forums-message-list__subcategory-card';

			const body = document.createElement('div');
			body.className = 'card-body';

			const title = document.createElement('div');
			title.className =
				'card-title font-weight-semi-bold forums-message-list__subcategory-title';
			title.textContent = categoryName || '';
			title.title = categoryName || '';
			body.appendChild(title);

			/* Always appended — an empty description still reserves its two
			   lines so every card ends up the same height. */
			const description = document.createElement('p');
			description.className =
				'card-text text-secondary small forums-message-list__subcategory-desc';
			description.textContent = categoryDescription || '';
			if (categoryDescription) {

				/* Clamped to two lines — expose the full text on hover */
				description.title = categoryDescription;
				description.setAttribute('data-tooltip-align', 'top');
			}
			body.appendChild(description);

			card.appendChild(body);
			col.appendChild(card);
			subcatsRow.appendChild(col);
		});

		/* A freshly rendered box starts collapsed */
		subcatsExpanded = false;
		applySubcategoryCollapse();

		subcatsContainer.style.display = '';
	};

	if (subcatsToggle) {
		subcatsToggle.addEventListener('click', () => {
			subcatsExpanded = !subcatsExpanded;
			applySubcategoryCollapse();
		});
	}

	/* Registered once: the number of visible cards depends on the breakpoint */
	let subcatsResizeTimer = null;
	window.addEventListener('resize', () => {
		if (subcatsResizeTimer) {
			clearTimeout(subcatsResizeTimer);
		}
		subcatsResizeTimer = setTimeout(applySubcategoryCollapse, 150);
	});

	/* One fetch drives the breadcrumb, the filter dropdown and the
	   subcategory cards. */
	Liferay.Util.fetch(
		portalURL +
			'/o/c/forumcategories/scopes/' +
			scopeGroupId +
			categoryQuery(messageList.dataset),
		{
			headers,
			method: 'GET',
		}
	)
		.then((r) => {
			return r.json();
		})
		.then((data) => {
			categoryTree = buildTree(data.items || []);

			buildBreadcrumb(categoryTree);
			populateCategoryFilter(categoryTree);
			renderSubcategories(categoryTree);
		})
		.catch(() => {});

	if (categoryFilter) {
		categoryFilter.addEventListener('change', function () {
			categoryId = this.value || null;
			currentPage = 1;

			const params = new URLSearchParams(window.location.search);
			if (categoryId) {
				params.set('categoryId', categoryId);
			}
			else {
				params.delete('categoryId');
			}
			history.pushState(
				null,
				'',
				window.location.pathname +
					(params.toString() ? '?' + params.toString() : '')
			);

			if (categoryTree) {
				buildBreadcrumb(categoryTree);
				renderSubcategories(categoryTree);
			}

			loadMessages();
		});
	}

	/* Load messages */
	const loadMessages = function () {
		cardsContainer
			.querySelectorAll('.forums-message-card')
			.forEach((element) => {
				element.remove();
			});
		cardsContainer
			.querySelectorAll('.forums-message-list__empty')
			.forEach((element) => {
				element.remove();
			});
		if (paginationNav) {
			paginationNav.style.display = 'none';
		}
		if (showingEl) {
			showingEl.style.display = 'none';
		}

		if (loadingEl) {
			loadingEl.classList.remove('forums-skeleton--fade-out');

			// XSS: skeletonHTML is escaped by construction, captured from this
			// fragment's own markup

			loadingEl.innerHTML = skeletonHTML;
			loadingEl.style.display = '';
			loadingEl.setAttribute('aria-busy', 'true');
			skeletonStart = Date.now();
		}

		const filterParts = [];
		if (categoryId) {
			filterParts.push(
				"r_categoryThreads_c_forumCategoryId eq '" + categoryId + "'"
			);
		}

		/* Prioritized threads always sort on top (MB orders every listing by
		   priority DESC, lastPostDate DESC). Search results are the exception:
		   the priority field is not search-indexed, so — as in MB — search-driven
		   listings keep the plain tab sort. */
		const effectiveSort = searchQuery
			? currentSort
			: 'priority:desc,' + currentSort;

		let url =
			portalURL +
			'/o/c/forumthreads/scopes/' +
			scopeGroupId +
			'?page=' +
			currentPage +
			'&pageSize=' +
			pageSize +
			'&sort=' +
			effectiveSort;

		if (filterParts.length) {
			url += '&filter=' + encodeURIComponent(filterParts.join(' and '));
		}

		if (searchQuery) {
			url += '&search=' + encodeURIComponent(searchQuery);
		}

		Liferay.Util.fetch(url, {headers, method: 'GET'})
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				hideSkeleton();

				const {actions} = data;

				if (askBtn) {
					if (
						!isBanned &&
						actions &&
						(actions['post'] || actions['create'])
					) {
						askBtn.style.display = '';

						/* Carry the current category into New Discussion so the
					   composer preselects it — no manual category pick when
					   posting from within a category. */
						askBtn.href = categoryId
							? newDiscussionBaseUrl +
								'?categoryId=' +
								encodeURIComponent(categoryId)
							: newDiscussionBaseUrl;
					}
					else {
						askBtn.style.display = 'none';
					}
				}

				const items = data.items || [];
				const totalCount = data.totalCount || 0;
				const lastPage = data.lastPage || 1;

				if (!items.length) {
					cardsContainer.innerHTML =
						'<div class="forums-message-list__empty text-secondary text-center py-5">' +
						Liferay.Util.escapeHTML(
							messageList.dataset.labelNoMessages ||
								'No messages found.'
						) +
						'</div>';

					return;
				}

				/* Fetch replies and activities separately to avoid JOIN-based pagination overlap */
				const messageIds = items.map((t) => {
					return t.id;
				});
				const repliesFilter = messageIds
					.map((id) => {
						return (
							"r_threadMessages_c_forumThreadId eq '" + id + "'"
						);
					})
					.join(' or ');
				const activitiesFilter = messageIds
					.map((id) => {
						return (
							"r_threadSuspiciousActivities_c_forumThreadId eq '" +
							id +
							"'"
						);
					})
					.join(' or ');

				return Promise.all([
					Liferay.Util.fetch(
						portalURL +
							'/o/c/forummessages/scopes/' +
							scopeGroupId +
							'?filter=' +
							encodeURIComponent(repliesFilter) +
							'&pageSize=500&sort=dateCreated:asc',
						{headers, method: 'GET'}
					)
						.then((r) => {
							return r.json();
						})
						.catch(() => {
							return {items: []};
						}),
					Liferay.Util.fetch(
						portalURL +
							'/o/c/forumsuspiciousactivities/scopes/' +
							scopeGroupId +
							'?filter=' +
							encodeURIComponent(activitiesFilter) +
							'&pageSize=500',
						{headers, method: 'GET'}
					)
						.then((r) => {
							return r.json();
						})
						.catch(() => {
							return {items: []};
						}),
				]).then(([repliesPage, activitiesPage]) => {
					const repliesByMessage = {};
					(repliesPage.items || []).forEach((reply) => {
						const tid = reply.r_threadMessages_c_forumThreadId;
						if (!repliesByMessage[tid]) {
							repliesByMessage[tid] = [];
						}
						repliesByMessage[tid].push(reply);
					});
					const activitiesByMessage = {};
					(activitiesPage.items || []).forEach((activity) => {
						const tid =
							activity.r_threadSuspiciousActivities_c_forumThreadId;
						if (!activitiesByMessage[tid]) {
							activitiesByMessage[tid] = [];
						}
						activitiesByMessage[tid].push(activity);
					});
					items.forEach((msg) => {
						msg.threadMessages = repliesByMessage[msg.id] || [];
						msg.threadSuspiciousActivities =
							activitiesByMessage[msg.id] || [];
					});

					let html = '';
					let missingDisplayPage = false;
					items.forEach((msg) => {
						if (isBanned && msg.actions) {
							msg.actions = {};
						}

						const {
							actions,
							creator,
							dateCreated,
							friendlyUrlPath,
							keywords,
							messageTitle,
							priority,
							question,
							threadMessages,
							threadSuspiciousActivities,
							viewCount,
						} = msg;

						const title =
							messageTitle ||
							messageList.dataset.labelUntitledMessage ||
							'Untitled Message';
						const creatorName =
							displayName(creator) ||
							messageList.dataset.labelUnknown ||
							'Unknown';
						const creatorImage = (creator && creator.image) || '';
						const dateStr = dateCreated || '';
						const messages = threadMessages || [];
						const replyCount = messages.length
							? messages.length - 1
							: 0;
						let hasSolution = false;

						for (const {answer} of messages) {
							if (answer === true) {
								hasSolution = true;
								break;
							}
						}

						let isFlagged = false;
						const suspiciousActivities =
							threadSuspiciousActivities || [];
						for (const {validated} of suspiciousActivities) {
							if (validated === true) {
								isFlagged = true;
								break;
							}
						}

						/* Get first message body as preview */
						let preview = '';
						const [firstMessage] = messages;
						if (firstMessage && firstMessage.body) {
							const parsedBody = new DOMParser().parseFromString(
								firstMessage.body,
								'text/html'
							);
							preview = parsedBody.body.textContent || '';
							if (preview.length > 160) {
								preview = preview.substring(0, 160) + '...';
							}
						}

						/* Avatar (Clay sticker). Image stickers use `sticker-user-icon`
				   (white bg + subtle gray ring); initial-based stickers use
				   the colored `sticker-outline-N` palette. */
						let avatarHtml;
						if (creatorImage) {
							avatarHtml =
								'<span class="sticker sticker-circle sticker-lg"><span class="sticker-overlay"><img class="sticker-img" src="' +
								Liferay.Util.escapeHTML(creatorImage) +
								'" alt="' +
								Liferay.Util.escapeHTML(creatorName) +
								'"></span></span>';
						}
						else {
							avatarHtml =
								'<span class="sticker sticker-circle sticker-lg ' +
								avatarColorClass(creator) +
								'"><span class="sticker-overlay">' +
								Liferay.Util.escapeHTML(
									avatarInitial(creatorName)
								) +
								'</span></span>';
						}

						/* Solved badge */
						let solvedBadge = '';
						if (question && hasSolution) {
							const solvedText = Liferay.Util.escapeHTML(
								messageList.dataset.labelSolved || 'Solved'
							);
							solvedBadge =
								'<span class="forums-message-card__solved text-success font-weight-semi-bold ml-3 small">' +
								checkIcon +
								' ' +
								solvedText +
								'</span>';
						}

						const priorityBadgeHtml = priorityBadge(
							priority,
							messageList.dataset
						);

						const topicHref = friendlyUrlPath
							? sitePrefix + '/c_forumthread/' + friendlyUrlPath
							: null;
						if (!topicHref) {
							missingDisplayPage = true;
						}

						let flaggedBadge = '';
						if (isFlagged) {
							const flaggedText = Liferay.Util.escapeHTML(
								messageList.dataset.labelFlagged || 'Flagged'
							);
							flaggedBadge =
								'<span class="forums-message-card__solved text-danger ml-3 small"><svg class="lexicon-icon lexicon-icon-warning-full" role="presentation" viewBox="0 0 16 16" fill="currentColor"><path d="M16 14.5L8 1 0 14.5h16zM8 13c-.6 0-1-.4-1-1s.4-1 1-1 1 .4 1 1-.4 1-1 1zm1-3H7V6h2v4z"/></svg> ' +
								flaggedText +
								'</span>';
						}

						html +=
							'<div class="card forums-message-card">' +
							'<div class="card-body">' +
							'<div class="autofit-row">' +
							'<div class="autofit-col forums-message-card__avatar-col">' +
							avatarHtml +
							'<div class="forums-message-card__username text-secondary text-truncate">' +
							Liferay.Util.escapeHTML(creatorName) +
							'</div>' +
							'</div>' +
							'<div class="autofit-col autofit-col-expand forums-message-card__content">' +
							'<h5 class="card-title forums-message-card__title">' +
							(topicHref
								? '<a href="' +
									Liferay.Util.escapeHTML(topicHref) +
									'">' +
									Liferay.Util.escapeHTML(title) +
									'</a>'
								: '<span>' +
									Liferay.Util.escapeHTML(title) +
									'</span>') +
							priorityBadgeHtml +
							solvedBadge +
							flaggedBadge +
							'</h5>' +
							'<p class="forums-message-card__preview text-secondary">' +
							Liferay.Util.escapeHTML(preview) +
							'</p>' +
							(function () {
								const messageTags = keywords || [];
								if (!messageTags.length) {
									return '';
								}
								let tHtml =
									'<div class="forums-message-card__tags">';
								messageTags.forEach((tag) => {
									tHtml +=
										'<span class="label label-lg forums-message-card__tag"><span class="label-item label-item-expand">' +
										Liferay.Util.escapeHTML(tag) +
										'</span></span>';
								});
								tHtml += '</div>';

								return tHtml;
							})() +
							'<div class="forums-message-card__meta text-secondary small">' +
							'<span class="forums-message-card__meta-item">' +
							clockIcon +
							' <time datetime="' +
							Liferay.Util.escapeHTML(dateStr) +
							'" title="' +
							fullDateTime(dateStr) +
							'" aria-label="' +
							fullDateTime(dateStr) +
							'">' +
							Liferay.Util.escapeHTML(timeAgo(dateStr)) +
							'</time></span>' +
							'<span class="forums-message-card__meta-item">' +
							replyIcon +
							' ' +
							(replyCount === 1
								? (
										messageList.dataset.labelXReply ||
										'{0} reply'
									).replace('{0}', replyCount)
								: (
										messageList.dataset.labelXReplies ||
										'{0} replies'
									).replace('{0}', replyCount)) +
							'</span>' +
							'<span class="forums-message-card__meta-item">' +
							eyeIcon +
							' ' +
							(viewCount || 0) +
							'</span>' +
							(actions && actions['delete']
								? '<span class="forums-message-card__meta-item ml-auto"><button class="btn btn-monospaced btn-sm btn-outline-danger forums-list-delete-btn" data-delete-url="' +
									actions['delete'].href +
									'" title="' +
									(messageList.dataset.labelDelete ||
										'Delete') +
									'" aria-label="' +
									(messageList.dataset.labelDelete ||
										'Delete') +
									'"><svg class="lexicon-icon lexicon-icon-trash" role="presentation"><use href="' +
									clayIconsUrl +
									'#trash"></use></svg></button></span>'
								: '') +
							'</div>' +
							'</div>' +
							'</div>' +
							'</div>' +
							'</div>';
					});

					// XSS: html is escaped by Liferay.Util.escapeHTML where it is built

					cardsContainer.innerHTML = html;
					attachDeleteHandlers();

					if (
						missingDisplayPage &&
						Liferay.Util &&
						Liferay.Util.openToast
					) {
						Liferay.Util.openToast({
							message: Liferay.Util.escapeHTML(
								messageList.dataset
									.labelDisplayPageNotConfigured ||
									'Display page is not configured for one or more messages.'
							),
							type: 'danger',
						});
					}

					/* Showing x-y of total */
					if (showingEl && totalCount > 0) {
						const startItem = (currentPage - 1) * pageSize + 1;
						const endItem = Math.min(
							currentPage * pageSize,
							totalCount
						);
						const showingLabel = (
							messageList.dataset.labelShowing ||
							'Showing {0} of {1} Items'
						)
							.replace('{0}', startItem + '-' + endItem)
							.replace('{1}', totalCount);
						showingEl.textContent = showingLabel;
						showingEl.style.display = '';
					}

					/* Pagination */
					if (lastPage > 1 && paginationNav && paginationUl) {
						paginationNav.style.display = '';
						let pagHtml = '';

						pagHtml +=
							'<li class="page-item' +
							(currentPage <= 1 ? ' disabled' : '') +
							'">' +
							'<a class="page-link" href="#" data-page="' +
							(currentPage - 1) +
							'">&laquo;</a></li>';

						const delta = 2;
						const pageNumbers = [1];
						const rangeStart = Math.max(2, currentPage - delta);
						const rangeEnd = Math.min(
							lastPage - 1,
							currentPage + delta
						);

						if (rangeStart > 2) {
							pageNumbers.push('ellipsis');
						}
						for (let p = rangeStart; p <= rangeEnd; p++) {
							pageNumbers.push(p);
						}
						if (rangeEnd < lastPage - 1) {
							pageNumbers.push('ellipsis');
						}
						pageNumbers.push(lastPage);

						pageNumbers.forEach((p) => {
							if (p === 'ellipsis') {
								pagHtml +=
									'<li class="page-item disabled"><span class="page-link">&hellip;</span></li>';
							}
							else {
								pagHtml +=
									'<li class="page-item' +
									(p === currentPage ? ' active' : '') +
									'">' +
									'<a class="page-link" href="#" data-page="' +
									p +
									'">' +
									p +
									'</a></li>';
							}
						});

						pagHtml +=
							'<li class="page-item' +
							(currentPage >= lastPage ? ' disabled' : '') +
							'">' +
							'<a class="page-link" href="#" data-page="' +
							(currentPage + 1) +
							'">&raquo;</a></li>';

						// XSS: pagHtml is escaped by construction, interpolating only integers

						paginationUl.innerHTML = pagHtml;

						paginationUl
							.querySelectorAll('.page-link')
							.forEach((link) => {
								link.addEventListener(
									'click',
									function (event) {
										event.preventDefault();
										const p = parseInt(
											this.dataset.page,
											10
										);
										if (p >= 1 && p <= lastPage) {
											currentPage = p;
											loadMessages();
											messageList.scrollIntoView({
												behavior: 'smooth',
											});
										}
									}
								);
							});
					}
				}); /* close Promise.all().then() */
			})
			.catch((error) => {
				hideSkeleton();
				cardsContainer.innerHTML =
					'<div class="forums-message-list__empty text-secondary text-center py-5">' +
					Liferay.Util.escapeHTML(
						messageList.dataset.labelUnableToLoad ||
							'Unable to load messages.'
					) +
					'</div>';
				console.error('ForumsMessageList error:', error);
			});
	};

	const hideSkeleton = function () {
		if (!loadingEl) {
			return;
		}
		const elapsed = Date.now() - skeletonStart;
		const remaining = Math.max(0, SKELETON_MIN_MS - elapsed);
		setTimeout(() => {
			loadingEl.classList.add('forums-skeleton--fade-out');
			setTimeout(() => {
				loadingEl.style.display = 'none';
				loadingEl.removeAttribute('aria-busy');
				loadingEl.classList.remove('forums-skeleton--fade-out');
			}, 250);
		}, remaining);
	};

	/* Delete Modal Setup */
	let deleteModalObj = null;

	const showDeleteModal = function (title, message, onConfirm) {
		let modal = document.getElementById('forumsDeleteModal');
		if (!modal) {
			modal = document.createElement('div');
			modal.id = 'forumsDeleteModal';
			modal.className = 'modal';
			modal.style.backgroundColor = 'rgba(0,0,0,0.5)';
			modal.style.zIndex = '1050';
			modal.setAttribute('tabindex', '-1');
			modal.setAttribute('role', 'dialog');
			modal.setAttribute('aria-modal', 'true');
			modal.setAttribute('aria-labelledby', 'forumsDeleteModalHeading');

			// XSS: clayIconsUrl is escaped by construction, from Liferay.ThemeDisplay,
			// and the heading and the body are set through textContent below

			modal.innerHTML =
				'<div class="modal-dialog modal-dialog-sm modal-dialog-centered modal-danger">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<h1 class="modal-title" tabindex="-1">' +
				'<div class="modal-title-indicator">' +
				'<svg class="lexicon-icon lexicon-icon-exclamation-full" role="presentation">' +
				'<use href="' +
				clayIconsUrl +
				'#exclamation-full"></use>' +
				'</svg>' +
				'</div>' +
				'<span id="forumsDeleteModalHeading"></span>' +
				'</h1>' +
				'<button class="close btn btn-unstyled forums-delete-modal-close" type="button" aria-label="Close">' +
				'<svg class="lexicon-icon lexicon-icon-times" role="presentation">' +
				'<use href="' +
				clayIconsUrl +
				'#times"></use>' +
				'</svg>' +
				'</button>' +
				'</div>' +
				'<div class="modal-body">' +
				'<div class="liferay-modal-body" id="forumsDeleteModalBody"></div>' +
				'</div>' +
				'<div class="modal-footer">' +
				'<div class="modal-item-last">' +
				'<div class="btn-group-spaced" role="group">' +
				'<button class="btn btn-secondary forums-delete-modal-close" type="button">' +
				(messageList.dataset.labelCancel || 'Cancel') +
				'</button>' +
				'<button class="btn btn-danger" type="button" id="forumsDeleteModalConfirmBtn">' +
				(messageList.dataset.labelDelete || 'Delete') +
				'</button>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>';
			document.body.appendChild(modal);

			modal
				.querySelectorAll('.forums-delete-modal-close')
				.forEach((button) => {
					button.addEventListener('click', () => {
						modal.style.display = 'none';
						modal.classList.remove('show');
						if (deleteModalObj && deleteModalObj.onCancel) {
							deleteModalObj.onCancel();
						}
					});
				});

			modal
				.querySelector('#forumsDeleteModalConfirmBtn')
				.addEventListener('click', () => {
					modal.style.display = 'none';
					modal.classList.remove('show');
					if (deleteModalObj && deleteModalObj.onConfirm) {
						deleteModalObj.onConfirm();
					}
				});
		}

		modal.querySelector('#forumsDeleteModalHeading').textContent = title;
		modal.querySelector('#forumsDeleteModalBody').textContent = message;

		deleteModalObj = {
			onCancel: null,
			onConfirm,
		};

		modal.style.display = 'block';
		setTimeout(() => {
			modal.classList.add('show');
		}, 10);
	};

	const attachDeleteHandlers = function () {
		messageList
			.querySelectorAll('.forums-list-delete-btn')
			.forEach((button) => {
				button.addEventListener('click', function (event) {
					event.preventDefault();
					event.stopPropagation();

					const title =
						messageList.dataset.labelDeleteTopic || 'Delete Topic';
					const confirmMsg =
						messageList.dataset.labelConfirmDeleteTopic ||
						'Deleting a topic is an action impossible to revert. All the replies in the topic will be removed and it will not be possible to recover them.';

					const deleteUrl = this.dataset.deleteUrl;
					const card = this.closest('.forums-message-card');

					showDeleteModal(title, confirmMsg, () => {
						Liferay.Util.fetch(deleteUrl, {
							headers,
							method: 'DELETE',
						})
							.then((r) => {
								if (r.ok) {
									if (card) {
										card.style.opacity = '0.5';
										setTimeout(() => {
											card.remove();
										}, 300);
									}
									setTimeout(loadMessages, 1500);
								}
								else {
									console.error('Failed to delete topic');
								}
							})
							.catch((error) => {
								console.error('Delete topic error:', error);
							});
					});
				});
			});
	};

	/* Tab click handlers */
	tabLinks.forEach((tab) => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			tabLinks.forEach((t) => {
				t.classList.remove('active');
				t.setAttribute('aria-selected', 'false');
			});
			this.classList.add('active');
			this.setAttribute('aria-selected', 'true');
			currentSort = this.dataset.sort;
			currentPage = 1;
			loadMessages();
		});
	});

	/* Search handler */
	const doSearch = function () {
		searchQuery = searchInput ? searchInput.value.trim() : '';
		currentPage = 1;
		loadMessages();
	};

	if (searchBtn) {
		searchBtn.addEventListener('click', doSearch);
	}

	if (searchInput) {
		searchInput.addEventListener('keypress', (event) => {
			if (event.key === 'Enter') {
				event.preventDefault();
				doSearch();
			}
		});
	}

	/* Initial load */
	if (Liferay.ThemeDisplay.isSignedIn()) {
		Liferay.Util.fetch(
			portalURL +
				'/o/c/forumbans/scopes/' +
				scopeGroupId +
				'?filter=' +
				encodeURIComponent('banUserId eq ' + currentUserId) +
				'&pageSize=1',
			{
				headers,
				method: 'GET',
			}
		)
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				if (data.items && !!data.items.length) {
					isBanned = true;
				}
				loadMessages();
			})
			.catch((error) => {
				console.error('Error checking ban status', error);
				loadMessages();
			});
	}
	else {
		loadMessages();
	}
}
