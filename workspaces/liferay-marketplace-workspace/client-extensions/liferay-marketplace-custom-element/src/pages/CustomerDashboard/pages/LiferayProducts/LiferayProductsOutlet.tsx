/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

import {NavbarProps} from '../../../../components/Navbar';
import {OrderTypes} from '../../../../enums/Order';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import {getSiteURL} from '../../../../utils/site';
import {BaseOutlet} from '../Apps/App/AppOutlet';

type ProductAndOrderPayload = NonNullable<
	ReturnType<typeof useGetProductByOrderId>['data']
>;

const getTabs = (data: ProductAndOrderPayload): NavbarProps['routes'] => {
	const {orderTypeExternalReferenceCode} = data?.placedOrder ?? {};

	if (orderTypeExternalReferenceCode === OrderTypes.AI_HUB) {
		return [];
	}

	const isCMP = orderTypeExternalReferenceCode === OrderTypes.CMP;
	const isDXP = orderTypeExternalReferenceCode === OrderTypes.DXP;

	return [
		{
			name: i18n.translate('activation-keys'),
			path: '',
			visible: isCMP || isDXP,
		},
		{
			name: i18n.translate('bundles'),
			path: 'bundles',
			visible: isDXP,
		},
	];
};

const LiferayProductsOutlet = () => (
	<BaseOutlet
		actionButtons={(props) => {
			const appBeta =
				props?.marketplaceDeliveryProduct?.specificationValues
					?.APP_BETA;

			if (
				[OrderTypes.AI_HUB, OrderTypes.CMP, OrderTypes.DXP].includes(
					props?.placedOrder
						?.orderTypeExternalReferenceCode as OrderTypes
				)
			) {
				return (
					<div className="mt-6">
						{appBeta && (
							<ClayButton
								className="mr-2"
								displayType="secondary"
								onClick={() => {
									Liferay.Util.navigate(
										`${getSiteURL()}/product-feedback?orderId=${String(props?.placedOrder?.id)}`
									);
								}}
								outline
								size="sm"
							>
								{i18n.translate('share-beta-feedback')}
							</ClayButton>
						)}

						{![OrderTypes.AI_HUB].includes(
							props?.placedOrder
								?.orderTypeExternalReferenceCode as OrderTypes
						) && (
							<ClayButton
								displayType="primary"
								onClick={() => {
									Liferay.Util.navigate(
										`${getSiteURL()}/product-purchase?productId=${props?.product?.productId}#/activation-key-form`
									);
								}}
								outline
								size={appBeta ? 'sm' : 'regular'}
							>
								{i18n.translate('new-activation-key')}
							</ClayButton>
						)}
					</div>
				);
			}
		}}
		backTitle={i18n.translate('back-to-my-products')}
		backURL="../../products"
		description={(props) => {
			return props?.product?.shortDescription;
		}}
		routes={getTabs}
		showActions={false}
	/>
);

export default LiferayProductsOutlet;
