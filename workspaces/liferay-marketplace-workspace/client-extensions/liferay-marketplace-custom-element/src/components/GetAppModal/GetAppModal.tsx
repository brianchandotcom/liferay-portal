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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useCallback, useEffect, useState} from 'react';

import {getCompanyId} from '../../liferay/constants';
import {Liferay} from '../../liferay/liferay';
import {
	baseURL,
	getAccountAddressesFromCommerce,
	getAccounts,
	getChannels,
	getDeliveryProduct,
	getOrderTypes,
	getPaymentMethodURL,
	getProduct,
	getProductSKU,
	getProductSpecifications,
	getSKUCustomFieldExpandoValue,
	getUserAccount,
	getUserAccountsById,
	patchOrderByERC,
	postCartByChannelId,
	postCheckoutCart,
} from '../../utils/api';
import {showAccountImage, showAppImage} from '../../utils/util';
import {AccountSelector} from './AccountSelector';

import './GetAppModal.scss';
import {SelectPaymentMethod} from './SelectPaymentMethod';
import {StepTracker} from './StepTracker';

interface App {
	createdBy: string;
	id: number;
	name: {en_US: string} | string;
	price: number;
	productId: number;
	slug: string;
	urlImage: string;
}

interface GetAppModalProps {
	handleClose: () => void;
}

const initialBillingAddress = {
	city: '',
	country: '',
	countryISOCode: 'US',
	name: '',
	phoneNumber: '',
	regionISOCode: '',
	street1: '',
	street2: '',
	zip: '',
};

export function GetAppModal({handleClose}: GetAppModalProps) {
	const {observer, onClose} = useModal({
		onClose: handleClose,
	});
	const [activeAccounts, setActiveAccounts] =
		useState<Partial<AccountBrief>[]>();
	const [accountPublisher, setAccountPublisher] = useState<Account>();
	const [accounts, setAccounts] = useState<AccountBrief[]>();
	const [app, setApp] = useState<App>({
		createdBy: '',
		id: 0,
		name: '',
		price: 0,
		productId: 0,
		slug: '',
		urlImage: '',
	});
	const [appVersion, setAppVersion] = useState<string>('');
	const [channel, setChannel] = useState<Channel>({
		channelId: 0,
		currencyCode: '',
		externalReferenceCode: '',
		id: 0,
		name: '',
		siteGroupId: 0,
		type: '',
	});
	const [currentUser, setCurrentUser] = useState<{emailAddress: string}>();
	const [sku, setSku] = useState<SKU>({
		cost: 0,
		externalReferenceCode: '',
		id: 0,
		price: 0,
		sku: '',
		skuOptions: [],
	});

	const [addresses, setAddresses] = useState<BillingAddress[]>([]);

	const [billingAddress, setBillingAddress] = useState<BillingAddress>(
		initialBillingAddress
	);

	const [selectedAccount, setSelectedAccount] =
		useState<Partial<AccountBrief>>();

	const [selectedPaymentMethod, setSelectedPaymentMethod] =
		useState<PaymentMethodSelector>('pay');

	const [selectedAddress, setSelectedAddress] = useState('');

	const [showNewAddressButton, setShowNewAddressButton] = useState(false);

	const [skus, setSkus] = useState<SKU[]>([]);

	const [enableTrialMethod, setEnableTrialMethod] = useState<boolean>(false);

	const [enablePurchaseButton, setEnablePurchaseButton] = useState(false);

	const [purchaseOrderNumber, setPurchaseOrderNumber] = useState<string>('');
	const [email, setEmail] = useState<string>('');

	const [freeApp, setFreeApp] = useState<boolean>(false);

	const [showSelectAccount, setShowSelectAccount] = useState(true);

	const [steps, setSteps] = useState([
		{
			checked: false,
			name: 'Select Account',
			selected: true,
		},
		{
			checked: false,
			name: 'Select Payment method',
			selected: false,
		},
	]);

	useEffect(() => {
		const getAddresses = async () => {
			if (selectedAccount?.id) {
				const billingAddresses = await getAccountAddressesFromCommerce(
					selectedAccount?.id as number
				);

				setAddresses(billingAddresses.items);
			}
		};

		getAddresses();
	}, [selectedAccount]);

	useEffect(() => {
		if (!freeApp && skus.length) {
			let selectedSku;

			if (selectedPaymentMethod === 'trial') {
				selectedSku = skus.filter((sku) =>
					sku.skuOptions.find((option) => option.value === 'yes')
				)[0];
			}
			else if (selectedPaymentMethod === 'pay') {
				selectedSku = skus.find((sku) => sku.price !== 0);
			}
			else {
				selectedSku = skus[0];
			}

			setSku(selectedSku as SKU);
		}
	}, [selectedPaymentMethod, freeApp, skus]);

	useEffect(() => {
		const getModalInfo = async () => {
			const channels = await getChannels();

			const channel =
				channels.find(
					(channel) => channel.name === 'Marketplace Channel'
				) || channels[0];

			setChannel(channel);

			const currentUser = await getUserAccount();

			setCurrentUser(currentUser);

			const response = await getUserAccountsById();

			const userAccounts = (await response.json()) as UserAccount;
			let accountId;

			if (userAccounts.accountBriefs.length) {
				accountId = userAccounts.accountBriefs[0].id;
			}
			else {
				accountId = 50307;
			}

			setAccounts(userAccounts.accountBriefs);
			const app = await getDeliveryProduct({
				accountId,
				appId: Liferay.MarketplaceCustomerFlow.appId,
				channelId: channel.id,
			});

			setApp(app);

			const skuResponse = await getProductSKU({
				appProductId: Liferay.MarketplaceCustomerFlow.appId,
			});

			setSkus(skuResponse.items);

			let newSku;

			if (skuResponse.items.length > 1) {
				const {items} = skuResponse;
				const isTrial = !!items.find(
					({sku, skuOptions: [skuOption]}) =>
						sku.endsWith('ts') && skuOption.value === 'yes'
				);
				setEnableTrialMethod(isTrial);
				newSku = skuResponse.items.find((sku) => sku.price !== 0);
			}
			else {
				newSku = skuResponse.items[0];
			}
			setSku(newSku as SKU);

			if (newSku?.price === 0) {
				setFreeApp(true);
				setSelectedPaymentMethod(null);
			}

			const versionResponse = await getSKUCustomFieldExpandoValue({
				companyId: Number(getCompanyId()),
				customFieldName: 'version',
				skuId: newSku?.id as number,
			});

			if (typeof versionResponse === 'string') {
				setAppVersion(versionResponse);
			}

			const adminProduct = await getProduct({
				appERC: app?.externalReferenceCode,
			});

			const catalogId = adminProduct?.catalogId;
			const accounts = await getAccounts();

			const accountPublisher = accounts.items.find(
				({customFields}: Account) => {
					if (customFields) {
						const catalogIdField = customFields.find(
							(customField) => customField.name === 'CatalogId'
						);

						return (
							catalogIdField?.customValue.data ===
							String(catalogId)
						);
					}
				}
			);

			setAccountPublisher(accountPublisher);
		};
		getModalInfo();
	}, []);

	async function handleGetApp() {
		const productSpecifications = await getProductSpecifications({
			appProductId: app.productId,
		});

		const {value: specificationValue} = productSpecifications.find(
			({value}) => value['en_US'] === 'cloud' || value['en_US'] === 'dxp'
		) as ProductSpecification;

		const orderTypes = await getOrderTypes();

		const orderType = orderTypes.find(({externalReferenceCode}) => {
			if (specificationValue['en_US'] === 'cloud') {
				return externalReferenceCode === 'CLOUDAPP';
			}

			return externalReferenceCode === 'DXPAPP';
		}) as OrderType;

		const cart: Partial<Cart> = {
			accountId: selectedAccount?.id as number,
			cartItems: [
				{
					price: {
						currency: channel.currencyCode,
						discount: 0,
						finalPrice: app.price,
						price: app.price,
					},
					productId: app.id,
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: sku?.id as number,
				},
			],
			currencyCode: channel.currencyCode,
			orderTypeExternalReferenceCode: orderType.externalReferenceCode,
			orderTypeId: orderType.id as number,
		};

		let newCart: Partial<Cart> = {};

		let cartResponse;

		if (freeApp) {
			newCart = {
				...cart,
			};

			cartResponse = await postCartByChannelId({
				cartBody: newCart,
				channelId: channel.id,
			});

			const cartCheckoutResponse = await postCheckoutCart({
				cartId: cartResponse.id,
			});

			const newOrderValues = {
				orderStatus: 1,
			};

			await patchOrderByERC(
				cartCheckoutResponse.orderUUID,
				newOrderValues
			);
		}
		else {
			newCart = {};
			if (selectedPaymentMethod === 'pay') {
				newCart = {
					...cart,
					billingAddress,
					paymentMethod: 'paypal',
				};
			}

			if (selectedPaymentMethod === 'order') {
				newCart = {
					...cart,
					author: email,
					billingAddress,
					purchaseOrderNumber,
				};
			}

			if (selectedPaymentMethod === 'trial') {
				newCart = {
					...cart,
					billingAddress,
				};
			}

			cartResponse = await postCartByChannelId({
				cartBody: newCart,
				channelId: channel.id,
			});

			await postCheckoutCart({cartId: cartResponse.id});
		}

		const nextStepsCallbackURL = `${Liferay.ThemeDisplay.getCanonicalURL().replace(
			`/p/${app.slug}`,
			''
		)}/next-steps?orderId=${cartResponse.id}&logoURL=${
			selectedAccount?.logoURL
		}&appLogoURL=${app?.urlImage}&accountName=${
			selectedAccount?.name
		}&accountLogo=${selectedAccount?.logoURL}&appName=${app.name}`;

		const paymentMethodURL = await getPaymentMethodURL(
			cartResponse.id,
			nextStepsCallbackURL
		);

		window.location.href =
			selectedPaymentMethod === 'pay'
				? paymentMethodURL
				: nextStepsCallbackURL;

		onClose();
	}

	const handleChangeStep = () => {
		setShowSelectAccount(false);
		setSteps([
			{
				checked: true,
				name: 'Select Account',
				selected: false,
			},
			{
				checked: false,
				name: 'Select Payment method',
				selected: true,
			},
		]);
	};

	const handleClick = useCallback(() => {
		if (!freeApp && showSelectAccount && selectedAccount) {
			return handleChangeStep();
		}
		if (
			(freeApp && selectedAccount) ||
			(!showSelectAccount && enablePurchaseButton)
		) {
			return handleGetApp();
		}

		return;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [enablePurchaseButton, freeApp, selectedAccount, showSelectAccount]);

	const getButtonText = () => {
		if (!freeApp) {
			if (showSelectAccount) {
				return 'Continue';
			}
			if (selectedPaymentMethod === 'pay') {
				return `Pay $${sku?.price} Now`;
			}
			if (selectedPaymentMethod === 'trial') {
				return 'Start Free Trial';
			}
			if (selectedPaymentMethod === 'order') {
				return 'Request Purchase Order';
			}
		}
		else {
			return 'Get This App';
		}
	};

	return (
		<div className="modal-open">
			<ClayModal observer={observer}>
				<div className="get-app-modal-header-container">
					<div className="get-app-modal-header-left-content">
						<span className="get-app-modal-header-title">
							{freeApp ? 'Confirm Install' : 'Confirm Payment'}
						</span>

						<span className="get-app-modal-header-description">
							{freeApp
								? 'Confirm installation of this free app.'
								: 'Choose app payment method and edit purchasing details'}
						</span>
					</div>

					<ClayButton displayType="unstyled" onClick={onClose}>
						<ClayIcon symbol="times" />
					</ClayButton>
				</div>

				<ClayModal.Body
					className="get-app-modal-body-card-body"
					scrollable={
						activeAccounts?.length
							? activeAccounts.length > 3
							: false
					}
				>
					<div className="get-app-modal-body-card-container">
						<div className="get-app-modal-body-card-header">
							<span className="get-app-modal-body-card-header-left-content">
								App Details
							</span>

							{selectedAccount && (
								<div className="get-app-modal-body-card-header-right-content-container">
									<div className="get-app-modal-body-card-header-right-content-account-info">
										<span className="get-app-modal-body-card-header-right-content-account-info-name">
											{selectedAccount?.name}
										</span>

										<span className="get-app-modal-body-card-header-right-content-account-info-email">
											{currentUser?.emailAddress}
										</span>
									</div>

									<img
										alt="Account icon"
										className="get-app-modal-body-card-header-right-content-account-info-icon"
										src={showAccountImage(
											selectedAccount?.logoURL
										)}
									/>
								</div>
							)}
						</div>

						<div className="get-app-modal-body-container">
							<div className="get-app-modal-body-content-container">
								<div className="get-app-modal-body-content-left">
									<img
										alt="App Image"
										className="get-app-modal-body-content-image"
										src={showAppImage(app.urlImage).replace(
											app.urlImage.split('/o')[0],
											baseURL
										)}
									/>

									<div className="get-app-modal-body-content-app-info-container">
										<span className="get-app-modal-body-content-app-info-name">
											{typeof app?.name === 'string'
												? app?.name
												: app?.name.en_US}
										</span>

										<span className="get-app-modal-body-content-app-info-version">
											{appVersion} by{' '}
											{accountPublisher?.name}
										</span>
									</div>
								</div>

								<div className="get-app-modal-body-content-right">
									<span className="get-app-modal-body-content-right-price">
										Price
									</span>

									<span className="get-app-modal-body-content-right-value">
										{freeApp ? 'Free' : `$ ${sku.price}`}
									</span>

									{!freeApp && (
										<div className="get-app-modal-body-content-right-subscription-container">
											<span className="get-app-modal-body-content-right-subscription-text">
												Annually
											</span>
										</div>
									)}
								</div>
							</div>

							<div>
								<ClayIcon
									className="get-app-modal-body-content-alert-icon"
									symbol="info-panel-open"
								/>

								<span className="get-app-modal-body-content-alert-message">
									{freeApp
										? ' A free app does not include support, maintenance or updates from the publisher.'
										: 'A subscription license includes support, maintenance and updates for the app as long as the subscription is current.'}
								</span>
							</div>
						</div>
					</div>

					<StepTracker freeApp={freeApp} steps={steps} />

					{showSelectAccount ? (
						<AccountSelector
							accounts={accounts as AccountBrief[]}
							activeAccounts={activeAccounts as AccountBrief[]}
							selectedAccount={selectedAccount}
							setActiveAccounts={setActiveAccounts}
							setSelectedAccount={setSelectedAccount}
							userEmail={currentUser?.emailAddress as string}
						/>
					) : (
						!freeApp && (
							<SelectPaymentMethod
								addresses={addresses}
								billingAddress={billingAddress}
								email={email}
								enableTrialMethod={enableTrialMethod}
								purchaseOrderNumber={purchaseOrderNumber}
								selectedAddress={selectedAddress}
								selectedPaymentMethod={selectedPaymentMethod}
								setBillingAddress={setBillingAddress}
								setEmail={setEmail}
								setEnablePurchaseButton={
									setEnablePurchaseButton
								}
								setPurchaseOrderNumber={setPurchaseOrderNumber}
								setSelectedAddress={setSelectedAddress}
								setSelectedPaymentMethod={
									setSelectedPaymentMethod
								}
								setShowNewAddressButton={
									setShowNewAddressButton
								}
								showNewAddressButton={showNewAddressButton}
							/>
						)
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<div className="get-app-modal-footer">
							<ClayButton.Group spaced>
								<button
									className="get-app-modal-button-cancel"
									onClick={onClose}
								>
									Cancel
								</button>

								<button
									className={classNames(
										'get-app-modal-button-get-this-app',
										{
											'get-app-modal-button-get-this-app-enabled':
												showSelectAccount &&
												selectedAccount,
										},
										{
											'get-app-modal-button-get-this-app-disabled':
												(showSelectAccount &&
													!selectedAccount) ||
												(!showSelectAccount &&
													!enablePurchaseButton),
										}
									)}
									onClick={handleClick}
								>
									{getButtonText()}
								</button>
							</ClayButton.Group>

							<span>
								{!freeApp &&
									!showSelectAccount &&
									selectedPaymentMethod === 'pay' &&
									'You will be redirected to PayPal to complete payment'}
							</span>
						</div>
					}
				/>
			</ClayModal>
		</div>
	);
}
