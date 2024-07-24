/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {useState} from 'react';
import useSWR from 'swr';

import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import {ORDER_TYPES} from '../../../../../enums/Order';
import useMarketplaceSpringBootOAuth2 from '../../../../../hooks/useMarketplaceSpringBootOAuth2';
import {Liferay} from '../../../../../liferay/liferay';
import HeadlessCommerceAdminCatalogImpl from '../../../../../services/rest/HeadlessCommerceAdminCatalog';
import headlessCommerceDeliveryCart from '../../../../../services/rest/HeadlessCommerceDeliveryCart';

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
	const {channel, myUserAccount} = useMarketplaceContext();
	const [selectedTrial, setSelectedTrial] = useState({
		accountId: '',
		productId: '',
	});
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
	const {accountBriefs} = myUserAccount;

	const publishedCloudApps = apps?.items?.filter(
		(product) =>
			product?.productSpecifications?.some(
				(spec) =>
					spec.specificationKey === 'type' &&
					spec.value.en_US === 'cloud'
			) && product.productStatus === 0
	);

	const onSubmit = async () => {
		const accountId = Number(selectedTrial.accountId);

		const skus = apps?.items?.find(
			(app) => app.productId === Number(selectedTrial.productId)
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
					productId: Number(selectedTrial.productId),
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
						aria-label="Select App"
						id="selectApp"
						onChange={({target}) => {
							setSelectedTrial({
								...selectedTrial,
								productId: target.value,
							});
						}}
						value={selectedTrial.productId}
					>
						<ClaySelect.Option
							aria-hidden
							disabled
							key="placeholderApp"
							label="Select an App"
							value=""
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
						aria-label="Select Account"
						id="selectAccount"
						onChange={({target}) => {
							setSelectedTrial({
								...selectedTrial,
								accountId: target.value,
							});
						}}
						value={selectedTrial.accountId || ''}
					>
						<ClaySelect.Option
							aria-hidden
							disabled
							key="placeholderAccount"
							label="Select an Account"
							value=""
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
							!selectedTrial.accountId.length ||
							!selectedTrial.productId.length
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
