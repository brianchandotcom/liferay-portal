/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const editMode = layoutMode === 'edit';
const menu = fragmentElement.querySelector('.dropdown-fragment-menu');
const toggle = fragmentElement.querySelector('.dropdown-fragment-toggle');

const menuDropdownCaret = toggle.querySelector('.menu-dropdown-caret');
const toggleEditable = toggle.querySelector('[data-lfr-editable-id]');
const regularMenuWidth = 240;
const withinMasterLayout = fragmentElement.parentElement.classList.contains(
	'page-editor__fragment-content--master'
);

let alignMenuInterval;

function alignMenu() {
	const toggleBoundingClientRect = toggle.getBoundingClientRect();
	const parentElement =
		document.querySelector('.page-editor__layout-viewport__resizer') ||
		document.body;

	const parentBoundingClientRect = parentElement.getBoundingClientRect();
	const wrapperRect = document
		.querySelector('#wrapper')
		?.getBoundingClientRect();
	const isRTL =
		Liferay.Language.direction?.[themeDisplay?.getLanguageId()] === 'rtl';

	menu.style.top = `${toggleBoundingClientRect.bottom}px`;

	if (configuration.panelType === 'mega-menu') {
		menu.style.left = `${parentBoundingClientRect.left}px`;
		menu.style.width = `${parentBoundingClientRect.width}px`;
	}
	else if (configuration.panelType === 'regular') {
		menu.style.width = `${regularMenuWidth}px`;

		if (
			toggleBoundingClientRect.left + regularMenuWidth >= window.innerWidth ||
			(wrapperRect &&
				toggleBoundingClientRect.left + regularMenuWidth >= wrapperRect.width)
		) {
			menu.style.right = `${window.innerWidth - toggleBoundingClientRect.right}px`;
		}
		else {
			menu.style.right = null;
		}

		if (isRTL && toggleBoundingClientRect.right - regularMenuWidth < 0) {
			menu.style.left = `${toggleBoundingClientRect.left}px`;
		}
	}
	else if (configuration.panelType === 'full-width') {
		menu.style.width = `${fragmentElement.getBoundingClientRect().width}px`;
	}
}

function handleBodyClick(event) {
	if (!toggle.isConnected) {
		document.body.removeEventListener('click', handleBodyClick);

		return;
	}

	if (
		isShown() &&
		!toggle.contains(event.target) &&
		!menu.contains(event.target)
	) {
		toggleMenu();
	}
}

function handleDropdownHover() {
	if (!isShown()) {
		toggleMenu();
	}
}

function handleDropdownLeave() {
	if (isShown()) {
		toggleMenu();
	}
}

function handleToggleClick(event) {
	if (!toggleEditable.contains(event.target) || !editMode) {
		toggleMenu();
	}
}

function handleWindowEvent() {
	if (!toggle.isConnected) {
		window.removeEventListener('resize', handleWindowEvent);
		window.removeEventListener('scroll', handleWindowEvent);

		return;
	}

	alignMenu();
}

function isShown() {
	return toggle.getAttribute('aria-expanded') === 'true';
}

function menuHasChildren() {
	const contentElement = editMode
		? menu.querySelector('lfr-drop-zone')
		: menu;

	return (
		contentElement &&
		!!Array.from(contentElement.firstElementChild.children).filter(
			(child) => child.tagName !== 'STYLE'
		).length
	);
}

function toggleMenu() {
	if (!menuHasChildren()) {
		return;
	}

	if (menu.style.display === 'block') {
		menu.style.display = 'none';
		toggle.setAttribute('aria-expanded', 'false');
		menuDropdownCaret.classList.remove('rotated'); // Remove rotação do caret

		window.removeEventListener('resize', handleWindowEvent);
		window.removeEventListener('scroll', handleWindowEvent);

		clearInterval(alignMenuInterval);
	}
	else {
		menu.style.display = 'block';
		toggle.setAttribute('aria-expanded', 'true');
		menuDropdownCaret.classList.add('rotated'); // Adiciona rotação ao caret

		alignMenu();

		window.addEventListener('resize', alignMenu);
		window.addEventListener('scroll', alignMenu);

		if (editMode && configuration.keepOpen) {
			alignMenuInterval = setInterval(alignMenu, 1000);
		}
	}
}

function main() {
	if (configuration.keepOpen && editMode && !withinMasterLayout) {
		toggleMenu();
	}
	else if (configuration.displayOnHover) {
		toggle.addEventListener('mouseenter', handleDropdownHover);
		toggle.addEventListener('mouseleave', handleDropdownLeave);

		menu.addEventListener('mouseenter', handleDropdownHover);
		menu.addEventListener('mouseleave', handleDropdownLeave);
	}
	else {
		toggle.addEventListener('click', handleToggleClick);
		document.body.addEventListener('click', handleBodyClick);
	}
}

main();
