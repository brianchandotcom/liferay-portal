import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import {getCompanyId} from '../../liferay/constants';
import {Liferay} from '../../liferay/liferay';
import {
	getAccountInfo,
	getAccounts,
	getChannels,
	getDeliveryProduct,
	getProduct,
	getProductSKU,
	getSKUCustomFieldExpandoValue,
	getUserAccount,
	getUserAccountsById,
	patchOrderByERC,
	postCartByChannelId,
	postCheckoutCart,
} from '../../utils/api';

import './GetAppModal.scss';

interface App {
	createdBy: string;
	id: number;
	name: {en_US: string} | string;
	price: number;
	urlImage: string;
}

interface GetAppModalProps {
	handleClose: () => void;
}

export function GetAppModal({handleClose}: GetAppModalProps) {
	const {observer, onClose} = useModal({
		onClose: handleClose,
	});
	const [account, setAccount] = useState<AccountBrief>();
	const [accountPublisher, setAccountPublisher] = useState<AccountBrief>();
	const [app, setApp] = useState<App>({
		createdBy: '',
		id: 0,
		name: '',
		price: 0,
		urlImage: '',
	});
	const [appVersion, setAppVersion] = useState<string>();
	const [channel, setChannel] = useState<Channel>({
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

	useEffect(() => {
		const getModalInfo = async () => {
			const channels = await getChannels();

			const channel =
				channels.find(
					(channel) => channel.name === 'Marketplace Channel'
				) || channels[0];

			setChannel(channel);

			const app = await getDeliveryProduct({
				appId: Liferay.MarketplaceCustomerFlow.appId,
				channelId: channel.id,
			});

			setApp(app);

			const currentUser = await getUserAccount();

			setCurrentUser(currentUser);

			const userAccounts = await getUserAccountsById();

			let accountId;

			if (userAccounts.accountBriefs.length) {
				accountId = userAccounts.accountBriefs[0].id;
			}
			else {
				accountId = 50307;
			}

			const currentAccount = await getAccountInfo({
				accountId,
			});

			setAccount(currentAccount);

			const skuResponse = await getProductSKU({
				appProductId: Liferay.MarketplaceCustomerFlow.appId,
			});

			const sku = skuResponse.items[0];

			setSku(sku);

			const version = await getSKUCustomFieldExpandoValue({
				companyId: parseInt(getCompanyId()),
				customFieldName: 'version',
				skuId: sku.id,
			});

			setAppVersion(version);

			const adminProduct = await getProduct({
				appERC: app?.externalReferenceCode,
			});

			const catalogID = adminProduct?.catalogId;
			const accounts = await getAccounts();

			const accountPublisher = accounts?.items.find(
				({customFields}: AccountBrief) => {
					return customFields?.CatalogID == catalogID;
				}
			);

			setAccountPublisher(accountPublisher);
		};

		getModalInfo();
	}, []);

	async function handleGetApp() {
		const newCart: Partial<Cart> = {
			accountId: account?.id || 50307,
			cartItems: [
				{
					price: {
						currency: channel.currencyCode,
						discount: 0,
						finalPrice: sku.price,
						price: sku.price,
					},
					productId: app?.id,
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: sku.id as number,
				},
			],
			currencyCode: channel.currencyCode,
		};

		const cartResponse = await postCartByChannelId({
			cartBody: newCart,
			channelId: channel.id,
		});

		const cartCheckoutResponse = await postCheckoutCart({
			cartId: cartResponse.id,
		});

		const newOrderStatus = {
			orderStatus: 1,
		};

		await patchOrderByERC(cartCheckoutResponse.orderUUID, newOrderStatus);

		onClose();
	}

	const freeApp = Number(sku.price) === 0;

	return (
		<ClayModal observer={observer}>
			<div className="get-app-modal-header-container">
				<div className="get-app-modal-header-left-content">
					<span className="get-app-modal-header-title">
						Confirm Install
					</span>

					<span className="get-app-modal-header-description">
						Confirm installation of this free app.
					</span>
				</div>

				<ClayButton displayType="unstyled" onClick={onClose}>
					<ClayIcon symbol="times" />
				</ClayButton>
			</div>

			<ClayModal.Body>
				<div className="get-app-modal-body-card-container">
					<div className="get-app-modal-body-card-header">
						<span className="get-app-modal-body-card-header-left-content">
							App Details
						</span>

						<div className="get-app-modal-body-card-header-right-content-container">
							<div className="get-app-modal-body-card-header-right-content-account-info">
								<span className="get-app-modal-body-card-header-right-content-account-info-name">
									{account?.name}
								</span>

								<span className="get-app-modal-body-card-header-right-content-account-info-email">
									{currentUser?.emailAddress}
								</span>
							</div>

							<img
								alt="Account icon"
								className="get-app-modal-body-card-header-right-content-account-info-icon"
								src={account?.logoURL}
							/>
						</div>
					</div>

					<div className="get-app-modal-body-container">
						<div className="get-app-modal-body-content-container">
							<div className="get-app-modal-body-content-left">
								<img
									alt="App Image"
									className="get-app-modal-body-content-image"
									src={app?.urlImage.replace(':8080', '')}
								/>

								<div className="get-app-modal-body-content-app-info-container">
									<span className="get-app-modal-body-content-app-info-name">
										{typeof app?.name === 'string'
											? app?.name
											: app?.name.en_US}
									</span>

									<span className="get-app-modal-body-content-app-info-version">
										{appVersion} by {accountPublisher?.name}
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
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<button
							className="get-app-modal-button-cancel"
							onClick={onClose}
						>
							Cancel
						</button>

						<button
							className="get-app-modal-button-get-this-app"
							onClick={handleGetApp}
						>
							Get This App
						</button>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
