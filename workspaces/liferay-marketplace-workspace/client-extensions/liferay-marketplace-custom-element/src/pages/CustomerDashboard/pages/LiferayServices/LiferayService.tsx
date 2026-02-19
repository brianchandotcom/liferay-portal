/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format} from 'date-fns';
import {useEffect} from 'react';
import {useParams} from 'react-router-dom';

import {breadcrumbStore} from '../../../../components/Breadcrumb/BreadcrumbStore';
import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import QATable from '../../../../components/QATable';
import {
	OrderCustomFields,
	OrderWorkflowStatusCode,
} from '../../../../enums/Order';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../i18n';
import LiferayServicesAlerts from './LiferayServicesAlerts';
import {PageRenderer} from '../../../../components/Page';

const LiferayService = () => {
	const {orderId} = useParams();
	const {data, isLoading} = useGetProductByOrderId(orderId as string);

	const placedOrder = data?.placedOrder;
	const product = data?.product;

	useEffect(() => {
		breadcrumbStore.send({
			replacements: {[orderId as string]: product?.name || ''},
			type: 'setReplacements',
		});
	}, [orderId, product?.name]);

	const orderStatusCode = placedOrder?.orderStatusInfo
		?.code as OrderWorkflowStatusCode;

	const isCompletedOrder =
		orderStatusCode === OrderWorkflowStatusCode.COMPLETED;

	const orderMetadata = placedOrder
		? JSON.parse(placedOrder.customFields[OrderCustomFields.ORDER_METADATA])
		: {};

	const allowedEmailDomains =
		orderMetadata?.provisioning?.allowedEmailDomains || [];

	const incidentReportEmailAddresses =
		orderMetadata?.provisioning?.incidentReportEmailAddresses || [];

	return (
		<PageRenderer className="mt-6" isLoading={isLoading}>
			{!isCompletedOrder && (
				<LiferayServicesAlerts orderStatusCode={orderStatusCode} />
			)}

			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle={i18n.translate('details')}
					clayIcon="order-form-tag"
				>
					<QATable
						items={[
							{
								title: i18n.translate('order-id'),
								value: orderId,
							},
							{
								title: i18n.translate('order-date'),
								value: format(
									new Date(placedOrder?.createDate || ''),
									'dd MMM, yyyy'
								),
							},
							{
								title: i18n.translate('account-name'),
								value: placedOrder?.account,
							},
							{
								title: i18n.translate('customer-project'),
								value: '',
							},
							{
								title: i18n.translate('purchased-by'),
								value: placedOrder?.author,
							},
							{
								title: i18n.translate('purchase-number'),
								value: '',
							},
							{
								title: i18n.translate('subscription-type'),
								value: placedOrder?.placedOrderItems[0].sku,
							},
						]}
					/>
				</DetailedCard>

				<DetailedCard
					cardIconAltText="Summary Icon"
					cardTitle={i18n.translate('workspace-info')}
					clayIcon="polls"
				>
					<QATable
						items={[
							{
								title: i18n.translate('workspace-name'),
								value: orderMetadata?.provisioning
									?.corpProjectName,
							},
							{
								title: i18n.translate('workspace-owner-email'),
								value: orderMetadata?.provisioning
									?.ownerEmailAddress,
							},
							{
								title: i18n.translate('data-center-location'),
								value: orderMetadata?.provisioning
									?.serverLocation,
							},
							{
								title: i18n.translate('timezone'),
								value: orderMetadata?.provisioning
									?.serverLocation,
							},
							{
								title: i18n.translate('workspace-friendly-url'),
								value: orderMetadata?.provisioning?.friendlyURL,
							},
							{
								title: i18n.translate('allowed-email-domains'),
								value: allowedEmailDomains?.map(
									(emailAddress: string) => (
										<div key={emailAddress}>
											{emailAddress}
										</div>
									)
								),
							},
							{
								title: i18n.translate(
									'incident-report-contacts'
								),
								value: incidentReportEmailAddresses?.map(
									(emailAddress: string) => (
										<div key={emailAddress}>
											{emailAddress}
										</div>
									)
								),
							},
						]}
					/>
				</DetailedCard>
			</div>
		</PageRenderer>
	);
};

export default LiferayService;
