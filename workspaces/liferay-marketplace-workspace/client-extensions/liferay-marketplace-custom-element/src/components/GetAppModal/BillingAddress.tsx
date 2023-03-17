import ClayIcon from '@clayui/icon';

import {Input} from '../../components/Input/Input';
import {Section} from '../../components/Section/Section';
import {RadioCard} from '../RadioCard/RadioCard';

export function BillingAddress({
	addresses,
	billingAddress,
	phoneNumber,
	selectedAddress,
	setBillingAddress,
	setSelectedAddress,
	setShowNewAddressButton,
	showNewAddressButton,
}: {
	addresses: PostalAddressResponse[];
	billingAddress: BillingAddress;
	phoneNumber: string;
	selectedAddress: string;
	showNewAddressButton: boolean;
	setBillingAddress: (value: BillingAddress) => void;
	setSelectedAddress: (value: string) => void;
	setShowNewAddressButton: (value: boolean) => void;
}) {
	function getPostalAddressDescription(address: PostalAddressResponse) {
		const description = `${address.streetAddressLine1}, ${
			address.streetAddressLine2 ? address.streetAddressLine2 + ',' : ''
		} ${address.addressLocality}, ${address.addressRegion}, ${
			address.addressCountry
		} ${address.postalCode} `;

		return {
			description,
			title: address.name,
		};
	}

	return (
		<Section className="get-app-modal-section" label="Billing Address">
			<div className="get-app-modal-section-card-addresses">
				{addresses.map((address) => {
					const {description, title} =
						getPostalAddressDescription(address);

					return (
						<RadioCard
							description={description}
							onChange={() => {
								setSelectedAddress(address.streetAddressLine1);

								const postalAddress = addresses.find(
									(address) => address.name === title
								);

								const billingAddress: BillingAddress = {
									city: postalAddress?.addressLocality,
									country: postalAddress?.addressCountry,
									countryISOCode: 'US',
									name: postalAddress?.name,
									phoneNumber,
									region: postalAddress?.addressRegion,
									street1: postalAddress?.streetAddressLine1,
									street2: postalAddress?.streetAddressLine2,
									zip: postalAddress?.postalCode,
								};

								setShowNewAddressButton(false);

								setBillingAddress(billingAddress);
							}}
							selected={
								selectedAddress === address.streetAddressLine1
							}
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
									region: '',
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
										region: target.value,
									});
								}}
								required
								value={billingAddress?.region}
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
