/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const messageComposer = fragmentElement.querySelector('#forumsMessageComposer');

/* Category query string. pageSize and sort come from fragment configuration;
   a blank sort omits the parameter entirely, which is needed on databases
   that cannot sort on a Text object field (Hypersonic raises "data type cast
   needed for parameter or null literal"). */
function categoryQuery(dataset) {
	const size = dataset.categoryPageSize || '50';
	const sort = (dataset.categorySort || '').trim();
	return '?pageSize=' + encodeURIComponent(size)
		+ (sort ? '&sort=' + encodeURIComponent(sort) : '');
}

if (messageComposer) {
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const currentUserId = Liferay.ThemeDisplay.getUserId();
	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json'
	};

	/* DOM refs */
	const modal = messageComposer.querySelector('#forumsMessageComposerModal');
	const backdrop = messageComposer.querySelector('#forumsMessageComposerBackdrop');
	const closeBtn = messageComposer.querySelector('#forumsMessageComposerCloseBtn');
	const form = messageComposer.querySelector('#forumsMessageComposerForm');
	const titleEl = messageComposer.querySelector('#forumsMessageComposerTitle');
	const categorySelect = messageComposer.querySelector('#forumsMessageComposerCategory');
	const categoryGroup = messageComposer.querySelector('#forumsMessageComposerCategoryGroup');
	const subjectInput = messageComposer.querySelector('#forumsMessageComposerSubject');
	const subjectGroup = messageComposer.querySelector('#forumsMessageComposerSubjectGroup');
	const questionCheck = messageComposer.querySelector('#forumsMessageComposerQuestion');
	const questionGroup = messageComposer.querySelector('#forumsMessageComposerQuestionGroup');
	const prioritySelect = messageComposer.querySelector('#forumsMessageComposerPriority');
	const priorityGroup = messageComposer.querySelector('#forumsMessageComposerPriorityGroup');
	const subscribeCheck = messageComposer.querySelector('#forumsMessageComposerSubscribe');
	const subscribeGroup = messageComposer.querySelector('#forumsMessageComposerSubscribeGroup');
	const tagsGroup = messageComposer.querySelector('#forumsMessageComposerTagsGroup');
	const leftCol = messageComposer.querySelector('#forumsMessageComposerLeftCol');
	const tagsInput = messageComposer.querySelector('#forumsMessageComposerTagsInput');
	const tagsList = messageComposer.querySelector('#forumsMessageComposerTagsList');
	const bodyLabel = messageComposer.querySelector('#forumsMessageComposerBodyLabel');
	const bodyError = messageComposer.querySelector('#forumsMessageComposerBodyError');
	const submitBtn = messageComposer.querySelector('#forumsMessageComposerSubmit');
	const cancelBtn = messageComposer.querySelector('#forumsMessageComposerCancel');
	const successAlert = messageComposer.querySelector('#forumsMessageComposerSuccess');
	const errorAlert = messageComposer.querySelector('#forumsMessageComposerError');
	const attachBtn = messageComposer.querySelector('#forumsMessageComposerAttachBtn');
	const fileInput = messageComposer.querySelector('#forumsMessageComposerFileInput');
	const pendingFilesEl = messageComposer.querySelector('#forumsMessageComposerPendingFiles');
	const existingFilesEl = messageComposer.querySelector('#forumsMessageComposerExistingFiles');

	/* Files staged for the next post. Uploaded as ForumMessageAttachment rows once
	   the message is created/edited (see uploadAttachments). Matches the object
	   field's maximumFileSize (10 MB) so we can reject oversized files up front. */
	let stagedFiles = [];
	const MAX_FILE_SIZE = 10 * 1024 * 1024;

	/* Edit mode only: the message's already-uploaded attachments, and the ids the
	   user has marked for removal. Removals are staged (like new files) and applied
	   on Save, so Cancel reverts. Only attachments the current user uploaded show a
	   remove control (deletion is also enforced server-side by object ownership). */
	let existingAttachments = [];
	let removedAttachmentIds = [];

	/* CKEditor instance tracking */
	const editorName = fragmentElementId + '-forumsMessageComposerBody';
	let bodyEditorInstance = null;

	const editorPromise = new Promise(function(resolve) {
		function matchesName(editor) {
			if (!editor) {
				return false;
			}
			const {config, name: instanceName} = editor;
			const name = instanceName ||
				(config && (typeof config.get === 'function') &&
					config.get('name'));
			return name === editorName;
		}

		/* Resolve as soon as the editor is ready, regardless of which editor
		   the server actually rendered. The LPD-11235 client flag and the
		   rendered editor can disagree (e.g. the flag is toggled at runtime
		   without restarting the server, so the server still renders the
		   legacy editor while the client behaves as if CKEditor 5 is active).
		   To stay correct either way we listen for BOTH the CKEditor 5
		   "ckeditor:ready" event and the legacy CKEditor "instanceReady"
		   event, and also resolve an instance that is already ready. */
		Liferay.on('ckeditor:ready', function({editor}) {
			if (matchesName(editor)) {
				resolve(editor);
			}
		});

		if (window.CKEDITOR) {
			if (CKEDITOR.instances && CKEDITOR.instances[editorName]) {
				resolve(CKEDITOR.instances[editorName]);
			}

			CKEDITOR.on('instanceReady', function({editor}) {
				if (matchesName(editor)) {
					resolve(editor);
				}
			});
		}
	});

	editorPromise.then(function(editor) {
		bodyEditorInstance = editor;
		try {
			if (typeof editor.resize === 'function') {
				/* Reduce default height to save vertical space in the modal */
				editor.resize('100%', 120);
			}
		} catch (e) {
			console.warn('Could not resize CKEditor', e);
		}
	});

	/* Read the editor content defensively: if the editor promise has not
	   resolved (e.g. the rendered editor and the LPD-11235 flag disagree),
	   fall back to the legacy CKEditor instance or the underlying textarea so
	   the body is never reported as empty when the user has typed something. */
	function getEditorData() {
		if (bodyEditorInstance && (typeof bodyEditorInstance.getData === 'function')) {
			return bodyEditorInstance.getData() || '';
		}
		if (window.CKEDITOR && CKEDITOR.instances &&
			CKEDITOR.instances[editorName] &&
			(typeof CKEDITOR.instances[editorName].getData === 'function')) {

			return CKEDITOR.instances[editorName].getData() || '';
		}
		const textarea = document.getElementById(editorName);
		if (textarea && (typeof textarea.value === 'string')) {
			return textarea.value;
		}
		return '';
	}

	/* ---- Attachment staging ---- */

	function renderPendingFiles() {
		if (!pendingFilesEl) return;
		pendingFilesEl.innerHTML = '';
		stagedFiles.forEach(function(file, index) {
			const chip = document.createElement('span');
			chip.className = 'label label-secondary label-dismissible forums-message-composer__pending-file';
			const removeLabel = (messageComposer.dataset.labelRemove || 'Remove');
			chip.innerHTML = '<span class="label-item label-item-expand">' +
				Liferay.Util.escapeHTML(file.name) + '</span>' +
				'<span class="label-item label-item-after"><button class="close" type="button" data-file-index="' +
				index + '" aria-label="' + Liferay.Util.escapeHTML(removeLabel) + '">×</button></span>';
			pendingFilesEl.appendChild(chip);
		});
	}

	/* Render the message's existing attachments (edit mode). Each shows a remove ×
	   only when the current user uploaded it; clicking stages it for deletion on
	   Save. Files marked for removal are hidden. */
	function renderExistingAttachments() {
		if (!existingFilesEl) return;
		existingFilesEl.innerHTML = '';
		const removeLabelTmpl = messageComposer.dataset.labelRemoveAttachment || 'Remove {0}';
		existingAttachments.forEach(function(att) {
			const {creator, file: attFile, id} = att;
			if (removedAttachmentIds.indexOf(id) !== -1) return;
			const {link, name: fileName} = attFile || {};
			const name = fileName || (link && link.label) || id;
			const canRemove = creator &&
				String(creator.id) === String(currentUserId);
			const chip = document.createElement('span');
			chip.className = 'label label-secondary forums-message-composer__existing-file';
			let inner = '<span class="label-item label-item-expand">' + Liferay.Util.escapeHTML(String(name)) + '</span>';
			if (canRemove) {
				const removeLabel = Liferay.Util.escapeHTML(removeLabelTmpl.replace('{0}', name));
				inner += '<span class="label-item label-item-after"><button class="close" type="button" data-attachment-id="' +
					id + '" aria-label="' + removeLabel + '" title="' + removeLabel + '">×</button></span>';
			}
			chip.innerHTML = inner;
			existingFilesEl.appendChild(chip);
		});
	}

	/* Fetch the attachments already on the message being edited so they can be shown
	   (and optionally removed) in the dialog. */
	function loadExistingAttachments(messageId) {
		if (!existingFilesEl || !messageId) return;
		/* The relationship FK is filtered as a quoted value (an unquoted numeric
		   yields a 400 "Incompatible types."), matching the message-detail fragment. */
		const filter = encodeURIComponent("r_messageAttachments_c_forumMessageId eq '" + messageId + "'");
		Liferay.Util.fetch(portalURL + '/o/c/forummessageattachments/scopes/' + scopeGroupId + '?nestedFields=file&pageSize=100&filter=' + filter, {
			headers,
			method: 'GET'
		})
		.then(function(r) { return r.json(); })
		.then(function(data) {
			existingAttachments = (data && data.items) || [];
			renderExistingAttachments();
		})
		.catch(function(err) { console.warn('ForumsMessageComposer: failed to load existing attachments', err); });
	}

	if (existingFilesEl) {
		existingFilesEl.addEventListener('click', function(e) {
			const btn = e.target.closest('.close');
			if (btn) {
				const id = parseInt(btn.getAttribute('data-attachment-id'), 10);
				if (removedAttachmentIds.indexOf(id) === -1) removedAttachmentIds.push(id);
				renderExistingAttachments();
			}
		});
	}

	/* Delete the attachments the user marked for removal (edit mode, on Save). */
	function deleteRemovedAttachments() {
		if (!removedAttachmentIds.length) return Promise.resolve();
		return Promise.all(removedAttachmentIds.map(function(id) {
			return Liferay.Util.fetch(portalURL + '/o/c/forummessageattachments/' + id, {
				headers,
				method: 'DELETE'
			}).catch(function(err) {
				console.warn('ForumsMessageComposer: attachment delete failed', err);
			});
		}));
	}

	if (attachBtn && fileInput) {
		attachBtn.addEventListener('click', function() {
			fileInput.click();
		});
		fileInput.addEventListener('change', function() {
			Array.prototype.forEach.call(fileInput.files, function(file) {
				if (file.size > MAX_FILE_SIZE) {
					if (Liferay.Util && Liferay.Util.openToast) {
						Liferay.Util.openToast({
							message: (messageComposer.dataset.labelFileTooLarge || 'The file is too large.') + ' (' + file.name + ')',
							type: 'danger'
						});
					}
					return;
				}
				stagedFiles.push(file);
			});
			/* Reset so selecting the same file again re-fires change. */
			fileInput.value = '';
			renderPendingFiles();
		});
	}

	if (pendingFilesEl) {
		pendingFilesEl.addEventListener('click', function(e) {
			const btn = e.target.closest('.close');
			if (btn) {
				const index = parseInt(btn.getAttribute('data-file-index'), 10);
				stagedFiles.splice(index, 1);
				renderPendingFiles();
			}
		});
	}

	/* Read a File as base64 (no data: prefix) for the object Attachment field payload. */
	function fileToBase64(file) {
		return new Promise(function(resolve, reject) {
			const reader = new FileReader();
			reader.onload = function() {
				const result = String(reader.result || '');
				const comma = result.indexOf(',');
				resolve(comma >= 0 ? result.slice(comma + 1) : result);
			};
			reader.onerror = function() { reject(reader.error || new Error('read failed')); };
			reader.readAsDataURL(file);
		});
	}

	/* Upload every staged file as a ForumMessageAttachment row hanging off the given
	   message. The Attachment field takes the file inline as {name, fileBase64}; the
	   row inherits the scope-level Site Member VIEW so any member can download it.
	   Best-effort per file so one failure doesn't abort the rest. */
	function uploadAttachments(messageId) {
		if (!messageId || !stagedFiles.length) return Promise.resolve();
		return Promise.all(stagedFiles.map(function(file) {
			const {name} = file;
			return fileToBase64(file).then(function(base64) {
				const body = {
					file: { name, fileBase64: base64 },
					r_messageAttachments_c_forumMessageId: parseInt(messageId)
				};
				return Liferay.Util.fetch(portalURL + '/o/c/forummessageattachments/scopes/' + scopeGroupId + '?nestedFields=file', {
					headers,
					method: 'POST',
					body: JSON.stringify(body)
				});
			}).catch(function(err) {
				console.warn('ForumsMessageComposer: attachment upload failed', err);
			});
		}));
	}

	/* Detect mode from URL */
	const urlParams = new URLSearchParams(window.location.search);
	let messageId = urlParams.get('messageId');
	let categoryIdParam = urlParams.get('categoryId');
	let isReplyMode = !!messageId;
	let parentMessageId = null;
	let categoriesLoaded = false;
	let isEditMode = false;
	let editMessageId = null;
	let editIsOp = false;
	let tagsArray = [];
	
	let isBanned = false;

	/* Thread priority (Message Boards parity). Whether the priority select is
	   offered is gated the same way the moderation page detects moderators: the
	   HATEOAS create action on the ForumBan collection, which regular users are
	   never granted. Like ban enforcement, this is UI-only — see the README. */
	let canSetPriority = false;
	/* True while the form is in a mode where priority applies (new topic or
	   edit topic, never replies). Combined with canSetPriority, which may
	   resolve after the modal is already open. */
	let priorityApplicable = false;

	function syncPriorityGroup() {
		if (priorityGroup) {
			priorityGroup.style.display = (priorityApplicable && canSetPriority) ? '' : 'none';
		}
	}

	if (Liferay.ThemeDisplay.isSignedIn()) {
		Liferay.Util.fetch(portalURL + '/o/c/forumbans/scopes/' + scopeGroupId + '?filter=' + encodeURIComponent('banUserId eq ' + currentUserId) + '&pageSize=1', {
			headers,
			method: 'GET'
		})
		.then(function(r) { return r.json(); })
		.then(function(data) {
			const {actions, items} = data;
			if (items && items.length > 0) {
				isBanned = true;
				if (submitBtn) submitBtn.disabled = true;
				if (errorAlert) {
					errorAlert.textContent = messageComposer.dataset.labelBannedWarning || 'Your account has been banned from participating in the forums.';
					errorAlert.style.display = '';
				}
			}
			canSetPriority = !isBanned && !!(actions && (actions['create'] || actions['post'] || actions['POST']));
			syncPriorityGroup();
		})
		.catch(function(err) { console.error('Error checking ban status', err); });
	}

	/* ---- Tags Logic ---- */

	function renderTags() {
		if (!tagsList) return;
		tagsList.innerHTML = '';
		tagsArray.forEach(function(tag, index) {
			const span = document.createElement('span');
			span.className = 'label label-secondary label-dismissible';
			span.innerHTML = '<span class="label-item label-item-expand">' + tag + '</span>' +
				'<span class="label-item label-item-after"><button class="close" type="button" data-tag-index="' + index + '" aria-label="Close">×</button></span>';
			tagsList.appendChild(span);
		});
	}

	if (tagsInput) {
		tagsInput.addEventListener('keydown', function(e) {
			if (e.key === ',' || e.key === 'Enter') {
				e.preventDefault();
				const val = tagsInput.value.trim().replace(/,/g, '');
				if (val && tagsArray.indexOf(val) === -1) {
					tagsArray.push(val);
					tagsInput.value = '';
					renderTags();
				} else if (val) {
					tagsInput.value = '';
				}
			} else if (e.key === 'Backspace' && tagsInput.value === '' && tagsArray.length > 0) {
				tagsArray.pop();
				renderTags();
			}
		});
	}

	if (tagsList) {
		tagsList.addEventListener('click', function(e) {
			const btn = e.target.closest('.close');
			if (btn) {
				const index = parseInt(btn.getAttribute('data-tag-index'), 10);
				tagsArray.splice(index, 1);
				renderTags();
			}
		});
	}

	/* ---- Modal show / hide (vanilla JS) ---- */

	let _modalTrigger = null;

	function showModal() {
		_modalTrigger = document.activeElement;
		if (modal) {
			modal.style.display = 'block';
			modal.classList.add('show');
			modal.setAttribute('aria-hidden', 'false');
		}
		if (backdrop) {
			backdrop.style.display = 'block';
			backdrop.classList.add('show');
		}
		document.body.classList.add('modal-open');
		/* Move focus into the dialog itself (not the close button) so screen
		   readers announce the modal and Esc/Tab work from a neutral start. */
		if (modal) modal.focus();
	}

	function hideModal() {
		if (modal) {
			modal.classList.remove('show');
			modal.style.display = 'none';
			modal.setAttribute('aria-hidden', 'true');
		}
		if (backdrop) {
			backdrop.classList.remove('show');
			backdrop.style.display = 'none';
		}
		document.body.classList.remove('modal-open');
		resetForm();
		/* Return focus to the element that opened the modal */
		if (_modalTrigger && typeof _modalTrigger.focus === 'function') {
			_modalTrigger.focus();
			_modalTrigger = null;
		}
	}

	function resetForm() {
		if (form) form.reset();
		if (successAlert) successAlert.style.display = 'none';
		if (errorAlert) errorAlert.style.display = 'none';
		if (bodyError) bodyError.style.display = 'none';
		if (submitBtn) {
			submitBtn.disabled = isBanned;
			submitBtn.textContent = messageComposer.dataset.labelPost || 'Post';
		}
		if (bodyEditorInstance) {
			bodyEditorInstance.setData('');
		}
		messageComposer.querySelectorAll('.is-invalid').forEach(function(el) {
			el.classList.remove('is-invalid');
		});
		isEditMode = false;
		editIsOp = false;
		editMessageId = null;
		if (prioritySelect) prioritySelect.value = '0';
		tagsArray = [];
		if (tagsInput) tagsInput.value = '';
		renderTags();
		stagedFiles = [];
		if (fileInput) fileInput.value = '';
		renderPendingFiles();
		existingAttachments = [];
		removedAttachmentIds = [];
		if (existingFilesEl) existingFilesEl.innerHTML = '';
	}

	/* "page" mode renders the form on its own screen (no modal); "modal" mode
	   keeps the Clay dialog used for replies and edits. */
	const formMode = messageComposer.dataset.formMode || 'modal';

	/* Close via X button, Cancel button, or backdrop click. In page mode the
	   Cancel button just navigates back instead of closing a dialog. */
	if (closeBtn) closeBtn.addEventListener('click', hideModal);
	if (cancelBtn) {
		cancelBtn.addEventListener('click', function() {
			if (formMode === 'page') {
				window.history.back();
			} else {
				hideModal();
			}
		});
	}
	if (backdrop) backdrop.addEventListener('click', hideModal);

	/* Close on Escape key */
	document.addEventListener('keydown', function(e) {
		if (e.key === 'Escape' && modal && modal.classList.contains('show')) {
			hideModal();
		}
	});

	/* ---- Configure form for new-message vs reply mode ---- */

	function configureModal(replyMode) {
		priorityApplicable = (isEditMode && editIsOp) || (!isEditMode && !replyMode);
		syncPriorityGroup();
		if (isEditMode && editIsOp) {
			if (titleEl) titleEl.textContent = messageComposer.dataset.labelEditTopic || 'Edit Topic';
			if (leftCol) leftCol.style.display = '';
			if (categoryGroup) categoryGroup.style.display = '';
			if (subjectGroup) subjectGroup.style.display = '';
			if (questionGroup) questionGroup.style.display = '';
			if (subscribeGroup) subscribeGroup.style.display = 'none';
			if (tagsGroup) tagsGroup.style.display = '';
			if (bodyLabel) bodyLabel.textContent = messageComposer.dataset.labelDetails || 'Details';
			if (submitBtn) submitBtn.textContent = messageComposer.dataset.labelSaveChanges || 'Save';
			loadCategories();
		} else if (isEditMode && !editIsOp) {
			if (titleEl) titleEl.textContent = messageComposer.dataset.labelEditReply || 'Edit Reply';
			/* Keep the left column visible so the Attachments section shows; the
			   topic-only field groups below are hidden individually. */
			if (leftCol) leftCol.style.display = '';
			if (categoryGroup) categoryGroup.style.display = 'none';
			if (subjectGroup) subjectGroup.style.display = 'none';
			if (questionGroup) questionGroup.style.display = 'none';
			if (subscribeGroup) subscribeGroup.style.display = 'none';
			if (tagsGroup) tagsGroup.style.display = 'none';
			if (bodyLabel) bodyLabel.textContent = messageComposer.dataset.labelYourReply || 'Your Reply';
			if (submitBtn) submitBtn.textContent = messageComposer.dataset.labelSaveChanges || 'Save';
		} else if (replyMode) {
			if (titleEl) titleEl.textContent = messageComposer.dataset.labelPostAReply || 'Post a Reply';
			/* Keep the left column visible so the Attachments section shows; the
			   topic-only field groups below are hidden individually. */
			if (leftCol) leftCol.style.display = '';
			if (categoryGroup) categoryGroup.style.display = 'none';
			if (subjectGroup) subjectGroup.style.display = 'none';
			if (questionGroup) questionGroup.style.display = 'none';
			if (subscribeGroup) subscribeGroup.style.display = 'none';
			if (tagsGroup) tagsGroup.style.display = 'none';
			if (bodyLabel) bodyLabel.textContent = messageComposer.dataset.labelYourReply || 'Your Reply';
			if (submitBtn) submitBtn.textContent = messageComposer.dataset.labelPost || 'Post';
		} else {
			if (titleEl) titleEl.textContent = messageComposer.dataset.labelNewForumThread || 'New Forum Thread';
			if (leftCol) leftCol.style.display = '';
			if (categoryGroup) categoryGroup.style.display = '';
			if (subjectGroup) subjectGroup.style.display = '';
			if (questionGroup) questionGroup.style.display = '';
			if (subscribeGroup) subscribeGroup.style.display = '';
			if (tagsGroup) tagsGroup.style.display = '';
			if (bodyLabel) bodyLabel.textContent = messageComposer.dataset.labelDetails || 'Details';
			if (submitBtn) submitBtn.textContent = messageComposer.dataset.labelPost || 'Post';
			loadCategories();
		}
	}

	/* Load categories into dropdown (only once) */
	function loadCategories() {
		if (categoriesLoaded) return;
		categoriesLoaded = true;

		Liferay.Util.fetch(portalURL + '/o/c/forumcategories/scopes/' + scopeGroupId + categoryQuery(messageComposer.dataset), {
			headers,
			method: 'GET'
		})
		.then(function(r) { return r.json(); })
		.then(function(data) {
			const items = data.items || [];

			/* Group subcategories under their parent so the structure is
			   visible. Posting into a parent stays valid — a parent lists only
			   its own topics, so it must remain a usable target. */
			const PARENT_FK = 'r_categorySubcategories_c_forumCategoryId';

			const byId = {};
			items.forEach(function(cat) { byId[cat.id] = cat; });

			const childrenOf = {};
			items.forEach(function(cat) {
				let pid = Number(cat[PARENT_FK]) || 0;
				if (pid && !byId[pid]) pid = 0;
				(childrenOf[pid] = childrenOf[pid] || []).push(cat);
			});

			function addOption(cat, depth) {
				const opt = document.createElement('option');
				opt.value = cat.id;
				opt.textContent = (depth > 0 ? '— ' : '')
					+ (cat.categoryName || messageComposer.dataset.labelUnnamed || 'Unnamed');
				if (categoryIdParam && String(cat.id) === categoryIdParam) {
					opt.selected = true;
				}
				categorySelect.appendChild(opt);
			}

			(childrenOf[0] || []).forEach(function(cat) {
				addOption(cat, 0);
				(childrenOf[cat.id] || []).forEach(function(child) {
					addOption(child, 1);
				});
			});
		})
		.catch(function(err) {
			console.error('ForumsMessageComposer: failed to load categories', err);
		});
	}

	/* ---- Public API for other fragments ---- */

	window.forumsOpenComposeModal = function({
		body, categoryId, editMode, isOp, isQuestion, priority, subject, tags, threadId,
		messageId: optionMessageId, parentMessageId: optionParentMessageId
	} = {}) {
		const replyMode = !!optionMessageId && !editMode;
		isEditMode = !!editMode;
		editIsOp = !!isOp;
		editMessageId = optionMessageId || null;

		if (threadId) messageId = threadId;
		else if (optionMessageId) messageId = optionMessageId;
		if (categoryId) categoryIdParam = String(categoryId);
		parentMessageId = optionParentMessageId || null;
		isReplyMode = replyMode;
		configureModal(replyMode);

		if (isEditMode) {
			loadExistingAttachments(editMessageId);
			if (subjectInput && subject) subjectInput.value = subject;
			if (questionCheck && isQuestion !== undefined) questionCheck.checked = isQuestion;
			if (prioritySelect) {
				/* Priority arrives as a decimal (e.g. 2.0); the select only knows
				   the discrete MB levels 0-3, anything else falls back to None. */
				const priorityValue = String(Math.round(parseFloat(priority)) || 0);
				prioritySelect.value = priorityValue;
				if (prioritySelect.value !== priorityValue) prioritySelect.value = '0';
			}
			if (tags && Array.isArray(tags)) {
				tagsArray = [].concat(tags);
				renderTags();
			}
			if (body && bodyEditorInstance) {
				bodyEditorInstance.setData(body);
			} else if (body) {
				/* Wait for editor to be ready if it's not yet */
				editorPromise.then(function(editor) {
					editor.setData(body);
				});
			}
			if (categoryId && categorySelect) {
				/* Ensure categories are loaded before setting value */
				if (!categoriesLoaded) {
					loadCategories();
					setTimeout(function() { categorySelect.value = String(categoryId); }, 500);
				} else {
					categorySelect.value = String(categoryId);
				}
			}
		} else if (!replyMode && categoryId && categorySelect) {
			categorySelect.value = String(categoryId);
		}

		showModal();
	};

	/* Listen for any element with data-forums-compose attribute */
	document.addEventListener('click', function(e) {
		const trigger = e.target.closest('[data-forums-compose]');
		if (trigger) {
			e.preventDefault();
			const {forumsCategoryId, forumsMessageId, forumsParentId, forumsTags} = trigger.dataset;
			const composeMessageId = forumsMessageId || messageId;
			const composeCategoryId = forumsCategoryId || categoryIdParam;
			/* For a reply to another reply, the root ForumMessage id is the
			   foreign key (data-forums-message-id) and the reply being answered
			   is the threading parent (data-forums-parent-id). */
			const composeParentId = forumsParentId || composeMessageId;
			const rawTags = forumsTags;
			let parsedTags = [];
			if (rawTags) {
				try { parsedTags = JSON.parse(rawTags); } catch (e) {}
			}
			window.forumsOpenComposeModal({
				messageId: trigger.hasAttribute('data-forums-reply') ? composeMessageId : null,
				categoryId: composeCategoryId,
				parentMessageId: composeParentId,
				tags: parsedTags
			});
		}
	});

	/* Initial configuration */
	configureModal(isReplyMode);

	/* SPA-friendly navigation helper */
	function spaNavigate(url) {
		if (Liferay.SPA && Liferay.SPA.app) {
			Liferay.SPA.app.navigate(url);
		} else {
			window.location.href = url;
		}
	}

	/* Check for pending success toast from a previous redirect */
	const pendingToast = sessionStorage.getItem('forumsSuccessToast');
	if (pendingToast) {
		sessionStorage.removeItem('forumsSuccessToast');
		Liferay.on('allPortletsReady', function() {
			setTimeout(function() {
				if (Liferay.Util && Liferay.Util.openToast) {
					Liferay.Util.openToast({
						message: pendingToast,
						title: messageComposer.dataset.labelSuccess || 'Success',
						type: 'success'
					});
				}
			}, 1000);
		});
	}

	/* Auto-open modal if ?compose=true is in the URL */
	if (urlParams.get('compose') === 'true') {
		showModal();
	}

	/* ---- Form submission ---- */

	if (form) {
		form.addEventListener('submit', function(e) {
			e.preventDefault();
			if (isBanned) return;
			
			if (successAlert) successAlert.style.display = 'none';
			if (errorAlert) errorAlert.style.display = 'none';

			const body = getEditorData();

			/* Strip HTML to check if it's completely empty */
			const tempDiv = document.createElement('div');
			tempDiv.innerHTML = body;
			const textContent = tempDiv.textContent || tempDiv.innerText || '';
			
			if (!textContent.trim()) {
				if (bodyError) bodyError.style.display = 'block';
				return;
			} else {
				if (bodyError) bodyError.style.display = 'none';
			}

			if (isEditMode) {
				const selectedCategory = categorySelect ? categorySelect.value : '';
				const subject = subjectInput ? subjectInput.value.trim() : '';
				const isQuestion = questionCheck ? questionCheck.checked : false;
				let valid = true;

				if (editIsOp) {
					if (!selectedCategory) { categorySelect.classList.add('is-invalid'); valid = false; } else { categorySelect.classList.remove('is-invalid'); }
					if (!subject) { subjectInput.classList.add('is-invalid'); valid = false; } else { subjectInput.classList.remove('is-invalid'); }
				}
				if (!valid) return;

				submitBtn.disabled = true;
				submitBtn.textContent = messageComposer.dataset.labelPosting || 'Posting...';

				const promises = [];
				if (editIsOp) {
					const threadPatchPayload = {
						messageTitle: subject,
						messageTitle_i18n: { en_US: subject },
						r_categoryThreads_c_forumCategoryId: parseInt(selectedCategory),
						question: isQuestion,
						keywords: tagsArray
					};
					/* Only privileged users may change the priority; omitting the
					   field keeps the thread's current value (MB resets a priority
					   sent without UPDATE_THREAD_PRIORITY the same way). */
					if (canSetPriority && prioritySelect) {
						threadPatchPayload.priority = parseFloat(prioritySelect.value) || 0;
					}
					promises.push(
						Liferay.Util.fetch(portalURL + '/o/c/forumthreads/' + messageId, {
							headers,
							method: 'PATCH',
							body: JSON.stringify(threadPatchPayload)
						}).then(function(r) { if (!r.ok) throw new Error('HTTP ' + r.status); })
					);
					promises.push(
						Liferay.Util.fetch(portalURL + '/o/c/forummessages/' + editMessageId, {
							headers,
							method: 'PATCH',
							body: JSON.stringify({
								subject,
								subject_i18n: { en_US: subject },
								body,
								r_categoryThreads_c_forumCategoryId: parseInt(selectedCategory)
							})
						}).then(function(r) { if (!r.ok) throw new Error('HTTP ' + r.status); })
					);
				} else {
					promises.push(
						Liferay.Util.fetch(portalURL + '/o/c/forummessages/' + editMessageId, {
							headers,
							method: 'PATCH',
							body: JSON.stringify({
								body
							})
						}).then(function(r) { if (!r.ok) throw new Error('HTTP ' + r.status); })
					);
				}

				Promise.all(promises)
				.then(function() { return deleteRemovedAttachments(); })
				.then(function() { return uploadAttachments(editMessageId); })
				.then(function() {
					hideModal();
					sessionStorage.setItem('forumsSuccessToast', messageComposer.dataset.labelSuccess || 'Success!');
					spaNavigate(window.location.pathname + window.location.search);
				})
				.catch(function(err) {
					console.error('Edit error:', err);
					if (errorAlert) errorAlert.style.display = '';
					submitBtn.disabled = false;
					submitBtn.textContent = messageComposer.dataset.labelSaveChanges || 'Save';
				});

			} else if (isReplyMode) {
				submitBtn.disabled = true;
				submitBtn.textContent = messageComposer.dataset.labelPosting || 'Posting...';

				const replyPayload = {
					r_threadMessages_c_forumThreadId: parseInt(messageId),
					parentMessageId: parentMessageId ? parseInt(parentMessageId) : 0,
					body,
					format: 'html',
					subject: 'Re: reply',
					subject_i18n: { en_US: 'Re: reply' }
				};

				Liferay.Util.fetch(portalURL + '/o/c/forummessages/scopes/' + scopeGroupId, {
					headers,
					method: 'POST',
					body: JSON.stringify(replyPayload)
				})
				.then(function(r) {
					if (!r.ok) throw new Error('HTTP ' + r.status);
					return r.json();
				})
				.then(function(reply) { return uploadAttachments(reply && reply.id); })
				.then(function() {
					hideModal();
					sessionStorage.setItem('forumsSuccessToast', messageComposer.dataset.labelReplyPosted || 'Reply posted successfully!');
					spaNavigate(window.location.pathname + window.location.search);
				})
				.catch(function(err) {
					console.error('Reply error:', err);
					if (errorAlert) errorAlert.style.display = '';
					submitBtn.disabled = false;
					submitBtn.textContent = messageComposer.dataset.labelPost || 'Post';
				});

			} else {
				const selectedCategory = categorySelect ? categorySelect.value : '';
				const subject = subjectInput ? subjectInput.value.trim() : '';
				const isQuestion = questionCheck ? questionCheck.checked : false;
				let valid = true;

				if (!selectedCategory) {
					categorySelect.classList.add('is-invalid');
					valid = false;
				} else {
					categorySelect.classList.remove('is-invalid');
				}

				if (!subject) {
					subjectInput.classList.add('is-invalid');
					valid = false;
				} else {
					subjectInput.classList.remove('is-invalid');
				}

				if (!valid) return;

				submitBtn.disabled = true;
				submitBtn.textContent = messageComposer.dataset.labelPosting || 'Posting...';

				const messagePayload = {
					messageTitle: subject,
					messageTitle_i18n: { en_US: subject },
					r_categoryThreads_c_forumCategoryId: parseInt(selectedCategory),
					question: isQuestion,
					keywords: tagsArray,
					priority: (canSetPriority && prioritySelect) ? (parseFloat(prioritySelect.value) || 0) : 0
				};

				Liferay.Util.fetch(portalURL + '/o/c/forumthreads/scopes/' + scopeGroupId, {
					headers,
					method: 'POST',
					body: JSON.stringify(messagePayload)
				})
				.then(function(r) {
					if (!r.ok) throw new Error('HTTP ' + r.status);
					return r.json();
				})
				.then(function(msg) {
					const {friendlyUrlPath, id: threadId, scopeKey} = msg;

					const msgPayload = {
						r_threadMessages_c_forumThreadId: threadId,
						r_categoryThreads_c_forumCategoryId: parseInt(selectedCategory),
						subject,
						subject_i18n: { en_US: subject },
						body,
						format: 'html'
					};

					const promises = [];
					promises.push(Liferay.Util.fetch(portalURL + '/o/c/forummessages/scopes/' + scopeGroupId, {
						headers,
						method: 'POST',
						body: JSON.stringify(msgPayload)
					}).then(function(r) {
						if (!r.ok) throw new Error('HTTP ' + r.status);
						return r.json();
					}));

					if (subscribeCheck && subscribeCheck.checked && parseInt(currentUserId) > 0) {
						promises.push(Liferay.Util.fetch(portalURL + '/o/c/forumsubscriptions/scopes/' + scopeGroupId, {
							headers,
							method: 'POST',
							body: JSON.stringify({
								r_threadSubscriptions_c_forumThreadId: threadId,
								subscriberUserId: parseInt(currentUserId)
							})
						}).then(function(r) {
							if (!r.ok) throw new Error('HTTP ' + r.status);
						}));
					}

					return Promise.all(promises).then(function(results) {
						const [rootMsg] = results || [];
						return uploadAttachments(rootMsg && rootMsg.id);
					}).then(function() {
						if (!friendlyUrlPath) {
							if (Liferay.Util && Liferay.Util.openToast) {
								Liferay.Util.openToast({
									message: messageComposer.dataset.labelDisplayPageNotConfigured || 'Message created, but the display page is not configured.',
									type: 'danger'
								});
							}
							hideModal();
							return;
						}
						hideModal();
						sessionStorage.setItem('forumsSuccessToast', messageComposer.dataset.labelQuestionPosted || 'Your question has been posted!');
						const siteSlug = (scopeKey || '').toLowerCase().replace(/ /g, '-');
						spaNavigate(Liferay.ThemeDisplay.getPathFriendlyURLPublic() + '/' + siteSlug + '/c_forumthread/' + friendlyUrlPath);
					});
				})
				.catch(function(err) {
					console.error('New message error:', err);
					if (errorAlert) errorAlert.style.display = '';
					submitBtn.disabled = false;
					submitBtn.textContent = messageComposer.dataset.labelPost || 'Post';
				});
			}
		});
	}

	/* ---------------------------------------------------------------------
	   @mention picker

	   Typing "@" in the body editor opens a caret-anchored dropdown of users
	   served by the OOTB (hidden) Mentions portlet's resource phase. That
	   portlet is embedded as a widget on every page that hosts this composer
	   (see the site-initializer page definitions), so it counts as "on the
	   page" and its serveResource can be invoked without a p_p_auth token --
	   letting us build the resource URL as a plain string in JS. The finder
	   honors the portal Social Interactions configuration, already excludes the
	   current user and guests, and returns [{fullName, screenName,
	   portraitHTML, mention}] (no user id and no email address, by design).

	   Selecting one inserts the OOTB mention shape -- the visible "@screenName"
	   token, wrapped in a <span class="lfr-ac-content"> (CKEditor 4) or as a
	   bare text token (CKEditor 5, which drops unknown spans on serialization).

	   The visible "@screenName" token is the reliable channel across editor
	   versions: the forums-microservice parses mentioned screen names with a
	   boundary-anchored @screenName regex from the posted body and resolves
	   them with a single site-scoped query (see MentionService), mirroring the
	   platform's own DefaultMentionsMatcher.
	   --------------------------------------------------------------------- */
	(function setupMentions() {
		const MENTION_MAX = 6;
		let mentionAttached = false;

		/* The OOTB Mentions portlet, embedded as an on-page widget. Its
		   resource params are namespaced with "_<portletId>_". The finder
		   permission-scopes its candidate list against a "discussion portlet";
		   the page comments portlet is a sensible default and mirrors how OOTB
		   comment mentions are scoped. */
		const MENTIONS_ID = 'com_liferay_mentions_web_portlet_MentionsPortlet';
		const MENTIONS_NS = '_' + MENTIONS_ID + '_';
		const MENTIONS_DISCUSSION_PORTLET_ID =
			'com_liferay_comment_page_comments_web_portlet_PageCommentsPortlet';

		/* Track every global/document listener added below so they can all be
		   removed on SPA navigation; otherwise each visit to this fragment would
		   leave behind orphaned listeners (and dropdowns) that accumulate. */
		let cleanups = [];
		function addListener(target, type, handler, options) {
			target.addEventListener(type, handler, options);
			cleanups.push(function() {
				target.removeEventListener(type, handler, options);
			});
		}

		/* Reuse a single dropdown per fragment instance: remove any stale one
		   left by a prior render before creating a fresh one, so they don't
		   stack up in the document body across SPA transitions. */
		const dropdownId = fragmentElementId + '-forumsMentionDropdown';
		const staleDropdown = document.getElementById(dropdownId);
		if (staleDropdown) staleDropdown.remove();

		const dropdown = document.createElement('div');
		dropdown.id = dropdownId;
		dropdown.className = 'forums-mention-dropdown';
		dropdown.style.display = 'none';
		dropdown.setAttribute('role', 'listbox');
		document.body.appendChild(dropdown);

		let activeIndex = -1;
		let currentItems = [];
		let currentQuery = null;    /* the text typed after "@" (may be "") */
		let fetchTimer = null;
		let lastReqId = 0;

		function mentionDisplayName(u) {
			return u.fullName || u.screenName || '';
		}

		/* The contenteditable element the editor renders into, and helpers to
		   read the DOM selection inside it (works for both editor versions;
		   CKEditor 4 may host the editable inside an iframe). */
		function getEditableEl(editor) {
			const {editing} = editor;
			if (editing && editing.view &&
				typeof editing.view.getDomRoot === 'function') {
				return editing.view.getDomRoot();   /* CKEditor 5 */
			}
			if (typeof editor.editable === 'function' && editor.editable()) {
				return editor.editable().$;                 /* CKEditor 4 */
			}
			return null;
		}

		function frameOffset(editableEl) {
			const win = editableEl.ownerDocument.defaultView;
			const frameEl = win && win.frameElement;
			if (frameEl) {
				const {left: x, top: y} = frameEl.getBoundingClientRect();
				return { x, y };
			}
			return { x: 0, y: 0 };
		}

		/* Inspect the caret; if it sits right after an "@token", return the
		   token text, else null. Only fires for a collapsed caret in a text
		   node so we never hijack a range selection.

		   At least one alphanumeric character must follow "@" before a query is
		   reported, so a bare "@" does not fire an XHR and dump the whole user
		   list -- matching the OOTB Page Comments autocomplete, which only
		   searches once a screen-name character is typed. The first character is
		   alphanumeric; later ones may include the punctuation "." "-" "_". */
		function detectQuery(editableEl) {
			const doc = editableEl.ownerDocument;
			const sel = doc.getSelection();
			if (!sel) return null;
			const {anchorNode, anchorOffset, isCollapsed, rangeCount} = sel;
			if (rangeCount === 0 || !isCollapsed) return null;
			if (!anchorNode || anchorNode.nodeType !== 3) return null;
			const before = anchorNode.textContent.slice(0, anchorOffset);
			const m = before.match(/(?:^|[\s (\[])@([a-zA-Z0-9][\w.\-]{0,29})$/);
			if (!m) return null;
			const [, query] = m;
			return query;
		}

		function hideDropdown() {
			dropdown.style.display = 'none';
			activeIndex = -1;
			currentItems = [];
			currentQuery = null;
		}

		function positionDropdown(editableEl) {
			const doc = editableEl.ownerDocument;
			const sel = doc.getSelection();
			if (!sel || sel.rangeCount === 0) return;
			const rect = sel.getRangeAt(0).getBoundingClientRect();
			const {x, y} = frameOffset(editableEl);
			let top = rect.bottom + y;
			let left = rect.left + x;
			if (!rect.height && !rect.width) {
				/* Some browsers return an empty rect for a collapsed caret;
				   fall back to the editable's top-left. */
				const er = editableEl.getBoundingClientRect();
				top = er.top + y + 24;
				left = er.left + x + 8;
			}
			const maxLeft = window.innerWidth - dropdown.offsetWidth - 8;
			dropdown.style.top = Math.round(top + 4) + 'px';
			dropdown.style.left = Math.round(Math.max(8, Math.min(left, maxLeft))) + 'px';
		}

		function renderDropdown(editableEl) {
			if (currentQuery === null) { hideDropdown(); return; }

			if (!currentItems.length) {
				dropdown.innerHTML = '<div class="forums-mention-dropdown__empty">' +
					Liferay.Util.escapeHTML(messageComposer.dataset.labelMentionNoUsers || 'No users found') + '</div>';
				dropdown.style.display = 'block';
				positionDropdown(editableEl);
				return;
			}

			let html = '';
			currentItems.forEach(function(u, i) {
				/* fullName and screenName arrive already HTML-escaped from the
				   Mentions portlet; insert them as-is (re-escaping would render
				   visible entities). portraitHTML is ready-to-render markup. */
				const {portraitHTML, screenName} = u;
				const name = mentionDisplayName(u);
				const screen = screenName ? ('@' + screenName) : '';
				const initial = (name || '?').charAt(0).toUpperCase();
				const avatar = portraitHTML
					? portraitHTML
					: '<span class="sticker sticker-circle sticker-sm sticker-outline-' + (i % 10) + '"><span class="sticker-overlay">' + Liferay.Util.escapeHTML(initial) + '</span></span>';
				html += '<button type="button" role="option" class="forums-mention-dropdown__item' +
					(i === activeIndex ? ' is-active' : '') + '" data-mention-index="' + i + '"' +
					(i === activeIndex ? ' aria-selected="true"' : '') + '>' +
					avatar +
					'<span class="forums-mention-dropdown__text">' +
						'<span class="forums-mention-dropdown__name">' + name + '</span>' +
						(screen ? '<span class="forums-mention-dropdown__screen">' + screen + '</span>' : '') +
					'</span>' +
					'</button>';
			});
			dropdown.innerHTML = html;
			dropdown.style.display = 'block';
			positionDropdown(editableEl);
		}

		function searchUsers(query, editableEl) {
			const reqId = ++lastReqId;
			/* Invoke the embedded Mentions portlet's serveResource. The base is
			   the current layout URL; because the portlet is on this page, no
			   p_p_auth token is needed. Resource params are namespaced. The
			   finder is Social Interactions-scoped and already excludes the
			   current user and guests, so no client-side self-filtering. */
			const base = Liferay.ThemeDisplay.getLayoutRelativeURL() ||
				window.location.pathname;
			const url = base +
				'?p_p_id=' + MENTIONS_ID +
				'&p_p_lifecycle=2' +
				'&p_p_state=exclusive' +
				'&' + MENTIONS_NS + 'discussionPortletId=' + encodeURIComponent(MENTIONS_DISCUSSION_PORTLET_ID) +
				'&' + MENTIONS_NS + 'query=' + encodeURIComponent(query || '');
			Liferay.Util.fetch(url, { headers, method: 'GET' })
				.then(function(r) { return r.json(); })
				.then(function(data) {
					if (reqId !== lastReqId || currentQuery === null) return;
					/* The Mentions portlet returns a plain JSON array. */
					currentItems = (Array.isArray(data) ? data : []).slice(0, MENTION_MAX);
					activeIndex = currentItems.length ? 0 : -1;
					renderDropdown(editableEl);
				})
				.catch(function() {
					if (reqId !== lastReqId) return;
					currentItems = [];
					activeIndex = -1;
					renderDropdown(editableEl);
				});
		}

		function insertMention(editor, user) {
			/* Insert the OOTB mention shape: the visible "@screenName" token is
			   the durable channel the notification microservice resolves (a
			   boundary-anchored @screenName regex, matching how the platform's
			   DefaultMentionsMatcher works). CKEditor 4 wraps it in the OOTB
			   "lfr-ac-content" span for chip styling; CKEditor 5 drops unknown
			   spans on serialization, so it gets the bare token (still fully
			   resolvable). screenName is already HTML-escaped by the portlet. */
			const screenName = user.screenName || '';
			if (!screenName) { hideDropdown(); return; }
			const label = '@' + screenName;
			const query = currentQuery || '';
			const removeLen = query.length + 1; /* the "@" plus the typed query */

			const {editing, model} = editor;
			if (model && editing) {
				/* CKEditor 5: delete "@query" then insert the plain token + space
				   (no link -- the mention is a text token, not a hyperlink). */
				try {
					model.change(function(writer) {
						const pos = model.document.selection.getFirstPosition();
						const startPos = pos.getShiftedBy(-removeLen);
						writer.remove(writer.createRange(startPos, pos));
						model.insertContent(
							writer.createText(label), startPos);
						const afterPos = startPos.getShiftedBy(label.length);
						model.insertContent(writer.createText(' '), afterPos);
						writer.setSelection(afterPos.getShiftedBy(1));
					});
				} catch (e) { console.warn('mention insert (v5) failed', e); }
			} else if (typeof editor.getSelection === 'function') {
				/* CKEditor 4: extend the range back over "@query", replace with
				   the OOTB lfr-ac-content span. label is already HTML-escaped. */
				try {
					const sel = editor.getSelection();
					const [range] = sel.getRanges();
					if (range && range.startOffset >= removeLen) {
						range.setStart(range.startContainer, range.startOffset - removeLen);
						range.select();
					}
					editor.insertHtml('<span class="lfr-ac-content">' +
						label + '</span>&nbsp;');
				} catch (e) { console.warn('mention insert (v4) failed', e); }
			}
			hideDropdown();
		}

		function choose(editor, editableEl, index) {
			if (index < 0 || index >= currentItems.length) return;
			insertMention(editor, currentItems[index]);
			editableEl.focus();
		}

		function onActivity(editor, editableEl) {
			const q = detectQuery(editableEl);
			if (q === null) { hideDropdown(); return; }
			currentQuery = q;
			if (fetchTimer) clearTimeout(fetchTimer);
			fetchTimer = setTimeout(function() { searchUsers(q, editableEl); }, 150);
		}

		function attach(editor) {
			if (mentionAttached) return;
			const editableEl = getEditableEl(editor);
			if (!editableEl) return;
			mentionAttached = true;

			['keyup', 'input', 'mouseup'].forEach(function(evt) {
				addListener(editableEl, evt, function() {
					/* Arrow/enter/esc are handled in keydown; skip here. */
					onActivity(editor, editableEl);
				});
			});

			/* Intercept navigation keys in the capture phase so the editor
			   doesn't also act on them while the dropdown is open. Bound on the
			   editable's document (not the element) so a document-level capture
			   listener fires before the editor's own keydown handler — CKEditor
			   4 hosts the editable in an iframe, hence ownerDocument. */
			addListener(editableEl.ownerDocument, 'keydown', function(e) {
				if (dropdown.style.display === 'none' || currentQuery === null) return;
				if (e.key === 'ArrowDown') {
					e.preventDefault(); e.stopPropagation();
					if (currentItems.length) { activeIndex = (activeIndex + 1) % currentItems.length; renderDropdown(editableEl); }
				} else if (e.key === 'ArrowUp') {
					e.preventDefault(); e.stopPropagation();
					if (currentItems.length) { activeIndex = (activeIndex - 1 + currentItems.length) % currentItems.length; renderDropdown(editableEl); }
				} else if (e.key === 'Enter' || e.key === 'Tab') {
					if (activeIndex >= 0 && currentItems.length) {
						e.preventDefault(); e.stopPropagation();
						choose(editor, editableEl, activeIndex);
					}
				} else if (e.key === 'Escape') {
					e.preventDefault(); e.stopPropagation();
					hideDropdown();
				}
			}, true);

			addListener(editableEl, 'blur', function() {
				/* Delay so a click on a dropdown item registers first. */
				setTimeout(hideDropdown, 150);
			});
		}

		/* Mouse selection from the dropdown. mousedown (not click) so it fires
		   before the editable's blur hides the list. */
		dropdown.addEventListener('mousedown', function(e) {
			const btn = e.target.closest('[data-mention-index]');
			if (!btn) return;
			e.preventDefault();
			const idx = parseInt(btn.getAttribute('data-mention-index'), 10);
			if (bodyEditorInstance) choose(bodyEditorInstance, getEditableEl(bodyEditorInstance), idx);
		});

		addListener(window, 'scroll', function() {
			if (dropdown.style.display !== 'none' && bodyEditorInstance) {
				positionDropdown(getEditableEl(bodyEditorInstance));
			}
		}, true);

		editorPromise.then(attach);

		/* On SPA navigation away, remove the dropdown and every listener bound
		   above so nothing accumulates across page transitions. */
		function cleanup() {
			cleanups.forEach(function(fn) {
				try { fn(); } catch (e) {}
			});
			cleanups = [];
			if (dropdown && dropdown.parentNode) {
				dropdown.parentNode.removeChild(dropdown);
			}
		}
		if (window.Liferay && Liferay.once) {
			Liferay.once('beforeScreenFlip', cleanup);
		} else if (window.Liferay && Liferay.on) {
			Liferay.on('beforeScreenFlip', cleanup);
		}
	})();
}
