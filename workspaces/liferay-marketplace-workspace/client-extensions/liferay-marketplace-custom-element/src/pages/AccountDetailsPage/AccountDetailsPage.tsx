import {AccountHeaderButton} from './AccountHeaderButton';

import './AccountDetailsPage.scss';

import {useEffect, useState} from 'react';

import creditCartIcon from '../../assets/icons/credit_card_icon.svg';
import downloadIcon from '../../assets/icons/download_icon.svg';
import locationIcon from '../../assets/icons/location_icon.svg';
import phoneIcon from '../../assets/icons/phone_icon.svg';
import userIcon from '../../assets/icons/user_icon.svg';
import {DetailedCard} from '../../components/DetailedCard/DetailedCard';
import {getAccountPostalAddressesByAccountId} from '../../utils/api';
import {getCustomFieldValue} from '../../utils/customFieldUtil';
import {removeProtocolURL, showAccountImage} from '../../utils/util';
import {DashboardListItems} from '../DashBoardPage/DashboardPage';

interface AccountDetailsPageProps {
	commerceAccount?: CommerceAccount;
	dashboardNavigationItems: DashboardListItems[];
	selectedAccount: Account;
	setDashboardNavigationItems: (values: DashboardListItems[]) => void;
	totalApps: number;
	totalMembers: number;
}

export function AccountDetailsPage({
	commerceAccount,
	dashboardNavigationItems,
	selectedAccount,
	setDashboardNavigationItems,
	totalApps,
	totalMembers,
}: AccountDetailsPageProps) {
	const [selectedAccountAddress, setSelectedAccountAddress] =
		useState<AccountPostalAddresses[]>();

	const maskDigits = (str: string) => {
		const first3Digits = str.slice(0, 3);
		const lastDigits = str.slice(3);
		const maskedDigits = lastDigits.replaceAll(/\S/g, '*');

		return first3Digits + maskedDigits;
	};

	const {type} = selectedAccount;

	const accountType =
		type === 'person'
			? 'Individual'
			: type.charAt(0).toUpperCase() + type.slice(1);

	const updateDashboardNavigationItems = (itemName: string) => {
		const newDashboardNavigationItems = dashboardNavigationItems.map(
			(navigationItem) => {
				if (navigationItem.itemName === itemName) {
					return {
						...navigationItem,
						itemSelected: true,
					};
				}

				return {
					...navigationItem,
					itemSelected: false,
				};
			}
		);

		setDashboardNavigationItems(newDashboardNavigationItems);
	};

	useEffect(() => {
		const makeFetch = async () => {
			const {items} = await getAccountPostalAddressesByAccountId(
				selectedAccount.id
			);

			setSelectedAccountAddress(items);
		};

		makeFetch();
	}, [selectedAccount]);

	return (
		<>
			<div className="account-details-container">
				<div className="account-details-header-container">
					<div className="account-details-header-left-content-container">
						<img
							alt="Account Image"
							className="account-details-header-left-content-image"
							src={showAccountImage(commerceAccount?.logoURL)}
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
							boldText={`${totalApps}`}
							name="apps"
							onClick={(itemName) =>
								updateDashboardNavigationItems(itemName)
							}
							text="Apps"
							title="Apps"
						/>

						<AccountHeaderButton
							boldText={`${totalMembers}`}
							name="members"
							onClick={(itemName) =>
								updateDashboardNavigationItems(itemName)
							}
							text="Items"
							title="Members"
						/>

						<AccountHeaderButton
							boldText="0"
							name="solutions"
							text="Items"
							title="Solutions"
						/>
					</div>
				</div>

				<div className="account-details-body-container">
					<DetailedCard
						cardIcon={userIcon}
						cardIconAltText="Profile Icon"
						cardTitle="Profile"
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
						cardIcon={phoneIcon}
						cardIconAltText="Contact Icon"
						cardTitle="Contact"
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
											selectedAccount.customFields ?? [],
											'Homepage URL'
										)}
									</a>
								</td>
							</tr>
						</table>
					</DetailedCard>

					<DetailedCard
						cardIcon={locationIcon}
						cardIconAltText="Address Icon"
						cardTitle="Address"
					>
						<table className="account-details-body-table">
							{selectedAccountAddress?.map((address) => (
								<tr className="account-details-body-table-row">
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
								<th>
									Liferay Publisher Program License agreement
								</th>

								<td className="account-details-body-table-description">
									<img src={downloadIcon} />
								</td>
							</tr>

							<tr>
								<th>Liferay Publisher agreement</th>

								<td className="account-details-body-table-description">
									<img src={downloadIcon} />
								</td>
							</tr>
						</table>
					</DetailedCard>

					<DetailedCard
						cardIcon={creditCartIcon}
						cardIconAltText="Payment  Icon"
						cardTitle="Payment "
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
											commerceAccount?.taxId ?? ''
										)}
									</td>
								</tr>
							</table>
						) : (
							<div className="account-details-body-empty-payment">
								Edit your publisher account to provide payment
								information for sales in the Marketplace
							</div>
						)}
					</DetailedCard>
				</div>
			</div>
		</>
	);
}
