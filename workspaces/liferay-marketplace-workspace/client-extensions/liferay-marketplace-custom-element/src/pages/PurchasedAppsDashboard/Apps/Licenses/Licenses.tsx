/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format, isBefore} from 'date-fns';
import {useOutletContext, useParams} from 'react-router-dom';
import useSWR from 'swr';
import {useMemo, useState} from 'react';

import solutionsIcon from '../../../../assets/icons/bookmarks_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import StatusCell from '../../../../components/Table/StatusCell';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';

import './Licenses.scss';

import classNames from 'classnames';
import LicenceKeyModalContent from './LicenseModalContent';
import Modal from '../../../../components/Modal';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';

import {OrderType} from '../../../../enums/OrderType';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import useProvisioningKoroneikiOAuth2 from '../../../GetAppPage/hooks/useProvisioningKoroneikiOAuth2';
import {useModal} from '@clayui/modal';
import AccountEmailInfo from '../../../CreateLicense/AccountInfo';

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
	const [visible, setVisible] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});
	const [modalData, setModalData] = useState<any>();
	const {myUserAccount} = useMarketplaceContext();

	const placedOrder = outletContext?.placedOrder;

	const provisioningKoroneikiOAuth2 = useProvisioningKoroneikiOAuth2();

	console.log('orderId', orderId);

	const {data: licenseKeysResponse, isLoading} = useSWR(
		`/order-license-key3s/${orderId}`,
		async () => {
			try {
				return provisioningKoroneikiOAuth2.getOrderLicenseKeys(
					orderId as string
				);
			} catch (error) {
				return {
					items: [
						{
							active: true,
							complimentary: false,
							createDate: '2023-11-03T15:50:54Z',
							description: 'Test Test - Liferay - Standard',
							expirationDate: '2123-11-03T15:50:52Z',
							hostName: 'localhost',
							id: 483336,
							ipAddresses: '',
							key: '68c060298e1984661dc257b3e517cdbb154aca19a1e10ae43c5e61c3e018d1b2b44c017ad6d2a5680f666d72d0e7cbd6835a6d4823f46e3fbf39241933f31891e746091fe2dda53c04b6c70ed27beee12f68e6620325c3e949cfb3171fefbfb95b65712d565c1551340998102d418ceccb35db8dbfb45f9041c4cae483d8717b',
							licenseType: 'production',
							macAddresses: '',
							modifiedDate: '2023-11-03T15:50:54Z',
							modifiedUserName: 'test@liferay.com',
							modifiedUserUuid: '20122',
							orderId: '46858',
							owner: 'test@liferay.com',
							productId: 'KOR-26515372',
							productName: 'Liferay Portal - Standard',
							productVersion: '1.0.0',
							startDate: '2023-11-03T15:50:52Z',
							userName: 'test@liferay.com',
							userUuid: '20122',
						},
					],
					totalCount: 1,
				};
			}
		}
	);

	if (isLoading) {
		return <div>Loading...</div>;
	}

	const rows = licenseKeysResponse?.items ?? [];

	const buttonsInfo = useMemo(
		() => ({
			cancelButton: {
				className: 'ml-4',
				displayType: 'unstyled',
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
				show: true,
				text: i18n.translate('download-key'),
			},
		}),
		[]
	);

	const modalHeaderData = {
		productName: 'Product Name',
		description: 'Description',
		title: 'Activation Key details',
	};

	const LicenseKeyDetailsHeader = () => (
		<div className="d-flex justify-content-between flex-row">
			<div className="flex-row mb-1">
				<h6 className="text-primary text-uppercase font-weight-bold">
					{modalHeaderData.title}
				</h6>

				<h2 className="text-neutral-10">
					{modalHeaderData.productName}
				</h2>

				<p>{modalHeaderData.description}</p>
			</div>

			<AccountEmailInfo userAccount={myUserAccount} />
		</div>
	);

	return (
		<div className="licenses mt-4">
			{rows.length ? (
				<Table
					onClickRow={(data) => {
						setVisible(true);
						setModalData(data);
					}}
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

			{visible && (
				<Modal
					buttonsInfo={buttonsInfo}
					observer={observer}
					onClose={onClose}
					size="lg"
				>
					{
						<LicenceKeyModalContent
							Header={() => <LicenseKeyDetailsHeader />}
							modalData={modalData!}
						/>
					}
				</Modal>
			)}
		</div>
	);
};

export default Licenses;
