/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import classNames from 'classnames';

type Step = {
	key: string;
	subTitle?: string;
	title: string;
};

type ProductPurchaseStepsProps = {
	activeKey: string;
	className?: string;
	onClickIndicator?: (step: Step) => void;
	steps: Step[];
};

const ProductPurchaseSteps: React.FC<ProductPurchaseStepsProps> = ({
	activeKey,
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
		{steps.map((step, i) => {
			const {key, subTitle, title} = step;

			const activeIndex = steps.findIndex(
				(_step) => _step.key === activeKey
			);

			const complete = activeIndex > i;

			return (
				<ClayMultiStepNav.Item
					active={activeKey === key}
					expand={i + 1 !== steps.length}
					key={i}
					state={complete ? 'complete' : undefined}
				>
					<ClayMultiStepNav.Title>{title}</ClayMultiStepNav.Title>
					<ClayMultiStepNav.Divider />
					<ClayMultiStepNav.Indicator
						label={1 + i}
						onClick={() => onClickIndicator(step)}
						subTitle={subTitle}
					/>
				</ClayMultiStepNav.Item>
			);
		})}
	</ClayMultiStepNav>
);

export default ProductPurchaseSteps;
