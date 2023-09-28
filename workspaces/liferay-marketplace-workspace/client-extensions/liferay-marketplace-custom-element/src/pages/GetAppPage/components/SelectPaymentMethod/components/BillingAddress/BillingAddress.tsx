/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEffect} from 'react';

import {Input} from '../../../../../../components/Input/Input';
import {RadioCard} from '../../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../../components/Section/Section';
import getPostalAddressDescription from './utils/getPostalAddressDescription';

import './BillingAddress.scss';

interface BillingAddressProps {
	addresses: BillingAddress[];
	billingAddress: BillingAddress;
	selectedAddress: string;
	setBillingAddress: (value: BillingAddress) => void;
	setEnablePurchaseButton: (value: boolean) => void;
	setSelectedAddress: (value: string) => void;
	setShowNewAddressButton: (value: boolean) => void;
	showNewAddressButton: boolean;
}

export function BillingAddress({
	addresses,
	billingAddress,
	selectedAddress,
	setBillingAddress,
	setEnablePurchaseButton,
	setSelectedAddress,
	setShowNewAddressButton,
	showNewAddressButton,
}: BillingAddressProps) {
	useEffect(() => {
		const emptyValues = Object.values(billingAddress).filter(
			(field) => field === ''
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
		<Section
			className="billing-address-section w-100"
			label="Billing Address"
		>
			<div className="billing-address-section-card-addresses">
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
						className="align-items-center billing-address-section-card-new-address d-flex justify-content-center mt-4 w-100"
						onClick={() => setShowNewAddressButton(false)}
					>
						<ClayIcon symbol="plus" />

						<span>New Address</span>
					</button>
				</>
			) : (
				<div className="billing-address-section-card-container h-auto w-100">
					<div className="align-items-center billing-address-section-card-header d-flex justify-content-between w-100">
						<span className="billing-address-section-card-header-left-content">
							New Address
						</span>

						<button
							className="px-4 py-2"
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

					<div className="billing-address-section-container d-flex flex-column p-4 w-100">
						<Input
							label="Full Name"
							onChange={({target}) =>
								setBillingAddress({
									...billingAddress,
									name: target.value,
								})
							}
							required
							value={billingAddress?.name}
						/>

						<Input
							label="Address"
							onChange={({target}) =>
								setBillingAddress({
									...billingAddress,
									street1: target.value,
								})
							}
							required
							value={billingAddress?.street1}
						/>

						<Input
							onChange={({target}) =>
								setBillingAddress({
									...billingAddress,
									street2: target.value,
								})
							}
							value={billingAddress?.street2}
						/>

						<div className="billing-address-double-input">
							<Input
								label="City"
								onChange={({target}) =>
									setBillingAddress({
										...billingAddress,
										city: target.value,
									})
								}
								required
								value={billingAddress?.city}
							/>

							<Input
								label="State"
								onChange={({target}) =>
									setBillingAddress({
										...billingAddress,
										regionISOCode: target.value,
									})
								}
								required
								value={billingAddress?.regionISOCode}
							/>
						</div>

						<div className="billing-address-double-input">
							<Input
								label="Zip/Area Code"
								onChange={({target}) =>
									setBillingAddress({
										...billingAddress,
										zip: target.value,
									})
								}
								required
								value={billingAddress?.zip}
							/>

							<Input
								label="Country"
								onChange={({target}) =>
									setBillingAddress({
										...billingAddress,
										country: target.value,
									})
								}
								required
								value={billingAddress?.country}
							/>
						</div>

						<Input
							label="Phone"
							onChange={({target}) =>
								setBillingAddress({
									...billingAddress,
									phoneNumber: target.value,
								})
							}
							required
							value={billingAddress?.phoneNumber}
						/>
					</div>
				</div>
			)}
		</Section>
	);
}
