import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';

import {
	getChannelById,
	getProductSKU,
	patchOrderByERC,
	postCartByChannelId,
	postCheckoutCart,
} from '../../utils/api';

import './GetAppModal.scss';

interface GetAppModalProps {
	account: {
		email: string;
		id?: number;
		image: string;
		name: string;
	};
	app: {
		createdBy: string;
		externalReferenceCode?: string;
		id: number;
		image: string;
		name: string;
		price: number;
		version: string;
	};
	channelId: number;
	handleClose: () => void;
}

export function GetAppModal({
	account,
	app,
	channelId,
	handleClose,
}: GetAppModalProps) {
	const {observer, onClose} = useModal({
		onClose: handleClose,
	});

	async function handleGetApp() {
		const channel = await getChannelById(channelId);

		const skuResponse = await getProductSKU({appProductId: app.id});

		const sku = skuResponse.items[0];

		const newCart: Partial<Cart> = {
			accountId: account.id as number,
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
					skuId: sku.id as number,
				},
			],
			currencyCode: channel.currencyCode,
		};

		const cartResponse = await postCartByChannelId({
			cartBody: newCart,
			channelId,
		});

		const cartCheckoutResponse = await postCheckoutCart({
			cartId: cartResponse.id,
		});

		const newOrderStatus = {
			orderStatus: 1,
		};

		await patchOrderByERC(cartCheckoutResponse.orderUUID, newOrderStatus);
	}

	const freeApp = Number(app.price) === 0;

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
									{account.name}
								</span>

								<span className="get-app-modal-body-card-header-right-content-account-info-email">
									{account.email}
								</span>
							</div>

							<img
								alt="Account icon"
								className="get-app-modal-body-card-header-right-content-account-info-icon"
								src={account.image}
							/>
						</div>
					</div>

					<div className="get-app-modal-body-container">
						<div className="get-app-modal-body-content-container">
							<div className="get-app-modal-body-content-left">
								<img
									alt="App Image"
									className="get-app-modal-body-content-image"
									src={app.image}
								/>

								<div className="get-app-modal-body-content-app-info-container">
									<span className="get-app-modal-body-content-app-info-name">
										{app.name}
									</span>

									<span className="get-app-modal-body-content-app-info-version">
										{app.version} by {app.createdBy}.
									</span>
								</div>
							</div>

							<div className="get-app-modal-body-content-right">
								<span className="get-app-modal-body-content-right-price">
									Price
								</span>

								<span className="get-app-modal-body-content-right-value">
									{freeApp ? 'Free' : `$ ${app.price}`}
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
