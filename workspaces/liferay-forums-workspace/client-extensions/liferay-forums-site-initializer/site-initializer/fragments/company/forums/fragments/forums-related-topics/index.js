/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const relatedTopics = fragmentElement.querySelector('#forumsRelatedTopics');

if (relatedTopics && !document.body.classList.contains('has-edit-mode-menu')) {
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
	};

	const listEl = relatedTopics.querySelector('#forumsRelatedTopicsList');
	const loadingEl = relatedTopics.querySelector(
		'#forumsRelatedTopicsLoading'
	);

	/* URL params */
	const urlParams = new URLSearchParams(window.location.search);
	let currentMessageId = urlParams.get('messageId');

	const runRelatedTopics = function (resolvedMessageId) {
		currentMessageId = resolvedMessageId;

		if (!currentMessageId) {
			if (loadingEl) {
				loadingEl.remove();
			}
			relatedTopics.style.display = 'none';

			return;
		}

		/* First, get the current message to find its category */
		Liferay.Util.fetch(
			portalURL + '/o/c/forumthreads/' + currentMessageId,
			{
				headers,
				method: 'GET',
			}
		)
			.then((r) => {
				return r.json();
			})
			.then((msg) => {
				const categoryId = msg.r_categoryThreads_c_forumCategoryId;

				/* Fetch other messages from the same category */
				const filterParts = [];
				if (categoryId) {
					filterParts.push(
						"r_categoryThreads_c_forumCategoryId eq '" +
							categoryId +
							"'"
					);
				}

				let url =
					portalURL +
					'/o/c/forumthreads/scopes/' +
					scopeGroupId +
					'?pageSize=6&sort=lastPostDate:desc&nestedFields=threadSuspiciousActivities';
				if (filterParts.length) {
					url +=
						'&filter=' +
						encodeURIComponent(filterParts.join(' and '));
				}

				return Liferay.Util.fetch(url, {headers, method: 'GET'});
			})
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				if (loadingEl) {
					loadingEl.remove();
				}

				const items = (data.items || [])
					.filter(({id}) => {
						return String(id) !== String(currentMessageId);
					})
					.slice(0, 5);

				if (!items.length) {
					listEl.innerHTML =
						'<div class="text-secondary text-center py-2">' +
						(relatedTopics.dataset.labelNoRelated ||
							'No related topics found.') +
						'</div>';

					return;
				}

				let html = '';
				let missingDisplayPage = false;
				items.forEach(
					({
						friendlyUrlPath,
						messageTitle,
						scopeKey,
						threadSuspiciousActivities,
					}) => {
						const title =
							messageTitle ||
							relatedTopics.dataset.labelUntitled ||
							'Untitled';
						let isFlagged = false;
						const suspiciousActivities =
							threadSuspiciousActivities || [];
						for (const {validated} of suspiciousActivities) {
							if (validated === true) {
								isFlagged = true;
								break;
							}
						}

						let flaggedBadge = '';
						if (isFlagged) {
							const flaggedText =
								relatedTopics.dataset.labelFlagged || 'Flagged';
							flaggedBadge =
								'<span class="text-danger ml-2" style="font-size:0.85em"><svg class="lexicon-icon lexicon-icon-warning-full" role="presentation" viewBox="0 0 16 16" fill="currentColor" width="12" height="12"><path d="M16 14.5L8 1 0 14.5h16zM8 13c-.6 0-1-.4-1-1s.4-1 1-1 1 .4 1 1-.4 1-1 1zm1-3H7V6h2v4z"/></svg> ' +
								flaggedText +
								'</span>';
						}

						if (friendlyUrlPath) {
							const siteSlug = (scopeKey || '')
								.toLowerCase()
								.replace(/ /g, '-');
							const messageHref =
								Liferay.ThemeDisplay.getPathFriendlyURLPublic() +
								'/' +
								siteSlug +
								'/c_forumthread/' +
								friendlyUrlPath;
							html +=
								'<a href="' +
								messageHref +
								'" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">' +
								'<span>' +
								Liferay.Util.escapeHTML(title) +
								'</span>' +
								flaggedBadge +
								'</a>';
						}
						else {
							missingDisplayPage = true;
							html +=
								'<div class="list-group-item d-flex justify-content-between align-items-center">' +
								'<span>' +
								Liferay.Util.escapeHTML(title) +
								'</span>' +
								flaggedBadge +
								'</div>';
						}
					}
				);

				listEl.innerHTML = html;

				if (
					missingDisplayPage &&
					Liferay.Util &&
					Liferay.Util.openToast
				) {
					Liferay.Util.openToast({
						message:
							relatedTopics.dataset
								.labelDisplayPageNotConfigured ||
							'Display page is not configured for one or more messages.',
						type: 'danger',
					});
				}
			})
			.catch((error) => {
				if (loadingEl) {
					loadingEl.remove();
				}
				listEl.innerHTML =
					'<div class="text-secondary text-center py-2">' +
					(relatedTopics.dataset.labelUnableToLoad ||
						'Unable to load related topics.') +
					'</div>';
				console.error('ForumsRelatedTopics error:', error);
			});
	}; // end runRelatedTopics

	/* Resolve messageId: ?messageId param → mapped reply ERC → mapped message ERC → URL path slug */
	if (currentMessageId) {
		runRelatedTopics(currentMessageId);
	}
	else {

		/* Reply ERC takes priority — set when this fragment is on a Forum Message Display Page */
		const replyErcEl = relatedTopics.querySelector(
			'#forumsRelatedTopicsReplyERC'
		);
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
					runRelatedTopics(
						parentMessageId ? String(parentMessageId) : null
					);
				})
				.catch(() => {
					runRelatedTopics(null);
				});
		}
		else {
			const ercEl = relatedTopics.querySelector(
				'#forumsRelatedTopicsERC'
			);
			let erc = ercEl ? ercEl.textContent.trim() : null;
			if (erc === 'Mappable Message ERC') {
				erc = null;
			}

			if (!erc) {
				if (loadingEl) {
					loadingEl.remove();
				}
				listEl.innerHTML =
					'<div class="text-secondary text-center py-2">' +
					(relatedTopics.dataset.labelErcNotMapped ||
						'Message ERC is not mapped.') +
					'</div>';
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
						runRelatedTopics(data.id ? String(data.id) : null);
					})
					.catch(() => {
						runRelatedTopics(null);
					});
			}
		}
	}
}
