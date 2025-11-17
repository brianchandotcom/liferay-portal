/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/portal/no-global-fetch */

/* eslint-disable no-undef */

const fetchRequest = async (input) => {
	const response = await fetch(input, {
		headers: {
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Cache-Control': 'max-age=30, stale-while-revalidate=30',
			'x-csrf-token': Liferay.authToken,
		},
	});

	return response.json();
};

const restArticle = async (requestId) => {
	const journalArticleResponse = await fetchRequest(
		`/o/headless-delivery/v1.0/structured-contents/${requestId}?fields=contentFields,relatedContents,taxonomyCategoryBriefs,title&nestedFields=embeddedTaxonomyCategory`
	);

	updateArticleContent(journalArticleResponse.contentFields);
	updateArticleLinks(journalArticleResponse.contentFields);
	updateArticleTitle(journalArticleResponse.title);
	updateLabelProductCapabilities(
		journalArticleResponse.taxonomyCategoryBriefs
	);
	updateLabelStatus(journalArticleResponse.taxonomyCategoryBriefs);
};

function createContentDiv(htmlContent) {
	const divElement = document.createElement('div');

	divElement.innerHTML = htmlContent;

	return divElement;
}

function createLabelSpan(classList, text) {
	const spanElement = document.createElement('span');

	spanElement.textContent = text;

	classList.forEach((className) => spanElement.classList.add(className));

	return spanElement;
}

function updateArticleContent(contentFields) {
	const articleContent = document.getElementById('articleContent');

	if (!articleContent) {
		return;
	}

	articleContent.innerHTML = '';

	contentFields.forEach((field) => {
		const trimmedContent = field.contentFieldValue?.data?.trim();

		if (trimmedContent && trimmedContent !== 'null') {
			const contentDiv = createContentDiv(trimmedContent);

			articleContent.appendChild(contentDiv);
		}
	});
}

function updateArticleLinks(contentFields) {
	const articleLink = document.getElementById('articleLink');

	if (articleLink) {
		articleLink.innerHTML = '';

		contentFields.forEach((field) => {
			let url = '';
			let urlTitle = '';

			field.nestedContentFields.forEach((nestedField) => {
				const nestedData = nestedField.contentFieldValue?.data;

				if (nestedData && nestedField.label.includes('Title')) {
					urlTitle = nestedData;
				}
				else {
					url = nestedData;
				}
			});

			if (urlTitle && urlTitle.trim() !== '' && urlTitle !== 'null') {
				const divElement = document.createElement('div');

				divElement.classList.add('d-flex', 'mb-2');

				const anchorElement = document.createElement('a');

				anchorElement.classList.add('link-container');
				anchorElement.href = url;
				anchorElement.target = '_blank';
				anchorElement.innerHTML =
					'<svg class="lexicon-icon lexicon-icon-link" role="presentation" viewBox="0 0 512 512">\n  <use xlink:href="/o/dialect-theme/images/clay/icons.svg#link"></use>\n </svg>';

				const titleSpan = document.createElement('span');

				titleSpan.innerHTML = urlTitle;

				anchorElement.appendChild(titleSpan);
				divElement.appendChild(anchorElement);
				articleLink.appendChild(divElement);
			}
		});
	}
}

function updateArticleTitle(title) {
	const articleTitle = document.getElementById('articleTitle');

	if (articleTitle) {
		articleTitle.innerHTML = title;
	}
}

function updateLabelProductCapabilities(taxonomyCategoryBriefs) {
	const labelProductCapabilities = document.getElementById(
		'labelProductCapabilities'
	);

	if (!labelProductCapabilities) {
		return;
	}

	labelProductCapabilities.innerHTML = '';

	taxonomyCategoryBriefs.forEach((taxonomyCategoryBrief) => {
		const taxonomyVocabularyName =
			taxonomyCategoryBrief.embeddedTaxonomyCategory
				.parentTaxonomyVocabulary.name;

		if (taxonomyVocabularyName === 'Product Capabilities') {
			const spanElement = document.createElement('span');

			spanElement.classList.add(
				'font-weight-normal',
				'label',
				'label-secondary',
				'label-tonal-info',
				'm-0',
				'mr-1',
				'px-2',
				'text-paragraph-sm'
			);
			spanElement.innerHTML = taxonomyCategoryBrief.taxonomyCategoryName;

			labelProductCapabilities.appendChild(spanElement);
		}
	});
}

function updateLabelStatus(taxonomyCategoryBriefs) {
	const labelStatus = document.getElementById('labelStatus');

	if (labelStatus) {
		labelStatus.innerHTML = '';

		taxonomyCategoryBriefs.forEach((brief) => {
			const taxonomyVocabularyName =
				brief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name;

			if (taxonomyVocabularyName === 'Release Status Previous') {
				const releaseStatusSpan = createLabelSpan(
					[
						'font-weight-normal',
						'label',
						'label-previous-status',
						'label-secondary',
						'label-tonal-secondary',
						'px-2',
						'text-paragraph-sm',
					],
					brief.taxonomyCategoryName
				);
				const iconArrowRightSpan = document.createElement('span');

				iconArrowRightSpan.classList.add('mr-1');
				iconArrowRightSpan.innerHTML =
					'<svg class="lexicon-icon lexicon-icon-order-arrow-right" role="presentation" viewBox="0 0 512 512">' +
					'<use xlink:href="/o/dialect-theme/images/clay/icons.svg#order-arrow-right"></use>' +
					'</svg>';

				labelStatus.appendChild(releaseStatusSpan);
				labelStatus.appendChild(iconArrowRightSpan);
			}
		});

		taxonomyCategoryBriefs.forEach((brief) => {
			const taxonomyVocabularyName =
				brief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name;

			if (taxonomyVocabularyName === 'Feature Availability') {
				const featureAvailabilitySpan = createLabelSpan(
					[
						'font-weight-normal',
						'label',
						'label-success',
						'label-tonal-success',
						'mb-3',
						'px-2',
						'text-paragraph-sm',
					],
					brief.taxonomyCategoryName
				);

				labelStatus.appendChild(featureAvailabilitySpan);
			}
		});
	}
}

(function () {
	const openSidetabButtons = document.querySelectorAll('.openSidetab');

	openSidetabButtons.forEach((button) => {
		button.addEventListener('click', (event) => {
			event.preventDefault();

			restArticle(button.dataset.requestId);

			document.getElementById('sidetabFeature').style.right = '0';
		});
	});

	document.getElementById('closeSidetab').addEventListener('click', () => {
		document.getElementById('sidetabFeature').style.right = '-31.875rem';
	});
})();
