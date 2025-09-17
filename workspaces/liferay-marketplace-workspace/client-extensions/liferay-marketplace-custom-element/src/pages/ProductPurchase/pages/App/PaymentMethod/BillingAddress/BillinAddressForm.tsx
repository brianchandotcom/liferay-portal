/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {Input} from '../../../../../../components/Input/Input';
import Select from '../../../../../../components/Select/Select';
import useCommerceRegions from '../../../../../../hooks/useCommerceRegions';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../liferay/liferay';

import './BillingAddress.scss';
import {productPurchaseStore} from '../../../../store';

type BillingAddressProps = {
	billingAddress: BillingAddress;
	saveAddress: (address: BillingAddress) => void;
	setBillingAddress: React.Dispatch<BillingAddress>;
	setSelectedAddress: React.Dispatch<string>;
	setShowNewAddressButton: React.Dispatch<boolean>;
	showNewAddressButton: boolean;
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

const BillingAddressForm: React.FC<
	BillingAddressProps & {
		setSelectedAddress: React.Dispatch<string>;
	}
> = ({
	billingAddress,
	saveAddress,
	setBillingAddress,
	setSelectedAddress,
	setShowNewAddressButton,
	showNewAddressButton,
}) => {
	const {data: regionsResponse} = useCommerceRegions();
	const {context} = productPurchaseStore.getSnapshot();

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
				className="align-items-center billing-address-section-card-new-address d-flex justify-content-center mt-4 rounded w-100"
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
		<div className="billing-address-section-card-container h-auto mt-4 rounded">
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

				<div className="d-flex justify-content-end">
					<ClayButton
						onClick={() =>
							saveAddress({
								...billingAddress,
								vatNumber:
									context.payment.billingAddress.vatNumber,
							})
						}
					>
						{i18n.translate('save')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default BillingAddressForm;
