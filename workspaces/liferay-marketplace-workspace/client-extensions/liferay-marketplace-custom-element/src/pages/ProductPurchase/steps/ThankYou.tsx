/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import {useLocation} from 'react-router-dom';

import ProductPurchaseFeedback from '../../../components/ProductPurchase/Feedback';
import {PRODUCT_TYPE_VOCABULARY, SOLUTION_TYPES} from '../../../enums/Product';
import {Liferay} from '../../../liferay/liferay';
import {getSiteURL} from '../../../utils/site';

type ProductPurchaseThankYouProps = {
	product: DeliveryProduct;
	productTypeCategory: PRODUCT_TYPE_VOCABULARY;
	solutionTypeSpecificationValue: SOLUTION_TYPES;
};

enum States {
	HOLD,
	PROCESSING,
}

const trialLabels = {
	[States.HOLD]: {
		body: 'You will be notified once the process begins, check your email for instructions.',
		title: 'project creation is on hold',
	},
	[States.PROCESSING]: {
		body: 'Expect two emails in 10 minutes or less to verify your project and extension environments are ready.',
		title: 'project is being created now.',
	},
};

const ProductPurchaseThankYou: React.FC<ProductPurchaseThankYouProps> = ({
	product,
	productTypeCategory,
	solutionTypeSpecificationValue,
}) => {
	const {search} = useLocation();

	const urlSearchParams = new URLSearchParams(search);

	const openDashboard = () =>
		Liferay.Util.navigate(
			`${getSiteURL()}/customer-dashboard/#/solutions/${urlSearchParams.get('orderId')}`
		);

	const getLabel = () => {
		if (productTypeCategory === PRODUCT_TYPE_VOCABULARY.SOLUTION) {
			if (solutionTypeSpecificationValue === SOLUTION_TYPES.ANALYTICS) {
				return {
					body: 'Expect an email in 10 minutes or less to get your Liferay DXP connection token or visit your dashboard.',
					title: 'workspace is being created now.',
				};
			}

			return trialLabels[
				urlSearchParams.get('state') ? States.HOLD : States.PROCESSING
			];
		}

		return {
			body: '',
			title: '',
		};
	};

	const labels = getLabel();

	return (
		<ProductPurchaseFeedback
			className="mt-9"
			description={labels.body}
			title={
				<span>
					Your{' '}
					<ProductPurchaseFeedback.Highlight>
						{product.name}
					</ProductPurchaseFeedback.Highlight>{' '}
					{labels.title}
				</span>
			}
		>
			<div className="d-flex">
				<ClayButton
					displayType="secondary"
					onClick={() => Liferay.Util.navigate(getSiteURL())}
				>
					Return to Marketplace
				</ClayButton>

				<ClayButton
					className="ml-4"
					displayType="primary"
					onClick={openDashboard}
				>
					Go Dashboard
				</ClayButton>
			</div>
		</ProductPurchaseFeedback>
	);
};

export default ProductPurchaseThankYou;
