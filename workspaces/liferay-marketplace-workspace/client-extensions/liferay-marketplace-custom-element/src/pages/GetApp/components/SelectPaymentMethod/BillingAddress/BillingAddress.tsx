/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import {Input} from '../../../../../components/Input/Input';
import {RadioCard} from '../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../components/Section/Section';
import getPostalAddressDescription from './getPostalAddressDescription';

import './BillingAddress.scss';

type BillingAddressProps = {
	addresses: BillingAddress[];
	billingAddress: BillingAddress;
	selectedAddress: string;
	setBillingAddress: React.Dispatch<BillingAddress>;
	setSelectedAddress: React.Dispatch<string>;
	setShowNewAddressButton: React.Dispatch<boolean>;
	showNewAddressButton: boolean;
};

export function BillingAddress({
	addresses,
	billingAddress,
	selectedAddress,
	setBillingAddress,
	setSelectedAddress,
	setShowNewAddressButton,
	showNewAddressButton,
}: BillingAddressProps) {
	const onChange = (event: React.ChangeEvent<HTMLInputElement>) =>
		setBillingAddress({
			...billingAddress,
			[event.target.name]: event.target.value,
		});

	return (
		<Section
			className="billing-address-section w-100"
			label="Billing Address"
			required
		>
			<div className="billing-address-section-card-addresses">
				{addresses.map((address, index) => {
					const {description, title} = getPostalAddressDescription(
						address
					);

					return (
						<RadioCard
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
			</div>

			{showNewAddressButton ? (
				<button
					className="align-items-center billing-address-section-card-new-address d-flex justify-content-center mt-4 w-100"
					onClick={() => setShowNewAddressButton(false)}
				>
					<ClayIcon symbol="plus" />

					<span>New Address</span>
				</button>
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

								setBillingAddress({
									city: '',
									country: '',
									countryISOCode: 'US',
									name: '',
									phoneNumber: '',
									regionISOCode: '',
									street1: '',
									street2: '',
									zip: '',
								});
							}}
						>
							Cancel
						</button>
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
							required
							value={billingAddress?.street1}
						/>

						<Input
							name="street2"
							onChange={onChange}
							value={billingAddress?.street2}
						/>

						<div className="billing-address-double-input">
							<Input
								label="City"
								name="city"
								onChange={onChange}
								required
								value={billingAddress?.city}
							/>

							<Input
								label="State"
								name="regionISOCode"
								onChange={onChange}
								required
								value={billingAddress?.regionISOCode}
							/>
						</div>

						<div className="billing-address-double-input">
							<Input
								label="Zip/Area Code"
								name="zip"
								onChange={onChange}
								required
								value={billingAddress?.zip}
							/>

							<Input
								label="Country"
								name="country"
								onChange={onChange}
								required
								value={billingAddress?.country}
							/>
						</div>

						<Input
							label="Phone"
							name="phoneNumber"
							onChange={onChange}
							required
							value={billingAddress?.phoneNumber}
						/>
					</div>
				</div>
			)}
		</Section>
	);
}
