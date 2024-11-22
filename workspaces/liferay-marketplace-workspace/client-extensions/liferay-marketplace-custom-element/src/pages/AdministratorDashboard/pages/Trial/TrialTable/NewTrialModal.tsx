/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {useMemo, useState} from 'react';
import useSWR from 'swr';

import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import SearchBuilder from '../../../../../core/SearchBuilder';
import {ORDER_TYPES} from '../../../../../enums/Order';
import {PRODUCT_SPECIFICATION_KEY} from '../../../../../enums/Product';
import useDebounce from '../../../../../hooks/useDebounce';
import {Liferay} from '../../../../../liferay/liferay';
import trialOAuth2 from '../../../../../services/oauth/Trial';
import HeadlessCommerceAdminCatalogImpl from '../../../../../services/rest/HeadlessCommerceAdminCatalog';
import headlessCommerceDeliveryCart from '../../../../../services/rest/HeadlessCommerceDeliveryCart';

type NewTrialModalProps = ReturnType<typeof useModal> & {
	revalidate: () => void;
};

type ProductWithPurchasable = {
	skus: (SKU & {purchasable: boolean})[];
} & Product;

const NewTrialModal: React.FC<NewTrialModalProps> = ({
	observer,
	onOpenChange,
	revalidate,
}) => {
	const [search, setSearch] = useState('');
	const {channel, myUserAccount} = useMarketplaceContext();
	const debouncedSearch = useDebounce(search, 1000);
	const [selectedTrial, setSelectedTrial] = useState<{
		accountId: string;
		product: Product;
	}>({
		accountId: '',
		product: {} as Product,
	});
	const {data: apps, isValidating} = useSWR<
		APIResponse<ProductWithPurchasable>
	>(`administrator-dashboard/trial/products/${debouncedSearch}`, () =>
		HeadlessCommerceAdminCatalogImpl.getProducts(
			new URLSearchParams({
				'filter': SearchBuilder.contains('name', debouncedSearch),
				'nestedFields': 'productSpecifications,skus',
				'pageSize': '10',
				'skus.accountId': '-1',
			})
		)
	);

	const publishedApps = useMemo(
		() =>
			(apps?.items ?? []).map((product) => ({
				...product,
				productName: product.name.en_US,
			})),
		[apps]
	);

	const {accountBriefs = []} = myUserAccount;

	const onSubmit = async () => {
		const accountId = Number(selectedTrial.accountId);

		const isDXP = selectedTrial.product?.productSpecifications?.some(
			(spec) =>
				spec.specificationKey ===
					PRODUCT_SPECIFICATION_KEY.APP_BUILD_CLOUD_COMPATIBLE &&
				spec.value.en_US === 'dxp'
		);

		if (isDXP) {
			return Liferay.Util.openToast({
				message: 'Not possible to create Trial, for DXP Apps',
				type: 'danger',
			});
		}

		const skus = apps?.items?.find(
			(app) => app.productId === Number(selectedTrial.product.productId)
		);

		const sku = skus?.skus?.find((sku) => sku.purchasable);

		try {
			const cart = await headlessCommerceDeliveryCart.createCart(
				channel.id,
				{
					accountId,
					cartItems: [
						{
							price: {
								currency: channel.currencyCode,
								discount: 0,
							},
							productId: Number(selectedTrial.product.productId),
							quantity: 1,
							settings: {
								maxQuantity: 1,
							},
							skuId: sku?.id as number,
						},
					],
					currencyCode: channel.currencyCode,
					orderTypeExternalReferenceCode: ORDER_TYPES.SOLUTIONS7,
				}
			);

			await headlessCommerceDeliveryCart.checkoutCart(cart.id);

			await trialOAuth2.provisioningTrial(cart.id);

			onOpenChange(false);

			await revalidate();

			setTimeout(() => revalidate(), 5000);

			Liferay.Util.openToast({
				message: 'Trial created successfully',
				type: 'success',
			});
		}
		catch {
			Liferay.Util.openToast({
				message: 'Not possible to create Trial',
				type: 'danger',
			});
		}
	};

	return (
		<ClayModal center observer={observer}>
			<ClayModal.Header>New Trial</ClayModal.Header>
			<ClayModal.Body className="pb-8">
				<div className="mb-5">
					<h5>Cloud Products</h5>

					<Autocomplete
						filterKey="productName"
						items={publishedApps}
						loadingState={isValidating ? 1 : 4}
						messages={{
							loading: 'Loading...',
							notFound: 'No results found',
						}}
						onChange={setSearch}
						onItemsChange={() => {}}
						placeholder="Enter the name of the Product"
						value={search}
					>
						{(product) => (
							<Autocomplete.Item
								disabled={true}
								key={product.productId}
								onClick={() =>
									setSelectedTrial({
										...selectedTrial,
										product,
									})
								}
								{...({} as any)}
							>
								{product.productName}
							</Autocomplete.Item>
						)}
					</Autocomplete>
				</div>

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
					{accountBriefs
						.sort((accountA, accountB) =>
							accountA.name.localeCompare(accountB.name)
						)
						.map((account, index) => (
							<ClaySelect.Option
								key={index}
								label={account.name}
								value={account.id}
							/>
						))}
				</ClaySelect>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton
						disabled={
							!selectedTrial.accountId.length ||
							!Object.keys(selectedTrial.product).length
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
