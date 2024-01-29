/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useGetAppContext} from '../GetAppContextProvider';
import {getProductBasePriceAndTrial} from '../GetAppOutlet';
import {StepType} from '../enums/stepType';

type ProductHeaderPriceProps = {
	productBasePriceAndTrial: ReturnType<typeof getProductBasePriceAndTrial>;
};

const ProductHeaderPrice: React.FC<ProductHeaderPriceProps> = ({
	productBasePriceAndTrial: {basePrice, trialSku},
}) => {
	const [
		{
			currentStep,
			license: {cart, type},
			steps,
		},
	] = useGetAppContext();

	const _currentStep = steps[currentStep];

	if (
		_currentStep.id === StepType.LICENSES ||
		_currentStep.id === StepType.PAYMENT
	) {
		return (
			<span className="price-text-value">
				{cart?.id && type !== 'TRIAL'
					? `${cart.summary.totalFormatted}`
					: `$0`}
			</span>
		);
	}

	if (basePrice) {
		if (trialSku) {
			return <span>30-day trial or ${basePrice}</span>;
		}

		return <span>${basePrice}</span>;
	}

	return <span className="price-text-value">Free</span>;
};

export default ProductHeaderPrice;
