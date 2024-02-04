/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ComponentProps} from 'react';

import ProductFooter from './ProductFooter';

type ContainerProps = {
	children: React.ReactNode;
	footerProps: ComponentProps<typeof ProductFooter>;
	title: string;
} & React.HTMLAttributes<HTMLDivElement>;

const Container: React.FC<ContainerProps> = ({
	children,
	footerProps,
	title,
	...containerProps
}) => {
	return (
		<div {...containerProps}>
			<h1 className="my-4 text-center">{title}</h1>

			<div className="mt-2">{children}</div>

			<ProductFooter {...footerProps} />
		</div>
	);
};

export default Container;
