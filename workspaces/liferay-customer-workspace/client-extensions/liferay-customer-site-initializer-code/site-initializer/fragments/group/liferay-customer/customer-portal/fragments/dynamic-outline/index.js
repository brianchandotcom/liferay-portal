/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.addEventListener('load', () => {
	const dynamicOutline = fragmentElement.querySelector(
		`#dynamicOutline${fragmentNamespace}`
	);
	const headers = document.querySelectorAll(
		`.${configuration.targetWrapper} h1, .${configuration.targetWrapper} h2, .${configuration.targetWrapper} h3`
	);
	const outlineMap = new Map();

	const scrollToHeader = (header) => {
		header.scrollIntoView({behavior: 'smooth', block: 'start'});
	};

	const setActiveLink = (link) => {
		fragmentElement.querySelectorAll('.active').forEach((element) => {
			element.classList.remove('active');
		});

		if (link) {
			link.classList.add('active');
		}
	};

	headers.forEach((header, index) => {
		if (!header.id) {
			header.id = 'section-' + index;
		}

		header.style.scrollMarginTop = '200px';

		const a = document.createElement('a');
		const li = document.createElement('li');

		a.href = '#' + header.id;
		a.id = `dynamicOutline${fragmentNamespace}-${header.id}`;
		a.textContent = header.textContent;

		a.addEventListener('click', (event) => {
			event.preventDefault();

			scrollToHeader(header);
		});

		li.appendChild(a);

		dynamicOutline.appendChild(li);

		outlineMap.set(header.id, a);
	});

	const intersectingHeaders = new Set();

	const observer = new IntersectionObserver((entries) => {
		entries.forEach((entry) => {
			if (entry.isIntersecting) {
				intersectingHeaders.add(entry.target);
			}
			else {
				intersectingHeaders.delete(entry.target);
			}
		});

		let lastIntersectingHeader = null;

		for (const header of headers) {
			if (intersectingHeaders.has(header)) {
				lastIntersectingHeader = header;
			}
		}

		if (lastIntersectingHeader) {
			setActiveLink(outlineMap.get(lastIntersectingHeader.id));
		}
	});

	for (const header of headers) {
		observer.observe(header);
	}
});
