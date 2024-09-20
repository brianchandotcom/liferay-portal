/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps, ReactNode} from 'react';

import ProductPurchaseFooter from '../Footer';

type ProductPurchaseShellProps = {
	as?: React.ElementType;
	children: ReactNode;
	footerProps?: ComponentProps<typeof ProductPurchaseFooter>;
	title: string;
} & React.HTMLAttributes<HTMLElement>;

const ProductPurchaseShell: React.FC<ProductPurchaseShellProps> = ({
	as: Component = 'div',
	children,
	footerProps,
	title,
}) => (
	<Component className="product-purchase-shell">
		<h1 className="my-4 text-center">{title}</h1>

		{children}

		<ProductPurchaseFooter {...footerProps} />
	</Component>
);

export default ProductPurchaseShell;
