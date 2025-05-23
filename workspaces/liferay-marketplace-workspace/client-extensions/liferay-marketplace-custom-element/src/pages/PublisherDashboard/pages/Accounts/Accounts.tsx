/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import EmptyState from '../../../../components/EmptyState';
import QATable from '../../../../components/QATable';
import i18n from '../../../../i18n';
import HeadlessAdminUser from '../../../../services/rest/HeadlessAdminUser';
import {getCustomFieldValue} from '../../../../utils/customFieldUtil';
import {getAccountImage} from '../../../../utils/util';

import './Accounts.scss';

type AccountDetailsPageProps = {
	selectedAccount: Account;
	totalApps: number;
};

type AccountHeaderButtonProps = {
	count: string;
	name: string;
	onClick?: (value: string) => void;
	text: string;
	title: string;
};

const AccountHeaderButton: React.FC<AccountHeaderButtonProps> = ({
	count,
	name,
	onClick,
	text,
	title,
}) => (
	<div className="d-flex flex-column">
		<span className="font-weight-bold mb-4">{title}</span>

		<ClayButton
			displayType="unstyled"
			onClick={() => onClick && onClick(name)}
		>
			<strong className="font-weight-bold mr-1">{count}</strong>

			<span>{text}</span>

			<ClayIcon symbol="angle-right-small" />
		</ClayButton>
	</div>
);

const maskDigits = (str: string) => {
	const first3Digits = str.slice(0, 3);
	const lastDigits = str.slice(3);
	const maskedDigits = lastDigits.replaceAll(/\S/g, '*');

	return first3Digits + maskedDigits;
};

function AccountDetailsPage({
	selectedAccount,
	totalApps,
}: AccountDetailsPageProps) {
	const navigate = useNavigate();
	const [selectedAccountAddress, setSelectedAccountAddress] =
		useState<AccountPostalAddresses[]>();

	let accountType = '';
	if (selectedAccount) {
		const {type} = selectedAccount;

		accountType =
			type === 'person'
				? 'Individual'
				: type.charAt(0).toUpperCase() + type.slice(1);
	}

	useEffect(() => {
		const makeFetch = async () => {
			const {items} = await HeadlessAdminUser.getAccountPostalAddresses(
				selectedAccount.id
			);

			setSelectedAccountAddress(items);
		};

		makeFetch();
	}, [selectedAccount]);

	return (
		<>
			{selectedAccount && accountType && (
				<div className="account-details-container">
					<div className="account-details-header-container">
						<div className="account-details-header-left-content-container">
							<img
								alt="Account Image"
								className="account-details-header-left-content-image"
								src={getAccountImage(selectedAccount?.logoURL)}
							/>

							<div className="account-details-header-left-content-text-container">
								<span className="account-details-header-left-content-title">
									{selectedAccount.name}
								</span>

								<span className="account-details-header-left-content-description">
									{accountType} Account
								</span>
							</div>
						</div>

						<div className="account-details-header-right-container">
							<AccountHeaderButton
								count={totalApps as unknown as string}
								name="apps"
								onClick={() => navigate('/')}
								text="Apps"
								title="Apps"
							/>

							<AccountHeaderButton
								count="0"
								name="solutions"
								text="Items"
								title="Solutions"
							/>
						</div>
					</div>

					<div className="account-details-body-container">
						<DetailedCard
							cardIconAltText="Profile Icon"
							cardTitle={i18n.translate('profile')}
							clayIcon="user"
						>
							<QATable
								items={[
									{
										title: i18n.translate('entity-type'),
										value: selectedAccount.type,
									},
									{
										title: i18n.translate('publisher-name'),
										value: selectedAccount.name,
									},
									{
										title: i18n.translate('publisher-id'),
										value: selectedAccount.id,
									},
									{
										title: i18n.translate(
											'github-username'
										),
										value:
											getCustomFieldValue(
												selectedAccount?.customFields ??
													[],
												'Github Username'
											) || '-',
									},
									{
										title: i18n.translate('description'),
										value:
											selectedAccount.description || '-',
									},
								]}
							/>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Contact Icon"
							cardTitle={i18n.translate('contact')}
							clayIcon="phone"
						>
							<QATable
								items={[
									{
										title: i18n.translate('phone'),
										value:
											getCustomFieldValue(
												selectedAccount.customFields ??
													[],
												'Contact Phone'
											) || '-',
									},
									{
										title: i18n.translate('email'),
										value:
											getCustomFieldValue(
												selectedAccount.customFields ??
													[],
												'Contact Email'
											) || '-',
									},
									{
										title: i18n.translate('website'),
										value: getCustomFieldValue(
											selectedAccount.customFields ?? [],
											'Homepage URL'
										) ? (
											<a>
												{getCustomFieldValue(
													selectedAccount.customFields ??
														[],
													'Homepage URL'
												)}
											</a>
										) : (
											'-'
										),
									},
								]}
							/>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Address Icon"
							cardTitle={i18n.translate('address')}
							clayIcon="geolocation"
						>
							<QATable
								items={[
									...(selectedAccountAddress
										? selectedAccountAddress.map(
												(address) => ({
													title: i18n.translate(
														'business-address'
													),
													value: `${address.streetAddressLine1}, 
															${address.addressLocality}, 
															${address.addressRegion}, 
															${address.postalCode}, 
															${address.addressCountry}`,
												})
											)
										: [{title: '', value: ''}]),
								]}
							/>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Agreements Icon"
							cardTitle={i18n.translate('agreements')}
							clayIcon="info-book"
						>
							<QATable
								items={[
									{
										title: i18n.translate(
											'liferay-publisher-license-agreement'
										),
										value: (
											<ClayIcon
												color="black"
												symbol="download"
											/>
										),
									},
									{
										title: i18n.translate(
											'end-user-license-agreement'
										),
										value: (
											<ClayIcon
												color="black"
												symbol="download"
											/>
										),
									},
								]}
							/>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Payment Icon"
							cardTitle={i18n.translate('payment')}
							clayIcon="credit-card"
						>
							<QATable
								items={[
									...(getCustomFieldValue(
										selectedAccount.customFields ?? [],
										'Paypal Email Address'
									)
										? [
												{
													title: i18n.translate(
														'paypal-account'
													),
													value: maskDigits(
														getCustomFieldValue(
															selectedAccount.customFields ??
																[],
															'Paypal Email Address'
														)
													),
												},
												{
													title: i18n.translate(
														'paypal-account'
													),
													value: maskDigits(
														selectedAccount?.taxId ??
															'-'
													),
												},
											]
										: [
												{
													title: '',
													value: 'Edit your publisher account to provide payment information for sales in the Marketplace',
												},
											]),
								]}
							/>
						</DetailedCard>
					</div>
				</div>
			)}
			{!selectedAccount && (
				<div className="pl-5">
					<EmptyState type="BLANK" />
				</div>
			)}
		</>
	);
}

const Accounts = () => {
	const {appsTotalCount, selectedAccount} = useOutletContext<any>();

	return (
		<AccountDetailsPage
			selectedAccount={selectedAccount}
			totalApps={appsTotalCount}
		/>
	);
};

export default Accounts;
