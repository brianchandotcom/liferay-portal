/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEffect} from 'react';

import {Input} from '../../components/Input/Input';
import {Section} from '../../components/Section/Section';
import {RadioCard} from '../RadioCard/RadioCard';

export function BillingAddress({
	addresses,
	billingAddress,
	selectedAddress,
	setBillingAddress,
	setEnablePurchaseButton,
	setSelectedAddress,
	setShowNewAddressButton,
	showNewAddressButton,
}: {
	addresses: BillingAddress[];
	billingAddress: BillingAddress;
	selectedAddress: string;
	setBillingAddress: (value: BillingAddress) => void;
	setEnablePurchaseButton: (value: boolean) => void;
	setSelectedAddress: (value: string) => void;
	setShowNewAddressButton: (value: boolean) => void;
	showNewAddressButton: boolean;
}) {
	function getPostalAddressDescription(address: BillingAddress) {
		const description = `${address.street1}, ${
			address.street2 ? address.street2 + ',' : ''
		} ${address.city}, ${address.regionISOCode}, ${
			address.countryISOCode
		} ${address.zip} `;

		return {
			description,
			title: address.name,
		};
	}

	useEffect(() => {
		const emptyValues = Object.values(billingAddress).filter(
			(x) => x === ''
		).length;
		if (
			emptyValues === 0 ||
			(emptyValues === 1 && billingAddress.street2 === '')
		) {
			setEnablePurchaseButton(true);
		}
		else {
			setEnablePurchaseButton(false);
		}
	}, [billingAddress, setEnablePurchaseButton]);

	return (
		<Section className="get-app-modal-section" label="Billing Address">
			<div className="get-app-modal-section-card-addresses">
				{addresses.map((address, i) => {
					const {description, title} = getPostalAddressDescription(
						address
					);

					return (
						<RadioCard
							description={description}
							key={i}
							onChange={() => {
								setSelectedAddress(address.name as string);

								const postalAddress = addresses.find(
									(address) => address.name === title
								);

								const billingAddress: BillingAddress = {
									city: postalAddress?.city,
									country: postalAddress?.countryISOCode,
									countryISOCode: 'US',
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
			</div>

			{showNewAddressButton ? (
				<>
					<button
						className="get-app-modal-body-card-new-address"
						onClick={() => setShowNewAddressButton(false)}
					>
						<ClayIcon symbol="plus" />

						<span>New Address</span>
					</button>
				</>
			) : (
				<div className="get-app-modal-body-card-container">
					<div className="get-app-modal-body-card-header">
						<span className="get-app-modal-body-card-header-left-content">
							New Address
						</span>

						<button
							onClick={() => {
								setShowNewAddressButton(true);
								setSelectedAddress('');

								const billingAddress: BillingAddress = {
									city: '',
									country: '',
									countryISOCode: 'US',
									name: '',
									phoneNumber: '',
									regionISOCode: '',
									street1: '',
									street2: '',
									zip: '',
								};

								setBillingAddress(billingAddress);
							}}
						>
							Cancel
						</button>
					</div>

					<div className="get-app-modal-body-container">
						<Input
							label="Full Name"
							onChange={({target}) => {
								setBillingAddress({
									...billingAddress,
									name: target.value,
								});
							}}
							required
							value={billingAddress?.name}
						/>

						<Input
							label="Address"
							onChange={({target}) => {
								setBillingAddress({
									...billingAddress,
									street1: target.value,
								});
							}}
							required
							value={billingAddress?.street1}
						/>

						<Input
							onChange={({target}) => {
								setBillingAddress({
									...billingAddress,
									street2: target.value,
								});
							}}
							value={billingAddress?.street2}
						/>

						<div className="get-app-modal-double-input">
							<Input
								label="City"
								onChange={({target}) => {
									setBillingAddress({
										...billingAddress,
										city: target.value,
									});
								}}
								required
								value={billingAddress?.city}
							/>

							<Input
								label="State"
								onChange={({target}) => {
									setBillingAddress({
										...billingAddress,
										regionISOCode: target.value,
									});
								}}
								required
								value={billingAddress?.regionISOCode}
							/>
						</div>

						<div className="get-app-modal-double-input">
							<Input
								label="Zip/Area Code"
								onChange={({target}) => {
									setBillingAddress({
										...billingAddress,
										zip: target.value,
									});
								}}
								required
								value={billingAddress?.zip}
							/>

							<Input
								label="Country"
								onChange={({target}) => {
									setBillingAddress({
										...billingAddress,
										country: target.value,
									});
								}}
								required
								value={billingAddress?.country}
							/>
						</div>

						<Input
							label="Phone"
							onChange={({target}) => {
								setBillingAddress({
									...billingAddress,
									phoneNumber: target.value,
								});
							}}
							required
							value={billingAddress?.phoneNumber}
						/>
					</div>
				</div>
			)}
		</Section>
	);
}
