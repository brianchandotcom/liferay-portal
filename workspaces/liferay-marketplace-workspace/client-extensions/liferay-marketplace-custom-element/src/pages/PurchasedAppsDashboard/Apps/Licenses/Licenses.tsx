/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format, isBefore} from 'date-fns';
import {useMemo, useState} from 'react';
import {useOutletContext, useParams} from 'react-router-dom';
import useSWR from 'swr';

import solutionsIcon from '../../../../assets/icons/bookmarks_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import StatusCell from '../../../../components/Table/StatusCell';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';

import './Licenses.scss';

import {useModal} from '@clayui/modal';

import DeactivateKeysModal from '../../../../components/DeactivateKeysModal/DeactivateKeysModal';
import Modal from '../../../../components/Modal';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderType} from '../../../../enums/OrderType';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import {LicenseKey} from '../../../../services/oauth/ProvisioningKoroneikiOAuth2';
import useProvisioningKoroneikiOAuth2 from '../../../GetAppPage/hooks/useProvisioningKoroneikiOAuth2';
import LicenseDetailsModalHeader from './components/LicenseDetailsModalHeader';
import LicenceKeyModalContent from './components/LicenseModalContent';
import TableActions from './components/TableActions';
import TitleSubtitleHeader from './components/TitleSubtitleHeader';
import useLicenseActions from './useLicensesActions';

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
	const deactivateLicenseModal = useModal();
	const licenseKeyModal = useModal();

	const [modalData, setModalData] = useState<LicenseKey>();
	const {myUserAccount} = useMarketplaceContext();

	const placedOrder = outletContext?.placedOrder;
	const product = outletContext?.product;

	const provisioningKoroneikiOAuth2 = useProvisioningKoroneikiOAuth2();

	const keyType =
		placedOrder?.orderTypeExternalReferenceCode === OrderType.DXP
			? 'On-Premise'
			: 'Cloud';

	const {data: licenseKeysResponse, isLoading, mutate} = useSWR(
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
			}
			catch (error) {
				return {
					items: [],
					totalCount: 0,
				};
			}
		}
	);

	const {
		onDeativateLicenseKey,
		onDownload,
		onViewLicenseKey,
	} = useLicenseActions({
		deactivateLicenseModal,
		keyType,
		licenseKeyModal,
		mutate,
		provisioningKoroneikiOAuth2,
		setModal: setModalData,
	});

	const buttonsInfo = useMemo(
		() => ({
			cancelButton: {
				className: 'ml-4',
				displayType: 'unstyled',
				onClick: licenseKeyModal.onClose,
				show: true,
			},
			customizedButton: {
				className: 'text-danger border-danger',
				displayType: 'secondary',
				onClick: () => {
					licenseKeyModal.onClose();

					deactivateLicenseModal.onOpenChange(true);
				},
				show: true,
				text: i18n.translate('deactivate'),
			},
			nextButton: {
				appendIcon: 'download',
				className: 'ml-4 mr-1',
				disabled: false,
				displayType: 'primary',
				onClick: () => onDownload(modalData as LicenseKey),
				show: true,
				text: i18n.translate('download-key'),
			},
		}),
		[licenseKeyModal, deactivateLicenseModal, onDownload, modalData]
	);

	if (isLoading) {
		return <div>Loading...</div>;
	}

	const rows = licenseKeysResponse?.items ?? [];

	return (
		<div className="licenses mb-9 mt-4">
			{rows.length ? (
				<Table
					Actions={({row}) => (
						<TableActions
							onDeactivate={() => {
								setModalData(row);

								deactivateLicenseModal.onOpenChange(true);
							}}
							onDownload={() => onDownload(row)}
							onView={() => onViewLicenseKey(row)}
						/>
					)}
					columns={[
						{
							bodyClass:
								'border-0 cursor-pointer text-capitalize',
							expanded: true,
							key: 'description',
							noWrap: true,
							render: (
								description,
								{licenseType}: {licenseType: string}
							) => (
								<TitleSubtitleHeader
									subtitle={description}
									title={licenseType.toLowerCase()}
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
								<div className="date-cell">
									<p className="m-0">
										{format(
											new Date(startDate),
											'MMM dd, yyyy'
										)}{' '}
										-
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
									isBefore(
										new Date(),
										new Date(expirationDate)
									);

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
							title: <TitleSubtitleHeader title="Status" />,
						},
					]}
					hasKebabButton
					hasPagination
					onClickRow={onViewLicenseKey}
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

			{licenseKeyModal.open && (
				<Modal
					buttonsInfo={buttonsInfo}
					observer={licenseKeyModal.observer}
					size="lg"
				>
					<LicenceKeyModalContent
						Header={() => (
							<LicenseDetailsModalHeader
								modalData={modalData}
								myUserAccount={myUserAccount}
								product={product as Product}
							/>
						)}
						modalData={modalData as LicenseKey}
					/>
				</Modal>
			)}

			{deactivateLicenseModal.open && (
				<DeactivateKeysModal
					{...deactivateLicenseModal}
					onConfirm={() =>
						onDeativateLicenseKey(modalData as LicenseKey)
					}
				/>
			)}
		</div>
	);
};

export default Licenses;
