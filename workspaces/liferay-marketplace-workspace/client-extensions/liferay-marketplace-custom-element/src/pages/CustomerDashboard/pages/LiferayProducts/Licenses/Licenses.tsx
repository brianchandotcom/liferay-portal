/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {differenceInDays, format, isBefore, subMonths} from 'date-fns';
import {Fragment, useEffect} from 'react';
import {useLocation, useOutletContext, useParams} from 'react-router-dom';
import useSWR, {KeyedMutator} from 'swr';

import {breadcrumbStore} from '../../../../../components/Breadcrumb/BreadcrumbStore';
import EmptyState from '../../../../../components/EmptyState';
import StatusCell from '../../../../../components/Table/StatusCell';
import Table from '../../../../../components/Table/Table';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import provisioningOAuth2 from '../../../../../services/oauth/Provisioning';
import {LicenseKey} from '../../../../../services/oauth/types';
import TitleSubtitleHeader from '../../../components/TitleSubtitleHeader';
import ActivationKeyAlert from './LicenseAlert';
import LicenseTitleHeader from './LicenseTitleHeader';

import './Licenses.scss';

type OutletContext = ReturnType<typeof useGetProductByOrderId>;

const isNewActivationKey = (licenseKey: LicenseKey) => {
	if (!licenseKey?.createDate) {
		return false;
	}

	const created = new Date(licenseKey.createDate);
	const today = new Date();

	return differenceInDays(today, created) <= 15;
};

const isRenewalAvailable = (licenseKey: LicenseKey) => {
	if (!licenseKey.active || !licenseKey.expirationDate) {
		return false;
	}

	return isBefore(
		subMonths(new Date(licenseKey.expirationDate), 3),
		new Date()
	);
};

const isLicenseExpired = (expirationDate: string) =>
	!isBefore(new Date(), new Date(expirationDate));

const ActivationKeysTable = ({
	licenseKeysResponse,
	mutate,
}: {
	mutate: KeyedMutator<APIResponse<LicenseKey>>;
	licenseKeysResponse: APIResponse<LicenseKey>;
}) => {
	if (licenseKeysResponse.totalCount === 0) {
		return <EmptyState title="No Activation Keys" />;
	}

	return (
		<Table
			Actions={({row}) => {
				const expired =
					!row.expirationDate || isLicenseExpired(row.expirationDate);

				const renewalAvailable = isRenewalAvailable(row);

				const Wrapper = renewalAvailable
					? ClayTooltipProvider
					: Fragment;

				return (
					<Wrapper>
						<div className="align-items-center d-flex license-actions">
							<ClayButton
								className="mr-3 renew-link"
								disabled={!renewalAvailable}
								displayType="unstyled"
								onClick={() => {
									provisioningOAuth2
										.licenseKeyTypeFreeRenew(row.id)
										.then(() =>
											mutate((data) => data, {
												revalidate: true,
											})
										);
								}}
								title={
									renewalAvailable
										? undefined
										: i18n.translate(
												'renewal-will-be-available-3-months-before-your-activation-key-expires'
											)
								}
							>
								{i18n.translate('renew')}
							</ClayButton>

							<ClayButton
								className="license-download-btn px-3 rounded"
								disabled={expired}
								displayType="secondary"
								onClick={() => {
									provisioningOAuth2.downloadLicenseKey(
										row.id
									);
								}}
							>
								{i18n.translate('download')}
							</ClayButton>
						</div>
					</Wrapper>
				);
			}}
			columns={[
				{
					bodyClass: 'border-0 cursor-pointer text-capitalize',
					expanded: true,
					key: 'licenseType',
					noWrap: true,
					render: (_, row) => (
						<LicenseTitleHeader
							isNewActivationKey={isNewActivationKey(row)}
							isToBeRenewed={isRenewalAvailable(row)}
							title={row.productName}
						/>
					),
					title: (
						<TitleSubtitleHeader
							title={i18n.translate('activation-key')}
						/>
					),
				},
				{
					bodyClass: 'border-0 cursor-pointer',
					key: 'domains',
					render: (domains: string) => (
						<ul className="list-unstyled">
							{domains.split(',').map((domain) => (
								<li
									className="font-weight-bold description-title mt-2"
									key={domain}
								>
									{domain}
								</li>
							))}
						</ul>
					),
					title: (
						<TitleSubtitleHeader title={i18n.translate('domain')} />
					),
				},
				{
					bodyClass: 'border-0 cursor-pointer',
					key: 'startDate',
					render: (startDate, {expirationDate}) => (
						<div className="date-cell">
							<p className="m-0">
								{format(new Date(startDate), 'MMM dd, yyyy')} -
							</p>

							<p className="m-0">
								{expirationDate
									? format(
											new Date(expirationDate),
											'MMM dd, yyyy'
										)
									: 'DNE'}
							</p>
						</div>
					),
					title: (
						<TitleSubtitleHeader
							title={
								<span>
									Start Date -<br />
									Exp. Date
								</span>
							}
						/>
					),
				},
				{
					bodyClass: 'border-0 cursor-pointer',
					key: 'status',
					render: (_, {active, expirationDate}) => {
						const isActive =
							active &&
							isBefore(new Date(), new Date(expirationDate));

						const label = isActive ? 'active' : 'expired';

						return (
							<StatusCell icon="circle" iconClassName={label}>
								{i18n.translate(label)}
							</StatusCell>
						);
					},
					title: (
						<TitleSubtitleHeader title={i18n.translate('status')} />
					),
				},
			]}
			hasHover
			hasKebabButton
			hasPagination
			kebabClassName="border-0"
			rows={licenseKeysResponse.items ?? []}
		/>
	);
};

export default function ActivationKeys() {
	const {orderId} = useParams();
	const location = useLocation();
	const outletContext = useOutletContext<OutletContext['data']>();
	const searchParams = new URLSearchParams(location.search);

	const product = outletContext?.product;

	const {
		data: licenseKeysResponse,
		isLoading,
		mutate,
	} = useSWR(`/order-free-dxp-license-keys/${orderId}`, () =>
		provisioningOAuth2.getOrderLicenseKeys(orderId as string)
	);

	useEffect(() => {
		breadcrumbStore.send({
			replacements: {[orderId as string]: product?.name || ''},
			type: 'setReplacements',
		});
	}, [orderId, product?.name]);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<div className="mt-5">
			{searchParams.has('next-steps') && (
				<ActivationKeyAlert
					className="license-alert"
					symbol="check-circle"
					title="Your free activation key has been generated!"
				>
					Thanks for choosing Liferay DXP! Download your activation
					key below and, if needed, the software bundle to get
					started.
				</ActivationKeyAlert>
			)}

			<div className="licenses mb-9">
				<ActivationKeysTable
					mutate={mutate}
					licenseKeysResponse={
						licenseKeysResponse as APIResponse<LicenseKey>
					}
				/>
			</div>
		</div>
	);
}
