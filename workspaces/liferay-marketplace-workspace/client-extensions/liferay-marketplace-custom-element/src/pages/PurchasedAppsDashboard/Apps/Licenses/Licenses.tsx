/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format, isBefore} from 'date-fns';
import {useOutletContext, useParams} from 'react-router-dom';
import useSWR from 'swr';

import solutionsIcon from '../../../../assets/icons/bookmarks_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import StatusCell from '../../../../components/Table/StatusCell';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';

import './Licenses.scss';

import classNames from 'classnames';

import {OrderType} from '../../../../enums/OrderType';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import useProvisioningKoroneikiOAuth2 from '../../../GetAppPage/hooks/useProvisioningKoroneikiOAuth2';

type TitleSubtitleHeaderProps = {
	bold?: boolean;
	subtitle: string;
	title: string;
};

const TitleSubtitleHeader: React.FC<TitleSubtitleHeaderProps> = ({
	bold = true,
	subtitle,
	title,
}) => (
	<>
		<p
			className={classNames('description m-1', {
				'description-title font-weight-bold': bold,
			})}
		>
			{title}
		</p>

		<p className="description m-1">{subtitle}</p>
	</>
);

type OutletContext = ReturnType<typeof useGetProductByOrderId>;

const Licenses = () => {
	const {orderId} = useParams();
	const outletContext = useOutletContext<OutletContext['data']>();

	const placedOrder = outletContext?.placedOrder;

	const provisioningKoroneikiOAuth2 = useProvisioningKoroneikiOAuth2();

	const {data: licenseKeysResponse, isLoading} = useSWR(
		`/order-license-key3s/${orderId}`,
		async () => {
			try {
				return provisioningKoroneikiOAuth2.getOrderLicenseKeys(
					orderId as string
				);
			}
			catch (error) {
				return {
					items: [],
					totalCount: 0,
				};
			}
		}
	);

	if (isLoading) {
		return <div>Loading...</div>;
	}

	const rows = licenseKeysResponse?.items ?? [];

	return (
		<div className="licenses mt-4">
			{rows.length ? (
				<Table
					columns={[
						{
							bodyClass: 'border-0 cursor-pointer',
							expanded: true,
							key: 'description',
							noWrap: true,
							render: (description, {licenseType}) => (
								<TitleSubtitleHeader
									subtitle={description}
									title={licenseType}
								/>
							),
							title: (
								<TitleSubtitleHeader
									subtitle="Description"
									title="Environment"
								/>
							),
						},
						{
							bodyClass: 'border-0 cursor-pointer',
							key: 'hostName',
							render: (hostName) => (
								<TitleSubtitleHeader
									subtitle={hostName || '-'}
									title={
										placedOrder?.orderTypeExternalReferenceCode ===
										OrderType.DXP
											? 'On-Premise'
											: 'Cloud'
									}
								/>
							),
							title: (
								<TitleSubtitleHeader
									subtitle="Host Name"
									title="Key Type"
								/>
							),
						},
						{
							bodyClass: 'border-0 cursor-pointer',
							key: 'startDate',
							render: (startDate, {expirationDate}) => (
								<TitleSubtitleHeader
									bold={false}
									subtitle={
										expirationDate
											? format(
													new Date(expirationDate),
													'MMM dd, yyyy'
											  )
											: 'DNE'
									}
									title={format(
										new Date(startDate),
										'MMM dd, yyyy'
									)}
								/>
							),

							title: (
								<TitleSubtitleHeader
									subtitle="Exp. Date"
									title="Start Date -"
								/>
							),
						},
						{
							bodyClass: 'border-0 cursor-pointer',
							key: 'status',
							render: (_, {active, expirationDate}) => {
								const isActive =
									active &&
									isBefore(
										new Date(),
										new Date(expirationDate)
									)
										? 'active'
										: 'inactive';

								return (
									<StatusCell
										icon="circle"
										iconClassName={
											isActive ? 'active' : 'expired'
										}
									>
										{isActive ? 'Activated' : 'Expired'}
									</StatusCell>
								);
							},
							title: 'Status',
						},
					]}
					hasKebabButton
					hasPagination
					rows={rows}
				/>
			) : (
				<DashboardEmptyTable
					button
					buttonName={i18n.translate('create-license-key')}
					description1={i18n.translate(
						'create-new-licenses-and-they-will-show-up-here'
					)}
					icon={solutionsIcon}
					title={i18n.translate('no-licenses-yet')}
				/>
			)}
		</div>
	);
};

export default Licenses;
