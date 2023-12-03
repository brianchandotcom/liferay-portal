/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
declare module '*.svg' {
	const content: any;
	export default content;
}

declare module 'warning';

type Account = {
	customFields?: CustomField[];
	description: string;
	emailAddress: string;
	externalReferenceCode: string;
	id: number;
	logoURL?: string;
	name: string;
	taxId: string;
	type: string;
};

type Categories = {
	externalReferenceCode: string;
	id: string;
	name: string;
	vocabulary: string;
};

interface CheckboxVersion {
	isChecked: boolean;
	versionName: string;
}

type CustomField = {
	customValue: {
		data: string | string[];
	};
	dataType?: string;
	name: string;
};

type AccountBrief = {
	customFields?: any;
	externalReferenceCode: string;
	id: number;
	logoURL?: string;
	name: string;
	roleBriefs: RoleBrief[];
};

type AccountPostalAddresses = {
	addressCountry: string;
	addressLocality: string;
	addressRegion: string;
	addressType: string;
	id: number;
	name: string;
	postalCode: number;
	primary: boolean;
	streetAddressLine1: string;
	streetAddressLine2: string;
	streetAddressLine3: string;
};

type AccountPostalAddresses = {
	addressCountry: string;
	addressLocality: string;
	addressRegion: string;
	addressType: string;
	id: number;
	name: string;
	postalCode: number;
	primary: boolean;
	streetAddressLine1: string;
	streetAddressLine2: string;
	streetAddressLine3: string;
};

type AccountGroup = {
	customFields: {};
	externalReferenceCode: string;
	id: number;
	name: string;
};

type AccountRole = {
	accountId: number;
	description: string;
	displayName: string;
	id: number;
	name: string;
	roleId: number;
};

type BillingAddress = {
	city?: string;
	country?: string;
	countryISOCode: string;
	description?: string;
	name?: string;
	phoneNumber?: string;
	regionISOCode?: string;
	street1?: string;
	street2?: string;
	zip?: string;
};

type Cart = {
	accountId: number;
	author?: string;
	billingAddress: BillingAddress;
	cartItems: CartItem[];
	currencyCode: string;
	id: number;
	orderStatusInfo: {[key: string]: string};
	orderTypeExternalReferenceCode: string;
	orderTypeId: number;
	paymentMethod: string;
	paymentStatusInfo: {[key: string]: string};
	paymentStatusLabel: string;
	purchaseOrderNumber?: string;
	shippingAddress: BillingAddress;
	summary: {
		totalFormatted: string;
	};
};

type CartItem = {
	customFields?: {};
	price: {
		currency: string;
		discount: number;
		finalPrice?: number;
		price?: number;
	};
	productId?: number;
	quantity: number;
	settings: {
		maxQuantity: number;
	};
	skuId: number;
};

type Catalog = {
	accountId: number | null;
	currencyCode: string;
	defaultLanguageId: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	system: boolean;
};

type EmailAppInformation = {
	dashboardLink: string;
	orderID: number;
	priceModel?: string;
	productName?: string;
	productType: string;
};

type Vocabulary = {
	description: string;
	externalReferenceCode: string;
	id: string;
	name: string;
	parentTaxonomyVocabulary: {
		id: number;
		name: string;
	};
	siteId: number;
	taxonomyVocabularyId: number;
};

type Channel = {
	channelId: number;
	currencyCode: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	siteGroupId: number;
	type: string;
};

type DefaultProperties = {
	cloudBaseURL: string;
};

interface CommerceAccount extends Omit<Account, 'description'> {
	active: boolean;
	logoURL: string;
	taxId: string;
}

type CommerceOption = {
	id: number;
	key: string;
	name: string;
};

type Order = {
	account: {
		id: number;
		type: string;
	};
	accountExternalReferenceCode?: string;
	accountId: number;
	billingAddressId?: number;
	channel: {
		currencyCode?: string;
		id: number;
		type: string;
	};
	channelExternalReferenceCode?: string;
	channelId: number;
	createDate?: string;
	creatorEmailAddress?: string;
	currencyCode: string;
	customFields?: {[key: string]: string};
	externalReferenceCode?: string;
	id?: number;
	marketplaceOrderType?: string;
	modifiedDate?: string;
	orderDate?: string;
	orderItems: [
		{
			id?: number;
			quantity?: number;
			skuId: number;
			unitPriceWithTaxAmount?: number;
		}
	];
	orderStatus: number;
	orderTypeExternalReferenceCode?: string;
	orderTypeId?: number;
	shippingAmount?: number;
	shippingWithTaxAmount?: number;
};

type OrderType = {
	externalReferenceCode: string;
	id: number;
	name: {[key: string]: string};
};

type PaymentMethodSelector = 'order' | 'pay' | 'trial' | 'free';

interface PlacedOrder {
	account: string;
	accountId: number;
	author: string;
	createDate: string;
	customFields: {[key: string]: string};
	id: number;
	orderStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
	orderTypeExternalReferenceCode: string;
	placedOrderItems: PlacedOrderItems[];
}

interface PlacedOrderItems {
	id: number;
	name: string;
	price: {
		priceFormatted: string;
	};
	productId: number;
	quantity: number;
	sku: string;
	skuId: number;
	subscription: boolean;
	thumbnail: string;
	version: string;
	virtualItemURLs: string;
}

interface PostalAddressResponse {
	addressCountry: string;
	addressLocality: string;
	addressRegion: string;
	addressType: string;
	id: number;
	name: string;
	postalCode: string;
	streetAddressLine1: string;
	streetAddressLine2: string;
}

interface PostCartResponse {
	account: string;
	accountId: number;
	author: string;
	billingAddressId: number;
	createDate: string;
	customFields: object;
	id: number;
	modifiedDate: string;
	orderStatusInfo: {
		cod: number;
		label: string;
		label_i18: string;
	};
	orderTypeId: number;
	orderUUID: string;
	paymentMethod: string;
	paymentStatus: number;
	paymentStatusInfo: {
		cod: number;
		label: string;
		label_i18: string;
	};
	paymentStatusLabel: string;
	purchaseOrderNumber: string;
	status: string;
}

interface PostCheckoutCartResponse extends PostCartResponse {
	cartItems: CartItem[];
}

interface Product {
	active: boolean;
	attachments: ProductAttachment[];
	catalog: Catalog;
	catalogId: number;
	catalogName?: string;
	categories: ProductCategories[];
	customFields?: CustomField[];
	description: {[key: string]: string};
	externalReferenceCode: string;
	finalPrice?: number;
	id?: number;
	images: ProductImages[];
	modifiedDate: string;
	name: {[key: string]: string};
	price?: number;
	productChannelFilter?: boolean;
	productChannels: Channel[];
	productId: number;
	productSpecifications: ProductSpecification[];
	productStatus: number;
	productType: string;
	skus: SKU[];
	thumbnail: string;
	version: number;
	workflowStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
}

interface DeliveryProductAttachment {
	customFields: CustomField[];
	galleryEnabled: boolean;
	id: number;
	priority: number;
	src: string;
	tags: any;
	title: string;
	type: number;
}

interface DeliveryProductSpecification {
	id: number;
	optionCategoryId: number;
	priority: number;
	specificationGroupKey: string;
	specificationGroupTitle: string;
	specificationId: number;
	specificationKey: string;
	specificationTitle: string;
	value: string;
}

type DeliverySKU = {
	customFields?: CustomField[];
	externalReferenceCode: string;
	id: number;
	price: {price: number; priceFormatted: string};
	purchasable: boolean;
	sku: string;
	skuOptions: {skuOptionKey: string; skuOptionValueKey: string}[];
};

interface DeliveryProduct {
	attachments: DeliveryProductAttachment[];
	catalogName?: string;
	categories: ProductCategories[];
	customFields?: CustomField[];
	description: string;
	externalReferenceCode: string;
	id?: number;
	images: ProductImages[];
	modifiedDate: string;
	name: string;
	productId: number;
	productSpecifications: DeliveryProductSpecification[];
	productType: string;
	skus: DeliverySKU[];
	urlImage: string;
}

interface ProductAttachment {
	customFields?: CustomField[];
	externalReferenceCode: string;
	id: number;
	priority: number;
	src: string;
	title: {[key: string]: string};
}

type ProductCategories = {
	externalReferenceCode: string;
	id: number;
	name: string;
	vocabulary: string;
};

interface ProductImages extends ProductAttachment {}

type ProductOptionItem = {
	id: number;
	key: string;
	name: string;
	optionId: number;
};

type RoleBrief = {
	id: number;
	name: string;
};

type PermissionDescription = {
	permissionName: string;
	permissionTooltip: string;
	permittedRoles: string[];
};

type SKU = {
	cost: number;
	customFields?: CustomField[];
	externalReferenceCode: string;
	id: number;
	price: number;
	sku: string;
	skuOptions: {key: string; value: string}[];
};

type OptionCategory = {
	description?: {[key: string]: string};
	id?: number;
	key?: string;
	priority?: number;
	title?: {[key: string]: string};
};

type Specification = {
	description?: {[key: string]: string};
	id?: number;
	key?: string;
	optionCategory?: OptionCategory;
	title?: {[key: string]: string};
};

type ProductSpecification = {
	id?: number;
	optionCategoryId?: number;
	priority?: number;
	productId?: number;
	specificationId?: number;
	specificationKey?: string;
	value: {[key: string]: string};
};

type UserAccount = {
	accountBriefs: AccountBrief[];
	alternateName: string;
	currentPassword: string;
	emailAddress: string;
	externalReferenceCode: string;
	familyName: string;
	givenName: string;
	id: number;
	image: string;
	isCustomerAccount: boolean;
	isPublisherAccount: boolean;
	logoURL: string;
	name: string;
	newsSubscription: boolean;
	password: string;
};

type RequestBody = {
	[keys: string]: string;
};

interface CheckboxRole {
	isChecked: boolean;
	roleName: string;
}

type UserLogged = {
	accountBriefs: AccountBrief[];
	isAdminAccount: boolean;
	isCustomerAccount: boolean;
	isPublisherAccount: boolean;
};

type AdditionalInfoBody = {
	acceptInviteStatus: boolean;
	accountName: string;
	emailOfMember: string;
	id?: number;
	inviteURL: string;
	inviterName: string;
	mothersName: string;
	r_accountEntryToUserAdditionalInfo_accountEntryId: number;
	r_userToUserAddInfo_userId: string;
	roles: string;
	sendType: {key: string; name: string};
	userFirstName: string;
};

type PhonesFlags = {
	code: string;
	flag: string;
};

type Industries = {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: {
		'en-US': string;
	};
};

type UserForm = {
	accountQuantity: number;
	accountSelected: Account | undefined;
	accounts: Account[];
	agreeToTermsAndConditions: boolean;
	companyName: string;
	emailAddress: string;
	extension?: string | undefined;
	familyName: string;
	givenName: string;
	industry: string;
	phone: PhonesFlags;
	phoneNumber: string;
};

type OfferingType = {
	description: string;
	disabled?: boolean;
	label: string;
};

type OrderInfo = {
	account: Account | UserForm;
	product?: Product;
	sku?: number;
	specifications?: ProductSpecification[];
};

type RadioOption<T> = {
	index: number;
	value: T;
};

type APIResponse<Query = any> = {
	actions: ObjectActions;
	facets: Facets[];
	items: Query[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};
