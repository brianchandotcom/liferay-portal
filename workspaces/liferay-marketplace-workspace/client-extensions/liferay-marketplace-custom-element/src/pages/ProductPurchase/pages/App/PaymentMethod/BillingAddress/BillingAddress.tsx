/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {Input} from '../../../../../../components/Input/Input';
import {RadioCard} from '../../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../../components/Section/Section';
import Select from '../../../../../../components/Select/Select';
import useCommerceRegions from '../../../../../../hooks/useCommerceRegions';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../liferay/liferay';
import getPostalAddressDescription from './getPostalAddressDescription';

import './BillingAddress.scss';

type BillingAddressProps = {
	addresses: BillingAddress[];
	billingAddress: BillingAddress;
	setBillingAddress: React.Dispatch<BillingAddress>;
};

const defaultBillingAddress = {
	city: '',
	country: '',
	countryISOCode: '',
	name: '',
	phoneNumber: '',
	regionISOCode: '',
	street1: '',
	street2: '',
	zip: '',
};

function BillingAddressForm({
	billingAddress,
	setBillingAddress,
	setSelectedAddress,
	setShowNewAddressButton,
	showNewAddressButton,
}: BillingAddressProps & {
	setSelectedAddress: React.Dispatch<string>;
	setShowNewAddressButton: React.Dispatch<boolean>;
	showNewAddressButton: boolean;
}) {
	const {data: regionsResponse} = useCommerceRegions();

	const regions = regionsResponse?.items || [];

	const states =
		regions.find((region) => region.a2 === billingAddress.country)
			?.regions ?? [];

	const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setBillingAddress({
			...billingAddress,
			[event.target.name]: event.target.value,
		});
	};

	if (showNewAddressButton) {
		return (
			<button
				className="align-items-center billing-address-section-card-new-address d-flex justify-content-center mt-4 w-100"
				onClick={() => {
					setShowNewAddressButton(false);

					setBillingAddress({
						...defaultBillingAddress,
						countryISOCode: regions[0].a2,
					});
				}}
			>
				<ClayIcon symbol="plus" />

				<span>New Address</span>
			</button>
		);
	}

	return (
		<div className="billing-address-section-card-container h-auto mt-4">
			<div className="align-items-center billing-address-section-card-header d-flex justify-content-between">
				<small className="font-weight-bold">New Address</small>

				<ClayButton
					onClick={() => {
						setShowNewAddressButton(true);
						setSelectedAddress('');

						setBillingAddress(defaultBillingAddress);
					}}
				>
					{i18n.translate('cancel')}
				</ClayButton>
			</div>

			<div className="billing-address-section-container d-flex flex-column p-4 w-100">
				<Input
					label="Full Name"
					name="name"
					onChange={onChange}
					required
					value={billingAddress?.name}
				/>

				<Input
					label="Address"
					name="street1"
					onChange={onChange}
					placeholder="Address 1"
					required
					value={billingAddress?.street1}
				/>

				<Input
					name="street2"
					onChange={onChange}
					placeholder="Address 2"
					value={billingAddress?.street2}
				/>

				<Select
					boldLabel
					className="custom-input"
					label="Country"
					name="country"
					onChange={({target: {value}}) => {
						const states =
							regions.find((region) => region.a2 === value)
								?.regions ?? [];

						setBillingAddress({
							...billingAddress,
							country: value,
							countryISOCode: value,
							...(!!states.length && {
								regionISOCode: states[0].regionCode,
							}),
						});
					}}
					options={regions.map((region) => ({
						key: region.a2,
						name:
							region.title_i18n[
								Liferay.ThemeDisplay.getLanguageId()
							] ||
							region.title_i18n[
								Liferay.ThemeDisplay.getDefaultLanguageId()
							] ||
							region.name,
					}))}
					required
					value={billingAddress?.country}
				/>

				<Select
					boldLabel
					className="custom-input"
					defaultOption={false}
					disabled={!states.length}
					label="State"
					name="regionISOCode"
					onChange={onChange}
					options={states.map((state) => ({
						key: state.regionCode,
						name: state.name,
					}))}
					required={!!states.length}
					value={billingAddress?.regionISOCode}
				/>

				<Input
					label="City"
					name="city"
					onChange={onChange}
					required
					value={billingAddress?.city}
				/>

				<Input
					label="Zip/Area Code"
					name="zip"
					onChange={onChange}
					required
					value={billingAddress?.zip}
				/>

				<Input
					label="Phone"
					name="phoneNumber"
					onChange={onChange}
					required
					value={billingAddress?.phoneNumber}
				/>
			</div>
		</div>
	);
}

export function BillingAddress(props: BillingAddressProps) {
	const [selectedAddress, setSelectedAddress] = useState('');

	const {addresses, setBillingAddress} = props;

	const [showNewAddressButton, setShowNewAddressButton] = useState(true);

	return (
		<Section
			className="billing-address-section w-100"
			label="Billing Address"
			required
		>
			{addresses.map((address, index) => {
				const {description, title} =
					getPostalAddressDescription(address);

				return (
					<RadioCard
						className="mb-4"
						description={description}
						key={index}
						onChange={() => {
							setSelectedAddress(address.name as string);

							const postalAddress = addresses.find(
								(address) => address.name === title
							);

							const billingAddress = {
								city: postalAddress?.city,
								country: postalAddress?.countryISOCode,
								countryISOCode:
									postalAddress?.countryISOCode || 'US',
								name: postalAddress?.name,
								phoneNumber: postalAddress?.phoneNumber,
								regionISOCode: postalAddress?.regionISOCode,
								street1: postalAddress?.street1,
								street2: postalAddress?.street2,
								zip: postalAddress?.zip,
							};

							setShowNewAddressButton(false);

							setBillingAddress(billingAddress);
						}}
						selected={selectedAddress === address.name}
						title={title}
					/>
				);
			})}

			<BillingAddressForm
				{...props}
				setSelectedAddress={setSelectedAddress}
				setShowNewAddressButton={setShowNewAddressButton}
				showNewAddressButton={showNewAddressButton}
			/>
		</Section>
	);
}
