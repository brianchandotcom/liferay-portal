/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Mark from 'mark.js';

/**
 * Adds an alert with the text being highlighted and provides a way to remove
 * the highlight styling.
 */

const HIGHLIGHT_ALERT_ID = 'highlightAlert';
const HIGHLIGHT_TEXT_MATCH_ID = 'highlightTextMatch';
const REMOVE_HIGHLIGHT_LINK_ID = 'removeHighlightLink';

const HIGHLIGHT_PARAM = 'highlight';

const TRUNCATE_LENGTH = 50;

function initHighlightingAlert() {
	const urlSearchParams = new URLSearchParams(window.location.search);

	if (urlSearchParams.has(HIGHLIGHT_PARAM)) {
		const highlightAlertElement =
			document.getElementById(HIGHLIGHT_ALERT_ID);

		if (highlightAlertElement) {
			const articleBody = document.querySelector('.article-body');
			if (articleBody) {
				const mark = new Mark(articleBody);

				// Add text being highlighted

				const textMatchElement = document.getElementById(
					HIGHLIGHT_TEXT_MATCH_ID
				);

				if (textMatchElement) {
					let searchTerm = urlSearchParams.get(HIGHLIGHT_PARAM);

					if (searchTerm.length > TRUNCATE_LENGTH) {
						searchTerm =
							urlSearchParams
								.get(HIGHLIGHT_PARAM)
								.slice(0, TRUNCATE_LENGTH) + '...';
					}

					textMatchElement.textContent = ' "' + searchTerm + '"';
					textMatchElement.title =
						urlSearchParams.get(HIGHLIGHT_PARAM);

					mark.unmark();
					mark.mark(searchTerm, {
						className: 'highlighted',
					});
				}

				// Setup remove highlight link to clear highlights and dismiss alert

				const removeHighlightLinkElement = document.getElementById(
					REMOVE_HIGHLIGHT_LINK_ID
				);

				if (removeHighlightLinkElement) {
					removeHighlightLinkElement.addEventListener('click', () => {
						mark.unmark();
						highlightAlertElement.remove();
					});
				}

				// Show alert

				highlightAlertElement.classList.remove('hide');
			}
		}
	}
}

// Initialize after DOM is ready

window.onload = initHighlightingAlert;
Liferay.on('endNavigate', initHighlightingAlert);
