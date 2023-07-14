/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		const highlightAlertElement = document.getElementById(
			HIGHLIGHT_ALERT_ID
		);

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
					textMatchElement.title = urlSearchParams.get(
						HIGHLIGHT_PARAM
					);

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
