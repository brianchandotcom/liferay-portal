/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext, useMemo, useReducer} from 'react';
import {useNavigate} from 'react-router-dom';

import {PRODUCT_SPECIFICATION_KEY} from '../../enums/Product';
import {useDeliveryProduct} from '../../hooks/data/useProduct';
import zodSchema from '../../schema/zod';
import {getUrlParam} from '../../utils/getUrlParam';
import {isCloudProduct} from '../../utils/productUtils';
import {StepType} from './enums/stepType';
import useGetResourceInfo from './hooks/useGetResourceInfo';

type LicenseType = '' | 'TRIAL' | 'PAID';

type Invoice = {
	email: string;
	purchaseOrderNumber: string;
};

type InitialState = {
	account?: Account;
	appResourceInfo: ReturnType<typeof useGetResourceInfo>;
	currentStep: number;
	formState: {
		isValid: boolean;
	};
	isCloudApp: boolean;
	license: {
		cart?: Cart;
		cartItems: CartItem[];
		selectedSKU?: any;
		type: LicenseType;
	};
	payment: {
		billingAddress: BillingAddress;
		eula: string;
		eulaCheckbox: boolean;
		invoice: Invoice;
		method: PaymentMethodSelector;
	};
	product: DeliveryProduct;
	project?: string;
	stepState: {
		onNext: () => void;
		onPrevious: () => void;
	};
	steps: {id: StepType; path: string; title: string}[];
};

export type ActionMap<M extends {[index: string]: any}> = {
	[Key in keyof M]: M[Key] extends undefined
		? {
				type: Key;
			}
		: {
				payload: M[Key];
				type: Key;
			};
};

const initialState: InitialState = {
	appResourceInfo: {hasResources: false} as ReturnType<
		typeof useGetResourceInfo
	>,
	currentStep: 0,
	formState: {
		isValid: false,
	},
	isCloudApp: false,
	license: {cart: undefined, cartItems: [], selectedSKU: undefined, type: ''},
	payment: {
		billingAddress: {} as BillingAddress,
		eula: '',
		eulaCheckbox: false,
		invoice: {
			email: '',
			purchaseOrderNumber: '',
		} as Invoice,
		method: 'pay',
	},
	product: {} as DeliveryProduct,
	stepState: {} as InitialState['stepState'],
	steps: [
		{
			id: StepType.ACCOUNT,
			path: '/',
			title: 'Account',
		},
		{
			id: StepType.PROJECT,
			path: '/project',
			title: 'Project',
		},
		{
			id: StepType.LICENSES,
			path: '/license',
			title: 'Licenses',
		},
		{
			id: StepType.PAYMENT,
			path: '/payment',
			title: 'Payment Method',
		},
	],
};

export type Actions = ActionMap<GetAppPayload>[keyof ActionMap<GetAppPayload>];

const reducer = (state: InitialState, action: Actions) => {
	switch (action.type) {
		case 'SET_ACCOUNT': {
			return {
				...state,
				account: action.payload,
			};
		}

		case 'SET_BILLING_ADDRESS': {
			return {
				...state,
				payment: {
					...state.payment,
					billingAddress: action.payload,
				},
			};
		}

		case 'SET_CART': {
			return {
				...state,
				license: {
					...state.license,
					cart: action.payload,
				},
			};
		}

		case 'SET_CART_ITEMS': {
			return {
				...state,
				license: {
					...state.license,
					cartItems: action.payload,
				},
			};
		}

		case 'SET_EULA_CHECKBOX': {
			const eulaCheckbox = action.payload;

			return {...state, payment: {...state.payment, eulaCheckbox}};
		}

		case 'SET_INVOICE': {
			return {
				...state,
				payment: {
					...state.payment,
					invoice: action.payload,
				},
			};
		}

		case 'SET_LICENSE_TYPE': {
			return {
				...state,
				license: {
					...state.license,
					type: action.payload,
				},
			};
		}

		case 'SET_PAYMENT_METHOD': {
			return {
				...state,
				payment: {
					...state.payment,
					method: action.payload,
				},
			};
		}

		case 'SET_PROJECT': {
			return {
				...state,
				project: action.payload,
			};
		}

		case 'SET_STEP': {
			return {
				...state,
				currentStep: action.payload,
			};
		}

		case 'SET_SELETED_SKU': {
			return {
				...state,
				license: {
					...state.license,
					selectedSKU: action.payload,
				},
			};
		}

		default:
			return state;
	}
};

type GetAppPayload = {
	SET_ACCOUNT: Account;
	SET_BILLING_ADDRESS: BillingAddress;
	SET_CART: Cart | undefined;
	SET_CART_ITEMS: CartItem[];
	SET_EULA_CHECKBOX: boolean;
	SET_INVOICE: Invoice;
	SET_LICENSE_TYPE: LicenseType;
	SET_PAYMENT_METHOD: PaymentMethodSelector;
	SET_PRODUCT: DeliveryProduct;
	SET_PROJECT: string;
	SET_SELETED_SKU: unknown;
	SET_STEP: number;
};

type GetAppContextProviderProps = {
	children: ReactNode;
};

export const GetAppContext = createContext<
	[InitialState, (param: Actions) => void]
>([initialState, () => null]);

const GetAppContextProvider: React.FC<GetAppContextProviderProps> = ({
	children,
}) => {
	const navigate = useNavigate();
	const [state, dispatch] = useReducer(reducer, initialState);
	const {data: product} = useDeliveryProduct(getUrlParam('productId') ?? '');

	const isCloudApp = isCloudProduct(product);

	const appResourceInfo = useGetResourceInfo({
		product,
		selectedProject: state.project,
		shouldFetch: isCloudApp,
	});

	const {hasConsoleProjectsAvailable} = appResourceInfo;

	const isFreeApp =
		product?.productSpecifications.some(
			({specificationKey, value}) =>
				specificationKey ===
					PRODUCT_SPECIFICATION_KEY.APP_PRICING_MODEL &&
				value === 'Free'
		) ?? false;

	const steps = useMemo(
		() =>
			state.steps.filter(({id}) =>
				isCloudApp ? true : id !== StepType.PROJECT
			),
		[isCloudApp, state.steps]
	);

	const isValid = useMemo(() => {
		const currentStep = steps[state.currentStep];
		const currentStepId = currentStep.id;

		if (StepType.ACCOUNT === currentStepId) {
			const isAccountValid = !!state.account;

			if (isFreeApp) {
				return isAccountValid && state.payment.eulaCheckbox;
			}

			return isAccountValid;
		}

		if (StepType.PROJECT === currentStepId) {
			return state.project || !hasConsoleProjectsAvailable;
		}

		if (StepType.LICENSES === currentStepId) {
			if (state.license.type === 'TRIAL') {
				return state.license.selectedSKU;
			}

			return !!state.license.cart && !!state.license.cartItems.length;
		}

		// Payment Flow

		const paymentMethod = state.payment.method;

		const isAddressValid = zodSchema.billingAddress.safeParse(
			state.payment.billingAddress
		);

		if (!isAddressValid.success) {
			return false;
		}

		if (paymentMethod === 'pay') {
			return state.payment.eulaCheckbox;
		}

		if (paymentMethod === 'trial') {
			return isAddressValid;
		}

		if (paymentMethod === 'order') {
			const invoiceValues = Object.values(state.payment.invoice);

			return (
				!!invoiceValues.length &&
				invoiceValues.every((value) => value.trim())
			);
		}

		return false;
	}, [hasConsoleProjectsAvailable, isFreeApp, state, steps]);

	const stepState = {
		current: steps[state.currentStep],
		next: steps[state.currentStep + 1],
		previous: steps[state.currentStep - 1],
	};

	return (
		<GetAppContext.Provider
			value={[
				{
					...state,
					appResourceInfo,
					formState: {
						isValid,
					},
					isCloudApp,
					product: product as DeliveryProduct,
					stepState: {
						onNext() {
							dispatch({
								payload: state.currentStep + 1,
								type: 'SET_STEP',
							});

							navigate(stepState.next.path, {replace: true});
						},
						onPrevious() {
							dispatch({
								payload: state.currentStep - 1,
								type: 'SET_STEP',
							});

							navigate(stepState.previous.path, {replace: true});
						},
					},
					steps,
				},
				dispatch,
			]}
		>
			{children}
		</GetAppContext.Provider>
	);
};

const useGetAppContext = () => useContext(GetAppContext);

export {useGetAppContext};

export default GetAppContextProvider;
