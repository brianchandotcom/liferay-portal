declare module '*.svg' {
	const content: any;
	export default content;
}

declare module 'warning';

type Account = {
	customFields?: CustomField[];
	description: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	type: string;
};

type Categories = {
	externalReferenceCode: string;
	id: string;
	name: string;
	vocabulary: string;
};

type CustomField = {
	customValue: {
		data: string;
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

type BillingAddress = {
	city?: string;
	country?: string;
	countryISOCode: string;
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
	paymentMethod: string;
	purchaseOrderNumber?: string;
	shippingAddress: BillingAddress;
	orderTypeExternalReferenceCode: string;
	orderTypeId: number;
};

type CartItem = {
	customFields?: {};
	price: {
		currency: string;
		discount: number;
		finalPrice: number;
		price: number;
	};
	productId: number;
	quantity: number;
	settings: {
		maxQuantity: number;
	};
	skuId: number;
};

type Catalog = {
	currencyCode: string;
	defaultLanguageId: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	system: boolean;
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

interface CommerceAccount extends Omit<Account, 'description'> {
	active: boolean;
	taxId: string;
	logoURL: string;
}

type CommerceOption = {
	id: number;
	key: string;
	name: string;
};

interface Order {
	account: {
		id: number;
		type: string;
	};
	accountExternalReferenceCode?: string;
	accountId: number;
	billingAddressId?: number;
	channel: {
		currencyCode: string;
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
			skuId: number;
			unitPriceWithTaxAmount: number;
		}
	];
	orderStatus: number;
	orderTypeExternalReferenceCode?: string;
	orderTypeId: number;
}

interface OrderType {
	active: boolean;
	displayDate: string;
	displayOrder: number;
	externalReferenceCode: string;
	id: number;
	name: {[key: string]: string};
}

type PaymentMethodMode = 'PayPal';

type PaymentMethodSelector = 'order' | 'pay' | 'trial' | null;

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
	skuId: number;
	subscription: boolean;
	thumbnail: string;
	version: string;
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
	categories: ProductCategories[];
	catalogId: number;
	description: {[key: string]: string};
	name: {[key: string]: string};
	externalReferenceCode: string;
	id: number;
	images: ProductImages[];
	productChannels: Channel[];
	productId: number;
	productStatus: number;
	productType: string;
	version: number;
	workflowStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
	thumbnail: string;
	modifiedDate: string;
}

interface ProductAttachment {
	customFields?: CustomField[];
	title: {[key: string]: string};
	src: string;
	priority: number;
	id: number;
	externalReferenceCode: string;
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

type SKU = {
	customFields?: CustomField[];
	cost: number;
	externalReferenceCode: string;
	id: number;
	price: number;
	sku: string;
	skuOptions: {key: string; value: string}[];
};

type ProductSpecification = {
	id: number;
	optionCategoryId: number;
	priority: number;
	productId: number;
	specificationId: number;
	specificationKey: string;
	value: {[key: string]: string};
};

type UserAccount = {
	accountBriefs: AccountBrief[];
	isCustomerAccount: boolean;
	isPublisherAccount: boolean;
};
