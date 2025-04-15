/* eslint-disable no-undef */

/* eslint-disable @liferay/portal/no-global-fetch */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const contactPublisherButtonElement = fragmentElement.querySelector(
	'button#contact-publisher'
);
const getAppButtonElement = fragmentElement.querySelector('button#get-app');
const getAppDescriptionElement = fragmentElement.querySelector(
	'#get-app-description'
);
const tooltipElement = fragmentElement.querySelector('.clay-tooltip-bottom');

const isFreeApp = (productSpecifications = []) =>
	productSpecifications.some(
		(productSpecification) =>
			productSpecification.specificationKey === 'price-model' &&
			productSpecification.value === 'Free'
	);

const isLowCodeConfiguration = (productSpecifications = []) =>
	productSpecifications.some(
		(productSpecification) =>
			productSpecification.specificationKey === 'type' &&
			productSpecification.value === 'low-code-configuration'
	);

const trackAnalytics = (key, options) => {
	if (!window.Analytics) {
		return;
	}

	Analytics.track(key, options);
};

const productId = fragmentElement
	.querySelector('.product-id')
	.innerText.replace(/[\n\r]+|[\s]{2,}/g, ' ')
	.trim();

const getHelpModal = () => `
	<div class="mb-5">
		<p class="pb-1" style="color: #54555F;">Fragments are installed directly from DXP.</p>
	
		<p style="color: #54555F;">In order to install fragments please follow these steps:</p>
	
		<ol>
			<li class="pb-1" style="color: #54555F;">
				Link your DXP environment to your Liferay Marketplace Account. Check this 
				<a href="https://learn.liferay.com/w/dxp/liferay-development/marketplace/connecting-liferay-dxp-to-marketplace" target="_blank">
				documentation</a> to learn how to link the DXP to Marketplace.
			</li>
	
			<li style="color: #54555F;">
				Install fragments directly from Page Builder. 
				Check <a href="https://learn.liferay.com/w/dxp/site-building/creating-pages/page-fragments-and-widgets/using-fragments/using-fragments-from-the-marketplace" target="_blank">
				here</a> to learn how.
			</li>
		</ol>
	</div>
`;

const getProductPrice = async (product) => {
	const {productSpecifications = []} = product;

	if (isFreeApp(productSpecifications)) {
		return 'Free';
	}

	const skus = product.skus.filter(({purchasable}) => purchasable);

	const hasTrialSku = skus.some(({skuOptions}) =>
		skuOptions.find((skuOption) =>
			['trial', 'yes'].includes(skuOption.skuOptionValueKey)
		)
	);

	const standardSku = skus.find(({skuOptions}) =>
		skuOptions.some((skuOption) =>
			['standard', 'no'].includes(skuOption.skuOptionValueKey)
		)
	);

	const licenseType = productSpecifications.find(
		(productSpecification) =>
			productSpecification.specificationKey === 'license-type'
	);

	const licenseTypeText =
		licenseType?.value === 'Perpetual' ? 'One-Time' : 'Annually';
	const currency = await getCurrentCurrency();

	let displayPrice = '';

	if (currency) {
		const convertedPrice = standardSku?.price?.price * currency.rate;

		displayPrice = `${currency.symbol} ${convertedPrice?.toFixed(2)}`;
	}
	else {
		displayPrice = standardSku?.price?.priceFormatted
			?.replace(' ', '')
			?.replace(',', '.');
	}

	const price = `${hasTrialSku ? '30-day trial or' : ''} ${displayPrice}`;

	return `${price} ${licenseTypeText}`;
};

const openLowCodeHelpModal = () => {
	Liferay.Util.openModal({
		bodyHTML: getHelpModal(),
		center: true,
		headerHTML: 'How to Install a Low Code App',
		size: 'md',
	});
};

const customizeGetAppButton = async (product) => {
	const isLowCodeApp = isLowCodeConfiguration(product.productSpecifications);

	getAppButtonElement.onclick = () => {
		if (isLowCodeApp) {
			openLowCodeHelpModal();

			return;
		}

		trackAnalytics('Click on Get App Button', {
			isFree: isFreeApp(product.productSpecifications),
			productName: product.name,
		});

		Liferay.Util.navigate(`${getSiteURL()}/get-app?productId=${productId}`);
	};

	getAppDescriptionElement.innerText = await getProductPrice(product);
};

const getCommerceProduct = async (channelId) => {
	try {
		const response = await Liferay.Util.fetch(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products/${productId}?nestedFields=productSpecifications,skus&accountId=-1&skus.accountId=-1`
		);

		const product = await response.json();

		return product ?? {skus: []};
	}
	catch {
		return {skus: []};
	}
};

const getCurrentCurrency = async () => {
	try {
		const response = await Liferay.Util.fetch(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/currencies`
		);

		const currencyResponse = await response.json();

		return currencyResponse.items.find(
			(currency) =>
				currency.code === Liferay.CommerceContext.currency.currencyCode
		);
	}
	catch (error) {
		console.error('Error fetching currency:', error);

		return null;
	}
};

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.startsWith('/web/')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

const getModalTemplate = ({accountName, email, logoURL, website}) => `
<div class="d-flex">
	<div class="mr-2" style="width:24px;">
		${
			logoURL &&
			`<img class="rounded" src="${logoURL}" style="height: 24px; width: 24px;" />`
		}
	</div>

	<div style="color: #282934; font-size: 20px; font-weight: 600;">${accountName}</div>
</div>

${email && `<p className="my-2">${email}</p>`}

${
	website &&
	`<a href="${website}" target="_blank" style="font-weight: 600;">${website}</a>`
}
`;

const customizeUnavailableButton = async (product) => {
	contactPublisherButtonElement.onmouseover = () =>
		tooltipElement.classList.replace('hide', 'show');

	contactPublisherButtonElement.onmouseout = () =>
		tooltipElement.classList.replace('show', 'hide');

	if (!themeDisplay.isSignedIn()) {
		contactPublisherButtonElement.onclick = () => {
			sessionStorage.setItem(
				'@marketplace/redirect-to',
				window.location.href
			);

			location.href = `${getSiteURL()}/sign-in`;
		};

		return;
	}

	const customFields = product.customFields ?? [];

	const getCustomFieldValue = (name) =>
		customFields.find((customField) => customField.name === name)
			?.customValue?.data ?? '';

	contactPublisherButtonElement.onclick = () => {
		trackAnalytics('Click on Contact Publisher Button', {
			isFree: isFreeApp(product.productSpecifications),
			productName: product.name,
		});

		Liferay.Util.openModal({
			bodyHTML: getModalTemplate({
				accountName: product.catalogName || product.name,
				email: getCustomFieldValue('Support'),
				logoURL:
					getCustomFieldValue('Publisher Icon') ||
					`/o/${product.urlImage.split('/o/')[1]}`,
				website: getCustomFieldValue('Developer Website'),
			}),
			buttons: [
				{
					displayType: 'secondary',
					label: 'Close',
					type: 'cancel',
				},
			],
			center: true,
			headerHTML: 'Publisher Contact Info',
			size: 'md',
		});
	};

	if (sessionStorage.getItem('@marketplace/redirect-to')) {
		contactPublisherButtonElement.click();

		sessionStorage.removeItem('@marketplace/redirect-to');
	}
};

const main = async () => {
	const channelId = Liferay.CommerceContext.commerceChannelId;

	if (!channelId) {
		return;
	}

	const product = await getCommerceProduct(channelId);
	const skuPublished = product.skus.some((sku) => sku.purchasable);

	if (skuPublished) {
		getAppButtonElement.classList.remove('d-none');

		return customizeGetAppButton(product);
	}

	contactPublisherButtonElement.classList.remove('d-none');

	customizeUnavailableButton(product);
};

main();
