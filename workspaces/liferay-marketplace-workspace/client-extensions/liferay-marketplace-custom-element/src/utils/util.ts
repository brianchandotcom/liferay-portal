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

import accountPlaceholder from '../assets/images/account_placeholder.png';
import appPlaceholder from '../assets/images/app_placeholder.png';
import {
	createProductSpecification,
	getAccountGroup,
	getCatalogs,
	getSpecifications,
	getUserAccountsById,
	updateProductSpecification,
} from './api';

type FileRequest = {
	appERC: string;
	file: File | string;
	index?: number;
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

export async function getCatalogId() {
	const catalogs = await getCatalogs();

	return catalogs[0].id;
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

export async function userAccountChecker(verifiedAccounts: string[]) {
	const response = await getUserAccountsById();

	if (response.ok) {
		const userAccounts = (await response.json()) as UserAccount;

		const userHasPublisherGroup = await Promise.all(
			userAccounts.accountBriefs.map(async (currentAccount) => {
				const accountGroup = await getAccountGroup(currentAccount.id);

				const accountGroupPublisher = accountGroup.some(
					(currentAccountGroup) =>
						verifiedAccounts.includes(currentAccountGroup.name)
				);

				return accountGroupPublisher;
			})
		);

		return userHasPublisherGroup.some((item) => item);
	}

	return false;
}

export function getProductVersionFromSpecifications(
	specifications: ProductSpecification[]
) {
	let productVersion = '0';

	specifications.forEach((specification) => {
		if (specification.specificationKey === 'latest-version') {
			productVersion = specification.value.en_US;
		}
	});

	return productVersion;
}

export function showAccountImage(url?: string) {
	return url?.includes('img_id=0') || !url ? accountPlaceholder : url;
}

export function showAppImage(url?: string) {
	return url?.includes('/default') || !url ? appPlaceholder : url;
}

export function removeProtocolURL(url: string) {
	return url.replace(/^(?:https?:\/\/)?(?:www\.)?/i, '').split('/')[0];
}

async function submitSpecification(
	appId: string,
	productId: number,
	productSpecificationId: number,
	key: string,
	title: string,
	value: string
): Promise<number> {
	const specifications = await getSpecifications();

	const specification = specifications.items.map(
		({specificationKey}: {specificationKey: string}) =>
			specificationKey === key
	);

	if (productSpecificationId) {
		updateProductSpecification({
			body: {
				specificationKey: key,
				value: {en_US: value},
			},
			id: productSpecificationId,
		});

		return -1;
	}
	else {
		const {id} = await createProductSpecification({
			appId,
			body: {
				productId,
				specificationId: specification.id,
				specificationKey: key,
				value: {en_US: value},
			},
		});

		return id;
	}
}

export async function saveSpecification(
	appId: string,
	productId: number,
	productSpecificationId: number,
	key: string,
	title: string,
	value: string
) {
	return await submitSpecification(
		appId,
		productId,
		productSpecificationId,
		key,
		title,
		value
	);
}

export async function submitFile({
	appERC,
	file: fileBase64,
	index,
	requestFunction,
	title,
}: FileRequest) {
	const response = await requestFunction({
		body: {
			attachment: fileBase64,
			priority: index,
			title: {en_US: title},
		},
		externalReferenceCode: appERC,
	});

	return (await response.json()) as ProductAttachment;
}

export async function submitBase64EncodedFile({
	appERC,
	file,
	index,
	requestFunction,
	title,
}: FileRequest) {
	return new Promise((resolve) => {
		let attachmentId;
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

				if (result) {
					const {id} = await submitFile({
						appERC,
						file: result,
						index,
						requestFunction,
						title,
					});
					attachmentId = id;
					resolve(attachmentId);
				}
			},
			false
		);
		reader.readAsDataURL(file as File);
	});
}
