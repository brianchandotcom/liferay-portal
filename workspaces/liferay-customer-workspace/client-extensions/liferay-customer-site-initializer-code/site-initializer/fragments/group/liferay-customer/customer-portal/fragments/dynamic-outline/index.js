/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.addEventListener('load', () => {
	const headers = document.querySelectorAll(
		`.${configuration.className} h1, .${configuration.className} h2, .${configuration.className} h3`
	);

	const tocContainer = fragmentElement.querySelector('#dynamic-outline-list');
	const tocLinksMap = new Map();

	const scrollToHeader = (header) => {
		header.scrollIntoView({behavior: 'smooth', block: 'start'});
	};

	const setActiveLink = (link) => {
		fragmentElement
			.querySelectorAll('.dynamic-toc li a')
			.forEach((element) => {
				element.classList.remove('active');
			});

		if (link) {
			link.classList.add('active');
		}
	};

	const configurationElements = document.querySelectorAll(
		`.${configuration.className}`
	);
	configurationElements.forEach((element) =>
		element.classList.add('dynamic-outline-article')
	);

	headers.forEach((header, index) => {
		if (!header.id) {
			header.id = 'section-' + index;
		}

		const a = document.createElement('a');
		const li = document.createElement('li');

		a.href = '#' + header.id;
		a.id = 'dynamic-toc-' + header.id;
		a.textContent = header.textContent;

		a.addEventListener('click', (event) => {
			event.preventDefault();
			scrollToHeader(header);
		});

		li.appendChild(a);
		tocContainer.appendChild(li);

		tocLinksMap.set(header.id, a);

		header.addEventListener('click', (event) => {
			event.preventDefault();
			scrollToHeader(header);
		});
	});

	window.addEventListener('scroll', () => {
		let currentSection = null;

		for (const header of headers) {
			const offset = header.getBoundingClientRect().top;

			if (offset <= 200) {
				currentSection = header;
			}
			else {
				break;
			}
		}

		if (currentSection) {
			const link = tocLinksMap.get(currentSection.id);
			setActiveLink(link);
		}
	});
});
