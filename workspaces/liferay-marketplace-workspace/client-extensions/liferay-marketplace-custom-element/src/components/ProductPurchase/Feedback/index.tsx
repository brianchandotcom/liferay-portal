/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import createdProjectIcon from '../../../assets/images/created_project.svg';

import './index.scss';

type ProductPurchaseFeedback = {
	children: React.ReactNode;
	className?: string;
	description: string | React.ReactNode;
	title: string | React.ReactNode;
};

type HighlightProps = {
	children: string;
};

const Highlight: React.FC<HighlightProps> = ({children}) => (
	<b className="highlight-text">{children}</b>
);

const ProductPurchaseFeedback: React.FC<ProductPurchaseFeedback> & {
	Highlight: typeof Highlight;
} = ({children, className, description, title}) => (
	<div className="d-flex justify-content-center w-100">
		<div
			className={classNames(
				'align-items-center d-flex flex-column justify-content-center col-3',
				className
			)}
		>
			<img
				alt="project icon"
				className="gate-card-image mb-6"
				draggable={false}
				src={createdProjectIcon}
			/>

			<div className="my-5 text-center">
				<h1>{title}</h1>

				<span>{description}</span>
			</div>

			{children}
		</div>
	</div>
);

ProductPurchaseFeedback.Highlight = Highlight;

export default ProductPurchaseFeedback;
