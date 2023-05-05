import {ProductSpecificationProps} from '../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';
import {
	createProductSpecification,
	getAccountGroup,
	getCatalogs,
	getSpecifications,
	getUserAccountsById,
	updateProductSpecification,
} from './api';

import accountPlaceholder from '../assets/images/account-placeholder.png';
import appPlaceholder from '../assets/images/app-placeholder.png';

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
	specifications: ProductSpecificationProps
) {
	let productVersion = '0';

	specifications.items.forEach((specification: Specification) => {
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

export async function submitFile(
	appERC: string,
	fileBase64: string,
	requestFunction: Function,
	title: string
) {
	const response = await requestFunction({
		body: {
			attachment: fileBase64,
			title: {en_US: title},
		},
		externalReferenceCode: appERC,
	});

	response.json();
}

export function submitBase64EncodedFile(
	appERC: string,
	file: File,
	requestFunction: Function,
	title: string
) {
	const reader = new FileReader();

	reader.addEventListener(
		'load',
		() => {
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
				submitFile(appERC, result, requestFunction, title);
			}
		},
		false
	);

	reader.readAsDataURL(file);
}
