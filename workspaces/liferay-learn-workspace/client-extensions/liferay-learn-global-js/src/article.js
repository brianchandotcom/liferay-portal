/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DropdownProvider from './DropdownProvider';

function initArticle() {

	// Table of contents reading indicator

	const headings = document.querySelectorAll('.learn-article-content h2');

	let activeIndex;
	const articleTOC = document.getElementById('articleTOC');
	const targets = [];

	if (headings && !!headings.length) {
		if (articleTOC) {
			articleTOC.innerHTML = '';
		}

		headings.forEach((heading) => {
			const id = heading.querySelector('a').hash.replace('#', '');

			if (articleTOC) {
				articleTOC.innerHTML += `
				<li class="learn-article-nav-item">
					<a href="#${id}" id="toc-${id}">
						${heading.innerText}
					</a>
				</li>`;
			}

			targets.push({id, isIntersecting: false});
		});
	}
	else if (articleTOC) {
		articleTOC.closest('.learn-article-page-nav').classList.add('hide');
	}

	const callback = (entries) => {
		entries.forEach((entry) => {
			const index = targets.findIndex(
				(target) => target.id === entry.target.id
			);

			targets[index].isIntersecting = entry.isIntersecting;

			if (!targets[activeIndex] || !targets[activeIndex].isIntersecting) {
				setActiveIndex();
			}
		});

		if (targets[activeIndex]) {
			toggleActiveClass(targets[activeIndex].id);
		}
	};

	const observer = new IntersectionObserver(callback);

	const setActiveIndex = () => {
		activeIndex = targets.findIndex(
			(target) => target.isIntersecting === true
		);
	};

	const toggleActiveClass = (id) => {
		targets.forEach((target) => {
			const node = document.getElementById(`toc-${target.id}`);

			if (node) {
				node.classList.remove('selected');
			}
		});

		const activeNode = document.getElementById(`toc-${id}`);

		if (activeNode) {
			activeNode.classList.add('selected');
		}
	};

	targets.forEach((target) => {
		const node = target.id ? document.getElementById(target.id) : null;

		if (node) {
			observer.observe(node);
		}
	});

	new DropdownProvider(
		'.learn-dropdown',
		'.learn-dropdown-menu',
		'show',
		true
	);
}

document.addEventListener('DOMContentLoaded', initArticle);

Liferay.on('endNavigate', initArticle);
