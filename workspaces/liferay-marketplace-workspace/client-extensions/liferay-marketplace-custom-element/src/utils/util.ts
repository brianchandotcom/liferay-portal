/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import accountPlaceholder from '../assets/images/account_placeholder.png';
import appPlaceholder from '../assets/images/app_placeholder.png';
import i18n from '../i18n';
import {
	createProductSpecification,
	getProductSpecifications,
	getSiteStructuredContentByKey,
	updateProductSpecification,
} from './api';

type FileRequest = {
	appERC: string;
	callback?: (progress: number) => void;
	file: File | string;
	index?: number;
	isAppIcon: boolean;
	requestFunction: Function;
	title: string;
};

export function createSkuName(
	appProductId: number,
	appVersion: string,
	concatValue?: string
) {
	return `${appProductId}v${appVersion.replace(/[^a-zA-Z0-9 ]/g, '')}${
		concatValue ? concatValue : ''
	}`;
}

export function getCloudOptionBody() {
	return {
		fieldType: 'radio',
		key: 'cloud-license-usage-type',
		name: {en_US: i18n.translate('cloud-license-usage-type')},
	};
}

export function getCloudProductOptionBody(newOptionId: number) {
	return {
		facetable: false,
		fieldType: 'radio',
		key: 'cloud-license-usage-type',
		name: {
			en_US: i18n.translate('cloud-license-usage-type'),
		},
		optionId: newOptionId,
		productOptionValues: [],
		required: true,
		skuContributor: true,
	};
}

export function getDxpOptionBody() {
	return {
		fieldType: 'radio',
		key: 'dxp-license-usage-type',
		name: {en_US: i18n.translate('dxp-license-usage-type')},
	};
}

export function getDxpProductOptionBody(newOptionId: number) {
	return {
		facetable: false,
		fieldType: 'radio',
		key: 'dxp-license-usage-type',
		name: {
			en_US: i18n.translate('dxp-license-usage-type'),
		},
		optionId: newOptionId,
		productOptionValues: [],
		required: true,
		skuContributor: true,
	};
}

export async function getEulaDescription() {
	const keyEula = 'EULA';
	const response = await getSiteStructuredContentByKey(keyEula);

	return response?.contentFields[0]?.contentFieldValue?.data;
}

export function getLicenceTypesObject() {
	return [
		{code: 'd', key: 'developer', name: 'DEVELOPER'},
		{code: 's', key: 'standard', name: 'STANDARD'},
		{code: 'ts', key: 'trial', name: 'TRIAL'},
	];
}

export function getOptionDeveloperBody() {
	return {key: 'developer', name: {en_US: 'Developer'}, priority: 1};
}

export function getOptionStandardBody() {
	return {key: 'standard', name: {en_US: 'Standard'}, priority: 0};
}

export function getOptionTrialBody() {
	return {key: 'trial', name: {en_US: 'Trial'}, priority: 2};
}

export function getInitials(userName: string) {
	const names = userName.trim().split(' ');
	const lastNameIndex = names.length - 1;

	const initials = names.reduce((initials, currentName, index) => {
		if (!index || index === lastNameIndex) {
			initials = `${initials}${currentName.charAt(0).toUpperCase()}`;
		}

		return initials;
	});

	return initials;
}

export function getThumbnailByProductAttachment(
	images?: Partial<ProductAttachment | DeliveryProductAttachment>[]
): string | undefined {
	if (!Array.isArray(images)) {
		return undefined;
	}

	const thumbnail =
		images.find((images) => {
			return (images.tags || []).indexOf('app icon') >= 0;
		}) || images[0];

	return thumbnail?.src;
}

export function getProductVersionFromSpecifications(
	specifications: ProductSpecification[]
) {
	let productVersion = '';

	specifications.forEach((specification) => {
		if (specification.specificationKey === 'latest-version') {
			productVersion = specification.value.en_US;
		}
	});

	return productVersion;
}

export function getValueFromDeliverySpecifications(
	specifications: DeliveryProductSpecification[],
	valueKey: string
) {
	let value = '';
	specifications?.forEach((specification) => {
		if (specification?.specificationKey === valueKey) {
			value = specification?.value;
		}
	});

	return value;
}

export function getAccountImage(url?: string) {
	return url?.includes('img_id=0') || !url ? accountPlaceholder : url;
}

type LicenceTiersPrices = {
	developer: {key: number; value: number}[];
	standard: {key: number; value: number}[];
};

export function getSkuPrice(appLicensePrice: LicenceTiersPrices, sku: SKU) {
	const dxpLicenseUsageType = sku.skuOptions.find(
		({key}) => key === 'dxp-license-usage-type'
	);

	if (!dxpLicenseUsageType) {
		if (sku.sku.endsWith('ts')) {
			return 0;
		}

		if (sku?.sku.endsWith('d')) {
			appLicensePrice.developer[0]?.value ?? 0;
		}

		return appLicensePrice.standard[0]?.value ?? 0;
	}

	const dxpLicenseUsageTypeValue = dxpLicenseUsageType.value;

	if (dxpLicenseUsageTypeValue === 'standard') {
		return appLicensePrice['standard'][0]?.value;
	}
	else if (dxpLicenseUsageTypeValue === 'developer') {
		return appLicensePrice['developer'][0]?.value;
	}
	else {
		return 0;
	}
}

export function showAppImage(url?: string) {
	let newURL;

	if (url) {
		const currentURL = new URL(url!);
		newURL = window.location.origin + currentURL.pathname;
	}

	return newURL?.includes('/default') || !newURL ? appPlaceholder : newURL;
}

export function removeProtocolURL(url: string) {
	return url.replace(/^(?:https?:\/\/)?(?:www\.)?/i, '').split('/')[0];
}

export async function submitSpecification(
	productId: number,
	productSpecifications: {specificationKey: string; value: string}[]
) {
	const dataSpecificationList = await getProductSpecifications({
		appProductId: productId as number,
	});

	for (const productSpecification of productSpecifications) {
		const dataSpecification = dataSpecificationList?.find(
			(specification) =>
				specification?.specificationKey ===
				productSpecification.specificationKey
		);

		const fn = dataSpecification?.id
			? updateProductSpecification
			: createProductSpecification;

		await fn({
			body: {
				specificationKey: productSpecification.specificationKey,
				value: {en_US: productSpecification.value},
			},
			id: dataSpecification?.id || productId,
		});
	}
}

export async function submitFile({
	appERC,
	callback,
	file: fileBase64,
	index,
	isAppIcon,
	requestFunction,
	title,
}: FileRequest) {
	const response = await requestFunction({
		body: {
			attachment: fileBase64,
			galleryEnabled: !isAppIcon,
			neverExpire: true,
			priority: index,
			tags: isAppIcon ? ['app icon'] : [],
			title: {en_US: title},
		},
		callback,
		externalReferenceCode: appERC,
	});

	return response as ProductAttachment;
}

export async function submitBase64EncodedFile({
	appERC,
	callback,
	file,
	index,
	isAppIcon,
	requestFunction,
	title,
}: FileRequest) {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();

		reader.addEventListener(
			'load',
			async () => {
				let result = reader.result as string;

				if (result?.includes('application/zip')) {
					result = result?.substring(28);
				}
				else if (
					result?.includes('image/gif') ||
					result?.includes('image/png')
				) {
					result = result?.substring(22);
				}
				else if (result?.includes('image/jpeg')) {
					result = result?.substring(23);
				}
				else if (
					result?.includes('application/octet-stream') ||
					result?.includes('application/java-archive')
				) {
					result = result?.substring(37);
				}

				if (result) {
					const response = await submitFile({
						appERC,
						callback,
						file: result,
						index,
						isAppIcon,
						requestFunction,
						title,
					}).catch((error) => {
						reject(error);
					});

					resolve(response);
				}
			},
			false
		);
		reader.readAsDataURL(file as File);
	});
}

export function safeJSONParse<T = any>(
	value: string | null,
	defaultValue: unknown = null
): T {
	if (defaultValue && typeof value !== 'string') {
		return defaultValue as T;
	}

	try {
		return JSON.parse(value as string);
	}
	catch (error) {
		return defaultValue as T;
	}
}

export function isCloudEnvironment() {
	return window.location.protocol === 'https:';
}

export function waitTimeout(timer: number) {
	return new Promise((resolve) => setTimeout(() => resolve(null), timer));
}
