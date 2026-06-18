/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const container = fragmentElement.querySelector('.category-badges');
	const source = fragmentElement.querySelector('.category-badges-source');

	if (!container || !source) {
		return;
	}

	const text = source.textContent.trim();

	if (!text) {
		container.classList.add('d-none');

		return;
	}

	const names = text
		.split(/\s*,\s*/)
		.map((name) => name.trim())
		.filter(Boolean);

	container.innerHTML = '';

	names.forEach((name) => {
		const pill = document.createElement('span');

		pill.classList.add('category-badges-pill');
		pill.textContent = name;

		container.appendChild(pill);
	});
})();
