declare let Liferay: {Service: any; ThemeDisplay: any; authToken: string};
const headers = {
	'Content-Type': 'application/json',
	'X-CSRF-Token': Liferay.authToken,
};

type Categories = {
	externalReferenceCode: string;
	id: number;
	name: string;
	vocabulary: string;
};

export async function addSkuExpandoValue({
	companyId,
	notesValue,
	skuId,
	versionValue,
}: {
	companyId: number;
	notesValue: string;
	skuId: number;
	versionValue: string;
}) {
	await Liferay.Service(
		'/expandovalue/add-values',
		{
			attributeValues: {
				'version': versionValue,
				'version description': notesValue,
			},
			className: 'com.liferay.commerce.product.model.CPInstance',
			classPK: skuId,
			companyId,
			tableName: 'CUSTOM_FIELDS',
		},
		(obj: any) => {

			// console.log(obj);

		}
	);
}

export function createApp({
	appCategories,
	appDescription,
	appName,
	catalogId,
}: {
	appCategories: Categories[];
	appDescription: string;
	appName: string;
	catalogId: number;
}) {
	return fetch('/o/headless-commerce-admin-catalog/v1.0/products', {
		body: JSON.stringify({
			active: true,
			catalogId,
			categories: appCategories,
			configuration: {allowBackOrder: true, maxOrderQuantity: 1},
			description: {en_US: appDescription},
			name: {en_US: appName},
			productStatus: 2,
			productType: 'virtual',
		}),
		headers,
		method: 'POST',
	});
}

export async function createAppLicensePrice({
	appProductId,
	body,
}: {
	appProductId: number;
	body: Object;
}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/${appProductId}/skus
	  `,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);

	return await response.json();
}

export async function createAppSKU({
	appProductId,
	body,
}: {
	appProductId: number;
	body: Object;
}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/${appProductId}/skus
	  `,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);

	return await response.json();
}

export function createAttachment({
	body,
	externalReferenceCode,
}: {
	body: Object;
	externalReferenceCode: string;
}) {
	return fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}/attachments`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);
}

export function createImage({
	body,
	externalReferenceCode,
}: {
	body: Object;
	externalReferenceCode: string;
}) {
	return fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}/images`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);
}

export async function createProductSpecification({
	appId,
	body,
}: {
	appId: string;
	body: Object;
}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/${appId}/productSpecifications`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);

	return await response.json();
}

export async function createProductSubscriptionConfiguration({
	body,
	externalReferenceCode,
}: {
	body: Object;
	externalReferenceCode: string;
}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}/subscriptionConfiguration`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'PATCH',
		}
	);

	return await response.json();
}

export async function createSpecification({body}: {body: Object}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/specifications`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);

	return await response.json();
}

export async function getAccount() {
	const response = await fetch(
		'/o/headless-admin-user/v1.0/my-user-account',
		{headers, method: 'GET'}
	);

	return response.json();
}

export async function getCatalogs() {
	const response = await fetch(
		'/o/headless-commerce-admin-catalog/v1.0/catalogs',
		{headers, method: 'GET'}
	);

	return response.json();
}

export async function getCategories({vocabId}: {vocabId: number}) {
	const response = await fetch(
		`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${vocabId}/taxonomy-categories`,
		{
			headers,
			method: 'GET',
		}
	);

	return response.json();
}

export async function getChannelById(channelId: number) {
	const channelResponse = await fetch(
		`/o/headless-commerce-admin-channel/v1.0/channels/${channelId}`,
		{
			headers,
			method: 'GET',
		}
	);

	return (await channelResponse.json()) as Channel;
}

export async function getChannels() {
	const channelsResponse = await fetch(
		'/o/headless-commerce-admin-channel/v1.0/channels',
		{
			headers,
			method: 'GET',
		}
	);

	const response = await channelsResponse.json();

	return response.items as Channel[];
}

export async function getOrders(
	accountId: number,
	channelId: number,
	page: number,
	pageSize: number
) {
	const response = await fetch(
		`/o/headless-commerce-delivery-order/v1.0/channels/${channelId}/accounts/${accountId}/placed-orders?nestedFields=placedOrderItems&page=${page}&pageSize=${pageSize}`,
		{headers, method: 'GET'}
	);

	return (await response.json()) as {
		items: PlacedOrder[];
		totalCount: number;
	};
}

export async function getProduct({appERC}: {appERC: string}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${appERC}
		`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getProductImages({appProductId}: {appProductId: number}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/${appProductId}/images`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getProducts() {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products?pageSize=-1`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getProductSKU({appProductId}: {appProductId: number}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/${appProductId}/skus`,
		{
			headers,
			method: 'GET',
		}
	);

	return (await response.json()) as {items: SKU[]};
}

export async function getProductSpecifications({
	appProductId,
}: {
	appProductId: number;
}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/${appProductId}/productSpecifications`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getProductSubscriptionConfiguration({
	appERC,
}: {
	appERC: string;
}) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${appERC}/subscriptionConfiguration`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getSKUById(skuId: number) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/skus/${skuId}`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getSpecifications() {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/specifications`,
		{
			headers,
			method: 'GET',
		}
	);

	return await response.json();
}

export async function getUserAccounts() {
	const response = await fetch('/o/headless-admin-user/v1.0/user-accounts', {
		headers,
		method: 'GET',
	});

	return response.json();
}

export async function getUserAccountsById() {
	const response = await fetch(
		`/o/headless-admin-user/v1.0/user-accounts/${Liferay.ThemeDisplay.getUserId()}`,
		{
			headers,
			method: 'GET',
		}
	);

	return (await response.json()) as UserAccount;
}

export async function getVocabularies() {
	const response = await fetch(
		`/o/headless-admin-taxonomy/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/taxonomy-vocabularies`,
		{
			headers,
			method: 'GET',
		}
	);

	return response.json();
}

export function patchAppByExternalReferenceCode({
	body,
	externalReferenceCode,
}: {
	body: Object;
	externalReferenceCode: string;
}) {
	return fetch(
		`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}/patchProductByExternalReferenceCode`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'PATCH',
		}
	);
}

export async function patchOrderByERC(erc: string, body: any) {
	const response = await fetch(
		`/o/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/${erc}`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'PATCH',
		}
	);

	return response;
}

export async function patchSKUById(skuId: number, body: any) {
	const response = await fetch(
		`/o/headless-commerce-admin-catalog/v1.0/skus/${skuId}`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'PATCH',
		}
	);

	return await response.json();
}

export async function postCartByChannelId({
	cartBody,
	channelId,
}: {
	cartBody: any;
	channelId: number;
}) {
	const cartResponse = await fetch(
		`/o/headless-commerce-delivery-cart/v1.0/channels/${channelId}/carts`,
		{
			body: JSON.stringify(cartBody),
			headers,
			method: 'POST',
		}
	);

	return (await cartResponse.json()) as PostCartResponse;
}

export async function postCheckoutCart({
	body,
	cartId,
}: {
	body?: any;
	cartId: number;
}) {
	const response = await fetch(
		`/o/headless-commerce-delivery-cart/v1.0/carts/${cartId}/checkout`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'POST',
		}
	);

	return (await await response.json()) as PostCheckoutCartResponse;
}

export function updateApp({
	appDescription,
	appERC,
	appName,
}: {
	appDescription: string;
	appERC: string;
	appName: string;
}) {
	return fetch(
		`o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${appERC}`,
		{
			body: JSON.stringify({
				description: {en_US: appDescription},
				name: {en_US: appName},
			}),
			headers,
			method: 'PATCH',
		}
	);
}

export async function updateProductSpecification({
	body,
	id,
}: {
	body: Object;
	id: number;
}) {
	const response = await fetch(
		`o/headless-commerce-admin-catalog/v1.0/productSpecifications/${id}`,
		{
			body: JSON.stringify(body),
			headers,
			method: 'PATCH',
		}
	);

	return await response.json();
}
