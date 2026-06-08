/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import React, {useMemo, useState} from 'react';

import {RadioCard} from '../../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../../components/Section/Section';
import useAccountAddresses from '../../../../../../hooks/useAccountAddresses';
import useCommerceRegions from '../../../../../../hooks/useCommerceRegions';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../liferay/liferay';
import HeadlessAdminUser from '../../../../../../services/rest/HeadlessAdminUser';
import {Region} from '../../../../../../services/rest/HeadlessCommerceAdminAddress';
import {useProductPurchaseOutletContext} from '../../../../ProductPurchaseOutlet';
import {productPurchaseStore} from '../../../../store';
import BillingAddressForm from './BillingAddressForm';
import getPostalAddressDescription from './getPostalAddressDescription';

const mapPostalAddressToBillingAddress = (
	postalAddress?: BillingAddress
): BillingAddress => ({
	city: postalAddress?.city || '',
	country: postalAddress?.countryISOCode || '',
	countryISOCode: postalAddress?.countryISOCode || 'US',
	name: postalAddress?.name || '',
	phoneNumber: postalAddress?.phoneNumber || '',
	regionISOCode: postalAddress?.regionISOCode || '',
	street1: postalAddress?.street1 || '',
	street2: postalAddress?.street2 || '',
	zip: postalAddress?.zip || '',
});

type BillingAddressProps = {
	hideNewAddressButton?: boolean;
	sectionName?: string;
};

const BillingAddress: React.FC<BillingAddressProps> = ({
	hideNewAddressButton = false,
	sectionName = i18n.translate('billing-address'),
}) => {
	const {selectedAccount} = useProductPurchaseOutletContext();

	const {data: addressResponse, mutate: mutateUserAccoutAddress} =
		useAccountAddresses(selectedAccount?.id);

	const addresses = useMemo(
		() => addressResponse?.items ?? [],
		[addressResponse]
	);

	const {billingAddress} = useSelector(
		productPurchaseStore,
		(state) => state.context.payment
	);

	const billingAdressName = billingAddress.name || '';

	const [showNewAddressButton, setShowNewAddressButton] = useState(true);
	const {data: regionsResponse} = useCommerceRegions();
	const [selectedAddress, setSelectedAddress] =
		useState<string>(billingAdressName);

	const regions = regionsResponse?.items ?? [];

	React.useEffect(() => {
		if (
			hideNewAddressButton &&
			!!addresses.length &&
			!billingAddress.name
		) {
			const firstAddress = addresses[0];
			const newBillingAddress =
				mapPostalAddressToBillingAddress(firstAddress);

			setSelectedAddress(firstAddress.name || '');

			productPurchaseStore.send({
				billingAddress: newBillingAddress,
				type: 'setBillingAddress',
			});
		}
	}, [addresses, billingAddress.name, hideNewAddressButton]);

	React.useEffect(() => {
		if (billingAddress.name) {
			setSelectedAddress(billingAddress.name);
		}
	}, [billingAddress.name]);

	const onSelectAddress = async ({
		address,
		title,
	}: {
		address: BillingAddress;
		title: string | undefined;
	}) => {
		setSelectedAddress(address.name as string);

		const postalAddress = addresses.find(
			(address) => address.name === title
		);

		const billingAddress = mapPostalAddressToBillingAddress(
			postalAddress || address
		);

		productPurchaseStore.send({
			billingAddress,
			type: 'setBillingAddress',
		});

		if (!showNewAddressButton) {
			setShowNewAddressButton(true);
		}
	};

	const saveAddress = async (billingAddress: BillingAddress) => {
		const getCountryNameByCode = (
			regions: Region[],
			countryCode?: string
		) => {
			const country = regions.find((region) => region.a2 === countryCode);

			return (
				country?.title_i18n[Liferay.ThemeDisplay.getLanguageId()] ||
				country?.title_i18n[
					Liferay.ThemeDisplay.getDefaultLanguageId()
				] ||
				country?.name
			);
		};

		const getRegionByCountryCode = (
			regions: Region[],
			regionISOCode?: string,
			countryCode?: string
		) => {
			const country = regions.find((region) => region.a2 === countryCode);
			const addressRegion = country?.regions.find(
				(region) => region.regionCode === regionISOCode
			);

			return addressRegion?.name;
		};

		const postAddress = await HeadlessAdminUser.postAddress(
			selectedAccount.id,
			{
				addressCountry: getCountryNameByCode(
					regions,
					billingAddress?.country
				),
				addressLocality: billingAddress.city,
				addressRegion: getRegionByCountryCode(
					regions,
					billingAddress.regionISOCode,
					billingAddress?.country
				),
				addressType: 'billing-and-shipping',
				name: billingAddress.name,
				phoneNumber: billingAddress.phoneNumber,
				postalCode: billingAddress.zip,
				primary: false,
				streetAddressLine1: billingAddress.street1,
				streetAddressLine2: billingAddress.street2,
			}
		);

		await mutateUserAccoutAddress((addressCache) => ({
			...addressCache,
			items: [...addresses, postAddress],
		}));

		setSelectedAddress(billingAddress.name as string);

		productPurchaseStore.send({
			billingAddress,
			type: 'setBillingAddress',
		});

		if (!showNewAddressButton) {
			setShowNewAddressButton(true);
		}
	};

	return (
		<Section
			className="billing-address-section"
			label={sectionName}
			required
		>
			{addresses?.map((address, index) => {
				const {description, title} =
					getPostalAddressDescription(address);

				return (
					<RadioCard
						className="mb-4"
						description={description}
						key={index}
						onChange={() => onSelectAddress({address, title})}
						selected={selectedAddress === address.name}
						title={title}
					/>
				);
			})}

			{!hideNewAddressButton && (
				<BillingAddressForm
					saveAddress={saveAddress}
					setBillingAddress={() =>
						productPurchaseStore.send({
							billingAddress,
							type: 'setBillingAddress',
						})
					}
					setSelectedAddress={setSelectedAddress}
					setShowNewAddressButton={setShowNewAddressButton}
					showNewAddressButton={showNewAddressButton}
				/>
			)}
		</Section>
	);
};

export default BillingAddress;
