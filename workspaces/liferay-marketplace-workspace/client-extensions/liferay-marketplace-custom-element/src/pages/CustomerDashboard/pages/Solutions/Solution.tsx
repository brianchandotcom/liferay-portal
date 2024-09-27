/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import ClayLabel from '@clayui/label';
import {differenceInDays, format} from 'date-fns';
import {useOutletContext, useParams} from 'react-router-dom';
import useSWR from 'swr';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import Loading from '../../../../components/Loading';
import QATable from '../../../../components/QATable';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {
	ORDER_CUSTOM_FIELDS,
	ORDER_TYPES,
	ORDER_WORKFLOW_STATUS_CODE,
} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import analyticsOAuth2 from '../../../../services/oauth/Analytics';
import {removeHTMLTags} from '../../../../utils/string';
import {formatDate} from '../../../PublisherDashboard/PublisherDashboardPageUtil';
import TrialAlert from '../../components/Solution/TrialAlert';

const NEXT_TO_EXPIRE_LEFT_DAYS = 2;

const getTrialDetails = (placedOrder: PlacedOrder) => {
	const orderStatusCode = placedOrder.orderStatusInfo
		?.code as ORDER_WORKFLOW_STATUS_CODE;

	const customFields = placedOrder.customFields;

	const isTrialCompleted =
		orderStatusCode === ORDER_WORKFLOW_STATUS_CODE.COMPLETED;

	const nextToExpire = customFields[ORDER_CUSTOM_FIELDS.END_DATE]
		? !isTrialCompleted &&
			differenceInDays(
				new Date(customFields[ORDER_CUSTOM_FIELDS.END_DATE]),
				new Date()
			) <= NEXT_TO_EXPIRE_LEFT_DAYS
		: false;

	const virtualHost = customFields[ORDER_CUSTOM_FIELDS.VIRTUAL_HOST] || '';

	return [
		{
			title: i18n.translate('license-type'),
			value: 'Trial',
		},
		{
			title: i18n.translate('trial-start-date'),
			value: customFields[ORDER_CUSTOM_FIELDS.START_DATE]
				? formatDate(customFields[ORDER_CUSTOM_FIELDS.START_DATE])
				: '-',
		},
		{
			title: i18n.translate('trial-end-date'),
			value: customFields[ORDER_CUSTOM_FIELDS.END_DATE] ? (
				<span>
					{formatDate(customFields[ORDER_CUSTOM_FIELDS.END_DATE])}

					{nextToExpire && (
						<ClayLabel
							className="ml-2"
							displayType={'primary' as DisplayType}
						>
							Expires soon
						</ClayLabel>
					)}

					{!nextToExpire && isTrialCompleted && (
						<ClayLabel className="ml-2" displayType="danger">
							{i18n.translate('expired')}
						</ClayLabel>
					)}
				</span>
			) : (
				'-'
			),
		},
		{
			title: i18n.translate('trial-url'),
			value: (
				<a
					href={
						(virtualHost as string).startsWith('https')
							? virtualHost
							: `https://${virtualHost}`
					}
					rel="noopener noreferrer"
					target="_blank"
				>
					{virtualHost}
				</a>
			),
			visible:
				orderStatusCode === ORDER_WORKFLOW_STATUS_CODE.IN_PROGRESS &&
				!!virtualHost,
		},
	];
};

type AnalyticsWorkspaceDetailsProps = {
	analyticsGroupId: string;
};

const AnalyticsWorkspaceDetails: React.FC<AnalyticsWorkspaceDetailsProps> = ({
	analyticsGroupId,
}) => {
	const {
		properties: {analyticsCloudURL},
	} = useMarketplaceContext();
	const {data = [], isLoading} = useSWR(
		`/analytics/project/${analyticsGroupId}/`,
		() =>
			Promise.all([
				analyticsOAuth2.getProject(analyticsGroupId),
				analyticsOAuth2.getProjectEmailAddressDomains(analyticsGroupId),
			])
	);

	const [project, emailAddressDomains = []] = data ?? [];

	return (
		<DetailedCard
			cardIconAltText="Summary Icon"
			cardTitle={i18n.translate('workspace-info')}
			clayIcon="liferay-ac"
		>
			{isLoading ? (
				<Loading
					className="mt-7"
					displayType="secondary"
					shape="circle"
					size="md"
				/>
			) : (
				<QATable
					items={[
						{
							title: i18n.translate('workspace-friendly-url'),
							value: project?.friendlyURL ? (
								<a
									href={`${analyticsCloudURL}/workspace${project?.friendlyURL}`}
									target="blank"
								>
									{project?.friendlyURL}
								</a>
							) : null,
						},
						{
							title: i18n.translate('workspace-name'),
							value: project?.name,
						},
						{
							title: i18n.translate('workspace-owner-email'),
							value: project?.ownerEmailAddress,
						},
						{
							title: i18n.translate('data-center-location'),
							value: project?.serverLocation,
						},
						{
							title: i18n.translate('timezone'),
							value: project?.timeZone.country,
						},

						{
							title: i18n.translate('incident-report-contacts'),
							value: project?.incidentReportEmailAddresses.map(
								(emailAddress) => (
									<div key={emailAddress}>{emailAddress}</div>
								)
							),
						},
						{
							title: i18n.translate('allowed-email-domains'),
							value: emailAddressDomains.map((emailAddress) => (
								<div key={emailAddress}>{emailAddress}</div>
							)),
						},
						{
							title: i18n.translate('subscription-type'),
							value: project?.faroSubscription.name,
						},
					]}
				/>
			)}
		</DetailedCard>
	);
};

const Solution = () => {
	const {orderId} = useParams();

	const {placedOrder, product} = useOutletContext<{
		placedOrder: PlacedOrder;
		product: DeliveryProduct;
	}>();

	const orderStatusCode = placedOrder.orderStatusInfo
		?.code as ORDER_WORKFLOW_STATUS_CODE;

	const isAddOn =
		placedOrder.orderTypeExternalReferenceCode === ORDER_TYPES.ADDONS;

	const limitedTrial = [
		ORDER_TYPES.SOLUTIONS7,
		ORDER_TYPES.SOLUTIONS30,
	].includes(placedOrder.orderTypeExternalReferenceCode as ORDER_TYPES);

	const analyticsGroupId =
		placedOrder.customFields[ORDER_CUSTOM_FIELDS.ANALYTICS_GROUP_ID];

	const getOrderDetails = () => {
		if (
			[ORDER_TYPES.SOLUTIONS7, ORDER_TYPES.SOLUTIONS30].includes(
				placedOrder.orderTypeExternalReferenceCode as ORDER_TYPES
			)
		) {
			return getTrialDetails(placedOrder);
		}

		return [];
	};

	return (
		<div className="mt-6">
			{limitedTrial && <TrialAlert orderStatusCode={orderStatusCode} />}

			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle={i18n.translate('details')}
					clayIcon="shopping-cart"
				>
					<QATable
						items={[
							{
								title: i18n.translate('account-name'),
								value: placedOrder.account,
							},
							{
								title: i18n.translate('purchased-by'),
								value: placedOrder.author,
							},
							{
								title: i18n.translate('order-id'),
								value: orderId,
							},
							{
								title: i18n.translate('order-date'),
								value: format(
									new Date(placedOrder.createDate),
									'dd MMM, yyyy'
								),
							},
							...getOrderDetails(),
						]}
					/>
				</DetailedCard>

				{limitedTrial && (
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
				)}

				{isAddOn && analyticsGroupId && (
					<AnalyticsWorkspaceDetails
						analyticsGroupId={analyticsGroupId}
					/>
				)}
			</div>
		</div>
	);
};

export default Solution;
