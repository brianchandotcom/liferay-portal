/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const forumsCategoriesAdmin = fragmentElement.querySelector(
	'#forumsCategoriesAdmin'
);

/* Category query string. pageSize and sort come from fragment configuration;
   a blank sort omits the parameter entirely, which is needed on databases
   that cannot sort on a Text object field (Hypersonic raises "data type cast
   needed for parameter or null literal"). */
function categoryQuery(dataset) {
	const size = dataset.categoryPageSize || '100';
	const sort = (dataset.categorySort || '').trim();

	return (
		'?pageSize=' +
		encodeURIComponent(size) +
		(sort ? '&sort=' + encodeURIComponent(sort) : '')
	);
}

if (forumsCategoriesAdmin) {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
	const portalURL = Liferay.ThemeDisplay.getPortalURL();
	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
	const clayIconsUrl =
		Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
	const headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
	};

	/* FK exposed by the ForumCategory self-relationship (0 / absent = top-level) */
	const PARENT_FK = 'r_categorySubcategories_c_forumCategoryId';

	/* Subcategories are intentionally capped at ONE level.
	   This is a constant, NOT a configuration option: a configurable depth
	   recreates the unbounded-nesting problem this cap exists to prevent.
	   Categories cut where permissions and audiences cut; tags handle topics. */
	const MAX_DEPTH = 1;

	const cardEl = forumsCategoriesAdmin.querySelector(
		'.forums-categories-admin__card'
	);
	const noPermissionsEl = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminNoPermissions'
	);
	const seedSection = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminSeedSection'
	);
	const seedBtn = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminSeedBtn'
	);
	const seedStatus = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminSeedStatus'
	);
	const addHeading = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminAddHeading'
	);
	const addForm = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminAddForm'
	);
	const addName = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminCatName'
	);
	const addDesc = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminCatDesc'
	);
	const addParent = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminCatParent'
	);
	const addBtn = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminAddBtn'
	);
	const listEl = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminCategoryList'
	);
	const loadingEl = forumsCategoriesAdmin.querySelector(
		'#forumsCategoriesAdminLoading'
	);

	/* Track whether the current user has create permission */
	let canCreate = false;

	const topLevelLabel =
		forumsCategoriesAdmin.dataset.labelTopLevel || 'None (Top Level)';

	const {
		category1Desc,
		category1ERC,
		category1Name,
		category2Desc,
		category2ERC,
		category2Name,
		category3Desc,
		category3ERC,
		category3Name,
		category4Desc,
		category4ERC,
		category4Name,
		category5Desc,
		category5ERC,
		category5Name,
	} = configuration;

	const defaultCategories = [
		{description: category1Desc, erc: category1ERC, name: category1Name},
		{description: category2Desc, erc: category2ERC, name: category2Name},
		{description: category3Desc, erc: category3ERC, name: category3Name},
		{description: category4Desc, erc: category4ERC, name: category4Name},
		{description: category5Desc, erc: category5ERC, name: category5Name},
	].filter(({name}) => {
		return name;
	});

	/* --- Hierarchy helpers ---------------------------------------------- */

	const getParentId = function (cat) {
		return Number(cat[PARENT_FK]) || 0;
	};

	/* Build {byId, childrenOf} from a flat category list.
	   A category whose parent is missing — or whose parent is itself a child,
	   which only the REST API can produce — is normalized to top-level so the
	   UI stays coherent and never hides an entry. */
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

		return {byId, childrenOf};
	};

	const hasChildren = function (id, childrenOf) {
		return !!(childrenOf[id] || []).length;
	};

	/* Fill a <select> with the categories eligible to be a parent.
	   THIS IS WHERE THE CAP IS ENFORCED: only top-level categories are
	   offered, so a new/edited category can never land deeper than
	   MAX_DEPTH. Categories in excludeIds (the entry itself) are omitted. */
	const populateParentSelect = function (
		selectEl,
		{childrenOf},
		selectedId,
		excludeIds = []
	) {
		selectEl.innerHTML = '';

		const topOption = document.createElement('option');
		topOption.value = '';
		topOption.textContent = topLevelLabel;
		selectEl.appendChild(topOption);

		(childrenOf[0] || []).forEach(({categoryName, id}) => {
			if (excludeIds.indexOf(id) !== -1) {
				return;
			}

			const opt = document.createElement('option');
			opt.value = id;
			opt.textContent =
				categoryName ||
				forumsCategoriesAdmin.dataset.labelUnnamed ||
				'Unnamed';
			if (String(id) === String(selectedId)) {
				opt.selected = true;
			}
			selectEl.appendChild(opt);
		});
	};

	/* --- Data access ----------------------------------------------------- */

	const loadCategories = function () {
		if (loadingEl) {
			loadingEl.style.display = 'block';
		}
		listEl.innerHTML = '';

		Liferay.Util.fetch(
			portalURL +
				'/o/c/forumcategories/scopes/' +
				scopeGroupId +
				categoryQuery(forumsCategoriesAdmin.dataset),
			{
				headers,
				method: 'GET',
			}
		)
			.then((r) => {
				return r.json();
			})
			.then((data) => {
				if (loadingEl) {
					loadingEl.style.display = 'none';
				}

				/* HATEOAS: check collection-level actions for create permission */
				const {actions} = data;
				canCreate = !!(
					actions &&
					(actions['create'] || actions['post'] || actions['POST'])
				);

				if (canCreate) {

					/* User has admin-level permissions — show the admin card */
					if (noPermissionsEl) {
						noPermissionsEl.style.display = 'none';
					}
					if (cardEl) {
						cardEl.style.display = '';
					}
					if (seedSection) {
						seedSection.style.display = '';
					}
					if (addHeading) {
						addHeading.style.display = '';
					}
					if (addForm) {
						addForm.style.display = '';
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
				const tree = buildTree(items);

				/* Refresh the add-form parent picker with the current tree */
				if (addParent) {
					populateParentSelect(addParent, tree, '', []);
				}

				if (!items.length) {
					listEl.innerHTML =
						'<li class="list-group-item text-secondary">' +
						Liferay.Util.escapeHTML(
							forumsCategoriesAdmin.dataset.labelNoCategories ||
								'No categories found.'
						) +
						'</li>';

					return;
				}

				/* Two tiers only: top-level categories, each followed by its children */
				const {childrenOf} = tree;
				(childrenOf[0] || []).forEach((cat) => {
					listEl.appendChild(renderCategoryItem(cat, 0, tree));
					(childrenOf[cat.id] || []).forEach((child) => {
						listEl.appendChild(renderCategoryItem(child, 1, tree));
					});
				});
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
				console.error(error);
			});
	};

	/* Build a single list row (with inline edit form) for one category */
	const renderCategoryItem = function (cat, depth, tree) {
		const {actions, categoryDescription, categoryName, id} = cat;
		const {childrenOf} = tree;

		const li = document.createElement('li');
		li.className = 'list-group-item flex-column align-items-start';
		if (depth > 0) {
			li.style.marginLeft = depth * 1.5 + 'rem';
		}

		const viewContainer = document.createElement('div');
		viewContainer.className =
			'd-flex justify-content-between align-items-center w-100';

		const infoDiv = document.createElement('div');
		infoDiv.className = 'd-flex flex-column flex-grow-1';

		const nameSpan = document.createElement('span');
		nameSpan.className = 'font-weight-bold';
		nameSpan.textContent =
			categoryName ||
			forumsCategoriesAdmin.dataset.labelUnnamed ||
			'Unnamed';

		const descSpan = document.createElement('span');
		descSpan.className = 'text-secondary small';
		descSpan.textContent = categoryDescription || '';

		infoDiv.appendChild(nameSpan);
		if (categoryDescription) {
			infoDiv.appendChild(descSpan);
		}

		viewContainer.appendChild(infoDiv);

		const actionsDiv = document.createElement('div');
		actionsDiv.className = 'd-flex';

		/* HATEOAS: only render edit button if the item-level actions include 'update' */
		const updateAction =
			actions &&
			(actions['update'] ||
				actions['patch'] ||
				actions['put'] ||
				actions['PATCH'] ||
				actions['PUT']);
		let editBtn = null;
		if (updateAction) {
			editBtn = document.createElement('button');
			editBtn.className = 'btn btn-sm btn-outline-secondary mr-2';
			editBtn.title = forumsCategoriesAdmin.dataset.labelEdit || 'Edit';
			editBtn.ariaLabel =
				forumsCategoriesAdmin.dataset.labelEdit || 'Edit';
			editBtn.setAttribute('data-tooltip-align', 'top');

			// XSS: clayIconsUrl is escaped by construction, from Liferay.ThemeDisplay

			editBtn.innerHTML =
				'<svg class="lexicon-icon lexicon-icon-pencil" role="presentation"><use href="' +
				clayIconsUrl +
				'#pencil"></use></svg>';
			actionsDiv.appendChild(editBtn);
		}

		/* HATEOAS: only render delete button if the item-level actions include 'delete' */
		if (actions && actions['delete']) {
			const delBtn = document.createElement('button');
			delBtn.className = 'btn btn-sm btn-outline-danger';
			delBtn.title =
				forumsCategoriesAdmin.dataset.labelDelete || 'Delete';
			delBtn.ariaLabel =
				forumsCategoriesAdmin.dataset.labelDelete || 'Delete';
			delBtn.setAttribute('data-tooltip-align', 'top');

			// XSS: clayIconsUrl is escaped by construction, from Liferay.ThemeDisplay

			delBtn.innerHTML =
				'<svg class="lexicon-icon lexicon-icon-trash" role="presentation"><use href="' +
				clayIconsUrl +
				'#trash"></use></svg>';
			delBtn.addEventListener('click', () => {
				deleteCategory(
					actions['delete'].href,
					id,
					(childrenOf[id] || []).length
				);
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
			const {
				labelCategoryName,
				labelDescription,
				labelHasSubcategories,
				labelParentCategory,
			} = forumsCategoriesAdmin.dataset;
			const labelName = Liferay.Util.escapeHTML(
				labelCategoryName || 'Category Name'
			);
			const labelDesc = Liferay.Util.escapeHTML(
				labelDescription || 'Description'
			);
			const labelParent = Liferay.Util.escapeHTML(
				labelParentCategory || 'Parent Category'
			);
			const labelHasSubs =
				labelHasSubcategories ||
				'A category with subcategories cannot be nested.';

			const parentFieldHtml = isParent
				? '<div class="form-group mb-3 mb-md-0">' +
					'<span class="text-secondary small">' +
					Liferay.Util.escapeHTML(labelHasSubs) +
					'</span>' +
					'</div>'
				: '<div class="form-group mb-3 mb-md-0">' +
					'<label for="' +
					parentFieldId +
					'" class="sr-only">' +
					labelParent +
					'</label>' +
					'<select class="form-control" id="' +
					parentFieldId +
					'" aria-label="' +
					labelParent +
					'"></select>' +
					'</div>';

			// XSS: every label is escaped by Liferay.Util.escapeHTML above, and each
			// field id is escaped by construction from the entry id

			editForm.innerHTML =
				'<div class="row align-items-end">' +
				'<div class="col-12 col-md">' +
				'<div class="form-group mb-3 mb-md-0">' +
				'<label for="' +
				nameFieldId +
				'" class="sr-only">' +
				labelName +
				'</label>' +
				'<input type="text" class="form-control" id="' +
				nameFieldId +
				'" aria-label="' +
				labelName +
				'" required>' +
				'</div>' +
				'</div>' +
				'<div class="col-12 col-md">' +
				'<div class="form-group mb-3 mb-md-0">' +
				'<label for="' +
				descFieldId +
				'" class="sr-only">' +
				labelDesc +
				'</label>' +
				'<input type="text" class="form-control" id="' +
				descFieldId +
				'" aria-label="' +
				labelDesc +
				'">' +
				'</div>' +
				'</div>' +
				'<div class="col-12 col-md">' +
				parentFieldHtml +
				'</div>' +
				'<div class="col-12 col-md-auto mt-3 mt-md-0">' +
				'<button type="submit" class="btn btn-primary mr-2">' +
				Liferay.Util.escapeHTML(
					forumsCategoriesAdmin.dataset.labelSave || 'Save'
				) +
				'</button>' +
				'<button type="button" class="btn btn-outline-secondary cancel-edit-btn">' +
				Liferay.Util.escapeHTML(
					forumsCategoriesAdmin.dataset.labelCancel || 'Cancel'
				) +
				'</button>' +
				'</div>' +
				'</div>';

			const nameInput = editForm.querySelector('#' + nameFieldId);
			const descInput = editForm.querySelector('#' + descFieldId);
			const parentSelect = editForm.querySelector('#' + parentFieldId);
			const cancelBtn = editForm.querySelector('.cancel-edit-btn');
			const saveBtn = editForm.querySelector('button[type="submit"]');

			editBtn.addEventListener('click', () => {
				nameInput.value = categoryName || '';
				descInput.value = categoryDescription || '';
				if (parentSelect) {

					/* Exclude self; the picker already offers top-level only */
					populateParentSelect(
						parentSelect,
						tree,
						getParentId(cat) || '',
						[id]
					);
				}
				viewContainer.style.display = 'none';
				editContainer.style.display = 'block';
			});

			cancelBtn.addEventListener('click', () => {
				editContainer.style.display = 'none';
				viewContainer.style.display = 'flex';
			});

			editForm.addEventListener('submit', (event) => {
				event.preventDefault();
				const newName = nameInput.value.trim();
				const newDesc = descInput.value.trim();

				/* A parent category keeps its top-level position */
				const newParent = parentSelect ? parentSelect.value : '';
				if (!newName) {
					return;
				}

				saveBtn.disabled = true;
				cancelBtn.disabled = true;

				updateCategory(
					updateAction.href,
					id,
					newName,
					newDesc,
					newParent
				)
					.then(() => {

						/* Reload so the tree reflects any re-parenting */
						loadCategories();
					})
					.catch((error) => {
						console.error(error);
						alert(
							forumsCategoriesAdmin.dataset.labelErrorUpdating ||
								'Error updating category.'
						);
						saveBtn.disabled = false;
						cancelBtn.disabled = false;
					});
			});

			editContainer.appendChild(editForm);
			li.appendChild(editContainer);
		}

		return li;
	};

	const createCategory = function (name, description, erc, parentId) {
		const body = {
			categoryDescription: description || '',
			categoryName: name,
			categoryName_i18n: {[defaultLanguageId]: name},
		};
		if (erc) {
			body.externalReferenceCode = erc;
		}
		if (parentId) {
			body[PARENT_FK] = parseInt(parentId, 10);
		}

		return Liferay.Util.fetch(
			portalURL + '/o/c/forumcategories/scopes/' + scopeGroupId,
			{
				body: JSON.stringify(body),
				headers,
				method: 'POST',
			}
		).then((r) => {
			if (!r.ok) {
				throw new Error('Create failed');
			}

			return r.json();
		});
	};

	const updateCategory = function (
		updateUrl,
		id,
		name,
		description,
		parentId
	) {
		const url = updateUrl || portalURL + '/o/c/forumcategories/' + id;

		const body = {
			categoryDescription: description || '',
			categoryName: name,
			categoryName_i18n: {[defaultLanguageId]: name},
		};

		/* 0 unsets the relationship (promotes the category back to top-level) */
		body[PARENT_FK] = parentId ? parseInt(parentId, 10) : 0;

		return Liferay.Util.fetch(url, {
			body: JSON.stringify(body),
			headers,
			method: 'PATCH',
		}).then((r) => {
			if (!r.ok) {
				throw new Error('Update failed');
			}

			return r.json();
		});
	};

	const showConfirmModal = function (message, confirmLabel, onConfirm) {
		const existing = document.getElementById('forumsCatAdminConfirmModal');
		if (existing) {
			existing.remove();
		}

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

		// XSS: every value is escaped by Liferay.Util.escapeHTML below

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
			.querySelector('#forumsCatAdminConfirmCancel')
			.addEventListener('click', closeModal);
		modal
			.querySelector('#forumsCatAdminConfirmClose')
			.addEventListener('click', closeModal);
		modal
			.querySelector('#forumsCatAdminConfirmOk')
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

	const deleteCategory = function (deleteUrl, id, subcategoryCount) {
		let message =
			forumsCategoriesAdmin.dataset.labelConfirmDelete ||
			'Are you sure you want to delete this category?';

		/* The self-relationship cascades: warn that the subtree goes too */
		if (subcategoryCount > 0) {
			const cascadeMsg =
				forumsCategoriesAdmin.dataset
					.labelConfirmDeleteCategoryWithSubcategories ||
				'This category has {0} subcategories. Deleting it will also delete them and all of their topics.';
			message = cascadeMsg.replace('{0}', subcategoryCount);
		}

		const confirmLabel =
			forumsCategoriesAdmin.dataset.labelDelete || 'Delete';
		showConfirmModal(message, confirmLabel, () => {
			const url = deleteUrl || portalURL + '/o/c/forumcategories/' + id;
			Liferay.Util.fetch(url, {
				headers,
				method: 'DELETE',
			})
				.then((r) => {
					if (r.ok) {
						loadCategories();
					}
					else {
						alert(
							forumsCategoriesAdmin.dataset.labelFailedDelete ||
								'Failed to delete category.'
						);
					}
				})
				.catch((error) => {
					console.error(error);
					alert(
						forumsCategoriesAdmin.dataset.labelErrorDelete ||
							'Error deleting category.'
					);
				});
		});
	};

	if (seedBtn) {
		seedBtn.addEventListener('click', () => {
			seedBtn.disabled = true;
			seedBtn.textContent =
				forumsCategoriesAdmin.dataset.labelSeeding || 'Seeding...';

			const promises = defaultCategories.map(
				({description, erc, name}) => {
					return createCategory(name, description, erc).catch(
						(event) => {
							console.error(event);
						}
					);
				}
			);

			Promise.all(promises)
				.then(() => {
					seedBtn.disabled = false;
					seedBtn.textContent =
						forumsCategoriesAdmin.dataset.labelSeedDefault ||
						'Seed Default Categories';
					if (seedStatus) {
						seedStatus.style.display = 'inline';
						setTimeout(() => {
							seedStatus.style.display = 'none';
						}, 3000);
					}
					loadCategories();
				})
				.catch((error) => {
					console.error('ForumsCategoriesAdmin seed error:', error);
				});
		});
	}

	if (addForm) {
		addForm.addEventListener('submit', (event) => {
			event.preventDefault();
			const name = addName.value.trim();
			const description = addDesc.value.trim();
			const parentId = addParent ? addParent.value : '';

			if (!name) {
				return;
			}

			addBtn.disabled = true;
			createCategory(name, description, null, parentId)
				.then(() => {
					addName.value = '';
					addDesc.value = '';
					if (addParent) {
						addParent.value = '';
					}
					addBtn.disabled = false;
					loadCategories();
				})
				.catch((error) => {
					console.error(error);
					alert(
						forumsCategoriesAdmin.dataset.labelErrorCreating ||
							'Error creating category.'
					);
					addBtn.disabled = false;
				});
		});
	}

	loadCategories();
}
