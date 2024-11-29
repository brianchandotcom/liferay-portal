/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import {getCustomFieldValue} from '../../../../utils/customFieldUtil';
import {getAccountImage, removeProtocolURL} from '../../../../utils/util';

import './Accounts.scss';
import EmptyState from '../../../../components/EmptyState';
import HeadlessAdminUserImpl from '../../../../services/rest/HeadlessAdminUser';

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
			const {items} =
				await HeadlessAdminUserImpl.getAccountPostalAddresses(
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
							cardTitle="Profile"
							clayIcon="user"
						>
							<table className="account-details-body-table">
								<tr className="account-details-body-table-row">
									<th>Entity Type</th>

									<td className="account-details-body-table-description">
										{selectedAccount.type}
									</td>
								</tr>

								<tr className="account-details-body-table-row">
									<th>Publisher Name</th>

									<td className="account-details-body-table-description">
										{selectedAccount.name}
									</td>
								</tr>

								<tr className="account-details-body-table-row">
									<th>Publisher ID</th>

									<td className="account-details-body-table-description">
										{selectedAccount.id}
									</td>
								</tr>

								<tr className="account-details-body-table-row">
									<th>Github Username</th>

									<td className="account-details-body-table-description">
										{getCustomFieldValue(
											selectedAccount?.customFields ?? [],
											'Github Username'
										)}
									</td>
								</tr>

								<tr className="account-details-body-table-row">
									<th>Description</th>

									<td className="account-details-body-table-description">
										{selectedAccount.description}
									</td>
								</tr>
							</table>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Contact Icon"
							cardTitle="Contact"
							clayIcon="phone"
						>
							<table className="account-details-body-table">
								<tr className="account-details-body-table-row">
									<th>Phone</th>

									<td className="account-details-body-table-description">
										{getCustomFieldValue(
											selectedAccount.customFields ?? [],
											'Contact Phone'
										)}
									</td>
								</tr>

								<tr className="account-details-body-table-row">
									<th>Email</th>

									<td className="account-details-body-table-description">
										{getCustomFieldValue(
											selectedAccount.customFields ?? [],
											'Contact Email'
										)}
									</td>
								</tr>

								<tr className="account-details-body-table-row">
									<th>Website</th>

									<td className="account-details-body-table-description">
										<a
											href={
												`https://` +
												removeProtocolURL(
													getCustomFieldValue(
														selectedAccount.customFields ??
															[],
														'Homepage URL'
													)
												)
											}
											target="_blank"
										>
											{getCustomFieldValue(
												selectedAccount.customFields ??
													[],
												'Homepage URL'
											)}
										</a>
									</td>
								</tr>
							</table>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Address Icon"
							cardTitle="Address"
							clayIcon="geolocation"
						>
							<table className="account-details-body-table">
								{selectedAccountAddress?.map((address, i) => (
									<tr
										className="account-details-body-table-row"
										key={i}
									>
										<th>Business Address</th>

										<td className="account-details-body-table-description">
											{address.streetAddressLine1}
											{', '}
											{address.addressLocality}
											{', '}
											{address.addressRegion}{' '}
											{address.postalCode}
											{', '}
											{address.addressCountry}
										</td>
									</tr>
								))}
							</table>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Agreements Icon"
							cardTitle="Agreements"
							clayIcon="info-book"
						>
							<table className="account-details-body-table">
								<tr>
									<th>Liferay Publisher License Agreement</th>

									<td className="account-details-body-table-description">
										<ClayIcon
											color="black"
											symbol="download"
										/>
									</td>
								</tr>

								<tr>
									<th>End User License Agreement</th>

									<td className="account-details-body-table-description">
										<ClayIcon
											color="black"
											symbol="download"
										/>
									</td>
								</tr>
							</table>
						</DetailedCard>

						<DetailedCard
							cardIconAltText="Payment Icon"
							cardTitle="Payment "
							clayIcon="credit-card"
						>
							{getCustomFieldValue(
								selectedAccount.customFields ?? [],
								'Paypal Email Address'
							) ? (
								<table className="account-details-body-table">
									<tr className="account-details-body-table-row">
										<th>Paypal Account</th>

										<td className="account-details-body-table-description">
											{maskDigits(
												getCustomFieldValue(
													selectedAccount.customFields ??
														[],
													'Paypal Email Address'
												)
											)}
										</td>
									</tr>

									<tr className="account-details-body-table-row">
										<th>Tax ID</th>

										<td className="account-details-body-table-description">
											{maskDigits(
												selectedAccount?.taxId ?? ''
											)}
										</td>
									</tr>
								</table>
							) : (
								<div className="account-details-body-empty-payment">
									Edit your publisher account to provide
									payment information for sales in the
									Marketplace
								</div>
							)}
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
