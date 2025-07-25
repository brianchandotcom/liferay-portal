/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const _buildArticlesFilterQuery = (categories) => {
	const filterQueryArray = [];

	categories.forEach((category) => {
		filterQueryArray.push(`taxonomyCategoryIds/any(t:t eq ${category.id})`);
	});

	return `?filter=${filterQueryArray.join(' or ')}`;
};

const _buildButton = (id) => {
	const button = document.createElement('button');
	button.classList.add(
		'd-flex',
		'btn',
		'expand-controller',
		'p-0',
		'text-neutral-10'
	);
	button.id = `selector${id}`;
	button.innerHTML = `
        <svg class="lexicon-icon lexicon-icon-angle-right-small" role="presentation">
            <use href="${Liferay.Icons.spritemap}#angle-right-small" />
        </svg>`;

	return button;
};

const _buildDivision = (href, isActive, textContent) => {
	const division = document.createElement('div');
	division.classList.add(
		'align-items-center',
		'cp-category-side-panel-item',
		'd-flex',
		'flex-row',
		'font-weight-semi-bold',
		'justify-content-between',
		'p-2',
		'rounded'
	);
	division.classList.toggle('active', isActive);

	const anchor = document.createElement('a');
	anchor.classList.add('text-decoration-none', 'text-neutral-10');
	anchor.href = href;
	anchor.textContent = textContent;

	division.appendChild(anchor);

	return division;
};

const _buildSidePanel = (articles, categories, currentItemId) => {
	const baseURL = Liferay.ThemeDisplay.getCanonicalURL().split(/\/[wv]\//)[0];

	const sidePanel = document.createElement('ul');
	sidePanel.classList.add(
		'cp-category-side-panel-container',
		'd-flex',
		'flex-column',
		'list-unstyled',
		'p-2',
		'rounded'
	);

	categories.forEach((category) => {
		const parentItem = document.createElement('li');

		const parentItemDiv = _buildDivision(
			`${baseURL}/v/${category.id}`,
			currentItemId === category.id,
			category.name
		);

		const childrenArticles = articles.filter((article) => {
			return article.taxonomyCategoryBriefs
				.map((taxonomyCategoryBrief) => {
					return `${taxonomyCategoryBrief.taxonomyCategoryId}`;
				})
				.includes(category.id);
		});

		const childrenArticlesIds = childrenArticles.map((childArticle) => {
			return childArticle.key;
		});

		if (
			category.taxonomyCategories.items.length ||
			childrenArticles.length
		) {
			const parentItemButton = _buildButton(category.id);

			parentItemDiv.appendChild(parentItemButton);

			parentItem.appendChild(parentItemDiv);

			const childPanel = document.createElement('ul');
			childPanel.classList.add(
				'cp-category-side-panel-container',
				'd-flex',
				'flex-column',
				'list-unstyled',
				'pl-5',
				'py-2',
				'rounded',
				`selector${category.id}`
			);

			const expandIcon = parentItemButton.querySelector('.lexicon-icon');

			parentItemButton.addEventListener('click', (event) => {
				event.preventDefault();
				_toggleDisplay(childPanel);
				_toggleRotate(expandIcon);
			});

			const isChildPanelExpanded =
				currentItemId === category.id ||
				childrenArticlesIds.includes(`${currentItemId}`);
			childPanel.classList.toggle('hide-content', !isChildPanelExpanded);

			if (isChildPanelExpanded) {
				expandIcon.classList.add('rotate-90');
			}

			category.taxonomyCategories.items.forEach((taxonomyCategory) => {
				const childItem = document.createElement('li');

				const childItemDiv = _buildDivision(
					`${baseURL}/v/${taxonomyCategory.id}`,
					currentItemId === taxonomyCategory.id,
					taxonomyCategory.name
				);

				childItem.appendChild(childItemDiv);
				childPanel.appendChild(childItem);
			});

			childrenArticles.forEach((childArticle) => {
				const childItem = document.createElement('li');

				const childItemDiv = _buildDivision(
					`${baseURL}/w/${childArticle.friendlyUrlPath}`,
					currentItemId === childArticle.key,
					childArticle.title
				);

				childItem.appendChild(childItemDiv);
				childPanel.appendChild(childItem);
			});

			parentItem.appendChild(childPanel);
		}
		else {
			parentItem.appendChild(parentItemDiv);
		}

		sidePanel.appendChild(parentItem);
	});

	return sidePanel;
};

const _fetchArticles = async (categories) => {
	const filterQuery = _buildArticlesFilterQuery(categories);

	const folderId = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/structured-content-folders`,
		{
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'GET',
		}
	)
		.then((response) => response.json())
		.then((data) => {
			return data.items.filter((structuredContentFolder) => {
				return structuredContentFolder.name === 'Articles';
			})[0].id;
		});

	const articles = await Liferay.Util.fetch(
		`/o/headless-delivery/v1.0/structured-content-folders/${folderId}/structured-contents${filterQuery}`,
		{
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'GET',
		}
	)
		.then((response) => response.json())
		.then((data) => {
			return data.items;
		});

	return articles;
};

const _fetchCategory = async (categoryId) => {
	const category = await Liferay.Util.fetch('/o/graphql', {
		body: JSON.stringify({
			query: `query {
				taxonomyCategory (taxonomyCategoryId: "${categoryId}") {
					id
					name
					taxonomyCategories {
						items {
							id
							name
							taxonomyCategories {
								items {
									id
									name
								}
							}
						}
					}
				}
			}`,
		}),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
	})
		.then((response) => response.json())
		.then((data) => {
			return data.data.taxonomyCategory;
		});

	return category;
};

const _toggleDisplay = (element) => {
	if (element.classList.contains('hide-content')) {
		element.classList.remove('hide-content');
	}
	else {
		element.classList.add('hide-content');
	}
};

const _toggleRotate = (element) => {
	if (element.classList.contains('rotate-90')) {
		element.classList.remove('rotate-90');
	}
	else {
		element.classList.add('rotate-90');
	}
};

window.addEventListener('load', async () => {
	const prefix = fragmentElement.id.replaceAll('-', '');

	const parentTaxonomyCategoryId =
		window[`${prefix}ParentTaxonomyCategoryId`];

	if (parentTaxonomyCategoryId) {
		const category = await _fetchCategory(parentTaxonomyCategoryId);

		const subCategories = category.taxonomyCategories.items;

		const articles = await _fetchArticles(subCategories);

		const sidePanel = _buildSidePanel(
			articles,
			subCategories,
			window[`${prefix}CurrentItemId`]
		);

		fragmentElement.querySelector('.loading-animation').remove();

		fragmentElement.appendChild(sidePanel);
	}
});
