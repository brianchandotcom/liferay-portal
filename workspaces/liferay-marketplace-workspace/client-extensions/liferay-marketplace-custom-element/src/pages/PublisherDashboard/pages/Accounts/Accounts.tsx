/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import EmptyState from '../../../../components/EmptyState';
import QATable from '../../../../components/QATable';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import i18n from '../../../../i18n';
import HeadlessAdminUser from '../../../../services/rest/HeadlessAdminUser';
import {getCustomFieldValue} from '../../../../utils/customFieldUtil';
import {getSiteName} from '../../../../utils/site';
import {getAccountImage} from '../../../../utils/util';
import {usePublisherDashboardOutletContext} from '../../PublisherDashboardOutlet';

import './Accounts.scss';

type AccountDetailsPageProps = {
	selectedAccount: UserAccount;
};

function AccountDetailsPage({selectedAccount}: AccountDetailsPageProps) {
	const {properties} = useMarketplaceContext();

	const [selectedAccountAddress, setSelectedAccountAddress] =
		useState<AccountPostalAddresses[]>();

	const accountType = selectedAccount
		? selectedAccount.type === 'person'
			? 'Personal'
			: selectedAccount.type
		: '';

	useEffect(() => {
		const makeFetch = async () => {
			const {items} = await HeadlessAdminUser.getAccountPostalAddresses(
				selectedAccount.id
			);

			setSelectedAccountAddress(items);
		};

		makeFetch();
	}, [selectedAccount]);

	if (!selectedAccount) {
		return (
			<div className="pl-5">
				<EmptyState type="BLANK" />
			</div>
		);
	}

	return (
		<div className="account-details-container">
			<div className="account-details-header-container">
				<div className="account-details-header-left-content-container">
					<img
						alt="Account Image"
						className="account-details-header-left-content-image rounded"
						draggable={false}
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
								title: i18n.translate('github-username'),
								value:
									getCustomFieldValue(
										selectedAccount?.customFields ?? [],
										'Github Username'
									) || '-',
							},
							{
								title: i18n.translate('description'),
								value: selectedAccount.description || '-',
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
										selectedAccount.customFields ?? [],
										'Contact Phone'
									) || '-',
							},
							{
								title: i18n.translate('email'),
								value:
									getCustomFieldValue(
										selectedAccount.customFields ?? [],
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
											selectedAccount.customFields ?? [],
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

				{!!selectedAccountAddress?.length && (
					<DetailedCard
						cardIconAltText="Address Icon"
						cardTitle={i18n.translate('address')}
						clayIcon="geolocation"
					>
						<QATable
							items={[
								...(selectedAccountAddress
									? selectedAccountAddress.map((address) => ({
											title: i18n.translate(
												'business-address'
											),
											value: `${address.streetAddressLine1}, 
															${address.addressLocality}, 
															${address.addressRegion}, 
															${address.postalCode}, 
															${address.addressCountry}`,
										}))
									: [{title: '', value: ''}]),
							]}
						/>
					</DetailedCard>
				)}

				<DetailedCard
					cardIconAltText="Agreements Icon"
					cardTitle={i18n.translate('agreements')}
					clayIcon="info-book"
				>
					<QATable
						items={[
							{
								title: (
									<a
										href={`/documents/d/${getSiteName()}/${properties.publisherLicenseAgreement}`}
										target="_blank"
									>
										{i18n.translate(
											'liferay-publisher-license-agreement'
										)}
									</a>
								),
								value: (
									<ClayIcon color="black" symbol="download" />
								),
							},
							{
								title: (
									<a
										href={`/documents/d/${getSiteName()}/${properties.endUserLicenseAgreement}`}
										target="_blank"
									>
										{i18n.translate(
											'end-user-license-agreement'
										)}
									</a>
								),
								value: (
									<ClayIcon color="black" symbol="download" />
								),
							},
						]}
					/>
				</DetailedCard>
			</div>
		</div>
	);
}

const Accounts = () => {
	const {selectedAccount} = usePublisherDashboardOutletContext();

	return <AccountDetailsPage selectedAccount={selectedAccount} />;
};

export default Accounts;
