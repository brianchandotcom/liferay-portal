/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import {useEffect, useMemo, useState} from 'react';

import ProductPurchase from '../../../../../components/ProductPurchase';
import RadioCardList from '../../../../../components/RadioCardList/RadioCardList';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import HeadlessCommerceDeliveryCart from '../../../../../services/rest/HeadlessCommerceDeliveryCart';
import {getAiHubTokenSKUs} from '../../../../../utils/productUtils';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {cartStore, productPurchaseStore} from '../../../store';

import './AIHubForm.scss';

import '../index.scss';

const AIHubTokenSelection = () => {
	const {
		actions: {nextStep},
		product,
		productPurchaseCart,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	useEffect(() => {
		productPurchaseStore.send({
			licenseType: 'PAID',
			type: 'setLicenseType',
		});
	}, []);

	const aiHubTokens = useMemo(() => getAiHubTokenSKUs(product), [product]);

	const [selectedSkuId, setSelectedSkuId] = useState<number | undefined>();
	const [isCartLoading, setIsCartLoading] = useState(true);

	useEffect(() => {
		if (!selectedAccount?.id) {
			setIsCartLoading(true);

			return;
		}

		let active = true;
		setIsCartLoading(true);
		setSelectedSkuId(undefined);
		cartStore.send({type: 'reset'});

		(async () => {
			try {
				const channelId = Liferay.CommerceContext.commerceChannelId;

				const {items: carts} =
					await HeadlessCommerceDeliveryCart.getAccountCarts(
						selectedAccount.id,
						channelId
					);

				if (!active) {
					return;
				}

				if (carts?.length > 0) {
					const [cart] = carts;
					const {items: cartItems} =
						await HeadlessCommerceDeliveryCart.getCartItems(
							cart.id
						);
					if (active) {
						cartStore.send({cart, type: 'setCart'});
						cartStore.send({cartItems, type: 'setCartItems'});
					}
				}
			}
			catch (error) {
				console.error(error);
			}
			finally {
				if (active) {
					setIsCartLoading(false);
				}
			}
		})();

		return () => {
			active = false;
		};
	}, [selectedAccount?.id]);

	useEffect(() => {
		if (isCartLoading) {
			return;
		}

		if (productPurchaseCart.cartItems.length) {
			const cartSkuIds = productPurchaseCart.cartItems.map(
				(item) => item.skuId
			);
			const tokensInCart = aiHubTokens.filter((token: DeliverySKU) =>
				cartSkuIds.includes(token.id)
			);

			if (tokensInCart.length) {
				const activeToken = tokensInCart[0];
				setSelectedSkuId(activeToken.id);

				if (tokensInCart.length > 1) {
					const tokenSkuIds = aiHubTokens.map(
						(token: any) => token.id
					);
					const filteredItems = productPurchaseCart.cartItems.filter(
						(item) =>
							!tokenSkuIds.includes(item.skuId) ||
							item.skuId === activeToken.id
					);
					cartStore.send({
						cartItems: filteredItems,
						type: 'setCartItems',
					});
				}

				return;
			}
		}

		if (!selectedSkuId && !!aiHubTokens.length) {
			const defaultSkuId = aiHubTokens[0].id;
			setSelectedSkuId(defaultSkuId);
			productPurchaseCart.addCart(product.id, defaultSkuId);
		}
	}, [
		isCartLoading,
		productPurchaseCart.cartItems,
		selectedSkuId,
		aiHubTokens,
	]);

	const handleSelectToken = async (token: any) => {
		const tokenValue = token?.value;
		const newSkuId = tokenValue?.id;

		if (!newSkuId || selectedSkuId === newSkuId) {
			return;
		}

		const tokenSkuIds = aiHubTokens.map((token: any) => token.id);
		const filteredItems = productPurchaseCart.cartItems.filter(
			(item) => !tokenSkuIds.includes(item.skuId)
		);

		const newCartItems = [
			...filteredItems,
			{productId: product.id, quantity: 1, skuId: newSkuId} as CartItem,
		];

		cartStore.send({cartItems: newCartItems, type: 'setCartItems'});
		setSelectedSkuId(newSkuId);
	};

	const onSubmit = () => {
		nextStep();
	};

	return (
		<ProductPurchase.Shell
			className="liferay-ai-hub-form"
			title={i18n.translate('select-desired-amount-of-tokens')}
		>
			<p className="mb-6 text-black-50">
				{i18n.translate(
					'pick-one-of-the-following-three-options-to-immediately-obtain-extra-tokens-to-foster-your-ai-hub-capabilities'
				)}
			</p>

			<div>
				{aiHubTokens.length ? (
					<RadioCardList
						contentList={aiHubTokens.map((token: any) => ({
							...token,
							selected: selectedSkuId === token?.id,
							title: (
								<div className="align-items-center d-flex justify-content-between pt-2">
									<div>
										<p className="liferay-ai-hub-form-token-name mb-1">
											{
												token.skuOptions[0]
													.skuOptionValueNames[0]
											}
										</p>
										<p className="liferay-ai-hub-form-token-description mb-0 text-black-50">
											{
												token.customFields?.find(
													(field: any) =>
														field.name ===
														'Description'
												)?.customValue.data
											}
										</p>
									</div>
									<p className="liferay-ai-hub-form-token-price mb-0">
										{token.price.priceFormatted}
									</p>
								</div>
							),
							value: token,
						}))}
						leftRadio
						onSelect={handleSelectToken}
						showImage
					/>
				) : (
					<p className="font-weight-bold my-5">No tokens available</p>
				)}

				<span className="mr-1 secondary-text">
					Need help calculating tokens usage?
				</span>

				<ClayLink
					className="font-weight-bold"
					href="http://help.liferay.com/"
					rel="noopener noreferrer"
					target="_blank"
				>
					Contact Support
				</ClayLink>
			</div>

			<ClayButton
				className="mt-6 w-100"
				disabled={!selectedSkuId}
				onClick={onSubmit}
			>
				<div className="align-items-center d-flex justify-content-center">
					<span>{i18n.translate('continue')}</span>
				</div>
			</ClayButton>
		</ProductPurchase.Shell>
	);
};

export default AIHubTokenSelection;
