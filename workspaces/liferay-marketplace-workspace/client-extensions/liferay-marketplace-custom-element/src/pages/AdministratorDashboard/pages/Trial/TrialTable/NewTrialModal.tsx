/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import useSWR from 'swr';
import HeadlessCommerceAdminCatalogImpl from '../../../../../services/rest/HeadlessCommerceAdminCatalog';
import {ClaySelect} from '@clayui/form';
import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import {useState} from 'react';
import headlessCommerceDeliveryCart from '../../../../../services/rest/HeadlessCommerceDeliveryCart';
import useMarketplaceSpringBootOAuth2 from '../../../../../hooks/useMarketplaceSpringBootOAuth2';
import {ORDER_TYPES} from '../../../../../enums/Order';
import {Liferay} from '../../../../../liferay/liferay';

type NewTrialModal = ReturnType<typeof useModal> & {
	revalidate: () => void;
};

interface ProductWithPurchasable extends Product {
	skus: (SKU & {purchasable: boolean})[];
}

const NewTrialModal: React.FC<NewTrialModal> = ({
	observer,
	onOpenChange,
	revalidate,
}) => {
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();
	const [selectedApp, setSelectedApp] = useState({
		accountId: '',
		productId: '',
	});
	const {channel, myUserAccount} = useMarketplaceContext();
	const {data: apps} = useSWR<APIResponse<ProductWithPurchasable>>(
		'administrator-dashboard/trial',
		() =>
			HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					'nestedFields': 'productSpecifications,skus',
					'skus.accountId': '-1',
				})
			)
	);

	const cloudApps = apps?.items?.filter((product) =>
		product?.productSpecifications.some(
			({specificationKey, value}) =>
				specificationKey === 'type' && value.en_US === 'cloud'
		)
	);

	const publishedCloudApps = cloudApps?.filter(
		(app) => app.productStatus === 0
	);

	const {accountBriefs} = myUserAccount;

	const onSubmit = async () => {
		const accountId = Number(selectedApp.accountId);

		const skus = apps?.items?.find(
			(app) => app.productId === Number(selectedApp.productId)
		);

		const skuId = skus?.skus?.find((sku) => sku.purchasable === true);

		const cart = await headlessCommerceDeliveryCart.createCart(channel.id, {
			accountId,
			cartItems: [
				{
					price: {
						currency: channel.currencyCode,
						discount: 0,
					},
					productId: Number(selectedApp.productId),
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: skuId?.id as number,
				},
			],
			currencyCode: channel.currencyCode,
			orderTypeExternalReferenceCode: ORDER_TYPES.SOLUTIONS7,
		});

		await headlessCommerceDeliveryCart.checkoutCart(cart.id);

		await marketplaceSpringBootOAuth2.provisioningTrial(cart.id);

		onOpenChange(false);

		await revalidate();

		Liferay.Util.openToast({
			message: 'Trial created successfully',
			type: 'success',
		});
	};

	return (
		<ClayModal center observer={observer}>
			<ClayModal.Header>New Trial</ClayModal.Header>
			<ClayModal.Body className="pb-8">
				<div className="mb-5">
					<h5>Cloud Products</h5>
					<ClaySelect
						value={selectedApp.productId}
						onChange={({target}) => {
							setSelectedApp({
								...selectedApp,
								productId: target.value,
							});
						}}
						aria-label="Select App"
						id="selectApp"
					>
						<ClaySelect.Option
							key="placeholderApp"
							label="Select an App"
							value=""
							disabled
							aria-hidden
						/>
						{publishedCloudApps?.map((app, index) => (
							<ClaySelect.Option
								key={index}
								label={app.name.en_US}
								value={app.productId}
							/>
						))}
					</ClaySelect>
				</div>

				<>
					<h5>Select Account</h5>
					<ClaySelect
						value={selectedApp.accountId || ''}
						onChange={({target}) => {
							setSelectedApp({
								...selectedApp,
								accountId: target.value,
							});
						}}
						aria-label="Select Account"
						id="selectAccount"
					>
						<ClaySelect.Option
							key="placeholderAccount"
							label="Select an Account"
							value=""
							disabled
							aria-hidden
						/>
						{accountBriefs?.map((account, index) => (
							<ClaySelect.Option
								key={index}
								label={account.name}
								value={account.id}
							/>
						))}
					</ClaySelect>
				</>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton
						disabled={
							!selectedApp.accountId.length ||
							!selectedApp.productId.length
						}
						onClick={onSubmit}
					>
						Create Trial
					</ClayButton>
				}
			/>
		</ClayModal>
	);
};

export default NewTrialModal;
