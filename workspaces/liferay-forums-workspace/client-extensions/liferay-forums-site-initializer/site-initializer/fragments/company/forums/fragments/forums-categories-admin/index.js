/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const forumsCategoriesAdmin = fragmentElement.querySelector('#forumsCategoriesAdmin');

/* Category query string. pageSize and sort come from fragment configuration;
   a blank sort omits the parameter entirely, which is needed on databases
   that cannot sort on a Text object field (Hypersonic raises "data type cast
   needed for parameter or null literal"). */
function categoryQuery(dataset) {
	const size = dataset.categoryPageSize || '100';
	const sort = (dataset.categorySort || '').trim();
	return '?pageSize=' + encodeURIComponent(size)
		+ (sort ? '&sort=' + encodeURIComponent(sort) : '');
}

if (forumsCategoriesAdmin) {
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const clayIconsUrl = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json'
	};

	/* FK exposed by the ForumCategory self-relationship (0 / absent = top-level) */
	const PARENT_FK = 'r_categorySubcategories_c_forumCategoryId';

	/* Subcategories are intentionally capped at ONE level.
	   This is a constant, NOT a configuration option: a configurable depth
	   recreates the unbounded-nesting problem this cap exists to prevent.
	   Categories cut where permissions and audiences cut; tags handle topics. */
	const MAX_DEPTH = 1;

	const cardEl = forumsCategoriesAdmin.querySelector('.forums-categories-admin__card');
	const noPermissionsEl = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminNoPermissions');
	const seedSection = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminSeedSection');
	const seedBtn = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminSeedBtn');
	const seedStatus = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminSeedStatus');
	const addHeading = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminAddHeading');
	const addForm = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminAddForm');
	const addName = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminCatName');
	const addDesc = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminCatDesc');
	const addParent = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminCatParent');
	const addBtn = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminAddBtn');
	const listEl = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminCategoryList');
	const loadingEl = forumsCategoriesAdmin.querySelector('#forumsCategoriesAdminLoading');

	/* Track whether the current user has create permission */
	let canCreate = false;

	const topLevelLabel = forumsCategoriesAdmin.dataset.labelTopLevel || 'None (top-level)';

	const {
		category1Name, category1Desc, category1ERC,
		category2Name, category2Desc, category2ERC,
		category3Name, category3Desc, category3ERC,
		category4Name, category4Desc, category4ERC,
		category5Name, category5Desc, category5ERC
	} = configuration;

	const defaultCategories = [
		{ name: category1Name, desc: category1Desc, erc: category1ERC },
		{ name: category2Name, desc: category2Desc, erc: category2ERC },
		{ name: category3Name, desc: category3Desc, erc: category3ERC },
		{ name: category4Name, desc: category4Desc, erc: category4ERC },
		{ name: category5Name, desc: category5Desc, erc: category5ERC }
	].filter(function({name}) { return name; });

	/* --- Hierarchy helpers ---------------------------------------------- */

	function getParentId(cat) {
		return Number(cat[PARENT_FK]) || 0;
	}

	/* Build {byId, childrenOf} from a flat category list.
	   A category whose parent is missing — or whose parent is itself a child,
	   which only the REST API can produce — is normalized to top-level so the
	   UI stays coherent and never hides an entry. */
	function buildTree(items) {
		const byId = {};
		items.forEach(function(cat) { byId[cat.id] = cat; });

		const depthOf = {};
		items.forEach(function(cat) {
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
		items.forEach(function(cat) {
			let pid = getParentId(cat);
			if (!pid || !byId[pid] || depthOf[cat.id] > MAX_DEPTH) pid = 0;
			(childrenOf[pid] = childrenOf[pid] || []).push(cat);
		});

		return { byId, childrenOf };
	}

	function hasChildren(id, childrenOf) {
		return (childrenOf[id] || []).length > 0;
	}

	/* Fill a <select> with the categories eligible to be a parent.
	   THIS IS WHERE THE CAP IS ENFORCED: only top-level categories are
	   offered, so a new/edited category can never land deeper than
	   MAX_DEPTH. Categories in excludeIds (the entry itself) are omitted. */
	function populateParentSelect(selectEl, {childrenOf}, selectedId, excludeIds = []) {
		selectEl.innerHTML = '';

		const topOption = document.createElement('option');
		topOption.value = '';
		topOption.textContent = topLevelLabel;
		selectEl.appendChild(topOption);

		(childrenOf[0] || []).forEach(function({id, categoryName}) {
			if (excludeIds.indexOf(id) !== -1) return;

			const opt = document.createElement('option');
			opt.value = id;
			opt.textContent = categoryName || forumsCategoriesAdmin.dataset.labelUnnamed || 'Unnamed';
			if (String(id) === String(selectedId)) opt.selected = true;
			selectEl.appendChild(opt);
		});
	}

	/* --- Data access ----------------------------------------------------- */

	function loadCategories() {
		if (loadingEl) loadingEl.style.display = 'block';
		listEl.innerHTML = '';

		Liferay.Util.fetch(portalURL + '/o/c/forumcategories/scopes/' + scopeGroupId + categoryQuery(forumsCategoriesAdmin.dataset), {
			headers,
			method: 'GET'
		})
		.then(function(r) { return r.json(); })
		.then(function(data) {
			if (loadingEl) loadingEl.style.display = 'none';

			/* HATEOAS: check collection-level actions for create permission */
			const {actions} = data;
			canCreate = !!(actions && (actions['create'] || actions['post'] || actions['POST']));

			if (canCreate) {
				/* User has admin-level permissions — show the admin card */
				if (noPermissionsEl) noPermissionsEl.style.display = 'none';
				if (cardEl) cardEl.style.display = '';
				if (seedSection) seedSection.style.display = '';
				if (addHeading) addHeading.style.display = '';
				if (addForm) addForm.style.display = '';
			} else {
				/* Non-privileged user — show the OOTB permissions warning */
				if (cardEl) cardEl.style.display = 'none';
				if (noPermissionsEl) noPermissionsEl.style.display = '';
				return;
			}

			const items = data.items || [];
			const tree = buildTree(items);

			/* Refresh the add-form parent picker with the current tree */
			if (addParent) populateParentSelect(addParent, tree, '', []);

			if (items.length === 0) {
				listEl.innerHTML = '<li class="list-group-item text-secondary">' + (forumsCategoriesAdmin.dataset.labelNoCategories || 'No categories found.') + '</li>';
				return;
			}

			/* Two tiers only: top-level categories, each followed by its children */
			const {childrenOf} = tree;
			(childrenOf[0] || []).forEach(function(cat) {
				listEl.appendChild(renderCategoryItem(cat, 0, tree));
				(childrenOf[cat.id] || []).forEach(function(child) {
					listEl.appendChild(renderCategoryItem(child, 1, tree));
				});
			});
		})
		.catch(function(err) {
			if (loadingEl) loadingEl.style.display = 'none';

			/* On error (e.g. 403), show the permissions warning */
			if (cardEl) cardEl.style.display = 'none';
			if (noPermissionsEl) noPermissionsEl.style.display = '';
			console.error(err);
		});
	}

	/* Build a single list row (with inline edit form) for one category */
	function renderCategoryItem(cat, depth, tree) {
		const {id, actions, categoryName, categoryDescription} = cat;
		const {childrenOf} = tree;

		const li = document.createElement('li');
		li.className = 'list-group-item flex-column align-items-start';
		if (depth > 0) li.style.marginLeft = (depth * 1.5) + 'rem';

		const viewContainer = document.createElement('div');
		viewContainer.className = 'd-flex justify-content-between align-items-center w-100';

		const infoDiv = document.createElement('div');
		infoDiv.className = 'd-flex flex-column flex-grow-1';

		const nameSpan = document.createElement('span');
		nameSpan.className = 'font-weight-bold';
		nameSpan.textContent = categoryName || forumsCategoriesAdmin.dataset.labelUnnamed || 'Unnamed';

		const descSpan = document.createElement('span');
		descSpan.className = 'text-secondary small';
		descSpan.textContent = categoryDescription || '';

		infoDiv.appendChild(nameSpan);
		if (categoryDescription) infoDiv.appendChild(descSpan);

		viewContainer.appendChild(infoDiv);

		const actionsDiv = document.createElement('div');
		actionsDiv.className = 'd-flex';

		/* HATEOAS: only render edit button if the item-level actions include 'update' */
		const updateAction = actions && (actions['update'] || actions['patch'] || actions['put'] || actions['PATCH'] || actions['PUT']);
		let editBtn = null;
		if (updateAction) {
			editBtn = document.createElement('button');
			editBtn.className = 'btn btn-sm btn-outline-secondary mr-2';
			editBtn.title = forumsCategoriesAdmin.dataset.labelEdit || 'Edit';
			editBtn.ariaLabel = forumsCategoriesAdmin.dataset.labelEdit || 'Edit';
			editBtn.setAttribute('data-tooltip-align', 'top');
			editBtn.innerHTML = '<svg class="lexicon-icon lexicon-icon-pencil" role="presentation"><use href="' + clayIconsUrl + '#pencil"></use></svg>';
			actionsDiv.appendChild(editBtn);
		}

		/* HATEOAS: only render delete button if the item-level actions include 'delete' */
		if (actions && actions['delete']) {
			const delBtn = document.createElement('button');
			delBtn.className = 'btn btn-sm btn-outline-danger';
			delBtn.title = forumsCategoriesAdmin.dataset.labelDelete || 'Delete';
			delBtn.ariaLabel = forumsCategoriesAdmin.dataset.labelDelete || 'Delete';
			delBtn.setAttribute('data-tooltip-align', 'top');
			delBtn.innerHTML = '<svg class="lexicon-icon lexicon-icon-trash" role="presentation"><use href="' + clayIconsUrl + '#trash"></use></svg>';
			delBtn.addEventListener('click', function() {
				deleteCategory(actions['delete'].href, id, (childrenOf[id] || []).length);
			});
			actionsDiv.appendChild(delBtn);
		}

		viewContainer.appendChild(actionsDiv);
		li.appendChild(viewContainer);

		if (updateAction && editBtn) {
			/* A category that already has subcategories cannot itself be nested —
			   doing so would push its children past MAX_DEPTH. */
			const isParent = hasChildren(id, childrenOf);

			const editContainer = document.createElement('div');
			editContainer.className = 'w-100 mt-3';
			editContainer.style.display = 'none';

			const editForm = document.createElement('form');
			editForm.className = 'mb-0';
			const nameFieldId = 'forumsCatEditName-' + id;
			const descFieldId = 'forumsCatEditDesc-' + id;
			const parentFieldId = 'forumsCatEditParent-' + id;
			const {labelCategoryName, labelDescription, labelParentCategory, labelHasSubcategories} = forumsCategoriesAdmin.dataset;
			const labelName = labelCategoryName || 'Category Name';
			const labelDesc = labelDescription || 'Description';
			const labelParent = labelParentCategory || 'Parent Category';
			const labelHasSubs = labelHasSubcategories || 'A category with subcategories cannot be nested.';

			const parentFieldHtml = isParent
				? '<div class="form-group mb-3 mb-md-0">' +
						'<span class="text-secondary small">' + Liferay.Util.escapeHTML(labelHasSubs) + '</span>' +
					'</div>'
				: '<div class="form-group mb-3 mb-md-0">' +
						'<label for="' + parentFieldId + '" class="sr-only">' + labelParent + '</label>' +
						'<select class="form-control" id="' + parentFieldId + '" aria-label="' + labelParent + '"></select>' +
					'</div>';

			editForm.innerHTML = '<div class="row align-items-end">' +
				'<div class="col-12 col-md">' +
					'<div class="form-group mb-3 mb-md-0">' +
						'<label for="' + nameFieldId + '" class="sr-only">' + labelName + '</label>' +
						'<input type="text" class="form-control" id="' + nameFieldId + '" aria-label="' + labelName + '" required>' +
					'</div>' +
				'</div>' +
				'<div class="col-12 col-md">' +
					'<div class="form-group mb-3 mb-md-0">' +
						'<label for="' + descFieldId + '" class="sr-only">' + labelDesc + '</label>' +
						'<input type="text" class="form-control" id="' + descFieldId + '" aria-label="' + labelDesc + '">' +
					'</div>' +
				'</div>' +
				'<div class="col-12 col-md">' + parentFieldHtml + '</div>' +
				'<div class="col-12 col-md-auto mt-3 mt-md-0">' +
					'<button type="submit" class="btn btn-primary mr-2">' + (forumsCategoriesAdmin.dataset.labelSave || 'Save') + '</button>' +
					'<button type="button" class="btn btn-outline-secondary cancel-edit-btn">' + (forumsCategoriesAdmin.dataset.labelCancel || 'Cancel') + '</button>' +
				'</div>' +
			'</div>';

			const nameInput = editForm.querySelector('#' + nameFieldId);
			const descInput = editForm.querySelector('#' + descFieldId);
			const parentSelect = editForm.querySelector('#' + parentFieldId);
			const cancelBtn = editForm.querySelector('.cancel-edit-btn');
			const saveBtn = editForm.querySelector('button[type="submit"]');

			editBtn.addEventListener('click', function() {
				nameInput.value = categoryName || '';
				descInput.value = categoryDescription || '';
				if (parentSelect) {
					/* Exclude self; the picker already offers top-level only */
					populateParentSelect(parentSelect, tree, getParentId(cat) || '', [id]);
				}
				viewContainer.style.display = 'none';
				editContainer.style.display = 'block';
			});

			cancelBtn.addEventListener('click', function() {
				editContainer.style.display = 'none';
				viewContainer.style.display = 'flex';
			});

			editForm.addEventListener('submit', function(e) {
				e.preventDefault();
				const newName = nameInput.value.trim();
				const newDesc = descInput.value.trim();
				/* A parent category keeps its top-level position */
				const newParent = parentSelect ? parentSelect.value : '';
				if (!newName) return;

				saveBtn.disabled = true;
				cancelBtn.disabled = true;

				updateCategory(updateAction.href, id, newName, newDesc, newParent)
					.then(function() {
						/* Reload so the tree reflects any re-parenting */
						loadCategories();
					})
					.catch(function(err) {
						console.error(err);
						alert(forumsCategoriesAdmin.dataset.labelErrorUpdating || 'Error updating category.');
						saveBtn.disabled = false;
						cancelBtn.disabled = false;
					});
			});

			editContainer.appendChild(editForm);
			li.appendChild(editContainer);
		}

		return li;
	}

	function createCategory(name, desc, erc, parentId) {
		const body = {
			categoryName: name,
			categoryName_i18n: { en_US: name },
			categoryDescription: desc || ''
		};
		if (erc) body.externalReferenceCode = erc;
		if (parentId) body[PARENT_FK] = parseInt(parentId, 10);

		return Liferay.Util.fetch(portalURL + '/o/c/forumcategories/scopes/' + scopeGroupId, {
			headers,
			method: 'POST',
			body: JSON.stringify(body)
		}).then(function(r) {
			if (!r.ok) throw new Error('Create failed');
			return r.json();
		});
	}

	function updateCategory(updateUrl, id, name, desc, parentId) {
		const url = updateUrl || (portalURL + '/o/c/forumcategories/' + id);

		const body = {
			categoryName: name,
			categoryName_i18n: { en_US: name },
			categoryDescription: desc || ''
		};
		/* 0 unsets the relationship (promotes the category back to top-level) */
		body[PARENT_FK] = parentId ? parseInt(parentId, 10) : 0;

		return Liferay.Util.fetch(url, {
			headers,
			method: 'PATCH',
			body: JSON.stringify(body)
		}).then(function(r) {
			if (!r.ok) throw new Error('Update failed');
			return r.json();
		});
	}

	function showConfirmModal(message, confirmLabel, onConfirm) {
		const existing = document.getElementById('forumsCatAdminConfirmModal');
		if (existing) existing.remove();

		const modal = document.createElement('div');
		modal.id = 'forumsCatAdminConfirmModal';
		modal.className = 'modal';
		modal.style.display = 'flex';
		modal.style.backgroundColor = 'rgba(0,0,0,0.5)';
		modal.style.zIndex = '1050';
		modal.setAttribute('tabindex', '-1');
		modal.setAttribute('role', 'dialog');
		modal.setAttribute('aria-modal', 'true');
		modal.setAttribute('aria-labelledby', 'forumsCatAdminConfirmHeading');

		modal.innerHTML = `
			<div class="modal-dialog modal-dialog-sm modal-dialog-centered modal-danger">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title" tabindex="-1">
							<div class="modal-title-indicator">
								<svg class="lexicon-icon lexicon-icon-exclamation-full" role="presentation"><use href="${clayIconsUrl}#exclamation-full"></use></svg>
							</div>
							<span id="forumsCatAdminConfirmHeading">${Liferay.Util.escapeHTML(confirmLabel)}</span>
						</h1>
						<button class="close btn btn-unstyled" type="button" id="forumsCatAdminConfirmClose" aria-label="${Liferay.Util.escapeHTML(forumsCategoriesAdmin.dataset.labelCancel || 'Cancel')}">
							<svg class="lexicon-icon lexicon-icon-times" focusable="false" role="presentation"><use href="${clayIconsUrl}#times"></use></svg>
						</button>
					</div>
					<div class="modal-body">
						<div class="liferay-modal-body">${Liferay.Util.escapeHTML(message)}</div>
					</div>
					<div class="modal-footer">
						<div class="modal-item-last">
							<div class="btn-group-spaced" role="group">
								<button class="btn btn-secondary" type="button" id="forumsCatAdminConfirmCancel">${Liferay.Util.escapeHTML(forumsCategoriesAdmin.dataset.labelCancel || 'Cancel')}</button>
								<button class="btn btn-danger" type="button" id="forumsCatAdminConfirmOk">${Liferay.Util.escapeHTML(confirmLabel)}</button>
							</div>
						</div>
					</div>
				</div>
			</div>`;

		document.body.appendChild(modal);
		const previousFocus = document.activeElement;

		function onKeydown(e) {
			if (e.key === 'Escape') closeModal();
		}

		function closeModal() {
			document.removeEventListener('keydown', onKeydown);
			modal.remove();
			if (previousFocus) previousFocus.focus();
		}

		modal.querySelector('#forumsCatAdminConfirmCancel').addEventListener('click', closeModal);
		modal.querySelector('#forumsCatAdminConfirmClose').addEventListener('click', closeModal);
		modal.querySelector('#forumsCatAdminConfirmOk').addEventListener('click', function() {
			closeModal();
			onConfirm();
		});
		modal.addEventListener('click', function(e) {
			if (e.target === modal) closeModal();
		});
		document.addEventListener('keydown', onKeydown);

		modal.querySelector('.modal-title').focus();
	}

	function deleteCategory(deleteUrl, id, subcategoryCount) {
		let message = forumsCategoriesAdmin.dataset.labelConfirmDelete || 'Are you sure you want to delete this category?';

		/* The self-relationship cascades: warn that the subtree goes too */
		if (subcategoryCount > 0) {
			const cascadeMsg = forumsCategoriesAdmin.dataset.labelConfirmDeleteCategoryWithSubcategories
				|| 'This category has {0} subcategories. Deleting it will also delete them and all of their topics.';
			message = cascadeMsg.replace('{0}', subcategoryCount);
		}

		const confirmLabel = forumsCategoriesAdmin.dataset.labelDelete || 'Delete';
		showConfirmModal(message, confirmLabel, function() {
			const url = deleteUrl || (portalURL + '/o/c/forumcategories/' + id);
			Liferay.Util.fetch(url, {
				headers,
				method: 'DELETE'
			})
			.then(function(r) {
				if (r.ok) loadCategories();
				else alert(forumsCategoriesAdmin.dataset.labelFailedDelete || 'Failed to delete category.');
			})
			.catch(function(err) {
				console.error(err);
				alert(forumsCategoriesAdmin.dataset.labelErrorDelete || 'Error deleting category.');
			});
		});
	}

	if (seedBtn) {
		seedBtn.addEventListener('click', function() {
			seedBtn.disabled = true;
			seedBtn.textContent = forumsCategoriesAdmin.dataset.labelSeeding || 'Seeding...';

			const promises = defaultCategories.map(function({name, desc, erc}) {
				return createCategory(name, desc, erc).catch(function(e) { console.error(e); });
			});

			Promise.all(promises).then(function() {
				seedBtn.disabled = false;
				seedBtn.textContent = forumsCategoriesAdmin.dataset.labelSeedDefault || 'Seed Default Categories';
				if (seedStatus) {
					seedStatus.style.display = 'inline';
					setTimeout(function() { seedStatus.style.display = 'none'; }, 3000);
				}
				loadCategories();
			});
		});
	}

	if (addForm) {
		addForm.addEventListener('submit', function(e) {
			e.preventDefault();
			const name = addName.value.trim();
			const desc = addDesc.value.trim();
			const parentId = addParent ? addParent.value : '';

			if (!name) return;

			addBtn.disabled = true;
			createCategory(name, desc, null, parentId)
			.then(function() {
				addName.value = '';
				addDesc.value = '';
				if (addParent) addParent.value = '';
				addBtn.disabled = false;
				loadCategories();
			})
			.catch(function(err) {
				console.error(err);
				alert(forumsCategoriesAdmin.dataset.labelErrorCreating || 'Error creating category.');
				addBtn.disabled = false;
			});
		});
	}

	loadCategories();
}
