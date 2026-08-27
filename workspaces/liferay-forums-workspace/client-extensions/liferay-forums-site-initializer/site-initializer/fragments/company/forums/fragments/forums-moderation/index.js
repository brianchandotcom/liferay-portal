/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const forumsMod = fragmentElement.querySelector('#forumsModeration');

if (forumsMod) {
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
	};

	const buildMessageHref = function (messageData) {
		const {friendlyUrlPath, scopeKey} = messageData || {};
		if (friendlyUrlPath) {
			const siteSlug = (scopeKey || '').toLowerCase().replace(/ /g, '-');

			return (
				Liferay.ThemeDisplay.getPathFriendlyURLPublic() +
				'/' +
				siteSlug +
				'/c_forumthread/' +
				friendlyUrlPath
			);
		}

		return null;
	};
	const cardEl = forumsMod.querySelector('.forums-moderation__card');
	const noPermissionsEl = forumsMod.querySelector('#forumsModNoPermissions');
	const loadingEl = forumsMod.querySelector('#forumsModLoading');
	const flagList = forumsMod.querySelector('#forumsModFlagList');
	const paginationNav = forumsMod.querySelector('#forumsModPagination');
	const paginationUl = forumsMod.querySelector('#forumsModPaginationUl');

	let currentFilter = 'pending'; /* 'pending' | 'validated' | 'all' */
	let currentPage = 1;
	const pageSize = 20;

	/* Reason labels map */
	const reasonLabels = {
		'harassment-bullying':
			forumsMod.dataset.labelHarassmentBullying ||
			'Harassment or Bullying',
		'harmful-dangerous-acts':
			forumsMod.dataset.labelHarmfulDangerousActs ||
			'Harmful Dangerous Acts',
		'nudity-sexual-content':
			forumsMod.dataset.labelNuditySexualContent ||
			'Nudity or Sexual Content',
		'other': forumsMod.dataset.labelOther || 'Other',
		'spam': forumsMod.dataset.labelSpam || 'Spam',
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

	const formatDate = function (dateStr) {
		if (!dateStr) {
			return '';
		}
		const d = new Date(dateStr);

		return d.toLocaleDateString('en-US', {
			day: 'numeric',
			hour: '2-digit',
			minute: '2-digit',
			month: 'short',
			year: 'numeric',
		});
	};

	const getReasonLabel = function (reason) {
		return (
			reasonLabels[reason] ||
			reason ||
			forumsMod.dataset.labelOther ||
			'Other'
		);
	};

	const getReasonBadgeClass = function (reason) {
		if (reason === 'spam') {
			return 'label label-warning';
		}
		if (
			reason === 'harmful-dangerous-acts' ||
			reason === 'harassment-bullying' ||
			reason === 'nudity-sexual-content'
		) {
			return 'label label-danger';
		}

		return 'label label-secondary';
	};

	const showConfirmModal = function (message, confirmLabel, onConfirm) {
		const existing = document.getElementById('forumsModConfirmModal');
		if (existing) {
			existing.remove();
		}

		const modal = document.createElement('div');
		modal.id = 'forumsModConfirmModal';
		modal.className = 'modal';
		modal.style.display = 'flex';
		modal.style.backgroundColor = 'rgba(0,0,0,0.5)';
		modal.style.zIndex = '1050';
		modal.setAttribute('tabindex', '-1');
		modal.setAttribute('role', 'dialog');
		modal.setAttribute('aria-modal', 'true');
		modal.setAttribute('aria-labelledby', 'forumsModConfirmHeading');

		modal.innerHTML = `
			<div class="modal-dialog modal-dialog-sm modal-dialog-centered modal-danger">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title" tabindex="-1">
							<div class="modal-title-indicator">
								<svg class="lexicon-icon lexicon-icon-exclamation-full" role="presentation"><use href="${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg#exclamation-full"></use></svg>
							</div>
							<span id="forumsModConfirmHeading">${Liferay.Util.escapeHTML(confirmLabel)}</span>
						</h1>
						<button class="close btn btn-unstyled" type="button" id="forumsModConfirmClose" aria-label="${Liferay.Util.escapeHTML(forumsMod.dataset.labelCancel || 'Cancel')}">
							<svg class="lexicon-icon lexicon-icon-times" focusable="false" role="presentation"><use href="${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg#times"></use></svg>
						</button>
					</div>
					<div class="modal-body">
						<div class="liferay-modal-body">${Liferay.Util.escapeHTML(message)}</div>
					</div>
					<div class="modal-footer">
						<div class="modal-item-last">
							<div class="btn-group-spaced" role="group">
								<button class="btn btn-secondary" type="button" id="forumsModConfirmCancel">${Liferay.Util.escapeHTML(forumsMod.dataset.labelCancel || 'Cancel')}</button>
								<button class="btn btn-danger" type="button" id="forumsModConfirmOk">${Liferay.Util.escapeHTML(confirmLabel)}</button>
							</div>
						</div>
					</div>
				</div>
			</div>`;

		document.body.appendChild(modal);
		const previousFocus = document.activeElement;

		function onKeydown(event) {
			if (event.key === 'Escape') {
				closeModal();
			}
		}

		function closeModal() {
			document.removeEventListener('keydown', onKeydown);
			modal.remove();
			if (previousFocus) {
				previousFocus.focus();
			}
		}

		modal
			.querySelector('#forumsModConfirmCancel')
			.addEventListener('click', closeModal);
		modal
			.querySelector('#forumsModConfirmClose')
			.addEventListener('click', closeModal);
		modal
			.querySelector('#forumsModConfirmOk')
			.addEventListener('click', () => {
				closeModal();
				onConfirm();
			});
		modal.addEventListener('click', (event) => {
			if (event.target === modal) {
				closeModal();
			}
		});
		document.addEventListener('keydown', onKeydown);

		modal.querySelector('.modal-title').focus();
	};

	const showToast = function (message) {
		if (Liferay.Util && Liferay.Util.openToast) {
			Liferay.Util.openToast({message, type: 'success'});
		}
	};

	/* Tab click handlers */
	forumsMod.querySelectorAll('#forumsModTabs .nav-link').forEach((tab) => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			forumsMod
				.querySelectorAll('#forumsModTabs .nav-link')
				.forEach((t) => {
					t.classList.remove('active');
					t.setAttribute('aria-selected', 'false');
				});
			this.classList.add('active');
			this.setAttribute('aria-selected', 'true');
			if (flagList) {
				flagList.setAttribute('aria-labelledby', this.id);
			}
			currentFilter = this.dataset.filter;
			currentPage = 1;
			if (currentFilter === 'bans') {
				loadBans();
			}
			else {
				loadFlags();
			}
		});
	});

	const renderPagination = function (lastPage, loadFunction) {
		if (lastPage > 1 && paginationNav && paginationUl) {
			paginationNav.style.display = '';
			let pagHtml = '';

			pagHtml +=
				'<li class="page-item' +
				(currentPage <= 1 ? ' disabled' : '') +
				'">' +
				'<a class="page-link" href="#" data-page="' +
				(currentPage - 1) +
				'" aria-label="Previous page"><span aria-hidden="true">&laquo;</span></a></li>';

			for (let p = 1; p <= lastPage && p <= 10; p++) {
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

			pagHtml +=
				'<li class="page-item' +
				(currentPage >= lastPage ? ' disabled' : '') +
				'">' +
				'<a class="page-link" href="#" data-page="' +
				(currentPage + 1) +
				'" aria-label="Next page"><span aria-hidden="true">&raquo;</span></a></li>';

			paginationUl.innerHTML = pagHtml;

			paginationUl.querySelectorAll('.page-link').forEach((link) => {
				link.addEventListener('click', function (event) {
					event.preventDefault();
					const p = parseInt(this.dataset.page, 10);
					if (p >= 1 && p <= lastPage) {
						currentPage = p;
						loadFunction();
					}
				});
			});
		}
		else if (paginationNav) {
			paginationNav.style.display = 'none';
		}
	};

	const loadBans = function () {
		if (loadingEl) {
			loadingEl.style.display = 'block';
		}
		flagList.innerHTML = '';
		if (paginationNav) {
			paginationNav.style.display = 'none';
		}

		const url =
			portalURL +
			'/o/c/forumbans/scopes/' +
			scopeGroupId +
			'?sort=dateCreated:desc' +
			'&page=' +
			currentPage +
			'&pageSize=' +
			pageSize;

		Liferay.Util.fetch(url, {headers, method: 'GET'})
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				if (loadingEl) {
					loadingEl.style.display = 'none';
				}
				const items = data.items || [];
				const lastPage = data.lastPage || 1;

				if (!items.length) {
					flagList.innerHTML =
						'<div class="list-group-item text-secondary">' +
						(forumsMod.dataset.labelNoBans || 'No bans found.') +
						'</div>';

					return;
				}

				items.forEach((ban) => {
					const {actions, banUserId, dateCreated} = ban;

					const item = document.createElement('div');
					item.className =
						'list-group-item forums-moderation__flag-item';
					const infoDiv = document.createElement('div');
					infoDiv.className = 'forums-moderation__flag-info';
					const titleLink = document.createElement('span');
					titleLink.className =
						'forums-moderation__message-title font-weight-bold';
					titleLink.textContent = (
						forumsMod.dataset.labelUserId || 'User ID: {0}'
					).replace('{0}', banUserId);
					const metaDiv = document.createElement('div');
					metaDiv.className =
						'forums-moderation__flag-meta text-secondary small mt-1';
					const dateSpan = document.createElement('span');
					dateSpan.textContent = (
						forumsMod.dataset.labelBannedOn || 'Banned on: {0}'
					).replace('{0}', formatDate(dateCreated));
					metaDiv.appendChild(dateSpan);
					infoDiv.appendChild(titleLink);
					infoDiv.appendChild(metaDiv);
					const actionsDiv = document.createElement('div');
					actionsDiv.className = 'forums-moderation__flag-actions';
					if (actions && actions['delete']) {
						const revokeBtn = document.createElement('button');
						revokeBtn.className = 'btn btn-sm btn-outline-success';
						revokeBtn.textContent =
							forumsMod.dataset.labelRevokeBan || 'Revoke Ban';
						revokeBtn.addEventListener('click', () => {
							const message =
								forumsMod.dataset.labelConfirmRevokeBan ||
								'Are you sure you want to revoke this ban?';
							showConfirmModal(
								message,
								forumsMod.dataset.labelRevokeBan ||
									'Revoke Ban',
								() => {
									revokeBtn.disabled = true;
									Liferay.Util.fetch(actions['delete'].href, {
										headers,
										method: 'DELETE',
									})
										.then((r) => {
											if (r.ok) {
												item.style.opacity = '0.5';
												setTimeout(() => {
													item.remove();
													showToast(
														forumsMod.dataset
															.labelBanRevokedSuccessfully ||
															'Ban revoked successfully.'
													);
													if (
														!flagList.children
															.length
													) {
														loadBans();
													}
												}, 300);
											}
										})
										.catch((event) => {
											revokeBtn.disabled = false;
											console.error(event);
										});
								}
							);
						});
						actionsDiv.appendChild(revokeBtn);
					}
					item.appendChild(infoDiv);
					item.appendChild(actionsDiv);
					flagList.appendChild(item);

					/* Names come from the forum's own Forum User object rather than
					   from the user account API, so reading the ban list needs no
					   permission over user accounts. Someone who has never posted
					   here has no row, and the entry keeps showing its id. */
					Liferay.Util.fetch(
						portalURL +
							'/o/c/forumusers/scopes/' +
							scopeGroupId +
							'?fields=firstName,lastName,screenName&pageSize=1&filter=' +
							encodeURIComponent('forumUserId eq ' + banUserId),
						{headers, method: 'GET'}
					)
						.then((r) => {
							return r.json();
						})
						.then((data) => {
							const forumUser = (data.items || [])[0];
							if (!forumUser) {
								return;
							}
							const n =
								[forumUser.firstName, forumUser.lastName]
									.filter(Boolean)
									.join(' ') || forumUser.screenName;
							if (n) {
								titleLink.textContent =
									n + ' (ID: ' + banUserId + ')';
							}
						})
						.catch(() => {});
				});
				renderPagination(lastPage, loadBans);
			})
			.catch((error) => {
				if (loadingEl) {
					loadingEl.style.display = 'none';
				}
				console.error('Bans load error:', error);
			});
	};

	const buildFilterParam = function () {
		if (currentFilter === 'pending') {
			return '&filter=' + encodeURIComponent('validated eq false');
		}
		else if (currentFilter === 'validated') {
			return '&filter=' + encodeURIComponent('validated eq true');
		}

		return ''; /* 'all' — no filter */
	};

	const loadFlags = function () {
		if (loadingEl) {
			loadingEl.style.display = 'block';
		}
		flagList.innerHTML = '';
		if (paginationNav) {
			paginationNav.style.display = 'none';
		}

		const url =
			portalURL +
			'/o/c/forumsuspiciousactivities/scopes/' +
			scopeGroupId +
			'?nestedFields=threadSuspiciousActivities' +
			'&sort=dateCreated:desc' +
			'&page=' +
			currentPage +
			'&pageSize=' +
			pageSize +
			buildFilterParam();

		Liferay.Util.fetch(url, {
			headers,
			method: 'GET',
		})
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				if (loadingEl) {
					loadingEl.style.display = 'none';
				}

				/* HATEOAS: check collection-level actions for write permission */
				const {actions} = data;
				const hasPermission = !!(
					actions &&
					(actions['create'] || actions['post'] || actions['POST'])
				);

				if (hasPermission) {

					/* User has moderation permissions — show the card */
					if (noPermissionsEl) {
						noPermissionsEl.style.display = 'none';
					}
					if (cardEl) {
						cardEl.style.display = '';
					}
				}
				else {

					/* Non-privileged user — show the OOTB permissions warning */
					if (cardEl) {
						cardEl.style.display = 'none';
					}
					if (noPermissionsEl) {
						noPermissionsEl.style.display = '';
					}

					return;
				}

				const items = data.items || [];
				const lastPage = data.lastPage || 1;

				if (!items.length) {
					flagList.innerHTML =
						'<div class="list-group-item text-secondary">' +
						(forumsMod.dataset.labelNoFlags ||
							'No flagged messages found.') +
						'</div>';

					return;
				}

				let missingDisplayPage = false;
				items.forEach((flag) => {
					const {
						actions: flagActions,
						creator: flagCreator,
						dateCreated,
						id: flagId,
						r_threadSuspiciousActivities_c_forumThreadId,
						reason: flagReason,
						suspiciousMessageId,
						threadSuspiciousActivities,
						validated,
					} = flag;

					const messageData = threadSuspiciousActivities || {};
					const {
						creator: messageCreator,
						messageTitle: dataTitle,
						title: dataAltTitle,
					} = messageData;
					const messageTitle =
						dataTitle ||
						dataAltTitle ||
						'Message #' +
							(suspiciousMessageId ||
								r_threadSuspiciousActivities_c_forumThreadId ||
								'?');
					const authorId = messageCreator ? messageCreator.id : null;
					const creator = flagCreator || {};
					const creatorName = displayName(creator) || 'Unknown';
					const reason = flagReason || 'other';
					const isValidated = validated === true;
					const date = formatDate(dateCreated);

					const item = document.createElement('div');
					item.className =
						'list-group-item forums-moderation__flag-item';

					/* Info column */
					const infoDiv = document.createElement('div');
					infoDiv.className = 'forums-moderation__flag-info';

					const titleHref = buildMessageHref(messageData);
					if (!titleHref) {
						missingDisplayPage = true;
					}

					const titleLink = document.createElement('a');
					titleLink.className = 'forums-moderation__message-title';
					titleLink.textContent = messageTitle;
					if (titleHref) {
						titleLink.href = titleHref;
					}
					titleLink.target = '_blank';
					titleLink.title =
						forumsMod.dataset.labelViewMessage || 'View Message';

					const metaDiv = document.createElement('div');
					metaDiv.className =
						'forums-moderation__flag-meta text-secondary small';

					/* Reporter */
					const reportedByTmpl =
						forumsMod.dataset.labelReportedBy || 'Reported by {0}';
					const reporterSpan = document.createElement('span');
					reporterSpan.textContent = reportedByTmpl.replace(
						'{0}',
						creatorName
					);

					/* Date */
					const dateSpan = document.createElement('span');
					dateSpan.textContent = date;

					/* Reason badge */
					const reasonBadge = document.createElement('span');
					reasonBadge.className = getReasonBadgeClass(reason);
					reasonBadge.textContent = getReasonLabel(reason);

					/* Status badge */
					const statusBadge = document.createElement('span');
					statusBadge.className =
						'label ' +
						(isValidated ? 'label-success' : 'label-warning');
					statusBadge.textContent = isValidated
						? forumsMod.dataset.labelValidated || 'Validated'
						: forumsMod.dataset.labelPending || 'Pending';

					metaDiv.appendChild(reporterSpan);
					metaDiv.appendChild(dateSpan);
					metaDiv.appendChild(reasonBadge);
					metaDiv.appendChild(statusBadge);

					infoDiv.appendChild(titleLink);
					infoDiv.appendChild(metaDiv);

					/* Actions column */
					const actionsDiv = document.createElement('div');
					actionsDiv.className = 'forums-moderation__flag-actions';

					/* View link — always shown (read-only) */
					const viewLink = document.createElement('a');
					viewLink.className =
						'btn btn-sm btn-outline-primary' +
						(titleHref ? '' : ' disabled');
					viewLink.textContent =
						forumsMod.dataset.labelViewMessage || 'View Message';
					if (titleHref) {
						viewLink.href = titleHref;
					}
					viewLink.target = '_blank';
					actionsDiv.appendChild(viewLink);

					/* HATEOAS: only render Validate button if the item has update/patch actions */
					if (
						flagActions &&
						(flagActions['update'] ||
							flagActions['patch'] ||
							flagActions['PUT'])
					) {
						const {PUT, patch, update} = flagActions;
						const patchHref =
							(patch && patch.href) ||
							(update && update.href) ||
							(PUT && PUT.href) ||
							portalURL +
								'/o/c/forumsuspiciousactivities/' +
								flagId;

						const validateBtn = document.createElement('button');
						validateBtn.className = isValidated
							? 'btn btn-sm btn-outline-secondary'
							: 'btn btn-sm btn-outline-success';
						validateBtn.textContent = isValidated
							? forumsMod.dataset.labelPending || 'Pending'
							: forumsMod.dataset.labelValidate || 'Validate';
						validateBtn.addEventListener(
							'click',
							(function (flagId, flagHref, validated) {
								return function () {
									const button = this;
									button.disabled = true;
									Liferay.Util.fetch(flagHref, {
										body: JSON.stringify({
											validated: !validated,
										}),
										headers,
										method: 'PATCH',
									})
										.then((r) => {
											if (r.ok) {
												showToast(
													forumsMod.dataset
														.labelFlagValidated ||
														'Flag has been validated.'
												);
												loadFlags();
											}
											else {
												button.disabled = false;
												console.error(
													'Validate failed'
												);
											}
										})
										.catch((error) => {
											button.disabled = false;
											console.error(
												'Validate error:',
												error
											);
										});
								};
							})(flagId, patchHref, isValidated)
						);
						actionsDiv.appendChild(validateBtn);
					}

					/* Ban Author button (if validated and has author) */
					if (isValidated && authorId) {
						const banBtn = document.createElement('button');
						banBtn.className = 'btn btn-sm btn-outline-danger';
						banBtn.textContent =
							forumsMod.dataset.labelBanAuthor || 'Ban Author';
						banBtn.addEventListener('click', () => {
							const message =
								forumsMod.dataset.labelConfirmBanUser ||
								'Are you sure you want to ban this user?';
							showConfirmModal(
								message,
								forumsMod.dataset.labelBanAuthor ||
									'Ban Author',
								() => {
									banBtn.disabled = true;
									Liferay.Util.fetch(
										portalURL +
											'/o/c/forumbans/scopes/' +
											scopeGroupId,
										{
											body: JSON.stringify({
												banUserId: parseInt(
													authorId,
													10
												),
											}),
											headers,
											method: 'POST',
										}
									)
										.then((r) => {
											if (r.ok) {
												banBtn.style.display = 'none';
												showToast(
													forumsMod.dataset
														.labelUserBanned ||
														'User has been banned.'
												);
											}
											else {
												banBtn.disabled = false;
												console.error('Ban failed');
											}
										})
										.catch((event) => {
											banBtn.disabled = false;
											console.error(event);
										});
								}
							);
						});
						actionsDiv.appendChild(banBtn);
					}

					/* HATEOAS: only render Dismiss button if the item has delete action */
					if (flagActions && flagActions['delete']) {
						const deleteHref =
							flagActions['delete'].href ||
							portalURL +
								'/o/c/forumsuspiciousactivities/' +
								flagId;

						const dismissBtn = document.createElement('button');
						dismissBtn.className = 'btn btn-sm btn-outline-danger';
						dismissBtn.textContent =
							forumsMod.dataset.labelDismiss || 'Dismiss';
						dismissBtn.addEventListener(
							'click',
							(function (flagItem, flagDeleteHref) {
								return function () {
									const message =
										forumsMod.dataset.labelConfirmDismiss ||
										'Are you sure you want to dismiss this flag?';
									showConfirmModal(
										message,
										forumsMod.dataset.labelDismiss ||
											'Dismiss',
										() => {
											const button = dismissBtn;
											button.disabled = true;
											Liferay.Util.fetch(flagDeleteHref, {
												headers,
												method: 'DELETE',
											})
												.then((r) => {
													if (r.ok) {
														flagItem.style.opacity =
															'0.5';
														setTimeout(() => {
															flagItem.remove();
															showToast(
																forumsMod
																	.dataset
																	.labelFlagDismissed ||
																	'Flag has been dismissed.'
															);

															/* Reload if list is now empty */
															if (
																!flagList.querySelectorAll(
																	'.forums-moderation__flag-item'
																).length
															) {
																loadFlags();
															}
														}, 300);
													}
													else {
														button.disabled = false;
														console.error(
															'Dismiss failed'
														);
													}
												})
												.catch((error) => {
													button.disabled = false;
													console.error(
														'Dismiss error:',
														error
													);
												});
										}
									);
								};
							})(item, deleteHref)
						);
						actionsDiv.appendChild(dismissBtn);
					}

					item.appendChild(infoDiv);
					item.appendChild(actionsDiv);
					flagList.appendChild(item);
				});

				if (
					missingDisplayPage &&
					Liferay.Util &&
					Liferay.Util.openToast
				) {
					Liferay.Util.openToast({
						message:
							forumsMod.dataset.labelDisplayPageNotConfigured ||
							'Display page is not configured for one or more messages.',
						type: 'danger',
					});
				}

				/* Pagination */
				renderPagination(lastPage, loadFlags);
			})
			.catch((error) => {
				if (loadingEl) {
					loadingEl.style.display = 'none';
				}

				/* On error (e.g. 403), show the permissions warning */
				if (cardEl) {
					cardEl.style.display = 'none';
				}
				if (noPermissionsEl) {
					noPermissionsEl.style.display = '';
				}
				console.error('Moderation load error:', error);
			});
	};

	loadFlags();
}
