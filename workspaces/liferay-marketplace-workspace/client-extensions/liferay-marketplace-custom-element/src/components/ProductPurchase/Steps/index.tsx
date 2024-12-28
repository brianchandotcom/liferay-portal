/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import classNames from 'classnames';

type Step = {
	active: boolean;
	key: string;
	subTitle?: string;
	title: string;
};

type ProductPurchaseStepsProps = {
	className?: string;
	onClickIndicator?: (step: Step) => void;
	steps: Step[];
};

const ProductPurchaseSteps: React.FC<ProductPurchaseStepsProps> = ({
	className,
	onClickIndicator = () => null,
	steps,
}) => (
	<ClayMultiStepNav
		className={classNames(
			'mx-6 product-purchase--multi-step-nav',
			className
		)}
	>
		{steps.map((step, index) => (
			<ClayMultiStepNav.Item
				active={step.active}
				expand={index + 1 !== steps.length}
				key={index}
				state={
					steps.findIndex(({active}) => active) > index
						? 'complete'
						: undefined
				}
			>
				<ClayMultiStepNav.Title>{step.title}</ClayMultiStepNav.Title>
				<ClayMultiStepNav.Divider />
				<ClayMultiStepNav.Indicator
					label={1 + index}
					onClick={() => onClickIndicator(step)}
					subTitle={step.subTitle}
				/>
			</ClayMultiStepNav.Item>
		))}
	</ClayMultiStepNav>
);

export default ProductPurchaseSteps;
