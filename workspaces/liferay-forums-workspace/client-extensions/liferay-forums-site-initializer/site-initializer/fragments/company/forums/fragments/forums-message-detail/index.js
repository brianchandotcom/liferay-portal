/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const messageDetail = fragmentElement.querySelector('#forumsMessageDetail');

if (messageDetail) {
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const pathFriendlyURLPublic =
		Liferay.ThemeDisplay.getPathFriendlyURLPublic();
	let sitePrefix = '';
	if (pathFriendlyURLPublic) {
		const pubPath = pathFriendlyURLPublic + '/';
		const {pathname} = window.location;
		if (pathname.indexOf(pubPath) === 0) {
			const rest = pathname.substring(pubPath.length);
			const slugEnd = rest.indexOf('/');
			const siteSlug = slugEnd === -1 ? rest : rest.substring(0, slugEnd);
			sitePrefix = pathFriendlyURLPublic + '/' + siteSlug;
		}
	}
	const currentUserId = Liferay.ThemeDisplay.getUserId();

	/* Mentions render in the OOTB shape -- a "@screenName" token in a
	   <span class="lfr-ac-content"> (or a bare text token from CKEditor 5).
	   Neither navigates, so no click handling is needed. */

	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
	};
	const clayIconsUrl =
		Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

	/* DOM refs */
	const titleEl = messageDetail.querySelector('#forumsDetailTitle');
	const loadingEl = messageDetail.querySelector('#forumsDetailLoading');
	const opSection = messageDetail.querySelector('#forumsDetailOP');
	const opBody = messageDetail.querySelector('#forumsDetailOPBody');
	const opAttachments = messageDetail.querySelector(
		'#forumsDetailOPAttachments'
	);
	const opAvatar = messageDetail.querySelector('#forumsDetailOPAvatar');
	const opAuthor = messageDetail.querySelector('#forumsDetailOPAuthor');
	const opDate = messageDetail.querySelector('#forumsDetailOPDate');
	const opTags = messageDetail.querySelector('#forumsDetailOPTags');
	const solvedBanner = messageDetail.querySelector(
		'#forumsDetailSolvedBanner'
	);
	const solutionSection = messageDetail.querySelector(
		'#forumsDetailSolutionSection'
	);
	const solutionCards = messageDetail.querySelector(
		'#forumsDetailSolutionCards'
	);
	const repliesSection = messageDetail.querySelector(
		'#forumsDetailRepliesSection'
	);
	const replyCards = messageDetail.querySelector('#forumsDetailReplyCards');
	const replyCountEl = messageDetail.querySelector('#forumsDetailReplyCount');
	const replyBtn = messageDetail.querySelector('#forumsDetailReplyBtn');
	const flagBtn = messageDetail.querySelector('#forumsDetailFlagBtn');

	/* HATEOAS: hide write-action buttons by default; show after API confirms permission */
	if (replyBtn) {
		replyBtn.style.display = 'none';
		replyBtn.setAttribute(
			'title',
			messageDetail.dataset.labelComment || 'Comment'
		);
		replyBtn.setAttribute(
			'aria-label',
			messageDetail.dataset.labelComment || 'Comment'
		);
	}
	if (flagBtn) {
		flagBtn.style.display = 'none';
	}

	/* "View the Solution" scrolls the accepted-solution card into view and
	   flashes its highlight, rather than jumping to the top of the solution
	   section. */
	const viewSolutionLink = messageDetail.querySelector(
		'#forumsDetailViewSolution'
	);
	if (viewSolutionLink) {
		viewSolutionLink.addEventListener('click', (event) => {
			event.preventDefault();
			const solutionCard = messageDetail.querySelector(
				'.forums-message-detail__reply-card--solution'
			);
			if (!solutionCard) {
				return;
			}

			/* Scroll the card near the top of the viewport. When an admin is
			   signed in, the fixed Control Menu (.control-menu-container) sits
			   at the top z-order, so offset the scroll by its height to keep the
			   card from being hidden behind it. */
			const controlMenu = document.querySelector(
				'.control-menu-container'
			);
			const offset = (controlMenu ? controlMenu.offsetHeight : 0) + 12;
			const top =
				solutionCard.getBoundingClientRect().top +
				window.pageYOffset -
				offset;
			window.scrollTo({behavior: 'smooth', top});

			/* Remove + reflow + re-add so the flash replays on every click. */
			solutionCard.classList.remove(
				'forums-message-detail__reply-card--targeted'
			);
			void solutionCard.offsetWidth;
			solutionCard.classList.add(
				'forums-message-detail__reply-card--targeted'
			);
		});
	}

	const breadcrumbCategory = messageDetail.querySelector(
		'#forumsDetailBreadcrumbCategory'
	);
	const breadcrumbMessage = messageDetail.querySelector(
		'#forumsDetailBreadcrumbMessage'
	);
	const allTopicsLink = messageDetail.querySelector('#forumsDetailAllTopics');
	const categoryLink = messageDetail.querySelector(
		'#forumsDetailCategoryLink'
	);

	if (allTopicsLink) {
		allTopicsLink.href =
			sitePrefix +
			(typeof configuration !== 'undefined' && configuration.communityURL
				? configuration.communityURL
				: '/forums');
	}

	// Point the first breadcrumb crumb ("Forums") at the configured community home

	const communityBreadcrumb = messageDetail.querySelector(
		'#forumsDetailBreadcrumb li:first-child a'
	);
	if (communityBreadcrumb) {
		communityBreadcrumb.href =
			sitePrefix +
			(typeof configuration !== 'undefined' && configuration.communityURL
				? configuration.communityURL
				: '/forums');
	}
	const replyPaginationNav = messageDetail.querySelector(
		'#forumsDetailReplyPagination'
	);
	const replyPaginationUl = messageDetail.querySelector(
		'#forumsDetailReplyPaginationUl'
	);

	/* URL params */
	const urlParams = new URLSearchParams(window.location.search);
	let messageId = urlParams.get('messageId');

	/* Options Dropdown Vanilla JS Fallback */
	const optionsBtn = messageDetail.querySelector('#forumsDetailOptions');
	if (optionsBtn && Liferay.ThemeDisplay.isSignedIn()) {
		const optionsDropdown = messageDetail.querySelector(
			'#forumsDetailOptionsDropdown'
		);
		if (optionsDropdown) {
			optionsDropdown.style.display = '';
		}
		const optionsMenu = optionsBtn.nextElementSibling;
		optionsBtn.addEventListener('click', (event) => {
			event.preventDefault();
			if (optionsMenu) {
				const expanded = optionsMenu.classList.toggle('show');
				optionsBtn.setAttribute(
					'aria-expanded',
					expanded ? 'true' : 'false'
				);
			}
		});
		document.addEventListener('click', (event) => {
			if (
				optionsMenu &&
				!optionsBtn.contains(event.target) &&
				!optionsMenu.contains(event.target)
			) {
				optionsMenu.classList.remove('show');
				optionsBtn.setAttribute('aria-expanded', 'false');
			}
		});
		document.addEventListener('keydown', (event) => {
			if (
				event.key === 'Escape' &&
				optionsMenu &&
				optionsMenu.classList.contains('show')
			) {
				optionsMenu.classList.remove('show');
				optionsBtn.setAttribute('aria-expanded', 'false');
				optionsBtn.focus();
			}
		});
	}

	/* Reply options dropdown vanilla JS fallback (delegated: reply cards are
	   re-rendered on pagination, so we bind once on the fragment root). The menu
	   is positioned `fixed` while open so an ancestor's overflow:hidden (the page
	   / section wrapper) cannot clip it. */
	const closeReplyOptionMenus = function (except) {
		messageDetail
			.querySelectorAll(
				'.forums-message-detail__reply-options .dropdown-menu.show'
			)
			.forEach((menu) => {
				if (menu === except) {
					return;
				}
				menu.classList.remove('show');
				menu.style.position = '';
				menu.style.top = '';
				menu.style.left = '';
				menu.style.right = '';
				menu.style.zIndex = '';
				const toggle = menu.previousElementSibling;
				if (toggle) {
					toggle.setAttribute('aria-expanded', 'false');
				}
			});
	};
	messageDetail.addEventListener('click', (event) => {
		const toggle = event.target.closest('[id^="forumsReplyOptions_"]');
		if (!toggle) {
			return;
		}
		event.preventDefault();
		const menu = toggle.nextElementSibling;
		if (!menu || !menu.classList.contains('dropdown-menu')) {
			return;
		}
		const willOpen = !menu.classList.contains('show');
		closeReplyOptionMenus(menu);
		if (willOpen) {
			const {bottom, right} = toggle.getBoundingClientRect();
			menu.style.position = 'fixed';
			menu.style.top = Math.round(bottom + 2) + 'px';
			menu.style.left = 'auto';
			menu.style.right = Math.round(window.innerWidth - right) + 'px';
			menu.style.zIndex = '1050';
		}
		menu.classList.toggle('show', willOpen);
		toggle.setAttribute('aria-expanded', willOpen ? 'true' : 'false');
	});
	document.addEventListener('click', (event) => {
		if (!event.target.closest('.forums-message-detail__reply-options')) {
			closeReplyOptionMenus(null);
		}
	});
	document.addEventListener('keydown', (event) => {
		if (event.key === 'Escape') {
			closeReplyOptionMenus(null);
		}
	});
	window.addEventListener(
		'scroll',
		() => {
			closeReplyOptionMenus(null);
		},
		true
	);
	window.addEventListener('resize', () => {
		closeReplyOptionMenus(null);
	});

	const runMessageDetail = function (resolvedMessageId, replyId) {
		messageId = resolvedMessageId;
		const targetReplyId = replyId || null;
		let skeletonShownAt = Date.now();
		if (!messageId) {
			if (loadingEl) {
				loadingEl.innerHTML =
					'<div class="forums-message-list__empty text-secondary text-center py-5">' +
					(messageDetail.dataset.labelNoMessage ||
						'No message selected.') +
					'</div>';
			}

			return;
		}

		const replyPageSize = 10;
		let currentReplyPage = 1;
		let newViewCount = 0;
		let isBanned = false;

		/* Utility functions */
		function indentHtmlText(text) {
			const voidTag =
				/^<(area|base|br|col|embed|hr|img|input|link|meta|param|source|track|wbr)(\s|>|\/)/i;
			const pad = '  ';
			let indent = 0;

			return text
				.split('\n')
				.map((line) => {
					line = line.trim();
					if (!line) {
						return '';
					}
					const isClosing = /^<\//.test(line);
					const isOpening = /^<[^/!?]/.test(line);
					const isSelfClose =
						/\/\s*>$/.test(line) || voidTag.test(line);
					const hasInlineClose =
						isOpening && !isSelfClose && /<\/[^>]+>\s*$/.test(line);
					if (isClosing) {
						indent = Math.max(0, indent - 1);
					}
					const result = pad.repeat(indent) + line;
					if (isOpening && !isSelfClose && !hasInlineClose) {
						indent++;
					}

					return result;
				})
				.join('\n');
		}

		function formatMarkupCodeBlocks(container) {
			container.querySelectorAll('pre code').forEach((codeEl) => {
				let text = codeEl.textContent;
				if (text.indexOf('\n') === -1 && text.indexOf('> <') !== -1) {
					text = text.replace(/>\s+</g, '>\n<');
					text = indentHtmlText(text);
					codeEl.textContent = text;
				}
			});
		}

		function timeAgo(dateStr) {
			if (!dateStr) {
				return '';
			}
			const now = Date.now();
			const then = new Date(dateStr).getTime();
			const diff = Math.floor((now - then) / 1000);
			if (diff < 60) {
				return messageDetail.dataset.labelJustNow || 'just now';
			}
			if (diff < 3600) {
				return (
					messageDetail.dataset.labelXMinutesAgo || '{0} minutes ago.'
				).replace('{0}', Math.floor(diff / 60));
			}
			if (diff < 86400) {
				return (
					messageDetail.dataset.labelXHoursAgo || '{0} hours ago.'
				).replace('{0}', Math.floor(diff / 3600));
			}
			if (diff < 2592000) {
				return (
					messageDetail.dataset.labelXDaysAgo || '{0} days ago.'
				).replace('{0}', Math.floor(diff / 86400));
			}

			return new Date(dateStr).toLocaleDateString();
		}

		/* Full localized date + time (browser locale), e.g. "05/30/2026, 02:34:56 PM"
	   for en-US. Shown as the title tooltip and the screen-reader label on a
	   relative date. */
		function fullDateTime(dateStr) {
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
		}

		function avatarInitial(name) {
			if (!name) {
				return '?';
			}

			return name.charAt(0).toUpperCase();
		}

		function displayName(creator) {
			if (!creator) {
				return '';
			}
			const {familyName, givenName, name} = creator;
			const given = givenName || '';
			const family = familyName || '';

			return family && family !== 'User'
				? given + ' ' + family
				: given || name || '';
		}

		/* Stable avatar color from the Clay sticker-outline-0..9 palette */
		function avatarColorClass(creator) {
			const {id, name} = creator || {};
			const key = String(id || name || '');
			let n = 0;
			for (let i = 0; i < key.length; i++) {
				n = (n + key.charCodeAt(i)) % 10;
			}

			return 'sticker-outline-' + n;
		}

		function renderAvatar(creator, size) {
			const sizeClass = size === 'sm' ? 'sticker-sm' : 'sticker-lg';
			const baseCls = 'sticker sticker-circle ' + sizeClass;
			if (creator && creator.image) {
				return (
					'<span class="' +
					baseCls +
					'"><span class="sticker-overlay"><img class="sticker-img" src="' +
					Liferay.Util.escapeHTML(creator.image) +
					'" alt="' +
					Liferay.Util.escapeHTML(displayName(creator)) +
					'"></span></span>'
				);
			}
			const name = displayName(creator);

			return (
				'<span class="' +
				baseCls +
				' ' +
				avatarColorClass(creator) +
				'"><span class="sticker-overlay">' +
				avatarInitial(name) +
				'</span></span>'
			);
		}

		/* Vote state: maps messageId -> { voteId, voteValue } for current user */
		let userVoteMap = {};

		let opCreatorId = null; /* Track message owner for Mark as Answer */
		let opAuthorName =
			''; /* Display name of the OP, used in the "Answer selected by" annotation on the accepted reply */
		let currentAnswerId =
			null; /* Track currently accepted answer message ID */
		let messageDeleteUrl =
			null; /* Track HATEOAS URL to delete the whole message */
		let canUpdateMessage = false; /* HATEOAS: set true when ForumThreads API exposes update action for this message */
		let canVote = false; /* HATEOAS: set true when ForumVotes API exposes create action */
		let canReply = false; /* HATEOAS: set true when ForumReplies API exposes create action */
		let isMessageQuestion = false; /* Track if the message was marked as a question */
		let messageCategoryFK = null;
		let messageTitleText = '';
		let messagePriority = 0; /* Thread priority (MB parity: Urgent 3 / Sticky 2 / Announcement 1) */
		let messageTagsArray = [];

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

		function priorityBadge(priority) {
			const level =
				PRIORITY_LEVELS[Math.round(parseFloat(priority)) || 0];
			if (!level) {
				return '';
			}
			const {fallback, icon, labelKey, textClass} = level;
			const label = Liferay.Util.escapeHTML(
				messageDetail.dataset[labelKey] || fallback
			);

			return (
				'<span class="forums-message-detail__priority-badge ' +
				textClass +
				'">' +
				'<svg class="lexicon-icon lexicon-icon-' +
				icon +
				'" role="presentation"><use href="' +
				clayIconsUrl +
				'#' +
				icon +
				'"></use></svg> ' +
				label +
				'</span>'
			);
		}
		const replyMessagesMap = {};
		let existingFlagId =
			null; /* Track if the current user already flagged this message */

		function renderReplyCard(msg, isSolution, depth) {
			depth = depth || 0;

			const {
				actions,
				body: replyBody,
				creator: replyCreator,
				dateCreated,
				id,
				r_threadMessages_c_forumThreadId,
				voteScore,
			} = msg;

			const creator = replyCreator || {};
			const name =
				displayName(creator) ||
				messageDetail.dataset.labelUnknown ||
				'Unknown';
			const body = replyBody || '';
			const dateFull = fullDateTime(dateCreated);
			const date =
				'<time datetime="' +
				dateCreated +
				'" title="' +
				dateFull +
				'" aria-label="' +
				dateFull +
				'">' +
				timeAgo(dateCreated) +
				'</time>';
			const score = voteScore || 0;
			const solClass = isSolution
				? ' forums-message-detail__reply-card--solution'
				: '';
			const depthStyle =
				depth > 0 ? ' style="margin-left:' + depth * 2.5 + 'rem"' : '';
			const {voteValue} = userVoteMap[id] || {};
			const upActive = voteValue === 1 ? ' active' : '';
			const downActive = voteValue === -1 ? ' active' : '';
			const isUpPressed = voteValue === 1 ? 'true' : 'false';
			const isDownPressed = voteValue === -1 ? 'true' : 'false';
			const upIcon = voteValue === 1 ? 'thumbs-up-full' : 'thumbs-up';
			const downIcon =
				voteValue === -1 ? 'thumbs-down-full' : 'thumbs-down';

			const hasEditAction = !!(
				actions &&
				(actions['update'] || actions['patch'] || actions['PUT'])
			);
			const hasDeleteAction = !!(actions && actions['delete']);
			const hasOptions = hasEditAction || hasDeleteAction;
			const optionsLabel =
				messageDetail.dataset.labelOptions || 'Options';

			/* Lock the accepted answer: once one reply is marked, only that reply
		   keeps the toggle (so it can be unmarked). Hide the button on all
		   other replies — the user must unmark the current accepted answer
		   first before they can mark a different one. */
			const hasAcceptedAnswer = currentAnswerId !== null;
			const canMarkAnswer =
				isMessageQuestion &&
				(canUpdateMessage ||
					(opCreatorId &&
						String(opCreatorId) === String(currentUserId))) &&
				depth === 0 &&
				(!hasAcceptedAnswer || isSolution);
			const isAuthor =
				opCreatorId && String(creator.id) === String(opCreatorId);

			return `<div class="forums-message-detail__reply-card${solClass}" data-message-id="${id}"${depthStyle}>
			<div class="autofit-row forums-message-detail__reply-layout">
				<div class="autofit-col mr-2">
					${renderAvatar(creator, 'sm')}
				</div>
				<div class="autofit-col autofit-col-expand forums-message-detail__reply-content">
					<div class="forums-message-detail__reply-header">
						<span class="text-dark font-weight-bold">${Liferay.Util.escapeHTML(name)}</span>
						${isAuthor ? `<span class="label forums-message-detail__author-badge">${messageDetail.dataset.labelAuthor || 'Author'}</span>` : ''}
						<span class="text-secondary small">${date}</span>
						${
							isSolution
								? (function () {
										const tmpl =
											messageDetail.dataset
												.labelAnswerSelectedBy ||
											'Answer selected by {0}';
										const [beforeName, afterName] =
											tmpl.split('{0}');

										return `<span class="small forums-message-detail__answer-selected-by"><svg class="lexicon-icon lexicon-icon-check-circle-full" role="presentation"><use href="${clayIconsUrl}#check-circle-full"></use></svg>${Liferay.Util.escapeHTML(beforeName || '')}<span class="forums-message-detail__answer-selected-by-name">${Liferay.Util.escapeHTML(opAuthorName)}</span>${Liferay.Util.escapeHTML(afterName || '')}</span>`;
									})()
								: ''
						}
					</div>
					<div class="forums-message-detail__reply-body">${body}</div>
					${renderAttachments(msg)}
					<div class="forums-message-detail__reply-actions">
						${canReply ? `<button class="btn btn-outline-primary btn-sm" type="button" data-forums-compose data-forums-reply data-forums-message-id="${r_threadMessages_c_forumThreadId}" data-forums-parent-id="${id}">${messageDetail.dataset.labelReply || 'Reply'}</button>` : ''}
						${
							hasOptions
								? `<div class="dropdown forums-message-detail__reply-options">
							<button class="btn btn-monospaced btn-sm btn-outline-borderless btn-outline-secondary dropdown-toggle" type="button" id="forumsReplyOptions_${id}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" aria-label="${optionsLabel}" title="${optionsLabel}">
								<svg class="lexicon-icon lexicon-icon-ellipsis-v" role="presentation"><use href="${clayIconsUrl}#ellipsis-v"></use></svg>
							</button>
							<div class="dropdown-menu dropdown-menu-right" aria-labelledby="forumsReplyOptions_${id}">
								${hasEditAction ? `<a class="dropdown-item forums-edit-reply-btn" href="#" data-message-id="${id}">${messageDetail.dataset.labelEditReply || 'Edit Reply'}</a>` : ''}
								${hasDeleteAction ? `<a class="dropdown-item text-danger forums-delete-btn" href="#" data-delete-url="${actions['delete'].href}">${messageDetail.dataset.labelDeleteReply || 'Delete Reply'}</a>` : ''}
							</div>
						</div>`
								: ''
						}
						<div class="align-items-center d-inline-flex text-secondary forums-vote" data-message-id="${id}">
							<button class="btn-thumbs-up btn btn-monospaced btn-outline-borderless btn-sm btn-outline-secondary forums-vote__btn forums-vote__btn--up${upActive}" type="button" aria-pressed="${isUpPressed}"${canVote ? ` data-vote-dir="up" data-message-id="${id}"` : ' disabled'} title="${messageDetail.dataset.labelUpvote || 'Upvote'}">
								<svg class="lexicon-icon lexicon-icon-${upIcon}" role="presentation"><use href="${clayIconsUrl}#${upIcon}"></use></svg>
							</button>
							<span class="font-weight-bold p-1 forums-vote__score" data-vote-score="${id}">${score}</span>
							<button class="btn-thumbs-down btn btn-monospaced btn-outline-borderless btn-sm btn-outline-secondary forums-vote__btn forums-vote__btn--down${downActive}" type="button" aria-pressed="${isDownPressed}"${canVote ? ` data-vote-dir="down" data-message-id="${id}"` : ' disabled'} title="${messageDetail.dataset.labelDownvote || 'Downvote'}">
								<svg class="lexicon-icon lexicon-icon-${downIcon}" role="presentation"><use href="${clayIconsUrl}#${downIcon}"></use></svg>
							</button>
						</div>
						${canMarkAnswer ? `<button class="btn btn-sm ${isSolution ? 'btn-success' : 'btn-outline-secondary'} forums-answer-btn" data-answer-message-id="${id}" data-is-answer="${isSolution ? 'true' : 'false'}">${isSolution ? `&#10003; ${messageDetail.dataset.labelAccepted || 'Accepted'}` : messageDetail.dataset.labelMarkAsAnswer || 'Mark as Answer'}</button>` : ''}
					</div>
				</div>
			</div>
		</div>`;
		}

		/* Attachments embedded on a message via the messageAttachments relationship
	   nested field. Liferay returns them under the relationship-name key as either a
	   bare array or an {items:[...]} envelope; tolerate both. */
		function attachmentsOf(msg) {
			const value = msg && msg.messageAttachments;
			if (!value) {
				return [];
			}
			if (Array.isArray(value)) {
				return value;
			}

			return value.items || [];
		}

		/* The chip markup for one message's attachments. canManage adds a remove (×)
	   control (shown to the author only; deletion is also enforced server-side).
	   Returns '' when the message has no files. */
		function renderAttachments(msg) {
			const files = attachmentsOf(msg);
			if (!files.length) {
				return '';
			}

			/* Read-only view: download chips only. Removing an attachment is done from
		   the Edit dialog (forums-message-composer), alongside the rest of the post. */
			const chips = files
				.map((att) => {
					const {file: attFile, id} = att;
					const {link, name: fileName} = attFile || {};
					const name =
						fileName ||
						(link && link.label) ||
						messageDetail.dataset.labelDownload ||
						'Download';
					const safeName = Liferay.Util.escapeHTML(name);

					return (
						'<span class="forums-message-detail__attachment" data-attachment-id="' +
						id +
						'">' +
						'<button type="button" class="btn btn-outline-secondary btn-sm forums-attachment-download" data-attachment-id="' +
						id +
						'" data-attachment-name="' +
						safeName +
						'" title="' +
						safeName +
						'">' +
						'<svg class="lexicon-icon lexicon-icon-paperclip" role="presentation"><use href="' +
						clayIconsUrl +
						'#paperclip"></use></svg>' +
						'<span class="forums-message-detail__attachment-name">' +
						safeName +
						'</span>' +
						'</button>' +
						'</span>'
					);
				})
				.join('');

			return (
				'<div class="forums-message-detail__attachments">' +
				chips +
				'</div>'
			);
		}

		/* Sort messages: accepted answers first, then by voteScore desc, then dateCreated asc */
		function sortByVoteScore(messages) {
			return messages
				.slice()
				.sort(
					(
						{answer: aAns, dateCreated: aDate, voteScore: aVote},
						{answer: bAns, dateCreated: bDate, voteScore: bVote}
					) => {

						/* Accepted answers first */
						const aAnswer =
							isMessageQuestion && aAns === true ? 1 : 0;
						const bAnswer =
							isMessageQuestion && bAns === true ? 1 : 0;
						if (bAnswer !== aAnswer) {
							return bAnswer - aAnswer;
						}

						/* Higher score first */
						const aScore = aVote || 0;
						const bScore = bVote || 0;
						if (bScore !== aScore) {
							return bScore - aScore;
						}

						/* Older first as tiebreaker */
						return new Date(aDate) - new Date(bDate);
					}
				);
		}

		/* Build a tree from flat messages using parentMessageId */
		function buildMessageTree(messages, opId) {
			const childrenMap = {};
			let topLevel = [];
			const messageIds = {};

			messages.forEach((msg) => {
				messageIds[msg.id] = true;
			});

			messages.forEach((msg) => {
				let parentId = msg.parentMessageId || 0;

				/* If a message's parent is missing (e.g. deleted or on a different page), treat it as top-level */
				if (
					parentId !== 0 &&
					parentId !== opId &&
					!messageIds[parentId]
				) {
					parentId = opId;
				}

				if (!childrenMap[parentId]) {
					childrenMap[parentId] = [];
				}
				childrenMap[parentId].push(msg);
			});

			/* Top-level replies: parentMessageId is 0 or equals the OP id */
			topLevel = (childrenMap[0] || []).concat(childrenMap[opId] || []);

			/* Remove duplicates (if opId is 0, both keys point to same array) */
			if (opId === 0) {
				topLevel = childrenMap[0] || [];
			}

			/* Sort top-level replies by score */
			topLevel = sortByVoteScore(topLevel);

			function renderTree(msgList, depth) {
				let html = '';
				msgList.forEach((msg) => {
					html += renderReplyCard(
						msg,
						isMessageQuestion && msg.answer === true,
						depth
					);
					const children = childrenMap[msg.id];
					if (children && !!children.length) {
						html += renderTree(
							sortByVoteScore(children),
							depth + 1
						);
					}
				});

				return html;
			}

			return renderTree(topLevel, 0);
		}

		/* Fetch current user's votes for all messages in this message */
		function fetchUserVotes(messageIds, callback) {
			if (!messageIds || !messageIds.length) {
				callback();

				return;
			}
			if (!Liferay.ThemeDisplay.isSignedIn()) {
				callback();

				return;
			}

			/* Filter by current user's ID to only get this user's votes */
			const filterParam = encodeURIComponent(
				'creatorId eq ' + currentUserId
			);
			Liferay.Util.fetch(
				portalURL +
					'/o/c/forumvotes/scopes/' +
					scopeGroupId +
					'?filter=' +
					filterParam +
					'&pageSize=200',
				{
					headers,
					method: 'GET',
				}
			)
				.then((r) => {
					return r.json();
				})
				.then((data) => {

					/* HATEOAS: check if this user can create votes */
					if (
						!isBanned &&
						data.actions &&
						(data.actions['POST'] ||
							data.actions['post'] ||
							data.actions['create'])
					) {
						canVote = true;
					}
					else if (!isBanned && canReply) {

						/* Fallback: if HATEOAS is missing from the filtered votes endpoint but the user can reply, allow voting */
						canVote = true;
					}
					const items = data.items || [];
					userVoteMap = {};
					const messageIdSet = {};
					messageIds.forEach((id) => {
						messageIdSet[id] = true;
					});
					items.forEach(
						({
							id: voteId,
							r_messageVotes_c_forumMessageId: msgId,
							voteValue,
						}) => {
							if (msgId && messageIdSet[msgId]) {
								userVoteMap[msgId] = {voteId, voteValue};
							}
						}
					);
					callback();
				})
				.catch(() => {
					callback();
				});
		}

		/* Handle upvote/downvote click */
		function handleVote(messageId, direction) {
			if (!canVote) {
				return;
			}
			const voteValue = direction === 'up' ? 1 : -1;
			const existing = userVoteMap[messageId];

			if (existing) {
				const {voteId, voteValue: existingValue} = existing;
				if (existingValue === voteValue) {

					/* Same direction: remove the vote (toggle off) */
					Liferay.Util.fetch(
						portalURL + '/o/c/forumvotes/' + voteId,
						{
							headers,
							method: 'DELETE',
						}
					)
						.then(() => {
							delete userVoteMap[messageId];
							updateVoteScore(messageId, -voteValue);
						})
						.catch((error) => {
							console.error('Vote delete error:', error);
						});
				}
				else {

					/* Opposite direction: delete old, create new */
					Liferay.Util.fetch(
						portalURL + '/o/c/forumvotes/' + voteId,
						{
							headers,
							method: 'DELETE',
						}
					)
						.then(() => {
							return createVote(messageId, voteValue);
						})
						.then(() => {

							/* Score swings by 2: removed old + added new */
							updateVoteScore(messageId, voteValue * 2);
						})
						.catch((error) => {
							console.error('Vote switch error:', error);
						});
				}
			}
			else {

				/* No existing vote: create new */
				createVote(messageId, voteValue)
					.then(() => {
						updateVoteScore(messageId, voteValue);
					})
					.catch((error) => {
						console.error('Vote create error:', error);
					});
			}
		}

		function createVote(messageId, voteValue) {
			return Liferay.Util.fetch(
				portalURL + '/o/c/forumvotes/scopes/' + scopeGroupId,
				{
					body: JSON.stringify({
						r_messageVotes_c_forumMessageId: messageId,
						voteValue,
					}),
					headers,
					method: 'POST',
				}
			)
				.then((r) => {
					return r.json();
				})
				.then(({id: voteId}) => {
					userVoteMap[messageId] = {voteId, voteValue};
				});
		}

		function updateVoteScore(messageId, delta) {

			/* Update the score in the DOM */
			const scoreEl = messageDetail.querySelector(
				'[data-vote-score="' + messageId + '"]'
			);
			if (scoreEl) {
				const current = parseInt(scoreEl.textContent, 10) || 0;
				const newScore = current + delta;
				scoreEl.textContent = newScore;
			}

			/* Update button active states and icons */
			const voteContainer = messageDetail.querySelector(
				'.forums-vote[data-message-id="' + messageId + '"]'
			);
			if (voteContainer) {
				const upBtn = voteContainer.querySelector(
					'.forums-vote__btn--up'
				);
				const downBtn = voteContainer.querySelector(
					'.forums-vote__btn--down'
				);
				const {voteValue} = userVoteMap[messageId] || {};
				const isUp = voteValue === 1;
				const isDown = voteValue === -1;

				if (upBtn) {
					upBtn.classList.toggle('active', isUp);
					upBtn.setAttribute('aria-pressed', isUp ? 'true' : 'false');
					const upSvg = upBtn.querySelector('use');
					if (upSvg) {
						upSvg.setAttribute(
							'href',
							clayIconsUrl +
								'#' +
								(isUp ? 'thumbs-up-full' : 'thumbs-up')
						);
					}
				}
				if (downBtn) {
					downBtn.classList.toggle('active', isDown);
					downBtn.setAttribute(
						'aria-pressed',
						isDown ? 'true' : 'false'
					);
					const downSvg = downBtn.querySelector('use');
					if (downSvg) {
						downSvg.setAttribute(
							'href',
							clayIconsUrl +
								'#' +
								(isDown ? 'thumbs-down-full' : 'thumbs-down')
						);
					}
				}
			}

			/* Also PATCH the ForumMessage to persist the denormalized score */
			if (scoreEl) {
				const persistedScore = parseInt(scoreEl.textContent, 10) || 0;
				Liferay.Util.fetch(
					portalURL + '/o/c/forummessages/' + messageId,
					{
						body: JSON.stringify({voteScore: persistedScore}),
						headers,
						method: 'PATCH',
					}
				).catch((error) => {
					console.error('Score persist error:', error);
				});
			}
		}

		/* Attach vote click handlers after rendering */
		function attachVoteHandlers() {
			messageDetail
				.querySelectorAll('.forums-vote__btn')
				.forEach((button) => {
					button.addEventListener('click', function (event) {
						event.preventDefault();
						const msgId = this.dataset.messageId;
						const dir = this.dataset.voteDir;
						if (msgId && dir) {
							handleVote(parseInt(msgId, 10), dir);
						}
					});
				});
		}

		/* Mark / Unmark as Answer */
		function handleMarkAnswer(messageId, isCurrentlyAnswer) {
			if (isCurrentlyAnswer) {

				/* Unmark this answer */
				Liferay.Util.fetch(
					portalURL + '/o/c/forummessages/' + messageId,
					{
						body: JSON.stringify({answer: false}),
						headers,
						method: 'PATCH',
					}
				)
					.then((r) => {
						if (r.ok) {
							currentAnswerId = null;
							setTimeout(loadMessages, 1500);
						}
					})
					.catch((error) => {
						console.error('Unmark answer error:', error);
					});
			}
			else {

				/* If another answer exists, unmark it first */
				let chain = Promise.resolve();
				if (currentAnswerId && currentAnswerId !== messageId) {
					chain = Liferay.Util.fetch(
						portalURL + '/o/c/forummessages/' + currentAnswerId,
						{
							body: JSON.stringify({answer: false}),
							headers,
							method: 'PATCH',
						}
					);
				}
				chain
					.then(() => {
						return Liferay.Util.fetch(
							portalURL + '/o/c/forummessages/' + messageId,
							{
								body: JSON.stringify({answer: true}),
								headers,
								method: 'PATCH',
							}
						);
					})
					.then((r) => {
						if (r.ok) {
							currentAnswerId = messageId;
							setTimeout(loadMessages, 1500);
						}
					})
					.catch((error) => {
						console.error('Mark answer error:', error);
					});
			}
		}

		function attachAnswerHandlers() {
			messageDetail
				.querySelectorAll('.forums-answer-btn')
				.forEach((button) => {
					button.addEventListener('click', function (event) {
						event.preventDefault();
						const msgId = parseInt(
							this.dataset.answerMessageId,
							10
						);
						const isAnswer = this.dataset.isAnswer === 'true';

						this.style.opacity = '0.5';
						this.style.pointerEvents = 'none';

						handleMarkAnswer(msgId, isAnswer);
					});
				});
		}

		/* Delete Modal Setup */
		let deleteModalObj = null;

		function showDeleteModal(title, message, onConfirm) {
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
				modal.setAttribute(
					'aria-labelledby',
					'forumsDeleteModalHeading'
				);

				modal.innerHTML = `
			<div class="modal-dialog modal-dialog-sm modal-dialog-centered modal-danger">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title" tabindex="-1">
							<div class="modal-title-indicator">
								<svg class="lexicon-icon lexicon-icon-exclamation-full" role="presentation">
									<use href="${clayIconsUrl}#exclamation-full"></use>
								</svg>
							</div>
							<span id="forumsDeleteModalHeading"></span>
						</h1>
						<button class="close btn btn-unstyled forums-delete-modal-close" type="button" aria-label="Close">
							<svg class="lexicon-icon lexicon-icon-times" role="presentation">
								<use href="${clayIconsUrl}#times"></use>
							</svg>
						</button>
					</div>
					<div class="modal-body">
						<div class="liferay-modal-body" id="forumsDeleteModalBody"></div>
					</div>
					<div class="modal-footer">
						<div class="modal-item-last">
							<div class="btn-group-spaced" role="group">
								<button class="btn btn-secondary forums-delete-modal-close" type="button">${messageDetail.dataset.labelCancel || 'Cancel'}</button>
								<button class="btn btn-danger" type="button" id="forumsDeleteModalConfirmBtn">${messageDetail.dataset.labelDelete || 'Delete'}</button>
							</div>
						</div>
					</div>
				</div>
			</div>`;
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

			modal.querySelector('#forumsDeleteModalHeading').textContent =
				title;
			modal.querySelector('#forumsDeleteModalBody').textContent = message;

			deleteModalObj = {
				onCancel: null,
				onConfirm,
			};

			modal.style.display = 'block';
			setTimeout(() => {
				modal.classList.add('show');
			}, 10);
		}

		/* Delete Reply */
		function attachDeleteHandlers() {
			messageDetail
				.querySelectorAll('.forums-delete-btn')
				.forEach((button) => {
					button.addEventListener('click', function (event) {
						event.preventDefault();
						const isReply = this.closest(
							'.forums-message-detail__reply-card'
						);
						const title = isReply
							? messageDetail.dataset.labelDeleteReply ||
								'Delete Reply'
							: messageDetail.dataset.labelDeleteTopic ||
								'Delete Topic';
						const confirmMsg = isReply
							? messageDetail.dataset.labelConfirmDeleteReply ||
								'Deleting a reply is an action impossible to revert. It will not be possible to recover it.'
							: messageDetail.dataset.labelConfirmDeleteTopic ||
								'Deleting a topic is an action impossible to revert. All the replies in the topic will be removed and it will not be possible to recover them.';

						const deleteUrl = this.dataset.deleteUrl;
						const btnEl = this;

						showDeleteModal(title, confirmMsg, () => {
							Liferay.Util.fetch(deleteUrl, {
								headers,
								method: 'DELETE',
							})
								.then((r) => {
									if (r.ok) {

										/* Optimistically remove the card from the DOM */
										const card = btnEl.closest(
											'.forums-message-detail__reply-card'
										);
										if (card) {
											card.style.opacity = '0.5';
											setTimeout(() => {
												card.remove();
											}, 300);

											/* Delay the reload to allow the backend search index to catch up */
											setTimeout(loadMessages, 1500);
										}
										else {

											/* This is the Original Post being deleted — navigate back to the message list */
											const opSection =
												messageDetail.querySelector(
													'#forumsDetailOP'
												);
											if (opSection) {
												opSection.style.opacity = '0.5';
											}
											const breadcrumbCatEl =
												messageDetail.querySelector(
													'#forumsDetailBreadcrumbCategory'
												);
											const messagesBase =
												sitePrefix +
												(typeof configuration !==
													'undefined' &&
												configuration.messagesURL
													? configuration.messagesURL
													: '/forums-messages');
											const targetHref =
												breadcrumbCatEl &&
												breadcrumbCatEl.href
													? breadcrumbCatEl.href
													: messageCategoryFK
														? messagesBase +
															'?categoryId=' +
															messageCategoryFK
														: messagesBase;
											setTimeout(() => {
												window.location.href =
													targetHref;
											}, 1500);
										}
									}
									else {
										console.error(
											'Failed to delete message'
										);
									}
								})
								.catch((error) => {
									console.error(
										'Delete message error:',
										error
									);
								});
						});
					});
				});
		}

		function attachEditReplyHandlers() {
			messageDetail
				.querySelectorAll('.forums-edit-reply-btn')
				.forEach((button) => {
					button.addEventListener('click', function (event) {
						event.preventDefault();
						const msgId = parseInt(this.dataset.messageId, 10);
						const msg = replyMessagesMap[msgId];
						if (msg && window.forumsOpenComposeModal) {
							window.forumsOpenComposeModal({
								body: msg.body,
								editMode: true,
								isOp: false,
								messageId: msg.id,
							});
						}
					});
				});
		}

		/* Download an attachment's bytes and save them client-side. The file's own
	   /documents link may be permission-gated, so instead we fetch the content as
	   base64 through object REST (gated by the row's VIEW, which every site member
	   has) and build a Blob. */
		function downloadAttachment(attachmentId, name) {
			Liferay.Util.fetch(
				portalURL +
					'/o/c/forummessageattachments/' +
					attachmentId +
					'?nestedFields=file.fileBase64',
				{
					headers,
					method: 'GET',
				}
			)
				.then((r) => {
					return r.json();
				})
				.then((row) => {
					const {
						fileBase64,
						mimeType,
						name: fileName,
					} = (row && row.file) || {};
					if (!fileBase64) {
						throw new Error('no file content');
					}
					const bytes = atob(fileBase64);
					const array = new Uint8Array(bytes.length);
					for (let i = 0; i < bytes.length; i++) {
						array[i] = bytes.charCodeAt(i);
					}
					const blob = new Blob([array], {
						type: mimeType || 'application/octet-stream',
					});
					const objectUrl = URL.createObjectURL(blob);
					const anchor = document.createElement('a');
					anchor.href = objectUrl;
					anchor.download = fileName || name;
					document.body.appendChild(anchor);
					anchor.click();
					anchor.remove();
					URL.revokeObjectURL(objectUrl);
				})
				.catch((error) => {
					console.error('Download attachment error:', error);
					if (Liferay.Util && Liferay.Util.openToast) {
						Liferay.Util.openToast({
							message:
								messageDetail.dataset
									.labelCouldNotDownloadFile ||
								'Could not download the file. Please try again.',
							type: 'danger',
						});
					}
				});
		}

		/* Wire the download control on attachment chips (available to any member).
	   Removing an attachment lives in the Edit dialog, not this read-only view. */
		function attachAttachmentHandlers() {
			messageDetail
				.querySelectorAll('.forums-attachment-download')
				.forEach((button) => {
					button.addEventListener('click', function (event) {
						event.preventDefault();
						downloadAttachment(
							this.dataset.attachmentId,
							this.dataset.attachmentName
						);
					});
				});
		}

		/* Load message data */
		function initMessageDetail() {
			if (isBanned) {
				const bannedBanner = document.createElement('div');
				bannedBanner.className = 'alert alert-danger mt-3';
				bannedBanner.setAttribute('role', 'alert');
				bannedBanner.innerHTML = `<span class="alert-indicator"><svg class="lexicon-icon lexicon-icon-warning-full" role="presentation" viewBox="0 0 16 16" fill="currentColor"><path d="M16 14.5L8 1 0 14.5h16zM8 13c-.6 0-1-.4-1-1s.4-1 1-1 1 .4 1 1-.4 1-1 1zm1-3H7V6h2v4z"/></svg></span><strong class="lead">${messageDetail.dataset.labelBanned || 'Banned'}: </strong>${messageDetail.dataset.labelBannedWarning || 'Your account has been banned from participating in the forums.'}`;

				const titleRow = messageDetail.querySelector(
					'.forums-message-detail__title-row'
				);
				if (titleRow) {
					titleRow.parentNode.insertBefore(bannedBanner, titleRow);
				}
			}

			Liferay.Util.fetch(
				portalURL +
					'/o/c/forumthreads/' +
					messageId +
					'?nestedFields=threadSuspiciousActivities',
				{
					headers,
					method: 'GET',
				}
			)
				.then((r) => {
					return r.json();
				})
				.then((msg) => {
					if (isBanned) {
						if (msg.actions) {
							msg.actions = {};
						}
						if (optionsBtn) {
							optionsBtn.style.display = 'none';
						}
					}

					const {
						actions,
						keywords,
						messageTitle,
						priority,
						question,
						r_categoryThreads_c_forumCategoryId,
						threadSuspiciousActivities,
						viewCount,
					} = msg;

					if (actions && actions['delete']) {
						messageDeleteUrl = actions['delete'].href;
					}
					if (
						actions &&
						(actions['update'] ||
							actions['patch'] ||
							actions['PUT'])
					) {
						canUpdateMessage = true;
					}

					/* Subscription state lives in the ForumSubscription object. Object entry
		   permissions scope the list to the caller, so a match means "I am
		   subscribed". */

					let subscribeBtn = messageDetail.querySelector(
						'#forumsDetailSubscribeBtn'
					);

					if (
						subscribeBtn &&
						!isBanned &&
						parseInt(currentUserId, 10) > 0
					) {
						const subscriptionsUrl =
							portalURL +
							'/o/c/forumsubscriptions/scopes/' +
							scopeGroupId;

						/* Relationship fields compare as strings, so quote the id. */
						const subscriptionFilter = encodeURIComponent(
							"r_threadSubscriptions_c_forumThreadId eq '" +
								messageId +
								"'"
						);

						Liferay.Util.fetch(
							subscriptionsUrl +
								'?pageSize=1&fields=id&filter=' +
								subscriptionFilter,
							{
								headers,
								method: 'GET',
							}
						)
							.then((r) => {
								return r.ok ? r.json() : {items: []};
							})
							.then((page) => {
								const [subscription] = page.items || [];
								let subscriptionId = subscription
									? subscription.id
									: 0;

								function renderSubscribeLabel(button) {
									button.textContent = subscriptionId
										? messageDetail.dataset
												.labelUnsubscribe ||
											'Unsubscribe'
										: messageDetail.dataset
												.labelSubscribe || 'Subscribe';
								}

								renderSubscribeLabel(subscribeBtn);
								subscribeBtn.style.display = '';

								const newSubBtn = subscribeBtn.cloneNode(true);
								subscribeBtn.parentNode.replaceChild(
									newSubBtn,
									subscribeBtn
								);
								subscribeBtn = newSubBtn;

								subscribeBtn.addEventListener(
									'click',
									function (event) {
										event.preventDefault();
										const button = this;
										button.style.opacity = '0.5';
										button.style.pointerEvents = 'none';

										const request = subscriptionId
											? Liferay.Util.fetch(
													portalURL +
														'/o/c/forumsubscriptions/' +
														subscriptionId,
													{
														headers,
														method: 'DELETE',
													}
												).then((r) => {
													if (r.ok) {
														subscriptionId = 0;
													}

													return r.ok;
												})
											: Liferay.Util.fetch(
													subscriptionsUrl,
													{
														body: JSON.stringify({
															r_threadSubscriptions_c_forumThreadId:
																parseInt(
																	messageId,
																	10
																),
															subscriberUserId:
																parseInt(
																	currentUserId,
																	10
																),
														}),
														headers,
														method: 'POST',
													}
												).then((r) => {
													if (!r.ok) {
														return false;
													}

													return r
														.json()
														.then((created) => {
															subscriptionId =
																created.id;

															return true;
														});
												});

										request
											.then((ok) => {
												if (!ok) {
													return;
												}

												renderSubscribeLabel(button);

												const optionsMenu =
													button.closest(
														'.dropdown-menu'
													);
												if (optionsMenu) {
													optionsMenu.classList.remove(
														'show'
													);
												}

												if (
													Liferay.Util &&
													Liferay.Util.openToast
												) {
													const toastMsg =
														subscriptionId
															? messageDetail
																	.dataset
																	.labelSubscribedToast ||
																'You have been subscribed to this message.'
															: messageDetail
																	.dataset
																	.labelUnsubscribedToast ||
																'You have been unsubscribed from this message.';
													Liferay.Util.openToast({
														message: toastMsg,
														title:
															messageDetail
																.dataset
																.labelSuccess ||
															'Success',
														type: 'success',
													});
												}
											})
											.catch((error) => {
												console.error(
													'Subscription error:',
													error
												);
											})
											.finally(() => {
												button.style.opacity = '1';
												button.style.pointerEvents = '';
											});
									}
								);
							})
							.catch((error) => {
								console.error(
									'Subscription lookup error:',
									error
								);
							});
					}

					/* Increment viewCount via REST PATCH (unique per session) */
					let currentViewCount = viewCount;
					currentViewCount = currentViewCount || 0;
					const viewStorageKey =
						'forums_viewed_' + currentUserId + '_' + messageId;
					let alreadyViewed = false;
					try {
						alreadyViewed =
							!!sessionStorage.getItem(viewStorageKey);
					}
					catch (error) {}

					if (!alreadyViewed && Liferay.ThemeDisplay.isSignedIn()) {
						newViewCount = currentViewCount + 1;
						Liferay.Util.fetch(
							portalURL + '/o/c/forumthreads/' + messageId,
							{
								body: JSON.stringify({viewCount: newViewCount}),
								headers,
								method: 'PATCH',
							}
						)
							.then((r) => {
								return r.json().then((body) => {
									if (r.ok) {
										try {
											sessionStorage.setItem(
												viewStorageKey,
												'1'
											);
										}
										catch (error) {}
									}
									else {
										console.error(
											'View count update failed:',
											r.status,
											body
										);
									}
								});
							})
							.catch((error) => {
								console.error(
									'View count update error:',
									error
								);
							});
					}
					else {
						newViewCount = currentViewCount;
					}

					isMessageQuestion = question === true;

					let isFlagged = false;
					const suspiciousActivities =
						threadSuspiciousActivities || [];
					for (const {validated} of suspiciousActivities) {
						if (validated === true) {
							isFlagged = true;
							break;
						}
					}

					if (isFlagged) {
						let flaggedBanner = messageDetail.querySelector(
							'#forumsDetailFlaggedBanner'
						);
						if (!flaggedBanner) {
							flaggedBanner = document.createElement('div');
							flaggedBanner.id = 'forumsDetailFlaggedBanner';
							flaggedBanner.className = 'alert alert-danger mt-3';
							flaggedBanner.setAttribute('role', 'alert');
							flaggedBanner.innerHTML = `<span class="alert-indicator"><svg class="lexicon-icon lexicon-icon-warning-full" role="presentation" viewBox="0 0 16 16" fill="currentColor"><path d="M16 14.5L8 1 0 14.5h16zM8 13c-.6 0-1-.4-1-1s.4-1 1-1 1 .4 1 1-.4 1-1 1zm1-3H7V6h2v4z"/></svg></span><strong class="lead">${messageDetail.dataset.labelFlagged || 'Flagged'}: </strong>${messageDetail.dataset.labelFlaggedWarning || 'This message has been flagged and validated as inappropriate content.'}`;

							const titleRow = messageDetail.querySelector(
								'.forums-message-detail__title-row'
							);
							if (titleRow) {
								titleRow.parentNode.insertBefore(
									flaggedBanner,
									titleRow.nextSibling
								);
							}
						}
						else {
							flaggedBanner.style.display = '';
						}
					}

					messageTitleText =
						messageTitle ||
						messageDetail.dataset.labelUntitledMessage ||
						'Untitled Message';
					messageCategoryFK = r_categoryThreads_c_forumCategoryId;
					messagePriority = priority || 0;
					messageTagsArray = keywords || [];
					const title = messageTitleText;
					const categoryFK = messageCategoryFK;

					if (titleEl) {
						titleEl.textContent = title;
						const priorityBadgeHtml =
							priorityBadge(messagePriority);
						if (priorityBadgeHtml) {
							titleEl.insertAdjacentHTML(
								'beforeend',
								priorityBadgeHtml
							);
						}
					}
					if (breadcrumbMessage) {
						breadcrumbMessage.textContent = title;
					}

					/* Fetch category for breadcrumb */
					if (categoryFK) {
						Liferay.Util.fetch(
							portalURL + '/o/c/forumcategories/' + categoryFK,
							{
								headers,
								method: 'GET',
							}
						)
							.then((r) => {
								return r.json();
							})
							.then((cat) => {
								const catName =
									cat.categoryName ||
									messageDetail.dataset.labelCategory ||
									'Category';
								const messagesHref =
									sitePrefix +
									(typeof configuration !== 'undefined' &&
									configuration.messagesURL
										? configuration.messagesURL
										: '/forums-messages');
								const catURL =
									messagesHref + '?categoryId=' + categoryFK;

								if (breadcrumbCategory) {
									breadcrumbCategory.textContent = catName;
									breadcrumbCategory.href = catURL;
								}

								/* Also populate the bottom category link */
								if (categoryLink) {
									const labelText = (
										messageDetail.dataset.labelBackToX ||
										'Back to {0}'
									).replace('{0}', catName);
									categoryLink.textContent = labelText;
									categoryLink.href = catURL;
									categoryLink.style.display = '';
								}
							})
							.catch(() => {});
					}

					/* Check if the current user has already flagged this message (dedup) */
					if (flagBtn && currentUserId) {
						Liferay.Util.fetch(
							portalURL +
								'/o/c/forumsuspiciousactivities/scopes/' +
								scopeGroupId +
								'?filter=' +
								encodeURIComponent(
									'creatorId eq ' +
										currentUserId +
										' and suspiciousMessageId eq ' +
										messageId
								) +
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
								const items = data.items || [];
								const [firstFlag] = items;
								if (firstFlag) {
									existingFlagId = firstFlag.id;
									flagBtn.textContent =
										messageDetail.dataset.labelFlagged ||
										'Flagged';
									flagBtn.classList.add('disabled');
									flagBtn.disabled = true;
								}
							})
							.catch((error) => {
								console.error('Flag dedup check error:', error);
							});
					}

					/* Fetch messages for this message */
					loadMessages();
				})
				.catch((error) => {
					if (loadingEl) {
						loadingEl.innerHTML =
							'<div class="forums-message-list__empty text-secondary text-center py-5">' +
							(messageDetail.dataset.labelUnableToLoadMessage ||
								'Unable to load message.') +
							'</div>';
					}
					console.error('ForumsMessageDetail error:', error);
				});
		}

		/* Load messages */
		function loadMessages() {

			/* Re-show skeleton during pagination / refresh reloads */
			if (loadingEl) {
				loadingEl.classList.remove('forums-skeleton--fade-out');
				loadingEl.style.display = '';
				loadingEl.setAttribute('aria-busy', 'true');
				skeletonShownAt = Date.now();
			}

			Liferay.Util.fetch(
				portalURL +
					'/o/c/forummessages/scopes/' +
					scopeGroupId +
					'?filter=' +
					encodeURIComponent(
						"r_threadMessages_c_forumThreadId eq '" +
							messageId +
							"'"
					) +
					'&sort=dateCreated:asc&page=' +
					currentReplyPage +
					'&pageSize=' +
					replyPageSize +
					'&nestedFields=messageAttachments',
				{
					headers,
					method: 'GET',
				}
			)
				.then((r) => {
					return r.json();
				})
				.then((data) => {
					const messages = data.items || [];
					const totalCount = data.totalCount || 0;
					const lastPage = data.lastPage || 1;

					if (!messages.length) {
						if (loadingEl) {
							loadingEl.style.display = 'none';
							loadingEl.removeAttribute('aria-busy');
						}

						return;
					}

					if (isBanned) {
						messages.forEach((msg) => {
							msg.actions = {};
						});
					}

					/* HATEOAS: check if this user can create messages (reply) */
					if (
						!isBanned &&
						data.actions &&
						(data.actions['POST'] ||
							data.actions['post'] ||
							data.actions['create'])
					) {
						canReply = true;
						if (replyBtn) {
							replyBtn.style.display = '';
						}
						if (flagBtn) {
							flagBtn.style.display = '';
						}
					}

					/* Fetch user votes FIRST, then render everything */
					const allMsgIds = messages.map((m) => {
						return m.id;
					});
					fetchUserVotes(allMsgIds, () => {

						/* Clear existing DOM structures to prevent artifacts */
						if (solutionCards) {
							solutionCards.innerHTML = '';
						}
						if (replyCards) {
							replyCards.innerHTML = '';
						}
						if (solvedBanner) {
							solvedBanner.style.display = 'none';
						}
						if (solutionSection) {
							solutionSection.style.display = 'none';
						}
						if (repliesSection) {
							repliesSection.style.display = 'none';
						}

						/* Separate OP from replies */
						let opMsg = null;
						const replyMessages = [];

						messages.forEach((msg, index) => {
							replyMessagesMap[msg.id] = msg;
							if (currentReplyPage === 1 && index === 0) {
								opMsg = msg;
							}
							else {
								replyMessages.push(msg);
							}
						});

						/* Render Original Post */
						if (opMsg) {
							const {
								body: opMsgBody,
								creator: opCreator,
								dateCreated: opDateCreated,
								id: opMsgId,
								voteScore: opVoteScore,
							} = opMsg;
							const creator = opCreator || {};
							const {id: creatorId, image} = creator;
							opCreatorId = creatorId || null;
							opAuthorName =
								displayName(creator) ||
								messageDetail.dataset.labelUnknown ||
								'Unknown';
							if (opBody) {
								opBody.innerHTML = opMsgBody || '';
								formatMarkupCodeBlocks(opBody);
							}
							if (opAttachments) {
								opAttachments.innerHTML =
									renderAttachments(opMsg);
							}
							if (opAvatar) {
								const opAvatarCls =
									'sticker sticker-circle sticker-lg';
								if (image) {
									opAvatar.className = opAvatarCls;
									opAvatar.innerHTML =
										'<span class="sticker-overlay"><img class="sticker-img" src="' +
										Liferay.Util.escapeHTML(image) +
										'" alt="' +
										Liferay.Util.escapeHTML(
											displayName(creator)
										) +
										'"></span>';
								}
								else {
									opAvatar.className =
										opAvatarCls +
										' ' +
										avatarColorClass(creator);
									opAvatar.innerHTML =
										'<span class="sticker-overlay">' +
										Liferay.Util.escapeHTML(
											avatarInitial(displayName(creator))
										) +
										'</span>';
								}
							}
							if (opAuthor) {
								opAuthor.textContent =
									displayName(creator) ||
									messageDetail.dataset.labelUnknown ||
									'Unknown';
							}
							if (opDate) {
								opDate.textContent = timeAgo(opDateCreated);
								const opDateFull = fullDateTime(opDateCreated);
								opDate.title = opDateFull;
								opDate.setAttribute('aria-label', opDateFull);
							}

							/* Render OP Tags */
							if (opTags && !!messageTagsArray.length) {
								const tagsHtml = messageTagsArray
									.map((tag) => {
										return `<span class="label label-lg forums-message-detail__tag"><span class="label-item label-item-expand">${Liferay.Util.escapeHTML(tag)}</span></span>`;
									})
									.join('');
								opTags.innerHTML = tagsHtml;
								opTags.style.display = '';
							}

							if (opSection) {
								opSection.style.display = '';
							}

							const authorInfoEl = messageDetail.querySelector(
								'#forumsDetailAuthorInfo'
							);
							if (authorInfoEl) {
								authorInfoEl.style.display = '';
							}

							/* Render OP vote buttons */
							const opVoteEl = messageDetail.querySelector(
								'#forumsDetailOPVote'
							);
							if (opVoteEl) {
								const opScore = opVoteScore || 0;
								const {voteValue: opVoteValue} =
									userVoteMap[opMsgId] || {};
								const opUpActive =
									opVoteValue === 1 ? ' active' : '';
								const opDownActive =
									opVoteValue === -1 ? ' active' : '';
								const opIsUpPressed =
									opVoteValue === 1 ? 'true' : 'false';
								const opIsDownPressed =
									opVoteValue === -1 ? 'true' : 'false';
								const opUpIcon =
									opVoteValue === 1
										? 'thumbs-up-full'
										: 'thumbs-up';
								const opDownIcon =
									opVoteValue === -1
										? 'thumbs-down-full'
										: 'thumbs-down';

								opVoteEl.className =
									'align-items-center d-inline-flex justify-content-center text-secondary forums-vote';
								opVoteEl.setAttribute(
									'data-message-id',
									opMsgId
								);
								const upvoteTitle =
									messageDetail.dataset.labelUpvote ||
									'Upvote';
								const downvoteTitle =
									messageDetail.dataset.labelDownvote ||
									'Downvote';
								opVoteEl.innerHTML = `
						<button class="btn-thumbs-up btn btn-monospaced btn-outline-borderless btn-outline-secondary forums-vote__btn forums-vote__btn--up${opUpActive}" type="button" aria-pressed="${opIsUpPressed}"${canVote ? ` data-vote-dir="up" data-message-id="${opMsgId}"` : ' disabled'} title="${upvoteTitle}">
							<svg class="lexicon-icon lexicon-icon-${opUpIcon}" role="presentation"><use href="${clayIconsUrl}#${opUpIcon}"></use></svg>
						</button>
						<span class="font-weight-bold mx-2 forums-vote__score" data-vote-score="${opMsgId}">${opScore}</span>
						<button class="btn-thumbs-down btn btn-monospaced btn-outline-borderless btn-outline-secondary forums-vote__btn forums-vote__btn--down${opDownActive}" type="button" aria-pressed="${opIsDownPressed}"${canVote ? ` data-vote-dir="down" data-message-id="${opMsgId}"` : ' disabled'} title="${downvoteTitle}">
							<svg class="lexicon-icon lexicon-icon-${opDownIcon}" role="presentation"><use href="${clayIconsUrl}#${opDownIcon}"></use></svg>
						</button>`;
							}

							/* Wire up OP Edit / Delete dropdown items if permitted (HATEOAS).
				   The buttons live in the options dropdown next to the title;
				   here we just toggle their visibility and (re)attach handlers. */
							const dropdownEditBtn = messageDetail.querySelector(
								'#forumsDetailEditBtn'
							);
							if (dropdownEditBtn && canUpdateMessage) {
								dropdownEditBtn.style.display = '';

								/* Clone to clear any prior click handler from previous loadMessages. */
								const newDropdownEditBtn =
									dropdownEditBtn.cloneNode(true);
								dropdownEditBtn.parentNode.replaceChild(
									newDropdownEditBtn,
									dropdownEditBtn
								);
								newDropdownEditBtn.addEventListener(
									'click',
									(event) => {
										event.preventDefault();
										if (window.forumsOpenComposeModal) {
											window.forumsOpenComposeModal({
												body: opMsgBody,
												categoryId: messageCategoryFK,
												editMode: true,
												isOp: true,
												isQuestion: isMessageQuestion,
												messageId: opMsgId,
												priority: messagePriority,
												subject: messageTitleText,
												tags: messageTagsArray,
												threadId: messageId,
											});
										}
									}
								);
							}

							const dropdownDeleteBtn =
								messageDetail.querySelector(
									'#forumsDetailDeleteBtn'
								);
							if (dropdownDeleteBtn && messageDeleteUrl) {

								/* Clone to clear any prior click handler from previous loadMessages
					   (attachDeleteHandlers() re-binds on the new node below). */
								const newDropdownDeleteBtn =
									dropdownDeleteBtn.cloneNode(true);
								newDropdownDeleteBtn.setAttribute(
									'data-delete-url',
									messageDeleteUrl
								);
								newDropdownDeleteBtn.style.display = '';
								dropdownDeleteBtn.parentNode.replaceChild(
									newDropdownDeleteBtn,
									dropdownDeleteBtn
								);

								/* The click handler is attached by the shared attachDeleteHandlers()
					   call below because the element carries .forums-delete-btn. */
							}

							/* Render OP Toggle Question button if permitted (HATEOAS) */
							const toggleQuestionBtn =
								messageDetail.querySelector(
									'#forumsDetailToggleQuestionBtn'
								);
							if (
								toggleQuestionBtn &&
								!isBanned &&
								(canUpdateMessage ||
									(opCreatorId &&
										String(opCreatorId) ===
											String(currentUserId)))
							) {
								toggleQuestionBtn.textContent =
									isMessageQuestion
										? messageDetail.dataset
												.labelConvertToMessage ||
											'Convert to Discussion'
										: messageDetail.dataset
												.labelConvertToQuestion ||
											'Convert to Question';
								toggleQuestionBtn.style.display = '';

								const newBtn =
									toggleQuestionBtn.cloneNode(true);
								toggleQuestionBtn.parentNode.replaceChild(
									newBtn,
									toggleQuestionBtn
								);

								newBtn.addEventListener('click', (event) => {
									event.preventDefault();
									const newStatus = !isMessageQuestion;

									/* When demoting a question to a discussion, first fetch every
						   reply marked as answer (across all pages) and clear the flag
						   on each. Discussions don't have solutions, so leaving
						   `answer: true` rows behind would be stale. */
									const preWork = newStatus
										? Promise.resolve()
										: Liferay.Util.fetch(
												portalURL +
													'/o/c/forummessages/scopes/' +
													scopeGroupId +
													'?filter=' +
													encodeURIComponent(
														"r_threadMessages_c_forumThreadId eq '" +
															messageId +
															"' and answer eq true"
													) +
													'&fields=id&pageSize=100',
												{
													headers,
													method: 'GET',
												}
											)
												.then((r) => {
													return r.json();
												})
												.then((data) => {
													const items =
														(data && data.items) ||
														[];

													return Promise.all(
														items.map((reply) => {
															return Liferay.Util.fetch(
																portalURL +
																	'/o/c/forummessages/' +
																	reply.id,
																{
																	body: JSON.stringify(
																		{
																			answer: false,
																		}
																	),
																	headers,
																	method: 'PATCH',
																}
															);
														})
													);
												});

									preWork
										.then(() => {
											return Liferay.Util.fetch(
												portalURL +
													'/o/c/forumthreads/' +
													messageId,
												{
													body: JSON.stringify({
														question: newStatus,
													}),
													headers,
													method: 'PATCH',
												}
											);
										})
										.then((r) => {
											if (r.ok) {
												isMessageQuestion = newStatus;
												if (!newStatus) {
													currentAnswerId = null;
												}
												loadMessages();
											}
										})
										.catch((error) => {
											console.error(
												'Error toggling question status',
												error
											);
										});
								});
							}
						}

						/* Separate solutions from regular replies. Reset the tracked
			   accepted-answer id first so a stale value from a previous load
			   doesn't leak into the "Mark as Answer" button visibility. */
						const solutions = [];
						const regularReplies = [];
						currentAnswerId = null;
						replyMessages.forEach((msg) => {
							if (isMessageQuestion && msg.answer === true) {
								solutions.push(msg);
								currentAnswerId = msg.id;
							}
							else {
								regularReplies.push(msg);
							}
						});

						/* Render Solved banner + solution cards */
						if (solutions.length) {
							if (solvedBanner) {
								solvedBanner.style.display = '';
							}
							if (solutionSection) {
								solutionSection.style.display = '';
							}

							let solHtml = '';
							solutions.forEach((sol) => {
								solHtml += renderReplyCard(sol, true, 0);
							});
							if (solutionCards) {
								solutionCards.innerHTML = solHtml;
								formatMarkupCodeBlocks(solutionCards);
							}
						}

						/* Render regular replies as a messageed tree */
						let regularReplyCount =
							totalCount - 1 - solutions.length;
						if (regularReplyCount < 0) {
							regularReplyCount = 0;
						}

						if (!!regularReplies.length || regularReplyCount > 0) {
							if (repliesSection) {
								repliesSection.style.display = '';
							}
							if (replyCountEl) {
								const tmpl =
									regularReplyCount === 1
										? messageDetail.dataset.labelXReply ||
											'{0} reply'
										: messageDetail.dataset.labelXReplies ||
											'{0} replies';
								replyCountEl.textContent = tmpl.replace(
									'{0}',
									regularReplyCount
								);
							}

							const opId = opMsg ? opMsg.id : 0;
							const repHtml = buildMessageTree(
								regularReplies,
								opId
							);
							if (replyCards) {
								replyCards.innerHTML = repHtml;
								formatMarkupCodeBlocks(replyCards);
							}
						}

						/* Attach all handlers after rendering */
						attachVoteHandlers();
						attachAnswerHandlers();
						attachDeleteHandlers();
						attachEditReplyHandlers();
						attachAttachmentHandlers();

						/* Hide skeleton after render, with a minimum display time to prevent flash */
						if (loadingEl) {
							const elapsed = Date.now() - skeletonShownAt;
							const minDisplayMs = 600;
							const remainingMs = Math.max(
								0,
								minDisplayMs - elapsed
							);
							setTimeout(() => {
								loadingEl.classList.add(
									'forums-skeleton--fade-out'
								);
								setTimeout(() => {
									loadingEl.style.display = 'none';
									loadingEl.removeAttribute('aria-busy');
									loadingEl.classList.remove(
										'forums-skeleton--fade-out'
									);
								}, 250);
							}, remainingMs);
						}

						/* Scroll to a specific reply when the fragment is on a Forum Message Display Page */
						if (targetReplyId) {
							const targetCard = messageDetail.querySelector(
								'.forums-message-detail__reply-card[data-message-id="' +
									targetReplyId +
									'"]'
							);
							if (targetCard) {
								setTimeout(() => {
									targetCard.scrollIntoView({
										behavior: 'smooth',
										block: 'center',
									});
									targetCard.classList.add(
										'forums-message-detail__reply-card--targeted'
									);
								}, 200);
							}
						}

						/* Reply pagination */
						if (
							lastPage > 1 &&
							replyPaginationNav &&
							replyPaginationUl
						) {
							replyPaginationNav.style.display = '';
							const pageNums = [];
							for (let p = 1; p <= lastPage && p <= 10; p++) {
								pageNums.push(p);
							}

							const pagHtml =
								`<li class="page-item${currentReplyPage <= 1 ? ' disabled' : ''}"><a class="page-link" href="#" data-page="${currentReplyPage - 1}">&laquo;</a></li>` +
								pageNums
									.map((p) => {
										return `<li class="page-item${p === currentReplyPage ? ' active' : ''}"><a class="page-link" href="#" data-page="${p}">${p}</a></li>`;
									})
									.join('') +
								`<li class="page-item${currentReplyPage >= lastPage ? ' disabled' : ''}"><a class="page-link" href="#" data-page="${currentReplyPage + 1}">&raquo;</a></li>`;

							replyPaginationUl.innerHTML = pagHtml;

							replyPaginationUl
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
											if (p >= 1) {
												currentReplyPage = p;
												loadMessages();
												repliesSection.scrollIntoView({
													behavior: 'smooth',
												});
											}
										}
									);
								});
						}
					}); /* end fetchUserVotes callback */
				})
				.catch((error) => {
					if (loadingEl) {
						loadingEl.innerHTML =
							'<div class="forums-message-list__empty text-secondary text-center py-5">' +
							(messageDetail.dataset.labelUnableToLoadMessages ||
								'Unable to load messages.') +
							'</div>';
					}
					console.error('ForumsMessageDetail messages error:', error);
				});
		}

		/* Report Inappropriate Content Modal */
		let reportModalObj = null;

		function showReportModal(onSubmit) {
			let modal = document.getElementById('forumsReportModal');
			if (!modal) {
				modal = document.createElement('div');
				modal.id = 'forumsReportModal';
				modal.className = 'modal';
				modal.style.backgroundColor = 'rgba(0,0,0,0.5)';
				modal.style.zIndex = '1050';
				modal.setAttribute('tabindex', '-1');
				modal.setAttribute('role', 'dialog');
				modal.setAttribute('aria-modal', 'true');
				modal.setAttribute(
					'aria-labelledby',
					'forumsReportModalHeading'
				);

				const reasonOptions = [
					{
						label: messageDetail.dataset.labelSpam || 'Spam',
						value: 'spam',
					},
					{
						label:
							messageDetail.dataset.labelHarmfulDangerousActs ||
							'Harmful Dangerous Acts',
						value: 'harmful-dangerous-acts',
					},
					{
						label:
							messageDetail.dataset.labelHarassmentBullying ||
							'Harassment or Bullying',
						value: 'harassment-bullying',
					},
					{
						label:
							messageDetail.dataset.labelNuditySexualContent ||
							'Nudity or Sexual Content',
						value: 'nudity-sexual-content',
					},
					{
						label: messageDetail.dataset.labelOther || 'Other',
						value: 'other',
					},
				];

				const optionsHtml = reasonOptions
					.map(({label, value}) => {
						return `<option value="${Liferay.Util.escapeHTML(value)}">${Liferay.Util.escapeHTML(label)}</option>`;
					})
					.join('');

				modal.innerHTML = `
				<div class="modal-dialog modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title" id="forumsReportModalHeading" tabindex="-1">${Liferay.Util.escapeHTML(messageDetail.dataset.labelReportInappropriateContent || 'Report Inappropriate Content')}</h1>
							<button class="close btn btn-unstyled forums-report-modal-close" type="button" aria-label="Close">
								<svg class="lexicon-icon lexicon-icon-times" role="presentation">
									<use href="${clayIconsUrl}#times"></use>
								</svg>
							</button>
						</div>
						<div class="modal-body">
							<p class="text-secondary">${Liferay.Util.escapeHTML(messageDetail.dataset.labelReportDescription || 'You are about to report a violation of our Terms of Use. All reports are strictly confidential.')}</p>
							<div class="form-group">
								<label for="forumsReportReason" class="font-weight-bold">${Liferay.Util.escapeHTML(messageDetail.dataset.labelReasonForReport || 'Reason for the Report')}</label>
								<select class="form-control" id="forumsReportReason">
									${optionsHtml}
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<div class="modal-item-last">
								<div class="btn-group-spaced" role="group">
									<button class="btn btn-secondary forums-report-modal-close" type="button">${Liferay.Util.escapeHTML(messageDetail.dataset.labelCancel || 'Cancel')}</button>
									<button class="btn btn-primary" type="button" id="forumsReportModalSubmitBtn">${Liferay.Util.escapeHTML(messageDetail.dataset.labelReport || 'Report')}</button>
								</div>
							</div>
						</div>
					</div>
				</div>`;
				document.body.appendChild(modal);

				modal
					.querySelectorAll('.forums-report-modal-close')
					.forEach((button) => {
						button.addEventListener('click', () => {
							modal.style.display = 'none';
							modal.classList.remove('show');
						});
					});

				modal
					.querySelector('#forumsReportModalSubmitBtn')
					.addEventListener('click', function () {
						const reason = modal.querySelector(
							'#forumsReportReason'
						).value;
						const submitBtn = this;
						submitBtn.disabled = true;
						submitBtn.textContent = '...';

						if (reportModalObj && reportModalObj.onSubmit) {
							reportModalObj.onSubmit(
								reason,
								() => {
									modal.style.display = 'none';
									modal.classList.remove('show');
									submitBtn.disabled = false;
									submitBtn.textContent =
										messageDetail.dataset.labelReport ||
										'Report';
								},
								() => {
									submitBtn.disabled = false;
									submitBtn.textContent =
										messageDetail.dataset.labelReport ||
										'Report';
								}
							);
						}
					});
			}

			/* Reset dropdown to first option each time the modal is opened */
			const selectEl = modal.querySelector('#forumsReportReason');
			if (selectEl) {
				selectEl.selectedIndex = 0;
			}

			reportModalObj = {onSubmit};

			modal.style.display = 'block';
			setTimeout(() => {
				modal.classList.add('show');
			}, 10);
		}

		/* Flag message handler */
		if (flagBtn) {
			flagBtn.addEventListener('click', (event) => {
				event.preventDefault();

				/* Already flagged – do nothing */
				if (flagBtn.disabled) {
					return;
				}

				/* Close the options dropdown */
				const optionsMenu = flagBtn.closest('.dropdown-menu');
				if (optionsMenu) {
					optionsMenu.classList.remove('show');
				}

				showReportModal((reason, onSuccess, onError) => {

					/* addOrUpdate pattern: PATCH if a flag exists, POST if not */
					let flagUrl;
					let flagMethod;
					let flagBody;
					if (existingFlagId) {
						flagUrl =
							portalURL +
							'/o/c/forumsuspiciousactivities/' +
							existingFlagId;
						flagMethod = 'PATCH';
						flagBody = JSON.stringify({reason});
					}
					else {
						flagUrl =
							portalURL +
							'/o/c/forumsuspiciousactivities/scopes/' +
							scopeGroupId;
						flagMethod = 'POST';
						flagBody = JSON.stringify({
							r_threadSuspiciousActivities_c_forumThreadId:
								parseInt(messageId, 10),
							reason,
							suspiciousMessageId: parseInt(messageId, 10),
						});
					}

					Liferay.Util.fetch(flagUrl, {
						body: flagBody,
						headers,
						method: flagMethod,
					})
						.then((r) => {
							if (r.ok) {
								return r.json().then((body) => {
									existingFlagId = body.id;
									onSuccess();
									flagBtn.textContent =
										messageDetail.dataset.labelFlagged ||
										'Flagged';
									flagBtn.classList.add('disabled');
									flagBtn.disabled = true;

									/* Standard Liferay toast feedback */
									if (
										Liferay.Util &&
										Liferay.Util.openToast
									) {
										Liferay.Util.openToast({
											message:
												messageDetail.dataset
													.labelReportSubmitted ||
												'Thank you! Your report has been submitted.',
											type: 'success',
										});
									}
								});
							}
							else {
								onError();
								console.error('Report submission failed');
							}
						})
						.catch((error) => {
							onError();
							console.error('Report error:', error);
						});
				});
			});
		}

		/* Reply button - open compose modal in reply mode */
		if (replyBtn) {
			replyBtn.addEventListener('click', () => {
				if (typeof window.forumsOpenComposeModal === 'function') {
					window.forumsOpenComposeModal({
						messageId,
						subject: messageTitleText,
					});
				}
				else {
					alert(
						messageDetail.dataset.labelReplyFormNotFound ||
							'Reply form not found on this page. Please add the forums-message-composer fragment.'
					);
				}
			});
		}

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
					initMessageDetail();
				})
				.catch((error) => {
					console.error('Error checking ban status', error);
					initMessageDetail();
				});
		}
		else {
			initMessageDetail();
		}
	}; // end runMessageDetail

	/* Resolve messageId: ?messageId param → mapped reply ERC → mapped message ERC → URL path slug */
	if (messageId) {
		runMessageDetail(messageId, null);
	}
	else {

		/* Reply ERC takes priority — set when this fragment is on a Forum Message Display Page */
		const replyErcEl = messageDetail.querySelector('#forumsDetailReplyERC');
		let replyErc = replyErcEl ? replyErcEl.textContent.trim() : null;
		if (replyErc === 'Mappable Reply ERC') {
			replyErc = null;
		}

		if (replyErc) {
			Liferay.Util.fetch(
				portalURL +
					'/o/c/forummessages/scopes/' +
					scopeGroupId +
					'/by-external-reference-code/' +
					encodeURIComponent(replyErc),
				{
					headers,
					method: 'GET',
				}
			)
				.then((r) => {
					if (!r.ok) {
						throw new Error('Not found');
					}

					return r.json();
				})
				.then((reply) => {
					const parentMessageId =
						reply.r_threadMessages_c_forumThreadId;
					runMessageDetail(
						parentMessageId ? String(parentMessageId) : null,
						reply.id ? String(reply.id) : null
					);
				})
				.catch(() => {
					runMessageDetail(null, null);
				});
		}
		else {
			const ercEl = messageDetail.querySelector('#forumsDetailERC');
			let erc = ercEl ? ercEl.textContent.trim() : null;
			if (erc === 'Mappable Message ERC') {
				erc = null;
			}

			if (!erc) {
				if (loadingEl) {
					loadingEl.innerHTML =
						'<div class="forums-message-list__empty text-secondary text-center py-5">' +
						(messageDetail.dataset.labelErcNotMapped ||
							'Message ERC is not mapped.') +
						'</div>';
				}
			}
			else {
				Liferay.Util.fetch(
					portalURL +
						'/o/c/forumthreads/scopes/' +
						scopeGroupId +
						'/by-external-reference-code/' +
						encodeURIComponent(erc),
					{
						headers,
						method: 'GET',
					}
				)
					.then((r) => {
						if (!r.ok) {
							throw new Error('Not found');
						}

						return r.json();
					})
					.then((data) => {
						runMessageDetail(
							data.id ? String(data.id) : null,
							null
						);
					})
					.catch(() => {
						runMessageDetail(null, null);
					});
			}
		}
	}
}
