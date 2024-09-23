/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ComponentProps, ReactNode} from 'react';

import ProductPurchaseFooter from '../Footer';

type ProductPurchaseShellProps = {
	as?: React.ElementType;
	children: ReactNode;
	footerProps?: ComponentProps<typeof ProductPurchaseFooter>;
	subtitle?: ReactNode | string;
	title: string;
} & React.HTMLAttributes<HTMLElement>;

const ProductPurchaseShell: React.FC<ProductPurchaseShellProps> = ({
	as: Component = 'div',
	children,
	footerProps,
	subtitle,
	title,
	...props
}) => (
	<Component
		{...props}
		className={classNames('product-purchase-shell', props.className)}
	>
		<div className="mb-4 product-purchase-shell-heading">
			<h1 className="my-4 text-center">{title}</h1>

			{subtitle && <span>{subtitle}</span>}
		</div>

		{children}

		<ProductPurchaseFooter {...footerProps} />
	</Component>
);

export default ProductPurchaseShell;
