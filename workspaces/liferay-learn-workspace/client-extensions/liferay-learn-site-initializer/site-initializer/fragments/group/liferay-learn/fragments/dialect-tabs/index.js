/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const dropdown = fragmentElement.querySelector('.navbar-collapse');
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const editMode = layoutMode === 'edit';
const tabItems = fragmentElement.querySelectorAll(
	`[data-fragment-namespace="${fragmentNamespace}"].nav-link`
);

let tabIndex = 0;
const tabPanel = fragmentElement.querySelector('.tab-panel');
const tabPanelItems = fragmentElement.querySelectorAll(
	`[data-fragment-namespace="${fragmentNamespace}"].tab-panel-item`
);

function activeTab(item) {
	tabItems.forEach((tabItem) => {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
	});

	if (item === null) {
		return;
	}

	item.setAttribute('aria-selected', true);
	item.classList.add('active');
}

function activeTabPanel(item) {
	tabPanelItems.forEach((tabPanelItem) => {
		tabPanelItem.classList.add('d-none');
	});

	if (item === null) {
		return;
	}

	item.classList.remove('d-none');
}

function handleDropdown(event, item) {
	event.preventDefault();
	dropdown.classList.toggle('show');

	const ariaExpanded = dropdownButton.getAttribute('aria-expanded');
	dropdownButton.setAttribute(
		'aria-expanded',
		ariaExpanded === 'false' ? true : false
	);

	if (item) {
		handleDropdownButtonName(item);
	}
}

function handleDropdownButtonName(item) {
	if (item === null) {
		return;
	}

	const tabText =
		item.querySelector('lfr-editable') ||
		item.querySelector('.navbar-text-truncate');

	if (tabText) {
		dropdownButton.querySelector('.navbar-text-truncate').innerHTML =
			tabText.textContent;
	}
}

function openTabPanel(event, i) {
	const currentTarget = event.currentTarget;
	const target = event.target;
	const isEditable =
		target.hasAttribute('data-lfr-editable-id') ||
		target.hasAttribute('contenteditable');
	const dropdownIsOpen = JSON.parse(
		dropdownButton.getAttribute('aria-expanded')
	);

	const isCurrentTab = tabItems[i].classList.contains('active');

	if (!isEditable || !editMode) {
		if (dropdownIsOpen) {
			handleDropdown(event, currentTarget);
		}

		currentTarget.focus();

		if (isCurrentTab) {
			activeTab(null);
			activeTabPanel(null);
			tabIndex = -1;
		}
		else {
			activeTab(currentTarget);
			activeTabPanel(tabPanelItems[i]);
			tabIndex = i;
		}
	}

	if (configuration.offClickHidePanel) {
		const outsideClickListener = (event) => {
			if (!tabPanel.contains(event.target)) {
				activeTab(null);
				activeTabPanel(null);
				removeClickListener();
			}
		};
		const removeClickListener = () => {
			document.removeEventListener('click', outsideClickListener);
		};

		event.stopPropagation();

		document.addEventListener('click', outsideClickListener);
	}
}

function main() {
	const initialState = !tabIndex || tabIndex >= tabItems.length;

	let tabItemSelected = null;

	if (configuration.defaultShowPanel) {
		tabItemSelected = tabItems[0];
	}

	if (!configuration.defaultShowPanel) {
		tabItems.forEach((item, i) => {
			item.addEventListener('click', (event) => {
				openTabPanel(event, i);
			});
		});
	}
	else if (initialState) {
		tabItems.forEach((item, i) => {
			if (!i) {
				activeTab(item);
			}
			item.addEventListener('click', (event) => {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach((item, i) => {
			if (!i) {
				activeTabPanel(item);
			}
		});
	}
	else {
		tabItemSelected = tabItems[tabIndex];
		tabItems.forEach((item, i) => {
			activeTab(tabItems[tabIndex]);
			item.addEventListener('click', (event) => {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach(() => {
			activeTabPanel(tabPanelItems[tabIndex]);
		});
	}

	dropdownButton.addEventListener('click', (event) => {
		handleDropdown(event);
	});
	handleDropdownButtonName(tabItemSelected);
}

main();
