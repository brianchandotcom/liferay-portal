/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RadioCard} from '../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../components/Section/Section';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../context/NewAppContext';
import {ProductWorkflowStatusCode} from '../../../../../enums/Product';
import i18n from '../../../../../i18n';
import {PRICING_OPTIONS} from '../constants';

const Pricing = () => {
	const [
		{
			_product,
			pricing: {priceModel},
		},
		dispatch,
	] = useNewAppContext();

	const isDraft = (status: number) =>
		status === ProductWorkflowStatusCode.DRAFT;

	const isSaveAsDraft = !_product || isDraft(_product.productStatus);

	const isDisabled = !isSaveAsDraft && !!_product.id;

	return (
		<Section
			className="mt-4"
			label="App Price"
			required
			tooltip="Choose Free or Paid. Apps that are free have no further payment obligations once installed."
			tooltipText={i18n.translate('more-info')}
		>
			{PRICING_OPTIONS.map((pricingOption, index) => (
				<RadioCard
					{...pricingOption}
					className="mb-5"
					disabled={isDisabled}
					key={index}
					onChange={() => {
						dispatch({
							payload: {priceModel: pricingOption.title},
							type: NewAppTypes.SET_PRICING,
						});
					}}
					selected={priceModel === pricingOption.title}
				/>
			))}
		</Section>
	);
};

export default Pricing;
