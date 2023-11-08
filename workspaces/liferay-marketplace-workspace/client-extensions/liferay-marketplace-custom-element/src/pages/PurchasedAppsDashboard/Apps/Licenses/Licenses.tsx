/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format, isBefore} from 'date-fns';
import {useCallback, useMemo, useState} from 'react';
import {useOutletContext, useParams} from 'react-router-dom';
import useSWR from 'swr';

import solutionsIcon from '../../../../assets/icons/bookmarks_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import StatusCell from '../../../../components/Table/StatusCell';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';

import './Licenses.scss';

import {useModal} from '@clayui/modal';
import classNames from 'classnames';

import Modal from '../../../../components/Modal';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderType} from '../../../../enums/OrderType';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import {getThumbnailByProductAttachment} from '../../../../utils/util';
import AccountEmailInfo from '../../../CreateLicense/AccountInfo';
import useProvisioningKoroneikiOAuth2 from '../../../GetAppPage/hooks/useProvisioningKoroneikiOAuth2';
import OrderDetailsHeader from '../components/OrderDetailsHeader';
import LicenceKeyModalContent from './LicenseModalContent';

export type LicenceKeyProps = {
	active: boolean;
	complimentary: boolean;
	createDate: string;
	description: string;
	expirationDate: string;
	hostName: string;
	id: number;
	ipAddresses: string;
	key: string;
	keyType: string;
	licenseType: string;
	licenseTypeAsString: string;
	macAddresses: string;
	modifiedDate: string;
	modifiedUserName: string;
	modifiedUserUuid: string;
	orderId: string;
	owner: string;
	productId: string;
	productName: string;
	productVersion: string;
	startDate: string;
	userName: string;
	userUuid: string;
};

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

const PAGE_SIZES = [
	{label: 5},
	{label: 10},
	{label: 20},
	{label: 30},
	{label: 50},
];

const Licenses = () => {
	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(5);
	const {orderId} = useParams();
	const outletContext = useOutletContext<OutletContext['data']>();
	const [visible, setVisible] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});
	const [modalData, setModalData] = useState<LicenceKeyProps>();
	const {myUserAccount} = useMarketplaceContext();

	const placedOrder = outletContext?.placedOrder;
	const product = outletContext?.product;

	const provisioningKoroneikiOAuth2 = useProvisioningKoroneikiOAuth2();

	const handleDownloadLicense = useCallback(
		(id: number) => {
			provisioningKoroneikiOAuth2.downloadLicenseKey(id);
		},
		[provisioningKoroneikiOAuth2]
	);

	const buttonsInfo = useMemo(
		() => ({
			cancelButton: {
				className: 'ml-4',
				displayType: 'unstyled',
				onClick: onClose,
				show: true,
			},
			customizedButton: {
				className: 'text-danger border-danger',
				displayType: 'secondary',
				show: true,
				text: i18n.translate('deactivate'),
			},
			nextButton: {
				appendIcon: 'download',
				className: 'ml-4 mr-1',
				disabled: false,
				displayType: 'primary',
				onClick: () => handleDownloadLicense(modalData?.id as number),
				show: true,
				text: i18n.translate('download-key'),
			},
		}),
		[modalData, handleDownloadLicense, onClose]
	);

	const keyType =
		placedOrder?.orderTypeExternalReferenceCode === OrderType.DXP
			? 'On-Premise'
			: 'Cloud';

	const {data: licenseKeysResponse, isLoading} = useSWR(
		`/order-license-keys/${orderId}-${page}-${pageSize}`,
		async () => {
			try {
				return provisioningKoroneikiOAuth2.getOrderLicenseKeys(
					orderId as string,
					new URLSearchParams({
						page: page.toString(),
						pageSize: pageSize.toString(),
					})
				);
			} catch (error) {
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

	const LicenseDetailsModalHeader = () => (
		<div className="d-flex flex-row justify-content-between">
			<div className="flex-row mb-1">
				<h6 className="font-weight-bold text-primary text-uppercase">
					Activation Key details
				</h6>

				<OrderDetailsHeader
					className="d-flex flex-row justify-content-between mt-3"
					hasOrderDescription={modalData?.description}
					image={getThumbnailByProductAttachment(
						product?.attachments
					)}
					name={product?.name?.en_US}
					version={modalData?.productVersion}
				/>
			</div>

			<AccountEmailInfo userAccount={myUserAccount} />
		</div>
	);

	return (
		<div className="licenses mb-9 mt-4">
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
									title={keyType}
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
					onClickRow={(row) => {
						row.keyType = keyType;

						setVisible(true);
						setModalData(row);
					}}
					paginationProps={{
						active: page,
						activeDelta: pageSize,
						deltas: PAGE_SIZES,
						onActiveChange: (page: number) => setPage(page),
						onDeltaChange: (pageSize: number) =>
							setPageSize(pageSize),
						totalItems: licenseKeysResponse?.totalCount || 0,
					}}
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

			{visible && (
				<Modal buttonsInfo={buttonsInfo} observer={observer} size="lg">
					<LicenceKeyModalContent
						Header={() => <LicenseDetailsModalHeader />}
						modalData={modalData as LicenceKeyProps}
					/>
				</Modal>
			)}
		</div>
	);
};

export default Licenses;
