/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

type ProductPurchaseBodyProps = React.HTMLAttributes<HTMLDivElement>;

const ProductPurchaseBody: React.FC<ProductPurchaseBodyProps> = ({
	children,
	className,
	...props
}) => (
	<div
		className={classNames(
			'border d-flex flex-column p-5 rounded',
			className
		)}
		{...props}
	>
		{children}
	</div>
);

export default ProductPurchaseBody;
