/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {format, isBefore} from 'date-fns';
import {useMemo, useState} from 'react';
import {Link, useOutletContext, useParams} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardEmptyTable} from '../../../../../../components/DashboardTable/DashboardEmptyTable';
import Modal from '../../../../../../components/Modal';
import {Statuses as OrderStatuses} from '../../../../../../components/OrderStatus';
import StatusCell from '../../../../../../components/Table/StatusCell';
import Table from '../../../../../../components/Table/Table';
import {useMarketplaceContext} from '../../../../../../context/MarketplaceContext';
import {OrderType} from '../../../../../../enums/OrderType';
import useGetProductByOrderId from '../../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../../i18n';
import provisioningOAuth2 from '../../../../../../services/oauth/Provisioning';
import {LicenseKey} from '../../../../../../services/oauth/types';
import DeactivateKeysModal from '../../../../components/DeactivateKeysModal/DeactivateKeysModal';
import LicenseDetailsModalHeader from '../../../../components/LicenseDetailsModalHeader';
import LicenceKeyModalContent from '../../../../components/LicenseModalContent';
import TableActions from '../../../../components/TableActions';
import TitleSubtitleHeader from '../../../../components/TitleSubtitleHeader';
import useLicenseActions from './useLicensesActions';

import './Licenses.scss';

type OutletContext = ReturnType<typeof useGetProductByOrderId>;

const PAGE_SIZES = [
	{label: 5},
	{label: 10},
	{label: 20},
	{label: 30},
	{label: 50},
];

const isLicenseExpired = (expirationDate: string) =>
	!isBefore(new Date(), new Date(expirationDate));

const Licenses = () => {
	const [modalData, setModalData] = useState<LicenseKey>();
	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(5);
	const {myUserAccount} = useMarketplaceContext();
	const {orderId} = useParams();
	const deactivateLicenseModal = useModal();
	const licenseKeyModal = useModal();
	const outletContext = useOutletContext<OutletContext['data']>();

	const placedOrder = outletContext?.placedOrder;
	const product = outletContext?.product;

	const keyType =
		placedOrder?.orderTypeExternalReferenceCode === OrderType.DXP
			? 'On-Premise'
			: 'Cloud';

	const {
		data: licenseKeysResponse,
		isLoading,
		mutate,
	} = useSWR(
		`/order-license-keys/${orderId}/${page}/${pageSize}`,
		async () => {
			try {
				return provisioningOAuth2.getOrderLicenseKeys(
					orderId as string,
					new URLSearchParams({
						page: page.toString(),
						pageSize: pageSize.toString(),
					})
				);
			}
			catch {
				return {
					items: [],
					totalCount: 0,
				};
			}
		}
	);

	const rows = licenseKeysResponse?.items ?? [];

	const orderStatusIsNotCompleted =
		placedOrder?.orderStatusInfo?.label !== OrderStatuses.COMPLETED;

	const {onDeativateLicenseKey, onDownload, onViewLicenseKey} =
		useLicenseActions({
			deactivateLicenseModal,
			keyType,
			licenseKeyModal,
			mutate,
			product,
			setModal: setModalData,
		});

	const buttonsInfo = useMemo(
		() => ({
			first: (
				<ClayButton
					className="ml-4"
					displayType="unstyled"
					onClick={licenseKeyModal.onClose}
				>
					{i18n.translate('cancel')}
				</ClayButton>
			),
			last: (
				<>
					<ClayButton
						className="border-danger text-danger"
						displayType="secondary"
						onClick={() => {
							licenseKeyModal.onClose();

							deactivateLicenseModal.onOpenChange(true);
						}}
					>
						{i18n.translate('deactivate')}
					</ClayButton>

					<ClayButton
						className="ml-4 mr-1"
						disabled={isLicenseExpired(
							modalData?.expirationDate as string
						)}
						displayType="primary"
						onClick={() => {
							onDownload(modalData as LicenseKey);
						}}
						title={
							isLicenseExpired(
								modalData?.expirationDate as string
							)
								? i18n.translate(
										'this-key-is-expired-and-cannot-be-downloaded'
									)
								: ''
						}
					>
						<ClayIcon symbol="download" />
						{i18n.translate('download-key')}
					</ClayButton>
				</>
			),
		}),
		[licenseKeyModal, modalData, deactivateLicenseModal, onDownload]
	);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<div className="licenses mb-9 mt-4">
			{rows.length ? (
				<Table
					Actions={({row}) => (
						<TableActions
							isDisabled={isLicenseExpired(row.expirationDate)}
							onDeactivate={() => {
								setModalData(row);

								deactivateLicenseModal.onOpenChange(true);
							}}
							onDownload={() => onDownload(row)}
							onView={() => onViewLicenseKey(row)}
							tooltip={
								isLicenseExpired(row.expirationDate)
									? i18n.translate(
											'this-key-is-expired-and-cannot-be-downloaded'
										)
									: ''
							}
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
					kebabClassName="border-0"
					onClickRow={onViewLicenseKey}
					paginationProps={{
						activeDelta: pageSize,
						activePage: page,
						deltas: PAGE_SIZES,
						onDeltaChange: (pageSize: number) =>
							setPageSize(pageSize),
						onPageChange: (page: number) => setPage(page),
						totalItems: licenseKeysResponse?.totalCount || 0,
					}}
					rows={rows}
				/>
			) : (
				<DashboardEmptyTable
					description1={i18n.translate(
						'create-new-licenses-and-they-will-show-up-here'
					)}
					icon="bookmarks"
					title={i18n.translate('no-licenses-yet')}
				>
					<ClayTooltipProvider>
						<Link
							className={classNames('btn btn-primary mt-4', {
								disabled: orderStatusIsNotCompleted,
							})}
							data-tooltip-align="bottom"
							title={
								orderStatusIsNotCompleted
									? i18n.translate(
											'the-order-must-be-completed-before-licensing-this-app.'
										)
									: undefined
							}
							to={`/order/${orderId}/create-license`}
						>
							{i18n.translate('create-license-key')}
						</Link>
					</ClayTooltipProvider>
				</DashboardEmptyTable>
			)}

			{licenseKeyModal.open && (
				<Modal
					first={buttonsInfo.first}
					last={buttonsInfo.last}
					observer={licenseKeyModal.observer}
					size="lg"
					visible={true}
				>
					<LicenceKeyModalContent
						Header={
							<LicenseDetailsModalHeader
								modalData={modalData}
								myUserAccount={myUserAccount}
								product={product as DeliveryProduct}
							/>
						}
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
