/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayLabel from '@clayui/label';
import {differenceInDays} from 'date-fns';
import {useOutletContext, useParams} from 'react-router-dom';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import QATable from '../../../../components/QATable';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {removeHTMLTags} from '../../../../utils/string';
import {formatDate} from '../../../PublisherDashboard/PublisherDashboardPageUtil';

export enum TRIAL_CUSTOM_FIELDS {
	START_DATE = 'trial-start-date',
	END_DATE = 'trial-end-date',
	VIRTUAL_HOST = 'trial-virtualhost',
}

const statusAlert = {
	[ORDER_WORKFLOW_STATUS_CODE.CANCELLED]: {
		displayType: 'danger',
		text: 'Your order has been cancelled. Please contact support if you have any questions.',
	},
	[ORDER_WORKFLOW_STATUS_CODE.ON_HOLD]: {
		displayType: 'secondary',
		text: 'Your order is currently on hold. Please check your email for further instructions.',
	},
	[ORDER_WORKFLOW_STATUS_CODE.PROCESSING]: {
		displayType: 'info',
		text: 'Your order is being processed. We will notify you once it is ready for the next step.',
	},
	[ORDER_WORKFLOW_STATUS_CODE.PENDING]: {
		displayType: 'warning',
		text: 'Your order is pending. Please wait a few minutes or hours for the processing to complete.',
	},
};

const NEXT_TO_EXPIRE_LEFT_DAYS = 2;

const Solution = () => {
	const {orderId} = useParams();
	const {placedOrder, product} = useOutletContext<{
		placedOrder: PlacedOrder;
		product: DeliveryProduct;
	}>();

	const customFields = placedOrder.customFields;

	const nextToExpire = customFields[TRIAL_CUSTOM_FIELDS.END_DATE]
		? differenceInDays(
				new Date(customFields[TRIAL_CUSTOM_FIELDS.END_DATE]),
				new Date()
			) <= NEXT_TO_EXPIRE_LEFT_DAYS
		: false;
	const virtualHost = customFields[TRIAL_CUSTOM_FIELDS.VIRTUAL_HOST] || '';

	const orderStatusCode = placedOrder.orderStatusInfo
		?.code as ORDER_WORKFLOW_STATUS_CODE;

	const alert = (statusAlert as any)[orderStatusCode];

	return (
		<div className="mt-6">
			{alert && (
				<ClayAlert displayType={alert.displayType}>
					{alert.text}
				</ClayAlert>
			)}

			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle={i18n.translate('trial-details')}
					clayIcon="shopping-cart"
				>
					<QATable
						items={[
							{
								title: i18n.translate('account-name'),
								value: placedOrder.account,
							},
							{
								title: i18n.translate('order-id'),
								value: orderId,
							},
							{
								title: i18n.translate('license-type'),
								value: 'Trial',
							},
							{
								title: i18n.translate('created-by'),
								value: placedOrder.author,
							},
							{
								title: i18n.translate('trial-start-date'),
								value: customFields[
									TRIAL_CUSTOM_FIELDS.START_DATE
								]
									? formatDate(
											customFields[
												TRIAL_CUSTOM_FIELDS.START_DATE
											]
										)
									: '-',
							},
							{
								title: i18n.translate('trial-end-date'),
								value: customFields[
									TRIAL_CUSTOM_FIELDS.END_DATE
								] ? (
									<span>
										{formatDate(
											customFields[
												TRIAL_CUSTOM_FIELDS.END_DATE
											]
										)}

										{nextToExpire && (
											<ClayLabel
												className="ml-2"
												displayType={
													'primary' as DisplayType
												}
											>
												Expires soon
											</ClayLabel>
										)}
									</span>
								) : (
									'-'
								),
							},
							{
								title: i18n.translate('trial-url'),
								value: virtualHost ? (
									<a
										href={
											(virtualHost as string).startsWith(
												'https'
											)
												? virtualHost
												: `https://${virtualHost}`
										}
										rel="noopener noreferrer"
										target="_blank"
									>
										{virtualHost}
									</a>
								) : (
									'-'
								),
							},
						]}
					/>
				</DetailedCard>

				<DetailedCard
					cardIconAltText="Summary Icon"
					cardTitle={i18n.translate('solution-summary')}
					clayIcon="shopping-cart"
				>
					<QATable
						items={[
							{
								title: i18n.translate('publisher-name'),
								value: product.catalogName,
							},
							{
								title: i18n.translate('published-at'),
								value: formatDate(product.createDate),
							},
							{
								title: i18n.translate('description'),
								value:
									product.shortDescription ||
									removeHTMLTags(product.description),
							},
						]}
					/>
				</DetailedCard>
			</div>
		</div>
	);
};

export default Solution;
