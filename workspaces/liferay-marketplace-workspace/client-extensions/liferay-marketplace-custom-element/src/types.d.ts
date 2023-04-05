type Account = {
	externalReferenceCode: string;
	id: number;
	name: string;
};

type AccountBrief = {
	externalReferenceCode: string;
	id: number;
	image?: string;
	name: string;
};

type Cart = {
	accountId: number;
	cartItems: CartItem[];
	currencyCode: string;
};

type CartItem = {
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

type Channel = {
	currencyCode: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	siteGroupId: number;
	type: string;
};

type CommerceOption = {
	id: number;
	key: string;
	name: string;
};

interface PlacedOrder {
	account: string;
	accountId: number;
	author: string;
	createDate: string;
	id: number;
	orderStatusInfo: {code: number; label: string; label_i18n: string};
	placedOrderItems: PlacedOrderItems[];
}

interface PlacedOrderItems {
	id: number;
	name: string;
	subscription: boolean;
	thumbnail: string;
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

type ProductOptionItem = {
	id: number;
	key: string;
	name: string;
	optionId: number;
};

type SKU = {
	cost: number;
	externalReferenceCode: string;
	id: number;
	price: number;
	sku: string;
	skuOptions: [];
};

type UserAccount = {
	accountBriefs: AccountBrief[];
};
